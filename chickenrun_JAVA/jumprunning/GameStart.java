package jumprunning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class GameStart extends JFrame implements Runnable, ActionListener {
	private	int x = 200; // ���Ƹ� ��ǥ ����
	private	int y = 380; 
	private	int x2 = 600; //���� ��ǥ����1
	private	int x3 = 900; //���� ��ǥ����2
	private	int x4 = 1200; //���� ��ǥ����3
	private	int x5 = 1200; //�δ��� ��ǥ ����
	private	int x6 = 1200; //�Ҵߺ�����
	private	int x7 = 1200; //�� ��ǥ����
	private	int x8 = 1200; //�� ��ǥ����
	private	int x9 = 1200; //�� ��ǥ����
	private	int[] cornX, cornX2, cornY; //������ ��ǥ ����
	private	int xX, xX2, xX3, xX4, xX5, xX6, xX7, xX8, xX9; //ĳ���� ��ֹ� �̹��� ���� ���� 
	private	int num1, num2, num3, num4, num5, num6;
	private	int foxSpeed = 6, moleSpeed, moleSpeed2, noodleSpeed, bridSpeed, bridSpeed2; 
	private	int jump = 10;
	private	int score, cornScore; 
	private	int level = 1; //���� �ö󰡴� ����
	private	int characterChange; //ĳ���� ������ �˷��ִ� ���� 
	private	int protectCnt; //�Ͻù��� ���ѽð� ī��Ʈ
	private	Toolkit tool;
	private	Image character_Img, memori_Img, loading_Img, corn_Img, mole_Img, firechicken_Img, firechicken2_Img, 
				  firenoodle_Img, bird_Img, mainScreen_Img, gameStart_Img, ranking_Img; 
	private	Image[] map_Img, fox_Img, corns_Img;
	private	Graphics buffg; //���۸� ���� 
	private	Thread thread;
	private	Font scoreFont, gameOverFont; 
	private	JButton jbutton_Restart, jbutton_SaveScore, jbutton_MainScreen,  jbutton_Save, jbutton_Cancel,
					jbutton_Ranking, jbutton_End, jbutton_Confirm, jbutton_Notice;
	private	JLabel[] jlabel = new JLabel[10];
	private	Label [] label = new Label[20];
	private	JDialog jdialog_GameOver, jdialog_SaveScore, jdialog_Ranking, jdialog_Notice;
	private	JPanel jpanel_cornScore, jpanel_Name;
	private	JTextArea jtextArea = new JTextArea();
	private	JScrollPane scrollbar = new JScrollPane(jtextArea);
	private	JTextField jtextField_Name = new JTextField();
	private	Vector<SaveScore> rankingList = new Vector<SaveScore>();
	private	int imgCnt, loadingCnt;
	private	boolean ctrl = true, up, down; //Ű���� �Է� ó���� ���� ����
	private	boolean loadingCompletion; //�̹��� �ε� �ϷḦ �˷��ִ� ����.
	private	boolean mapRun = true;
	private	boolean protectRun; //�Ͻ� ���� ���� ����
	private	boolean firejump; //�Ӵ� ����
	private	boolean gameOverSignal = true; 
	//gameOverSignal ��������� ���ӿ��� �޼ҵ尡 ��� �ҷ������� ���� ���� �ϰ�  �ӻ��� ���·�  �������� ������ �ٽ� ������ �� �Ͻ� ���� ����Ǵ� ���� �����ϱ� ���� ����.
	private	boolean saveScoreSignal; //��������� ���� ���� �����ϰ� ���ִ� ����
	private	boolean mainScreenSignal = true; //����ȭ�� ȣ�� ���� 
	
	public GameStart() {
		
		tool = Toolkit.getDefaultToolkit();
		character_Img = tool.getImage(getClass().getResource("character.gif")); //���Ƹ��̹���
		firechicken_Img = tool.getImage(getClass().getResource("firechicken.gif")); //�һմ� �Ӵ��̹���
		firechicken2_Img = tool.getImage(getClass().getResource("firechicken2.png")); //�Ҵ��̹���
		bird_Img = tool.getImage(getClass().getResource("bird.png")); //�Ҵ��̹���
		loading_Img = tool.getImage(getClass().getResource("loading.gif")); //���̹��� 
		corn_Img = tool.getImage(getClass().getResource("corn.png")); //�������̹��� 
		mole_Img = tool.getImage(getClass().getResource("mole.png")); //ū���̹���
		firenoodle_Img = tool.getImage(getClass().getResource("firenoodle.png")); //�Ҵߺ�����
		mainScreen_Img = tool.getImage(getClass().getResource("mainScreen.png")); //����ȭ�� �̹���
		gameStart_Img = tool.getImage(getClass().getResource("gameStart.png")); //���ӽ��� �̹���
		ranking_Img = tool.getImage(getClass().getResource("ranking.png")); //��ŷ���� �̹��� 
		
		//����̹��� �ʱ�ȭ
		map_Img = new Image[121];
        Toolkit kit = Toolkit.getDefaultToolkit();
        for(int i = 1; i < map_Img.length; i++){
        	map_Img[i] = kit.getImage(getClass().getResource("mapimg_" + i + ".jpg"));
        }
        
        //�����̹��� �ʱ�ȭ
        fox_Img = new Image[4];
        for(int i = 1; i < fox_Img.length; i++) {
        	fox_Img[i] = kit.getImage(getClass().getResource("fox_"+ i + ".png"));
        }
        
        //�������̹��� �ʱ�ȭ 
        corns_Img = new Image[10];
        for(int i = 0; i < corns_Img.length; i++) {
        	corns_Img[i] = kit.getImage(getClass().getResource("corn.png"));
        }
        
        //������ x��ǥ�ʱ�ȭ
        int sum = 560;
        cornX = new int[10];
        for(int i = 0; i < cornX.length; i++) {
        	cornX[i] = (sum += 60);
        }
        
        //������ x����  �ʱ�ȭ
        cornX2 = new int[10];
        
        //������ y��ǥ �ʱ�ȭ;
        cornY = new int[10];
        for(int i = 0; i < cornY.length; i++) {
        	cornY[i] = 200;
        }
        
        //�����̹��� ���������� ���� ���� �ʱ�ȭ 
        num1 = ((int)(Math.random()*2)+1);
        num2 = ((int)(Math.random()*2)+1);
 	   	num3 = ((int)(Math.random()*2)+1);
 	   	
 	   	//�Ҵߺ��� �ð����� ���� ���� �ʱ�ȭ
 	   	num5 = 1;
 	   	
 	   	//�Ͻù��� �ð����� ���� ���� �ʱ�ȭ 
 	   	num6 = 1;
		
 	   	//��Ʈ �ʱ�ȭ 
 	   	scoreFont = new Font("", Font.BOLD, 18);
 	   	gameOverFont = new Font("", Font.BOLD, 30);
 	   	
 	   	//��ư �ʱ�ȭ 
 	    jbutton_Restart = new JButton(new ImageIcon(getClass().getResource("jbutton_Restart.png")));
 	    jbutton_SaveScore = new JButton(new ImageIcon(getClass().getResource("jbutton_SaveScore.png")));
 	    jbutton_MainScreen = new JButton(new ImageIcon(getClass().getResource("jbutton_MainScreen.png")));
 	    jbutton_Save = new JButton(new ImageIcon(getClass().getResource("jbutton_Save.png")));
 	    jbutton_Cancel = new JButton(new ImageIcon(getClass().getResource("jbutton_Cancel.png")));
 	    jbutton_Confirm = new JButton(new ImageIcon(getClass().getResource("jbutton_Confirm.png")));
 	    jbutton_Notice = new JButton(new ImageIcon(getClass().getResource("jbutton_Confirm.png")));
 	    jbutton_Restart.setBorderPainted(false);
 	    jbutton_Restart.setFocusPainted(false);
 	    jbutton_Restart.setContentAreaFilled(false);
 	    jbutton_SaveScore.setBorderPainted(false);
 	    jbutton_SaveScore.setFocusPainted(false);
 	    jbutton_SaveScore.setContentAreaFilled(false);
 	    jbutton_MainScreen.setBorderPainted(false);
 	    jbutton_MainScreen.setFocusPainted(false);
 	    jbutton_MainScreen.setContentAreaFilled(false);
 	    jbutton_Save.setBorderPainted(false);
 	   	jbutton_Save.setFocusPainted(false);
 	  	jbutton_Save.setContentAreaFilled(false);
 	 	jbutton_Cancel.setBorderPainted(false);
 		jbutton_Cancel.setFocusPainted(false);
 	 	jbutton_Cancel.setContentAreaFilled(false);
 	 	jbutton_Confirm.setBorderPainted(false);
 	 	jbutton_Confirm.setFocusPainted(false);
 	 	jbutton_Confirm.setContentAreaFilled(false);
 	 	jbutton_Notice.setBorderPainted(false);
 	 	jbutton_Notice.setFocusPainted(false);
 	 	jbutton_Notice.setContentAreaFilled(false);
 	    
 	    //j�� �ʱ�ȭ 
 	    for(int i = 0; i < jlabel.length; i++) {
 	    	jlabel[i] = new JLabel();
        }
 	    jlabel[0] = new JLabel(new ImageIcon(getClass().getResource("corn.png")));
 	    
 	    //�� �ʱ�ȭ 
 	    for(int i = 0; i < label.length; i++) {
 	    	label[i] = new Label("", Label.CENTER);
 	    }
 	    
 	    //���̾�α� �ʱ�ȭ
 	    jdialog_GameOver = new JDialog();
 	    jdialog_SaveScore = new JDialog();
 	    jdialog_Ranking = new JDialog();
 	    jdialog_Notice = new JDialog();
 	    
 	   
 	    //�ǳ� �ʱ�ȭ 
 	    jpanel_cornScore = new JPanel();
 	    jpanel_Name = new JPanel();
 	    
 	    //������ ���� 
		setSize(1200, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Ű���� �̺�Ʈ 
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_CONTROL : //��Ʈ��Ű ���� 
					//���Ƹ��� �� ����.
					if(characterChange == 0) { 
						if(y == 380) { //���Ƹ��� ���� �������� ���� ����.
							up = true;
							jump = 10;
						}
					
					//�Ҵ� �� �� ����.
					} else {
						
						//�Ҵ��� ctrlŰ�� �� �� �������� ������ ���Ƹ� ������ �Ȱ��� ����
						if(y == 380) { 
							up = true;
							jump = 10;
							firejump = false;
						}
						
						//�Ҵ���  y���� 80~350 ���� �϶� ��� ctrl Ű�� ����� ���� �� ������ �����Ǵ� ���౸��.
						if(y >= 80 && y <= 350) {
							up = true;
							jump = 20;
							firejump = true;
						}
					}
					break;
					
				case KeyEvent.VK_ESCAPE : //ESCŰ ���ӿ����޴� ȣ��
					gameOver();
					break;
				}
			}
			
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_CONTROL :
					//�Ҵ��� ���� ����.
					if(characterChange != 0) {
						//�Ҵ߿� y���� 100�̻� �� �� ctrl Ű�� ������ ���� ���� ������ ��������.
						if(firejump && y >= 80 && y <= 350) { 
							up = false;
							firejump = false;
							down = true;
							jump = 5;
						}
					}
				}	
			}
		});	
		
		//���콺 �̺�Ʈ 
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
							&& e.getY() >= 330 && e.getY() <= 375) { //���ӽ��� 
					mainScreenSignal = false; 
				}
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 390 && e.getY() <= 435) { //��ŷ����
					ranking();
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 330 && e.getY() <= 375) { //���ӽ��� 
					mainScreenSignal = false;
				}
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 390 && e.getY() <= 435) { //��ŷ����
					ranking();
				}
			}
		});
		
		//������� ���� ������
		new Thread(this){
            public void run (){
	            while(true){
	            	repaint();
	            	
	            	if(characterChange != 0) { //�Ҵ����� ���� ������ ���� 
    					protectCnt = 3; //�Ҵ� ���ѽð� ����� ���Ƹ� �Ͻ� �������� �ð� �ʱ�ȭ 
    					if(gameOverSignal) {
    						protectRun = false; //�Ҵ��� �� �Ͻù��� ����
    					}
    				} else { //�ӵ��� ���� ������ Ž������ �ʵ��� ���������.
    					//�Ҵߺ��� ���ѽð� ����ÿ� ����.
        				if(characterChange == 0 && protectCnt <= 3 && protectCnt > 0) {
        					if(protectCnt == 3) {
        						firejump = false;
          						down = false;
    							y = 380; //�Ҵߺ��� ����� ���Ƹ� ��ġ �ʱ�ȭ 
        					}
        					protectRun = true; //���Ƹ� �Ͻ� ���� ����.
        					
        				} else if(gameOverSignal){ //�Ӵ��� ���¿��� �׾��� �� ���Ƹ��� �Ǹ鼭 �������� ����Ǵ� ���� �����ϴ� ���ǹ� 
        					protectRun = false; //���Ƹ� �Ͻù��� �ð� ���� ��. ���� ����
        				}
    				}
	            }
            }
        }.start(); 
		
        //����̹��� �����̴� ȿ�� ������
        new Thread(this){
            public void run (){
                try{
                    while(true){
                       if(mapRun) {
	                       imgCnt++;
	                       if(! (imgCnt < 121)) imgCnt = 1; //�׸� ����̹��� ó������ �ʱ�ȭ    
                       }
                       Thread.sleep(30);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start(); 
        
        //���Ƹ� �Ҵ� ���� ���ѽð� ������
        new Thread(this){
        	public void run (){
        		try{
        			while(true) {
        				if(characterChange != 0) { //�Ҵ����� ���� ������ ���� 
        					characterChange -= num5; //�Ҵߺ��� ���ѽð� ����.
        				} else { //�ӵ��� ���� ������ Ž������ �ʵ��� ���������.
        					//�Ҵߺ��� ���ѽð� ����ÿ� ����.
	        				if(characterChange == 0 && protectCnt <= 3 && protectCnt > 0) {
	        					protectCnt -= num6; //�Ͻù��� ���ѽð� ����.
	        				}
        				}
        				Thread.sleep(1000);
        			}
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }.start(); 
        
        //��ֹ� �����̴� ȿ�� ������
        new Thread(this){
        	public synchronized void run (){
        		try{
        			while(true){
        				if(!mainScreenSignal) {
	        				loadingCnt++; //�ε� ī��Ʈ
	        				if(loadingCnt > 600) {
	        					loadingCompletion = true; //�̹��� �ε��Ϸ�
	        					
	        					//���� �����̴� �ӵ�
	        					x2 -= foxSpeed; 
	        					x3 -= foxSpeed;
	        					x4 -= foxSpeed; 
	        					
	        					//�δ��� �����̴� �ӵ�
	        					x5 -= moleSpeed;
	        					x9 -= moleSpeed2;
	        					
	        					//������ �����̴� �ӵ� 
	        					for(int i = 0; i < cornX.length; i++) {
	        						cornX[i] -= foxSpeed;
	        					}
	        					
	        					//�Ӵߺ����� �����̴� �ӵ� 
								if (num4 == 3) {
	        						x6 -= noodleSpeed;
	        					}
	        					
	        					//�� �����̴� �ӵ� 
	        					x7 -= bridSpeed;
	        					x8 -= bridSpeed2;
	        					
	        					//���Ƹ� �̹��� ����
	        					xX = x+50;
	        					
	        					//�δ��� �̹��� ���� 
	        					xX5 = x5+50;
	        					xX9 = x9+50;
	        					
	        					//�Ӵߺ����� �̹��� ���� 
	        					xX6 = x6+50;
	        					
	        					//�� �̹��� ���� 
	        					xX7 = x7+82;
	        					xX8 = x8+82;
	        					
	        					//���� ����
	        					score += level;
	        					
	        					//������ ���� ���Ƹ��� ���� �� �ִ� ���� �̹��� ����(���� �̹��� ���� ������� 30~50�� ��) 
	        					//���� num1
	        					if(num1 == 1) 
	        						xX2 = x2 + 34; //���� �� ������ �� 
	        					if(num1 == 2) 
	        						xX2 = x2 + 98; //���� �� ������ �� 
	        					if(num1 == 3) 
	        						xX2 = x2 + 125; //���� �� ������ �� 
	        					
	        					//���� num2
	        					if(num2 ==1)
	        						xX3 = x3 + 34;
	        					if(num2 ==2)
	        						xX3 = x3 + 98;
	        					if(num2 ==3)
	        						xX3 = x3 + 125;
	        					
	        					//���� num3
	        					if(num3 == 1)
	        						xX4 = x4 + 34;
	        					if(num3 == 2)
	        						xX4 = x4 + 98;
	        					if(num3 == 3)
	        						xX4 = x4 + 125;
	        				}
	        				
	        				//������ ���� �� �ִ� ���� �ʱ�ȭ
	        				for(int i = 0; i < cornX.length; i++) {
		    			        cornX2[i] = (cornX[i] + 40);
	    					}
	        				
	        				//���Ƹ��� �������� �Ծ����� ���� ����.
	        				for(int i = 0; i < cornX.length; i++) {
		        				if(cornX[i] <= xX && cornX[i] >= x 
		        						|| cornX2[i] <= xX && cornX2[i] >= x) { //�������� ���Ƹ� ��ġ�� �������� ����.
		        					if(y >= 130 && y <= 240) { //���Ƹ��� �������� �� ������ ��ġ�� �ִٸ� ����  
		        							//�������� ȭ�鿡�� ���ش�.
		        							cornY[i] = 0; //���ǹ� ������ ���� Y���� 0�� �ش�.
			        						cornX[i] = 0;
			        						
		        						if(cornY[i] == 0 && cornX[i] <= 0) {
		        							cornScore += level; //�������� ���� ������ŭ ���� �ʱ�ȭ
		        							
		        							//������ ������ ��ǥ�ʱ�ȭ
		        							cornX[i] = 1200;
		        							cornY[i] = 200;
		        							
		        							//���Ƹ��� ���� �������鿡 ��ġ���� �ʱ�ȭ �Ǿ��� �� ��ġ�� �ʰ� ���ִ� ����.
		        							cornPosition(i); //������ ��ġ�ʱ�ȭ �޼ҵ� 
		        						}
		        					} 
		        				} else { 
	        						if(cornX[i] <= 0) { //���Ƹ��� ���� ���� �������� �������� ���� ���� �� ���� �ȴ�.
	        							cornX[i] = 1200;
	        							
	    							    //�������� ��� �������鿡 ��ġ���� �ʱ�ȭ �Ǿ��� �� ��ġ�� �ʰ� ���ִ� ����.
	        							cornPosition(i); //������ ��ġ�ʱ�ȭ �޼ҵ� 
	    	        				}
	    						}
	        				}
	        				
	        				//���������� �յ� ������ 60�̻� ���̳����� ���������ִ� ����.
	        				for(int i = 0; i < cornX.length; i++) {
	        					cornPosition(i); //������ ��ġ�ʱ�ȭ �޼ҵ� 
	        				}
	        				
	        				//���Ƹ��� �Ҵߺ������� �Ծ��� �� ���౸��.
	        				if(x6 <= xX && x6 >= x 
	        						|| xX6 <= xX && xX6 >= x) {  //�Ҵߺ������� ���Ƹ� ��ġ�� �������� ����.
	        					if(y >= 80 && y <= 189) {
	        						x6 = 0; //�Ҵߺ����� �����ӿ��� ���ֱ� 
	        						characterChange = 10; //�Ҵߺ��Žð� ���� �ʱ�ȭ
	        					}	
	        				}
	        				
	        				//���찡 �������� ���� ���� �� �ٽ� �������� ��ġ �ʱ�ȭ 
	        				if(x2 <= 0) {
	        					x2 = 1200;
	        					if(score >= 1500) { //���ھ ���ǹ��� �ش�� ��. 3���� ���쵵 ���´�.
	        						num1 = ((int)(Math.random()*3)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					} else {
	        						num1 = ((int)(Math.random()*2)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					}	
	        					//ù ��° ��ġ ���찡 �������� ���� �� �� ��������  ���ǹ��� ���ھ �Ѿ��� �� �δ����� ���� ��Ÿ����.
	        					if(score >= 500)
	        						moleSpeed = 8; //�δ��� ���ǵ�
	        					if(score >= 500)
	        						bridSpeed = 10; //�� ���ǵ� 
	        					if(score >= 1000 && num3 != 3)
	        						moleSpeed2 = 18; //�δ��� ���ǵ�
	        				}
	        				if(x3 <= 0) {
	        					x3 = 1200;
	        					if(score >= 1500) { //���ھ ���ǹ��� �ش�� ��. 3���� ���쵵 ���´�.
	        						num2 = ((int)(Math.random()*3)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					} else {
	        						num2 = ((int)(Math.random()*2)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					}
	        				}
	        				if(x4 <= 0) {
	        					x4 = 1200;
	        					if(score >= 1500) { //���ھ ���ǹ��� �ش�� ��. 3���� ���쵵 ���´�.
	        						num3 = ((int)(Math.random()*3)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					} else {
	        						num3 = ((int)(Math.random()*2)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
	        					}
	        					//�� ��° ��ġ ���찡 �������� ���� �� �� �Ҵߺ������� ��Ÿ���� Ȯ�� ���� ����.
	            				if(x6 == 1200) {
	            					num4 = ((int)(Math.random()*5)+1); 
	            					noodleSpeed = 6;
	            				}
	            				//�� ��° ��ġ ���찡 �������� ���� �� �� ��������  �ι�° ���� ��Ÿ����.
	            				if(score >= 1000)
	        						bridSpeed2 = 17; //�� ���ǵ� 
	            				
	        				}
	        				
	        				//�Ҵߺ������� �������� ���� ���� �� �ٽ� �������� ��ġ �ʱ�ȭ 
	        				if(x6 == 0) {
	        					x6 = 1200;
	        					noodleSpeed = 0;
	        				}
	        				
	        				//�δ����� �������� ���� ���� �� �ٽ� �������� ��ġ �ʱ�ȭ 
	        				if(x5 <= 0) {
	        					x5 = 1200;
	        					moleSpeed = 0;
	        				}
	        				if(x9 <= 0) {
	        					x9 = 1200;
	        					moleSpeed2 = 0;
	        				}
	        				
	        				//���� �������� ���� ���� �� �ٽ� �������� ��ġ �ʱ�ȭ 
	        				if(x7 <= 0) {
	        					x7 = 1200;
	        					bridSpeed = 0;
	        				}
	        				if(x8 <= 0) {
	        					x8 = 1200;
	        					bridSpeed2 = 0;
	        				}
	        				
	        				if(!protectRun) {
		        				//���Ƹ��� ���츦 �������� �� �������� ���� 
		        				if(x2 <= xX && x2 >= x || xX2 <= xX && xX2 >= x) { //���� ��ġ�� x2 y2�� ��
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        						 
		        					}
		        				}	
		    				
		        				if(x3 <= xX && x3 >= x || xX3 <= xX && xX3 >= x) { //���� ��ġ�� x3 y3�� ��
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
		    				
		        				if(x4 <= xX && x4 >= x || xX4 <= xX && xX4 >= x) { //���� ��ġ�� x4 y4�� ��
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
		        				
		        				//���Ƹ��� �δ����� �������� �� �������� ���� 
		        				if(x5 <= xX && x5 >= x || xX5 <= xX && xX5 >= x) { 
		        					if(y <= 380 && y >= 330) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
		        				if(x9 <= xX && x9 >= x || xX9 <= xX && xX9 >= x) { 
		        					if(y <= 380 && y >= 330) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
		        				
	        					//���Ƹ��� ���� �������� �� �������� ���� 
		        				if(x7 <= xX && x7 >= x || xX7 <= xX && xX7 >= x) { //ù ��° �� 
		        					if(y <= 150 && y >= 100) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
		        				if(x8 <= xX && x8 >= x || xX8 <= xX && xX8 >= x) { //�� ��° �� 
		        					if(y <= 150 && y >= 100) {
		        						saveScoreSignal= true; //���� ����� ���� ���尡���ϰ� �ϴ� ����
		        						gameOver();
		        					}
		        				}	
	        				}
        				}
        				Thread.sleep(20);
        			}
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }.start(); 
        
        //Ű���� ��Ʈ�� ����Ʈ ���� 
        thread = new Thread(this); 
		thread.start();
		
		//��ư �̺�Ʈ ����.
		jbutton_Restart.addActionListener(this);
		jbutton_SaveScore.addActionListener(this);
		jbutton_MainScreen.addActionListener(this);
		jbutton_Save.addActionListener(this);
		jbutton_Cancel.addActionListener(this);
		jbutton_Confirm.addActionListener(this);
		jbutton_Notice.addActionListener(this);
		
	} //--����������
	
	//Ű���� ��Ʈ�� 
	public void run() {
		try{ 
			while(true){
				if(ctrl) {
					
					//���Ƹ��� �� ���� ����.
					if(characterChange == 0) { 
						if(up) { //��Ʈ��Ű�� �������� ����Ǵ� ����
							y -= jump;
							if(y <= 80) {
								up = false;
								down = true;
							}
						}
						
						if(down) { //���� ���� �ִ�ġ�� ����� �� ���������ϴ� ���� ����
							y += jump;
							if(y == 380) {
								down = false;
							}
						}
					
					//�Ҵ��� �� ���� ����. 	
					} else { 
						if(up) { //��Ʈ��Ű�� �ѹ� ������ �� ����Ǵ� ����
							y -= jump;
							if(y <= 80) {
								up = false;
								down = true;
							}
							
							//�Ҵ��� ������ ������ �� ��� �ٿ�����༭ �Ӵ��� �ѹ��� �ö��� ���ϵ��� ���װ��� �ش�.
							if(firejump && y >= 80 && y <= 350) { 
								up = false;
								down = true;
								jump = 5;
							}
						}
						
						if(down) { //���� ���� �ִ�ġ�� ��� �����ö� ���� ����
							y += jump;
							if(y == 380) {
								down = false;
							}
						}
					}
				}
				if(characterChange != 0 && firejump) {
					Thread.sleep(25); 
				} else {
					Thread.sleep(15); 
				}
			}
		}catch (Exception e){}
	}
	
	//��ư �̺�Ʈ ����.
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == jbutton_Restart) { //���� �ٽ� ���� �޴� ��ư
			jdialog_GameOver.setVisible(false);
			jtextField_Name.setText("");
			gameRestart(); 
		}
		if(obj == jbutton_SaveScore) { //���� ���� �޴� ��ư
			if(saveScoreSignal) { //���� ����ÿ��� ���������� �����ϵ��� �ϴ� ���ǹ�.
				saveScore();
			} else { //�������� ���� ���� �˸�â
				label[12].setText("���������� ���� ���� �Ŀ�");
				label[13].setText("�� ���� �����մϴ�.");
				jdialog_Notice.setLayout(new GridLayout(6, 1));
				jdialog_Notice.getContentPane().setBackground(new Color(255, 153, 0));
				jdialog_Notice.add(jlabel[1]); //����
				jdialog_Notice.add(label[12]); //��Ʈ
				jdialog_Notice.add(label[13]); //��Ʈ
				jdialog_Notice.add(jlabel[2]); //����
				jdialog_Notice.add(jbutton_Notice); //Ȯ�ι�ư
				jdialog_Notice.add(jlabel[3]); //����
				jdialog_Notice.setSize(300, 300);
				jdialog_Notice.setLocationRelativeTo(null);
				jdialog_Notice.setVisible(true);
			}
		}
		if(obj == jbutton_Notice) { //�������� ���� ���� �˸�â Ȯ�� ��ư
			jdialog_Notice.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_Save) { //���� ���� ��ư 
			Date date = new Date();
			SimpleDateFormat fdate = new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss");
			obj = fdate.format(date);
			String dateString = obj.toString(); //��¥ �ð� ����  
			String name = jtextField_Name.getText(); //�̸� 
			SaveScore saveScore = new SaveScore(dateString, name, score + (cornScore*10)); //���� ���� Ŭ����
			SaveScoreDB_Insert saveScoreDB_Insert = new SaveScoreDB_Insert(); //���� DB���� ��ü ����
			saveScoreDB_Insert.setSaveScoreDB_Insert(saveScore); //���� DB���� �޼ҵ� ȣ��
			saveScoreSignal = false; //������ �ߺ����� ���ϵ��� ���� �ʱ�ȭ 
			jdialog_SaveScore.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_Cancel) { //���� ���� ��� ��ư
			jdialog_SaveScore.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_MainScreen) { //���� ȭ�� �޴� ��ư
			jdialog_GameOver.setVisible(false);
			loadingCnt = 0;
			loadingCompletion = false;
			gameRestart();
			mainScreenSignal = true;
		}
		if(obj == jbutton_Confirm) { //��ŷ ���� Ȯ�� ��ư
			jdialog_Ranking.setVisible(false);
		}
	}
	
	//������ ��ġ �ʱ�ȭ �޼ҵ�
	public void cornPosition(int i) {
		for(int j = 0; j < cornX.length; j++) {
			if(i != j) {
				//���� �������� �տ� �ִ� �������� ��ġ�� ���ؼ� ���� 60���� ������ x��ǥ�� ������ �ڷ� 60��ŭ ����ش�.
				boolean	result1 = (cornX[i] - cornX[j]) < 60 && (cornX[i] - cornX[j]) >= 0;
				if(result1) {
					cornX[i] += 60;
				}
				
				//���� �������� �ڿ� �ִ� �������� ��ġ�� ���ؼ�  ������ 60���� ������ x��ǥ�� ������  ������ 60��ŭ ����ش�.
				boolean	result2 = (cornX[i] - cornX[j]) > -60 && (cornX[i] - cornX[j]) <= 0;
				if(result2) {
					cornX[i] -= 60;
				}
			}
		}
	}
	
	//���� �ٽ� ����
	public void gameRestart() {
		
		//������ �ߺ����� ���ϵ��� ���� �ʱ�ȭ 
		saveScoreSignal = false; 
		
		//ĳ���� ���Ƹ��� ����
		characterChange = 0;
		
		//���Ƹ� �� ��ֹ� �ʱ�ȭ 
		x = 200;
		y = 380;
		x2 = 600;
		x3 = 900;
		x4 = 1200;
		x5 = 1200;
		x6 = 1200;
		x7 = 1200;
		x8 = 1200;
		x9 = 1200;
		
		//���� �����̴� �ӵ� �ʱ�ȭ 
		foxSpeed = 6;
		
		//���� �� ���� ���� �ʱ�ȭ 
		score = 0;
		cornScore = 0;
		level = 1;
		
		//����̹��� �����̱� 
		mapRun = true;
		
		//Ű���� ����
		ctrl = true;
		
		//���� ������ �ʱ�ȭ
		if(num1 == 3) {
			num1 = ((int)(Math.random()*2)+1); //ù ��° ��ġ ����. �̹������� ������ ���� ����.
		}
		if(num2 == 3) {
			num2 = ((int)(Math.random()*2)+1); //�� ��° ��ġ ����. �̹������� ������ ���� ����.
		}
		if(num3 == 3) {
			num3 = ((int)(Math.random()*2)+1); //�� ��° ��ġ ����. �̹������� ������ ���� ����.
		}
		
		//�Ҵߺ��� �ð����� ���� ���� �ʱ�ȭ
 	   	num5 = 1;
 	   	
 	   	//�Ͻù��� �ð����� ���� ���� �ʱ�ȭ 
 	   	num6 = 1;
		
 	   	//�Ͻù��� ���ѽð� �ʱ�ȭ;
 	   	protectCnt = 0; //�Ͻù��� ���ѽð� �ʱ�ȭ�� 
 	   	
 	   	//���ǹ������� ���� ���ӿ��� ��ȣ �ʱ�ȭ 
 	   	gameOverSignal = true; //��ȣ������ 
 	   	
 	   	//���Ƹ� ��ȣ���� �ʱ�ȭ 
		protectRun = false;
		
		//������ x��ǥ �ʱ�ȭ 
		int sum = 560;
        for(int i = 0; i < cornX.length; i++) {
        	cornX[i] = sum += 60;
        }
        
        //������ y��ǥ �ʱ�ȭ 
        for(int i = 0; i < cornY.length; i++) {
        	cornY[i] = 200;
        }
	}
	
	//�������� ȭ��
	public void saveScore() {
		//����
		label[1].setText("�� �� �� ��");
		label[1].setFont(gameOverFont);
		
		//������ ���� �ǳ� 
		jlabel[0].setHorizontalAlignment(JLabel.RIGHT);
		label[2].setText(cornScore + "  x  " + 10 + "  =  ");
		label[3].setText("" + (cornScore*10));
		label[3].setAlignment(Label.LEFT);
		jpanel_cornScore.setLayout(new GridLayout(1, 3));
		jpanel_cornScore.setBackground(new Color(255, 153, 0));
		jpanel_cornScore.add(jlabel[0]); //������ �̹���
		jpanel_cornScore.add(label[2]);
		jpanel_cornScore.add(label[3]);
		
		//�̸� �Է� �ǳ�
		label[9].setText("��  ��");
		jpanel_Name.setLayout(new BorderLayout());
		jpanel_Name.setBackground(new Color(255, 153, 0));
		jpanel_Name.add(label[9], BorderLayout.WEST);
		jpanel_Name.add(jtextField_Name, BorderLayout.CENTER);
		jpanel_Name.add(label[10], BorderLayout.EAST);
		jpanel_Name.add(label[11], BorderLayout.SOUTH);
		
		//���� �� ������ 
		label[4].setText("���� : "+ score);
		label[5].setText("������ : "+ (score + (cornScore*10)));
		jdialog_SaveScore.setLayout(new GridLayout(7, 1));
		jdialog_SaveScore.getContentPane().setBackground(new Color(255, 153, 0));
		jdialog_SaveScore.add(label[1]); //����
		jdialog_SaveScore.add(jpanel_cornScore); //������ ����
		jdialog_SaveScore.add(label[4]); //����
		jdialog_SaveScore.add(label[5]); //������
		jdialog_SaveScore.add(jpanel_Name); //�̸��Է� �ǳ� 
		jdialog_SaveScore.add(jbutton_Save); //�����ư
		jdialog_SaveScore.add(jbutton_Cancel); //��ҹ�ư
		jdialog_SaveScore.setSize(300, 400);
		jdialog_SaveScore.setLocationRelativeTo(null);
		jdialog_SaveScore.setVisible(true);
	}
	
	//��ŷ
	public void ranking() {
		SaveScoreDB_SelectAll saveScoreDB_SelectAll = new SaveScoreDB_SelectAll(); //DB���� ��� ������ �������� ��ü
		rankingList = saveScoreDB_SelectAll.getRankingList(); //��� ������ �����ͼ� rankingList �÷��ǿ� ����
		jtextArea.setText("");
		jtextArea.setBackground(new Color(255, 153, 0));
		label[6].setText("�� ŷ �� ��");
		label[6].setFont(scoreFont);
		for(int i = 0; i < rankingList.size(); i++) {
			jtextArea.append("�Ͻ� : " + rankingList.get(i).getDate() +"\n" +
					  "�̸� : " + rankingList.get(i).getName() +"\n" + 
					  "���� : " + rankingList.get(i).getScore() +"\n" + 
					  "------------------------------------------------------" +"\n");
		}
		jtextArea.setCaretPosition(0);
		jdialog_Ranking.setLayout(new BorderLayout());
		jdialog_Ranking.getContentPane().setBackground(new Color(255, 153, 0));
		jdialog_Ranking.add(label[6], BorderLayout.NORTH);
		jdialog_Ranking.add(jbutton_Confirm, BorderLayout.SOUTH);
		jdialog_Ranking.add(scrollbar, BorderLayout.CENTER);
		jdialog_Ranking.add(label[7], BorderLayout.WEST);
		jdialog_Ranking.add(label[8], BorderLayout.EAST);
		jdialog_Ranking.setSize(300, 400);
		jdialog_Ranking.setLocationRelativeTo(null);
		jdialog_Ranking.setVisible(true);
	}
	
	//���� ����� ��� ����
	public void gameOver() {
		//�������
		foxSpeed = 0;
		moleSpeed = 0;
		moleSpeed2 = 0;
		noodleSpeed = 0;
		bridSpeed = 0;
		bridSpeed2 = 0;
		jump = 0;
		level = 0;
 	   	num5 = 0;
 	   	num6 = 0;
 	    protectRun = true; //ĳ���Ͱ� ��ֹ��� ��Ƽ� ������� ���� ���ӿ��� �޼ҵ尡 ��� ȣ��Ǵ� ���� ���� ���� true�ش�.
 	    gameOverSignal = false; //��ȣ���� �����ϴ� ���� �����ش�.
		mapRun = false;
		ctrl = false;
		
		//�˸�â 
		label[0].setText("GAME OVER");
		label[0].setFont(gameOverFont);
		jdialog_GameOver.setLayout(new GridLayout(4, 1));
		jdialog_GameOver.getContentPane().setBackground(new Color(255, 153, 0));
		jdialog_GameOver.add(label[0]);
		jdialog_GameOver.add(jbutton_Restart);
		jdialog_GameOver.add(jbutton_SaveScore);
		jdialog_GameOver.add(jbutton_MainScreen);
		jdialog_GameOver.setSize(300, 400);
		jdialog_GameOver.setLocationRelativeTo(null);
		jdialog_GameOver.setVisible(true);
	}
	
	//�׸��� �׸��� �޼ҵ�
	public void paint(Graphics g) {
		//���� ������
//		g.clearRect(0, 0, f_width, f_height);
//		g.drawImage(mapImg, 0, 0, this);//���� ȭ��(g)���� ������ũ(buffg)�� �׷��� �̹���(buffImage)�� �ű�.
//		g.drawImage(characterImg, x, y, this);//���� ȭ��(g)���� ������ũ(buffg)�� �׷��� �̹���(buffImage)�� �ű�.
		memori_Img = createImage(this.getWidth(), this.getHeight()); //�����̹��� 
		buffg = memori_Img.getGraphics(); //�̹����� �׸��� ���� �׷��� ��ü�� ���´�.
		
		buffg.drawImage(map_Img[imgCnt], 0, 0, this); //��	
		
		buffg.drawImage(mole_Img, x5, 400, this); //�δ���
		buffg.drawImage(mole_Img, x9, 400, this); //�δ���
		
		if(characterChange == 0) {
			buffg.drawImage(character_Img, x, y, this); //���Ƹ�
		} else {
			if(characterChange >= 9) {
				buffg.drawImage(firechicken2_Img, x, y, this); //�һմ� �Ӵ�
				buffg.drawImage(firenoodle_Img, 220, 40, this); //�Ҵߺ�����
				buffg.setColor(Color.WHITE);
				buffg.setFont(scoreFont);
				buffg.drawString(""+ characterChange , 280, 70); //�Ҵ�ȿ�� �����Ű�
			} else {
				buffg.drawImage(firechicken_Img, x, y, this); //�Ӵ�
				buffg.drawImage(firenoodle_Img, 220, 40, this); //�Ҵߺ�����
				buffg.setColor(Color.WHITE);
				buffg.setFont(scoreFont);
				buffg.drawString(""+ characterChange , 280, 70); //�Ҵ�ȿ�� �����Ű�
			}
		}	
		
		buffg.drawImage(fox_Img[num1], x2, 360, this); //�����̹��� ��ġ1
		buffg.drawImage(fox_Img[num2], x3, 360, this); //�����̹��� ��ġ2
		buffg.drawImage(fox_Img[num3], x4, 360, this); //�����̹��� ��ġ3
		
		buffg.drawImage(firenoodle_Img, x6, 140, this); //�Ҵߺ�����
		
		buffg.drawImage(bird_Img, x7, 100, this); //�� 
		buffg.drawImage(bird_Img, x8, 100, this); //��2
		
		for(int i = 0; i < corns_Img.length; i++) {
			buffg.drawImage(corns_Img[i], cornX[i], cornY[i], this); //������ �̹��� 
		}
		
		buffg.setColor(Color.WHITE);
		buffg.setFont(scoreFont);
		buffg.drawImage(corn_Img, 100, 50, this); 
		buffg.drawString(" X " + cornScore, 140, 75); //������ ������
		buffg.drawString("�� �� : " + score, 100, 115); //run ������
		
		if(protectRun && protectCnt != 0 && characterChange == 0) { //�Ҵ߿��� ���̸��� ���ư��� �� ����.
			buffg.setColor(Color.WHITE);
			buffg.setFont(scoreFont);
			buffg.drawString("�Ͻ� ���� : "+ protectCnt , x -10, y - 10); //�Ҵ߿��� ���̸��� ���ư��� �� �����ð�
		}
		
		if(mainScreenSignal) { //����ȭ�� ȣ��
			g.drawImage(mainScreen_Img, 0, 0, this); 
			g.drawImage(gameStart_Img, 480, 330, this); 
			g.drawImage(ranking_Img, 480, 390, this); 
		} else { //���ӽ��۽� ȣ��
			if(loadingCompletion) { //�̹��� �ε��Ϸ�� ����.
				g.drawImage(memori_Img, 0, 0, this); //���Ƹ�, ����, �� �̹��� ��.
				//���� ȭ��(g)���� ������ũ(buffg)�� �׷��� �̹���(buffImage)�� �ű�.
			} else {
				g.drawImage(loading_Img, 0, 0, this);//�ε��̹���
			}
		}
	}
	
	public static void main(String[] args) {
		new GameStart();
	}

}

