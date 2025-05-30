console.log('main.js file loaded');
console.log('Current URL:', window.location.href);
console.log('Document ready state:', document.readyState);

try {
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOM fully loaded');
        
        // Get notification elements
        const notificationDropdown = document.getElementById('notificationDropdown');
        const notificationList = document.getElementById('notificationList');
        const notificationBadge = document.getElementById('notificationBadge');
        
        // Function to fetch notifications
        function fetchNotifications() {
            console.log('Fetching notifications...');
            fetch('/api/notifications')
                .then(response => {
                    console.log('Response status:', response.status);
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Received notifications:', data);
                    updateNotifications(data);
                })
                .catch(error => {
                    console.error('Error fetching notifications:', error);
                    notificationList.innerHTML = '<li><div class="dropdown-item text-center">Error loading notifications</div></li>';
                });
        }

        // Function to update notifications in the UI
        function updateNotifications(notifications) {
            console.log('Updating notifications UI with', notifications.length, 'notifications');
            if (notifications.length === 0) {
                notificationList.innerHTML = '<li><div class="dropdown-item text-center">No new notifications</div></li>';
                notificationBadge.classList.remove('show');
                return;
            }

            notificationBadge.textContent = notifications.length;
            notificationBadge.classList.add('show');

            notificationList.innerHTML = notifications.map(notification => `
                <li>
                    <div class="dropdown-item">
                        <div class="d-flex flex-column">
                            <div class="mb-1">
                                <strong>${notification.userName}</strong> liked your pet <strong>${notification.petName}</strong>
                            </div>
                            <div class="d-flex justify-content-between align-items-center">
                                <small class="text-muted">
                                    <i class="bi bi-telephone"></i> ${notification.phoneNumber || 'No phone number'}
                                </small>
                                <small class="text-muted ms-2">${new Date(notification.timestamp).toLocaleString()}</small>
                            </div>
                        </div>
                    </div>
                </li>
            `).join('');
        }

        // Add click event listener to notification dropdown
        if (notificationDropdown) {
            notificationDropdown.addEventListener('click', function(e) {
                e.preventDefault();
                console.log('Notification dropdown clicked');
                
                // Fetch notifications first
                fetchNotifications();
                
                // Toggle the dropdown menu
                const dropdownMenu = notificationDropdown.nextElementSibling;
                if (dropdownMenu && dropdownMenu.classList.contains('dropdown-menu')) {
                    dropdownMenu.classList.toggle('show');
                    notificationDropdown.setAttribute('aria-expanded', 
                        dropdownMenu.classList.contains('show').toString());
                }
            });

            // Close dropdown when clicking outside
            document.addEventListener('click', function(e) {
                if (!notificationDropdown.contains(e.target)) {
                    const dropdownMenu = notificationDropdown.nextElementSibling;
                    if (dropdownMenu && dropdownMenu.classList.contains('dropdown-menu')) {
                        dropdownMenu.classList.remove('show');
                        notificationDropdown.setAttribute('aria-expanded', 'false');
                    }
                }
            });
        }

        // Initial fetch of notifications
        fetchNotifications();

        // Set up periodic refresh of notifications
        const refreshInterval = 30000; // 30 seconds
        console.log('Setting up notification refresh interval:', refreshInterval, 'ms');
        setInterval(fetchNotifications, refreshInterval);
    });
} catch (error) {
    console.error('Error in main.js:', error);
} 