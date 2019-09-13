package jumprunning;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

//Select : ������ ���̺� �÷� ���� �������� �����̴�.
public class SaveScoreDB_SelectAll {
	Vector<SaveScore> rankingList = new Vector<SaveScore>();
	public SaveScoreDB_SelectAll() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false";
		Connection conn = null; //�����ͺ��̽��� ������ �����Ǵ� ���� ��ü�� �����ȴ�. �������θ� �� �� �ִ� Ŭ������.
		Statement stmt = null; //�����ͺ��̽����� ����� ������ ��ü��.
		ResultSet rs = null; //����Ʈ���� Ȱ���Ҷ� ������ؾ� �ϴ� ��ü 
		try {
			rankingList.clear();
			Class.forName(driver);
			conn = DriverManager.getConnection(url, "user2", "user2");
			stmt = conn.createStatement();
//			String sql = "SELECT * FROM SaveScore"; 
//			//���ǹ� ������ TEST���̺�� ���� *��� ���� �����Ͷ�� ���̰� ID, PW�� ����� ���� �÷��� ���� ���� ���� �� ���� �ִ�.
			String sql = "select * from SaveScore " + " order by SaveScore.Score DESC";
			rs = stmt.executeQuery(sql); //����� ���ڵ带 ������. �ٸ� �޼ҵ�� �ٸ��� �̸޼ҵ��� ��ȯ����  ResultSet�̴�.
			while(rs.next()) {
//				String id = rs.getString(1); 
//				String pw = rs.getString(2);
				String date = rs.getString("DATE");
				String name = rs.getString("NAME"); 
				int score  = rs.getInt("SCORE");
				//getString(idx) ��ȣ�ȿ� �ִ� idx �÷� �ε����̴�. �ڹٿ� �ٸ��� ù��° �ε��� ���� 1���� �����Ѵ�.
				//�÷��� ���� ������ getInt�� ���� �ҷ��´�.
				//�ε��� ���ܿ��� ������ �÷��̸��� �̿��� �ش��ϴ� ���� ������ �� �� �ִ�.
				SaveScore saveScore = new SaveScore(date, name, score);
				rankingList.add(saveScore);
				setRankingList(rankingList);
			    
//			    int result = stmt.executeUpdate(sql); //�����ϸ� ���, �����ϸ� ������ ���´�.
//			    String msg = result > -1 ?  "������������ successful" : "������������ fail";
//				System.out.println(msg);
			}
		} catch(Exception e) {
			System.out.println("�����ͺ��̽� ���� ����!");
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close(); 
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				//���� ������ �ݴ�� �ݾ��ش�.
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
