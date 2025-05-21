document.addEventListener("DOMContentLoaded", function () {
    const rentBtn = document.getElementById("rentBtn");

    if (rentBtn) {
        rentBtn.addEventListener("click", function (e) {
            e.preventDefault();

            const itemId = this.dataset.id;
            const planStatus = this.dataset.planStatus;

            if (!planStatus || planStatus === "INACTIVE") {
                alert("구독 이용자만 대여가 가능합니다.");
                window.location.href = "/user/payment";
            } else {
                window.location.href = "/order/detail/" + itemId;
            }
        });
    }
});
