document.addEventListener("DOMContentLoaded", function () {
    const toggle = document.getElementById('changePasswordToggle');
    const passwordBox = document.getElementById('passwordBox');
    const password = document.getElementById('password');
    const password2 = document.getElementById('password2');

    function updateState() {
        const enable = toggle.checked;
        passwordBox.style.display = enable ? 'block' : 'none';

        if (!enable) {
            password.value = '';
            password2.value = '';
        }
    }

    toggle.addEventListener('change', updateState);

    updateState();
});