package com.item_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ItemReportDAO implements ItemReportDAO_interface{
	
	private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
			} catch(NamingException e) {
				e.printStackTrace();
			}
		}
	
	
	private static final String INSERT_STMT = "INSERT INTO ITEM_REPORT(ITEM_REPORT_NO,MEM_NO ,EMP_NO, ITEM_NO, REPORT_DESCRIPTION, REPORT_STATUS,REPORT_REASONS,REPORT_PIC)"+
									"VALUES(('R'||LPAD(to_char(item_report_seq.NEXTVAL), 5,'0')), ? , ?, ?, ?, ?, ?, ?)";
			
	private static final String UPDATE_STMT = "UPDATE ITEM_REPORT SET REPORT_STATUS = ? , EMP_NO = ? WHERE ITEM_REPORT_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM ITEM_REPORT WHERE ITEM_REPORT_NO = ?";
	
	private static final String FIND_BY_PK = "SELECT * FROM ITEM_REPORT WHERE ITEM_REPORT_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM ITEM_REPORT ORDER BY ITEM_REPORT_NO";
	private static final String GET_ALL_ITEM_NAME = 
	"SELECT ITEM_NAME FROM ITEM_REPORT JOIN ITEM ON ITEM_REPORT.ITEM_NO = ITEM.ITEM_NO ORDER BY ITEM_REPORT_NO";

	@Override
	public void insert(ItemReportVO itemReportVO) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {

				
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
					
			pstmt.setString(1, itemReportVO.getMem_NO());
			pstmt.setString(2, itemReportVO.getEmp_NO());
			pstmt.setString(3, itemReportVO.getItem_NO());
			pstmt.setString(4, itemReportVO.getReport_Description());
			pstmt.setInt(5, itemReportVO.getReport_Status());
			pstmt.setString(6, itemReportVO.getReport_reasons());
			if(itemReportVO.getReportPic() != null)
				pstmt.setBytes(7,itemReportVO.getReportPic());
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
	public void update(String itemReportNo,String itemReportHandler,int itemReportStatus) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
			
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, itemReportStatus);	
			pstmt.setString(2,itemReportHandler);
			pstmt.setString(3, itemReportNo);
			pstmt.executeUpdate();
			
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
		
	}

	@Override
	public void delete(String itemReportNO) {
		Connection con = null;
		PreparedStatement pstmt= null;
		
		try {
		
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1,itemReportNO);			
		
		
			pstmt.executeUpdate();
			
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}finally {
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
	public ItemReportVO findByPrimaryKey(String itemReportNO) {
		ItemReportVO ir = null;
		Connection con = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			
			pstmt.setString(1,itemReportNO);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ir = new ItemReportVO();
				ir.setItem_Report_NO(rs.getString("ITEM_REPORT_NO"));
				ir.setMem_NO(rs.getString("MEM_NO"));
				ir.setEmp_NO(rs.getString("EMP_NO"));
				ir.setItem_NO(rs.getString("ITEM_NO"));
				ir.setReport_Description(rs.getString("REPORT_DESCRIPTION"));
				ir.setReport_Status(rs.getInt("REPORT_STATUS"));				
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
		return ir;
	}

	@Override
	public List<ItemReportVO> getAll() {
		List<ItemReportVO> itemReportList = new ArrayList<>();
		ItemReportVO ir = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ir = new ItemReportVO();
				ir.setItem_Report_NO(rs.getString("ITEM_REPORT_NO"));
				ir.setMem_NO(rs.getString("MEM_NO"));
				ir.setEmp_NO(rs.getString("EMP_NO"));
				ir.setItem_NO(rs.getString("ITEM_NO"));
				ir.setReport_reasons(rs.getString("REPORT_REASONS"));
				ir.setReport_Description(rs.getString("REPORT_DESCRIPTION"));
				ir.setReport_Status(rs.getInt("REPORT_STATUS"));
				if(rs.getBytes("REPORT_PIC") != null) //如果檢舉的圖片欄位不為空值
					ir.setReportPic(rs.getBytes("REPORT_PIC"));
				itemReportList.add(ir);		
			}

		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}finally {
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
		return itemReportList;
	}

	@Override
	public List<String> getAllName() {	//得到所有被檢舉的商品名稱
		List<String> reportItemNameList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ITEM_NAME);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				reportItemNameList.add(rs.getString("ITEM_NAME"));
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
		return reportItemNameList;
	}

}
