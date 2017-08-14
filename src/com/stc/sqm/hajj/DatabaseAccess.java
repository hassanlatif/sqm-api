package com.stc.sqm.hajj;

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

	public ArrayList<ToDo> getToDoListByCreator(String creatorUid) {

		ArrayList<ToDo> toDoList = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		System.out.println("getToDoListByCreator(...) --> Creator ID = " + creatorUid);

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT TASK_ID, CREATOR_UID, CREATOR_EMAIL, CREATOR_MOBILE, " + "ASSIGNED_UID, ASSIGNED_SHIFT, TASK_DESC,"
							+ " STATUS, NOTIFY_ID, CREATE_DATE, TARGET_DATE, PRIORITY, "
							+ "ASSIGNED_EMAIL, ASSIGNED_MOBILE FROM HAJJ_TODO_LIST " 
							+ "WHERE CREATOR_UID LIKE '" + creatorUid + "'");

			toDoList = new ArrayList<ToDo>();

			while (rs.next()) {
				ToDo toDo = new ToDo();
				toDo.setTaskId(rs.getInt("TASK_ID"));
				toDo.setCreatorUid(rs.getString("CREATOR_UID"));
				toDo.setCreatorEmail(rs.getString("CREATOR_EMAIL"));
				toDo.setCreatorMobile(rs.getString("CREATOR_MOBILE"));
				toDo.setAssignedUid(rs.getString("ASSIGNED_UID"));
				toDo.setAssignedShift(rs.getString("ASSIGNED_SHIFT"));
				toDo.setTaskDesc(rs.getString("TASK_DESC"));
				toDo.setStatus(rs.getString("STATUS"));
				toDo.setCreateDate(rs.getString("CREATE_DATE"));
				toDo.setTargetDate(rs.getString("TARGET_DATE"));
				toDo.setPriority(rs.getInt("PRIORITY"));

				toDoList.add(toDo);
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

		return toDoList;

	}
	
	public ArrayList<ToDo> getToDoListAll() {

		ArrayList<ToDo> toDoList = null;
		
		toDoList = getToDoListByCreator("%");

		return toDoList;

	}

	public int setToDoTaskAsCleared(int taskId) {

		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		System.out.println("Task ID to be CLEARED is " + taskId);

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();

			rs = stmt.executeUpdate("UPDATE HAJJ_TODO_LIST SET STATUS = 'CLEARED' WHERE TASK_ID = " + taskId);
			
			stmt.close();
			stmt = null;
			conn.close(); // Return to connection pool
			conn = null; // Make sure we don't close it twice

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
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

		return rs;

	}
	
	public int setToDoStatus(int taskId, String status) {

		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
		System.out.println("Set Status (Task ID: " + taskId + ", New Status: " + status);

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();

			rs = stmt.executeUpdate("UPDATE HAJJ_TODO_LIST SET STATUS = '" + status + "' WHERE TASK_ID = " + taskId);
			
			stmt.close();
			stmt = null;
			conn.close(); // Return to connection pool
			conn = null; // Make sure we don't close it twice

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
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

		return rs;
	}
	
	public boolean addNewToDo(ToDo toDo) {

		Connection conn = null;
		Statement stmt = null;
		boolean rs = false;
		
		System.out.println("Add ToDo (Task ID: " + toDo.getTaskId() + ", Create UID: " + toDo.getCreatorUid());

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO HAJJ_TODO_LIST "
					+ "(TASK_ID, CREATOR_UID, ASSIGNED_UID, ASSIGNED_SHIFT, ASSIGNED_EMAIL, ASSIGNED_MOBILE, TASK_DESC, STATUS, PRIORITY) "
					+ "VALUES "
					+ "(" + toDo.getTaskId() + ", '" + toDo.getCreatorUid() + "', '" + toDo.getAssignedUid() + "', '" + toDo.getAssignedShift() +
							"', '" + toDo.getAssignedEmail() + "', '" + toDo.getAssignedMobile() + "', '" + toDo.getTaskDesc() + "', 'PENDING', " + toDo.getPriority() + ")";
			
			System.out.println("Add ToDo --> SQL: " + sql);
			
			rs = stmt.execute(sql);
		
			stmt.close();
			stmt = null;
			conn.close(); // Return to connection pool
			conn = null; // Make sure we don't close it twice

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Always make sure result sets and statements are closed,
			// and the connection is returned to the pool
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

		return rs;
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
