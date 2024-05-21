package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private synchronized void performWithdrawOperation(JSONObject userJson, double amount) throws JSONException {
        // Perform the withdraw logic
        double currentBalance = userJson.getDouble("balance");
        if (currentBalance >= amount) {
            userJson.put("balance", currentBalance - amount);

            // Handle transactions array
            JSONArray transactions = userJson.getJSONArray("transactions");

            JSONObject newTransaction = new JSONObject();
            newTransaction.put("transactionId", generateTransactionId());
            newTransaction.put("date", new Date().toString());
            newTransaction.put("amount", amount);
            newTransaction.put("type", "withdraw");

            transactions.put(newTransaction);
        } else {
            // Throwing an exception in case of insufficient funds
            throw new JSONException("Insufficient funds for withdrawal");
        }
    }

    private String generateTransactionId() {
        // Placeholder logic to generate a unique transaction ID
        return "T" + new Random().nextInt(100000);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if user session is not valid
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException e) {
            response.sendRedirect("failure.jsp"); // Redirect if amount is not a valid number
            return;
        }

        String realPathToUsersFile = getServletContext().getRealPath("/users.json");
        File file = new File(realPathToUsersFile);

        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray users = new JSONArray(content);

            JSONObject userJson = null;
            for (int i = 0; i < users.length(); i++) {
                JSONObject tempUserJson = users.getJSONObject(i);
                if (tempUserJson.getString("username").equals(user.getUsername())) {
                    userJson = tempUserJson;
                    performWithdrawOperation(userJson, amount);
                    break;
                }
            }

            if (userJson != null) {
                Files.write(file.toPath(), users.toString().getBytes(StandardCharsets.UTF_8));
                request.getSession().setAttribute("user", userJson); // Update user session
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("failure.jsp");
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            response.sendRedirect("failure.jsp");
        }
    }
}