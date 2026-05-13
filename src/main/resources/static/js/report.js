document.addEventListener('click', function(e) {
            var btn = e.target.closest('.btn-preview-report');
            if (!btn) return;

            var d = btn.dataset;
            openPreviewModal(
                d.reportId,
                d.type,
                d.title,
                d.content,
                d.author,
                d.avatar,
                d.mediaUrl,
                d.mediaType,
                d.reason,
                d.reporter,
                d.createdAt
            );
        });

        function openPreviewModal(reportId, type, title, content, authorName, authorAvatar, mediaUrl, mediaType, reason, reporter, createdAt) {
            document.getElementById('modal-delete-form').action = '/admin/reports/resolve/' + reportId;
            document.getElementById('modal-dismiss-form').action = '/admin/reports/resolve/' + reportId;

            document.getElementById('modal-reason').textContent = reason;
            document.getElementById('modal-reporter').textContent = reporter;
            document.getElementById('modal-created-at').textContent = createdAt;

            document.getElementById('modal-author-avatar').src = authorAvatar || '/assets/images/users/user-default.png';
            document.getElementById('modal-author-name').textContent = authorName;

            var badgeEl = document.getElementById('modal-content-type-badge');
            badgeEl.innerHTML = type === 'POST'
                ? '<span class="badge bg-primary"><i class="bi bi-file-text me-1"></i>Bài viết</span>'
                : '<span class="badge bg-secondary"><i class="bi bi-chat me-1"></i>Bình luận</span>';

            var titleEl = document.getElementById('modal-post-title');
            if (type === 'POST' && title) {
                titleEl.textContent = title;
                titleEl.classList.remove('d-none');
            } else {
                titleEl.textContent = '';
                titleEl.classList.add('d-none');
            }

            document.getElementById('modal-content-body').textContent = content || '(Không có nội dung)';

            var mediaSection = document.getElementById('modal-media-section');
            var mediaImg = document.getElementById('modal-media-img');
            var mediaVideo = document.getElementById('modal-media-video');
            var mediaVideoSrc = document.getElementById('modal-media-video-src');

            mediaImg.classList.add('d-none');
            mediaVideo.classList.add('d-none');

            if (mediaUrl && type === 'POST') {
                mediaSection.classList.remove('d-none');
                if (mediaType === 'IMAGE') {
                    mediaImg.src = mediaUrl;
                    mediaImg.classList.remove('d-none');
                } else if (mediaType === 'VIDEO') {
                    mediaVideoSrc.src = mediaUrl;
                    mediaVideo.load();
                    mediaVideo.classList.remove('d-none');
                }
            } else {
                mediaSection.classList.add('d-none');
            }

            new bootstrap.Modal(document.getElementById('previewReportModal')).show();
}