package com.specialForU.model;


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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// 此類別實作DAO interface，並將資料庫操作細節封裝起來
public class SpecialForUDAO implements SpecialForUDAO_interface {
	
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
	"INSERT INTO SPECIAL_FORU(MEM_NO, CLOTH_CNTS, FOOD_CNTS, GAME_CNTS, OUTDOOR_CNTS, HOMEELEC_CNTS, THREEC_CNTS)"
	+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = 
	"UPDATE SPECIAL_FORU SET CLOTH_CNTS = ?, FOOD_CNTS = ?, GAME_CNTS = ?, OUTDOOR_CNTS = ?, HOMEELEC_CNTS = ?, THREEC_CNTS = ? WHERE MEM_NO = ?";
	private static final String DELETE_STMT = 
	"DELETE FROM SPECIAL_FORU WHERE MEM_NO = ?";
	private static final String FIND_BY_PK = 
	"SELECT * FROM SPECIAL_FORU WHERE MEM_NO = ?";
	private static final String GET_ALL = "SELECT * FROM SPECIAL_FORU";
	
	
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
	
	
	@Override
	public void add(SpecialForUVO sf) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, sf.getMem_no());
			pstmt.setInt(2, sf.getCloth_cnts());
			pstmt.setInt(3, sf.getFood_cnts());
			pstmt.setInt(4, sf.getGame_cnts());
			pstmt.setInt(5, sf.getOutdoor_cnts());
			pstmt.setInt(6, sf.getHomeelec_cnts());
			pstmt.setInt(7, sf.getThreec_cnts());
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
	public void update(SpecialForUVO sf) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, sf.getCloth_cnts());
			pstmt.setInt(2, sf.getFood_cnts());
			pstmt.setInt(3, sf.getGame_cnts());
			pstmt.setInt(4, sf.getOutdoor_cnts());
			pstmt.setInt(5, sf.getHomeelec_cnts());
			pstmt.setInt(6, sf.getThreec_cnts());
			pstmt.setString(7, sf.getMem_no());
			
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

	public void delete(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, mem_no);
			
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
	public SpecialForUVO findByPK(String mem_no) {
		SpecialForUVO thisMemNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thisMemNo = new SpecialForUVO();
				thisMemNo.setMem_no(rs.getString("MEM_NO"));
				thisMemNo.setCloth_cnts(rs.getInt("CLOTH_CNTS"));
				thisMemNo.setFood_cnts(rs.getInt("FOOD_CNTS"));
				thisMemNo.setGame_cnts(rs.getInt("GAME_CNTS"));
				thisMemNo.setOutdoor_cnts(rs.getInt("OUTDOOR_CNTS"));
				thisMemNo.setHomeelec_cnts(rs.getInt("HOMEELEC_CNTS"));
				thisMemNo.setThreec_cnts(rs.getInt("THREEC_CNTS"));
				
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

		return thisMemNo;
	}

	@Override
	public List<SpecialForUVO> getAll() {
		List<SpecialForUVO> notifyList = new ArrayList<>();
		SpecialForUVO thisMemNo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				thisMemNo = new SpecialForUVO();
				thisMemNo.setMem_no(rs.getString("MEM_NO"));
				thisMemNo.setCloth_cnts(rs.getInt("CLOTH_CNTS"));
				thisMemNo.setFood_cnts(rs.getInt("FOOD_CNTS"));
				thisMemNo.setGame_cnts(rs.getInt("GAME_CNTS"));
				thisMemNo.setOutdoor_cnts(rs.getInt("OUTDOOR_CNTS"));
				thisMemNo.setHomeelec_cnts(rs.getInt("HOMEELEC_CNTS"));
				thisMemNo.setThreec_cnts(rs.getInt("THREEC_CNTS"));
				notifyList.add(thisMemNo);
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
		return notifyList;
	}


	@Override
	public void updateMemPreferenceCode(Integer code, String mem_no) {
		// TODO Auto-generated method stub
		
	}
	

}
