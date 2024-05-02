<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page import="banking.User" %>
<%@ page import="org.json.JSONObject" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Dashboard - Banking App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <h1>Banking Application Dashboard</h1>
        <nav>
            <ul>
                <li><a href="account.jsp">My Account</a></li>
                <li><a href="transactions.jsp">Transactions</a></li>
                <!-- <li><a href="loan.jsp">Loan Services</a></li> -->
                <li><a href="logout.jsp">Logout</a></li>
            </ul>
        </nav>
    </header>
    <main>
        <section class="overview">
            <h2>Account Overview</h2>
            <%
			    User user = (User) request.getSession().getAttribute("user");
			    if (user != null) {
			        out.println("<p>Welcome back, " + user.getUsername() + "!</p>");
			    }
			%>

            <!-- Dynamic content like account balance, recent transactions can go here -->
        </section>
        
        <section class="features">
            <h2>Features</h2>
            <ul>
                <li><a href="deposit.jsp">Deposit Funds</a></li>
                <li><a href="withdraw.jsp">Withdraw Funds</a></li>
                <li><a href="transfer.jsp">Transfer Funds</a></li>
            </ul>
        </section>
    </main>
    <footer>
        <p>&copy; 2024 Banking Application. All rights reserved.</p>
    </footer>
</body>
</html>
