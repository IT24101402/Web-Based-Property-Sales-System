// Star Rating Functionality
const stars = document.querySelectorAll('.star');
const ratingInput = document.getElementById('rating');

stars.forEach(star => {
    star.addEventListener('click', function() {
        const rating = this.getAttribute('data-rating');
        ratingInput.value = rating;

        stars.forEach(s => {
            if (s.getAttribute('data-rating') <= rating) {
                s.classList.add('active');
            } else {
                s.classList.remove('active');
            }
        });
    });

    star.addEventListener('mouseenter', function() {
        const rating = this.getAttribute('data-rating');
        stars.forEach(s => {
            if (s.getAttribute('data-rating') <= rating) {
                s.style.color = '#ffd700';
            }
        });
    });
});

document.getElementById('starRating').addEventListener('mouseleave', function() {
    const currentRating = ratingInput.value;
    stars.forEach(s => {
        if (s.getAttribute('data-rating') <= currentRating) {
            s.style.color = '#ffd700';
        } else {
            s.style.color = '#c8e6c9';
        }
    });
});

// Error Handling Functions
function showError(inputId, errorId, message) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);
    input.classList.add('error');
    error.textContent = message;
    error.classList.add('show');
}

function clearError(inputId, errorId) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);
    input.classList.remove('error');
    error.classList.remove('show');
}

// Real-time Validation
document.getElementById('userRole').addEventListener('change', function() {
    if (this.value) {
        clearError('userRole', 'roleError');
    }
});

document.getElementById('feedbackType').addEventListener('change', function() {
    if (this.value) {
        clearError('feedbackType', 'typeError');
    }
});

document.getElementById('subject').addEventListener('input', function() {
    if (this.value.trim()) {
        clearError('subject', 'subjectError');
    }
});

document.getElementById('description').addEventListener('input', function() {
    if (this.value.trim()) {
        clearError('description', 'descriptionError');
    }
});

document.getElementById('consent').addEventListener('change', function() {
    if (this.checked) {
        clearError('consent', 'consentError');
    }
});

// Form Submit Handler
const form = document.getElementById('feedbackForm');

form.addEventListener('submit', function(e) {
    e.preventDefault();

    let isValid = true;

    // Validate User Role
    const userRole = document.getElementById('userRole').value;
    if (!userRole) {
        showError('userRole', 'roleError', 'Please select your role');
        isValid = false;
    } else {
        clearError('userRole', 'roleError');
    }

    // Validate Feedback Type
    const feedbackType = document.getElementById('feedbackType').value;
    if (!feedbackType) {
        showError('feedbackType', 'typeError', 'Please select feedback type');
        isValid = false;
    } else {
        clearError('feedbackType', 'typeError');
    }

    // Validate Subject
    const subject = document.getElementById('subject').value.trim();
    if (!subject) {
        showError('subject', 'subjectError', 'Please enter a subject');
        isValid = false;
    } else {
        clearError('subject', 'subjectError');
    }

    // Validate Description
    const description = document.getElementById('description').value.trim();
    if (!description) {
        showError('description', 'descriptionError', 'Please enter your feedback details');
        isValid = false;
    } else {
        clearError('description', 'descriptionError');
    }

    // Validate Rating
    const rating = ratingInput.value;
    if (rating === '0') {
        showError('rating', 'ratingError', 'Please provide a rating');
        isValid = false;
    } else {
        clearError('rating', 'ratingError');
    }

    // Validate Consent
    const consent = document.getElementById('consent').checked;
    if (!consent) {
        showError('consent', 'consentError', 'You must agree to continue');
        isValid = false;
    } else {
        clearError('consent', 'consentError');
    }

    if (isValid) {
        const feedbackData = {
            role: userRole,
            type: feedbackType,
            subject: subject,
            description: description,
            rating: parseInt(rating),
            date: new Date().toISOString().split('T')[0],
            consentGiven: consent
        };

        fetch("http://localhost:8081/api/feedback", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(feedbackData)
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error('Network response was not ok');
                }
                return res.json();
            })
            .then(data => {
                console.log("Saved:", data);
                document.getElementById('popupOverlay').classList.add('show');
                form.reset();
                ratingInput.value = "0";

                stars.forEach(s => s.classList.remove('active'));

                window.location.href = "/"
            })
            .catch(err => {
                console.error("Error:", err);
                alert("Failed to submit feedback. Please try again.");
            });
    }
});

// Close Popup Function
function closePopup() {
    document.getElementById('popupOverlay').classList.remove('show');
}