package com.item.model;

import java.sql.Connection;
import java.util.Base64;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemJDBCDAO implements ItemDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "Instabuy";
	private static final String PASSWORD = "123456";

	private static final String INSERT_STMT = "INSERT INTO ITEM(ITEM_NO,ITEM_NAME,ITEM_PRICE,ITEM_PRIMARY_CLASS,ITEM_SECONDARY_CLASS,ITEM_OWNER,"
			+ "IS_FB_LAUNCH,IS_MALL_LAUNCH,ITEM_INVENTORY,ITEM_DESCRIPTION)"
			+ "VALUES(('I'||LPAD(to_char(itemno_SEQ.nextval),5,'0')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = "UPDATE ITEM SET ITEM_NAME = ?,ITEM_PRICE = ?,ITEM_PRIMARY_CLASS = ?,ITEM_SECONDARY_CLASS = ?,ITEM_OWNER = ?,IS_FB_LAUNCH = ?,"
			+ "IS_MALL_LAUNCH = ?,ITEM_INVENTORY = ?,ITEM_DESCRIPTION = ? WHERE ITEM_NO = ?";
	
	private static final String FIND_BY_PK_PICS = "SELECT * FROM ITEM JOIN ITEMPIC ON ITEM.ITEM_NO = ITEMPIC.ITEM_NO WHERE ITEM.ITEM_NO = ?";

	private static final String DELETE_STMT = "DELETE FROM ITEM WHERE ITEM_NO = ?"; // 商品不能被刪除

	private static final String FIND_BY_PK = "SELECT * FROM ITEM WHERE ITEM_NO = ?";

	// 20180724新增會員只看到自己商品
	private static final String GET_ALL = "SELECT * FROM ITEM  WHERE ITEM_OWNER = ? ORDER BY ITEM_NO";

	// 20180723新增上下架設定
	private static final String UPDATE_LAUNCH = "UPDATE ITEM SET IS_FB_LAUNCH = ?, IS_MALL_LAUNCH = ? WHERE ITEM_NO=?";

	// 20180724 Hugh搜尋欄
	private static final String FIND_BY_CAT = 
	"SELECT * FROM ITEM NATURAL JOIN (SELECT A.*, B.ITEM_PIC FROM ( SELECT  MIN(ITEM_PIC_NO) ITEM_PIC_NO, ITEM_NO FROM ITEMPIC GROUP BY ITEM_NO)A, ITEMPIC B WHERE A.ITEM_PIC_NO = B.ITEM_PIC_NO) WHERE IS_MALL_LAUNCH = '1' AND ITEM_PRIMARY_CLASS = ?";

	private static final String FIND_BY_PART = 
	"SELECT * FROM ITEM NATURAL JOIN (SELECT A.*, B.ITEM_PIC FROM ( SELECT  MIN(ITEM_PIC_NO) ITEM_PIC_NO, ITEM_NO FROM ITEMPIC GROUP BY ITEM_NO)A, ITEMPIC B WHERE A.ITEM_PIC_NO = B.ITEM_PIC_NO) WHERE IS_MALL_LAUNCH = '1' AND ITEM_SECONDARY_CLASS = ?";

	private static final String FIND_BY_KEYWORD = 
	"SELECT * FROM ITEM NATURAL JOIN (SELECT A.*, B.ITEM_PIC FROM ( SELECT  MIN(ITEM_PIC_NO) ITEM_PIC_NO, ITEM_NO FROM ITEMPIC GROUP BY ITEM_NO)A, ITEMPIC B WHERE A.ITEM_PIC_NO = B.ITEM_PIC_NO) WHERE IS_MALL_LAUNCH = '1' AND UPPER(ITEM_NAME) LIKE UPPER(?)";

	@Override
	public String insert(ItemVO item) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String next_itemno = null;
		ResultSet rs = null;
		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
//			con.setAutoCommit(false);
			
			String cols[] = {"ITEM_NO"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);

			// pstmt.setString(1, item.getItem_no()); 這裡會自增主鍵，不用設定
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_price());
			pstmt.setInt(3, item.getItem_primary_class());
			pstmt.setInt(4, item.getItem_secondary_class());
			pstmt.setString(5, item.getItem_owner());
			pstmt.setInt(6, item.getIs_fb_launch());
			pstmt.setInt(7, item.getIs_mall_launch());
			pstmt.setInt(8, item.getItem_inventory());
			pstmt.setString(9, item.getItem_description());
			pstmt.executeUpdate();
			
			// 取得對應的自增主鍵值
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_itemno = rs.getString(1);
				System.out.println("自增主鍵值 = " + next_itemno + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
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
		return next_itemno;
	}


	@Override
	public void update(ItemVO item) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_price());
			pstmt.setInt(3, item.getItem_primary_class());
			pstmt.setInt(4, item.getItem_secondary_class());
			pstmt.setString(5, item.getItem_owner());
			pstmt.setInt(6, item.getIs_fb_launch());
			pstmt.setInt(7, item.getIs_mall_launch());
			pstmt.setInt(8, item.getItem_inventory());
			pstmt.setString(9, item.getItem_description());
			pstmt.setString(10, item.getItem_no()); // 決定要改的商品編號
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
	public void delete(String item_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, item_no);
			int i = pstmt.executeUpdate();
			System.out.println(i);
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
	public ItemVO findByPK(String item_no) {
		ItemVO item = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// System.out.println("item_no="+item_no);
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new ItemVO();
				item.setItem_no(rs.getString("ITEM_NO"));
				item.setItem_name(rs.getString("ITEM_NAME"));
				item.setItem_price(rs.getInt("ITEM_PRICE"));
				item.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				item.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				item.setItem_owner(rs.getString("ITEM_OWNER"));
				item.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				item.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				item.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
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
		return item;
	}

	@Override
	public List<ItemVO> getAll(String login_mem) {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO item = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setString(1, login_mem);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new ItemVO();
				item.setItem_no(rs.getString("ITEM_NO"));
				item.setItem_name(rs.getString("ITEM_NAME"));
				item.setItem_price(rs.getInt("ITEM_PRICE"));
				item.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				item.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				item.setItem_owner(rs.getString("ITEM_OWNER"));
				item.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				item.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				item.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				itemList.add(item);
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
		return itemList;
	}

	public void updateLaunch(ItemVO item) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_LAUNCH);
			pstmt.setInt(1, item.getIs_fb_launch());
			pstmt.setInt(2, item.getIs_mall_launch());
			pstmt.setString(3, item.getItem_no()); // 決定要改的商品編號
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
	public List<ItemVO> findByCat(Integer item_primary_class) {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO item = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_CAT);
			pstmt.setInt(1, item_primary_class);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new ItemVO();
				item.setItem_no(rs.getString("ITEM_NO"));
				item.setItem_name(rs.getString("ITEM_NAME"));
				item.setItem_price(rs.getInt("ITEM_PRICE"));
				item.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				item.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				item.setItem_owner(rs.getString("ITEM_OWNER"));
				item.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				item.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				item.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				item.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				String encodedText = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				item.setEncoded(encodedText);
				itemList.add(item);
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
		return itemList;
	}

	@Override
	public List<ItemVO> findByPart(Integer item_secondary_class) {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO item = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PART);
			pstmt.setInt(1, item_secondary_class);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new ItemVO();
				item.setItem_no(rs.getString("ITEM_NO"));
				item.setItem_name(rs.getString("ITEM_NAME"));
				item.setItem_price(rs.getInt("ITEM_PRICE"));
				item.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				item.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				item.setItem_owner(rs.getString("ITEM_OWNER"));
				item.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				item.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				item.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				item.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				String encodedText = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				item.setEncoded(encodedText);
				itemList.add(item);
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
		return itemList;
	}

	@Override
	public List<ItemVO> findByKeyWord(String item_name) {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO item = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_KEYWORD);
			pstmt.setString(1, "%" + item_name + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				item = new ItemVO();
				item.setItem_no(rs.getString("ITEM_NO"));
				item.setItem_name(rs.getString("ITEM_NAME"));
				item.setItem_price(rs.getInt("ITEM_PRICE"));
				item.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				item.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				item.setItem_owner(rs.getString("ITEM_OWNER"));
				item.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				item.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				item.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				item.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				item.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				String encodedText = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				item.setEncoded(encodedText);
				itemList.add(item);
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
		return itemList;
	}
	
	
	@Override
	public List<String> findItemPics(String item_no) {
		
		List<String> itemPicsList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK_PICS);
			pstmt.setString(1, item_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemPicsList.add(Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC")));
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
		return itemPicsList;
	}
}
