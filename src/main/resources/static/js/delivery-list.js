function completeDelivery(button) {
    if (!confirm("배송처리를 완료하시겠습니까?")) return;

    const rentalId = button.getAttribute("data-rental-id");

    $.post("/brand/delivery/complete", { rentalId: rentalId }, function (response) {
        if (response === "OK") {
            button.textContent = "배송완료";
            button.disabled = true;
            button.classList.add("disabled-btn");
        } else {
            alert("처리 실패. 다시 시도해 주세요.");
        }
    }).fail(function () {
        alert("서버 오류가 발생했습니다.");
    });
}
