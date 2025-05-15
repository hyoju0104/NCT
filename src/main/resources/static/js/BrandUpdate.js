document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("togglePasswordBtn");
    const passwordFields = document.getElementById("passwordFields");
    const passwordInput = document.getElementById("password");
    const password2Input = document.getElementById("password2");

    toggleBtn.addEventListener("click", function () {
        const isVisible = passwordFields.style.display === "block";
        passwordFields.style.display = isVisible ? "none" : "block";
        toggleBtn.textContent = isVisible ? "비밀번호 변경하기" : "비밀번호 변경 안하기";

        if (isVisible) {
            passwordInput.value = "";
            password2Input.value = "";
        }
    });
});
