package networkex;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class RunningManServer {
	private Vector<RunningManHandler> handlers;
	public RunningManServer(int port) {
		try {
			ServerSocket server = new ServerSocket(port);
			handlers = new Vector<RunningManHandler>();
			System.out.println("ChatServer is reaky.");
			while(true) {
				Socket client = server.accept(); //프로그램이 종료될때까지 계속 사용자의 수락을 해준다.
				RunningManHandler c = new RunningManHandler(this, client);//핸들러에게 서버와 클라이언트 정보를 전달한다.
				c.start(); //핸들러에 정의된 런메소드를 호출
			}
		} catch (Exception e) {}
	}
	
	//1:1채팅 대상자 검색
	public Object getHandler(int index) {
		return handlers.elementAt(index);
	}
	
	//사용자 추가 
	public void register(RunningManHandler c) { 
		handlers.addElement(c); //핸들러를 집어넣은다.
	}
	
	//사용자 삭제 
	public void unregister(Object o) { 
		handlers.removeElement(o);
	}
	
	//이름중복 확인Ex
//	public boolean findByName(String name) {
//		for(Object user : handlers) {
//			RunningManHandler ch = (RunningManHandler)user;
//			String userName = ch.getUserName();
//			if(userName.equals(name)) {
//				return true;
//			}
//		}
//		return false;
//	}
			
	//이름 중복확인 
//	public boolean nameOverlap(String name) {
//		for(int i=0; i<handlers.size(); i++) {
//			if(handlers.get(i).getUserName().equals(name)) {
//				return false;
//			}
//		}
//		return true;
//	}
	//프로토콜 100 위경도값 공유 
	public void broadcast() { 
		synchronized(handlers) { //동기화 블록처리 
			StringBuilder userInFo = new StringBuilder(); //String과 기능의 차이는 없으나 StringBuilder는 메모리사용에 유용성이 있다.
			for(RunningManHandler user : handlers) {
				userInFo.append(user.getLatitude() + "|" + user.getLongitude() + "|" 
						+ user.getUserId() + "|" + user.getUserTeam() + "!");
			}
			int n = handlers.size();//백터 컬렉션의 몇개의 객체가 들어 있는지 반환
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//지정된 백터의 객체를 저장한다.
				try {
					c.println(userInFo.toString()); //핸들러의 프린트메소드 호출
				} catch(Exception ex) {}
			}
		}
	}
	
	
	//접속된 사용자들의 메시지를 공유
	public void broadcast(String message) { 
		synchronized(handlers) { //동기화 블록처리 
			StringBuilder userInFo = new StringBuilder(); //String과 기능의 차이는 없으나 StringBuilder는 메모리사용에 유용성이 있다.
			for(RunningManHandler user : handlers) {
				userInFo.append(user.getLatitude() + "|" + user.getLongitude() + "|" 
						+ user.getUserId() + "|" + user.getUserTeam() + "!");
			}
			int n = handlers.size();//백터 컬렉션의 몇개의 객체가 들어 있는지 반환
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//지정된 백터의 객체를 저장한다.
				try {
					c.println(userInFo + "@" + message); //핸들러의 프린트메소드 호출
					System.out.println(userInFo + "@" + message);
				} catch(Exception ex) {}
			}
		}
	}
	
	public void broadcastId(String message) { 
		synchronized(handlers) { //동기화 블록처리 
			StringBuilder userInFo = new StringBuilder(); //String과 기능의 차이는 없으나 StringBuilder는 메모리사용에 유용성이 있다.
			
			int n = handlers.size();//백터 컬렉션의 몇개의 객체가 들어 있는지 반환
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//지정된 백터의 객체를 저장한다.
				try {
					c.println(message + "$" +"df"); //핸들러의 프린트메소드 호출	
				} catch(Exception ex) {}
			}
		}
	}
	
	//1:1채팅용 브로드캐스트 
	public void broadcast(Vector<RunningManHandler>users, String message) {
		synchronized(handlers) { //동기화 블록처리 
			StringBuilder names = new StringBuilder(); //String과 기능의 차이는 없으나 StringBuilder는 메모리사용에 유용성이 있다.
			
			for(Object user : handlers) {
				RunningManHandler ch = (RunningManHandler)user; 
				names.append(ch.getUserId() + "|"); //사용자들의 이름을 저장
			}
			
			int n = users.size();//백터 컬렉션의 몇개의 객체가 들어 있는지 반환
			for(int i=0; i<n; i++) {
				RunningManHandler c = users.get(i);//전달 받은 1:1채팅 사용자들의 정보를 저장한다.
				try {
					c.println(names + message); //핸들러의 프린트메소드 호출. 1:1채팅 사용자들에게 메세지를 전달한다.
				} catch(Exception ex) {}
			}
		}	
	}
	
	public static void main(String[] args) {
		new RunningManServer(9830);
	}

}
