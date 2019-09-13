package networkex;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
/*Ŭ���̾�Ʈ : ��������忡�� ������ �Է��ϰ� ���޹��� ������ �����Ͽ� ����Ѵ�. 
 *�ڵ鷯 : Ŭ���̾�Ʈ�� ������ ��û�ϴ� ������ �ǵ��� �ľ��ϰ� ������ ����. �������� Ŭ���̾�Ʈ�� ��û������ �����Ѵ�. 
 *���� ������ Ŭ���̾�Ʈ�� ��û�� �۾��� �ڵ鷯�� ���� �����ϹǷ� �ڵ鷯�� ������ Ŭ���̾�Ʈ�� �긴�� ������ �ϴ� Ŭ������. 
 *���� : �ڵ鷯�� �����ؼ� ������ Ŭ���̾�Ʈ�� ��û�� ���޹޾� �۾��� �����ϰ� �ڵ鷯�� ���� Ŭ���̾�Ʈ���� ������ �۾��� �����Ѵ�.*/ 
public class RunningManHandler extends Thread {
	private Socket s; //Ŭ���̾�Ʈ�� ����� �� �ִ� ���� 
	private BufferedReader i; 
	private PrintWriter o;
	private RunningManServer server;
	private String userId;
	private String userTeam;
	private String latitude;
	private String longitude;
	public RunningManHandler(RunningManServer server, Socket s) throws IOException {
		this.s = s; //Ŭ���̾�Ʈ
		this.server = server; //���� 
		InputStream ins = s.getInputStream(); //Ŭ���̾�Ʈ�� ����� �о�帰��.
		OutputStream os = s.getOutputStream(); //Ŭ���̾�Ʈ���� ����Ѵ�.
		i = new BufferedReader(new InputStreamReader(ins, "utf-8"));
		o = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
	}
	
	public void run() { //���� �����ڿ��� ���α׷��� ����ɶ� ���� ���޼ҵ带 ȣ���Ѵ�.
		String name = "";
		try {
			String user = i.readLine();
			StringTokenizer stMemberInfo = new StringTokenizer(user, "#");
			setUserId(stMemberInfo.nextToken()); //id
			setUserTeam(stMemberInfo.nextToken()); //��
			server.register(this); //������ ���ڽ��� ��ü�� ���Ϳ� ���� �ֱ�����
			server.broadcastId(getUserId() + "���� �湮�ϼ̽��ϴ�.");
//			broadcast(getUserId() + "���� �湮�ϼ̽��ϴ�."); //Ŭ���̾�Ʈ �� �Է��� �̸��� ������ ����
			while(true) {
				String msg = i.readLine(); //Ŭ���̾�Ʈ�� �޼����� �о�帰��.
				StringTokenizer st = new StringTokenizer(msg, "#"); //����ڸ�#�޼���
				String protocol = st.nextToken();
				if(protocol.equals("100")) {
					setLatitude(st.nextToken());
					setLongitude(st.nextToken());
					server.broadcast(); //�Ϲ�ä�� �޼���
					
				} else if (protocol.equals("200")) {
					System.out.println("dsf");
					setLatitude(st.nextToken());
					setLongitude(st.nextToken());
					broadcast(getUserId() +"-"+st.nextToken());
				}
//				if(st.countTokens() < 2) { //1:1 ä������ �ƴ��� Ȯ���ϴ� ����.
//					broadcast(name + " - " + msg); //�Ϲ�ä�� �޼���
//				} else {
//					if(st.hasMoreTokens()) { 
//						int clientUserName = Integer.parseInt(st.nextToken()); //����ó���ؾ���
//						System.out.println(clientUserName);
//						Vector<RunningManHandler> users = new Vector<RunningManHandler>();
//						RunningManHandler ch = (RunningManHandler)server.getHandler(clientUserName); //1:1 ä�� ������� ��ü�� ��ȯ�޾� �����Ѵ�.
//						users.add(this); //1:1 ä���� ��û�ϴ� �����. (�̱����� ����� ���������Ⱑ �ȴ�.)
//						users.add(ch);	//1:1 ä���� �޴� �����	
//						msg = st.nextToken();
//						System.out.println(msg);
//						server.broadcast(users, name + " - " + msg); //�����ε��� ��ε�ĳ��Ʈ�� 1:1����� ä�� ����� ������ �޼����� �����Ѵ�.
//					}
//				}
			}
			
//			while(run) {
//				name = i.readLine(); //Ŭ���̾�Ʈ�� �Է��� �̸��� �о�帰��.
//				if(server.nameOverlap(name)) {//�̸��ߺ� Ȯ�� 
//					setUserName(name);
//					server.register(this); //������ ���ڽ��� ��ü�� ���Ϳ� ���� �ֱ�����
//					broadcast(name + "���� �湮�ϼ̽��ϴ�."); //Ŭ���̾�Ʈ �� �Է��� �̸��� ������ ����
//					run = false;
//					while(true) {
//						String msg = i.readLine(); //������ ����� �о�帰��.
//						broadcast(name + " - " + msg); //Ŭ���̾�Ʈ�� �Է��� �޼����� ������ ����
//					}
//				} else {
//					println("�̹� ���ǰ� �ִ� �̸��Դϴ�.");
//				}
//			}
		} catch(Exception ex) {}
		server.unregister(this); //������ �ִ� �������� ��ü�� ���Ϳ��� �����.
		broadcast(getUserId() + "���� �����̽��ϴ�.");//������ Ŭ���̾�Ʈ�� �����ٰ� �����Ѵ�.
		try {
			i.close();
			o.close();
			s.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void println(String message) { //�������� ȣ���ؼ� �޼�������
		o.println(message); //Ŭ���ξ�Ʈ���� ������ �����ϴ� �޼����� �ѷ��ش�.
	}
	protected void broadcast(String message) {
		server.broadcast(message); //������ ��ε� ĳ��Ʈ�� ȣ���� �޼����� �����Ѵ�.
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userName) {
		this.userId = userName;
	}
	
	public String getUserTeam() {
		return userTeam;
	}

	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
