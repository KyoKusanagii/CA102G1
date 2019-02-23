package com.chat.model;

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

import com.member.model.MemVO;

public class ChatDAO implements ChatDAO_interface {
	
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
	"INSERT INTO CHAT(CHAT_MASTER_NO,CHAT_FRIENDS) VALUES(?, ?)";
	
	private static final String DELETE_STMT = "DELETE FROM  CONSULTER WHERE CONSULTER_NO = ?";
	
	private static final String FIND_BY_PK =  "SELECT * FROM CONSULTER WHERE CONSULTER_NO = ?";
	
	//找出這個人所有朋友的資料，包括會員編號、姓名、大頭貼
	private static final String GET_ALL = 
	"SELECT CHAT_FRIENDS,MEM_NO,MEM_NAME,MEM_PROFILEPIC FROM MEMBER RIGHT JOIN CHAT ON MEM_NO = CHAT_FRIENDS " + 
	"WHERE CHAT_MASTER_NO = ? ORDER BY CHAT_FRIENDS";
	

	
	@Override 
	public void insert(ChatVO chat_master) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

		
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
				
			pstmt.setString(1, chat_master.getChat_master_no()); 
			pstmt.setString(2, chat_master.getChat_friends());
			
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
	public void update(ChatVO chat_master){
		
		
	}

	@Override
	public void delete(String chat_master_no) {
		
	}

	@Override
	public ChatVO findByPK(String chat_master_no) {
		ChatVO chat_master = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, chat_master_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				chat_master = new ChatVO();
				chat_master.setChat_master_no(rs.getString("CHAT_MASTER_NO"));
				chat_master.setChat_friends(rs.getString("CHAT_FRIENDS"));
				
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
		return chat_master;
	}

	@Override
	public List<MemVO> getAll(String chat_master_no) {
		//回傳所有朋友的集合，即為會員的集合
		List<MemVO> friendsList = new ArrayList<>();
		MemVO friend = new MemVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			pstmt.setString(1, chat_master_no);
			rs = pstmt.executeQuery();
				
			friend.setMem_no("E00000");	//每個人都會有一個加入客服回好友預設對象
			friend.setMem_name("客服人員");
			friendsList.add(friend);
			
			rs.next();	//跳過客服人員的不取.
			while (rs.next()) {
				friend = new MemVO();
				friend.setMem_no(rs.getString("MEM_NO"));
				friend.setMem_name(rs.getString("MEM_NAME"));
				friend.setMem_profilepic(rs.getBytes("MEM_PROFILEPIC"));				
				friendsList.add(friend);
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
		return friendsList;
	}


}
