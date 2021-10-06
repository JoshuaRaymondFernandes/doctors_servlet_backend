package com.insertion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Enumeration;

import org.json.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class createAppointment
 * http://localhost:8080/project/createAppointment?doctor_id=2&patient_id=1&time=2021-09-17%2023:18:17&reason=cough
 */


@WebServlet("/createAppointment")
public class createAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String url = "jdbc:mysql://localhost:3306/portal_db";
		String user = "root";
		String dbPassword = "1joshua1";
		String query = "Insert into appointments (`doctor_id`, `patient_id`, `time`, `reason`) values (?, ?, ?, ?);";
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		
		
		try {
			String doctor_id = request.getParameter("doctor_id");
			String patient_id = request.getParameter("patient_id");
			String time = request.getParameter("time");
			String reason = request.getParameter("reason");
			
//			Enumeration<String> parameterNames = request.getParameterNames();
//			
//			System.out.println(parameterNames.nextElement());
			System.out.println(doctor_id);
			System.out.println(patient_id);
			System.out.println(time);
			System.out.println(reason);
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, dbPassword);
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, doctor_id);
			st.setString(2, patient_id);
			st.setString(3, time);
			st.setString(4, reason);
			
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
