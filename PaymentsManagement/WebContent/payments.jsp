<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/payments.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payments Management </h1>
				
				<form id="formPayment" name="formPayment">
					Card Holder's Name: 
					<input id="cardHolderName" name="cardHolderName" type="text"
						class="form-control form-control-sm" placeholder="Abhiman Sangeeth">
						
					<br> Card Number:
					<input id="cardNumber" name="cardNumber" type="text"
						class="form-control form-control-sm" placeholder="1111-2222-3333-4444"> 
						
					<br> Charge: 
					<input id="charge" name="charge" type="text"
						class="form-control form-control-sm" placeholder="1500.00"> 
						
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> 
					<input type="hidden" id="hidPayIDSave" 
						name="hidPayIDSave" value="">
				</form>
				
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divPaymentsGrid">
<%
Payment paymentObj = new Payment();
out.print(paymentObj.readPayment());
%>

</div>
</div>
</div>
</div>
</body>
</html>