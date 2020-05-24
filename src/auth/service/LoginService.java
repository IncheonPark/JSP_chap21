package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class LoginService {
	
	private MemberDao memberDao = new MemberDao();
	
	public User login(String id, String password) {
		
		try(Connection conn = ConnectionProvider.getConnection()) {
			Member member = memberDao.selectById(conn, id);
			
			//아이디 조회가 안됨
			if (member == null) {
				throw new LoginFailException();
			}
			//비밀번호가 매치가 안됨
			if (!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			//둘다 패스 시 User객체 리턴
			return new User(member.getId(), member.getName());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

}
