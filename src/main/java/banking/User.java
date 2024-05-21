package banking;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean isActive;
    private double balance;
    private boolean haveTakenLoan;
    private Loan loanDetails;
    private String typeOfAccount;
    private Transaction[] transactions;
    private boolean isAdmin;

    // Constructors
    public User(String username, String password, String email, String phone,
                boolean isActive, double balance, boolean haveTakenLoan,
                Loan loanDetails, String typeOfAccount, Transaction[] transactions,
                boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.balance = balance;
        this.haveTakenLoan = haveTakenLoan;
        this.loanDetails = loanDetails;
        this.typeOfAccount = typeOfAccount;
        this.transactions = transactions;
        this.isAdmin = isAdmin;
    }

    // Getters and setters

    public JSONObject toJsonObject() {
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("username", username);
            userJson.put("password", password);
            userJson.put("email", email);
            userJson.put("phone", phone);
            userJson.put("isActive", isActive);
            userJson.put("balance", balance);
            userJson.put("haveTakenLoan", haveTakenLoan);
            userJson.put("typeOfAccount", typeOfAccount);
            userJson.put("isAdmin", isAdmin);
            // Add other attributes as needed
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson;
    }

    public static User fromJsonObject(JSONObject userJson) {
        try {
            String username = userJson.getString("username");
            String password = userJson.getString("password");
            String email = userJson.getString("email");
            String phone = userJson.getString("phone");
            boolean isActive = userJson.getBoolean("isActive");
            double balance = userJson.getDouble("balance");
            boolean haveTakenLoan = userJson.getBoolean("haveTakenLoan");
            String typeOfAccount = userJson.getString("typeOfAccount");
            boolean isAdmin = userJson.getBoolean("isAdmin");
            // Parse other attributes
            return new User(username, password, email, phone, isActive, balance,
                    haveTakenLoan, null, typeOfAccount, null, isAdmin);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Other methods as needed
}