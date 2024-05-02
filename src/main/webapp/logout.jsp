<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- Logout logic here --%>
<% 
    // Example logout logic
    request.getSession().invalidate();
    response.sendRedirect("login.html"); // Redirect to login page or home page after logout
%>
