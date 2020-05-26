package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {
	
	public Article insert(Connection conn, Article article) throws SQLException {
		
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//pstmt의 readcount는 setInt 입력 빙식으로 수정했음.
		try {
			pstmt = conn.prepareStatement
			("insert into article (writer_id, writer_name, title, regdate, moddate, read_cnt) values (?,?,?,?,?,?)");
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4, toTimestamp(article.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));
			pstmt.setInt(6, 0);
			
			int insertedCount = pstmt.executeUpdate();
			
			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select last_insert_id() from article");
				if (rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Article(newNum, article.getWriter(), article.getTitle(), article.getRegDate(), article.getModifiedDate(),0);
				}
			}
			return null;
			
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}
	
	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	//전체 게시글 갯수 구하는 메서드
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
		}
	}
	
	//지정한 행 번호에 해당하는 게시글 목록 구하는 메서드
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article order by article_no desc limit ?, ?");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
			}
			return result;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	//rs 객체를 사용해 Article 객체 생성
	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"), new Writer(rs.getString("writer_id"), 
				rs.getString("writer_name")),rs.getString("title"), toDate(rs.getTimestamp("regdate")), 
				toDate(rs.getTimestamp("moddate")), rs.getInt("read_cnt"));
	}
	
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	//특정 번호에 해당하는 게시글 데이터 읽기
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			Article article = null;
			
			if (rs.next()) {
				article = convertArticle(rs);
			}
			return article;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	//조회 수 올리기
	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("update article set read_cnt = read_cnt +1 where article_no = ?")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}
	
	//게시글 제목 수정 기능
	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("update article set title = ?, moddate = now() where article_no = ?")) {
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
	
	//게시글 삭제 기능
	public int delete(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("delete from article where article_no = ?")) {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}
	
}
