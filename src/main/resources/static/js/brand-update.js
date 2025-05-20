document.addEventListener("DOMContentLoaded", function () {
    const toggle = document.getElementById('togglePasswordBtn');
    const passwordBox = document.getElementById('passwordFields');
    const password = document.getElementById('password');
    const password2 = document.getElementById('password2');

    toggle.addEventListener('click', function () {
        const isVisible = passwordBox.style.display === 'block';
        passwordBox.style.display = isVisible ? 'none' : 'block';

        if (!isVisible) {
            password.value = '';
            password2.value = '';
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const logoInput = document.getElementById("logo");
    const fileNameDisplay = document.getElementById("selectedFileName");

    if (logoInput) {
        logoInput.addEventListener("change", function () {
            const fileName = this.files.length > 0 ? this.files[0].name : "선택된 파일 없음";
            fileNameDisplay.textContent = fileName;
        });
    }
});
