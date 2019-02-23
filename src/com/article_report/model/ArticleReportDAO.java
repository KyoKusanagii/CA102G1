package com.article_report.model;

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

public class ArticleReportDAO implements Article_reportDAO_interface {
	
	 private static DataSource ds = null;
	 static {
	  try {
		  Context ctx = new InitialContext();
		  ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
	  	} catch (NamingException e) {
		  e.printStackTrace();
	  	}
	 }


	private static final String INSERT_STMT = 
	"INSERT INTO ARTICLE_REPORT(ARTICLE_REPORT_NO,MEM_NO,ARTI_NO,REPORT_DESCRIPTION,REPORT_STATUS,REPORT_REASONS)" + "VALUES(('R'||LPAD(to_char(article_report_seq.NEXTVAL), 5,'0')), ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT = 
	"UPDATE ARTICLE_REPORT SET REPORT_STATUS = ? WHERE ARTICLE_REPORT_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM ARTICLE_REPORT WHERE ARTI_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM ARTICLE_REPORT WHERE ARTICLE_REPORT_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM ARTICLE_REPORT ORDER BY ARTICLE_REPORT_NO";

	
	@Override 
	public void insert(ArticleReportVO article_report) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
				
			//pstmt.setString(1, article_report.getArticle_report_no());
			pstmt.setString(1, article_report.getMem_no());
			pstmt.setString(2, article_report.getArti_no());
			pstmt.setString(3, article_report.getReport_description());
			pstmt.setInt(4, article_report.getReport_status());
			pstmt.setString(5, article_report.getReport_reasons());
			pstmt.executeQuery();

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public void update(String article_report_no,int article_report_status){	//更新只要更新審核狀態就好
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, article_report_status);	//要更新的狀態
			pstmt.setString(2, article_report_no);	//要更新的編號
			pstmt.executeUpdate();	
			// Handle any driver errors
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

//	@Override
//	public void delete(String article_report_no) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			Class.forName(DRIVER);
//			con = DriverManager.getConnection(URL, USER, PASSWORD);
//			pstmt = con.prepareStatement(DELETE_STMT);
//			pstmt.setInt(1, news_no);	
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException ce) {
//			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}

	@Override
	public ArticleReportVO findByPK(String article_report_no) {	
		ArticleReportVO articleReport = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, article_report_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				articleReport = new ArticleReportVO();
				articleReport.setArticle_report_no(rs.getString("ARTICLE_REPORT_NO"));
				articleReport.setEmp_no(rs.getString("MEM_NO"));
				articleReport.setArti_no(rs.getString("ARTI_NO"));
				articleReport.setReport_description("REPORT_DECRIPTION");
				articleReport.setReport_status(rs.getInt("REPORT_STATUS"));
			}
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
		return articleReport;
	}

	@Override
	public List<ArticleReportVO> getAll() {
		List<ArticleReportVO> articleReportList = new ArrayList<>();
		ArticleReportVO articleReport = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				articleReport = new ArticleReportVO();
				articleReport.setArticle_report_no(rs.getString("ARTICLE_REPORT_NO"));
				articleReport.setMem_no(rs.getString("MEM_NO"));
				articleReport.setArti_no(rs.getString("ARTI_NO"));						
				articleReport.setReport_description(rs.getString("REPORT_DESCRIPTION"));
				articleReport.setReport_status(rs.getInt("REPORT_STATUS"));
				articleReport.setReport_reasons(rs.getString("REPORT_REASONS"));
				articleReportList.add(articleReport);
			}

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
		return articleReportList;
	}

	@Override
	public void delete(String arti_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
	

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, arti_no);
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
