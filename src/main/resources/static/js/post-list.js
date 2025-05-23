document.addEventListener('DOMContentLoaded', function() {
	const cards = Array.from(document.querySelectorAll('.post-card'));
	const perLoad = 15;
	let loadedCount = 0;

	// 처음엔 15개 외엔 숨김
	cards.forEach((card, i) => {
		if (i >= perLoad) card.classList.add('hidden');
	});
	loadedCount = perLoad;

	// 다음 15개 보여주기
	function showNext() {
		const next = cards.slice(loadedCount, loadedCount + perLoad);
		next.forEach(card => card.classList.remove('hidden'));
		loadedCount += next.length;
	}

	// 바닥 스크롤 감지
	window.addEventListener('scroll', () => {
		if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 50) {
			if (loadedCount < cards.length) {
				showNext();
			}
		}
	});
});
