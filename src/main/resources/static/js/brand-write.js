// 사용자가 이미지를 선택했을 때 미리보기를 보여주는 함수
function previewImage() {

    // 미리보기 요소
    const preview = document.getElementById('preview');

    // 기본 이미지 영역
    const placeholder = document.querySelector('.image-preview-placeholder');

    // 사용자가 선택한 첫 번째 파일
    const file = document.getElementById('file').files[0];

    // 파일 내용을 읽기 위한 FileReader 객체 생성
    const reader = new FileReader();

    // 파일 읽기 완료 시 실행되는 콜백 함수
    reader.onloadend = function() {

        // 읽은 이미지 데이터를 img 에 출력
        preview.src = reader.result;

        // img 요소를 보여줌
        preview.style.display = 'block';

        // 기본 placeholder 숨김
        placeholder.style.display = 'none';
    }

    if (file) {
        // 파일을 Data URL로 읽음
        reader.readAsDataURL(file);
    } else {
        preview.src = '';
        preview.style.display = 'none';
        placeholder.style.display = 'flex';
    }
}