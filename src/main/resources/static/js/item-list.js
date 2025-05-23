document.addEventListener('DOMContentLoaded', function () {
    const cards = Array.from(document.querySelectorAll('.item-card'));
    const perLoad = 9; // 3개씩 3행씩 로드
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
