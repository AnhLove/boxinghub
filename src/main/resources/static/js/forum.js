// --- 1. BIẾN TOÀN CỤC ---
let stompClient = null;
let currentReceiverEmail = null;

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

            // TRƯỜNG HỢP 1: Đang mở đúng khung chat với người gửi
            if (currentReceiverEmail === msg.senderEmail) {
                renderChatMessage(msg, 'received');
                markMessageAsRead(msg.senderEmail);
            }

            // TRƯỜNG HỢP 2: Bất kể có đang mở chat hay không,
            // ta luôn cập nhật lại số Badge và danh sách tin nhắn trên Header
            if (typeof updateNavChatInfo === 'function') {
                updateNavChatInfo();
            }

            // Hiển thị thông báo trình duyệt/console nếu không đang chat với người đó
            if (currentReceiverEmail !== msg.senderEmail) {
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
    // Tùy chọn: Hiển thị toast hoặc rung chuông
    console.log("Tin nhắn mới từ: " + name);
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