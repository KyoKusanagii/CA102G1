package com.trailer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.trailer.model.TrailerVO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TrailerDAO implements TrailerDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO TRAILER (TRAILER_NO,TRAILER_SELLER_NO,TRAILER_TIME,TRAILER_TOPIC) VALUES "
			+ "(('T'||LPAD(to_char(trailer_seq.NEXTVAL), 5, '0')),?,?,?)";
	private static final String GET_ALL_STMT = "SELECT TRAILER_NO,TRAILER_SELLER_NO,TRAILER_TIME,TRAILER_TOPIC FROM TRAILER ORDER BY TRAILER_NO";
	private static final String GET_ONE_STMT = "SELECT TRAILER_NO,TRAILER_SELLER_NO,TRAILER_TIME,TRAILER_TOPIC FROM TRAILER WHERE TRAILER_SELLER_NO = ?";
	private static final String DELETE = "DELETE FROM TRAILER WHERE TRAILER_NO = ?";
	private static final String UPDATE = "UPDATE TRAILER SET TRAILER_SELLER_NO=?, TRAILER_TIME=?,TRAILER_TOPIC=? WHERE TRAILER_NO = ?";

	@Override
	public void insert(TrailerVO trailerVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, trailerVO.getTrailer_seller_no());
			pstmt.setTimestamp(2, trailerVO.getTrailer_time());
			pstmt.setString(3, trailerVO.getTrailer_topic());

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
	public void update(TrailerVO trailerVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, trailerVO.getTrailer_seller_no());
			pstmt.setTimestamp(2, trailerVO.getTrailer_time());
			pstmt.setString(3, trailerVO.getTrailer_topic());
			pstmt.setString(4, trailerVO.getTrailer_no());

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
	public void delete(String trailer_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, trailer_no);

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
	public TrailerVO findByPrimaryKey(String trailer_seller_no) {
		TrailerVO trailerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, trailer_seller_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				trailerVO = new TrailerVO();
				trailerVO.setTrailer_no(rs.getString("TRAILER_NO"));
				trailerVO.setTrailer_seller_no(rs.getString("TRAILER_SELLER_NO"));
				trailerVO.setTrailer_time(rs.getTimestamp("TRAILER_TIME"));
				trailerVO.setTrailer_topic(rs.getString("TRAILER_TOPIC"));
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
		return trailerVO;
	}

	@Override
	public List<TrailerVO> getAll() {
		List<TrailerVO> list = new ArrayList<TrailerVO>();
		TrailerVO trailerVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// trailerVO ¤]ºÙ¬° Domain objects
				trailerVO = new TrailerVO();
				trailerVO.setTrailer_no(rs.getString("TRAILER_NO"));
				trailerVO.setTrailer_seller_no(rs.getString("TRAILER_SELLER_NO"));
				trailerVO.setTrailer_time(rs.getTimestamp("TRAILER_TIME"));
				trailerVO.setTrailer_topic(rs.getString("TRAILER_TOPIC"));
				list.add(trailerVO); // Store the row in the vector
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
