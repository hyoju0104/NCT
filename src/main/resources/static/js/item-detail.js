document.addEventListener("DOMContentLoaded", function () {
    const rentBtn = document.getElementById("rentBtn");

    if (rentBtn) {
        rentBtn.addEventListener("click", function (e) {
            if (typeof planId === "undefined" || planId === null) {
                e.preventDefault();
                alert("대여는 구독한 사용자만 가능합니다.");
                window.location.href = "/user/payment";
            } else {
                window.location.href = "/order/detail/" + rentBtn.getAttribute("data-id");
            }
        });
    }
});
