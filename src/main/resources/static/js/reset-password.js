// reset-password.js

function toggleVisibility(id, btn) {
    const input = document.getElementById(id);
    const icon = btn.querySelector('i');
    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.replace('fa-eye', 'fa-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.replace('fa-eye-slash', 'fa-eye');
    }
}

function checkStrength(value) {
    const fill = document.getElementById('strengthFill');
    const text = document.getElementById('strengthText');
    let score = 0;
    if (value.length >= 6) score++;
    if (value.length >= 10) score++;
    if (/[A-Z]/.test(value)) score++;
    if (/[0-9]/.test(value)) score++;
    if (/[^A-Za-z0-9]/.test(value)) score++;

    const levels = [
        { pct: '20%', color: '#dc3545', label: 'Rất yếu' },
        { pct: '40%', color: '#fd7e14', label: 'Yếu' },
        { pct: '60%', color: '#ffc107', label: 'Trung bình' },
        { pct: '80%', color: '#20c997', label: 'Mạnh' },
        { pct: '100%', color: '#198754', label: 'Rất mạnh' },
    ];
    const lvl = levels[Math.max(0, score - 1)] || levels[0];
    fill.style.width     = value.length ? lvl.pct : '0';
    fill.style.background = lvl.color;
    text.textContent     = value.length ? 'Độ mạnh: ' + lvl.label : '';
    text.style.color     = lvl.color;
}

function checkMatch() {
    const pw  = document.getElementById('newPassword').value;
    const cpw = document.getElementById('confirmPassword').value;
    const txt = document.getElementById('matchText');
    const btn = document.getElementById('submitBtn');
    if (cpw === '') { txt.textContent = ''; return; }
    if (pw === cpw) {
        txt.textContent = '✓ Mật khẩu khớp';
        txt.style.color = '#198754';
        btn.disabled    = false;
    } else {
        txt.textContent = '✗ Mật khẩu không khớp';
        txt.style.color = '#dc3545';
        btn.disabled    = true;
    }
}