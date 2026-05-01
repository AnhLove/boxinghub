// Gộp logic để chạy mượt
document.addEventListener("DOMContentLoaded", function() {
    const sidebar = document.getElementById('sidebar');
    const icon = document.getElementById('toggleIcon');
    const toggleBtn = document.getElementById('toggleBtn');

    // Khởi tạo trạng thái Sidebar
    if (localStorage.getItem('sb') === '1') {
        sidebar.classList.add('collapsed');
        icon.className = 'bi bi-chevron-right';
    }

    toggleBtn.onclick = () => {
        let isCollapsed = sidebar.classList.toggle('collapsed');
        icon.className = isCollapsed ? 'bi bi-chevron-right' : 'bi bi-chevron-left';
        localStorage.setItem('sb', isCollapsed ? '1' : '0');
    };

    // Khởi tạo Theme
    const themeBtn = document.getElementById("themeBtn");
    const themeIcon = document.getElementById("themeIcon");

    function applyTheme(theme) {
        document.documentElement.setAttribute("data-theme", theme);
        if (themeIcon) {
            themeIcon.className = theme === "dark" ? "bi bi-sun" : "bi bi-moon-stars";
        }
        localStorage.setItem("theme", theme);
    }

    applyTheme(localStorage.getItem("theme") || "dark");

    if (themeBtn) {
        themeBtn.onclick = () => {
            let newTheme = document.documentElement.getAttribute("data-theme") === "dark" ? "light" : "dark";
            applyTheme(newTheme);
        };
    }

    const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.nav-item-link');

        navLinks.forEach(link => {
            const linkPath = link.getAttribute('href');

            // Kiểm tra nếu đường dẫn hiện tại khớp chính xác hoặc là trang con của mục đó
            if (currentPath === linkPath || (linkPath !== '/admin/dashboard' && currentPath.startsWith(linkPath))) {
                link.classList.add('active');
            } else {
                link.classList.remove('active');
            }
        });
});