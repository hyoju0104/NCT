$(function() {
	let monthlyChart, planChart;

	// 차트 렌더링 함수: period = 'month' | 'quarter' | 'year'
	function renderCharts(period) {

		// 월별 매출 차트 데이터 요청
		$.getJSON(`/admin/sales/monthly-data?period=${period}`, function(data) {
			const labels = data.map(d => d.month);
			const totals = data.map(d => d.total);

			// 기존 차트가 있으면 파괴
			if (monthlyChart) monthlyChart.destroy();

			const ctxMonthly = $('#monthlySalesChart')[0].getContext('2d');
			monthlyChart = new Chart(ctxMonthly, {
				type: 'line',
				data: {
					labels: labels,
					datasets: [{
						label: period === 'month' ? '월별 매출' : period === 'quarter' ? '분기별 매출' : '연간 매출',
						data: totals,
						fill: false,
						tension: 0.1
					}]
				},
				options: {
					scales: { y: { beginAtZero: true } }
				}
			});
		});

		// 구독 종류별 매출 차트 데이터 요청
		$.getJSON(`/admin/sales/plan-data?period=${period}`, function(res) {
			const labels = res.months;
			const datasets = res.planNames.map(name => ({
				label: name,
				data: res.data[name],
				fill: false,
				tension: 0.1
			}));

			if (planChart) planChart.destroy();

			const ctxPlan = $('#planSalesChart')[0].getContext('2d');
			planChart = new Chart(ctxPlan, {
				type: 'line',
				data: { labels: labels, datasets: datasets },
				options: { scales: { y: { beginAtZero: true } } }
			});
		});
	}

	// 버튼 클릭 이벤트 연결
	$('.period-btn').on('click', function() {
		$('.period-btn').removeClass('active');
		$(this).addClass('active');
		renderCharts($(this).data('period'));
	});

	// 초기 로드: 월간 차트
	renderCharts('month');
});
