package org.mahi.loginvalidationApp;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(urlPatterns = "/mahi", loadOnStartup = 1)
public class FirstServlet extends HttpServlet{
	
	@Override
	public void init() throws ServletException {
		System.out.println("Servlet Object is Created");
	}
	
	//SERVLETS
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String urnm = req.getParameter("un");
		String pwd = req.getParameter("pass");


		PrintWriter out = resp.getWriter();


		//JDBC
		Connection con = null;
		PreparedStatement pstmt= null;
		String qry = "select * from mahi.stu_details where username = ? and password = ?";
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Class Load and Registered");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=tigerdb");
			System.out.println("Connection Established");

			pstmt = con.prepareStatement(qry);
			System.out.println("Platform Created");

			//set the value/data for placeholder before execution
			pstmt.setString(1, urnm);
			pstmt.setString(2, pwd);

			rs=pstmt.executeQuery();

			if(rs.next()) {
				String name = rs.getString(3);
				int age = rs.getInt(4);
				double perc = rs.getDouble(5);
				String place =  rs.getString(6);

				out.println("<html><body bgcolor='gray'>"
						+ "<h1>The Student details are: <br>Name is "+name+", Age is "+age+", Percentage is "+perc+" and Place is "+place+".</h1>"
						+ "</body></html>");
			}
			else {
				out.println("<html><body bgcolor='yellow'>"
						+ "<h1>Sorry, "+urnm+" and "+pwd+" doesn't match!!");
			}
			out.close();
			System.out.println("service method is executed");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	@Override
	public void destroy() {
		System.out.println("Close all costly Resources");
	}

}
