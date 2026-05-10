// --- 1. BIẾN TOÀN CỤC CHO CHAT ---
let stompClient = null;
let currentReceiverEmail = null;

document.addEventListener('DOMContentLoaded', function() {

    // --- 2. XỬ LÝ HIỂN THỊ TÊN FILE  ---
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('fileNameDisplay');
    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            if (this.files && this.files[0]) {
                const file = this.files[0];
                fileNameDisplay.textContent = file.name;
                if (file.size > 50 * 1024 * 1024) {
                    alert("File quá lớn! Vui lòng chọn file dưới 50MB.");
                    this.value = "";
                    fileNameDisplay.textContent = "";
                }
            }
        });
    }

    // --- 3. XỬ LÝ COMMENT AJAX  ---
    const commentForms = document.querySelectorAll('.ajax-comment-form');
    const myAvatarUrl = document.querySelector('.post-card img.author-avatar')?.src
                     || document.querySelector('.author-avatar')?.src
                     || '/assets/images/users/user-default.png';

    commentForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            const postId = this.getAttribute('data-post-id');
            const input = this.querySelector('.comment-input');
            const content = input.value.trim();
            const commentList = document.getElementById(`comment-list-${postId}`);
            const commentCountSpan = document.getElementById(`comment-count-${postId}`);
            const submitBtn = this.querySelector('button[type="submit"]');

            if (!content) return;
            if(submitBtn) submitBtn.disabled = true;

            const token = document.querySelector("meta[name='_csrf']")?.content;
            const header = document.querySelector("meta[name='_csrf_header']")?.content;
            const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };
            if (token && header) headers[header] = token;

            fetch(`/member/forum/comment/${postId}`, {
                method: 'POST',
                headers: headers,
                body: new URLSearchParams({ 'content': content })
            })
            .then(response => {
                if (response.ok) {
                    const newComment = document.createElement('div');
                    newComment.className = 'd-flex gap-2 mb-2 align-items-start animate__animated animate__fadeIn';
                    newComment.innerHTML = `
                        <img src="${myAvatarUrl}" style="width: 32px; height: 32px; object-fit: cover; border-radius: 50%; border: 1px solid var(--border);" onerror="this.src='/assets/images/users/user-default.png'">
                        <div class="comment-bubble flex-grow-1 shadow-sm" style="background: var(--bg-3); border-radius: 15px; padding: 8px 12px;">
                            <div class="fw-bold small text-danger">Bạn vừa xong</div>
                            <div class="comment-text" style="color: var(--text) !important; font-size: 0.875rem;">${content}</div>
                        </div>
                    `;
                    if (commentList) commentList.prepend(newComment);
                    input.value = '';
                    if (commentCountSpan) commentCountSpan.innerText = parseInt(commentCountSpan.innerText) + 1;
                }
            })
            .catch(error => console.error('Lỗi comment:', error))
            .finally(() => { if(submitBtn) submitBtn.disabled = false; });
        });
    });

    // --- 4. TÍCH HỢP CHỨC NĂNG CHAT MINI ---

    // Khởi tạo kết nối WebSocket
    const socket = new SockJS('/ws-chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null; // Tắt logs trên console để giao diện sạch hơn

    stompClient.connect({}, function (frame) {
        console.log('Kết nối WebSocket thành công');
        // Đăng ký nhận tin nhắn cá nhân
        stompClient.subscribe('/user/queue/messages', function (message) {
            const msg = JSON.parse(message.body);
            // Nếu đang mở đúng khung chat với người gửi, hiển thị luôn
            if (currentReceiverEmail === msg.senderEmail) {
                renderChatMessage(msg, 'received');
            } else {
                // Hiển thị thông báo hoặc highlight người dùng có tin nhắn mới
                showChatNotification(msg.senderFullName);
            }
        });
    }, function(error) {
        console.error('Lỗi kết nối WebSocket:', error);
    });

    // Lắng nghe sự kiện Click vào các đối tượng có class .chat-trigger
    // Ví dụ: <img class="chat-trigger" data-email="..." data-name="..." data-avatar="...">
    document.addEventListener('click', function(e) {
        const trigger = e.target.closest('.chat-trigger');
        if (trigger) {
            const email = trigger.getAttribute('data-email');
            const name = trigger.getAttribute('data-name');
            const avatar = trigger.getAttribute('data-avatar');
            openMiniChat(email, name, avatar);
        }
    });

    // Sự kiện nút gửi và đóng chat
    document.getElementById('btn-send-chat')?.addEventListener('click', sendChatData);
    document.getElementById('chat-input')?.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendChatData();
    });
    document.getElementById('btn-close-chat')?.addEventListener('click', closeMiniChat);
});

// --- 5. CÁC HÀM XỬ LÝ CHAT CHI TIẾT ---

function openMiniChat(email, name, avatar) {
    if (email === currentUserEmail) return; // Không tự chat với mình

    currentReceiverEmail = email;
    document.getElementById('chat-target-name').innerText = name;
    document.getElementById('chat-target-avatar').src = avatar || '/assets/images/users/user-default.png';
    document.getElementById('mini-chat-box').classList.remove('d-none');

    const contentArea = document.getElementById('chat-content');
    contentArea.innerHTML = '<div class="text-center small text-muted">Đang tải lịch sử...</div>';

    // Gọi API RestController lấy lịch sử chat
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
        .catch(err => console.error("Lỗi load history:", err));
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

        // Gửi qua WebSocket
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatDTO));

        // Hiển thị ngay phía mình
        renderChatMessage(chatDTO, 'sent');
        input.value = '';
        scrollChatToBottom();
    }
}

function renderChatMessage(msg, type) {
    const chatContent = document.getElementById('chat-content');
    if (!chatContent) return;

    const msgDiv = document.createElement('div');
    msgDiv.className = `d-flex mb-2 ${type === 'sent' ? 'justify-content-end' : 'justify-content-start'}`;

    const bubbleClass = type === 'sent' ? 'bg-danger text-white' : 'bg-light text-dark';
    const borderRadius = type === 'sent' ? 'border-radius: 15px 15px 0 15px;' : 'border-radius: 15px 15px 15px 0;';

    msgDiv.innerHTML = `
        <div style="max-width: 80%; padding: 8px 12px; ${borderRadius}" class="shadow-sm ${bubbleClass} small">
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
    console.log("Tin nhắn mới từ " + name);
}

// --- 6. XỬ LÝ LIKE  ---
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