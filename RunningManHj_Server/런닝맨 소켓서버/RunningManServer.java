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
				Socket client = server.accept(); //���α׷��� ����ɶ����� ��� ������� ������ ���ش�.
				RunningManHandler c = new RunningManHandler(this, client);//�ڵ鷯���� ������ Ŭ���̾�Ʈ ������ �����Ѵ�.
				c.start(); //�ڵ鷯�� ���ǵ� ���޼ҵ带 ȣ��
			}
		} catch (Exception e) {}
	}
	
	//1:1ä�� ����� �˻�
	public Object getHandler(int index) {
		return handlers.elementAt(index);
	}
	
	//����� �߰� 
	public void register(RunningManHandler c) { 
		handlers.addElement(c); //�ڵ鷯�� ���������.
	}
	
	//����� ���� 
	public void unregister(Object o) { 
		handlers.removeElement(o);
	}
	
	//�̸��ߺ� Ȯ��Ex
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
			
	//�̸� �ߺ�Ȯ�� 
//	public boolean nameOverlap(String name) {
//		for(int i=0; i<handlers.size(); i++) {
//			if(handlers.get(i).getUserName().equals(name)) {
//				return false;
//			}
//		}
//		return true;
//	}
	//�������� 100 ���浵�� ���� 
	public void broadcast() { 
		synchronized(handlers) { //����ȭ ���ó�� 
			StringBuilder userInFo = new StringBuilder(); //String�� ����� ���̴� ������ StringBuilder�� �޸𸮻�뿡 ���뼺�� �ִ�.
			for(RunningManHandler user : handlers) {
				userInFo.append(user.getLatitude() + "|" + user.getLongitude() + "|" 
						+ user.getUserId() + "|" + user.getUserTeam() + "!");
			}
			int n = handlers.size();//���� �÷����� ��� ��ü�� ��� �ִ��� ��ȯ
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//������ ������ ��ü�� �����Ѵ�.
				try {
					c.println(userInFo.toString()); //�ڵ鷯�� ����Ʈ�޼ҵ� ȣ��
				} catch(Exception ex) {}
			}
		}
	}
	
	
	//���ӵ� ����ڵ��� �޽����� ����
	public void broadcast(String message) { 
		synchronized(handlers) { //����ȭ ���ó�� 
			StringBuilder userInFo = new StringBuilder(); //String�� ����� ���̴� ������ StringBuilder�� �޸𸮻�뿡 ���뼺�� �ִ�.
			for(RunningManHandler user : handlers) {
				userInFo.append(user.getLatitude() + "|" + user.getLongitude() + "|" 
						+ user.getUserId() + "|" + user.getUserTeam() + "!");
			}
			int n = handlers.size();//���� �÷����� ��� ��ü�� ��� �ִ��� ��ȯ
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//������ ������ ��ü�� �����Ѵ�.
				try {
					c.println(userInFo + "@" + message); //�ڵ鷯�� ����Ʈ�޼ҵ� ȣ��
					System.out.println(userInFo + "@" + message);
				} catch(Exception ex) {}
			}
		}
	}
	
	public void broadcastId(String message) { 
		synchronized(handlers) { //����ȭ ���ó�� 
			StringBuilder userInFo = new StringBuilder(); //String�� ����� ���̴� ������ StringBuilder�� �޸𸮻�뿡 ���뼺�� �ִ�.
			
			int n = handlers.size();//���� �÷����� ��� ��ü�� ��� �ִ��� ��ȯ
			for(int i=0; i<n; i++) {
				RunningManHandler c = handlers.elementAt(i);//������ ������ ��ü�� �����Ѵ�.
				try {
					c.println(message + "$" +"df"); //�ڵ鷯�� ����Ʈ�޼ҵ� ȣ��	
				} catch(Exception ex) {}
			}
		}
	}
	
	//1:1ä�ÿ� ��ε�ĳ��Ʈ 
	public void broadcast(Vector<RunningManHandler>users, String message) {
		synchronized(handlers) { //����ȭ ���ó�� 
			StringBuilder names = new StringBuilder(); //String�� ����� ���̴� ������ StringBuilder�� �޸𸮻�뿡 ���뼺�� �ִ�.
			
			for(Object user : handlers) {
				RunningManHandler ch = (RunningManHandler)user; 
				names.append(ch.getUserId() + "|"); //����ڵ��� �̸��� ����
			}
			
			int n = users.size();//���� �÷����� ��� ��ü�� ��� �ִ��� ��ȯ
			for(int i=0; i<n; i++) {
				RunningManHandler c = users.get(i);//���� ���� 1:1ä�� ����ڵ��� ������ �����Ѵ�.
				try {
					c.println(names + message); //�ڵ鷯�� ����Ʈ�޼ҵ� ȣ��. 1:1ä�� ����ڵ鿡�� �޼����� �����Ѵ�.
				} catch(Exception ex) {}
			}
		}	
	}
	
	public static void main(String[] args) {
		new RunningManServer(9830);
	}

}
