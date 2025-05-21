function searchAddress() {
	new daum.Postcode({
		oncomplete: function(data) {
			document.getElementById('zipcode').value      = data.zonecode;
			document.getElementById('address').value      = data.address;
			document.getElementById('addressDetail').focus();
		}
	}).open();
}