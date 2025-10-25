document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".btn-buy");
    cards.forEach(btn => {
        btn.addEventListener("click", (e) => {
            const id = e.target.dataset.id;
            if (id) window.location.href = `/buyer/buy/${id}`;
        });
    });
});
