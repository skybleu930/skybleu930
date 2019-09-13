package jumprunning;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//insert ������� ���̺� ���� �־� �ش�.
public class SaveScoreDB_Insert {
	String sql;
	//�������� ����
	public void setSaveScoreDB_Insert (SaveScore saveScore) {
		String driver = "com.mysql.jdbc.Driver"; //����̹� �ε� 
		String url = "jdbc:mysql://localhost:3306/user2?useSSL=false"; //�����ͺ��̽��� �����ϱ� ���� ��Ű�� url����
		Connection conn = null; //�����ͺ��̽��� ������ �����Ǵ� ���� ��ü�� �����ȴ�. �������θ� �� �� �ִ� Ŭ������.
		Statement stmt = null; //�����ͺ��̽����� ����� ������ ��ü��.
		try {
			Class.forName(driver); //�̸��� �ش��ϴ� Ŭ������ ��üȭ �ϰڴٴ� ���� 
			conn = DriverManager.getConnection(url, "user2", "user2"); //DriverManagerŬ������ Ŀ�ؼ��� ���� ���� Ŭ������. �����ͺ��̽��� ���������� ���ڴٶ�� ���̴�.
			stmt = conn.createStatement(); //�����ͺ��̽� ������ ������ ��ü�� ���ؼ� ������Ʈ��Ʈ ��ü�� ������ �����̴�.
			
			//ȸ������ ���� 
			sql = "INSERT INTO SaveScore VALUES('" + saveScore.getDate() 
										 + "', '" + saveScore.getName() 
										 + "', " + saveScore.getScore() + ")"; 
										 
			
			int result = stmt.executeUpdate(sql); //�����ϸ� ���, �����ϸ� ������ ���´�.
			String msg = result > -1 ?  "�������� successful" : "�������� fail";
			System.out.println(msg);
		} catch(Exception e) {
			System.out.println("�����ͺ��̽� ���� ����!");
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