package jumprunning;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//insert 만들어진 테이블에 값을 넣어 준다.
public class SaveScoreDB_Insert {
	String sql;
	//점수정보 저장
	public void setSaveScoreDB_Insert (SaveScore saveScore) {
		String driver = "com.mysql.jdbc.Driver"; //드라이버 로딩 
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false"; //데이터베이스를 연결하기 위한 스키마 url정보
		Connection conn = null; //데이터베이스와 연결이 성공되는 순간 객체가 생선된다. 성공여부를 알 수 있는 클래스다.
		Statement stmt = null; //데이터베이스에게 명령을 내리는 객체다.
		try {
			Class.forName(driver); //이름에 해당하는 클래스를 객체화 하겠다는 구문 
			conn = DriverManager.getConnection(url, "user2", "user2"); //DriverManager클래스는 커넥션을 얻어내기 위한 클래스다. 데이터베이스를 물리적으로 얻어내겠다라는 뜻이다.
			stmt = conn.createStatement(); //데이터베이스 연결이 성공된 객체를 통해서 스테이트먼트 객체를 만들라는 구문이다.
			
			//회원정보 저장 
			sql = "INSERT INTO SaveScore VALUES('" + saveScore.getDate() 
										 + "', '" + saveScore.getName() 
										 + "', " + saveScore.getScore() + ")"; 
										 
			
			int result = stmt.executeUpdate(sql); //성공하면 양수, 실패하면 음수가 나온다.
			String msg = result > -1 ?  "점수저장 successful" : "점수저장 fail";
			System.out.println(msg);
		} catch(Exception e) {
			System.out.println("데이터베이스 연결 실패!");
		} finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) stmt.close();
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}