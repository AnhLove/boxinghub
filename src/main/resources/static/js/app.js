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
});