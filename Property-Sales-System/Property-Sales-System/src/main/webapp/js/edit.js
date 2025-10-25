// --- utilities ---

// Get feedback ID from URL parameter
function getFeedbackIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get("feedbackId");
}

// DOM refs we'll reuse
const loadingContainer = document.getElementById("loadingContainer");
const formContainer = document.getElementById("formContainer");
const editForm = document.getElementById("editFeedbackForm");

const roleSelect = document.getElementById("userRole");
const typeSelect = document.getElementById("feedbackType");
const subjectInput = document.getElementById("subject");
const descInput = document.getElementById("description");
const consentCheckbox = document.getElementById("consent");
const ratingHiddenInput = document.getElementById("rating");
const feedbackIdInput = document.getElementById("feedbackId");

const stars = document.querySelectorAll(".star");

// --- star rating UI setup ---

function paintStars(activeRating) {
    stars.forEach(star => {
        const starVal = parseInt(star.getAttribute("data-rating"), 10);
        if (starVal <= activeRating) {
            star.classList.add("active");
            star.style.color = "#ffd700";
        } else {
            star.classList.remove("active");
            star.style.color = "#c8e6c9";
        }
    });
}

// click to set rating
stars.forEach(star => {
    star.addEventListener("click", () => {
        const newRating = parseInt(star.getAttribute("data-rating"), 10);
        ratingHiddenInput.value = newRating;
        paintStars(newRating);
    });

    // hover preview
    star.addEventListener("mouseenter", () => {
        const hoverRating = parseInt(star.getAttribute("data-rating"), 10);
        paintStars(hoverRating);
    });
});

// restore actual rating when you leave star row
document.getElementById("starRating").addEventListener("mouseleave", () => {
    const currentRating = parseInt(ratingHiddenInput.value || "0", 10);
    paintStars(currentRating);
});

// --- inline validation helpers ---

function showError(inputId, errorId, message) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);
    input.classList.add("error");
    error.textContent = message;
    error.classList.add("show");
}

function clearError(inputId, errorId) {
    const input = document.getElementById(inputId);
    const error = document.getElementById(errorId);
    input.classList.remove("error");
    error.classList.remove("show");
}

// live validation
roleSelect.addEventListener("change", () => {
    if (roleSelect.value) clearError("userRole", "roleError");
});

typeSelect.addEventListener("change", () => {
    if (typeSelect.value) clearError("feedbackType", "typeError");
});

subjectInput.addEventListener("input", () => {
    if (subjectInput.value.trim()) clearError("subject", "subjectError");
});

descInput.addEventListener("input", () => {
    if (descInput.value.trim()) clearError("description", "descriptionError");
});

consentCheckbox.addEventListener("change", () => {
    if (consentCheckbox.checked) clearError("consent", "consentError");
});

// --- popup helpers ---

function showSuccessPopup() {
    document.getElementById("successPopup").classList.add("show");
}

function closeSuccessPopup() {
    document.getElementById("successPopup").classList.remove("show");
    // go back to list after success
    window.location.href = "/feedback/list";
}

function showErrorPopup(message) {
    document.getElementById("errorMessage").textContent = message;
    document.getElementById("errorPopup").classList.add("show");
}

function closeErrorPopup() {
    document.getElementById("errorPopup").classList.remove("show");
}

// make these available to the HTML buttons
window.closeSuccessPopup = closeSuccessPopup;
window.closeErrorPopup = closeErrorPopup;

// cancel button handler
function cancelEdit() {
    if (confirm("Are you sure you want to cancel? Any unsaved changes will be lost.")) {
        window.location.href = "/feedback/list";
    }
}
window.cancelEdit = cancelEdit;

// --- load existing feedback into form ---

async function loadFeedbackData() {
    const feedbackId = getFeedbackIdFromUrl();

    if (!feedbackId) {
        showErrorPopup("No feedback ID provided in URL.");
        return;
    }

    // show loading, hide form
    loadingContainer.style.display = "block";
    formContainer.style.display = "none";

    try {
        // use relative URL so it hits the same origin (your Spring Boot 8080)
        const res = await fetch(`/api/feedback/${feedbackId}`);
        if (!res.ok) {
            throw new Error(`Server returned ${res.status}`);
        }

        const body = await res.json();
        const data = body.data; // {id, role, type, subject, description, rating, consentGiven, date,...}

        if (!data || !data.id) {
            throw new Error("Invalid response body");
        }

        // fill hidden id
        feedbackIdInput.value = data.id;

        // fill selects/inputs with backend field names
        roleSelect.value = data.role || "";
        typeSelect.value = data.type || "";
        subjectInput.value = data.subject || "";
        descInput.value = data.description || "";
        ratingHiddenInput.value = data.rating != null ? data.rating : 0;
        consentCheckbox.checked = !!data.consentGiven;

        // sync visual stars
        paintStars(parseInt(ratingHiddenInput.value || "0", 10));

        // show the form
        loadingContainer.style.display = "none";
        formContainer.style.display = "block";

    } catch (err) {
        console.error("Error loading feedback:", err);
        loadingContainer.style.display = "none";
        showErrorPopup("Failed to load feedback. Please check the feedback ID and try again.");
    }
}

// --- submit updated feedback ---

editForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    let isValid = true;

    // validate role
    if (!roleSelect.value) {
        showError("userRole", "roleError", "Please select your role");
        isValid = false;
    } else {
        clearError("userRole", "roleError");
    }

    // validate type
    if (!typeSelect.value) {
        showError("feedbackType", "typeError", "Please select feedback type");
        isValid = false;
    } else {
        clearError("feedbackType", "typeError");
    }

    // validate subject
    if (!subjectInput.value.trim()) {
        showError("subject", "subjectError", "Please enter a subject");
        isValid = false;
    } else {
        clearError("subject", "subjectError");
    }

    // validate description
    if (!descInput.value.trim()) {
        showError("description", "descriptionError", "Please enter your feedback details");
        isValid = false;
    } else {
        clearError("description", "descriptionError");
    }

    // validate rating
    if (!ratingHiddenInput.value || ratingHiddenInput.value === "0") {
        showError("rating", "ratingError", "Please provide a rating");
        isValid = false;
    } else {
        clearError("rating", "ratingError");
    }

    // validate consent
    if (!consentCheckbox.checked) {
        showError("consent", "consentError", "You must agree to continue");
        isValid = false;
    } else {
        clearError("consent", "consentError");
    }

    if (!isValid) {
        return;
    }

    const feedbackId = feedbackIdInput.value;

    // build payload to match your FeedbackModel fields
    const payload = {
        id: parseInt(feedbackId, 10),
        role: roleSelect.value,                      //  fixed
        type: typeSelect.value,                      //  fixed
        subject: subjectInput.value.trim(),
        description: descInput.value.trim(),
        rating: parseInt(ratingHiddenInput.value, 10),
        date: new Date().toISOString().split("T")[0], //  fixed
        consentGiven: consentCheckbox.checked
    };

    // disable button while saving
    const submitBtn = document.querySelector(".submit-btn");
    submitBtn.disabled = true;
    submitBtn.textContent = "Updating...";

    try {
        const res = await fetch(`/api/feedback/${feedbackId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            throw new Error(`Update failed with status ${res.status}`);
        }

        const updated = await res.json();
        console.log("Updated:", updated);

        showSuccessPopup();
    } catch (err) {
        console.error("Update error:", err);
        showErrorPopup("Failed to update feedback. Please try again.");
    } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = "Update Feedback";
    }
});

// --- init ---
document.addEventListener("DOMContentLoaded", () => {
    loadFeedbackData();
});