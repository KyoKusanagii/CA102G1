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

public class ArtiReplyReportDAO implements ArtiReplyReportDAO_interface{

	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
	"INSERT INTO ARTIREPLY_REPORT(ARTIREPLY_REPORT_NO, MEM_NO, EMP_NO, ARTI_NO, REP_NO, REPORT_DESCRIPTION, REPORT_STATUS,REPORT_REASONS)" 
	+ "VALUES('R'||LPAD(to_char(artireply_report_seq.NEXTVAL), 5,'0'), ? , ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE ARTIREPLY_REPORT SET REPORT_STATUS = ? WHERE ARTIREPLY_REPORT_NO = ?";

	private static final String DELETE_STMT = "DELETE FROM ARTIREPLY_REPORT WHERE REP_NO = ?";
	
	private static final String DELETE_STMT_ALL_REP = "DELETE FROM ARTIREPLY_REPORT WHERE ARTI_NO = ?";

	private static final String FIND_BY_PK = "SELECT * FROM ARTIREPLY_REPORT WHERE ARTIREPLY_REPORT_NO = ?";

	private static final String GET_ALL = "SELECT * FROM ARTIREPLY_REPORT ORDER BY ARTIREPLY_REPORT_NO";

	

	@Override
	public void insert(ArtiReplyReportVO artiReplyReportVO) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, artiReplyReportVO.getMem_NO());
			pstmt.setString(2, artiReplyReportVO.getEmp_NO());
			pstmt.setString(3, artiReplyReportVO.getArti_NO());
			pstmt.setInt(4, artiReplyReportVO.getRep_NO());
			pstmt.setString(5, artiReplyReportVO.getReport_Description());
			pstmt.setInt(6, artiReplyReportVO.getReport_Status());
			pstmt.setString(7, artiReplyReportVO.getReport_Reasons());
		
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}  finally {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, artiReply_report_status);
			pstmt.setString(2, artiReply_report_no);
		
			pstmt.executeUpdate();
			
			
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
	public void delete(Integer artiReplyReportRepNo) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1,artiReplyReportRepNo);			
			pstmt.executeUpdate();
			
			
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
	public ArtiReplyReportVO findByPrimaryKey(String artiReplyReportNO) {
		ArtiReplyReportVO arr = null;
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
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

			con = ds.getConnection();
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
	public void delete(String arti_no) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT_ALL_REP);
			pstmt.setString(1,arti_no);			
			pstmt.executeUpdate();
			
			
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
	

}
