package com.article.model;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.article.model.ArticleVO;

public class ArticleJNDIDAO implements ArticleDAO_interface{

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
//		private static DataSource ds = null;
//		static {
//			try {
//				Context ctx = new InitialContext();
//				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}
//		}
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "Instabuy";
	private static final String PASSWORD = "123456";
	public static final String INSERT_STMT = "INSERT INTO ARTICLE(ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME) VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), ?, ?, ?, ?)";
	public static final String UPDATE_STMT = "UPDATE ARTICLE SET ARTI_TOPIC = ?, ARTI_CONTENT = ?, PO_TIME = ? WHERE ARTI_NO = ?";
	public static final String DELETE_STMT = "DELETE FROM ARTICLE WHERE ARTI_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM ARTICLE WHERE ARTI_NO = ?" ;
	//public static final String GET_ALL = "SELECT * FROM ARTICLE ORDER BY ARTI_NO DESC";
	
	//只顯示沒被檢舉的、被檢舉未處理的、被檢舉駁回的文章
	public static final String GET_ALL = 
	"SELECT * FROM ARTICLE  WHERE (ARTI_NO IN (SELECT ARTICLE.ARTI_NO FROM ARTICLE JOIN ARTICLE_REPORT " + 
	"ON ARTICLE.ARTI_NO = ARTICLE_REPORT.ARTI_NO WHERE ARTICLE_REPORT.REPORT_STATUS !=1) OR (ARTI_NO NOT IN " + 
	"(SELECT ARTICLE_REPORT.ARTI_NO FROM ARTICLE_REPORT))) ORDER BY ARTICLE.ARTI_NO DESC";
	
	public static final String FIND_BY_TOPIC = "SELECT * FROM ARTICLE WHERE UPPER(ARTI_TOPIC) LIKE UPPER(?) OR UPPER(ARTI_TOPIC) LIKE UPPER(?) ORDER BY ARTI_NO DESC";
	
	
	@Override
	public void insert(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, articleVO.getArti_topic());
			pstmt.setString(2, articleVO.getMem_no());
			pstmt.setCharacterStream(3, stringToReader(articleVO.getArti_content()));
			pstmt.setTimestamp(4, articleVO.getPo_time());
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
	public void update(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, articleVO.getArti_topic());
			pstmt.setCharacterStream(2, stringToReader(articleVO.getArti_content()));
			pstmt.setTimestamp(3, articleVO.getPo_time());
			pstmt.setString(4, articleVO.getArti_no());
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
	public void delete(String arti_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
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

	@Override
	public ArticleVO findByPrimaryKey(String arti_no) {
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, arti_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArti_no(rs.getString("arti_no"));
				articleVO.setArti_topic(rs.getString("arti_topic"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArti_content(readerToString(rs.getCharacterStream("arti_content")));
				articleVO.setPo_time(rs.getTimestamp("po_time"));
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
		
		
		return articleVO;
	}

	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArti_no(rs.getString("arti_no"));
				articleVO.setArti_topic(rs.getString("arti_topic"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArti_content(readerToString(rs.getCharacterStream("arti_content")));
				articleVO.setPo_time(rs.getTimestamp("po_time"));
				list.add(articleVO);
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
	public List<ArticleVO> findByTopic(String arti_topic) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//con = ds.getConnection();
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_TOPIC);
			pstmt.setString(1, "%" + arti_topic + "%");
			pstmt.setString(2, "%" + arti_topic + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArti_no(rs.getString("arti_no"));
				articleVO.setArti_topic(rs.getString("arti_topic"));
				articleVO.setMem_no(rs.getString("mem_no"));
				articleVO.setArti_content(readerToString(rs.getCharacterStream("arti_content")));
				articleVO.setPo_time(rs.getTimestamp("po_time"));
				list.add(articleVO);
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
	
	public Reader stringToReader(String text) {
		if(text != null) {
			return new StringReader(text);
		}
		else {
			return null;
		}
	}
	
	
	
	
	
	public String readerToString(Reader reader) {
		if(reader != null) {
			int i ;
			StringBuilder sb = new StringBuilder();
			try {
				while((i = reader.read()) != -1) {
					sb.append((char)i);
				}
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	

	
}

