// 1) centerText 플러그인 등록
Chart.register({
	id: 'centerText',
	beforeDraw(chart) {
		const { ctx, width, height, data, options } = chart;
		const pluginOpts = options.plugins.centerText || {};
		// 데이터 합계 계산
		const total = data.datasets[0].data.reduce((sum, v) => sum + v, 0);
		// 출력할 텍스트 (플러그인 옵션으로 override 가능)
		const text    = pluginOpts.text || `${total}`;
		const lines   = text.split('\n');

		ctx.save();
		// 차트 캔버스 높이의 1/8을 폰트 크기로 사용
		const fontSize = Math.floor(height / 20);
		ctx.font = `${fontSize}px sans-serif`;
		ctx.textAlign = 'center';
		ctx.textBaseline = 'middle';

		const x = width / 2;
		const y = height / 2;
		lines.forEach((line, i) => {
			// 줄 간격 = fontSize * 1.2
			const offset = (i - (lines.length - 1) / 2) * (fontSize * 1.2);
		ctx.fillText(line, x, y + offset);
	});
ctx.restore();
	}
});


$(function() {

	// 1) 구독 종류별 차트
	$.getJSON('/admin/users/plan-data', data => {
		const total = data.reduce((sum, e) => sum + e.cnt, 0);
		new Chart($('#planChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.planType),
				datasets: [{ data: data.map(e => e.cnt) }]
			},
			options: {
				plugins: {
					centerText: {
						text: `${total} 명`
					},
					legend: { position: 'bottom' }
				}
			}
		});
	});

	// 2) 대여 상태별 차트
	$.getJSON('/admin/users/status-data', data => {
		const total = data.reduce((sum, e) => sum + e.cnt, 0);
		new Chart($('#statusChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.status),
				datasets: [{ data: data.map(e => e.cnt) }]
			},
			options: {
				plugins: {
					centerText: {
						text: `${total} 건`
					},
					legend: { position: 'bottom' }
				}
			}
		});
	});

	// 3) 브랜드별 대여 건수 차트
	$.getJSON('/admin/users/brand-data', data => {
		const total = data.reduce((sum, e) => sum + e.cnt, 0);
		new Chart($('#brandChart'), {
			type: 'doughnut',
			data: {
				labels: data.map(e => e.brandName),
				datasets: [{ data: data.map(e => e.cnt) }]
			},
			options: {
				plugins: {
					centerText: {
						text: `${total} 건`
					},
					legend: { position: 'bottom' }
				}
			}
		});
	});

	// 4) 연체 회원 테이블
	$.getJSON('/admin/users/late-data', list => {
		const $tb = $('#lateTbody').empty();
		if (list.length === 0) {
			// 데이터가 없으면 안내 문구
			$tb.append(
				$('<tr class="no-data">').append(
					$('<td colspan="6">').text('정지된 회원이 없습니다')
				)
			);
			return;
		}

		list.forEach(r => {
			const $tr = $('<tr>');

			// ID (사용자 ID 스타일 적용)
			$tr.append(`<td><span class="user-id">${r.userId}</span></td>`);

			// 사용자 계정명
			$tr.append(`<td>${r.username}</td>`);

			// 상품명
			$tr.append(`<td>${r.itemName}</td>`);

			// 초과일 (빨간색으로 강조)
			$tr.append(`<td><span class="overdue-days">${r.overdueDate}일</span></td>`);

			// 반납 상태 (상태별 배지 스타일)
			const statusClass = r.status.toLowerCase();
			$tr.append(`<td><span class="rental-status ${statusClass}">${r.status}</span></td>`);

			// 정지 해제 버튼
			$tr.append(
				$('<td>').append(
					$('<button class="unban-btn">정지 해제</button>').click(() => {
						if (confirm('정말로 정지를 해제하시겠습니까?')) {
							$.post(`/admin/users/${r.userId}/release`)
								.done(() => {
									alert('정지 해제 완료');
									location.reload();
								})
								.fail(() => {
									alert('정지 해제에 실패했습니다. 다시 시도해주세요.');
								});
						}
					})
				)
			);
			$tb.append($tr);
		});
	});
});
