document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('fileNameDisplay');

    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            if (this.files && this.files[0]) {
                const file = this.files[0];
                fileNameDisplay.textContent = file.name;

                // Kiểm tra dung lượng (Ví dụ: chặn > 50MB)
                if (file.size > 50 * 1024 * 1024) {
                    alert("File quá lớn! Vui lòng chọn file dưới 50MB.");
                    this.value = "";
                    fileNameDisplay.textContent = "";
                }
            }
        });
    }
});

function handleLike(postId) {
    // 1. Lấy CSRF từ thẻ meta
    const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

    fetch(`/member/forum/like/${postId}`, {
        method: 'POST',
        headers: {
            [header]: token
        }
    }).then(response => {
        if (response.ok) {
            // 2. Cập nhật số lượng
            const countSpan = document.getElementById(`like-count-${postId}`);
            if (countSpan) {
                countSpan.innerText = parseInt(countSpan.innerText) + 1;
            }

            // 3. Cập nhật Icon
            const icon = document.getElementById(`like-icon-${postId}`);
            if (icon) {
                icon.classList.replace('bi-heart', 'bi-heart-fill');
                icon.style.color = 'var(--danger)';
            }
        }
    }).catch(err => console.error("Lỗi khi tương tác:", err));
}