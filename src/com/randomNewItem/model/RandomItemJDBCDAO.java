package com.randomNewItem.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.item.model.ItemVO;

public class RandomItemJDBCDAO implements RandomItem_interface{
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "Instabuy";
	private static final String PASSWORD = "123456";
	
	private static final String FIND_RANDOM_ROWS = 
			"SELECT * FROM ( SELECT * FROM ITEM NATURAL JOIN ITEMPIC ORDER BY dbms_random.value) WHERE rownum <= ?";
	private static final String FIND_RANDOM_ROWS_BY_CATEGORY = 
			"SELECT * FROM ( SELECT * FROM ITEM NATURAL JOIN ITEMPIC ORDER BY dbms_random.value) WHERE ITEM_PRIMARY_CLASS = ? AND rownum <= 5";

	@Override
	public List<ItemVO> findRandomItem() {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO thisItemNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_RANDOM_ROWS);
			pstmt.setString(1, "6");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thisItemNo = new ItemVO();
				thisItemNo.setItem_no(rs.getString("ITEM_NO"));
				thisItemNo.setItem_name(rs.getString("ITEM_NAME"));
				thisItemNo.setItem_price(rs.getInt("ITEM_PRICE"));
				thisItemNo.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				thisItemNo.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				thisItemNo.setItem_owner(rs.getString("ITEM_OWNER"));
				thisItemNo.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				thisItemNo.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				thisItemNo.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				thisItemNo.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				thisItemNo.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				String picToString = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				thisItemNo.setEncoded(picToString);
				itemList.add(thisItemNo);
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
	public List<ItemVO> getRandomFiveItemsByCategory(Integer category) {
		List<ItemVO> itemList = new ArrayList<>();
		ItemVO thisItemNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_RANDOM_ROWS_BY_CATEGORY);
			pstmt.setInt(1, category);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thisItemNo = new ItemVO();
				thisItemNo.setItem_no(rs.getString("ITEM_NO"));
				thisItemNo.setItem_name(rs.getString("ITEM_NAME"));
				thisItemNo.setItem_price(rs.getInt("ITEM_PRICE"));
				thisItemNo.setItem_primary_class(rs.getInt("ITEM_PRIMARY_CLASS"));
				thisItemNo.setItem_secondary_class(rs.getInt("ITEM_SECONDARY_CLASS"));
				thisItemNo.setItem_owner(rs.getString("ITEM_OWNER"));
				thisItemNo.setIs_fb_launch(rs.getInt("IS_FB_LAUNCH"));
				thisItemNo.setIs_mall_launch(rs.getInt("IS_MALL_LAUNCH"));
				thisItemNo.setItem_inventory(rs.getInt("ITEM_INVENTORY"));
				thisItemNo.setItem_description(rs.getString("ITEM_DESCRIPTION"));
				thisItemNo.setItem_pic_no(rs.getString("ITEM_PIC_NO"));
				String picToString = Base64.getEncoder().encodeToString(rs.getBytes("ITEM_PIC"));
				thisItemNo.setEncoded(picToString);
				itemList.add(thisItemNo);
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

}
