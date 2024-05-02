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

import org.json.*;

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
            // TODO: Read the content of 'users.json' into a String.
    
            JSONArray users = new JSONArray(content);
    
            JSONObject senderJson = null;
            JSONObject receiverJson = null;
    
            // TODO: Iterate through users JSONArray to find the sender and receiver JSONObjects based on the username and account number.
    
            if (senderJson != null && receiverJson != null) {
                // TODO: Create a User instance for the receiver using receiverJson.
                
                performTransferOperation(senderJson, receiverJson, amount, sender, receiver);
    
                // TODO: Write updated users data back to 'users.json'.
                // Update the session with the modified sender data.
                // Redirect to a success page on successful transfer.
    
            } else {
                // Redirect to a failure page if sender or receiver is not found.
                response.sendRedirect("failure.jsp");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("failure.jsp");
        }
    }
    
    private synchronized void performTransferOperation(JSONObject senderJson, JSONObject receiverJson, double amount, User sender, User receiver) throws Exception {
        // Check sender's balance
        double senderBalance = senderJson.getDouble("balance");
        if (senderBalance < amount) {
            throw new Exception("Insufficient funds for transfer");
        }
    
        // TODO: Update balances in senderJson and receiverJson.
        // Add a transaction record for both sender and receiver using the addTransaction method.
    
        // Update User objects, if your application requires it.
        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiverBalance + amount);
    }
    
    private void addTransaction(JSONObject userJson, double amount, String type) throws JSONException {
        // TODO: Retrieve the user's transaction array from userJson.
        // Create a new transaction JSONObject with necessary details.
        // Add the new transaction to the user's transaction array.
    }
    
    private String generateTransactionId() {
        // Generate a unique transaction ID (students should implement their own logic)
        return "T" + new Random().nextInt(100000);
    }
    

}
