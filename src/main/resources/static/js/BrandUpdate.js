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

    document.querySelectorAll(".delete-file-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
            const fileId = this.getAttribute("data-fileid");
            if (!fileId) return;

            if (confirm("첨부파일을 삭제하시겠습니까?")) {
                fetch("/brand/mypage/deleteFile", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "id=" + fileId
                })
                    .then(response => response.text())
                    .then(result => {
                        if (result === "OK") {
                            alert("삭제되었습니다.");
                            location.reload();
                        } else {
                            alert("삭제 실패");
                        }
                    });
            }
        });
    });
});
