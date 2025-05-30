console.log('main.js file loaded');
console.log('Current URL:', window.location.href);
console.log('Document ready state:', document.readyState);

try {
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOM loaded, initializing notification system');
        const notificationDropdown = document.getElementById('notificationDropdown');
        const notificationList = document.getElementById('notificationList');
        const notificationBadge = document.getElementById('notificationBadge');

        console.log('Elements found:', {
            notificationDropdown: !!notificationDropdown,
            notificationList: !!notificationList,
            notificationBadge: !!notificationBadge
        });

        if (notificationDropdown) {
            console.log('Notification dropdown found, adding click handler');
            notificationDropdown.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('Notification dropdown clicked, fetching notifications');
                fetchNotifications();
            });
        } else {
            console.log('Notification dropdown not found');
        }

        function fetchNotifications() {
            console.log('Fetching notifications from /api/notifications');
            fetch('/api/notifications')
                .then(response => {
                    console.log('Received response:', response.status);
                    if (!response.ok) {
                        throw new Error('Network response was not ok: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Received notifications data:', data);
                    notificationList.innerHTML = '';
                    if (data.length === 0) {
                        console.log('No notifications found');
                        notificationList.innerHTML = '<li><div class="dropdown-item text-center">No new notifications</div></li>';
                        notificationBadge.classList.remove('show');
                    } else {
                        console.log('Processing', data.length, 'notifications');
                        data.forEach(notification => {
                            console.log('Processing notification:', notification);
                            const item = document.createElement('li');
                            item.innerHTML = `
                                <div class="dropdown-item">
                                    ${notification.message}
                                    <br>
                                    <small class="text-muted">${notification.timestamp}</small>
                                </div>
                            `;
                            notificationList.appendChild(item);
                        });
                        notificationBadge.textContent = data.length;
                        notificationBadge.classList.add('show');
                    }
                })
                .catch(error => {
                    console.error('Error fetching notifications:', error);
                    notificationList.innerHTML = '<li><div class="dropdown-item text-center text-danger">Error loading notifications</div></li>';
                });
        }

        // Fetch notifications every 30 seconds
        console.log('Setting up notification refresh interval');
        setInterval(fetchNotifications, 30000);
    });
} catch (error) {
    console.error('Error in main.js:', error);
} 