// --- 1. BIẾN TOÀN CỤC ---
let stompClient = null;
let currentReceiverEmail = null;

const notificationSound = new Audio('/sounds/ting.mp3');

function openMiniChat(email, name, avatar) {
    if (email === currentUserEmail) return;

    currentReceiverEmail = email;
    document.getElementById('chat-target-name').innerText = name;

    // Xử lý ảnh avatar: Nếu null, rỗng hoặc là chuỗi 'null' thì dùng ảnh mặc định
    const imgEl = document.getElementById('chat-target-avatar');
    if (imgEl) {
        imgEl.src = (avatar && avatar !== 'null' && avatar !== '')
                    ? avatar
                    : '/assets/images/users/user-default.png';
    }

    document.getElementById('mini-chat-box').classList.remove('d-none');

    markMessageAsRead(email);

    const contentArea = document.getElementById('chat-content');
    contentArea.innerHTML = '<div class="text-center small text-muted">Đang tải...</div>';

    fetch(`/member/chat/history?email=${email}`)
        .then(res => res.json())
        .then(data => {
            contentArea.innerHTML = '';
            data.forEach(msg => {
                const type = (msg.senderEmail === currentUserEmail) ? 'sent' : 'received';
                renderChatMessage(msg, type);
            });
            scrollChatToBottom();
        })
        .catch(err => console.error("History error:", err));
}

document.addEventListener('DOMContentLoaded', function() {

    // --- 2. XỬ LÝ HIỂN THỊ TÊN FILE ---
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('fileNameDisplay');
    if (fileInput) {
        fileInput.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                fileNameDisplay.textContent = this.files[0].name;
            }
        });
    }

    // --- 3. XỬ LÝ COMMENT AJAX ---
    const commentForms = document.querySelectorAll('.ajax-comment-form');
    commentForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            handleCommentSubmit(this);
        });
    });

    // --- 4. TÍCH HỢP CHAT MINI & WEBSOCKET ---
    initWebSocket();

        document.addEventListener('click', function(e) {
            const trigger = e.target.closest('.chat-trigger');
            if (trigger) {
                const email = trigger.getAttribute('data-email');
                const name = trigger.getAttribute('data-name');
                const avatar = trigger.getAttribute('data-avatar');
                openMiniChat(email, name, avatar);
            }
        });

        document.getElementById('btn-send-chat')?.addEventListener('click', sendChatData);
        document.getElementById('chat-input')?.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') sendChatData();
        });
        document.getElementById('btn-close-chat')?.addEventListener('click', closeMiniChat);

        const urlParams = new URLSearchParams(window.location.search);
        const chatWithEmail = urlParams.get('chatWith');

        if (chatWithEmail) {
            const chatWithName = urlParams.get('name');
            const chatWithAvatar = urlParams.get('avatar');

            const displayName = chatWithName ? decodeURIComponent(chatWithName) : "Thành viên";
            const displayAvatar = chatWithAvatar ? decodeURIComponent(chatWithAvatar) : null;

            // Mở khung chat
            openMiniChat(chatWithEmail, displayName, displayAvatar);

            // QUAN TRỌNG: Xóa tham số trên URL mà không gây reload trang
            // Điều này ngăn việc F5 tự mở lại chat
            const newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
            window.history.replaceState({ path: newUrl }, '', newUrl);
        }
    });

// --- 6. CÁC HÀM XỬ LÝ CHI TIẾT ---

function initWebSocket() {
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;

    stompClient.connect({}, function (frame) {
        console.log('Chat Connected');
        stompClient.subscribe('/user/queue/messages', function (message) {
            const msg = JSON.parse(message.body);

            // Luôn cập nhật badge trên header bất kể đang ở đâu
            if (typeof updateNavChatInfo === 'function') {
                updateNavChatInfo();
            }

            if (currentReceiverEmail === msg.senderEmail) {
                // TRƯỜNG HỢP 1: Đang mở đúng khung chat với người gửi
                renderChatMessage(msg, 'received');
                markMessageAsRead(msg.senderEmail);
            } else {
                // TRƯỜNG HỢP 2: Đang đóng chat hoặc chat với người khác
                // Chỉ phát âm thanh ở đây để tránh kêu khi đang nhìn vào màn hình chat
                showChatNotification(msg.senderFullName);
            }
        });
    }, (error) => console.error('WS Error:', error));
}

function sendChatData() {
    const input = document.getElementById('chat-input');
    const content = input.value.trim();

    if (content && stompClient && currentReceiverEmail) {
        const chatDTO = {
            content: content,
            senderEmail: currentUserEmail,
            receiverEmail: currentReceiverEmail,
            timestamp: new Date().toISOString()
        };

        stompClient.send("/app/chat.send", {}, JSON.stringify(chatDTO));
        renderChatMessage(chatDTO, 'sent');
        input.value = '';
    }
}

function markMessageAsRead(senderEmail) {
    const token = document.querySelector("meta[name='_csrf']")?.content;
    const header = document.querySelector("meta[name='_csrf_header']")?.content;

    fetch(`/member/chat/mark-as-read?senderEmail=${senderEmail}`, {
        method: 'POST',
        headers: { [header]: token }
    }).then(() => {
        // Đọc xong thì cập nhật lại số Badge trên Header về 0 hoặc giảm đi
        if (typeof updateNavChatInfo === 'function') {
            updateNavChatInfo();
        }
    }).catch(err => console.error("Mark as read error:", err));
}

function renderChatMessage(msg, type) {
    const chatContent = document.getElementById('chat-content');
    if (!chatContent) return;

    const msgDiv = document.createElement('div');
    msgDiv.className = `d-flex mb-2 ${type === 'sent' ? 'justify-content-end' : 'justify-content-start'}`;

    const bubbleClass = type === 'sent' ? 'bg-danger text-white' : 'bg-light text-dark border';
    const radius = type === 'sent' ? '15px 15px 0 15px' : '15px 15px 15px 0';

    msgDiv.innerHTML = `
        <div style="max-width: 80%; padding: 8px 12px; border-radius: ${radius}" class="shadow-sm ${bubbleClass} small">
            ${msg.content}
        </div>
    `;
    chatContent.appendChild(msgDiv);
    scrollChatToBottom();
}

function scrollChatToBottom() {
    const chatContent = document.getElementById('chat-content');
    if (chatContent) chatContent.scrollTop = chatContent.scrollHeight;
}

function closeMiniChat() {
    document.getElementById('mini-chat-box').classList.add('d-none');
    currentReceiverEmail = null;
}

function showChatNotification(name) {
    console.log("Tin nhắn mới từ: " + name);

    // 1. Reset nhạc về giây thứ 0
    notificationSound.currentTime = 0;

    // 2. Thiết lập âm lượng
    notificationSound.volume = 0.6;

    // 3. Phát nhạc
    notificationSound.play().catch(error => {
        console.warn("Âm báo chờ tương tác người dùng để kích hoạt lần đầu.");
    });
}
// --- 7. XỬ LÝ LIKE  ---
let isProcessingLike = false;
function handleLike(postId) {
    if (isProcessingLike) return;
    const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");
    const headers = { 'Accept': 'application/json', 'Content-Type': 'application/json' };
    if (token && header) headers[header] = token;

    const countSpan = document.getElementById(`like-count-${postId}`);
    const icon = document.getElementById(`like-icon-${postId}`);
    if (!countSpan || !icon) return;

    isProcessingLike = true;
    fetch(`/member/forum/like/${postId}`, { method: 'POST', headers: headers })
    .then(response => {
        if (response.ok) {
            let currentLikes = parseInt(countSpan.innerText);
            if (icon.classList.contains('bi-heart')) {
                icon.classList.replace('bi-heart', 'bi-heart-fill');
                icon.classList.add('text-danger');
                countSpan.innerText = currentLikes + 1;
            } else {
                icon.classList.replace('bi-heart-fill', 'bi-heart');
                icon.classList.remove('text-danger');
                countSpan.innerText = Math.max(0, currentLikes - 1);
            }
        }
    })
    .finally(() => { isProcessingLike = false; });
}
// --- XỬ LÝ VIDEO VOLUME ---
// Dùng WeakSet để tránh gắn listener trùng lặp
const boostedVideos = new WeakSet();

function boostVideoVolume() {
    document.querySelectorAll('video').forEach(v => {
        if (boostedVideos.has(v)) return; // Đã xử lý rồi, bỏ qua
        boostedVideos.add(v);

        // Chỉ set volume 1 lần khi metadata load xong
        v.addEventListener('loadedmetadata', () => {
            v.volume = 1.0;
        });
    });
}

boostVideoVolume();

// Observer chỉ theo dõi video mới thêm vào
const videoObserver = new MutationObserver(() => boostVideoVolume());
videoObserver.observe(document.body, { childList: true, subtree: true });

// Khi user nhấn play, đảm bảo volume = 1 (chỉ lần đầu)
document.addEventListener('play', function(e) {
    if (e.target.tagName === 'VIDEO' && e.target.volume < 0.1) {
        e.target.volume = 2.0;
    }
}, true);

// --- 7. XỬ LÝ COMMENT AJAX ---
function handleCommentSubmit(form) {
    const postId = form.getAttribute('data-post-id');
    const input = form.querySelector('.comment-input');
    const content = input.value.trim();
    if (!content) return;

    const token = document.querySelector("meta[name='_csrf']")?.content;
    const header = document.querySelector("meta[name='_csrf_header']")?.content;

    const formData = new URLSearchParams();
    formData.append('content', content);

    const headers = {
        'Content-Type': 'application/x-www-form-urlencoded'
    };
    // Chỉ thêm CSRF nếu tồn tại
    if (token && header) {
        headers[header] = token;
    }

    fetch(`/member/forum/comment/${postId}`, {
        method: 'POST',
        headers: headers,
        body: formData
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(t => { throw new Error(t); });
        }
        return res.json();
    })
    .then(data => {
        console.log("Comment response:", data); // DEBUG - xem field thực tế

        const list = document.getElementById(`comment-list-${postId}`);
        const newComment = document.createElement('div');
        newComment.className = 'd-flex gap-2 mb-2 align-items-start new-comment-anim';

        // Thử nhiều tên field khác nhau (tùy Controller Java trả về gì)
        const avatar = data.authorAvatar || data.avatar || data.authorAvatarUrl
                     || data.avatarUrl || '/assets/images/users/user-default.png';
        const name   = data.authorName  || data.fullName || data.name || 'Ẩn danh';
        const text   = data.content     || data.text     || '';

        newComment.innerHTML = `
            <img src="${avatar}"
                 style="width: 32px; height: 32px; object-fit: cover; border-radius: 50%;
                        flex-shrink: 0; border: 1px solid var(--border);"
                 onerror="this.src='/assets/images/users/user-default.png'">
            <div class="comment-bubble flex-grow-1 shadow-sm">
                <div class="fw-bold small text-danger">${name}</div>
                <div class="comment-text">${text}</div>
            </div>`;

        list.insertAdjacentElement('afterbegin', newComment);

        const countSpan = document.getElementById(`comment-count-${postId}`);
        if (countSpan) countSpan.innerText = parseInt(countSpan.innerText) + 1;

        input.value = '';
    })
    .catch(err => {
        console.error("Comment error:", err);
        alert("Không thể gửi bình luận. Vui lòng thử lại!");
    });
}