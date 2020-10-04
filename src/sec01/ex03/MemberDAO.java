package sec01.ex03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
//	private static final String driver = "com.mysql.cj.jdbc.Driver";
//	private static final String url = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
//	private static final String user = "daelimcafe";
//	private static final String pwd = "dc1234";
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	// 생성자 호출시 톰캣 DataSource를 가져옴
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();  // 고정
			Context envContext = (Context) ctx.lookup("java:/comp/env");  // 고정
			dataFactory = (DataSource) envContext.lookup("jdbc:mysql");  // 톰캣에서 지정한 데이터소스를 가져옴. (context.xml에 <Resource>태그 name속성의 이름과 동일해야함)
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
//			connDB();
			con = dataFactory.getConnection();  // DataSource를 이용해 db에 연결
			String query = "select * from t_member ";
			System.out.println(query);
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//	private void connDB() {
//		try {
//			Class.forName(driver);
//			System.out.println("Oracle 드라이버 로딩 성공");
//			con = DriverManager.getConnection(url, user, pwd);
//			System.out.println("Connection 생성 성공");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}