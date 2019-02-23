package com.follow_mem.model;

import java.sql.Connection;
import java.sql.Date;
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

import com.live.model.LiveVO;
import com.member.model.MemVO;



public class Follow_memDAO implements Follow_memDAO_interface{
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static final String INSERT_STMT = "INSERT INTO FOLLOW_MEM(FOLO_MEM_NO, FOLOED_MEM_NO, FOLO_TIME) VALUES(?, ?, ?)";
	public static final String DELETE_STMT = "DELETE FROM FOLLOW_MEM WHERE FOLO_MEM_NO = ? AND FOLOED_MEM_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM FOLLOW_MEM WHERE FOLO_MEM_NO = ? AND FOLOED_MEM_NO = ?";
	public static final String GET_ALL = "SELECT * FROM FOLLOW_MEM WHERE FOLO_MEM_NO= ? ORDER BY FOLOED_MEM_NO";
	
	
	public static final String GET_ALL_FANS = "SELECT * FROM FOLLOW_MEM WHERE FOLOED_MEM_NO= ? ORDER BY FOLOED_MEM_NO";
	
	public static final String GETFOLLOWADDRESS = "SELECT FOLOED_MEM_NO ,LIVE_ADDRESS, LIVE_STATUS FROM FOLLOW_MEM JOIN LIVE ON FOLOED_MEM_NO = LIVE_SELLER_NO WHERE FOLO_MEM_NO = ?";

	
	@Override
	public void insert(Follow_memVO follow_memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, follow_memVO.getFolo_mem_no());
			pstmt.setString(2, follow_memVO.getFoloed_mem_no());
			pstmt.setDate(3, follow_memVO.getFolo_time());
			pstmt.executeUpdate();
			
		
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, folo_mem_no);
			pstmt.setString(2, foloed_mem_no);
			pstmt.executeUpdate();
			
			
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FANS);
			pstmt.setString(1, foloed_mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("FOLO_MEM_NO"));
				list.add(memVO);
			}
			
			
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
	
	
	
}
