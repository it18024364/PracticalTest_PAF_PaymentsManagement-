$(document).ready(function() 
{
	
	$("#alertSuccess").hide();
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validatePaymentForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidPayIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentsAPI",
		type : type,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});
});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {

		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {

			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			
			$("#divPaymentsGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {

		$("#alertError").text("Error while saving.");
		$("#alertError").show();

	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidPayIDSave").val("");
	$("#formPayment")[0].reset();
}

// UPDATE==========================================
$(document)
		.on(
				"click",
				".btnUpdate",
				function(event) {
					$("#hidPayIDSave")
							.val(
									$(this).closest("tr").find(
											'#hidPayIDUpdate').val());
					$("#cardHolderName").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#cardNumber").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#charge").val(
							$(this).closest("tr").find('td:eq(3)').text());

				});

// REMOVE=============================================================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "payID=" + $(this).data("payid"),
		dataType : "text",
		complete : function(response, status) {
			onPaymentDeleteComplete(response.responseText, status);
		}
	});
});

function onPaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validatePaymentForm() {
	// cardHolderName
	if ($("#cardHolderName").val().trim() == "") {
		return "Insert Card Holder Name.";
	}
	
	// cardNumber---------------------------
	if ($("#cardNumber").val().trim() == "") {
		return "Insert Card Number.";
	}
	// is numerical value
	var tmpCard = $("#cardNumber").val().trim();
	if (!$.isNumeric(tmpCard)) {
		return "Insert a numerical value for Card Number.";
	}
	//has 16 digits
	if ($("#cardNumber").val().length != 16) {
		return "Invalid Card.";
	}
	
	// charge-------------------------------
	if ($("#charge").val().trim() == "") {
		return "Insert Charge.";
	}
	// is numerical value
	var tmpCharge = $("#charge").val().trim();
	if (!$.isNumeric(tmpCharge)) {
		return "Insert a numerical value for Charge.";
	}
	// convert to decimal price
	$("#charge").val(parseFloat(tmpCharge).toFixed(2));

	return true;
}
