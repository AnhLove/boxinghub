/**
 * BoxingHub - Support Management JS
 */
document.addEventListener('DOMContentLoaded', function () {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    const supportForms = document.querySelectorAll('form[action*="/update-status"]');
    supportForms.forEach(form => {
        form.addEventListener('submit', function (e) {
            const select = this.querySelector('select[name="status"]');
            if (select.value === 'RESOLVED') {
                if (!confirm('Xác nhận đánh dấu báo lỗi này đã được xử lý xong?')) {
                    e.preventDefault();
                }
            }
        });
    });
});