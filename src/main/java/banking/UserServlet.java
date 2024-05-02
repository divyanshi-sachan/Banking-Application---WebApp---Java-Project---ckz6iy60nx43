package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import jakarta.servlet.ServletContext;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		
//		
//		System.out.println(username + " " + password);
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		System.out.println(action);
        if ("register".equals(action)) {
            try {
				handleRegistration(request, response ,  getServletContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if ("login".equals(action)) {
            try {
				handleLogin(request, response,  getServletContext());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
		
	}
	
	

	

	
	public Transaction[] convertJsonToTransactions(JSONArray transactionsJsonArray) throws JSONException {
	    Transaction[] transactions = new Transaction[transactionsJsonArray.length()];

	    for (int i = 0; i < transactionsJsonArray.length(); i++) {
	        JSONObject transactionJson = transactionsJsonArray.getJSONObject(i);
	        String transactionId = transactionJson.getString("transactionId");
	        String date = transactionJson.getString("date");
	        double amount = transactionJson.getDouble("amount");
	        String type = transactionJson.getString("type");

	        // Assuming Transaction has a constructor that matches these fields
	        transactions[i] = new Transaction(transactionId, date, amount, type);
	    }

	    return transactions;
	}

	public Loan convertJsonToLoan(JSONObject loanDetailsJson) throws JSONException {
	    String loanType = loanDetailsJson.getString("type"); // Assuming there's a type field
	    double principalAmount = loanDetailsJson.getDouble("principalAmount");
	    double interestRate = loanDetailsJson.getDouble("interestRate");
	    int loanPeriod = loanDetailsJson.getInt("tenure");

	    if ("EducationLoan".equals(loanType)) {
	        return new EducationLoan(principalAmount, interestRate, loanPeriod);
	    } else if ("PersonalLoan".equals(loanType)) {
	        return new PersonalLoan(principalAmount, interestRate, loanPeriod);
	    } else {
	        // Handle unknown loan type or throw exception
	        throw new IllegalArgumentException("Unknown loan type: " + loanType);
	    }
	}

	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, JSONException {
	    
		
		
		String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    System.out.println(username + " " + password);
	    
	    JSONArray users = readUsers(servletContext);
	    System.out.println(users);
	    System.out.println("Users have been read");
	    for (int i = 0; i < users.length(); i++) {
	        JSONObject userJson = users.getJSONObject(i);
	        if (userJson.getString("username").equals(username) && userJson.getString("password").equals(password)) {
	            // Create a User object from JSONObject

	        	String email = userJson.getString("email");
	        	String phone = userJson.getString("phone");
	        	String accNo = userJson.getString("accNo");
	        	boolean isActive = userJson.getBoolean("isActive");
	        	double balance = userJson.getDouble("balance");
	        	boolean haveTakenLoan = userJson.getBoolean("haveTakenLoan");
	        	Loan loanDetails = null;
	        	if (haveTakenLoan) {
	        	    JSONObject loanDetailsJson = userJson.optJSONObject("loanDetails"); // Use optJSONObject to handle if it's not present
	        	    if (loanDetailsJson != null) {
	        	        loanDetails = convertJsonToLoan(loanDetailsJson); // Convert only if not null
	        	    }
	        	}
	        	String typeOfAccount = userJson.getString("typeOfAccount");
	        	JSONArray transactionsJsonArray = userJson.getJSONArray("transactions");
	        	// Convert transactionsJsonArray to Transaction[] array
	        	Transaction[] transactions = convertJsonToTransactions(transactionsJsonArray); // You need to implement this method
	        	boolean isAdmin = userJson.getBoolean("isAdmin");
	        		
	        	// Assuming typeOfAccount is a string representing the account type
	        	Account accType = null;

	        	// Check the value of typeOfAccount and instantiate the appropriate account type
	        	if ("Savings".equals(typeOfAccount)) {
	        	    accType = new SavingsAccount();
	        	} else if ("Salary".equals(typeOfAccount)) {
	        	    accType = new SalaryAccount();
	        	} else if ("Current".equals(typeOfAccount)) {
	        	    accType = new CurrentAccount();
	        	} else {
	        	    // Handle the case when the account type is unknown or invalid
	        	    // For example, you can throw an exception or set accType to null
	        	    // Depending on your application's requirements
	        	    // Handle accordingly based on your use case
	        	    // Example:
	        	    throw new IllegalArgumentException("Invalid account type: " + typeOfAccount);
	        	}

	        	// Now accType holds the instance of the appropriate account type based on the value of typeOfAccount

	        	// Now create the User object with all the extracted data
	        	User user = new User(accNo, username, password, email, phone, isActive, balance, haveTakenLoan, loanDetails, accType, transactions, isAdmin);


	            // Store User object in session
	            request.getSession().setAttribute("user", user);

	            try {
	                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
	            } catch (ServletException e) {
	                System.out.println("Servlet Exception");
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return;
	        }
	    }

	    // Login failed
	    response.sendRedirect("login.html?error=invalid_credentials");
	}

	
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) throws IOException, JSONException {
		// Get the real path to 'users.json' in the server context
		String realPathToUsersFile = servletContext.getRealPath("/users.json");
		System.out.println("Real Path for writing: " + realPathToUsersFile);
	
		// Retrieve user details from the request parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
	
		// TODO: Create a new User object using the retrieved parameters.
		// The User constructor should take username, email, phone, and password as arguments.
	
		// TODO: Convert the new User object to a JSON string.
		// Hint: Use the Gson library to convert the User object to a JSON string.
	
		// Read the existing users from the 'users.json' file
		JSONArray users = readUsers(servletContext); // Ensure this method is correctly implemented
	
		// TODO: Add the new user's JSON object to the 'users' JSONArray.
	
		// Write the updated user list back to 'users.json'
		// TODO: Open a FileWriter to write to 'realPathToUsersFile' and write the 'users' JSONArray to it.
		// Ensure to format the JSON for readability and handle possible IOException.
	
		// Verification: Print the updated 'users.json' content
		// TODO: Use a Scanner to read and print each line of the 'users.json' file.
		// Handle FileNotFoundException appropriately.
	
		// Redirect to the login page after registration
		response.sendRedirect("login.html");
	}
	
	private JSONArray readUsers(ServletContext servletContext) throws IOException, JSONException {
		// Log the path retrieval
		System.out.println("Inside readUsers");
		String fullPath = servletContext.getRealPath("/users.json");
		System.out.println(fullPath);
	
		// Check if 'users.json' exists at the specified path
		// TODO: Create a File object with 'fullPath' and check if it exists.
		// Throw an IOException if the file does not exist.
	
		try {
			// TODO: Read the content of 'users.json' into a String.
			// Hint: Use Files.readAllBytes and Paths.get, and convert the result to a String.
	
			// TODO: Convert the String content to a JSONArray and return it.
			// Handle JSONException by logging the error and re-throwing the exception.
		} catch (JSONException e) {
			// Log JSON parsing error
			System.err.println("Error parsing JSON from 'users.json': " + e.getMessage());
			throw e;  // Re-throw the exception if needed
		}
	}
	

}
