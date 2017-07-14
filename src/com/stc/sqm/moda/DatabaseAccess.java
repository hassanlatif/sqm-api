package com.stc.sqm.moda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseAccess {

	public List<String> getUserRole(String userName) {
		
		List<String> userRoles = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null; // Or PreparedStatement if needed
		ResultSet rs = null;

		try {

			conn = getDBConnection(); 
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT ROLE FROM MOD_USERS_ROLES WHERE USERNAME='" + userName + "'");

			while (rs.next()) {
				userRoles.add(rs.getString("ROLE"));
			}

			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			conn.close(); // Return to connection pool
			conn = null; // Make sure we don't close it twice

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					;
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					;
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					;
				}
				conn = null;
			}
		}

		return userRoles;

	}

	public Connection getDBConnection() throws SQLException {

		Context initContext = null;
		Context envContext = null;
		DataSource dataSource = null;
		Connection connection = null;

		try {
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/sadb");
			connection = dataSource.getConnection();

			if (connection != null) {
				System.out.println("Connected to database!");
			}

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;

	}

}
