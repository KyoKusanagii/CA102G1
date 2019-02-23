package com.article.model;

import java.util.List;
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

public class ArticleJDBCDAO implements ArticleDAO_interface{

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "Instabuy";
	private static final String PASSWORD = "123456";
	
	public static final String INSERT_STMT = "INSERT INTO ARTICLE(ARTI_NO, ARTI_TOPIC, MEM_NO, ARTI_CONTENT, PO_TIME) VALUES('A'||LPAD(to_char(article_seq.NEXTVAL), 5,'0'), ?, ?, ?, ?)";
	public static final String UPDATE_STMT = "UPDATE ARTICLE SET ARTI_TOPIC = ?, ARTI_CONTENT = ?, PO_TIME = ? WHERE ARTI_NO = ?";
	public static final String DELETE_STMT = "DELETE FROM ARTICLE WHERE ARTI_NO = ?";
	public static final String FIND_BY_PK = "SELECT * FROM ARTICLE WHERE ARTI_NO = ?";
	public static final String GET_ALL = "SELECT * FROM ARTICLE ORDER BY ARTI_NO";
	public static final String FIND_BY_TOPIC = "SELECT * FROM ARTICLE WHERE UPPER(ARTI_TOPIC) LIKE UPPER(?) OR UPPER(ARTI_TOPIC) LIKE UPPER(?)";
	
	
	@Override
	public void insert(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, articleVO.getArti_topic());
			pstmt.setString(2, articleVO.getMem_no());
			pstmt.setCharacterStream(3, stringToReader(articleVO.getArti_content()));
			pstmt.setTimestamp(4, articleVO.getPo_time());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, articleVO.getArti_topic());
			pstmt.setCharacterStream(2, stringToReader(articleVO.getArti_content()));
			pstmt.setTimestamp(3, articleVO.getPo_time());
			pstmt.setString(4, articleVO.getArti_no());
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, arti_no);
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
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
			Class.forName(DRIVER);
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
			Class.forName(DRIVER);
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
			Class.forName(DRIVER);
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
	
	public static void main(String[] args) {
		
		ArticleDAO dao = new ArticleDAO();
		
//				insert()
//			ArticleVO articleVO = new ArticleVO();
//			articleVO.setArti_topic("6666");
//			articleVO.setMem_no("M00006");
//			articleVO.setArti_content("7777");
//			articleVO.setPo_time(java.sql.Timestamp.valueOf("2018-12-03 10:15:30"));
//			dao.insert(articleVO);
		
//				update()
//			ArticleVO articleVO = new ArticleVO();
//			articleVO.setArti_topic("安安安安安安安安安安");
//			articleVO.setArti_content("ㄏㄏㄏㄏㄏㄏㄏㄏㄏ");
//			articleVO.setPo_time(java.sql.Timestamp.valueOf("2018-07-14 10:15:15"));
//			articleVO.setArti_no("A00011");
//			dao.update(articleVO);
		
//				delete()
//			dao.delete("A00011");
		
		
//				findByPrimaryKey()
//			ArticleVO articleVO = dao.findByPrimaryKey("A00001");
//			System.out.println("Arti_no : " + articleVO.getArti_no());
//			System.out.println("Arti_topic : " + articleVO.getArti_topic());
//			System.out.println("Mem_no : " + articleVO.getMem_no());
//			System.out.println("Arti_content : " + articleVO.getArti_content());
//			System.out.println("Po_time : " + articleVO.getPo_time());
		
		
//				getAll()
//			List<ArticleVO> list = dao.getAll();
//			for(ArticleVO articleVO : list) {
//				System.out.println("Arti_no : " + articleVO.getArti_no());
//				System.out.println("Arti_topic : " + articleVO.getArti_topic());
//				System.out.println("Mem_no : " + articleVO.getMem_no());
//				System.out.println("Arti_content : " + articleVO.getArti_content());
//				System.out.println("Po_time : " + articleVO.getPo_time());
//				System.out.println("=========================");
//			}
			
//				findByTopic()
		List<ArticleVO> list = dao.findByTopic("david");
		for(ArticleVO articleVO : list) {
			System.out.println("Arti_no : " + articleVO.getArti_no());
			System.out.println("Arti_topic : " + articleVO.getArti_topic());
			System.out.println("Mem_no : " + articleVO.getMem_no());
			System.out.println("Arti_content : " + articleVO.getArti_content());
			System.out.println("Po_time : " + articleVO.getPo_time());
			System.out.println("=========================");
		}
		
		
		
		System.out.println("ok");
		
	}

	
	
}

