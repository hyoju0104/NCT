// DOM이 완전히 로드된 후 실행
document.addEventListener("DOMContentLoaded", function () {

    // 대여 버튼 요소 가져오기
    const rentBtn = document.getElementById("rentBtn");

    // 버튼이 존재할 경우 이벤트 리스너 등록
    if (rentBtn) {
        rentBtn.addEventListener("click", function (e) {

            // 기본 이동(링크) 방지
            e.preventDefault();

            // data-id 속성에서 상품 ID 추출
            const itemId = this.dataset.id;

            // data-plan-status에서 사용자 구독 상태 추출
            const planStatus = this.dataset.planStatus;

            // 조건 1: 로그인하지 않은 사용자
            if (planStatus === "" || planStatus === null || planStatus === undefined) {
                alert("로그인 한 사용자만 이용 가능합니다.");
                window.location.href = "/login";
                return;
            }

            // 조건 2: 구독하지 않은 사용자
            if (planStatus === "INACTIVE") {
                alert("구독 이용자만 대여가 가능합니다.");
                window.location.href = "/user/payment";
                return;
            }

            // 조건 3: 구독 사용자 → 대여 진행
            window.location.href = "/order/detail/" + itemId;
        });
    }
});
