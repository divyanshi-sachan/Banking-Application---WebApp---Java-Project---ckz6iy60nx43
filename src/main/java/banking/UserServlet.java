package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("register".equals(action)) {
            handleRegistration(request, response, getServletContext());
        } else if ("login".equals(action)) {
            handleLogin(request, response, getServletContext());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONArray users = readUsers(servletContext);
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("username").equals(username) && userJson.getString("password").equals(password)) {
                // Login successful, create User object and set session attribute
                User user = createUserFromJson(userJson);
                request.getSession().setAttribute("user", user);
                request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("login.html?error=invalid_credentials");
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response,
                                     ServletContext servletContext) throws IOException, ServletException {
        String realPathToUsersFile = servletContext.getRealPath("/users.json");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        User newUser = new User(username, password, email, phone);
        JSONObject newUserJson = newUser.toJsonObject();
        JSONArray users = readUsers(servletContext);
        users.put(newUserJson);
        writeUsersToFile(users, realPathToUsersFile);
        response.sendRedirect("login.html");
    }

    private JSONArray readUsers(ServletContext servletContext) throws IOException {
        String fullPath = servletContext.getRealPath("/users.json");
        File file = new File(fullPath);
        if (!file.exists()) {
            throw new FileNotFoundException("users.json file not found");
        }
        String content = new String(Files.readAllBytes(Paths.get(fullPath)), StandardCharsets.UTF_8);
        return new JSONArray(content);
    }

    private void writeUsersToFile(JSONArray users, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(users.toString(4)); // 4 is for pretty printing
        }
    }

    private User createUserFromJson(JSONObject userJson) throws JSONException {
        String username = userJson.getString("username");
        String password = userJson.getString("password");
        String email = userJson.getString("email");
        String phone = userJson.getString("phone");
        // Assuming User class has a constructor with these parameters
        return new User(username, password, email, phone);
    }
}