package jumprunning;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//Select : 선택한 테이블에 컬럼 값을 가져오는 구문이다.
public class SaveScoreDB_SelectAll {
	Vector<SaveScore> rankingList = new Vector<SaveScore>();
	public SaveScoreDB_SelectAll() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false";
		Connection conn = null; //데이터베이스와 연결이 성공되는 순간 객체가 생선된다. 성공여부를 알 수 있는 클래스다.
		Statement stmt = null; //데이터베이스에게 명령을 내리는 객체다.
		ResultSet rs = null; //셀렉트문을 활용할때 꼭사용해야 하는 객체 
		try {
			rankingList.clear();
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "user2", "user2");
			stmt = conn.createStatement();
//			String sql = "SELECT * FROM SaveScore"; 
//			//질의문 지정한 TEST테이블로 부터 *모든 값을 가져와라는 뜻이고 ID, PW등 만들어 놓은 컬럼의 값만 가져 오게 할 수도 있다.
			String sql = "select * from SaveScore " + " order by SaveScore.Score DESC";
			rs = stmt.executeQuery(sql); //얻어진 레코드를 가져옴. 다른 메소드와 다르게 이메소드의 반환값은  ResultSet이다.
			while(rs.next()) {
//				String id = rs.getString(1); 
//				String pw = rs.getString(2);
				String date = rs.getString("DATE");
				String name = rs.getString("NAME"); 
				int score  = rs.getInt("SCORE");
				//getString(idx) 괄호안에 있는 idx 컬럼 인덱스이다. 자바와 다르게 첫번째 인덱스 값은 1부터 시작한다.
				//컬럼의 값이 정수면 getInt로 값을 불러온다.
				//인덱스 값외에도 지정된 컬럼이름을 이용해 해당하는 값을 가져올 수 도 있다.
				SaveScore saveScore = new SaveScore(date, name, score);
				rankingList.add(saveScore);
				setRankingList(rankingList);
			    
//			    int result = stmt.executeUpdate(sql); //성공하면 양수, 실패하면 음수가 나온다.
//			    String msg = result > -1 ?  "점수가져오기 successful" : "점수가져오기 fail";
//				System.out.println(msg);
			}
		} catch(Exception e) {
			System.out.println("데이터베이스 연결 실패!");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close(); 
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				//여는 순서와 반대로 닫아준다.
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void setRankingList(Vector<SaveScore> rankingList) {
		this.rankingList = rankingList;
	}
	public Vector<SaveScore> getRankingList() {
		return rankingList;
	}

}
