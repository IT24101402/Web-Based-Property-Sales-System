// Get feedback ID from URL parameter
function getFeedbackIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('feedbackId');
}

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

// Load feedback data
function loadFeedbackData() {
    const feedbackId = getFeedbackIdFromUrl();

    if (!feedbackId) {
        showErrorPopup('No feedback ID provided in URL');
        return;
    }

    // Show loading state
    document.getElementById('loadingContainer').style.display = 'block';
    document.getElementById('formContainer').style.display = 'none';

    fetch(`http://localhost:8081/api/feedback/${feedbackId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Feedback not found');
            }
            return response.json();
        })
        .then(d => {
            let data = d['data']
            // Hide loading state
            document.getElementById('loadingContainer').style.display = 'none';
            document.getElementById('formContainer').style.display = 'block';

            // Populate form fields
            document.getElementById('feedbackId').value = data.id;
            document.getElementById('userRole').value = data.role;
            document.getElementById('feedbackType').value = data.type;
            document.getElementById('subject').value = data.subject;
            document.getElementById('description').value = data.description;
            document.getElementById('rating').value = data.rating;
            document.getElementById('consent').checked = data.consentGiven;

            // Update star rating display
            updateStarRating(data.rating);
        })
        .catch(error => {
            console.error('Error loading feedback:', error);
            document.getElementById('loadingContainer').style.display = 'none';
            showErrorPopup('Failed to load feedback. Please check the feedback ID and try again.');
        });
}

// Update star rating display
function updateStarRating(rating) {
    stars.forEach(star => {
        const starRating = parseInt(star.getAttribute('data-rating'));
        if (starRating <= rating) {
            star.classList.add('active');
            star.style.color = '#ffd700';
        } else {
            star.classList.remove('active');
            star.style.color = '#c8e6c9';
        }
    });
}

// Form Submit Handler
const form = document.getElementById('editFeedbackForm');

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
        const feedbackId = document.getElementById('feedbackId').value;
        const feedbackData = {
            id: parseInt(feedbackId),
            role: userRole,
            type: feedbackType,
            subject: subject,
            description: description,
            rating: parseInt(rating),
            date: new Date().toISOString().split('T')[0],
            consentGiven: consent
        };

        // Disable submit button to prevent double submission
        const submitBtn = document.querySelector('.submit-btn');
        submitBtn.disabled = true;
        submitBtn.textContent = 'Updating...';

        fetch(`http://localhost:8081/api/feedback/${feedbackId}`, {
            method: "PUT",
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
                console.log("Updated:", data);
                showSuccessPopup();
            })
            .catch(err => {
                console.error("Error:", err);
                showErrorPopup("Failed to update feedback. Please try again.");
            })
            .finally(() => {
                // Re-enable submit button
                submitBtn.disabled = false;
                submitBtn.textContent = 'Update Feedback';
            });
    }
});

// Popup Functions
function showSuccessPopup() {
    document.getElementById('successPopup').classList.add('show');
}

function closeSuccessPopup() {
    document.getElementById('successPopup').classList.remove('show');
    // Redirect to home page or feedback list
    window.location.href = "/";
}

function showErrorPopup(message) {
    document.getElementById('errorMessage').textContent = message;
    document.getElementById('errorPopup').classList.add('show');
}

function closeErrorPopup() {
    document.getElementById('errorPopup').classList.remove('show');
}

// Cancel Edit Function
function cancelEdit() {
    if (confirm('Are you sure you want to cancel? Any unsaved changes will be lost.')) {
        window.location.href = "/";
    }
}

// Load feedback data when page loads
document.addEventListener('DOMContentLoaded', function() {
    loadFeedbackData();
});