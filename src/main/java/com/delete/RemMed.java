package com.delete;

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
 * Servlet implementation class RemMed
 */
@WebServlet("/RemMed")
public class RemMed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemMed() {
        super();
        // TODO Auto-generated constructor stub
    }

	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			
				String url = "jdbc:mysql://localhost:3306/portal_db";
				String user = "root";
				String dbPassword = "1joshua1";
				String query = "delete from medicine where medname=?";
				
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				JSONObject json = new JSONObject();
				
				
				try {
					String medname = request.getParameter("medname");
					
					
					
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con = DriverManager.getConnection(url, user, dbPassword);
					PreparedStatement st = con.prepareStatement(query);
					st.setString(1, medname);
					

					
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

