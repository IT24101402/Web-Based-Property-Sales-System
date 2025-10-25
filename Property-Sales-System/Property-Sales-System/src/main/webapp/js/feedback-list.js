let feedbackToDelete = null;

document.addEventListener("DOMContentLoaded", () => {
    loadFeedback();
});

// Load feedback from API
async function loadFeedback() {
    const loadingContainer = document.getElementById("loadingContainer");
    const feedbackContainer = document.getElementById("feedbackContainer");
    const noFeedbackContainer = document.getElementById("noFeedbackContainer");

    loadingContainer.style.display = "block";
    feedbackContainer.style.display = "none";
    noFeedbackContainer.style.display = "none";

    try {
        const response = await fetch("/api/feedback", {
            credentials: "include"
        });

        if (!response.ok) throw new Error("Failed to fetch feedback");

        const raw = await response.json();
        const data = Array.isArray(raw) ? raw : raw.data || [];

        loadingContainer.style.display = "none";

        if (!data.length) {
            noFeedbackContainer.style.display = "block";
        } else {
            feedbackContainer.style.display = "grid";
            renderFeedbackCards(data);
        }
    } catch (error) {
        console.error("Error loading feedback:", error);
        loadingContainer.style.display = "none";
        noFeedbackContainer.style.display = "block";
    }
}

// Render feedback cards
function renderFeedbackCards(feedbackList) {
    const container = document.getElementById("feedbackContainer");
    container.innerHTML = "";

    feedbackList.forEach(f => container.appendChild(createFeedbackCard(f)));
}

// Create feedback card
function createFeedbackCard(feedback) {
    const card = document.createElement("div");
    card.className = "feedback-card";

    const role = feedback.user?.role || feedback.user_role || feedback.role || "Unknown";
    const userId = feedback.user?.id || feedback.user_id || "N/A";
    const type = feedback.type || feedback.feedback_type || "General";
    const date = feedback.date || feedback.submission_date || "N/A";
    const rating = feedback.rating || 0;
    const subject = feedback.subject || "No Subject";
    const description = feedback.description || "No description provided.";

    const stars = generateStarRating(rating);
    const roleClass = `role-${String(role).toLowerCase()}`;

    card.innerHTML = `
        <div class="card-header">
            <div class="card-meta">
                <span class="role-badge ${roleClass}">${role}</span>
                <div class="feedback-type">${type}</div>
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

        <div class="card-subject">${subject}</div>
        <div class="card-description">${description}</div>

        <div class="card-footer">
            <div class="rating">${stars}</div>
            <div class="feedback-date">${formatDate(date)}</div>
        </div>

        <div class="card-user-info">
            <small><strong>User ID:</strong> ${userId}</small><br>
            <small><strong>User Role:</strong> ${role}</small>
        </div>
    `;

    return card;
}

// Generate star rating
function generateStarRating(rating) {
    let stars = "";
    for (let i = 1; i <= 5; i++) {
        stars += i <= rating
            ? '<i class="fa-solid fa-star star"></i>'
            : '<i class="fa-solid fa-star star empty"></i>';
    }
    return stars;
}

function formatDate(dateString) {
    if (!dateString) return "N/A";
    const date = new Date(dateString);
    return isNaN(date)
        ? dateString
        : date.toLocaleDateString("en-US", { year: "numeric", month: "short", day: "numeric" });
}

// Edit / Delete handlers
function editFeedback(feedbackId) {
    window.location.href = `/edit.html?feedbackId=${feedbackId}`;
}
function showDeleteModal(feedbackId) {
    feedbackToDelete = feedbackId;
    const modal = document.getElementById("deleteModal");
    modal.classList.add("show");
    modal.style.display = "flex";
}
function closeDeleteModal() {
    feedbackToDelete = null;
    const modal = document.getElementById("deleteModal");
    modal.classList.remove("show");
    setTimeout(() => modal.style.display = "none", 200);
}
function confirmDelete() {
    if (feedbackToDelete) deleteFeedback(feedbackToDelete);
}
async function deleteFeedback(feedbackId) {
    try {
        const response = await fetch(`/api/feedback/${feedbackId}`, {
            method: "DELETE",
            credentials: "include"
        });
        if (!response.ok) throw new Error("Failed to delete feedback");
        closeDeleteModal();
        loadFeedback();
    } catch (error) {
        console.error("Error deleting feedback:", error);
        alert("Failed to delete feedback. Please try again.");
        closeDeleteModal();
    }
}
document.getElementById("deleteModal").addEventListener("click", e => {
    if (e.target === e.currentTarget) closeDeleteModal();
});

function refreshFeedbackList() { loadFeedback(); }

// Search and filter
function filterFeedback(searchTerm, filterType = "all") {
    const cards = document.querySelectorAll(".feedback-card");
    cards.forEach(card => {
        const subject = card.querySelector(".card-subject").textContent.toLowerCase();
        const description = card.querySelector(".card-description").textContent.toLowerCase();
        const role = card.querySelector(".role-badge").textContent.toLowerCase();
        const type = card.querySelector(".feedback-type").textContent.toLowerCase();

        const matchesSearch = !searchTerm || subject.includes(searchTerm.toLowerCase()) || description.includes(searchTerm.toLowerCase());
        const matchesFilter = filterType === "all" || role.includes(filterType.toLowerCase()) || type.includes(filterType.toLowerCase());

        card.style.display = matchesSearch && matchesFilter ? "block" : "none";
    });
}