package com.category.model;

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


public class CategoryDAO implements CategoryDAO_interface {

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
	"INSERT INTO CATEGORY(CAT_NO, CAT_NAME) VALUES(category_seq.NEXTVAL, ?)";
	
	private static final String UPDATE_STMT = 
	"UPDATE CATEGORY SET CAT_NAME = ? WHERE CAT_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM CATEGORY WHERE CAT_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM CATEGORY WHERE CAT_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM CATEGORY ORDER BY CAT_NO";
	
	@Override 
	public void insert(CategoryVO category) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, category.getCat_name());
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
	public void update(CategoryVO category){
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, category.getCat_name());
			pstmt.setInt(2, category.getCat_no());
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
	public void delete(Integer cat_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, cat_no);	
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
	public CategoryVO findByPK(Integer cat_no) {
		CategoryVO category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, cat_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				category = new CategoryVO();
				category.setCat_no(rs.getInt("CAT_NO"));
				category.setCat_name(rs.getString("CAT_NAME"));		
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
		return category;
	}

	@Override
	public List<CategoryVO> getAll() {
		List<CategoryVO> categoryList = new ArrayList<>();
		CategoryVO category = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				category = new CategoryVO();
				category.setCat_no(rs.getInt("CAT_NO"));
				category.setCat_name(rs.getString("CAT_NAME"));							
				categoryList.add(category);
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
		return categoryList;
	}
	
	@Override
	public Map<Integer, String> getNameByNO() {
		Map<Integer, String> getNameByNO = new HashMap<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				getNameByNO.put(rs.getInt("CAT_NO"), rs.getString("CAT_NAME"));				
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
		CategoryJDBCDAO dao = new CategoryJDBCDAO();
		
		// 新增
		CategoryVO categoryVO1 = new CategoryVO();
		categoryVO1.setCat_name("測試");
		dao.insert(categoryVO1);
		System.out.println("insert success");
		
		
		// 修改
//		CategoryVO categoryVO2 = new CategoryVO();
//		categoryVO2.setCat_name("修改");
//		categoryVO2.setCat_no(7);
//		dao.update(categoryVO2);
//		System.out.println("update success from " + categoryVO2.getCat_no());

		
		// 查詢
//		CategoryVO categoryVO = dao.findByPK(7);
//		System.out.print(categoryVO.getCat_no() + ",");
//		System.out.print(categoryVO.getCat_name() + ",");
//		System.out.println("---------------------");

		
		// 查詢
//		List<CategoryVO> list = dao.getAll(); 
//		for(CategoryVO aCategory : list) {		
//			System.out.print(aCategory.getCat_no() + ",");
//			System.out.print(aCategory.getCat_name() + ",");
//			System.out.println("---------------------");
//		}
		
		
		//刪除
//		dao.delete(7);
//		System.out.println("delete success");
		
	}

}
