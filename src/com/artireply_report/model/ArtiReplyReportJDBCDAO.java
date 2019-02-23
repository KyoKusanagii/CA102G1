package com.artireply_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ArtiReplyReportJDBCDAO implements ArtiReplyReportDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "Instabuy";
	private static final String PASSWORD = "123456";
	
//	private static DataSource ds = null;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
//		} catch(NamingException e) {
//			e.printStackTrace();
//		}
//	}
	
	private static final String INSERT_STMT = 
	"INSERT INTO ARTIREPLY_REPORT(ARTIREPLY_REPORT_NO, MEM_NO, EMP_NO, ARTI_NO, REP_NO, REPORT_DESCRIPTION, REPORT_STATUS)" 
	+ "VALUES('R'||LPAD(to_char(artireply_report_seq.NEXTVAL), 5,'0'), ? , ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE ARTIREPLY_REPORT SET REPORT_STATUS = ? WHERE ARTIREPLY_REPORT_NO = ?";

	private static final String DELETE_STMT = "DELETE FROM ARTIREPLY_REPORT WHERE ARTIREPLY_REPORT_NO = ?";

	private static final String FIND_BY_PK = "SELECT * FROM ARTIREPLY_REPORT WHERE ARTIREPLY_REPORT_NO = ?";

	private static final String GET_ALL = "SELECT * FROM ARTIREPLY_REPORT ORDER BY ARTIREPLY_REPORT_NO";
//	private static final String GET_ALL =
//	"SELECT ARTIREPLY_REPORT_NO ," + 
//	"ARTIREPLY_REPORT.ARTI_NO," + 
//	"ARTIREPLY_REPORT.REP_NO ," + 
//	"REPORT_STATUS,n" + 
//	"REPORT_DESCRIPTION," + 
//	"REPORT_REASONS,"+
//	"ARTIREPLY_REPORT.MEM_NO" + 
//	"FROM ARTIREPLY_REPORT" + 
//	"JOIN ARTI_REPLY  ON  ARTI_REPLY.ARTI_NO = ARTIREPLY_REPORT.ARTI_NO AND ARTI_REPLY.REP_NO = ARTIREPLY_REPORT.REP_NO" + 
//	"JOIN MEMBER ON (MEMBER.MEM_NO = ARTIREPLY_REPORT.MEM_NO)" + 
//	"ORDER BY ARTIREPLY_REPORT_NO";
	

	@Override
	public void insert(ArtiReplyReportVO artiReplyReportVO) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			
			pstmt.setString(1, artiReplyReportVO.getMem_NO());
			pstmt.setString(2, artiReplyReportVO.getEmp_NO());
			pstmt.setString(3, artiReplyReportVO.getArti_NO());
			pstmt.setInt(4, artiReplyReportVO.getRep_NO());
			pstmt.setString(5, artiReplyReportVO.getReport_Description());
			pstmt.setInt(6, artiReplyReportVO.getReport_Status());
		
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public void update(String artiReply_report_no,int artiReply_report_status) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			//con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, artiReply_report_status);
			pstmt.setString(2, artiReply_report_no);
		
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public void delete(String artiReplyReportNO) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			//con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1,artiReplyReportNO);			
		
		
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	public ArtiReplyReportVO findByPrimaryKey(String artiReplyReportNO) {
		ArtiReplyReportVO arr = null;
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			//con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1,artiReplyReportNO);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				arr = new ArtiReplyReportVO();
				arr.setArtiReply_Report_NO(rs.getString("ARTIREPLY_REPORT_NO"));
				arr.setMem_NO(rs.getString("MEM_NO"));
				arr.setEmp_NO(rs.getString("EMP_NO"));
				arr.setArti_NO(rs.getString("ARTI_NO"));
				arr.setRep_NO(rs.getInt("REP_NO"));
				arr.setReport_Description(rs.getString("REPORT_DESCRIPTION"));
				arr.setReport_Status(rs.getInt("REPORT_STATUS"));				
			}			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return arr;
	}

	@Override
	public List<ArtiReplyReportVO> getAll() {
		List<ArtiReplyReportVO> artiReportList = new ArrayList<>();
		ArtiReplyReportVO arr = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			//con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				arr = new ArtiReplyReportVO();
				arr.setArtiReply_Report_NO(rs.getString("ARTIREPLY_REPORT_NO"));
				arr.setMem_NO(rs.getString("MEM_NO"));
				arr.setEmp_NO(rs.getString("EMP_NO"));
				arr.setArti_NO(rs.getString("ARTI_NO"));
				arr.setReport_Reasons(rs.getString("REPORT_REASONS"));
				arr.setRep_NO(rs.getInt("REP_NO"));
				arr.setReport_Description(rs.getString("REPORT_DESCRIPTION"));
				arr.setReport_Status(rs.getInt("REPORT_STATUS"));
				arr.setReport_Reasons(rs.getString("REPORT_REASONS"));
				artiReportList.add(arr);		
			}

		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		return artiReportList;
	}

	@Override
	public void delete(Integer artiReplyReportRepNo) {
		// TODO Auto-generated method stub
		
	}
	

}
