// Gộp logic để chạy mượt cho BoxingHub Layout
document.addEventListener("DOMContentLoaded", function() {

    // --- 1. XỬ LÝ SIDEBAR (ĐÓNG/MỞ) ---
    const sidebar = document.getElementById('sidebar');
    const icon = document.getElementById('toggleIcon');
    const toggleBtn = document.getElementById('toggleBtn');

    if (localStorage.getItem('sb') === '1' && sidebar) {
        sidebar.classList.add('collapsed');
        if (icon) icon.className = 'bi bi-chevron-right';
    }

    if (toggleBtn) {
        toggleBtn.onclick = () => {
            let isCollapsed = sidebar.classList.toggle('collapsed');
            if (icon) icon.className = isCollapsed ? 'bi bi-chevron-right' : 'bi bi-chevron-left';
            localStorage.setItem('sb', isCollapsed ? '1' : '0');
        };
    }

    // --- 2. XỬ LÝ THEME (SÁNG/TỐI) ---
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
            let currentTheme = document.documentElement.getAttribute("data-theme");
            let newTheme = currentTheme === "dark" ? "light" : "dark";
            applyTheme(newTheme);
        };
    }

    // --- 3. XỬ LÝ ACTIVE LINK (FIX LỖI NHẬN NHẦM TRANG CON) ---
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-item-link');

    navLinks.forEach(link => {
        const linkPath = link.getAttribute('href');

        // Loại bỏ class active cũ trước khi tính toán lại (tránh xung đột với Thymeleaf)
        link.classList.remove('active');

        const isExactMatch = currentPath === linkPath;

        // Logic lọc trang con:
        // Phải bắt đầu bằng linkPath + '/' VÀ linkPath đó không phải là Dashboard
        // ĐẶC BIỆT: Nếu link là 'Lớp tập nhóm', nó không được phép nhận 'Lịch tập nhóm' làm trang con
        let isSubPage = false;
        if (linkPath !== '/admin/dashboard') {
            if (currentPath.startsWith(linkPath + '/')) {
                // Nếu path hiện tại là 'schedule' nhưng link đang xét chỉ là 'group-classes' -> KHÔNG active
                if (linkPath === '/admin/group-classes' && currentPath.includes('/schedule')) {
                    isSubPage = false;
                } else {
                    isSubPage = true;
                }
            }
        }

        if (isExactMatch || isSubPage) {
            link.classList.add('active');
        }
    });
});