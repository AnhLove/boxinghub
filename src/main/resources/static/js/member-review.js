/**
 * BoxingHub - Member Review Logic
 */

function openReviewModal(id, name) {
    // Gán dữ liệu vào Modal
    document.getElementById('modalTrainerId').value = id;
    document.getElementById('modalTrainerName').innerText = name;

    // Reset trạng thái sao về 5 sao mặc định mỗi khi mở
    updateStars(5);

    // Hiển thị Modal (Sử dụng Bootstrap 5)
    const modalElement = document.getElementById('reviewModal');
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
}

function updateStars(value) {
    document.getElementById('selectedRating').value = value;
    const stars = document.querySelectorAll('.rating-star');

    stars.forEach(s => {
        const starValue = s.getAttribute('data-value');
        if (starValue <= value) {
            s.classList.replace('bi-star', 'bi-star-fill');
        } else {
            s.classList.replace('bi-star-fill', 'bi-star');
        }
    });
}

// Khởi tạo Event Listeners sau khi DOM load xong
document.addEventListener('DOMContentLoaded', function() {
    const stars = document.querySelectorAll('.rating-star');

    stars.forEach(star => {
        star.addEventListener('click', function() {
            const val = this.getAttribute('data-value');
            updateStars(val);
        });

        // Hiệu ứng hover nhẹ (tùy chọn)
        star.addEventListener('mouseover', function() {
            this.style.transform = 'scale(1.2)';
        });
        star.addEventListener('mouseout', function() {
            this.style.transform = 'scale(1)';
        });
    });
});