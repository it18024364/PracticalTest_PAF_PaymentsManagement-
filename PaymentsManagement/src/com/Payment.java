package com;

import java.sql.*;

public class Payment {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare_paf", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String name, String number, String charge)
	{
		String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
	
		// create a prepared statement
		String query = " insert into payments(`cardHolderName`,`cardNumber`,`charge`)" + "values (?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
	
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, number);
		preparedStmt.setFloat(3, Float.parseFloat(charge));
	
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		String newPayments = readPayment();
		output = "{\"status\":\"success\", \"data\": \"" +
				newPayments + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
		System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	public String readPayment()
	{
		String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
	
		// Prepare the html table to be displayed
		output = "<table border=\\\"1\\\"><tr><th>Payment ID</th><th>Card Holder Name</th><th>Card Number</th><th>Charge</th><th>Paid Date</th><th>Update</th><th>Remove</th></tr>";
		String query = "select * from payments";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
	
		// iterate through the rows in the result set
		while (rs.next())
		{
			String payID = Integer.toString(rs.getInt("payID"));
			String cardHolderName = rs.getString("cardHolderName");
			String cardNumber = rs.getString("cardNumber");
			String charge = Float.toString(rs.getFloat("charge"));
			Timestamp timestamp = rs.getTimestamp("paidDate"); 
			String paidDate = timestamp.toString();
	
			// Add into the html table
			output += "<tr><td><input id='hidPayIDUpdate' name='hidPayIDUpdate' type='hidden' value='" + payID +  "'>" + payID +"</td>";
			output += "<td>" + cardHolderName + "</td>";
			output += "<td>" + cardNumber + "</td>";
			output += "<td>" + charge + "</td>";
			output += "<td>" + paidDate + "</td>";
	
			// buttons
			output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"+"<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-payid='" +payID+ "'>" + "</td></tr>";
		}
		con.close();
		
		// Complete the html table
		output += "</table>";
		}
		catch (Exception e)
	{
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	public String updatePayment(String ID, String name, String number, String charge)
	{
		String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
	
		// create a prepared statement
		String query = "UPDATE payments SET cardHolderName=?, cardNumber=?, charge=?, paidDate=current_timestamp  WHERE payID=? ";
		PreparedStatement preparedStmt = con.prepareStatement(query);
	
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, number);
		preparedStmt.setFloat(3, Float.parseFloat(charge));
		preparedStmt.setInt(4, Integer.parseInt(ID));
	
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		String newPayments = readPayment();
		output = "{\"status\":\"success\", \"data\": \"" +
				newPayments + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
		System.err.println(e.getMessage());
	}
	return output;
	}
	
	
	public String deletePayment(String payID)
	{
		String output = "";
	try
	{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
	
		// create a prepared statement
		String query = "delete from payments where payID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
	
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(payID));
	
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		String newPayments = readPayment();
		output = "{\"status\":\"success\", \"data\": \"" +
		newPayments + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
		System.err.println(e.getMessage());
	}
	return output;
	}

}
