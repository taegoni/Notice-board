package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*import java.sql.Statement;*/

public class UserDAO {
	
		private Connection conn;
		private PreparedStatement pstmt;
		private ResultSet rs;
		
		
		public UserDAO() {
			try {
				String jdbcURL = "jdbc:mysql://localhost:3306/bbs";
				String jdbcID = "root";
				String jdbcPassword = "root";  
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(jdbcURL, jdbcID, jdbcPassword);

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		/*
		public int login(String userID, String userPassword) {
		    try {
		        // 정상적인 로그인 쿼리
		        String query = "SELECT * FROM USER WHERE userID = '" + userID + "' AND userPassword = '" + userPassword + "'";
		        
		        Statement statement = conn.createStatement();
		        ResultSet resultSet = statement.executeQuery(query);
		        
		        // 사용자가 입력한 아이디와 비밀번호가 일치하는 경우
		        if (resultSet.next()) {
		            return 1; // 로그인 성공
		        } else {
		            return -1; // 아이디 또는 비밀번호가 틀림
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return -2; // 데이터베이스 오류
		}
		*/

		/* sql 안전한코드*/
		public int login(String userID, String userPassword) {
			String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
			try {
				 pstmt = conn.prepareStatement(SQL);
				 pstmt.setString(1, userID);
				 rs = pstmt.executeQuery();
				 if (rs.next()) {
					 if(rs.getString(1).equals(userPassword)) {
						 return 1; //로그인 성공
					 }
					 else 
						 return  0; //비밀번호 불일치
				 }
				 return -1; // 아이디가 없음
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -2; // 데이터베이스 오류
		}
		
		
		
		public int join(User user) {
			String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
			try {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, user.getUserID());
				pstmt.setString(2, user.getUserPassword());
				pstmt.setString(3, user.getUserName());
				pstmt.setString(4, user.getUserGender());
				pstmt.setString(5, user.getUserEmail());
				return pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; // 데이터베이스 오류
		}
}
