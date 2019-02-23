package com.fborder.model;

import java.sql.Connection;
import com.android.model.*;
import com.fbitem.model.FbItemVO;
import com.fbitem.model.FbItemDAO;
import com.mallitem.model.MallItemVO;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mallorder.model.MallOrderDAO;
import com.mallorder.model.MallOrderVO;

public class FbOrderJNDIDAO implements FbOrderDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO FB_ORDER (FB_ORDER_NO,FB_BUYER_NO,FB_ORDER_TIME,FB_ORDER_PRC,FB_ORDER_TRANS,FB_ORDER_STATUS,FB_PAY_STATUS,FB_ORDER_REMARK,FB_TRANSPORT) VALUES "
			+ "((to_char(sysdate,'yyyymmdd')||'-F'||LPAD(to_char(fb_ord_seq.NEXTVAL), 5, '0')),?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT FB_ORDER_NO,FB_BUYER_NO,FB_ORDER_TIME,FB_ORDER_PRC,FB_ORDER_TRANS,FB_ORDER_STATUS,FB_PAY_STATUS,FB_ORDER_REMARK,FB_TRANSPORT FROM FB_ORDER ORDER BY FB_ORDER_NO";
	private static final String GET_ONE_STMT = "SELECT FB_ORDER_NO,FB_BUYER_NO,FB_ORDER_TIME,FB_ORDER_PRC,FB_ORDER_TRANS,FB_ORDER_STATUS,FB_PAY_STATUS,FB_ORDER_REMARK,FB_TRANSPORT FROM FB_ORDER WHERE FB_ORDER_NO = ?";
	private static final String DELETE = "DELETE FROM FB_ORDER WHERE FB_ORDER_NO = ?";
	private static final String UPDATE = "UPDATE FB_ORDER SET FB_BUYER_NO=?, FB_ORDER_TIME=?, FB_ORDER_PRC=?, FB_ORDER_TRANS=?,FB_ORDER_STATUS=?,FB_PAY_STATUS=?,FB_ORDER_REMARK=?,FB_TRANSPORT=? WHERE FB_ORDER_NO = ?";
	// 20180730新增會員只看到自己的訂單
//	private static final String GET_MY_ORDER = "SELECT DISTINCT FO.FB_ORDER_TIME,FO.FB_ORDER_NO, I.ITEM_OWNER,FO.FB_ORDER_TRANS,FO.FB_ORDER_STATUS,FO.FB_PAY_STATUS,FO.FB_ORDER_REMARK, COUNT(FO.FB_ORDER_TIME) OVER(PARTITION BY FO.FB_ORDER_TIME) COUNT "
//			+ "FROM FB_ORDER FO, FB_ORDER_ITEM FI, ITEM I WHERE FO.FB_ORDER_NO = FI.FB_ORDER_NO AND FI.ITEM_NO = I.ITEM_NO AND I.ITEM_OWNER = ? ORDER BY FO.FB_ORDER_TIME DESC";
	
	//android  取得會員訂單
	private static final String GET_MY_ORDER_FOR_BUYER = "SELECT * FROM FB_ORDER WHERE FB_BUYER_NO = ? ORDER BY FB_ORDER_TIME DESC";
	
	private static final String GET_ALL_ORDERITEM = "SELECT F.FB_ORDER_NO,FB_ORDER_TIME, I.ITEM_NO ,FB_ORDER_PRC,FB_ORDER_TRANS , FB_ORDER_STATUS, FB_PAY_STATUS ,FB_ORDER_REMARK, FB_COMMENT, FB_ITEM_CNT, FB_ITEM_PRC, ITEM_NAME, ITEM_DESCRIPTION , FB_ITEM_PIC " + 
			"FROM FB_ORDER F JOIN FB_ORDER_ITEM FO ON F.FB_ORDER_NO = FO.FB_ORDER_NO JOIN ITEM I ON FO.ITEM_NO = I.ITEM_NO  " + 
			"WHERE F.FB_ORDER_NO = ? ";
	
	private static final String GET_MY_ORDER = "SELECT DISTINCT FO.FB_ORDER_TIME,FO.FB_ORDER_NO, I.ITEM_OWNER, FO.FB_BUYER_NO, FO.FB_ORDER_PRC,FO.FB_TRANSPORT, FO.FB_ORDER_TRANS,FO.FB_ORDER_STATUS,FO.FB_PAY_STATUS,FO.FB_ORDER_REMARK, COUNT(FO.FB_ORDER_TIME) OVER(PARTITION BY FO.FB_ORDER_TIME) COUNT FROM FB_ORDER FO, FB_ORDER_ITEM FI, ITEM I WHERE FO.FB_ORDER_NO = FI.FB_ORDER_NO AND FI.ITEM_NO = I.ITEM_NO AND I.ITEM_OWNER = ? ORDER BY FO.FB_ORDER_TIME DESC"; 
	

	@Override
	public void insert(FbOrderVO fbOrderVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, fbOrderVO.getFb_buyer_no());
			pstmt.setTimestamp(2, fbOrderVO.getFb_order_time());
			pstmt.setInt(3, fbOrderVO.getFb_order_prc());
			pstmt.setString(4, fbOrderVO.getFb_order_trans());
			pstmt.setString(5, fbOrderVO.getFb_order_status());
			pstmt.setString(6, fbOrderVO.getFb_pay_status());
			pstmt.setString(7, fbOrderVO.getFb_order_remark());
			pstmt.setString(8, fbOrderVO.getFb_transport());

			updateCount = pstmt.executeUpdate();

			// Handle any SQL errors
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
	public void update(FbOrderVO fbOrderVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, fbOrderVO.getFb_buyer_no());
			pstmt.setTimestamp(2, fbOrderVO.getFb_order_time());
			pstmt.setInt(3, fbOrderVO.getFb_order_prc());
			pstmt.setString(4, fbOrderVO.getFb_order_trans());
			pstmt.setString(5, fbOrderVO.getFb_order_status());
			pstmt.setString(6, fbOrderVO.getFb_pay_status());
			pstmt.setString(7, fbOrderVO.getFb_order_remark());
			pstmt.setString(8, fbOrderVO.getFb_transport());
			pstmt.setString(9, fbOrderVO.getFb_order_no());

			updateCount = pstmt.executeUpdate();
			// Handle any SQL errors
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
	public void delete(String fb_order_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, fb_order_no);

			updateCount = pstmt.executeUpdate();
			// Handle any SQL errors
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
	public FbOrderVO findByPrimaryKey(String fb_order_no) {
		FbOrderVO fbOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, fb_order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				fbOrderVO = new FbOrderVO();
				fbOrderVO.setFb_order_no(rs.getString("FB_ORDER_NO"));
				fbOrderVO.setFb_buyer_no(rs.getString("FB_BUYER_NO"));
				fbOrderVO.setFb_order_time(rs.getTimestamp("FB_ORDER_TIME"));
				fbOrderVO.setFb_order_prc(rs.getInt("FB_ORDER_PRC"));
				fbOrderVO.setFb_order_trans(rs.getString("FB_ORDER_TRANS"));
				fbOrderVO.setFb_order_status(rs.getString("FB_ORDER_STATUS"));
				fbOrderVO.setFb_pay_status(rs.getString("FB_PAY_STATUS"));
				fbOrderVO.setFb_order_remark(rs.getString("FB_ORDER_REMARK"));
				fbOrderVO.setFb_transport(rs.getString("FB_TRANSPORT"));
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
		return fbOrderVO;
	}

	@Override
	public List<FbOrderVO> getAll() {
		List<FbOrderVO> list = new ArrayList<FbOrderVO>();
		FbOrderVO fbOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// fbOrderVO 也稱為 Domain objects
				fbOrderVO = new FbOrderVO();
				fbOrderVO.setFb_order_no(rs.getString("FB_ORDER_NO"));
				fbOrderVO.setFb_buyer_no(rs.getString("FB_BUYER_NO"));
				fbOrderVO.setFb_order_time(rs.getTimestamp("FB_ORDER_TIME"));
				fbOrderVO.setFb_order_prc(rs.getInt("FB_ORDER_PRC"));
				fbOrderVO.setFb_order_trans(rs.getString("FB_ORDER_TRANS"));
				fbOrderVO.setFb_order_status(rs.getString("FB_ORDER_STATUS"));
				fbOrderVO.setFb_pay_status(rs.getString("FB_PAY_STATUS"));
				fbOrderVO.setFb_order_remark(rs.getString("FB_ORDER_REMARK"));
				fbOrderVO.setFb_transport(rs.getString("FB_TRANSPORT"));

				list.add(fbOrderVO); // Store the row in the vector
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
	
	public List<FbOrderVO> getMyFBOrder(String item_owner) {
		List<FbOrderVO> list = new ArrayList<FbOrderVO>();
		FbOrderVO fbOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MY_ORDER);
			pstmt.setString(1, item_owner);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// fbOrderVO 也稱為 Domain objects
				fbOrderVO = new FbOrderVO();
				fbOrderVO.setFb_order_no(rs.getString("FB_ORDER_NO"));
				fbOrderVO.setFb_buyer_no(rs.getString("FB_BUYER_NO"));
				fbOrderVO.setFb_order_prc(rs.getInt("FB_ORDER_PRC"));
				fbOrderVO.setFb_order_time(rs.getTimestamp("FB_ORDER_TIME"));
				fbOrderVO.setFb_order_trans(rs.getString("FB_ORDER_TRANS"));
				fbOrderVO.setFb_order_status(rs.getString("FB_ORDER_STATUS"));
				fbOrderVO.setFb_pay_status(rs.getString("FB_PAY_STATUS"));
				fbOrderVO.setFb_order_remark(rs.getString("FB_ORDER_REMARK"));
				fbOrderVO.setFb_transport(rs.getString("FB_TRANSPORT"));
			

				list.add(fbOrderVO); // Store the row in the vector
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
	
	
	public List<FbOrderVO> getMyOrderForBuyer(String buyerNo) {
		List<FbOrderVO> list = new ArrayList<FbOrderVO>();
		FbOrderVO fbOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MY_ORDER_FOR_BUYER);
			pstmt.setString(1, buyerNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fbOrderVO = new FbOrderVO();
				fbOrderVO.setFb_order_no(rs.getString("FB_ORDER_NO"));
				fbOrderVO.setFb_buyer_no(rs.getString("FB_BUYER_NO"));
				fbOrderVO.setFb_order_time(rs.getTimestamp("FB_ORDER_TIME"));
				fbOrderVO.setFb_order_prc(rs.getInt("FB_ORDER_PRC"));
				fbOrderVO.setFb_order_trans(rs.getString("FB_ORDER_TRANS"));
				fbOrderVO.setFb_order_status(rs.getString("FB_ORDER_STATUS"));
				fbOrderVO.setFb_pay_status(rs.getString("FB_PAY_STATUS"));
				fbOrderVO.setFb_order_remark(rs.getString("FB_ORDER_REMARK"));
				fbOrderVO.setFb_transport(rs.getString("FB_TRANSPORT"));

				list.add(fbOrderVO); // Store the row in the vector
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

	public List<BuyerOrderVO> getAllOrderItem(String orderNumber) {
		List<BuyerOrderVO> list = new ArrayList<BuyerOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BuyerOrderVO buyerVO = null;
		
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ORDERITEM);
			pstmt.setString(1, orderNumber);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				buyerVO = new BuyerOrderVO();
				buyerVO.setOrder_no(rs.getString("FB_ORDER_NO"));
				buyerVO.setOrder_time(rs.getTimestamp("FB_ORDER_TIME"));
				buyerVO.setItem_no(rs.getString("ITEM_NO"));
				buyerVO.setOrder_prc(rs.getInt("FB_ORDER_PRC"));
				buyerVO.setOrder_trans(rs.getString("FB_ORDER_TRANS"));
				buyerVO.setOrder_status(rs.getString("FB_ORDER_STATUS"));
				buyerVO.setPay_status(rs.getString("FB_PAY_STATUS"));
				buyerVO.setOrder_remark(rs.getString("FB_ORDER_REMARK"));
				buyerVO.setItem_cnt(rs.getInt("FB_ITEM_CNT"));
				buyerVO.setItem_prc(rs.getInt("FB_ITEM_PRC"));
				buyerVO.setItem_comment(rs.getString("FB_COMMENT"));
				buyerVO.setItem_name(rs.getString("ITEM_NAME"));
				buyerVO.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				buyerVO.setItem_pic(rs.getBytes("FB_ITEM_PIC"));
				buyerVO.setType("FB");
				
				list.add(buyerVO); // Store the row in the vector
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
	public void insertFbOrder(FbOrderVO fbOrderVO, List<FbItemVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
		
			String[] cols = {"FB_ORDER_NO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
		
			pstmt.setString(1, fbOrderVO.getFb_buyer_no());
			pstmt.setTimestamp(2, fbOrderVO.getFb_order_time());
			pstmt.setInt(3, fbOrderVO.getFb_order_prc());
			pstmt.setString(4, fbOrderVO.getFb_order_trans());
			pstmt.setString(5, fbOrderVO.getFb_order_status());
			pstmt.setString(6, fbOrderVO.getFb_pay_status());
			pstmt.setString(7, fbOrderVO.getFb_order_remark());
			pstmt.setString(8, fbOrderVO.getFb_transport());
			pstmt.executeUpdate();
			
			
			String next_mallno = null;
			rs = pstmt.getGeneratedKeys();
		
			if(rs.next()) {
				next_mallno = rs.getString(1);
			}
			
			rs.close();
			
			FbItemDAO fbItemdao = new FbItemDAO();

			for(FbItemVO fbItemVO : list) {
				System.out.println(1);
				fbItemVO.setFb_order_no(next_mallno);
				fbItemdao.insertOrder(fbItemVO, con);

			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
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
	
	}
	
	
	
	public static void main(String[] args) {
		FbOrderJNDIDAO dao = new FbOrderJNDIDAO();
//		List<FbOrderBuyerVO> list = dao.getAllOrderItem("20180803-F00010");
//		for(FbOrderBuyerVO vo : list) {
//			System.out.println(vo.getItem_name());
//		}
//		
		List<FbOrderVO> list = dao.getMyFBOrder("M0000");
		for(FbOrderVO vo : list) {
			System.out.println(vo.getFb_order_remark());
		}
		System.out.println(list);
		
		
	}

	@Override
	public String getOrderSeller(String fb_order_no) {
		// TODO Auto-generated method stub
		return null;
	}

}
