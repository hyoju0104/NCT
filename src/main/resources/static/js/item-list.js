// 문서 로딩 완료 후 실행
document.addEventListener('DOMContentLoaded', function () {

    // 모든 상품 카드 요소를 배열로 가져옴
    const cards = Array.from(document.querySelectorAll('.item-card'));

    // 한 번에 보여줄 카드 수 (3행 × 3열 = 9개)
    const perLoad = 9;

    // 현재까지 화면에 로딩된 카드 수
    let loadedCount = 0;

    // 처음에 perLoad개만 표시
    cards.forEach((card, i) => {
        if (i >= perLoad) card.classList.add('hidden');
    });
    loadedCount = perLoad;

    // 다음 항목 보여주기
    function showNext() {
        const next = cards.slice(loadedCount, loadedCount + perLoad);
        next.forEach(card => card.classList.remove('hidden'));
        loadedCount += next.length;
    }

    // 스크롤 하단 감지
    window.addEventListener('scroll', () => {
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 50) {
            if (loadedCount < cards.length) {
                showNext();
            }
        }
    });
});
