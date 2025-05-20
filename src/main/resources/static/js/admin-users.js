$(function() {

	// 1) 구독 종류별 차트
	$.getJSON('/admin/users/plan-data', data => {
		new Chart($('#planChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.planType),
				datasets: [{ data: data.map(e => e.cnt) }]
			}
		});
	});

	// 2) 대여 상태별 차트
	$.getJSON('/admin/users/status-data', data => {
		new Chart($('#statusChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.status),
				datasets: [{ data: data.map(e => e.cnt) }]
			}
		});
	});

	// 3) 브랜드별 대여 건수 차트
	$.getJSON('/admin/users/brand-data', data => {
		new Chart($('#brandChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.brandName),
				datasets: [{ data: data.map(e => e.cnt) }]
			}
		});
	});

	// 4) 연체 회원 테이블
	$.getJSON('/admin/users/late-data', list => {
		const $tb = $('#lateTbody').empty();
		list.forEach(r => {
			const $tr = $('<tr>');
			$tr.append(`<td>${r.username}</td>`);
			$tr.append(`<td><button onclick="location.href='/admin/users/${r.userId}/rentals'">상품 보기</button></td>`);
			$tr.append(`<td>${r.overdueDate}일</td>`);
			$tr.append(`<td>${r.status}</td>`);
			$tr.append($('<td>').append(
				$('<button>정지 해제</button>').click(() => {
					$.post(`/admin/users/${r.userId}/release`)
						.done(() => { alert('정지 해제 완료'); location.reload(); });
				})
			));
			$tb.append($tr);
		});
	});

});
