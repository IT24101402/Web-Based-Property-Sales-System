let feedbackToDelete = null;

// Load all feedback on page load
document.addEventListener('DOMContentLoaded', function() {
    loadFeedback();
});

// Load feedback from API
function loadFeedback() {
    document.getElementById('loadingContainer').style.display = 'block';
    document.getElementById('feedbackContainer').style.display = 'none';
    document.getElementById('noFeedbackContainer').style.display = 'none';

    fetch('http://localhost:8081/api/feedback')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch feedback');
            }
            return response.json();
        })
        .then(d => {
            let data = d['data']
            document.getElementById('loadingContainer').style.display = 'none';

            if (data.length === 0) {
                document.getElementById('noFeedbackContainer').style.display = 'block';
            } else {
                document.getElementById('feedbackContainer').style.display = 'grid';
                renderFeedbackCards(data);
            }
        })
        .catch(error => {
            console.error('Error loading feedback:', error);
            document.getElementById('loadingContainer').style.display = 'none';
            document.getElementById('noFeedbackContainer').style.display = 'block';
        });
}

// Render feedback cards
function renderFeedbackCards(feedbackList) {
    const container = document.getElementById('feedbackContainer');
    container.innerHTML = '';

    feedbackList.forEach(feedback => {
        const card = createFeedbackCard(feedback);
        container.appendChild(card);
    });
}

// Create individual feedback card
function createFeedbackCard(feedback) {
    const card = document.createElement('div');
    card.className = 'feedback-card';

    const stars = generateStarRating(feedback.rating);
    const roleClass = `role-${feedback.role.toLowerCase()}`;

    card.innerHTML = `
        <div class="card-header">
            <div class="card-meta">
                <span class="role-badge ${roleClass}">${feedback.role}</span>
                <div class="feedback-type">${feedback.type}</div>
            </div>
            <div class="card-actions">
                <button class="action-btn edit-btn" onclick="editFeedback(${feedback.id})" title="Edit Feedback">
                    <i class="fa-solid fa-edit"></i>
                </button>
                <button class="action-btn delete-btn" onclick="showDeleteModal(${feedback.id})" title="Delete Feedback">
                    <i class="fa-solid fa-trash"></i>
                </button>
            </div>
        </div>
        
        <div class="card-subject">${feedback.subject}</div>
        <div class="card-description">${feedback.description}</div>
        
        <div class="card-footer">
            <div class="rating">${stars}</div>
            <div class="feedback-date">${formatDate(feedback.date)}</div>
        </div>
    `;

    return card;
}

// Generate star rating HTML
function generateStarRating(rating) {
    let stars = '';
    for (let i = 1; i <= 5; i++) {
        if (i <= rating) {
            stars += '<i class="fa-solid fa-star star"></i>';
        } else {
            stars += '<i class="fa-solid fa-star star empty"></i>';
        }
    }
    return stars;
}

// Format date
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

// Edit feedback - navigate to edit page
function editFeedback(feedbackId) {
    window.location.href = `/edit?feedbackId=${feedbackId}`;
}

// Show delete confirmation modal
function showDeleteModal(feedbackId) {
    feedbackToDelete = feedbackId;
    document.getElementById('deleteModal').classList.add('show');
}

// Close delete modal
function closeDeleteModal() {
    feedbackToDelete = null;
    document.getElementById('deleteModal').classList.remove('show');
}

// Confirm delete
function confirmDelete() {
    if (feedbackToDelete) {
        deleteFeedback(feedbackToDelete);
    }
}

// Delete feedback
function deleteFeedback(feedbackId) {
    fetch(`http://localhost:8081/api/feedback/${feedbackId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete feedback');
            }
            return response.json();
        })
        .then(data => {
            console.log('Feedback deleted successfully:', data);
            closeDeleteModal();
            // Reload feedback list
            loadFeedback();
        })
        .catch(error => {
            console.error('Error deleting feedback:', error);
            alert('Failed to delete feedback. Please try again.');
            closeDeleteModal();
        });
}

// Close modal when clicking outside
document.getElementById('deleteModal').addEventListener('click', function(e) {
    if (e.target === this) {
        closeDeleteModal();
    }
});

// Refresh feedback list function (can be called from other parts of the app)
function refreshFeedbackList() {
    loadFeedback();
}

// Search and filter functionality (optional enhancement)
function filterFeedback(searchTerm, filterType = 'all') {
    const cards = document.querySelectorAll('.feedback-card');

    cards.forEach(card => {
        const subject = card.querySelector('.card-subject').textContent.toLowerCase();
        const description = card.querySelector('.card-description').textContent.toLowerCase();
        const role = card.querySelector('.role-badge').textContent.toLowerCase();
        const type = card.querySelector('.feedback-type').textContent.toLowerCase();

        const matchesSearch = searchTerm === '' ||
            subject.includes(searchTerm.toLowerCase()) ||
            description.includes(searchTerm.toLowerCase());

        const matchesFilter = filterType === 'all' ||
            role.includes(filterType.toLowerCase()) ||
            type.includes(filterType.toLowerCase());

        if (matchesSearch && matchesFilter) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}