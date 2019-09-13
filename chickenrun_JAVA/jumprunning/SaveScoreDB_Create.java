package jumprunning;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveScoreDB_Create {

	public static void main(String[] args) {
		String driver = "com.mysql.jdbc.Driver"; //드라이버 로딩 
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false"; //데이터베이스를 연결하기 위한 url정보
		Connection conn = null; //데이터베이스와 연결이 성공되는 순간 객체가 생선된다. 성공여부를 알 수 있는 클래스다.
		Statement stmt = null; //데이터베이스에게 명령을 내리는 객체다.
		try {
			Class.forName(driver); //이름에 해당하는 클래스를 객체화 하겠다는 구문 
			conn = DriverManager.getConnection(url, "user2", "user2"); //DriverManager클래스는 커넥션을 얻어내기 위한 클래스다. 데이터베이스를 물리적으로 얻어내겠다라는 뜻이다.
			stmt = conn.createStatement(); //데이터베이스 연결이 성공된 객체를 통해서 스테이트먼트 객체를 만들라는 구문이다.
			String sql = "CREATE TABLE SaveScore("+ "DATE VARCHAR(30) NOT NULL, "
													+ "NAME VARCHAR(10) NOT NULL, "
													+ "SCORE INT(4) NOT NULL)";
			int result = stmt.executeUpdate(sql); //성공하면 양수, 실패하면 음수가 나온다.
			String msg = result > -1 ?  "successful" : " fail";
			System.out.println(msg);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
