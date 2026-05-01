/**
 * Dashboard Charts Logic
 * File này xử lý riêng việc vẽ biểu đồ để không làm rối file app.js chính
 */
document.addEventListener("DOMContentLoaded", function() {
    // Kiểm tra xem các canvas có tồn tại không trước khi vẽ
    const growthCtx = document.getElementById('memberGrowthChart');
    const statusCtx = document.getElementById('classStatusChart');

    if (growthCtx) {
        new Chart(growthCtx, {
            type: 'line',
            data: {
                labels: ['Tuần 1', 'Tuần 2', 'Tuần 3', 'Tuần 4'],
                datasets: [{
                    label: 'Học viên mới',
                    data: [5, 12, 8, window.dashboardData.totalMembers],
                    borderColor: '#e11d48',
                    tension: 0.4,
                    fill: true,
                    backgroundColor: 'rgba(225, 29, 72, 0.05)'
                }]
            },
            options: {
                plugins: { legend: { display: false } },
                scales: { y: { beginAtZero: true } }
            }
        });
    }

    if (statusCtx) {
        const open = window.dashboardData.openClasses;
        const total = window.dashboardData.totalClasses;

        new Chart(statusCtx, {
            type: 'doughnut',
            data: {
                labels: ['Đang mở', 'Các lớp khác'],
                datasets: [{
                    data: [open, total - open],
                    backgroundColor: ['#10b981', '#e5e7eb'],
                    borderWidth: 0
                }]
            },
            options: { cutout: '70%', plugins: { legend: { position: 'bottom' } } }
        });
    }
});