/**
 * BoxingHub - Quản lý học viên (Admin)
 * Chứa logic cho Modal nạp buổi tập
 */
window.openSessionModal = function(memberId, memberName) {
    console.log("Bấm nút nạp cho ID:", memberId);

    const idInput = document.getElementById('modalMemberId');
    const nameDisplay = document.getElementById('displayMemberName');
    const modalEl = document.getElementById('addSessionModal');

    if (modalEl && idInput && nameDisplay) {
        idInput.value = memberId;
        nameDisplay.innerText = memberName;

        // Khởi tạo và hiển thị modal
        const myModal = new bootstrap.Modal(modalEl);
        myModal.show();
    } else {
        console.error("Không tìm thấy các phần tử Modal trong HTML!");
    }
};