package com.particulars.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.category.model.CategoryJDBCDAO;
import com.category.model.CategoryVO;

public class ParticularsJDBCDAO implements ParticularsDAO_interface{

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
	"INSERT INTO PARTICULARS(CAT_NO, PART_NO, PART_NAME) VALUES(?, particulars_seq.NEXTVAL, ?)";
	
	private static final String UPDATE_STMT = 
	"UPDATE PARTICULARS SET CAT_NO = ?, PART_NAME = ? WHERE PART_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM PARTICULARS WHERE PART_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM PARTICULARS WHERE CAT_NO = ? AND PART_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM PARTICULARS ORDER BY PART_NO";

	private static final String GET_ALL_BY_CAG_NO = "SELECT * FROM PARTICULARS Where CAT_NO = ? ORDER BY CAT_NO";
	@Override
	public void insert(ParticularsVO particulars) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, particulars.getCat_no());
			pstmt.setString(2, particulars.getPart_name());
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

	@Override
	public void update(ParticularsVO particulars) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, particulars.getCat_no());
			pstmt.setString(2, particulars.getPart_name());
			pstmt.setInt(3, particulars.getPart_no());
			
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

	@Override
	public void delete(Integer part_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, part_no);	
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

	@Override
	public ParticularsVO findByPK(Integer cat_no, Integer part_no) {
		// TODO Auto-generated method stub
		ParticularsVO particulars = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, cat_no);
			pstmt.setInt(2, part_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				particulars = new ParticularsVO();
				particulars.setCat_no(rs.getInt("CAT_NO"));
				particulars.setPart_no(rs.getInt("PART_NO"));
				particulars.setPart_name(rs.getString("PART_NAME"));		
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
		return particulars;
	}

	@Override
	public List<ParticularsVO> getAll() {
		// TODO Auto-generated method stub
		List<ParticularsVO> particularsList = new ArrayList<>();
		ParticularsVO particulars = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				particulars = new ParticularsVO();
				particulars.setCat_no(rs.getInt("CAT_NO"));
				particulars.setPart_no(rs.getInt("PART_NO"));
				particulars.setPart_name(rs.getString("PART_NAME"));						
				particularsList.add(particulars);
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
		return particularsList;
	}
	
	@Override
	public Map<Integer, String> getNameByNO() {
		// TODO Auto-generated method stub
		Map<Integer, String> getNameByNO = new HashMap<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				getNameByNO.put(rs.getInt("PART_NO"), rs.getString("PART_NAME"));
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
		return getNameByNO;
	}
	
	
	public static void main(String[] args) {
		ParticularsJDBCDAO dao = new ParticularsJDBCDAO();
		
		// 新增
//		ParticularsVO particularsVO1 = new ParticularsVO();
//		particularsVO1.setCat_no(3);
//		particularsVO1.setPart_name("測試");
//		dao.insert(particularsVO1);
//		System.out.println("insert success");
		
		
		// 修改
//		ParticularsVO particularsVO2 = new ParticularsVO();
//		particularsVO2.setCat_no(3);
//		particularsVO2.setPart_name("修改");
//		particularsVO2.setPart_no(56);
//		dao.update(particularsVO2);
//		System.out.println("update success from " + particularsVO2.getPart_no());

		
		// 查詢
//		ParticularsVO particularsVO = dao.findByPK(3,56);
//		System.out.print(particularsVO.getCat_no() + ",");
//		System.out.print(particularsVO.getPart_no() + ",");
//		System.out.print(particularsVO.getPart_name() + ",");
//		System.out.println("---------------------");

		
		// 查詢
//		List<ParticularsVO> list = dao.getAll(); 
//		for(ParticularsVO aParticulars : list) {		
//			System.out.print(aParticulars.getCat_no() + ",");
//			System.out.print(aParticulars.getPart_no() + ",");
//			System.out.print(aParticulars.getPart_name() + ",");
//			System.out.println("---------------------");
//		}
		
		
		//刪除
		dao.delete(56);
		System.out.println("delete success");
		
	}

	@Override
	public List<ParticularsVO> getByCagNoAll(Integer cat_no) {
		List<ParticularsVO> particularsList = new ArrayList<>();
		ParticularsVO particulars = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setInt(1, cat_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				particulars = new ParticularsVO();
				particulars.setCat_no(rs.getInt("CAT_NO"));
				particulars.setPart_no(rs.getInt("PART_NO"));
				particulars.setPart_name(rs.getString("PART_NAME"));						
				particularsList.add(particulars);
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
		return particularsList;
	}
	
	

}
