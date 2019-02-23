package com.follow_mem.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.live.model.LiveVO;
import com.member.model.MemVO;



public class Follow_memJDBCDAO implements Follow_memDAO_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "instabuy";
	private static final String PASSWORD = "123456";
	
	public static final String INSERT_STMT = "INSERT INTO FOLLOW_MEM(FOLO_MEM_NO, FOLOED_MEM_NO, FOLO_TIME) VALUES(?, ?, ?)";
	public static final String DELETE_STMT = "DELETE FROM FOLLOW_MEM WHERE FOLO_MEM_NO = ? AND FOLOED_MEM_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM FOLLOW_MEM WHERE FOLO_MEM_NO = ? AND FOLOED_MEM_NO = ?";
	public static final String GET_ALL = "SELECT * FROM FOLLOW_MEM WHERE FOLO_MEM_NO= ? ORDER BY FOLO_TIME";
	
	public static final String GET_ALL_FANS = "SELECT * FROM FOLLOW_MEM WHERE FOLOED_MEM_NO= ? ORDER BY FOLOED_MEM_NO";

	public static final String GETFOLLOWADDRESS = "SELECT FOLOED_MEM_NO ,LIVE_ADDRESS, LIVE_STATUS FROM FOLLOW_MEM JOIN LIVE ON FOLOED_MEM_NO = LIVE_SELLER_NO WHERE FOLO_MEM_NO = ?";

	@Override
	public void insert(Follow_memVO follow_memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, follow_memVO.getFolo_mem_no());
			pstmt.setString(2, follow_memVO.getFoloed_mem_no());
			pstmt.setDate(3, follow_memVO.getFolo_time());
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
	public void delete(String folo_mem_no, String foloed_mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, folo_mem_no);
			pstmt.setString(2, foloed_mem_no);
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
	public Follow_memVO findByPrimaryKey(String folo_mem_no, String foloed_mem_no) {
		Follow_memVO follow_memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, folo_mem_no);
			pstmt.setString(2, foloed_mem_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				follow_memVO = new Follow_memVO();
				follow_memVO.setFolo_mem_no(rs.getString("folo_mem_no"));
				follow_memVO.setFoloed_mem_no(rs.getString("foloed_mem_no"));
				follow_memVO.setFolo_time(rs.getDate("folo_time"));
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
		
		return follow_memVO;
	}
	
	@Override
	public List<Follow_memVO> getAll(String folo_mem_no) {
		List<Follow_memVO> list = new ArrayList<Follow_memVO>();
		Follow_memVO follow_memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setString(1, folo_mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				follow_memVO = new Follow_memVO();
				follow_memVO.setFolo_mem_no(rs.getString("folo_mem_no"));
				follow_memVO.setFoloed_mem_no(rs.getString("foloed_mem_no"));		
				follow_memVO.setFolo_time(rs.getDate("folo_time"));
				list.add(follow_memVO);
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
	
	
	@Override
	public List<LiveVO> getFollowAddress(String FOLLOW_MEM_NO) {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETFOLLOWADDRESS);
			pstmt.setString(1, FOLLOW_MEM_NO);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				liveVO = new LiveVO();
				liveVO.setLive_seller_no(rs.getString("FOLOED_MEM_NO"));
				liveVO.setLive_address(rs.getString("LIVE_ADDRESS"));
				liveVO.setLive_status(Integer.parseInt(rs.getString("LIVE_STATUS")));
				list.add(liveVO);
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
	
	
	@Override
	public List<MemVO> getAllFans(String foloed_mem_no) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_FANS);
			pstmt.setString(1, foloed_mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("FOLO_MEM_NO"));
				list.add(memVO);
			}
			
			
			// Handle any SQL errors
		} catch (SQLException | ClassNotFoundException se) {
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
		
		Follow_memJDBCDAO dao = new Follow_memJDBCDAO();
		
//			insert()
//		Follow_memVO follow_memVO = new Follow_memVO();
//		follow_memVO.setFolo_mem_no("M00006");
//		follow_memVO.setFoloed_mem_no("M00009");
//		follow_memVO.setFolo_time(Date.valueOf("2018-7-15"));
//		dao.insert(follow_memVO);

		
//			delete()
//		dao.delete("M00006", "M00009");
			
		
//			findByPrimaryKey()
//		Follow_memVO follow_memVO = dao.findByPrimaryKey("M00002", "M00003");
//		System.out.println("folo_mem_no : " + follow_memVO.getFolo_mem_no());
//		System.out.println("foloed_mem_no : " + follow_memVO.getFoloed_mem_no());
//		System.out.println("folo_time : " + follow_memVO.getFolo_time());
		
			
//			getAll()
		List<Follow_memVO> list = dao.getAll("M00001");
		for(Follow_memVO follow_memVO : list) {
			System.out.println("folo_mem_no : " + follow_memVO.getFolo_mem_no());
			System.out.println("foloed_mem_no : " + follow_memVO.getFoloed_mem_no());
			System.out.println("folo_time : " + follow_memVO.getFolo_time());
			System.out.println("========================");
		}
		
		System.out.println("ok");
	}
	
}
