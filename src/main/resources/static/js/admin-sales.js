$(function() {
	// 1) 월별 전체 매출 차트
	const ctxMonthly = $('#monthlySalesChart')[0].getContext('2d');
	$.getJSON('/admin/sales/monthly-data', function (data) {
		const labels = data.map(d => d.month);
		const totals = data.map(d => d.total);

		new Chart(ctxMonthly, {
			type: 'line',
			data: {
				labels: labels,
				datasets: [{
					label: '월별 매출',
					data: totals,
					fill: false,
					tension: 0.1
				}]
			},
			options: {
				scales: {
					y: {beginAtZero: true}
				}
			}
		});
	});

	// 2) 구독 종류별 매출 차트
	const ctxPlan = $('#planSalesChart')[0].getContext('2d');
	$.getJSON('/admin/sales/plan-data', function (res) {
		const labels = res.months;
		const datasets = res.planNames.map(name => ({
			label: name,
			data: res.data[name],
			fill: false,
			tension: 0.1
		}));

		new Chart(ctxPlan, {
			type: 'line',
			data: {
				labels: labels,
				datasets: datasets
			},
			options: {
				scales: {
					y: {beginAtZero: true}
				}
			}
		});
	});
});
