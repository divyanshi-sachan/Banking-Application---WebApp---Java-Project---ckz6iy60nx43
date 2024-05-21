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

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String generateTransactionId() {

        return "T" + new Random().nextInt(100000); 
    }

    private synchronized void performDepositOperation(JSONObject userJson, double amount) throws JSONException {
        // Perform the deposit logic
        double currentBalance = userJson.getDouble("balance");
        userJson.put("balance", currentBalance + amount);

        // Add transaction logic here if needed

        // Handle transactions array
        JSONArray transactions = userJson.getJSONArray("transactions");

        JSONObject newTransaction = new JSONObject();
        newTransaction.put("transactionId", generateTransactionId()); // Implement this method to generate unique IDs
        newTransaction.put("date", new Date().toString()); // Use a proper date format as needed
        newTransaction.put("amount", amount);
        newTransaction.put("type", "deposit");

        transactions.put(newTransaction);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        String username = user.getUsername();

        System.out.println("Inside Deposit Servlet" + "Username is " + username);
        double amount = Double.parseDouble(request.getParameter("amount"));

        String realPathToUsersFile = getServletContext().getRealPath("/users.json");
        File file = new File(realPathToUsersFile);

        boolean success;
        try {
            success = user.performDeposit(amount);
        } catch (Exception e) {
            success = false;
            // Optionally log the exception e
        }

        if (!success) {
            response.sendRedirect("failure.jsp");
            return;
        }

        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray users = new JSONArray(content);

            JSONObject userJson = null;
            for (int i = 0; i < users.length(); i++) {
                JSONObject tempUserJson = users.getJSONObject(i);
                if (tempUserJson.getString("username").equals(username)) {
                    userJson = tempUserJson;
                    performDepositOperation(userJson, amount);
                    break;
                }
            }

            if (userJson != null) {
                Files.write(file.toPath(), users.toString().getBytes(StandardCharsets.UTF_8));
                // Return updated user or redirect as required
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