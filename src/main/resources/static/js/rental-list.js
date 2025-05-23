document.addEventListener("DOMContentLoaded", function () {
    // 1) 필터 버튼들
    const filterBtns = document.querySelectorAll('.filter-btn');
    // 2) 렌탈 내역 tr(data-status 속성 가진 행)
    const rows       = document.querySelectorAll('tbody tr[data-status]');
    // 3) no-data 문구를 넣을 tbody, 컬럼 개수 계산
    const tbody      = document.querySelector('.rental-table tbody');
    const colCount   = document.querySelectorAll('.rental-table thead th').length;

    filterBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            // active 토글
            filterBtns.forEach(b => b.classList.remove('active'));
            this.classList.add('active');

            const filter = this.dataset.filter; // all, rented, overdue, returned

            // 1) 각 row 표시/숨김
            rows.forEach(row => {
                if (filter === 'all') {
                    row.style.display = '';
                } else {
                    row.style.display =
                        (row.dataset.status === filter.toUpperCase())
                            ? ''      // 보여주기
                            : 'none'; // 숨기기
                }
            });

            // 2) 보이는 row가 하나도 없으면 '조회된 내역이 없습니다' 메시지 표시
            const visibleRows = Array.from(rows).filter(r => r.style.display !== 'none');
            let noDataRow = tbody.querySelector('.no-data-row');
            if (!noDataRow) {
                noDataRow = document.createElement('tr');
                noDataRow.classList.add('no-data-row');
                noDataRow.innerHTML = `
                  <td colspan="${colCount}" class="text-center text-secondary">
                    조회된 내역이 없습니다.
                  </td>`;
                tbody.appendChild(noDataRow);
            }
            noDataRow.style.display = visibleRows.length === 0 ? '' : 'none';
        });
    });
});
