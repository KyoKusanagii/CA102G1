package com.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemDAO implements MemDAO_interface {
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
	"INSERT INTO MEMBER(MEM_NO, MEM_ID, MEM_PSW, MEM_EMAIL, MEM_FBID, MEM_NAME, MEM_SEX, MEM_BIRTH, MEM_MOBILE, MEM_POST, MEM_ADDRESS, MEM_RECEIVEADD, MEM_CONDITION, MEM_ARTAUTH, MEM_MARTINFO, MEM_RECOMMEND, MEM_PROFILEPIC, MEM_MARTCOVER)" 
	+ "VALUES(('M'||LPAD(to_char(member_seq.nextval),5,'0')), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_STMT = 
	"UPDATE MEMBER SET MEM_PSW = ?, MEM_EMAIL = ?, MEM_FBID = ?, MEM_NAME = ?, MEM_BIRTH = ?, MEM_MOBILE = ?, MEM_POST = ?, MEM_ADDRESS = ?, MEM_RECEIVEADD = ?, MEM_MARTINFO = ?, MEM_PROFILEPIC = ?, MEM_MARTCOVER = ? WHERE MEM_NO = ?";
	
	private static final String DELETE_STMT = "DELETE FROM MEMBER WHERE MEM_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM MEMBER WHERE MEM_NO = ?";
	
	private static final String GET_ALL = "SELECT * FROM MEMBER";
	private static final String GET_ALL_DESC = "SELECT * FROM MEMBER ORDER BY MEM_NO DESC";
	
	private static final String SIGNUP_STMT = 			
	"INSERT INTO MEMBER(MEM_NO, MEM_ID, MEM_NAME, MEM_PSW, MEM_EMAIL, MEM_ACTIVECODE, MEM_CONDITION, MEM_ARTAUTH, MEM_PROFILEPIC, MEM_MARTCOVER)" 
	+ "VALUES(('M'||LPAD(to_char(member_seq.nextval),5,'0')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SETAUTH_STMT = 
	"UPDATE MEMBER SET MEM_CONDITION = ?, MEM_ARTAUTH = ? WHERE MEM_NO = ?";
	
	private static final String FIND_BY_ID =  "SELECT * FROM MEMBER WHERE MEM_ID = ?";
	
	private static final String LOGIN =
	"SELECT * FROM MEMBER WHERE MEM_ID = ? AND MEM_PSW = ?";
	
	// 8/1Hugh新增
	private static final String FIND_MALL_BY_MATRNAME = 
	"SELECT * FROM MEMBER WHERE UPPER(MEM_MARTNAME) LIKE UPPER(?)";
	
	// 8/5 MAX 新增，找出最新加入的會員
	private static final String NEWEST_MEMBER = "SELECT MAX(MEM_NO) FROM MEMBER";
	
	// 8/7Hugh新增
	private static final String ACTIVE = 
	"UPDATE MEMBER SET MEM_CONDITION = 1, MEM_ARTAUTH = 1 WHERE MEM_ID = ?";
	
	//修改會員資料
	private static final String UPDATE_MEM = 
	"UPDATE MEMBER SET MEM_EMAIL = ?, MEM_NAME = ?, MEM_MOBILE = ?,MEM_SEX = ?, MEM_POST = ?, MEM_ADDRESS = ?, MEM_BIRTH = ?, MEM_PROFILEPIC = ? WHERE MEM_NO = ?";
			
	//修改賣場資料
	private static final String UPDATE_STORE = 
	"UPDATE MEMBER SET MEM_MARTNAME = ?, MEM_MARTINFO = ?, MEM_MARTCOVER = ?, MEM_ACTIVECODE = ? WHERE MEM_NO = ?";
	
	//修改密碼
	private static final String UPDATE_PSW = 
	"UPDATE MEMBER SET MEM_PSW = ? WHERE MEM_NO = ?";
	
	// 8/10Hugh新增：FB註冊
	private static final String FB_SIGNUP = 
	"INSERT INTO MEMBER(MEM_NO, MEM_ID, MEM_PSW, MEM_EMAIL, MEM_FBID, MEM_NAME, MEM_CONDITION, MEM_ARTAUTH, MEM_PROFILEPIC, MEM_MARTCOVER)" 
	+ "VALUES(('M'||LPAD(to_char(member_seq.nextval),5,'0')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	// 8/11Hugh新增：假設FB圖片有換過
	private static final String FB_PICTURE = 
	"UPDATE MEMBER SET MEM_PROFILEPIC = ? WHERE MEM_NAME = ?";
	
	// 8/14Hugh新增：確認名字有無註冊過
	private static final String FB_FIND_BY_NAME = 
	"SELECT * FROM MEMBER WHERE MEM_NAME = ?";
	
	// 8/14Hugh新增：FB註冊登入
	private static final String FB_LOGIN =
	"SELECT * FROM MEMBER WHERE MEM_NAME = ? AND MEM_PSW = ?";
	
	// 8/11Hugh新增：假設FB圖片有換過
	private static final String FB_NAME_PICTURE = 
	"UPDATE MEMBER SET MEM_ID = ?, MEM_EMAIL = ?, MEM_FBID = ?, MEM_PROFILEPIC = ? WHERE MEM_NAME = ?";
	
	private static final String UPDATE_RECEIVEADD = 
	"UPDATE MEMBER SET MEM_RECEIVEADD = ? WHERE MEM_NO = ?";
	
	@Override 
	public void insert(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
				
			
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_psw());
			pstmt.setString(3, mem.getMem_email());
			pstmt.setString(4, mem.getMem_fbid());
			pstmt.setString(5, mem.getMem_name());
			pstmt.setString(6, mem.getMem_sex());
			pstmt.setDate(7, mem.getMem_birth());
			pstmt.setString(8, mem.getMem_mobile());
			pstmt.setString(9, mem.getMem_post());
			pstmt.setString(10, mem.getMem_address());
			pstmt.setString(11, mem.getMem_receiveadd());
			pstmt.setInt(12, 0); // 預設為0(停權)
			pstmt.setInt(13, 0); // 預設為0(停權)
			pstmt.setString(14, mem.getMem_martinfo());
			pstmt.setString(15, mem.getMem_recommend());
			pstmt.setBytes(16, mem.getMem_profilepic());
			pstmt.setBytes(17, mem.getMem_martcover());
			pstmt.executeUpdate();

		
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
	public void update(MemVO mem){
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, mem.getMem_psw());
			pstmt.setString(2, mem.getMem_email());
			pstmt.setString(3, mem.getMem_fbid());
			pstmt.setString(4, mem.getMem_name());
			pstmt.setDate(5, mem.getMem_birth());
			pstmt.setString(6, mem.getMem_mobile());
			pstmt.setString(7, mem.getMem_post());
			pstmt.setString(8, mem.getMem_address());
			pstmt.setString(9, mem.getMem_receiveadd());
			pstmt.setString(10, mem.getMem_martinfo());
			pstmt.setBytes(11, mem.getMem_profilepic());
			pstmt.setBytes(12, mem.getMem_martcover());
			pstmt.setString(13, mem.getMem_no());
			pstmt.executeUpdate();
			
		
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
	public void delete(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, mem_no);	
			pstmt.executeUpdate();

			
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
	public MemVO findByPK(String mem_no) {
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_no(rs.getString("MEM_NO"));
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_psw(rs.getString("MEM_PSW"));
				mem.setMem_email(rs.getString("MEM_EMAIL"));
				mem.setMem_fbid(rs.getString("MEM_FBID"));
				mem.setMem_name(rs.getString("MEM_NAME"));
				mem.setMem_sex(rs.getString("MEM_SEX"));
				mem.setMem_birth(rs.getDate("MEM_BIRTH"));
				mem.setMem_mobile(rs.getString("MEM_MOBILE"));
				mem.setMem_post(rs.getString("MEM_POST"));
				mem.setMem_address(rs.getString("MEM_ADDRESS"));
				mem.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				mem.setMem_condition(rs.getInt("MEM_CONDITION"));
				mem.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				mem.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				mem.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				mem.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				mem.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));
				mem.setMem_martname(rs.getString("MEM_MARTNAME"));
				mem.setMem_activecode(rs.getString("MEM_ACTIVECODE"));

				
				
				if(rs.getBytes("MEM_PROFILEPIC") != null) {
					String profilepicEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_PROFILEPIC"));
					mem.setProfilepicEncoded(profilepicEncoded);
				}
				if(rs.getBytes("MEM_MARTCOVER") != null) {
					String martcoverEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_MARTCOVER"));
					mem.setMartcoverEncoded(martcoverEncoded);
				}
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
		return mem;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> memList = new ArrayList<>();
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_no(rs.getString("MEM_NO"));
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_psw(rs.getString("MEM_PSW"));
				mem.setMem_email(rs.getString("MEM_EMAIL"));
				mem.setMem_fbid(rs.getString("MEM_FBID"));
				mem.setMem_name(rs.getString("MEM_NAME"));
				mem.setMem_sex(rs.getString("MEM_SEX"));
				mem.setMem_birth(rs.getDate("MEM_BIRTH"));
				mem.setMem_mobile(rs.getString("MEM_MOBILE"));
				mem.setMem_post(rs.getString("MEM_POST"));
				mem.setMem_address(rs.getString("MEM_ADDRESS"));
				mem.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				mem.setMem_condition(rs.getInt("MEM_CONDITION"));
				mem.setMem_martname(rs.getString("MEM_MARTNAME"));
				mem.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				mem.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				mem.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				mem.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				mem.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));							
				memList.add(mem);
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
		return memList;
	}
	
	@Override
	public List<MemVO> getAllDesc() {
		List<MemVO> memList = new ArrayList<>();
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DESC);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_no(rs.getString("MEM_NO"));
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_psw(rs.getString("MEM_PSW"));
				mem.setMem_email(rs.getString("MEM_EMAIL"));
				mem.setMem_fbid(rs.getString("MEM_FBID"));
				mem.setMem_name(rs.getString("MEM_NAME"));
				mem.setMem_sex(rs.getString("MEM_SEX"));
				mem.setMem_birth(rs.getDate("MEM_BIRTH"));
				mem.setMem_mobile(rs.getString("MEM_MOBILE"));
				mem.setMem_post(rs.getString("MEM_POST"));
				mem.setMem_address(rs.getString("MEM_ADDRESS"));
				mem.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				mem.setMem_condition(rs.getInt("MEM_CONDITION"));
				mem.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				mem.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				mem.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				mem.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				mem.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));							
				memList.add(mem);
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
		return memList;
	}
	
	
	@Override
	public void signup(MemVO mem) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(SIGNUP_STMT);
			
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_name());
			pstmt.setString(3, mem.getMem_psw());
			pstmt.setString(4, mem.getMem_email());
			pstmt.setString(5, mem.getMem_activecode());
			pstmt.setInt(6, 0); // 預設為0(未激活)
			pstmt.setInt(7, 0); // 預設為0(未激活)
			pstmt.setBytes(8, mem.getMem_profilepic());
			pstmt.setBytes(9, mem.getMem_martcover());
			pstmt.executeUpdate();
			
		
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
	public void setAuth(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SETAUTH_STMT);
			pstmt.setInt(1, mem.getMem_condition());
			pstmt.setInt(2, mem.getMem_artauth());
			pstmt.setString(3, mem.getMem_no());
			pstmt.executeUpdate();
			
		
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
	
	@Override// Hugh 7/29新增:確認帳號是否存在
	public MemVO checkID(String mem_id) {
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ID);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_id(rs.getString("MEM_ID"));
				
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
		return mem;
	}
	
	// For Larry
	@Override
	public MemVO findByID(String mem_id) {
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ID);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_no(rs.getString("MEM_NO"));
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_psw(rs.getString("MEM_PSW"));
				mem.setMem_email(rs.getString("MEM_EMAIL"));
				mem.setMem_activecode(rs.getString("MEM_ACTIVECODE"));
				mem.setMem_fbid(rs.getString("MEM_FBID"));
				mem.setMem_name(rs.getString("MEM_NAME"));
				mem.setMem_sex(rs.getString("MEM_SEX"));
				mem.setMem_birth(rs.getDate("MEM_BIRTH"));
				mem.setMem_mobile(rs.getString("MEM_MOBILE"));
				mem.setMem_post(rs.getString("MEM_POST"));
				mem.setMem_address(rs.getString("MEM_ADDRESS"));
				mem.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				mem.setMem_condition(rs.getInt("MEM_CONDITION"));
				mem.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				mem.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				mem.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				mem.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				mem.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));
				mem.setMem_martname(rs.getString("MEM_MARTNAME"));
				
				
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
		return mem;
	}
	
	@Override
	public MemVO login(String mem_id, String mem_psw) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, mem_psw);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_email(rs.getString("MEM_EMAIL"));
				memVO.setMem_fbid(rs.getString("MEM_FBID"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_sex(rs.getString("MEM_SEX"));
				memVO.setMem_birth(rs.getDate("MEM_BIRTH"));
				memVO.setMem_mobile(rs.getString("MEM_MOBILE"));
				memVO.setMem_post(rs.getString("MEM_POST"));
				memVO.setMem_address(rs.getString("MEM_ADDRESS"));
				memVO.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				memVO.setMem_condition(rs.getInt("MEM_CONDITION"));
				memVO.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				memVO.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				memVO.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				memVO.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				memVO.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));
				memVO.setMem_activecode(rs.getString("MEM_ACTIVECODE"));
				if(rs.getBytes("MEM_PROFILEPIC") != null) {
					String profilepicEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_PROFILEPIC"));
					memVO.setProfilepicEncoded(profilepicEncoded);
				}
				if(rs.getBytes("MEM_MARTCOVER") != null) {
					String martcoverEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_MARTCOVER"));
					memVO.setMartcoverEncoded(martcoverEncoded);
				}
				memVO.setMem_martname(rs.getString("MEM_MARTNAME"));
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
		return memVO;
	
	}

	// 8/1 HUGH新增:找賣場
	@Override
	public List<MemVO> searchMall(String mem_martname) {
		List<MemVO> memList = new ArrayList<>();
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_MALL_BY_MATRNAME);
			pstmt.setString(1,  "%" + mem_martname + "%");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_no(rs.getString("MEM_NO"));
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_martname(rs.getString("MEM_MARTNAME"));
				mem.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				mem.setMem_recommend(rs.getString("MEM_RECOMMEND"));
//				mem.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
//				mem.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));
				if(rs.getBytes("MEM_PROFILEPIC") != null) {
					String profilepicEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_PROFILEPIC"));
					mem.setProfilepicEncoded(profilepicEncoded);
				}
				if(rs.getBytes("MEM_MARTCOVER") != null) {
					String martcoverEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_MARTCOVER"));
					mem.setMartcoverEncoded(martcoverEncoded);
				}
				memList.add(mem);
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
		return memList;
	}
	
	// Max 8/5 找出加入的最新會員編號 
	@Override
	public String findNewestMember() {
		String newest_member_no = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(NEWEST_MEMBER);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				newest_member_no = rs.getString("MAX(MEM_NO)");
			}
		}catch (SQLException se) {
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
		return newest_member_no;
	}
	
	
	// 8/7 Hugh新增信箱驗證
	@Override
	public void activeMem(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ACTIVE);
			pstmt.setString(1, mem.getMem_id());
			pstmt.executeUpdate();
			
		
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
	
	// Larry新增
	@Override
	public Map<String, String> getNameByNO() {
		Map<String, String> getNameByNO = new HashMap<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				getNameByNO.put(rs.getString("MEM_NO"), rs.getString("MEM_NAME"));				
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
		return getNameByNO;
	}



	// Larry新增
	@Override
	public Map<String, byte[]> getPicByNO() {
		Map<String, byte[]> getPicByNO = new HashMap<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				getPicByNO.put(rs.getString("MEM_NO"), rs.getBytes("MEM_PROFILEPIC"));			
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
		return getPicByNO;
	}
	
	@Override
	public void updateStore(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STORE);
			pstmt.setString(1, mem.getMem_martname());
			pstmt.setString(2, mem.getMem_martinfo());
			pstmt.setBytes(3, mem.getMem_martcover());
			pstmt.setString(4, mem.getMem_activecode());
			pstmt.setString(5, mem.getMem_no());
			
			pstmt.executeUpdate();
			
			
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
	public void updateMem(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_MEM);
			pstmt.setString(1, memVO.getMem_email());
			pstmt.setString(2, memVO.getMem_name());
			pstmt.setString(3, memVO.getMem_mobile());
			pstmt.setString(4, memVO.getMem_sex());
			pstmt.setString(5, memVO.getMem_post());
			pstmt.setString(6, memVO.getMem_address());
			pstmt.setDate(7, memVO.getMem_birth());
			pstmt.setBytes(8, memVO.getMem_profilepic());
			pstmt.setString(9, memVO.getMem_no());
			pstmt.executeUpdate();
			
		
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
	public void updatePsw(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PSW);
			pstmt.setString(1, mem.getMem_psw());
			pstmt.setString(2, mem.getMem_no());
			
			pstmt.executeUpdate();
			
		
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
	public void fbSignup(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FB_SIGNUP);
			
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_psw());
			pstmt.setString(3, mem.getMem_email());
			pstmt.setString(4, mem.getMem_fbid());
			pstmt.setString(5, mem.getMem_name());
			pstmt.setInt(6, 1); //FB登入不驗證
			pstmt.setInt(7, 1); //FB登入不驗證
			pstmt.setBytes(8, mem.getMem_profilepic());
			pstmt.setBytes(9, mem.getMem_martcover());
			pstmt.executeUpdate();
			
		
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
	public void fbNamePicture(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FB_PICTURE);
			pstmt.setBytes(1, mem.getMem_profilepic());
			pstmt.setString(2, mem.getMem_name());
			
			pstmt.executeUpdate();
			
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
	public void fbFullInfo(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FB_NAME_PICTURE);
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_email());
			pstmt.setString(3, mem.getMem_fbid());
			pstmt.setBytes(4, mem.getMem_profilepic());
			pstmt.setString(5, mem.getMem_name());
			
			pstmt.executeUpdate();
			
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
	
	@Override// 8/14Hugh新增：確認名字有無註冊過
	public MemVO checkFBName(String mem_name) {
		MemVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FB_FIND_BY_NAME);
			pstmt.setString(1, mem_name);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MemVO();
				mem.setMem_id(rs.getString("MEM_ID"));
				mem.setMem_name(rs.getString("MEM_NAME"));
				mem.setMem_email(rs.getString("MEM_EMAIL"));
				//0817新增by CY
				mem.setMem_no(rs.getString("MEM_NO"));
				
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
		return mem;
	}
	
	@Override// 8/14Hugh新增：FB登入
	public MemVO fbLogin(String mem_name, String mem_psw) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FB_LOGIN);
			pstmt.setString(1, mem_name);
			pstmt.setString(2, mem_psw);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_no(rs.getString("MEM_NO"));
				memVO.setMem_id(rs.getString("MEM_ID"));
				memVO.setMem_psw(rs.getString("MEM_PSW"));
				memVO.setMem_email(rs.getString("MEM_EMAIL"));
				memVO.setMem_fbid(rs.getString("MEM_FBID"));
				memVO.setMem_name(rs.getString("MEM_NAME"));
				memVO.setMem_sex(rs.getString("MEM_SEX"));
				memVO.setMem_birth(rs.getDate("MEM_BIRTH"));
				memVO.setMem_mobile(rs.getString("MEM_MOBILE"));
				memVO.setMem_post(rs.getString("MEM_POST"));
				memVO.setMem_address(rs.getString("MEM_ADDRESS"));
				memVO.setMem_receiveadd(rs.getString("MEM_RECEIVEADD"));
				memVO.setMem_condition(rs.getInt("MEM_CONDITION"));
				memVO.setMem_artauth(rs.getInt("MEM_ARTAUTH"));
				memVO.setMem_martinfo(rs.getString("MEM_MARTINFO"));
				memVO.setMem_recommend(rs.getString("MEM_RECOMMEND"));
				memVO.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));
				memVO.setMem_martcover(rs.getBytes("MEM_MARTCOVER"));
				if(rs.getBytes("MEM_PROFILEPIC") != null) {
					String profilepicEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_PROFILEPIC"));
					memVO.setProfilepicEncoded(profilepicEncoded);
				}
				if(rs.getBytes("MEM_MARTCOVER") != null) {
					String martcoverEncoded = Base64.getEncoder().encodeToString(rs.getBytes("MEM_MARTCOVER"));
					memVO.setMartcoverEncoded(martcoverEncoded);
				}
				memVO.setMem_martname(rs.getString("MEM_MARTNAME"));
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
		return memVO;
	
	}

	@Override
	public void updateReceiveadd(MemVO mem) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_RECEIVEADD);
			pstmt.setString(1, mem.getMem_receiveadd());
			pstmt.setString(2, mem.getMem_no());
			
			pstmt.executeUpdate();
			
		
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
	
	
	

}