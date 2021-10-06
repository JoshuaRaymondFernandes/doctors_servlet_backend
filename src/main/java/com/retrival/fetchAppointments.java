package com.retrival;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * Servlet implementation class fetchAppointments
 * http://localhost:8080/project/fetchAppointments?doctor_id=2
 */
@WebServlet("/fetchAppointments")
public class fetchAppointments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/portal_db";
		String user = "root";
		String dbPassword = "1joshua1";
		
		String query = "select \r\n"
				+ "p.name as \"patient\", \r\n"
				+ "DATE(a.time) as \"date\", \r\n"
				+ "TIME(a.time) AS \"time\", \r\n"
				+ "a.reason\r\n"
				+ "from \r\n"
				+ "appointments a\r\n"
				+ "left join doctors d on a.doctor_id = d.doctor_id\r\n"
				+ "left join patients p on a.patient_id = p.patient_id\r\n"
				+ "where\r\n"
				+ "d.doctor_id = ?";
			
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		List<Map<String, String>> list = new ArrayList<>();
		
		System.out.println("Fetching Patients Appointments");

		try {
			
			String doctor_id = request.getParameter("doctor_id");
			System.out.println(doctor_id);
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, dbPassword);
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, doctor_id);

			ResultSet rs = st.executeQuery();
			
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				
				Map<String,String> map = new HashMap<>();
				map.put("patient", rs.getString("patient"));
				map.put("date", rs.getString("date"));
				map.put("time", rs.getString("time"));
				map.put("reason", rs.getString("reason"));

				list.add(map);
			}

			rs.close();
			
			String data = new Gson().toJson(list);
			
			json.put("status", "successful");
			json.put("data", data);
			
			out.print(json);
			out.flush();

		} catch (Exception e) {
			json.put("status", "failed");
			out.print(json);
			out.flush();
			e.printStackTrace();
		}
	}

}
