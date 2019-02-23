package com.limitSale.model;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 此類別實作DAO interface，並將資料庫操作細節封裝起來
public class LimitSaleJDBCDAO implements LimitSaleDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "instabuy";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = 
	"INSERT INTO LIMIT_SALE(SALE_NO, ITEM_NO, SALE_START, SALE_END, SALE_PRICE, SALE_STATUS, SALE_REMARK)"
	+ "VALUES(LIMIT_SALE_SEQ.nextval, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = 
	"UPDATE LIMIT_SALE SET ITEM_NO = ?, SALE_START = ?, SALE_END = ?, SALE_PRICE = ?,SALE_STATUS = ?, SALE_REMARK = ? WHERE SALE_NO = ?";
	private static final String DELETE_STMT = 
	"DELETE FROM LIMIT_SALE WHERE SALE_NO = ?";
	private static final String FIND_BY_PK = 
	"SELECT * FROM LIMIT_SALE WHERE SALE_NO = ?";
	private static final String GET_ALL = "SELECT * FROM LIMIT_SALE";
	private static final String FIND_ONE_SALE = "SELECT * FROM LIMIT_SALE WHERE ITEM_NO = ?";
	//20180804 首頁限時特賣
	private static final String FIND_HOME_PAGE = "select * from( select ls.* from limit_sale ls where sale_status = 2" 
			+"and to_char(sale_end,'YYYY-MM-DD HH24:MI:SS')>to_char(sysdate,'YYYY-MM-DD HH24:MI:SS')" 
			+"order by sale_end)"
			+"where ROWNUM = 1 and 1 = ?";
	
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
	
	public void add(LimitSaleVO limitSaleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			//pstmt.setInt(1, limitSaleVO.getSale_no()); 自增主鍵
			pstmt.setString(1, limitSaleVO.getItem_no());
			pstmt.setTimestamp(2, limitSaleVO.getSale_start());
			pstmt.setTimestamp(3, limitSaleVO.getSale_end());
			pstmt.setInt(4, limitSaleVO.getSale_price());
			pstmt.setInt(5, limitSaleVO.getSale_status());
			pstmt.setString(6, limitSaleVO.getSale_remark());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
	public void update(LimitSaleVO limitSaleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			
			pstmt.setString(1, limitSaleVO.getItem_no());
			pstmt.setTimestamp(2, limitSaleVO.getSale_start());
			pstmt.setTimestamp(3, limitSaleVO.getSale_end());
			pstmt.setInt(4, limitSaleVO.getSale_price());
			pstmt.setInt(5, limitSaleVO.getSale_no());
			pstmt.setInt(6, limitSaleVO.getSale_status());
			pstmt.setString(7, limitSaleVO.getSale_remark());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
	public void delete(Integer sale_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, sale_no);
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
	public LimitSaleVO findByPK(Integer sale_No) {
		LimitSaleVO limitSaleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setInt(1, sale_No);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(rs.getInt("Sale_NO"));
				limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
				limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
				limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
				limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
				limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
				limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
			}
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

		return limitSaleVO;
	}

	@Override
	public List<LimitSaleVO> getAll() {
		List<LimitSaleVO> list = new ArrayList<>();
		LimitSaleVO limitSaleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(rs.getInt("Sale_NO"));
				limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
				limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
				limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
				limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
				limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
				limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
				list.add(limitSaleVO);
			}

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
	public LimitSaleVO findOneSale(String item_no) {
		LimitSaleVO limitSaleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ONE_SALE);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(rs.getInt("Sale_NO"));
				limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
				limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
				limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
				limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
				limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
				limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
			}
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

		return limitSaleVO;
	}
	
	@Override
	public List<LimitSaleVO> findHomePage() {
		List<LimitSaleVO> list = null;
		LimitSaleVO limitSaleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_HOME_PAGE);
			pstmt.setInt(1, 1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				limitSaleVO = new LimitSaleVO();
				limitSaleVO.setSale_no(rs.getInt("SALE_NO"));
				limitSaleVO.setItem_no(rs.getString("ITEM_NO"));
				limitSaleVO.setSale_start(rs.getTimestamp("SALE_START"));
				limitSaleVO.setSale_end(rs.getTimestamp("SALE_END"));
				limitSaleVO.setSale_price(rs.getInt("SALE_PRICE"));
				limitSaleVO.setSale_status(rs.getInt("SALE_STATUS"));
				limitSaleVO.setSale_remark(rs.getString("SALE_REMARK"));
				list.add(limitSaleVO);
			}
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
	public void offLimit(Integer sale_no) {
		// TODO Auto-generated method stub
		
	}
	

}
