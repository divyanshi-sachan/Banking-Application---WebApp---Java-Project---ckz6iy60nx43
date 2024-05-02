<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="banking.User" %>
<%@ page import="banking.Transaction" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transactions - Banking App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <!-- Include your standard header/navigation -->
    </header>
    <main>
        <h2>Transaction History</h2>
        <% 
            User user = (User) session.getAttribute("user");
            if (user != null && user.getTransactions() != null && user.getTransactions().length > 0) {
                Transaction[] transactions = user.getTransactions();
        %>
        <table>
            <tr>
                <th>Transaction ID</th>
                <th>Date</th>
                <th>Amount</th>
                <th>Type</th>
            </tr>
            <% for(Transaction transaction : transactions) { %>
            <tr>
                <td><%= transaction.getTransactionId() %></td>
                 <td><%= transaction.getDate() != null ? transaction.getDate() : "N/A" %></td>
                <td><%= transaction.getAmount() %></td>
                <td><%= transaction.getType() %></td>
            </tr>
            <% } %>
        </table>
        <% 
            } else { 
        %>
            <p>No transaction history available.</p>
        <% 
            } 
        %>
    </main>
    <footer>
        <!-- Include your standard footer -->
    </footer>
</body>
</html>
