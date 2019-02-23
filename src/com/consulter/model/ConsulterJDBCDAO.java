package com.consulter.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsulterJDBCDAO implements ConsulterDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "Instabuy";	//此為登入的資料庫名稱，非資料庫帳號
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = 			
	"INSERT INTO CONSULTER(CONSULTER_NO,CONSUTLER_NAME,CONSULTER_ICON) VALUES(?, ?, ?)";
	
	private static final String UPDATE_STMT = 
	"UPDATE EMPLOYEE SET EMP_ID = ?,EMP_NAME = ?,EMP_STATUS = ?,EMP_ICON = ?,EMP_MEM_AUTH = ?,"
	+ "EMP_CAROUSEL_AUTH = ?,EMP_REPORT_AUTH = ?,EMP_CHAT_AUTH = ?,EMP_LEVEL = ? WHERE EMP_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM  CONSULTER WHERE CONSULTER_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM CONSULTER WHERE CONSULTER_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM CONSULTER ORDER BY CONSULTER_NO";

	
	@Override 
	public void insert(ConsulterVO consulter) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
				
			pstmt.setString(1, consulter.getConsulter_no()); 
			pstmt.setString(2, consulter.getConsulter_name());
			pstmt.setBytes(3, consulter.getConsulter_icon());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(ConsulterVO emp){
		
		
	}

	@Override
	public void delete(String consulter_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, consulter_no);	
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public ConsulterVO findByPK(String consulter_no) {
		ConsulterVO consulter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, consulter_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				consulter = new ConsulterVO();
				consulter.setConsulter_no(rs.getString("CONSULTER_NO"));
				consulter.setConsulter_name(rs.getString("CONSULTER_NAME"));
				consulter.setConsulter_icon(rs.getBytes("CONSULTER_ICON"));	
			}
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return consulter;
	}

	@Override
	public List<ConsulterVO> getAll() {
		List<ConsulterVO> consulterList = new ArrayList<>();
		ConsulterVO consulter = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				consulter = new ConsulterVO();
				consulter.setConsulter_no(rs.getString("CONSULTER_NO"));
				consulter.setConsulter_name(rs.getString("CONSULTER_NAME"));
				consulter.setConsulter_icon(rs.getBytes("CONSULTER_ICON"));						
				consulterList.add(consulter);
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return consulterList;
	}

}
