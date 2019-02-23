package com.mallorder.model;

import java.sql.Connection;
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

import com.android.model.BuyerOrderVO;
import com.fborder.model.FbOrderDAO;
import com.fborder.model.FbOrderVO;
import com.mallitem.model.MallItemVO;

public class MallOrderJNDIDAO implements MallOrderDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO MALL_ORDER (MALL_ORDER_NO,MALL_BUYER_NO,MALL_ORDER_TIME,MALL_ORDER_PRC,MALL_ORDER_TRANS,MALL_ORDER_STATUS,MALL_PAY_STATUS,MALL_ORDER_REMARK,MALL_TRANSPORT) VALUES "
			+ "((to_char(sysdate,'yyyymmdd')||'-M'||LPAD(to_char(mall_ord_seq.NEXTVAL), 5, '0')),?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT MALL_ORDER_NO,MALL_BUYER_NO,MALL_ORDER_TIME,MALL_ORDER_PRC,MALL_ORDER_TRANS,MALL_ORDER_STATUS,MALL_PAY_STATUS,MALL_ORDER_REMARK,MALL_TRANSPORT FROM MALL_ORDER ORDER BY MALL_ORDER_NO";
	private static final String GET_ONE_STMT = "SELECT MALL_ORDER_NO,MALL_BUYER_NO,MALL_ORDER_TIME,MALL_ORDER_PRC,MALL_ORDER_TRANS,MALL_ORDER_STATUS,MALL_PAY_STATUS,MALL_ORDER_REMARK,MALL_TRANSPORT FROM MALL_ORDER WHERE MALL_ORDER_NO = ?";
	private static final String DELETE = "DELETE FROM MALL_ORDER WHERE MALL_ORDER_NO = ?";
	private static final String UPDATE = "UPDATE MALL_ORDER SET MALL_BUYER_NO=?, MALL_ORDER_TIME=?, MALL_ORDER_PRC=?, MALL_ORDER_TRANS=?,MALL_ORDER_STATUS=?,MALL_PAY_STATUS=?,MALL_ORDER_REMARK=?,MALL_TRANSPORT=? WHERE MALL_ORDER_NO = ?";

	
	//android 取得會員訂單
	private static final String GET_MY_ORDER_FOR_BUYER = "SELECT * FROM MALL_ORDER WHERE MALL_BUYER_NO = ? ORDER BY MALL_ORDER_TIME DESC";
	private static final String GET_ALL_ORDERITEM = "SELECT A.* FROM (SELECT MO.ITEM_NO ,M.MALL_ORDER_NO, M.MALL_ORDER_TIME,  MALL_BUYER_NO, MALL_ORDER_PRC,MALL_ORDER_TRANS, MALL_ORDER_STATUS,  MALL_PAY_STATUS,MALL_ORDER_REMARK, MALL_ITEM_CNT, MALL_ITEM_PRC, ITEM_NAME, ITEM_DESCRIPTION, ITEM_PIC_NO, ITEM_PIC " + 
			"FROM MALL_ORDER M JOIN MALL_ORDER_ITEM MO ON M.MALL_ORDER_NO = MO.MALL_ORDER_NO JOIN ITEM I ON MO.ITEM_NO = I.ITEM_NO " + 
			"JOIN ITEMPIC IT ON I.ITEM_NO = IT.ITEM_NO " + 
			"WHERE M.MALL_ORDER_NO = ?)A , (SELECT  MIN(ITEM_PIC_NO)ITEM_PIC_NO, ITEM_NO FROM ITEMPIC GROUP BY ITEM_NO)B  " + 
			"WHERE A.ITEM_PIC_NO = B.ITEM_PIC_NO ORDER BY A.ITEM_NO";
	
	
	private static final String GET_MY_ORDER ="SELECT DISTINCT FO.MALL_ORDER_TIME,FO.MALL_ORDER_NO, I.ITEM_OWNER,FO.MALL_BUYER_NO,FO.MALL_ORDER_PRC,FO.MALL_ORDER_TRANS,FO.MALL_ORDER_STATUS,FO.MALL_PAY_STATUS,FO.MALL_ORDER_REMARK,FO.MALL_TRANSPORT, COUNT(FO.MALL_ORDER_TIME) OVER(PARTITION BY FO.MALL_ORDER_TIME) COUNT FROM MALL_ORDER FO, MALL_ORDER_ITEM FI, ITEM I WHERE FO.MALL_ORDER_NO = FI.MALL_ORDER_NO AND FI.ITEM_NO = I.ITEM_NO AND I.ITEM_OWNER = ? ORDER BY FO.MALL_ORDER_TIME DESC";
	
	@Override
	public void insert(MallOrderVO mallOrderVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mallOrderVO.getMall_buyer_no());
			pstmt.setTimestamp(2, mallOrderVO.getMall_order_time());
			pstmt.setInt(3, mallOrderVO.getMall_order_prc());
			pstmt.setString(4, mallOrderVO.getMall_order_trans());
			pstmt.setString(5, mallOrderVO.getMall_order_status());
			pstmt.setString(6, mallOrderVO.getMall_pay_status());
			pstmt.setString(7, mallOrderVO.getMall_order_remark());
			pstmt.setString(8, mallOrderVO.getMall_transport());

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
	public void update(MallOrderVO mallOrderVO) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mallOrderVO.getMall_buyer_no());
			pstmt.setTimestamp(2, mallOrderVO.getMall_order_time());
			pstmt.setInt(3, mallOrderVO.getMall_order_prc());
			pstmt.setString(4, mallOrderVO.getMall_order_trans());
			pstmt.setString(5, mallOrderVO.getMall_order_status());
			pstmt.setString(6, mallOrderVO.getMall_pay_status());
			pstmt.setString(7, mallOrderVO.getMall_order_remark());
			pstmt.setString(8, mallOrderVO.getMall_transport());
			pstmt.setString(9, mallOrderVO.getMall_order_no());

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
	public void delete(String mall_order_no) {
		int updateCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mall_order_no);

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
	public MallOrderVO findByPrimaryKey(String mall_order_no) {
		MallOrderVO mallOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mall_order_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mallOrderVO = new MallOrderVO();
				mallOrderVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallOrderVO.setMall_buyer_no(rs.getString("MALL_BUYER_NO"));
				mallOrderVO.setMall_order_time(rs.getTimestamp("MALL_ORDER_TIME"));
				mallOrderVO.setMall_order_prc(rs.getInt("MALL_ORDER_PRC"));
				mallOrderVO.setMall_order_trans(rs.getString("MALL_ORDER_TRANS"));
				mallOrderVO.setMall_order_status(rs.getString("MALL_ORDER_STATUS"));
				mallOrderVO.setMall_pay_status(rs.getString("MALL_PAY_STATUS"));
				mallOrderVO.setMall_order_remark(rs.getString("MALL_ORDER_REMARK"));
				mallOrderVO.setMall_transport(rs.getString("MALL_TRANSPORT"));
				
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
		return mallOrderVO;
	}

	@Override
	public List<MallOrderVO> getAll() {
		List<MallOrderVO> list = new ArrayList<MallOrderVO>();
		MallOrderVO mallOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mallOrderVO 也稱為 Domain objects
				mallOrderVO = new MallOrderVO();
				mallOrderVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallOrderVO.setMall_buyer_no(rs.getString("MALL_BUYER_NO"));
				mallOrderVO.setMall_order_time(rs.getTimestamp("MALL_ORDER_TIME"));
				mallOrderVO.setMall_order_prc(rs.getInt("MALL_ORDER_PRC"));
				mallOrderVO.setMall_order_trans(rs.getString("MALL_ORDER_TRANS"));
				mallOrderVO.setMall_order_status(rs.getString("MALL_ORDER_STATUS"));
				mallOrderVO.setMall_pay_status(rs.getString("MALL_PAY_STATUS"));
				mallOrderVO.setMall_order_remark(rs.getString("MALL_ORDER_REMARK"));
				mallOrderVO.setMall_transport(rs.getString("MALL_TRANSPORT"));
				list.add(mallOrderVO); // Store the row in the vector
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
	
	
	public List<MallOrderVO> getMyMallOrder(String item_owner) {
		List<MallOrderVO> list = new ArrayList<MallOrderVO>();
		MallOrderVO mallOrderVO = null;

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
				mallOrderVO = new MallOrderVO();
				mallOrderVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallOrderVO.setMall_buyer_no(rs.getString("MALL_BUYER_NO"));
				mallOrderVO.setMall_order_prc(rs.getInt("MALL_ORDER_PRC"));
				mallOrderVO.setMall_order_time(rs.getTimestamp("MALL_ORDER_TIME"));
				mallOrderVO.setMall_order_trans(rs.getString("MALL_ORDER_TRANS"));
				mallOrderVO.setMall_order_status(rs.getString("MALL_ORDER_STATUS"));
				mallOrderVO.setMall_pay_status(rs.getString("MALL_PAY_STATUS"));
				mallOrderVO.setMall_order_remark(rs.getString("MALL_ORDER_REMARK"));
				mallOrderVO.setMall_transport(rs.getString("MALL_TRANSPORT"));
			

				list.add(mallOrderVO); // Store the row in the vector
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
	
	
	
	public List<MallOrderVO> getMyOrderForBuyer(String buyerNo) {
		List<MallOrderVO> list = new ArrayList<MallOrderVO>();
		MallOrderVO mallOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MY_ORDER_FOR_BUYER);
			pstmt.setString(1, buyerNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mallOrderVO = new MallOrderVO();
				mallOrderVO.setMall_order_no(rs.getString("MALL_ORDER_NO"));
				mallOrderVO.setMall_buyer_no(rs.getString("MALL_BUYER_NO"));
				mallOrderVO.setMall_order_time(rs.getTimestamp("MALL_ORDER_TIME"));
				mallOrderVO.setMall_order_prc(rs.getInt("MALL_ORDER_PRC"));
				mallOrderVO.setMall_order_trans(rs.getString("MALL_ORDER_TRANS"));
				mallOrderVO.setMall_order_status(rs.getString("MALL_ORDER_STATUS"));
				mallOrderVO.setMall_pay_status(rs.getString("MALL_PAY_STATUS"));
				mallOrderVO.setMall_order_remark(rs.getString("MALL_ORDER_REMARK"));
				mallOrderVO.setMall_transport(rs.getString("MALL_TRANSPORT"));
				list.add(mallOrderVO); // Store the row in the vector
				
	
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
	
	public List<BuyerOrderVO> getAllOrderItem(String mall_order_no) {
		List<BuyerOrderVO> list = new ArrayList<BuyerOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BuyerOrderVO buyerVO = null;
		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_ORDERITEM);
			pstmt.setString(1, mall_order_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				buyerVO = new BuyerOrderVO();
				buyerVO.setItem_no(rs.getString("ITEM_NO"));
				buyerVO.setOrder_no(rs.getString("MALL_ORDER_NO"));
				buyerVO.setOrder_time(rs.getTimestamp("MALL_ORDER_TIME"));
				buyerVO.setOrder_prc(rs.getInt("MALL_ORDER_PRC"));
				buyerVO.setOrder_trans(rs.getString("MALL_ORDER_TRANS"));
				buyerVO.setOrder_status(rs.getString("MALL_ORDER_STATUS"));
				buyerVO.setPay_status(rs.getString("MALL_PAY_STATUS"));
				buyerVO.setOrder_remark(rs.getString("MALL_ORDER_REMARK"));
				buyerVO.setItem_cnt(rs.getInt("MALL_ITEM_CNT"));
				buyerVO.setItem_prc(rs.getInt("MALL_ITEM_PRC"));
				buyerVO.setItem_name(rs.getString("ITEM_NAME"));
				buyerVO.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				buyerVO.setItem_pic(rs.getBytes("ITEM_PIC"));
				buyerVO.setType("商城");
				
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
	
	
	public static void main(String[] args) {
		MallOrderDAO dao = new MallOrderDAO();
//		List<MallOrderBuyerVO> list = dao.getAllOrderItem("20180803-M00010");
//		for(MallOrderBuyerVO vo : list) {
//			System.out.println(vo.getItem_name());
//		}
		List<MallOrderVO> list = dao.getMyMallOrder("M00002");
//		System.out.println(list);
		
		
		FbOrderDAO fbdao = new FbOrderDAO();
		MallOrderDAO malldao = new MallOrderDAO();
		
		List<FbOrderVO> fbList = fbdao.getMyFBOrder("M00001");
		List<MallOrderVO> mallList = malldao.getMyMallOrder("M00001");
		
		List<BuyerOrderVO> mallList2 = malldao.getAllOrderItem("20180803-M00010");
		
		List<BuyerOrderVO> totalList = new ArrayList<>();
		BuyerOrderVO totalVO = null;
		
		for(FbOrderVO vo :fbList) {
			totalVO = new BuyerOrderVO();
			totalVO.setOrder_no(vo.getFb_order_no());
			totalVO.setOrder_time(vo.getFb_order_time());
			totalVO.setBuyer_no(vo.getFb_buyer_no());
			totalVO.setOrder_prc(vo.getFb_order_prc());
			totalVO.setOrder_trans(vo.getFb_order_trans());
			totalVO.setOrder_status(vo.getFb_order_status());
			totalVO.setPay_status(vo.getFb_pay_status());
			totalVO.setOrder_remark(vo.getFb_order_remark());
			totalVO.setType("FB");
			totalList.add(totalVO);
		}
		
		for(MallOrderVO vo :mallList) {
			totalVO = new BuyerOrderVO();
			totalVO.setOrder_no(vo.getMall_order_no());
			totalVO.setOrder_time(vo.getMall_order_time());
			totalVO.setBuyer_no(vo.getMall_buyer_no());
			totalVO.setOrder_prc(vo.getMall_order_prc());
			totalVO.setOrder_trans(vo.getMall_order_trans());
			totalVO.setOrder_status(vo.getMall_order_status());
			totalVO.setPay_status(vo.getMall_pay_status());
			totalVO.setOrder_remark(vo.getMall_order_remark());
			totalVO.setType("商城");
			
			totalList.add(totalVO);
		}
		
		for(BuyerOrderVO vo: mallList2) {
			System.out.println(vo.getItem_no());
			System.out.println(vo.getOrder_time());
			System.out.println(vo.getOrder_no());
			System.out.println(vo.getBuyer_no());
			System.out.println(vo.getOrder_prc());
			System.out.println(vo.getOrder_trans());
			System.out.println(vo.getOrder_status());
			System.out.println(vo.getPay_status());
			System.out.println(vo.getOrder_remark());
			System.out.println(vo.getItem_cnt());
			System.out.println(vo.getItem_prc());
			System.out.println(vo.getItem_name());
			System.out.println(vo.getItem_description());
			System.out.println(vo.getItem_pic());
			System.out.println(vo.getType());
		}
		
//		System.out.println(totalList);
		
		
	}

	@Override
	public void insertMallOrder(MallOrderVO mallOrderVO, List<MallItemVO> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOrderSeller(String mall_order_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<Integer>> revenuesByCat() {
		// TODO Auto-generated method stub
		return null;
	}

}
