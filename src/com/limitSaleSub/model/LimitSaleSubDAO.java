package com.limitSaleSub.model;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// 此類別實作DAO interface，並將資料庫操作細節封裝起來
public class LimitSaleSubDAO implements LimitSaleSubDAO_interface {
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
	"INSERT INTO LIMIT_SALE(SALE_NO, ITEM_NO, SALE_START, SALE_END, SALE_PRICE)"
	+ "VALUES(LIMIT_SALE_SEQ.nextval, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = 
	"UPDATE LIMIT_SALE SET ITEM_NO = ?, SALE_START = ?, SALE_END = ?, SALE_PRICE = ? WHERE SALE_NO = ?";
	private static final String DELETE_STMT = 
	"DELETE FROM LIMIT_SALE WHERE SALE_NO = ?";
	private static final String FIND_BY_PK = 
	"SELECT * FROM LIMIT_SALE WHERE SALE_NO = ?";
	private static final String GET_RANDOM_ONE = 
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO) ORDER BY dbms_random.value) WHERE rownum <= 1 AND SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	private static final String FIND_BY_ITEM_NO = 
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO)) where ITEM_NO = ? AND SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	private static final String FIND_BY_SELLER = 
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO)) where ITEM_OWNER = ? AND SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	private static final String FIND_BY_KEYWORD = 
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO)) where ITEM_NAME like ? AND SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	private static final String GET_RANDOM_FIVE =
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO) ORDER BY dbms_random.value) WHERE rownum <= 5 AND SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	private static final String GET_ALL = 
	"SELECT * FROM LIMIT_SALE natural join Item natural join (SELECT * FROM ITEMPIC WHERE ITEM_PIC_NO IN (SELECT MIN(ITEM_PIC_NO) FROM ITEMPIC GROUP BY ITEM_NO)) WHERE SALE_STATUS = 1 AND IS_MALL_LAUNCH = 1 AND SALE_START < SYSTIMESTAMP AND SALE_END > SYSTIMESTAMP order by SALE_END";
	
	
	//InputStream轉Byte
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
	
	public void add(LimitSaleSubVO ls) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			//pstmt.setInt(1, ls.getSale_no()); 自增主鍵
			pstmt.setString(1, ls.getItem_no());
			pstmt.setTimestamp(2, ls.getSale_start());
			pstmt.setTimestamp(3, ls.getSale_end());
			pstmt.setInt(4, ls.getSale_price());
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
	public void update(LimitSaleSubVO ls) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			
			pstmt.setString(1, ls.getItem_no());
			pstmt.setTimestamp(2, ls.getSale_start());
			pstmt.setTimestamp(3, ls.getSale_end());
			pstmt.setInt(4, ls.getSale_price());
			pstmt.setInt(5, ls.getSale_no());
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
	public void delete(Integer sale_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, sale_no);
			
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
	public LimitSaleSubVO findByPK(Integer live_No) {
		LimitSaleSubVO thisSaleNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, live_No);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					thisSaleNo = new LimitSaleSubVO();
					thisSaleNo.setSale_no(rs.getInt("Sale_NO"));
					thisSaleNo.setItem_no(rs.getString("ITEM_NO"));
					thisSaleNo.setSale_start(rs.getTimestamp("SALE_START"));
					thisSaleNo.setSale_end(rs.getTimestamp("SALE_END"));
					thisSaleNo.setSale_price(rs.getInt("SALE_PRICE"));
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

		return thisSaleNo;
	}
	
	@Override
	public LimitSaleSubVO getRandomOne() {
		LimitSaleSubVO thisSaleNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RANDOM_ONE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					thisSaleNo = new LimitSaleSubVO();
					thisSaleNo.setSale_no(rs.getInt("SALE_NO"));
					thisSaleNo.setItem_no(rs.getString("ITEM_NO"));
					thisSaleNo.setSale_start(rs.getTimestamp("SALE_START"));
					thisSaleNo.setSale_end(rs.getTimestamp("SALE_END"));
					thisSaleNo.setSale_price(rs.getInt("SALE_PRICE"));
					thisSaleNo.setSale_status(rs.getInt("SALE_STATUS"));
					thisSaleNo.setSale_remark(rs.getString("SALE_REMARK"));
					thisSaleNo.setItem_pic(rs.getBytes("ITEM_PIC"));
					thisSaleNo.setItem_owner(rs.getString("ITEM_OWNER"));
					thisSaleNo.setItem_name(rs.getString("ITEM_NAME"));
					thisSaleNo.setItem_price(rs.getInt("ITEM_PRICE"));
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

		return thisSaleNo;
	}
	
	@Override
	public LimitSaleSubVO findByItemNo(String item_no) {
		LimitSaleSubVO thisSaleNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ITEM_NO);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					thisSaleNo = new LimitSaleSubVO();
					thisSaleNo.setSale_no(rs.getInt("SALE_NO"));
					thisSaleNo.setItem_no(rs.getString("ITEM_NO"));
					thisSaleNo.setSale_start(rs.getTimestamp("SALE_START"));
					thisSaleNo.setSale_end(rs.getTimestamp("SALE_END"));
					thisSaleNo.setSale_price(rs.getInt("SALE_PRICE"));
					thisSaleNo.setSale_status(rs.getInt("SALE_STATUS"));
					thisSaleNo.setSale_remark(rs.getString("SALE_REMARK"));
					thisSaleNo.setItem_pic(rs.getBytes("ITEM_PIC"));
					thisSaleNo.setItem_owner(rs.getString("ITEM_OWNER"));
					thisSaleNo.setItem_name(rs.getString("ITEM_NAME"));
					thisSaleNo.setItem_price(rs.getInt("ITEM_PRICE"));
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

		return thisSaleNo;
	}
	
	
	
	@Override
	public Set<LimitSaleSubVO> findBySeller(String memNo) {
		Set<LimitSaleSubVO> LsByHim = new LinkedHashSet<LimitSaleSubVO>();
		LimitSaleSubVO limitSaleVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_SELLER);
			pstmt.setString(1, memNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					limitSaleVO = new LimitSaleSubVO();
					limitSaleVO.setSale_no(rs.getInt("SALE_NO"));
					limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
					limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
					limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
					limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
					limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
					limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
					limitSaleVO.setItem_pic(rs.getBytes("ITEM_PIC"));
					limitSaleVO.setItem_owner(rs.getString("Seller_ID"));
					limitSaleVO.setItem_name(rs.getString("ITEM_NAME"));
					limitSaleVO.setItem_price(rs.getInt("ITEM_PRICE"));
					LsByHim.add(limitSaleVO);
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
		
		return LsByHim;
	}

	
	@Override
	public Set<LimitSaleSubVO> findByKeyword(String search) {
		Set<LimitSaleSubVO> lsByKeyword = new LinkedHashSet<LimitSaleSubVO>();
		LimitSaleSubVO limitSaleVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_SELLER);
			pstmt.setString(1, "%"+search.toUpperCase()+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
					limitSaleVO = new LimitSaleSubVO();
					limitSaleVO.setSale_no(rs.getInt("SALE_NO"));
					limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
					limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
					limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
					limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
					limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
					limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
					limitSaleVO.setItem_pic(rs.getBytes("ITEM_PIC"));
					limitSaleVO.setItem_owner(rs.getString("ITEM_OWNER"));
					limitSaleVO.setItem_name(rs.getString("ITEM_NAME"));
					limitSaleVO.setItem_price(rs.getInt("ITEM_PRICE"));
					lsByKeyword.add(limitSaleVO);
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
		
		return lsByKeyword;
	}
	
	
	

	@Override
	public Set<LimitSaleSubVO> getAll() {
		Set<LimitSaleSubVO> allLM = new LinkedHashSet<>();
		LimitSaleSubVO thisSaleNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					thisSaleNo = new LimitSaleSubVO();
					thisSaleNo.setSale_no(rs.getInt("SALE_NO"));
					thisSaleNo.setItem_no(rs.getString("ITEM_NO"));
					thisSaleNo.setSale_start(rs.getTimestamp("SALE_START"));
					thisSaleNo.setSale_end(rs.getTimestamp("SALE_END"));
					thisSaleNo.setSale_price(rs.getInt("SALE_PRICE"));
					thisSaleNo.setSale_status(rs.getInt("SALE_STATUS"));
					thisSaleNo.setSale_remark(rs.getString("SALE_REMARK"));
					thisSaleNo.setItem_pic(rs.getBytes("ITEM_PIC"));
					thisSaleNo.setItem_owner(rs.getString("ITEM_OWNER"));
					thisSaleNo.setItem_name(rs.getString("ITEM_NAME"));
					thisSaleNo.setItem_price(rs.getInt("ITEM_PRICE"));
					allLM.add(thisSaleNo);
			}

		}  catch (SQLException se) {
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
		return allLM;
	}

	@Override
	public Set<LimitSaleSubVO> getRandomFive() {
		Set<LimitSaleSubVO> fiveLS = new LinkedHashSet<>();
		LimitSaleSubVO thisSaleNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RANDOM_FIVE);
			rs = pstmt.executeQuery();
			while (rs.next()) {
					thisSaleNo = new LimitSaleSubVO();
					thisSaleNo.setSale_no(rs.getInt("SALE_NO"));
					thisSaleNo.setItem_no(rs.getString("ITEM_NO"));
					thisSaleNo.setSale_start(rs.getTimestamp("SALE_START"));
					thisSaleNo.setSale_end(rs.getTimestamp("SALE_END"));
					thisSaleNo.setSale_price(rs.getInt("SALE_PRICE"));
					thisSaleNo.setSale_status(rs.getInt("SALE_STATUS"));
					thisSaleNo.setSale_remark(rs.getString("SALE_REMARK"));
					thisSaleNo.setItem_pic(rs.getBytes("ITEM_PIC"));
					thisSaleNo.setItem_owner(rs.getString("ITEM_OWNER"));
					thisSaleNo.setItem_name(rs.getString("ITEM_NAME"));
					thisSaleNo.setItem_price(rs.getInt("ITEM_PRICE"));
					fiveLS.add(thisSaleNo);
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
		return fiveLS;
	}

	

}
