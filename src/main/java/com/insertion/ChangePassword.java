package com.insertion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ChangePassword
 * http://localhost:8080/project/ChangePassword?uname=joshua@gmail.com&password=456
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
			String url = "jdbc:mysql://localhost:3306/portal_db";
			String user = "root";
			String dbPassword = "1joshua1";
			String query = "update users set password=? where uname=?";
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			
			
			try {
				String uname = request.getParameter("uname");
				String password = request.getParameter("password");
				System.out.println(uname);
				System.out.println(password);
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, dbPassword);
				PreparedStatement st = con.prepareStatement(query);
				st.setString(1, password);
				st.setString(2, uname);

				
				int rs = st.executeUpdate();
				
				if(rs == 1) {
					json.put("status", "success");
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

