document.addEventListener('DOMContentLoaded', function() {
    // --- 1. XỬ LÝ HIỂN THỊ TÊN FILE ---
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

    // --- 2. XỬ LÝ COMMENT AJAX ---
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
                        <img src="${myAvatarUrl}"
                             style="width: 32px; height: 32px; object-fit: cover; border-radius: 50%; flex-shrink: 0; border: 1px solid var(--border);"
                             onerror="this.src='/assets/images/users/user-default.png'">
                        <div class="comment-bubble flex-grow-1 shadow-sm" style="background: var(--bg-3); border-radius: 15px; padding: 8px 12px;">
                            <div class="fw-bold small text-danger">Bạn vừa xong</div>
                            <div class="comment-text" style="color: var(--text) !important; font-size: 0.875rem;">${content}</div>
                        </div>
                    `;

                    if (commentList) {
                        commentList.prepend(newComment);
                    } else {

                    }

                    input.value = '';
                    if (commentCountSpan) {
                        commentCountSpan.innerText = parseInt(commentCountSpan.innerText) + 1;
                    }
                } else {
                    alert('Không thể gửi bình luận. Vui lòng thử lại.');
                }
            })
            .catch(error => console.error('Lỗi comment:', error))
            .finally(() => {
                if(submitBtn) submitBtn.disabled = false;
            });
        });
    });
});

// --- 3. XỬ LÝ LIKE ---
let isProcessingLike = false;
function handleLike(postId) {
    if (isProcessingLike) return;
    const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");
    const headers = { 'Accept': 'application/json', 'Content-Type': 'application/json' };
    if (token && header) headers[header] = token;

    const countSpan = document.getElementById(`like-count-${postId}`);
    const icon = document.getElementById(`like-icon-${postId}`);
    const btn = icon?.closest('button');

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