document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('fileInput');
    const fileNameDisplay = document.getElementById('fileNameDisplay');

    if (fileInput) {
        fileInput.addEventListener('change', function(e) {
            if (this.files && this.files[0]) {
                const file = this.files[0];
                fileNameDisplay.textContent = file.name;

                // Kiểm tra dung lượng (Ví dụ: chặn > 50MB ngay tại client)
                if (file.size > 50 * 1024 * 1024) {
                    alert("File quá lớn! Vui lòng chọn file dưới 50MB.");
                    this.value = "";
                    fileNameDisplay.textContent = "";
                }
            }
        });
    }
});