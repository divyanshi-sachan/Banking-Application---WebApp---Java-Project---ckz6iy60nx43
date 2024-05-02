<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Deposit Funds - Banking App</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <header>
        <!-- Standard header/navigation -->
    </header>
    <main>
        <h2>Deposit Funds</h2>
        <form action="DepositServlet" method="post">
            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" required min="1">
            <input type="submit" value="Deposit">
        </form>
    </main>
    <footer>
        <!-- Standard footer -->
    </footer>
</body>
</html>
