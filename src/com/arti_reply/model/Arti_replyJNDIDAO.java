package com.arti_reply.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;





public class Arti_replyJNDIDAO implements Arti_replyDAO_interface{
	
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
	
	public static final String INSERT_STMT = "INSERT INTO ARTI_REPLY(ARTI_NO, REP_NO, MEM_NO, REP_CONTENT, REP_TIME) VALUES(?, rep_no_seq.NEXTVAL, ?, ?, ?)";
	public static final String UPDATE_STMT = "UPDATE ARTI_REPLY SET REP_CONTENT = ?, REP_TIME = ? WHERE ARTI_NO = ? AND REP_NO = ?";
	public static final String DELETE_STMT = "DELETE FROM ARTI_REPLY WHERE ARTI_NO = ? AND REP_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM ARTI_REPLY WHERE ARTI_NO = ? AND REP_NO = ?";
	public static final String GET_ALL = "SELECT * FROM ARTI_REPLY ORDER BY REP_NO";
	public static final String GET_REP = "SELECT * FROM ARTI_REPLY WHERE ARTI_NO = ?  ORDER BY REP_NO";
	public static final String DELETE_ALL_REP = "DELETE FROM ARTI_REPLY WHERE ARTI_NO = ?";
	
	
	public void insert(Arti_replyVO arti_replyVO) {
	Connection con = null;
	PreparedStatement pstmt = null;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(INSERT_STMT);
		pstmt.setString(1, arti_replyVO.getArti_no());
		pstmt.setString(2, arti_replyVO.getMem_no());
		pstmt.setString(3, arti_replyVO.getRep_content());
		pstmt.setTimestamp(4, arti_replyVO.getRep_time());
		pstmt.executeUpdate();
		
	
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
	
	
}
@Override
public void update(Arti_replyVO arti_replyVO) {
	Connection con = null;
	PreparedStatement pstmt = null;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(UPDATE_STMT);
		pstmt.setString(1, arti_replyVO.getRep_content());
		pstmt.setTimestamp(2, arti_replyVO.getRep_time());
		pstmt.setString(3, arti_replyVO.getArti_no());
		pstmt.setInt(4, arti_replyVO.getRep_no());
		pstmt.executeUpdate();
		
		
		// Handle any SQL errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
	
}
@Override
public void delete(String arti_no, Integer rep_no) {
	Connection con = null;
	PreparedStatement pstmt = null;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(DELETE_STMT);
		pstmt.setString(1, arti_no);
		pstmt.setInt(2, rep_no);
		pstmt.executeUpdate();
		
		
		// Handle any SQL errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
	
}
@Override
public Arti_replyVO findByPrimaryKey(Integer rep_no) {
	Arti_replyVO arti_replyVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(FIND_BY_PK);
		pstmt.setInt(1, rep_no);
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			arti_replyVO = new Arti_replyVO();
			arti_replyVO.setArti_no(rs.getString("arti_no"));
			arti_replyVO.setRep_no(rs.getInt("rep_no"));
			arti_replyVO.setMem_no(rs.getString("mem_no"));
			arti_replyVO.setRep_content(rs.getString("rep_content"));
			arti_replyVO.setRep_time(rs.getTimestamp("rep_time"));
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
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}
	return arti_replyVO;
}
@Override
public List<Arti_replyVO> getAll() {
	List<Arti_replyVO> list = new ArrayList<Arti_replyVO>();
	Arti_replyVO arti_replyVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			arti_replyVO = new Arti_replyVO();
			arti_replyVO.setArti_no(rs.getString("arti_no"));
			arti_replyVO.setRep_no(rs.getInt("rep_no"));
			arti_replyVO.setMem_no(rs.getString("mem_no"));
			arti_replyVO.setRep_content(rs.getString("rep_content"));
			arti_replyVO.setRep_time(rs.getTimestamp("rep_time"));
			list.add(arti_replyVO);
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
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace(System.err);
			}
		}
		if(con != null) {
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
	public List<Arti_replyVO> getRepByArtiNO(String arti_no) {
		List<Arti_replyVO> list = new ArrayList<Arti_replyVO>();
		Arti_replyVO arti_replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REP);
			pstmt.setString(1, arti_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				arti_replyVO = new Arti_replyVO();
				arti_replyVO.setArti_no(rs.getString("arti_no"));
				arti_replyVO.setRep_no(rs.getInt("rep_no"));
				arti_replyVO.setMem_no(rs.getString("mem_no"));
				arti_replyVO.setRep_content(rs.getString("rep_content"));
				arti_replyVO.setRep_time(rs.getTimestamp("rep_time"));
				list.add(arti_replyVO);
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
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
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
	public void deleteAllRep(String arti_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ALL_REP);
			pstmt.setString(1, arti_no);
			pstmt.executeUpdate();
			
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	
	
}
