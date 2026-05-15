// profile.js

// Preview avatar khi chọn ảnh
function previewImage(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => document.getElementById('avatarPreview').src = e.target.result;
        reader.readAsDataURL(input.files[0]);
    }
}

// Hiện/ẩn mật khẩu
function togglePw(id, btn) {
    const input = document.getElementById(id);
    const icon  = btn.querySelector('i');
    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.replace('bi-eye', 'bi-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.replace('bi-eye-slash', 'bi-eye');
    }
}

// Thanh độ mạnh mật khẩu
function updateStrength(val) {
    const wrapper = document.getElementById('strengthWrapper');
    const bar     = document.getElementById('pwStrengthBar');
    const label   = document.getElementById('pwStrengthLabel');

    if (!val) { wrapper.style.display = 'none'; return; }
    wrapper.style.display = 'block';

    let score = 0;
    if (val.length >= 6)           score++;
    if (val.length >= 10)          score++;
    if (/[A-Z]/.test(val))         score++;
    if (/[0-9]/.test(val))         score++;
    if (/[^A-Za-z0-9]/.test(val))  score++;

    const levels = [
        { pct: 20,  cls: 'bg-danger',  txt: 'Rất yếu'    },
        { pct: 40,  cls: 'bg-warning', txt: 'Yếu'        },
        { pct: 60,  cls: 'bg-info',    txt: 'Trung bình' },
        { pct: 80,  cls: 'bg-success', txt: 'Mạnh'       },
        { pct: 100, cls: 'bg-success', txt: 'Rất mạnh'   },
    ];
    const lvl = levels[Math.max(0, score - 1)];
    bar.style.width   = lvl.pct + '%';
    bar.className     = 'progress-bar ' + lvl.cls;
    label.textContent = 'Độ mạnh: ' + lvl.txt;
}

// Kiểm tra trùng khớp mật khẩu
function checkPasswordMatch() {
    const pw  = document.getElementById('newPassword').value;
    const cpw = document.getElementById('confirmPassword').value;
    const fb  = document.getElementById('matchFeedback');
    const btn = document.getElementById('changePwBtn');

    if (!cpw) { fb.textContent = ''; return; }

    if (pw === cpw) {
        fb.textContent = '✓ Mật khẩu khớp';
        fb.style.color = '#198754';
        btn.disabled   = false;
    } else {
        fb.textContent = '✗ Mật khẩu không khớp';
        fb.style.color = '#dc3545';
        btn.disabled   = true;
    }
}

// Cuộn xuống section đổi mật khẩu nếu có thông báo liên quan
(function () {
    const hasPwMsg = document.getElementById('changePasswordForm')
                            ?.closest('.card-box')
                            ?.querySelector('.alert');
    if (hasPwMsg) {
        hasPwMsg.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
})();