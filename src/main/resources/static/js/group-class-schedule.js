document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');

    // Kiểm tra nếu không có element calendar thì thoát để tránh lỗi các trang khác
    if (!calendarEl) return;

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'timeGridWeek',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        slotMinTime: '06:00:00',
        slotMaxTime: '22:00:00',
        locale: 'vi',
        height: '75vh',
        nowIndicator: true,
        allDaySlot: false,

        // API lấy dữ liệu sự kiện
        events: '/admin/group-classes/api/events',

        eventClick: function(info) {
            if (info.event.url) {
                window.location.href = info.event.url;
                info.jsEvent.preventDefault();
            }
        },

        loading: function(isLoading) {
            calendarEl.style.opacity = isLoading ? '0.5' : '1';
        }
    });

    calendar.render();
});