let _reportType = null;
        let _reportTargetId = null;

        function openReportModal(type, targetId) {
            _reportType = type;
            _reportTargetId = targetId;
            document.querySelectorAll('input[name="reportReason"]').forEach(r => r.checked = false);
            document.getElementById('report-custom-reason').value = '';
            new bootstrap.Modal(document.getElementById('reportModal')).show();
        }

        function submitReport() {
            const selected = document.querySelector('input[name="reportReason"]:checked');
            const custom = document.getElementById('report-custom-reason').value.trim();
            const reason = selected ? selected.value : custom;

            if (!reason) {
                alert('Vui lòng chọn lý do báo cáo!');
                return;
            }

            fetch('/member/forum/report', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `targetId=${_reportTargetId}&type=${_reportType}&reason=${encodeURIComponent(reason)}`
            }).then(res => {
                bootstrap.Modal.getInstance(document.getElementById('reportModal')).hide();
                if (res.ok) alert('Đã gửi báo cáo. Cảm ơn bạn!');
                else alert('Có lỗi xảy ra, vui lòng thử lại.');
            });
}