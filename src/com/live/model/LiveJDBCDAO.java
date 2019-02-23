package com.live.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class LiveJDBCDAO implements LiveDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO LIVE (LIVE_NO,LIVE_SELLER_NO,LIVE_ADDRESS,LIVE_STATUS,LIVE_START_TIME,LIVE_END_TIME) VALUES "
			+ "((to_char(sysdate,'yyyymmdd')||'-L'||LPAD(to_char(live_seq.NEXTVAL), 5, '0')),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT LIVE_NO,LIVE_SELLER_NO,LIVE_ADDRESS,LIVE_STATUS, LIVE_START_TIME, LIVE_END_TIME FROM LIVE ORDER BY LIVE_NO";
	private static final String GET_ONE_STMT = "SELECT LIVE_NO,LIVE_SELLER_NO,LIVE_ADDRESS,LIVE_STATUS, LIVE_START_TIME, LIVE_END_TIME FROM LIVE WHERE LIVE_NO = ?";
	
	//2018/8/2 Max新增，找出那個人所有播過的影片
	private static final String GET_ALLLIVES_BY_SELLER = "SELECT * FROM LIVE WHERE LIVE_SELLER_NO = ? ORDER BY LIVE_NO";
	private static final String DELETE = "DELETE FROM LIVE WHERE LIVE_NO = ?";
	private static final String UPDATE = "UPDATE LIVE SET LIVE_SELLER_NO=?, LIVE_ADDRESS=?, LIVE_STATUS=?, LIVE_START_TIME=?, LIVE_END_TIME=? WHERE LIVE_NO = ?";
	
	// 8/4 Hugh新增觀看直播區，找出正在直播的影片
	private static final String GET_LIVE_NOW = "SELECT * FROM LIVE WHERE LIVE_STATUS = 1 ORDER BY LIVE_START_TIME";
	
	
	@Override
	public String insert(LiveVO liveVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String next_liveno = null;
		
		try {

			con = ds.getConnection();
			
			String[] cols = {"LIVE_NO"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			pstmt.setString(1, liveVO.getLive_seller_no());
			pstmt.setString(2, liveVO.getLive_address());
			pstmt.setInt(3, liveVO.getLive_status());
			pstmt.setTimestamp(4, liveVO.getLive_start_time());
			pstmt.setTimestamp(5, liveVO.getLive_end_time());

			rs = pstmt.getGeneratedKeys();
		
			if(rs.next()) {
				next_liveno = rs.getString(1);
			}

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
		return next_liveno;
	}

	@Override
	public void update(LiveVO liveVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, liveVO.getLive_seller_no());
			pstmt.setString(2, liveVO.getLive_address());
			pstmt.setInt(3, liveVO.getLive_status());
			pstmt.setTimestamp(4, liveVO.getLive_start_time());
			pstmt.setTimestamp(5, liveVO.getLive_end_time());
			pstmt.setString(6, liveVO.getLive_no());

			updateCount = pstmt.executeUpdate();

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
		System.out.println(updateCount);
	}

	@Override
	public void delete(String live_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, live_no);

			updateCount = pstmt.executeUpdate();

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
		System.out.println(updateCount);
	}

	@Override
	public LiveVO findByPrimaryKey(String live_no) {
		LiveVO liveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, live_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getString("LIVE_NO"));
				liveVO.setLive_seller_no(rs.getString("LIVE_SELLER_NO"));
				liveVO.setLive_address(rs.getString("LIVE_ADDRESS"));
				liveVO.setLive_status(rs.getInt("LIVE_STATUS"));
				liveVO.setLive_start_time(rs.getTimestamp("LIVE_START_TIME"));
				liveVO.setLive_end_time(rs.getTimestamp("LIVE_END_TIME"));
			}

			// Handle any driver errors
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
		return liveVO;
	}

	@Override
	public List<LiveVO> getAll() {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// liveVO 也稱為 Domain objects
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getString("LIVE_NO"));
				liveVO.setLive_seller_no(rs.getString("LIVE_SELLER_NO"));
				liveVO.setLive_address(rs.getString("LIVE_ADDRESS"));
				liveVO.setLive_status(rs.getInt("LIVE_STATUS"));
				liveVO.setLive_start_time(rs.getTimestamp("LIVE_START_TIME"));
				liveVO.setLive_end_time(rs.getTimestamp("LIVE_END_TIME"));
				list.add(liveVO); // Store the row in the vector
			}

			// Handle any driver errors
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
		return list;
	}

	//找出那個人所有直播過的影片
	@Override
	public List<LiveVO> getAllBelongToSeller(String mem_no) {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLLIVES_BY_SELLER);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// liveVO 也稱為 Domain objects
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getString("LIVE_NO"));
				liveVO.setLive_seller_no(rs.getString("LIVE_SELLER_NO"));
				liveVO.setLive_address(rs.getString("LIVE_ADDRESS"));
				liveVO.setLive_status(rs.getInt("LIVE_STATUS"));
				liveVO.setLive_start_time(rs.getTimestamp("LIVE_START_TIME"));
				liveVO.setLive_end_time(rs.getTimestamp("LIVE_END_TIME"));
				list.add(liveVO); // Store the row in the vector
			}

			// Handle any driver errors
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
		return list;
	}

	
	// 2018/8/4 Hugh新增：觀看直播區
	@Override
	public List<LiveVO> getLiveNow() {
		List<LiveVO> list = new ArrayList<LiveVO>();
		LiveVO liveVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LIVE_NOW);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// liveVO 也稱為 Domain objects
				liveVO = new LiveVO();
				liveVO.setLive_no(rs.getString("LIVE_NO"));
				liveVO.setLive_seller_no(rs.getString("LIVE_SELLER_NO"));
				liveVO.setLive_address(rs.getString("LIVE_ADDRESS"));
				liveVO.setLive_status(rs.getInt("LIVE_STATUS"));
				liveVO.setLive_start_time(rs.getTimestamp("LIVE_START_TIME"));
				liveVO.setLive_end_time(rs.getTimestamp("LIVE_END_TIME"));
				list.add(liveVO); // Store the row in the vector
			}

			// Handle any driver errors
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
		return list;
	}

}
