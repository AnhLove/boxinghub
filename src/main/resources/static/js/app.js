// ===== SIDEBAR =====
const sidebar = document.getElementById('sidebar');
const topbar = document.getElementById('topbar');
const main = document.getElementById('main-content');
const icon = document.getElementById('toggleIcon');

if (localStorage.getItem('sb') === '1') {
    sidebar.classList.add('collapsed');
    topbar.classList.add('collapsed');
    main.classList.add('collapsed');
    icon.className = 'bi bi-chevron-right';
}

document.getElementById('toggleBtn').onclick = () => {
    let c = sidebar.classList.toggle('collapsed');
    topbar.classList.toggle('collapsed');
    main.classList.toggle('collapsed');
    icon.className = c ? 'bi bi-chevron-right' : 'bi bi-chevron-left';
    localStorage.setItem('sb', c ? '1' : '0');
};

// ===== THEME =====
const themeBtn = document.getElementById("themeBtn");
const themeIcon = document.getElementById("themeIcon");

function loadTheme() {
    let theme = localStorage.getItem("theme") || "dark";
    document.documentElement.setAttribute("data-theme", theme);
    themeIcon.className = theme === "dark" ? "bi bi-sun" : "bi bi-moon";
}

loadTheme();

themeBtn.onclick = () => {
    let current = document.documentElement.getAttribute("data-theme");

    if (current === "dark") {
        document.documentElement.setAttribute("data-theme", "light");
        localStorage.setItem("theme", "light");
    } else {
        document.documentElement.setAttribute("data-theme", "dark");
        localStorage.setItem("theme", "dark");
    }

    loadTheme();
};