document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll("tr[data-status-account]");

    rows.forEach(row => {
        const status = row.getAttribute("data-status-account");
        if (status === "INACTIVE") {
            row.classList.add("inactive-user-row");
        }
    });
});
