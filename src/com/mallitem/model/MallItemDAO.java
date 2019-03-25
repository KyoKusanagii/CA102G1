package com.mallitem.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MallItemDAO implements MallItemDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MALL_ORDER_ITEM (MALL_ORDER_NO,ITEM_NO,IS_ITEM_SALE,MALL_ITEM_CNT,MALL_ITEM_PRC) VALUES "
			+ "(?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT MALL_ORDER_NO,ITEM_NO,IS_ITEM_SALE,MALL_ITEM_CNT,MALL_ITEM_PRC FROM MALL_ORDER_ITEM ORDER BY MALL_ORDER_NO";
	private static final String GET_ONE_STMT = "SELECT MALL_ORDER_NO,ITEM_NO,IS_ITEM_SALE,MALL_ITEM_CNT,MALL_ITEM_PRC FROM MALL_ORDER_ITEM WHERE MALL_ORDER_NO = ? AND ITEM_NO = ?";
	private static final String DELETE = "DELETE FROM MALL_ORDER_ITEM WHERE MALL_ORDER_NO = ? AND ITEM_NO = ?";
	private static final String UPDATE = "UPDATE MALL_ORDER_ITEM SET IS_ITEM_SALE=?, MALL_ITEM_CNT=?, MALL_ITEM_PRC=? WHERE MALL_ORDER_NO = ? AND ITEM_NO = ?";
	@Override
	public void insert(MallItemVO mallItemVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mallItemVO.getMall_order_no());
			pstmt.setString(2, mallItemVO.getItem_no());
			pstmt.setInt(3, mallItemVO.getIs_item_sale());
			pstmt.setInt(4, mallItemVO.getMall_item_cnt());
			pstmt.setInt(5, mallItemVO.getMall_item_prc());

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
	public void update(MallItemVO mallItemVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, mallItemVO.getIs_item_sale());
			pstmt.setInt(2, mallItemVO.getMall_item_cnt());
			pstmt.setInt(3, mallItemVO.getMall_item_prc());
			pstmt.setString(4, mallItemVO.getMall_order_no());
			pstmt.setString(5, mallItemVO.getItem_no());

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
	public void delete(String mall_order_no, String item_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mall_order_no);
			pstmt.setString(2, item_no);

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
	public MallItemVO findByPrimaryKey(String mall_order_no, String item_no) {
		MallItemVO mallItemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mall_order_no);
			pstmt.setString(2, item_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallItemVO = new MallItemVO();
				mallItemVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallItemVO.setItem_no(rs.getString("ITEM_NO"));
				mallItemVO.setIs_item_sale(rs.getInt("IS_ITEM_SALE"));
				mallItemVO.setMall_item_cnt(rs.getInt("MALL_ITEM_CNT"));
				mallItemVO.setMall_item_prc(rs.getInt("MALL_ITEM_PRC"));

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
		return mallItemVO;
	}

	@Override
	public List<MallItemVO> getAll() {
		List<MallItemVO> list = new ArrayList<MallItemVO>();
		MallItemVO mallItemVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mallItemVO ¤]ºÙ¬° Domain objects
				mallItemVO = new MallItemVO();
				mallItemVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallItemVO.setItem_no(rs.getString("ITEM_NO"));
				mallItemVO.setIs_item_sale(rs.getInt("IS_ITEM_SALE"));
				mallItemVO.setMall_item_cnt(rs.getInt("MALL_ITEM_CNT"));
				mallItemVO.setMall_item_prc(rs.getInt("MALL_ITEM_PRC"));

				list.add(mallItemVO); // Store the row in the vector
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
	
	@Override
	public void insertOrder(MallItemVO mallItemVO, Connection con2) {
		int updateCount = 0;
		Connection con = con2;
		PreparedStatement pstmt = null;

		try {

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mallItemVO.getMall_order_no());
			pstmt.setString(2, mallItemVO.getItem_no());
			pstmt.setInt(3, mallItemVO.getIs_item_sale());
			pstmt.setInt(4, mallItemVO.getMall_item_cnt());
			pstmt.setInt(5, mallItemVO.getMall_item_prc());

			updateCount = pstmt.executeUpdate();

			// Handle any driver errors
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
	
		}	
	}
	
	

}
