document.addEventListener("DOMContentLoaded", function () {
    const path = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link');

    navLinks.forEach(link => {
        const href = link.getAttribute('href');

        if (href && path.startsWith(href)) {
            link.classList.add('active');
        }
    });
});
