package com.follow_item.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.follow_mem.model.Follow_memVO;

public class Follow_itemJDBCDAO implements Follow_itemDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "instabuy";
	private static final String PASSWORD = "123456";
	
	public static final String INSERT_STMT = "INSERT INTO FOLLOW_ITEM(MEM_NO, ITEM_NO, FOLO_TIME) VALUES(?, ?, ?)";
	public static final String DELETE_STMT = "DELETE FROM FOLLOW_ITEM WHERE MEM_NO = ? AND ITEM_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM FOLLOW_ITEM WHERE MEM_NO = ? AND ITEM_NO = ?";
	public static final String GET_ALL = "SELECT * FROM FOLLOW_ITEM WHERE MEM_NO = ? ORDER BY FOLO_TIME";
	
	@Override
	public void insert(Follow_itemVO follow_itemVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, follow_itemVO.getMem_no());
			pstmt.setString(2, follow_itemVO.getItem_no());
			pstmt.setDate(3, follow_itemVO.getFolo_time());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		
		
	}
	@Override
	public void delete(String mem_no, String item_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, item_no);
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public Follow_itemVO findByPrimaryKey(String mem_no, String item_no) {
		Follow_itemVO follow_itemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, mem_no);
			pstmt.setString(2, item_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				follow_itemVO = new Follow_itemVO();
				follow_itemVO.setMem_no(rs.getString("mem_no"));
				follow_itemVO.setItem_no(rs.getString("item_no"));
				follow_itemVO.setFolo_time(rs.getDate("folo_time"));
			}
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return follow_itemVO;
	}
	
	@Override
	public List<Follow_itemVO> getAll(String mem_no) {
		List<Follow_itemVO> list = new ArrayList<Follow_itemVO>();
		Follow_itemVO follow_itemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				follow_itemVO = new Follow_itemVO();
				follow_itemVO.setMem_no(rs.getString("mem_no"));
				follow_itemVO.setItem_no(rs.getString("item_no"));		
				follow_itemVO.setFolo_time(rs.getDate("folo_time"));
				list.add(follow_itemVO);
			}
			
			// Handle any driver errors
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
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		
		Follow_itemJDBCDAO dao = new Follow_itemJDBCDAO();
		
//			insert()
//		Follow_itemVO follow_itemVO = new Follow_itemVO();
//		follow_itemVO.setMem_no("M00003");
//		follow_itemVO.setItem_no("I00005");
//		follow_itemVO.setFolo_time(Date.valueOf("2018-7-15"));
//		dao.insert(follow_itemVO);
		
		
//			delete()
//		dao.delete("M00003", "I00005");
		
			
//			findByPrimaryKey()
//		Follow_itemVO follow_itemVO = dao.findByPrimaryKey("M00001", "I00001");
//		System.out.println("mem_no : " + follow_itemVO.getMem_no());
//		System.out.println("item_no : " + follow_itemVO.getItem_no());
//		System.out.println("folo_time : " + follow_itemVO.getFolo_time());
		
		
//			getAll()
		List<Follow_itemVO> list = dao.getAll("M00001");
		for(Follow_itemVO follow_itemVO : list) {
			System.out.println("mem_no : " + follow_itemVO.getMem_no());
			System.out.println("ITEM_no : " + follow_itemVO.getItem_no());
			System.out.println("folo_time : " + follow_itemVO.getFolo_time());
			System.out.println("========================");
		}
		
		
		System.out.println("ok");
		
	}

}
