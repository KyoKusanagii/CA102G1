package com.itempic.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class ItempicJDBCDAO implements ItempicDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER = "instabuy";	//此為登入的資料庫名稱，非資料庫帳號
	private static final String PASSWORD = "123456";
	
	private static final String INSERT_STMT = 			
	"INSERT INTO ITEMPIC(ITEM_PIC_NO, ITEM_NO, ITEM_PIC) VALUES(itempic_seq.NEXTVAL, ?, ?)";
	
	private static final String UPDATE_STMT = 
	"UPDATE ITEMPIC SET ITEM_NO = ?, ITEM_PIC = ? WHERE ITEM_PIC_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM ITEMPIC WHERE ITEM_PIC_NO = ?";
	
	private static final String FIND_ONEITEM_ALLPIC =  "SELECT * FROM ITEMPIC WHERE ITEM_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM ITEMPIC WHERE ITEM_NO = ? AND ITEM_PIC_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM ITEMPIC ORDER BY ITEM_NO";
//20180725 商品縮圖	
	private static final String FIND_THUMBNAIL = "SELECT A.*, B.ITEM_PIC FROM(SELECT  MIN(ITEM_PIC_NO) ITEM_PIC_NO, ITEM_NO FROM ITEMPIC GROUP BY ITEM_NO)A LEFT JOIN ITEMPIC B ON A.ITEM_PIC_NO = B.ITEM_PIC_NO "
			+ "WHERE A.ITEM_NO =?";
	
	public static byte[] getItemPicByteArray(String path) throws IOException {
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

	@Override
	public void insert(ItempicVO itempic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, itempic.getItem_no());
			pstmt.setBytes(2, itempic.getItem_pic());
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
	public void update(ItempicVO itempic) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, itempic.getItem_no());
			pstmt.setBytes(2, itempic.getItem_pic());
			pstmt.setString(3, itempic.getItem_pic_no());
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
	public void delete(String item_pic_no) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, item_pic_no);	
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
	public ItempicVO findByPK(String item_no, String item_pic_no) {
		ItempicVO itempic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, item_no);
			pstmt.setString(2, item_pic_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				itempic = new ItempicVO();
				itempic.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				itempic.setItem_no(rs.getString("ITEM_NO"));
				itempic.setItem_pic(rs.getBytes("ITEM_PIC"));		
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
		return itempic;
	}
	
	@Override
	public List<ItempicVO> getAll() {
		List<ItempicVO> itempicList = new ArrayList<>();
		ItempicVO itempic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();


			while (rs.next()) {
				itempic = new ItempicVO();
				itempic.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				itempic.setItem_no(rs.getString("ITEM_NO"));
				itempic.setItem_pic(rs.getBytes("ITEM_PIC"));
				itempicList.add(itempic);
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
		return itempicList;
	}

	@Override
	public List<ItempicVO> getOneItemAllPic(String item_no) {
		// TODO Auto-generated method stub
		List<ItempicVO> itempicList = new ArrayList<>();
		ItempicVO itempic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_ONEITEM_ALLPIC);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				itempic = new ItempicVO();
				itempic.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				itempic.setItem_no(rs.getString("ITEM_NO"));
				itempic.setItem_pic(rs.getBytes("ITEM_PIC"));
				itempicList.add(itempic);
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
		return itempicList;
	}
	
	public ItempicVO findThumbnail(String item_no) {
		ItempicVO itempic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_THUMBNAIL);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				itempic = new ItempicVO();
				itempic.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				itempic.setItem_no(rs.getString("ITEM_NO"));
				String encodedText = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				itempic.setEncoded(encodedText);
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
		return itempic;
	}

	
}