// 문서가 모두 로드되었을 때 실행
document.addEventListener("DOMContentLoaded", function () {

    // 비밀번호 토글 버튼과 관련 요소들을 가져옴
    const toggle = document.getElementById('togglePasswordBtn');
    const passwordBox = document.getElementById('passwordFields');
    const password = document.getElementById('password');
    const password2 = document.getElementById('password2');

    // 버튼 클릭 시 실행될 이벤트 핸들러 등록
    toggle.addEventListener('click', function () {

        // 현재 비밀번호 입력 필드의 표시 여부 확인
        const isVisible = passwordBox.style.display === 'block';

        // 토글: 보이면 숨기고, 숨겨져 있으면 보이게 함
        passwordBox.style.display = isVisible ? 'none' : 'block';

        // 비밀번호 필드를 새로 보여줄 때 기존 값 초기화
        if (!isVisible) {
            password.value = '';
            password2.value = '';
        }
    });
});

// 파일 선택 시 로고 파일 이름을 표시
document.addEventListener("DOMContentLoaded", function () {
    const logoInput = document.getElementById("logo");
    const fileNameDisplay = document.getElementById("selectedFileName");

    // 로고 input 요소가 존재할 경우만 이벤트 등록
    if (logoInput) {
        logoInput.addEventListener("change", function () {

            // 파일이 선택되었는지 확인
            const fileName = this.files.length > 0 ? this.files[0].name : "선택된 파일 없음";

            // 화면에 파일 이름 출력
            fileNameDisplay.textContent = fileName;

        });
    }
});
