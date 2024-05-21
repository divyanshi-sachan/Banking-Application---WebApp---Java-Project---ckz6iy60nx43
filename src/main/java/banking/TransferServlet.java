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

@WebServlet("/TransferServlet")
public class TransferServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user and transaction details from request
        User sender = (User) request.getSession().getAttribute("user");
        String recipientAccNo = request.getParameter("recipientAccNo");
        double amount = Double.parseDouble(request.getParameter("amount"));

        String realPathToUsersFile = getServletContext().getRealPath("/users.json");
        File file = new File(realPathToUsersFile);

        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            JSONArray users = new JSONArray(content);

            JSONObject senderJson = null;
            JSONObject receiverJson = null;

            // Iterate through users JSONArray to find the sender and receiver JSONObjects based on the username and account number.
            for (int i = 0; i < users.length(); i++) {
                JSONObject userJson = users.getJSONObject(i);
                if (userJson.getString("username").equals(sender.getUsername())) {
                    senderJson = userJson;
                } else if (userJson.getString("accNo").equals(recipientAccNo)) {
                    receiverJson = userJson;
                }
            }

            if (senderJson != null && receiverJson != null) {
                User receiver = User.fromJsonObject(receiverJson);
                performTransferOperation(senderJson, receiverJson, amount, sender, receiver);

                // Write updated users data back to 'users.json'.
                Files.write(file.toPath(), users.toString().getBytes(StandardCharsets.UTF_8));
                request.getSession().setAttribute("user", senderJson); // Update sender session
                response.sendRedirect("success.jsp"); // Redirect to a success page on successful transfer.
            } else {
                // Redirect to a failure page if sender or receiver is not found.
                response.sendRedirect("failure.jsp");
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            response.sendRedirect("failure.jsp");
        }
    }

    private synchronized void performTransferOperation(JSONObject senderJson, JSONObject receiverJson, double amount, User sender, User receiver) throws JSONException {
        try {
            // Check sender's balance
            double senderBalance = senderJson.getDouble("balance");
            if (senderBalance < amount) {
                throw new JSONException("Insufficient funds for transfer");
            }

            // Update balances in senderJson and receiverJson.
            senderJson.put("balance", senderBalance - amount);
            receiverJson.put("balance", receiverJson.getDouble("balance") + amount);

            // Add transaction records for both sender and receiver
            addTransaction(senderJson, amount, "debit");
            addTransaction(receiverJson, amount, "credit");

            // Update User objects
            sender.setBalance(senderBalance - amount);
            receiver.setBalance(receiver.getBalance() + amount);

        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void addTransaction(JSONObject userJson, double amount, String type) throws JSONException {
        JSONArray transactions = userJson.getJSONArray("transactions");

        JSONObject newTransaction = new JSONObject();
        newTransaction.put("transactionId", generateTransactionId());
        newTransaction.put("date", new Date().toString());
        newTransaction.put("amount", amount);
        newTransaction.put("type", type);

        transactions.put(newTransaction);
    }

    private String generateTransactionId() {
        // Generate a unique transaction ID (students should implement their own logic)
        return "T" + new Random().nextInt(100000);
    }
}