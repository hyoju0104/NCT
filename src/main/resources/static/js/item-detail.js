document.addEventListener("DOMContentLoaded", function () {
    const rentBtn = document.getElementById("rentBtn");

    if (rentBtn) {
        rentBtn.addEventListener("click", function (e) {
            e.preventDefault();

            const itemId = this.dataset.id;
            const planStatus = this.dataset.planStatus;

            // 로그인 안 한 사용자
            if (planStatus === "" || planStatus === null || planStatus === undefined) {
                alert("로그인 한 사용자만 이용 가능합니다.");
                window.location.href = "/login";
                return;
            }

            // 구독하지 않은 사용자
            if (planStatus === "INACTIVE") {
                alert("구독 이용자만 대여가 가능합니다.");
                window.location.href = "/user/payment";
                return;
            }

            // 구독 사용자
            window.location.href = "/order/detail/" + itemId;
        });
    }
});
