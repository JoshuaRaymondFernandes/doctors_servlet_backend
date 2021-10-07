package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class checkAuthentication
 * http://localhost:8080/project/checkAuthentication?username=joshua@gmail.com&password=123
 */
@WebServlet("/checkAuthentication")
public class checkAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/portal_db";
		String user = "root";
		String dbPassword = "1joshua1";
		String query = "select * from users where uname=? and password =?;";
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		
		try {

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			System.out.println(username);
			
			System.out.println(password);
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, dbPassword);
			PreparedStatement st = con.prepareStatement(query);
			
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			
			
			if(rs.next()) {
				
				json.put("status", "success");
				json.put("role", rs.getString("urole"));
				out.print(json);
				out.flush();

			}
			else {
				json.put("status", "failed");
				out.print(json);
				out.flush();
			}
			
		}
		catch(Exception e) {
			json.put("status", "failed");
			out.print(json);
			out.flush();
			e.printStackTrace();
		}
		
	}
}
