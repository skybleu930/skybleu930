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
		super("ä�� ���α׷�");
		cards = new CardLayout();
		host = server;
		
		//������Ʈ �ʱ�ȭ ����
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
		
		label1 = new JLabel("����� �̸�");
		label2 = new JLabel();
		label3 = new JLabel("������ ��� ", JLabel.CENTER);
		input1 = new JTextField();
		b1 = new JButton("   ������ ���     ");
		b2 = new JButton("  ��   ��   ");
		//������Ʈ �ʱ�ȭ ����--//
		
		//������ ��� ��ư
		p1.add(b1);
		p2.add(b2);
		p3.setLayout(cards);
		p3.add(p1);
		p3.add(p2);
		cards.show(p3, "p3");
		
		//������ ��� TextArea
		p4.add(label2); //������ ��� ��Ȱ��ȭ
		p5.add(label3, "North"); //������ ��� ��
		p5.add(userList, "Center"); //������ ����Ʈ 
		p6.setLayout(cards);
		p6.add(p4);
		p6.add(p5);
		cards.show(p6, "p6");
		
		//���� �޼��� TextArea
		p7.add(jScrollPane1, "Center");
		p7.add(p6, "East");
		
		//���� �ϴ� �޼��� �Է¶�
		bottom.add(label1, "West");
		bottom.add(p3, "East");
		bottom.add(input1, "Center");
		
		//���� ���̾ƿ� 
		add(p7, "Center");
		add(bottom, "South");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
		listener = new Thread(this);
		listener.start();
		
		//�̺�Ʈ ����
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
			Socket s = new Socket(host, 9830); //������ ������ ��û�Ѵ�.
			InputStream ins = s.getInputStream(); //�ڵ鷯��  �����ϴ� �޼����� �о�帰��.
			OutputStream os = s.getOutputStream(); //�ڵ鷯���� ���޼����� �����Ѵ�.
			i = new BufferedReader(new InputStreamReader(ins, "utf-8"));
			o = new PrintWriter(new OutputStreamWriter(os, "utf-8"), true);
			String delim = "|"; 
			StringTokenizer st;
			int index = 0;
			int dataSize = 0;
			while(true) {
				String line = i.readLine(); //�ڵ鷯�� �����ϴ� �޼����� �о�帰��.
				String mag = getMsg(line); //�ڵ鷯���� ���޹��� �̸��� �޼����� �޼ҵ忡�� �и��ؼ� �޼����� ��ȯ�޾� �����ϴ� ����.
				userList.clear(); //����Ʈ�� �ʱ�ȭ
				for(String name : userName)userList.add(name); //������� �̸��� ����Ʈ�� �����ش�.
				output.append(mag + "\n"); 
				jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
				
			}
		} catch(IOException e) {}
	}
	
	//�̸��� �־��ִ� ���� 
	public String getMsg(String str) {
		StringTokenizer	st = new StringTokenizer(str, "|");
		userName = new ArrayList<String>();
		String result = "";
		int userNameCnt = st.countTokens(); //��ū��
		while(st.hasMoreElements()) {
			if(userNameCnt-- != 1) //�̸��� �ش��ϴ� ��ū�� ������ �ϴ� ���ǹ�
				userName.add(st.nextToken()); //�̸�
			else
				result = st.nextToken(); //�޼���
		}
		return result;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object c = e.getSource();
		
		if(c == input1) {
			
			if(choiceUserNum != -1) {
				o.println(choiceUserNum + "#" + input1.getText()); //1:1ä�� ����� �ڵ鷯����  ����
			} else {
				o.println(input1.getText());  //�ڵ鷯���� �ؽ�Ʈ�ʵ忡 Ŭ���̾�Ʈ�� �Է��� �޼����� �����Ѵ�.
			}
			label1.setText("�޼���");
			input1.setText("");
		}
		if(c == b1) { //�����ڸ�� ��ư
			cards.next(p3); //�����ڸ�� ī�巹�̾ƿ�
			cards.next(p6); //������ ��� TextArea ī�巹�̾ƿ�
		}
		if(c == b2) { //�����ڸ�� �ݱ� ��ư
			cards.next(p3); //�����ڸ�� ī�巹�̾ƿ�
			cards.next(p6); //������ ��� TextArea ī�巹�̾ƿ�
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
