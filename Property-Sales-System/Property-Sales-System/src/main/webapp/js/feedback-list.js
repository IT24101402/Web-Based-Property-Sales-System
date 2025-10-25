let feedbackToDelete = null;

// Load all feedback on page load
document.addEventListener("DOMContentLoaded", () => {
    loadFeedback();
});

// Load feedback from API
async function loadFeedback() {
    const loadingContainer = document.getElementById("loadingContainer");
    const feedbackContainer = document.getElementById("feedbackContainer");
    const noFeedbackContainer = document.getElementById("noFeedbackContainer");

    // Reset visibility
    loadingContainer.style.display = "block";
    feedbackContainer.style.display = "none";
    noFeedbackContainer.style.display = "none";

    try {
        // ✅ Use relative URL so it works on any environment
        const response = await fetch("/api/feedback");
        if (!response.ok) throw new Error("Failed to fetch feedback");

        const d = await response.json();

        // ✅ Handle both possible response types
        const data = Array.isArray(d) ? d : d["data"];

        loadingContainer.style.display = "none";

        if (!data || data.length === 0) {
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

// Render feedback cards dynamically
function renderFeedbackCards(feedbackList) {
    const container = document.getElementById("feedbackContainer");
    container.innerHTML = "";

    feedbackList.forEach((feedback) => {
        const card = createFeedbackCard(feedback);
        container.appendChild(card);
    });
}

// Create a single feedback card element
function createFeedbackCard(feedback) {
    const card = document.createElement("div");
    card.className = "feedback-card";

    // Map correct DB field names
    const role = feedback.user_role || feedback.role || "Unknown";
    const type = feedback.feedback_type || feedback.type || "General";
    const date = feedback.submission_date || feedback.date;
    const rating = feedback.rating || 0;
    const subject = feedback.subject || "No Subject";
    const description = feedback.description || "No description provided.";

    const stars = generateStarRating(rating);
    const roleClass = `role-${role.toLowerCase()}`;

    card.innerHTML = `
        <div class="card-header">
            <div class="card-meta">
                <span class="role-badge ${roleClass}">
                    ${role}
                </span>
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
    `;

    return card;
}

// Generate star rating HTML
function generateStarRating(rating) {
    let stars = "";
    for (let i = 1; i <= 5; i++) {
        stars += i <= rating
            ? '<i class="fa-solid fa-star star"></i>'
            : '<i class="fa-solid fa-star star empty"></i>';
    }
    return stars;
}

// Format date nicely
function formatDate(dateString) {
    if (!dateString) return "N/A";
    const date = new Date(dateString);
    if (isNaN(date)) return dateString; // fallback for YYYY-MM-DD strings
    return date.toLocaleDateString("en-US", {
        year: "numeric",
        month: "short",
        day: "numeric",
    });
}

// Edit feedback — navigate to edit page
function editFeedback(feedbackId) {
    window.location.href = `/edit.html?feedbackId=${feedbackId}`;
}

// Show delete confirmation modal
function showDeleteModal(feedbackId) {
    feedbackToDelete = feedbackId;
    const modal = document.getElementById("deleteModal");
    modal.classList.add("show");
    modal.style.display = "flex";
}

// Close delete modal
function closeDeleteModal() {
    feedbackToDelete = null;
    const modal = document.getElementById("deleteModal");
    modal.classList.remove("show");
    setTimeout(() => {
        modal.style.display = "none";
    }, 200);
}

// Confirm delete
function confirmDelete() {
    if (feedbackToDelete) {
        deleteFeedback(feedbackToDelete);
    }
}

// Delete feedback
async function deleteFeedback(feedbackId) {
    try {
        const response = await fetch(`/api/feedback/${feedbackId}`, {
            method: "DELETE",
        });

        if (!response.ok) throw new Error("Failed to delete feedback");

        const data = await response.json();
        console.log("Feedback deleted successfully:", data);

        closeDeleteModal();
        loadFeedback(); // refresh list
    } catch (error) {
        console.error("Error deleting feedback:", error);
        alert("Failed to delete feedback. Please try again.");
        closeDeleteModal();
    }
}

// Close modal when clicking outside
document.getElementById("deleteModal").addEventListener("click", function (e) {
    if (e.target === this) closeDeleteModal();
});

// Refresh feedback list externally
function refreshFeedbackList() {
    loadFeedback();
}

// Search and filter functionality
function filterFeedback(searchTerm, filterType = "all") {
    const cards = document.querySelectorAll(".feedback-card");

    cards.forEach((card) => {
        const subject = card.querySelector(".card-subject").textContent.toLowerCase();
        const description = card.querySelector(".card-description").textContent.toLowerCase();
        const role = card.querySelector(".role-badge").textContent.toLowerCase();
        const type = card.querySelector(".feedback-type").textContent.toLowerCase();

        const matchesSearch =
            searchTerm === "" ||
            subject.includes(searchTerm.toLowerCase()) ||
            description.includes(searchTerm.toLowerCase());

        const matchesFilter =
            filterType === "all" ||
            role.includes(filterType.toLowerCase()) ||
            type.includes(filterType.toLowerCase());

        card.style.display = matchesSearch && matchesFilter ? "block" : "none";
    });
}