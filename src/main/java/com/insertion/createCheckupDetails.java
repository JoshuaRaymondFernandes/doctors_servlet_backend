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
 * Servlet implementation class createCheckupDetails
 */
@WebServlet("/createCheckupDetails")
public class createCheckupDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/portal_db";
		String user = "root";
		String dbPassword = "1joshua1";
		String query = "Insert into checkup (`apt_id`, `patient_id`,`pname`, `time`,`diagnosis`, `prescription`,`condition`,`amount`) values (?, ?, ?, ?, ?,?,?,?);";
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		
		try {

			String apt_id = request.getParameter("apt_id");
			String patient_id = request.getParameter("patient_id");
			String pname = request.getParameter("pname");
			String time = request.getParameter("time");
			
			String diagnosis = request.getParameter("diagnosis");
			String prescription = request.getParameter("prescription");
			String condition = request.getParameter("condition");
			String amount = request.getParameter("amount");
			
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, dbPassword);
			PreparedStatement st = con.prepareStatement(query);
			
			st.setString(1, apt_id);
			st.setString(2, patient_id);
			st.setString(3, pname);
			st.setString(4, time);
			st.setString(5, diagnosis);
			st.setString(6, prescription);
			st.setString(7, condition);
			st.setString(8, amount);
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

