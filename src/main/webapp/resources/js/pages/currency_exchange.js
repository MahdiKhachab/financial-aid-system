function checkValue() {

	var orginalValueLL = document.getElementById('orginalValueLL').innerHTML;
	orginalValueLL = orginalValueLL.replace(/,/g, '');
	var orginalValueDD = document.getElementById('originalValueDD').innerHTML;
	orginalValueDD = orginalValueDD.replace(/,/g, '');

	var rate = document.getElementById('rate_input').value;

	rate = rate.replace(/,/g, '');

	var hMessage = document.getElementById('error-messsage-vallueLL-bt-total');

	var valueToddOrginal = document.getElementById('valueTodd_input').value;
	valueToddOrginal = valueToddOrginal.replace(/,/g, '');

	var valueTodd = valueToddOrginal / rate;
	;

	if (rate > 0 && valueTodd > orginalValueDD) {

		hMessage.style.display = 'block';
	} else {

		hMessage.style.display = 'none';
	}

	if (valueTodd >= 0 && rate > 0) {
		
	 //   var n = parseInt(String(valueTodd).replace(/\D/g,''),10);
		
		document.getElementById('totalDD').value = valueTodd;

		var buttonSave = document.getElementById('save_btn');

		if (valueTodd - orginalValueDD > 0) {

			buttonSave.classList.add("disabled");
		} else {
			buttonSave.classList.remove("disabled");

		}

	}

}