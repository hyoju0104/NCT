document.addEventListener("DOMContentLoaded", function () {
    // 1) 필터 버튼들
    const filterBtns = document.querySelectorAll('.filter-btn');
    // 2) 렌탈 내역 tr(data-status 속성 가진 행)
    const rows       = document.querySelectorAll('tbody tr[data-status]');

    filterBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            // active 토글
            filterBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');

            const filter = this.dataset.filter; // all, rented, overdue, returned

            rows.forEach(row => {
                if (filter === 'all') {
                    row.style.display = '';
                } else {
                    // data-status 값은 대문자(RENTED, OVERDUE, RETURNED)
                    row.style.display =
                        (row.dataset.status === filter.toUpperCase())
                            ? ''      // 보여주기
                            : 'none'; // 숨기기
                }
            });
        });
    });
});
