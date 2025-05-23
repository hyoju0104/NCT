function completeDelivery(button) {
    if (confirm("배송처리를 완료하시겠습니까?")) {
        button.textContent = "배송완료";
        button.disabled = true;
        button.style.backgroundColor = "#ccc";
    }
}
