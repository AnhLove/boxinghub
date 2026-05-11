/**
 * header-chat.js
 * Xử lý thông báo tin nhắn Real-time và điều hướng chat trên Header
 */

// Hàm cập nhật số tin nhắn chưa đọc và danh sách liên hệ (Sử dụng global để gọi được từ forum.js)
function updateNavChatInfo() {
    fetch('/member/chat/unread-count')
        .then(res => res.json())
        .then(count => {
            const badge = document.getElementById('unread-chat-count');
            if (badge) {
                if (count > 0) {
                    badge.innerText = count;
                    badge.classList.remove('d-none');
                } else {
                    badge.classList.add('d-none');
                }
            }
        })
        .catch(err => console.error("Lỗi fetch unread count:", err));

    fetch('/member/chat/contacts')
        .then(res => res.json())
        .then(contacts => {
            const container = document.getElementById('contact-items-placeholder');
            if (!container) return;

            if (contacts && contacts.length > 0) {
                container.innerHTML = contacts.map(c => {
                    const safeName = encodeURIComponent(c.senderFullName || 'Thành viên');
                    const safeAvatar = encodeURIComponent(c.senderAvatar || '');

                    return `
                    <li>
                        <div class="dropdown-item d-flex align-items-center gap-2 py-2"
                             style="cursor: pointer;"
                             onclick="handleHeaderChatClick('${c.senderEmail}', '${safeName}', '${safeAvatar}')">
                            <img src="${c.senderAvatar || '/assets/images/users/user-default.png'}"
                                 style="width: 35px; height: 35px; border-radius: 50%; object-fit: cover; border: 1px solid var(--border);">
                            <div class="overflow-hidden">
                                <div class="fw-bold small text-truncate" style="color: var(--text);">${c.senderFullName}</div>
                                <div class="small text-muted text-truncate">Click để nhắn tin</div>
                            </div>
                        </div>
                    </li>`;
                }).join('');
            } else {
                container.innerHTML = '<li class="text-center p-3 small text-muted">Không có tin nhắn mới</li>';
            }
        })
        .catch(err => console.error("Lỗi lấy danh sách contact:", err));
}

// Hàm điều hướng thông minh
function handleHeaderChatClick(email, name, avatar) {
    if (typeof openMiniChat === 'function') {
        // Nếu đang ở trang Forum (có hàm openMiniChat)
        openMiniChat(email, decodeURIComponent(name), decodeURIComponent(avatar));

        const chatDropdown = document.getElementById('chatDropdown');
        if (chatDropdown) {
            const dropdownInstance = bootstrap.Dropdown.getInstance(chatDropdown);
            if (dropdownInstance) dropdownInstance.hide();
        }
    } else {
        // Nếu ở trang khác, chuyển hướng về Forum kèm tham số
        window.location.href = `/member/forum?chatWith=${email}&name=${name}&avatar=${avatar}`;
    }
}

// Khởi chạy khi trang load
document.addEventListener('DOMContentLoaded', function() {
    updateNavChatInfo();
    // Polling mỗi 30 giây để đảm bảo dữ liệu luôn mới nếu WebSocket rớt
    setInterval(updateNavChatInfo, 30000);
});