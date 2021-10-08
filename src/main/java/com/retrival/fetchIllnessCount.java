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
 * Servlet implementation class fetchIllnessCount
 * http://localhost:8080/project/fetchIllnessCount
 */
@WebServlet("/fetchIllnessCount")
public class fetchIllnessCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fetchIllnessCount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = "jdbc:mysql://localhost:3306/portal_db";
		String user = "root";
		String dbPassword = "1joshua1";
		
		String query = "select diagnosis, count(*) as count from checkup group by diagnosis;";
			
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
//		List<Map<String, String>> list = new ArrayList<>();
		
		System.out.println("Fetching Patients Appointments");

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, dbPassword);
			PreparedStatement st = con.prepareStatement(query);
			

			ResultSet rs = st.executeQuery();
			
			List<String> illnessNames = new ArrayList<String>();
			List<Integer> illnessCount = new ArrayList<Integer>();
			
			// Extract data from result set
			while (rs.next()) {
				// Retrieve by column naame

				illnessNames.add(rs.getString("diagnosis"));
				illnessCount.add(rs.getInt("count"));
			}

			rs.close();
			
			JSONObject result = new JSONObject();
			result.put("illness", illnessNames);
			result.put("count", illnessCount);
			
//			String data = new Gson().toJson(list);
			
			json.put("status", "successful");
			json.put("data", result);
			
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
