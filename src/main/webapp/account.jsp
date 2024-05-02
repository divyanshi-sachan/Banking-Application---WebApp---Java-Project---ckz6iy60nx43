<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="org.json.JSONObject" %> 
<%@ page import="banking.User" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Account - Banking App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <!-- Include your standard header/navigation -->
    </header>
	<main>
	    <h2>Account Details</h2>
	    <%-- Retrieve user details from session --%>
	    <% 
	        User user = (User) session.getAttribute("user");
	        if (user != null) {
	    %>
	        <p>Account Number: <%= user.getAccNo() %></p>
	        <p>Account Type: <%= user.getTypeOfAccount() %></p>
	        <p>Balance: <%= user.getBalance() %></p>
	        <!-- Other account details -->
	    <% } else { %>
	        <p>No user information available</p>
	    <% } %>
	</main>
    <footer>
        <!-- Include your standard footer -->
    </footer>
</body>
</html>
