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
/*클라이언트 : 사용자입장에서 정보를 입력하고 전달받은 정보를 가공하여 출력한다. 
 *핸들러 : 클라이언트가 서버에 요청하는 정보의 의도를 파악하고 구별한 다음. 서버에게 클라이언트의 요청사항을 전달한다. 
 *또한 서버가 클라이언트가 요청한 작업을 핸들러를 통해 전달하므로 핸들러는 서버와 클라이언트의 브릿지 역할을 하는 클래스다. 
 *서버 : 핸들러가 구별해서 전달한 클라이언트의 요청을 전달받아 작업을 수행하고 핸들러를 통해 클라이언트에게 수행한 작업을 전달한다.*/ 
public class RunningManHandler extends Thread {
	private Socket s; //클라이언트와 통신할 수 있는 소켓 
	private BufferedReader i; 
	private PrintWriter o;
	private RunningManServer server;
	private String userId;
	private String userTeam;
	private String latitude;
	private String longitude;
	public RunningManHandler(RunningManServer server, Socket s) throws IOException {
		this.s = s; //클라이언트
		this.server = server; //서버 
		InputStream ins = s.getInputStream(); //클라이언트의 출력을 읽어드린다.
		OutputStream os = s.getOutputStream(); //클라이언트에게 출력한다.
		i = new BufferedReader(new InputStreamReader(ins, "utf-8"));
		o = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
	}
	
	public void run() { //서버 생성자에서 프로그램이 종료될때 까지 런메소드를 호출한다.
		String name = "";
		try {
			String user = i.readLine();
			StringTokenizer stMemberInfo = new StringTokenizer(user, "#");
			setUserId(stMemberInfo.nextToken()); //id
			setUserTeam(stMemberInfo.nextToken()); //팀
			server.register(this); //서버의 내자신의 객체를 백터에 집어 넣기위함
			server.broadcastId(getUserId() + "님이 방문하셨습니다.");
//			broadcast(getUserId() + "님이 방문하셨습니다."); //클라이언트 가 입력한 이름을 서버에 전달
			while(true) {
				String msg = i.readLine(); //클라이언트의 메세지를 읽어드린다.
				StringTokenizer st = new StringTokenizer(msg, "#"); //사용자명#메세지
				String protocol = st.nextToken();
				if(protocol.equals("100")) {
					setLatitude(st.nextToken());
					setLongitude(st.nextToken());
					server.broadcast(); //일반채팅 메세지
					
				} else if (protocol.equals("200")) {
					System.out.println("dsf");
					setLatitude(st.nextToken());
					setLongitude(st.nextToken());
					broadcast(getUserId() +"-"+st.nextToken());
				}
//				if(st.countTokens() < 2) { //1:1 채팅인지 아닌지 확인하는 구문.
//					broadcast(name + " - " + msg); //일반채팅 메세지
//				} else {
//					if(st.hasMoreTokens()) { 
//						int clientUserName = Integer.parseInt(st.nextToken()); //예외처리해야함
//						System.out.println(clientUserName);
//						Vector<RunningManHandler> users = new Vector<RunningManHandler>();
//						RunningManHandler ch = (RunningManHandler)server.getHandler(clientUserName); //1:1 채팅 대상자의 객체를 반환받아 저장한다.
//						users.add(this); //1:1 채팅을 요청하는 사용자. (이구문을 지우면 쪽지보내기가 된다.)
//						users.add(ch);	//1:1 채팅을 받는 사용자	
//						msg = st.nextToken();
//						System.out.println(msg);
//						server.broadcast(users, name + " - " + msg); //오버로드한 브로드캐스트에 1:1사용자 채팅 사용자 정보와 메세지를 전달한다.
//					}
//				}
			}
			
//			while(run) {
//				name = i.readLine(); //클라이언트가 입력한 이름을 읽어드린다.
//				if(server.nameOverlap(name)) {//이름중복 확인 
//					setUserName(name);
//					server.register(this); //서버의 내자신의 객체를 백터에 집어 넣기위함
//					broadcast(name + "님이 방문하셨습니다."); //클라이언트 가 입력한 이름을 서버에 전달
//					run = false;
//					while(true) {
//						String msg = i.readLine(); //서버의 출력을 읽어드린다.
//						broadcast(name + " - " + msg); //클라이언트가 입력한 메세지를 서버에 전달
//					}
//				} else {
//					println("이미 사용되고 있는 이름입니다.");
//				}
//			}
		} catch(Exception ex) {}
		server.unregister(this); //서버에 있는 내자진의 객체를 백터에서 지운다.
		broadcast(getUserId() + "님이 나가셨습니다.");//서버에 클라이언트가 나갔다고 전달한다.
		try {
			i.close();
			o.close();
			s.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void println(String message) { //서버에서 호출해서 메세지전달
		o.println(message); //클라인언트에게 서버가 전달하는 메세지를 뿌려준다.
	}
	protected void broadcast(String message) {
		server.broadcast(message); //서버의 브로드 캐스트를 호출해 메세지를 전달한다.
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
