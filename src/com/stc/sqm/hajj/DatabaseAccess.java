package com.stc.sqm.hajj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

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
				toDo.setAssignedEmail(rs.getString("ASSIGNED_EMAIL"));
				toDo.setAssignedMobile(rs.getString("ASSIGNED_MOBILE"));
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
					e.printStackTrace();
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
					e.printStackTrace();;
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();;
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
					e.printStackTrace();
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return rs;
	}
	
	public int addNewToDo(ToDo toDo) {

		Connection conn = null;
		Statement stmt = null;
		int rs = 0;
		
//		System.out.println("Add ToDo (Task ID: " + toDo.getTaskId() + ", Create UID: " + toDo.getCreatorUid());

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO HAJJ_TODO_LIST "
					+ "(CREATOR_UID, ASSIGNED_UID, ASSIGNED_SHIFT, ASSIGNED_EMAIL, ASSIGNED_MOBILE, TASK_DESC, STATUS, PRIORITY) "
					+ "VALUES "
					+ "('" + toDo.getCreatorUid() + "', '" + toDo.getAssignedUid() + "', '" + toDo.getAssignedShift() +
							"', '" + toDo.getAssignedEmail() + "', '" + toDo.getAssignedMobile() + "', '" + toDo.getTaskDesc() + "', 'PENDING', " + toDo.getPriority() + ")";
			
			System.out.println("Add ToDo --> SQL: " + sql);
			
			rs = stmt.executeUpdate(sql);
		
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
					e.printStackTrace();
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return rs;
	}
	
	
	public HashMap<String, Utilization> getUtilAll() {

		HashMap<String, Utilization> utilHash = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		System.out.println("getUtilAll(...) --> BEGIN");

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();
			
			String sql = "SELECT KPI_DATE, CUSTOMER, CIRCUIT, AVAILABILITY, "
					+ "CONFIGURED_BANDWIDTH, AVG_UTIL_IN, AVG_UTIL_OUT, "
					+ "MAX_UTIL_IN, MAX_UTIL_IN_DATE, MAX_UTIL_OUT, MAX_UTIL_OUT_DATE, "
					+ "AVG_ERROR_IN, AVG_ERROR_OUT, HAJJ, VIP, IS_HAJJ_VIP FROM "
					+ "MV_DSH_HAJJ_VIP_IV_UTIL_2017"; 
					
			rs = stmt.executeQuery(sql);

			utilHash = new HashMap<String, Utilization>();

			while (rs.next()) {
				Utilization util = new Utilization();
				util.setKpiDate(rs.getString("KPI_DATE"));
				util.setCustomer(rs.getString("CUSTOMER"));
				util.setCircuit(rs.getString("CIRCUIT"));
				util.setAvailability(rs.getString("AVAILABILITY"));
				util.setConfiguredBandwidth(rs.getString("CONFIGURED_BANDWIDTH"));
				util.setAvgUtilIn(rs.getString("AVG_UTIL_IN"));
				util.setAvgUtilOut(rs.getString("AVG_UTIL_OUT"));
				util.setMaxUtilIn(rs.getString("MAX_UTIL_IN"));
				util.setMaxUtilInDate(rs.getString("MAX_UTIL_IN_DATE"));
				util.setMaxUtilOut(rs.getString("MAX_UTIL_OUT"));
				util.setMaxUtilOutDate(rs.getString("MAX_UTIL_OUT_DATE"));
				util.setAvgErrorIn(rs.getString("AVG_ERROR_IN"));
				util.setAvgErrorOut(rs.getString("AVG_ERROR_OUT"));
				util.setHajj(rs.getString("HAJJ"));
				util.setVip(rs.getString("VIP"));
				util.setIsHajjVip(rs.getString("IS_HAJJ_VIP"));
				
				utilHash.put(util.getCircuit(), util);
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
					e.printStackTrace();
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return utilHash;

	}
	
	
	public ArrayList<UtilReadings> getUtilReadingsAll() {

		ArrayList<UtilReadings> utilReadingsList = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//System.out.println("getUtilReadingsAll(...) --> BEGIN");

		try {

			conn = getDBConnection();
			stmt = conn.createStatement();
			
			String sql = "SELECT CIRCUIT, SUBSTR(UTIL_DATE, -8, 5) UTIL_DATE, UTIL_IN, UTIL_OUT "
					+ "FROM MV_DSH_HAJJ_VIP_LATEST_5_2017 WHERE (UTIL_IN IS NOT NULL AND UTIL_OUT IS NOT NULL) "
					+ "ORDER BY CIRCUIT, UTIL_DATE";
					
			rs = stmt.executeQuery(sql);

			utilReadingsList = new ArrayList<UtilReadings>();

			while (rs.next()) {
				UtilReadings utilR = new UtilReadings();
				
				utilR.setUtilDate(rs.getString("UTIL_DATE"));
				utilR.setCircuit(rs.getString("CIRCUIT"));
				utilR.setUtilIn(rs.getString("UTIL_IN"));
				utilR.setUtilOut(rs.getString("UTIL_OUT"));
				
				utilReadingsList.add(utilR);
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
					e.printStackTrace();
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				conn = null;
			}
		}

		return utilReadingsList;

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
