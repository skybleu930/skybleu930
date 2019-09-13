package networkex;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RunningManClient extends JFrame implements Runnable, ActionListener, ItemListener{
	
	private BufferedReader i;
	private PrintWriter o;
	private JTextArea output;
	private JTextField input1, input2;
	private JLabel label1, label2, label3;
	private JButton b1, b2;
	private Thread listener;
	private String host;
//	private String userName;
	private JScrollPane jScrollPane1, jScrollPane2;
	private CardLayout cards;
	private Panel p1, p2, p3, p4, p5, p6, p7, bottom;
	private ArrayList<String> data;
	private ArrayList<String> userName;
	List userList;
	int choiceUserNum;
	public RunningManClient(String server) {
		super("채팅 프로그램");
		cards = new CardLayout();
		host = server;
		
		//컴포넌트 초기화 시작
		output = new JTextArea();
		output.setEditable(false);
		jScrollPane1 = new JScrollPane(output);
		jScrollPane2 = new JScrollPane(userList);
		userList = new List();
		bottom = new Panel(new BorderLayout());
		p1 = new Panel(new BorderLayout());
		p2 = new Panel(new BorderLayout());
		p3 = new Panel(new BorderLayout());
		p4 = new Panel(new BorderLayout());
		p5 = new Panel(new BorderLayout());
		p6 = new Panel(new BorderLayout());
		p7 = new Panel(new BorderLayout());
		
		label1 = new JLabel("사용자 이름");
		label2 = new JLabel();
		label3 = new JLabel("접속자 목록 ", JLabel.CENTER);
		input1 = new JTextField();
		b1 = new JButton("   접속자 목록     ");
		b2 = new JButton("  닫   기   ");
		//컴포넌트 초기화 종료--//
		
		//접속자 목록 버튼
		p1.add(b1);
		p2.add(b2);
		p3.setLayout(cards);
		p3.add(p1);
		p3.add(p2);
		cards.show(p3, "p3");
		
		//접속자 목록 TextArea
		p4.add(label2); //접속자 목록 비활성화
		p5.add(label3, "North"); //접속자 목록 라벨
		p5.add(userList, "Center"); //접속자 리스트 
		p6.setLayout(cards);
		p6.add(p4);
		p6.add(p5);
		cards.show(p6, "p6");
		
		//메인 메세지 TextArea
		p7.add(jScrollPane1, "Center");
		p7.add(p6, "East");
		
		//메인 하단 메세지 입력란
		bottom.add(label1, "West");
		bottom.add(p3, "East");
		bottom.add(input1, "Center");
		
		//최종 레이아웃 
		add(p7, "Center");
		add(bottom, "South");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
		listener = new Thread(this);
		listener.start();
		
		//이벤트 적용
		input1.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		userList.addItemListener(this);
		choiceUserNum = -1; 
		
	}
	public void itemStateChanged(ItemEvent e) {
		choiceUserNum = userList.getSelectedIndex();
	}
	
	public void run() {
		try {
			Socket s = new Socket(host, 9830); //서버에 접속을 요청한다.
			InputStream ins = s.getInputStream(); //핸들러가  전달하는 메세지를 읽어드린다.
			OutputStream os = s.getOutputStream(); //핸들러에게 내메세지를 전달한다.
			i = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			o = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
			String delim = "|"; 
			StringTokenizer st;
			int index = 0;
			int dataSize = 0;
			while(true) {
				String line = i.readLine(); //핸들러가 전달하는 메세지를 읽어드린다.
				String mag = getMsg(line); //핸들러에게 전달받은 이름과 메세지를 메소드에서 분리해서 메세지만 반환받아 저장하는 구문.
				userList.clear(); //리스트를 초기화
				for(String name : userName)userList.add(name); //사용자의 이름을 리스트에 붙혀준다.
				output.append(mag + "\n"); 
				jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
				
			}
		} catch(IOException e) {}
	}
	
	//이름을 넣어주는 구문 
	public String getMsg(String str) {
		StringTokenizer	st = new StringTokenizer(str, "|");
		userName = new ArrayList<String>();
		String result = "";
		int userNameCnt = st.countTokens(); //토큰수
		while(st.hasMoreElements()) {
			if(userNameCnt-- != 1) //이름에 해당하는 토큰만 들어오게 하는 조건문
				userName.add(st.nextToken()); //이름
			else
				result = st.nextToken(); //메세지
		}
		return result;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object c = e.getSource();
		
		if(c == input1) {
			
			if(choiceUserNum != -1) {
				o.println(choiceUserNum + "#" + input1.getText()); //1:1채팅 대상을 핸들러에게  전달
			} else {
				o.println(input1.getText());  //핸들러에게 텍스트필드에 클라이언트가 입력한 메세지를 전달한다.
			}
			label1.setText("메세지");
			input1.setText("");
		}
		if(c == b1) { //접속자목록 버튼
			cards.next(p3); //접속자목록 카드레이아웃
			cards.next(p6); //접속자 목록 TextArea 카드레이아웃
		}
		if(c == b2) { //접속자목록 닫기 버튼
			cards.next(p3); //접속자목록 카드레이아웃
			cards.next(p6); //접속자 목록 TextArea 카드레이아웃
			choiceUserNum = -1;
		}
	}
	public static void main(String[] args) {
		if(args.length > 0) {
			new RunningManClient(args[0]);
		} else {
			new RunningManClient("localhost");
			//localhost
		}
	}

}
