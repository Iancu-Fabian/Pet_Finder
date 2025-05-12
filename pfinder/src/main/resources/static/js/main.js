// Initialize tooltips
document.addEventListener('DOMContentLoaded', function() {
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Handle image upload preview
function previewImage(input, previewId) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById(previewId).src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// Handle swipe animations
function handleSwipe(direction) {
    const petCard = document.querySelector('.pet-card');
    if (petCard) {
        petCard.style.transition = 'transform 0.5s ease-out';
        petCard.style.transform = direction === 'right' ? 'translateX(100%)' : 'translateX(-100%)';
        
        setTimeout(() => {
            // Submit the form after animation
            document.querySelector(`form[data-direction="${direction}"]`).submit();
        }, 500);
    }
}

// Add keyboard shortcuts for swiping
document.addEventListener('keydown', function(e) {
    if (e.key === 'ArrowLeft') {
        handleSwipe('left');
    } else if (e.key === 'ArrowRight') {
        handleSwipe('right');
    }
}); 