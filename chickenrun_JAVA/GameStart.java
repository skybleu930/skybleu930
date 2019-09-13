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
	private	int x = 200; // 병아리 좌표 변수
	private	int y = 380; 
	private	int x2 = 600; //여우 좌표변수1
	private	int x3 = 900; //여우 좌표변수2
	private	int x4 = 1200; //여우 좌표변수3
	private	int x5 = 1200; //두더지 좌표 변수
	private	int x6 = 1200; //불닭볶음면
	private	int x7 = 1200; //새 좌표변수
	private	int x8 = 1200; //새 좌표변수
	private	int x9 = 1200; //새 좌표변수
	private	int[] cornX, cornX2, cornY; //옥수수 좌표 변수
	private	int xX, xX2, xX3, xX4, xX5, xX6, xX7, xX8, xX9; //캐릭터 장애물 이미지 부피 변수 
	private	int num1, num2, num3, num4, num5, num6;
	private	int foxSpeed = 6, moleSpeed, moleSpeed2, noodleSpeed, bridSpeed, bridSpeed2; 
	private	int jump = 10;
	private	int score, cornScore; 
	private	int level = 1; //점수 올라가는 기준
	private	int characterChange; //캐릭터 변신을 알려주는 변수 
	private	int protectCnt; //일시무적 제한시간 카운트
	private	Toolkit tool;
	private	Image character_Img, memori_Img, loading_Img, corn_Img, mole_Img, firechicken_Img, firechicken2_Img, 
				  firenoodle_Img, bird_Img, mainScreen_Img, gameStart_Img, ranking_Img; 
	private	Image[] map_Img, fox_Img, corns_Img;
	private	Graphics buffg; //버퍼를 변수 
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
	private	boolean ctrl = true, up, down; //키보드 입력 처리를 위한 변수
	private	boolean loadingCompletion; //이미지 로딩 완료를 알려주는 변수.
	private	boolean mapRun = true;
	private	boolean protectRun; //일시 무적 적용 변수
	private	boolean firejump; //붉닭 점프
	private	boolean gameOverSignal = true; 
	//gameOverSignal 게임종료시 게임오버 메소드가 계속 불려나가는 것을 방지 하고  붉상인 상태로  죽은다음 게임을 다시 시작할 때 일시 무적 적용되는 것을 방지하기 위한 변수.
	private	boolean saveScoreSignal; //게임종료시 점수 저장 가능하게 해주는 변수
	private	boolean mainScreenSignal = true; //메인화면 호출 변수 
	
	public GameStart() {
		
		tool = Toolkit.getDefaultToolkit();
		character_Img = tool.getImage(getClass().getResource("character.gif")); //병아리이미지
		firechicken_Img = tool.getImage(getClass().getResource("firechicken.gif")); //불뿜는 붉닭이미지
		firechicken2_Img = tool.getImage(getClass().getResource("firechicken2.png")); //불닭이미지
		bird_Img = tool.getImage(getClass().getResource("bird.png")); //불닭이미지
		loading_Img = tool.getImage(getClass().getResource("loading.gif")); //맵이미지 
		corn_Img = tool.getImage(getClass().getResource("corn.png")); //옥수수이미지 
		mole_Img = tool.getImage(getClass().getResource("mole.png")); //큰맵이미지
		firenoodle_Img = tool.getImage(getClass().getResource("firenoodle.png")); //불닭볶음면
		mainScreen_Img = tool.getImage(getClass().getResource("mainScreen.png")); //메인화면 이미지
		gameStart_Img = tool.getImage(getClass().getResource("gameStart.png")); //게임시작 이미지
		ranking_Img = tool.getImage(getClass().getResource("ranking.png")); //랭킹보기 이미지 
		
		//배경이미지 초기화
		map_Img = new Image[121];
        Toolkit kit = Toolkit.getDefaultToolkit();
        for(int i = 1; i < map_Img.length; i++){
        	map_Img[i] = kit.getImage(getClass().getResource("mapimg_" + i + ".jpg"));
        }
        
        //여우이미지 초기화
        fox_Img = new Image[4];
        for(int i = 1; i < fox_Img.length; i++) {
        	fox_Img[i] = kit.getImage(getClass().getResource("fox_"+ i + ".png"));
        }
        
        //옥수수이미지 초기화 
        corns_Img = new Image[10];
        for(int i = 0; i < corns_Img.length; i++) {
        	corns_Img[i] = kit.getImage(getClass().getResource("corn.png"));
        }
        
        //옥수수 x좌표초기화
        int sum = 560;
        cornX = new int[10];
        for(int i = 0; i < cornX.length; i++) {
        	cornX[i] = (sum += 60);
        }
        
        //옥수수 x범위  초기화
        cornX2 = new int[10];
        
        //옥수수 y좌표 초기화;
        cornY = new int[10];
        for(int i = 0; i < cornY.length; i++) {
        	cornY[i] = 200;
        }
        
        //여우이미지 랜덤적용을 위한 난수 초기화 
        num1 = ((int)(Math.random()*2)+1);
        num2 = ((int)(Math.random()*2)+1);
 	   	num3 = ((int)(Math.random()*2)+1);
 	   	
 	   	//불닭변신 시간제한 감소 기준 초기화
 	   	num5 = 1;
 	   	
 	   	//일시무적 시간제한 감소 기준 초기화 
 	   	num6 = 1;
		
 	   	//폰트 초기화 
 	   	scoreFont = new Font("", Font.BOLD, 18);
 	   	gameOverFont = new Font("", Font.BOLD, 30);
 	   	
 	   	//버튼 초기화 
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
 	    
 	    //j라벨 초기화 
 	    for(int i = 0; i < jlabel.length; i++) {
 	    	jlabel[i] = new JLabel();
        }
 	    jlabel[0] = new JLabel(new ImageIcon(getClass().getResource("corn.png")));
 	    
 	    //라벨 초기화 
 	    for(int i = 0; i < label.length; i++) {
 	    	label[i] = new Label("", Label.CENTER);
 	    }
 	    
 	    //다이얼로그 초기화
 	    jdialog_GameOver = new JDialog();
 	    jdialog_SaveScore = new JDialog();
 	    jdialog_Ranking = new JDialog();
 	    jdialog_Notice = new JDialog();
 	    
 	   
 	    //판넬 초기화 
 	    jpanel_cornScore = new JPanel();
 	    jpanel_Name = new JPanel();
 	    
 	    //프레임 설정 
		setSize(1200, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//키보드 이벤트 
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_CONTROL : //컨트롤키 점프 
					//병아리일 때 실행.
					if(characterChange == 0) { 
						if(y == 380) { //병아리가 땅에 있을때만 점프 적용.
							up = true;
							jump = 10;
						}
					
					//불닭 일 때 실행.
					} else {
						
						//불닭이 ctrl키를 한 번 눌렀을때 기존에 병아리 점프와 똑같이 실행
						if(y == 380) { 
							up = true;
							jump = 10;
							firejump = false;
						}
						
						//불닭이  y값이 80~350 사이 일때 계속 ctrl 키를 누루고 있을 때 점프가 유지되는 실행구문.
						if(y >= 80 && y <= 350) {
							up = true;
							jump = 20;
							firejump = true;
						}
					}
					break;
					
				case KeyEvent.VK_ESCAPE : //ESC키 게임오버메뉴 호출
					gameOver();
					break;
				}
			}
			
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_CONTROL :
					//불닭일 때만 적용.
					if(characterChange != 0) {
						//불닭에 y값이 100이상 일 때 ctrl 키를 누르던 것을 때면 밑으로 내려간다.
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
		
		//마우스 이벤트 
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
							&& e.getY() >= 330 && e.getY() <= 375) { //게임시작 
					mainScreenSignal = false; 
				}
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 390 && e.getY() <= 435) { //랭킹보기
					ranking();
				}
			}
			public void mouseReleased(MouseEvent e) {
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 330 && e.getY() <= 375) { //게임시작 
					mainScreenSignal = false;
				}
				if(mainScreenSignal && e.getX() >= 480 && e.getX() <= 695
						&& e.getY() >= 390 && e.getY() <= 435) { //랭킹보기
					ranking();
				}
			}
		});
		
		//변경사항 적용 쓰레드
		new Thread(this){
            public void run (){
	            while(true){
	            	repaint();
	            	
	            	if(characterChange != 0) { //불닭으로 변신 했을때 실행 
    					protectCnt = 3; //불닭 제한시간 종료시 병아리 일시 무적적용 시간 초기화 
    					if(gameOverSignal) {
    						protectRun = false; //불닭일 때 일시무적 해제
    					}
    				} else { //속도를 위해 이프를 탐색하지 않도록 엘스를사용.
    					//불닭변신 제한시간 종료시에 실행.
        				if(characterChange == 0 && protectCnt <= 3 && protectCnt > 0) {
        					if(protectCnt == 3) {
        						firejump = false;
          						down = false;
    							y = 380; //불닭변신 종료시 병아리 위치 초기화 
        					}
        					protectRun = true; //병아리 일시 무적 적용.
        					
        				} else if(gameOverSignal){ //붉닭인 상태에서 죽었을 때 병아리로 되면서 무적상태 적용되는 것을 방지하는 조건문 
        					protectRun = false; //병아리 일시무적 시간 종료 후. 무적 해제
        				}
    				}
	            }
            }
        }.start(); 
		
        //배경이미지 움이이는 효과 쓰레드
        new Thread(this){
            public void run (){
                try{
                    while(true){
                       if(mapRun) {
	                       imgCnt++;
	                       if(! (imgCnt < 121)) imgCnt = 1; //그릴 배경이미지 처음으로 초기화    
                       }
                       Thread.sleep(30);
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }.start(); 
        
        //병아리 불닭 변신 제한시간 쓰레드
        new Thread(this){
        	public void run (){
        		try{
        			while(true) {
        				if(characterChange != 0) { //불닭으로 변신 했을때 실행 
        					characterChange -= num5; //불닭변신 제한시간 실행.
        				} else { //속도를 위해 이프를 탐색하지 않도록 엘스를사용.
        					//불닭변신 제한시간 종료시에 실행.
	        				if(characterChange == 0 && protectCnt <= 3 && protectCnt > 0) {
	        					protectCnt -= num6; //일시무적 제한시간 실행.
	        				}
        				}
        				Thread.sleep(1000);
        			}
        		}catch(InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }.start(); 
        
        //장애물 움직이는 효과 쓰레드
        new Thread(this){
        	public synchronized void run (){
        		try{
        			while(true){
        				if(!mainScreenSignal) {
	        				loadingCnt++; //로딩 카운트
	        				if(loadingCnt > 600) {
	        					loadingCompletion = true; //이미지 로딩완료
	        					
	        					//여우 움직이는 속도
	        					x2 -= foxSpeed; 
	        					x3 -= foxSpeed;
	        					x4 -= foxSpeed; 
	        					
	        					//두더지 움직이는 속도
	        					x5 -= moleSpeed;
	        					x9 -= moleSpeed2;
	        					
	        					//옥수수 움직이는 속도 
	        					for(int i = 0; i < cornX.length; i++) {
	        						cornX[i] -= foxSpeed;
	        					}
	        					
	        					//붉닭볶음면 움직이는 속도 
								if (num4 == 3) {
	        						x6 -= noodleSpeed;
	        					}
	        					
	        					//새 움직이는 속도 
	        					x7 -= bridSpeed;
	        					x8 -= bridSpeed2;
	        					
	        					//병아리 이미지 범위
	        					xX = x+50;
	        					
	        					//두더지 이미지 범위 
	        					xX5 = x5+50;
	        					xX9 = x9+50;
	        					
	        					//붉닭볶음면 이미지 범위 
	        					xX6 = x6+50;
	        					
	        					//새 이미지 범위 
	        					xX7 = x7+82;
	        					xX8 = x8+82;
	        					
	        					//점수 변수
	        					score += level;
	        					
	        					//난수에 따라 병아리가 피할 수 있는 여우 이미지 범위(실제 이미지 가로 사이즈보다 30~50덜 줌) 
	        					//난수 num1
	        					if(num1 == 1) 
	        						xX2 = x2 + 34; //여우 한 마리일 때 
	        					if(num1 == 2) 
	        						xX2 = x2 + 98; //여우 두 마리일 때 
	        					if(num1 == 3) 
	        						xX2 = x2 + 125; //여우 세 마리일 때 
	        					
	        					//난수 num2
	        					if(num2 ==1)
	        						xX3 = x3 + 34;
	        					if(num2 ==2)
	        						xX3 = x3 + 98;
	        					if(num2 ==3)
	        						xX3 = x3 + 125;
	        					
	        					//난수 num3
	        					if(num3 == 1)
	        						xX4 = x4 + 34;
	        					if(num3 == 2)
	        						xX4 = x4 + 98;
	        					if(num3 == 3)
	        						xX4 = x4 + 125;
	        				}
	        				
	        				//옥수수 먹을 수 있는 범위 초기화
	        				for(int i = 0; i < cornX.length; i++) {
		    			        cornX2[i] = (cornX[i] + 40);
	    					}
	        				
	        				//병아리가 옥수수를 먹었을때 실행 구문.
	        				for(int i = 0; i < cornX.length; i++) {
		        				if(cornX[i] <= xX && cornX[i] >= x 
		        						|| cornX2[i] <= xX && cornX2[i] >= x) { //옥수수가 병아리 위치를 지나갈때 실행.
		        					if(y >= 130 && y <= 240) { //병아리가 점프했을 때 옥수수 위치에 있다면 실행  
		        							//옥수수를 화면에서 없앤다.
		        							cornY[i] = 0; //조건문 성립을 위해 Y값도 0로 준다.
			        						cornX[i] = 0;
			        						
		        						if(cornY[i] == 0 && cornX[i] <= 0) {
		        							cornScore += level; //옥수수를 먹은 개수만큼 점수 초기화
		        							
		        							//없어진 옥수수 좌표초기화
		        							cornX[i] = 1200;
		        							cornY[i] = 200;
		        							
		        							//병아리가 먹은 옥수수들에 위치값이 초기화 되었을 때 겹치지 않게 해주는 구문.
		        							cornPosition(i); //옥수수 위치초기화 메소드 
		        						}
		        					} 
		        				} else { 
	        						if(cornX[i] <= 0) { //병아리가 먹지 않은 옥수수가 프레임을 벗어 났을 때 실행 된다.
	        							cornX[i] = 1200;
	        							
	    							    //프레임을 벗어난 옥수수들에 위치값이 초기화 되었을 때 겹치지 않게 해주는 구문.
	        							cornPosition(i); //옥수수 위치초기화 메소드 
	    	        				}
	    						}
	        				}
	        				
	        				//옥수수들이 앞뒤 간격이 60이상 차이나도록 유지시켜주는 구문.
	        				for(int i = 0; i < cornX.length; i++) {
	        					cornPosition(i); //옥수수 위치초기화 메소드 
	        				}
	        				
	        				//병아리가 불닭볶음면을 먹었을 때 실행구문.
	        				if(x6 <= xX && x6 >= x 
	        						|| xX6 <= xX && xX6 >= x) {  //불닭볶음면이 병아리 위치를 지나갈때 실행.
	        					if(y >= 80 && y <= 189) {
	        						x6 = 0; //불닭볶음면 프레임에서 없애기 
	        						characterChange = 10; //불닭변신시간 제한 초기화
	        					}	
	        				}
	        				
	        				//여우가 프레임을 벗어 났을 때 다시 나오도록 위치 초기화 
	        				if(x2 <= 0) {
	        					x2 = 1200;
	        					if(score >= 1500) { //스코어가 조건문에 해당될 떄. 3마리 여우도 나온다.
	        						num1 = ((int)(Math.random()*3)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					} else {
	        						num1 = ((int)(Math.random()*2)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					}	
	        					//첫 번째 위치 여우가 프레임을 벗어 날 때 기준으로  조건문에 스코어를 넘었을 때 두더지와 새가 나타난다.
	        					if(score >= 500)
	        						moleSpeed = 8; //두더지 스피드
	        					if(score >= 500)
	        						bridSpeed = 10; //새 스피드 
	        					if(score >= 1000 && num3 != 3)
	        						moleSpeed2 = 18; //두더지 스피드
	        				}
	        				if(x3 <= 0) {
	        					x3 = 1200;
	        					if(score >= 1500) { //스코어가 조건문에 해당될 떄. 3마리 여우도 나온다.
	        						num2 = ((int)(Math.random()*3)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					} else {
	        						num2 = ((int)(Math.random()*2)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					}
	        				}
	        				if(x4 <= 0) {
	        					x4 = 1200;
	        					if(score >= 1500) { //스코어가 조건문에 해당될 떄. 3마리 여우도 나온다.
	        						num3 = ((int)(Math.random()*3)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					} else {
	        						num3 = ((int)(Math.random()*2)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
	        					}
	        					//세 번째 위치 여우가 프레임을 벗어 날 때 불닭볶음면이 나타나는 확률 랜덤 적용.
	            				if(x6 == 1200) {
	            					num4 = ((int)(Math.random()*5)+1); 
	            					noodleSpeed = 6;
	            				}
	            				//세 번째 위치 여우가 프레임을 벗어 날 때 기준으로  두번째 새가 나타난다.
	            				if(score >= 1000)
	        						bridSpeed2 = 17; //새 스피드 
	            				
	        				}
	        				
	        				//불닭볶음면이 프레임을 벗어 났을 때 다시 나오도록 위치 초기화 
	        				if(x6 == 0) {
	        					x6 = 1200;
	        					noodleSpeed = 0;
	        				}
	        				
	        				//두더지가 프레임을 벗어 났을 때 다시 나오도록 위치 초기화 
	        				if(x5 <= 0) {
	        					x5 = 1200;
	        					moleSpeed = 0;
	        				}
	        				if(x9 <= 0) {
	        					x9 = 1200;
	        					moleSpeed2 = 0;
	        				}
	        				
	        				//새가 프레임을 벗어 났을 때 다시 나오도록 위치 초기화 
	        				if(x7 <= 0) {
	        					x7 = 1200;
	        					bridSpeed = 0;
	        				}
	        				if(x8 <= 0) {
	        					x8 = 1200;
	        					bridSpeed2 = 0;
	        				}
	        				
	        				if(!protectRun) {
		        				//병아리가 여우를 못피했을 때 게임종료 범위 
		        				if(x2 <= xX && x2 >= x || xX2 <= xX && xX2 >= x) { //여우 위치가 x2 y2일 떄
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        						 
		        					}
		        				}	
		    				
		        				if(x3 <= xX && x3 >= x || xX3 <= xX && xX3 >= x) { //여우 위치가 x3 y3일 떄
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        					}
		        				}	
		    				
		        				if(x4 <= xX && x4 >= x || xX4 <= xX && xX4 >= x) { //여우 위치가 x4 y4일 떄
		        					if(y <= 380 && y >= 300) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        					}
		        				}	
		        				
		        				//병아리가 두더지를 못피했을 때 게임종료 범위 
		        				if(x5 <= xX && x5 >= x || xX5 <= xX && xX5 >= x) { 
		        					if(y <= 380 && y >= 330) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        					}
		        				}	
		        				if(x9 <= xX && x9 >= x || xX9 <= xX && xX9 >= x) { 
		        					if(y <= 380 && y >= 330) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        					}
		        				}	
		        				
	        					//병아리가 새를 못피했을 때 게임종료 범위 
		        				if(x7 <= xX && x7 >= x || xX7 <= xX && xX7 >= x) { //첫 번째 새 
		        					if(y <= 150 && y >= 100) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
		        						gameOver();
		        					}
		        				}	
		        				if(x8 <= xX && x8 >= x || xX8 <= xX && xX8 >= x) { //두 번째 새 
		        					if(y <= 150 && y >= 100) {
		        						saveScoreSignal= true; //게임 종료시 점수 저장가능하게 하는 변수
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
        
        //키보드 컨트롤 쓰레트 생성 
        thread = new Thread(this); 
		thread.start();
		
		//버튼 이벤트 연결.
		jbutton_Restart.addActionListener(this);
		jbutton_SaveScore.addActionListener(this);
		jbutton_MainScreen.addActionListener(this);
		jbutton_Save.addActionListener(this);
		jbutton_Cancel.addActionListener(this);
		jbutton_Confirm.addActionListener(this);
		jbutton_Notice.addActionListener(this);
		
	} //--생성자종료
	
	//키보드 컨트롤 
	public void run() {
		try{ 
			while(true){
				if(ctrl) {
					
					//병아리일 때 실행 구문.
					if(characterChange == 0) { 
						if(up) { //컨트롤키를 눌렀을때 실행되는 점프
							y -= jump;
							if(y <= 80) {
								up = false;
								down = true;
							}
						}
						
						if(down) { //점프 높이 최대치를 찍었을 때 내려오게하는 실행 구문
							y += jump;
							if(y == 380) {
								down = false;
							}
						}
					
					//불닭일 때 실행 구문. 	
					} else { 
						if(up) { //컨트롤키를 한번 눌렀을 때 실행되는 점프
							y -= jump;
							if(y <= 80) {
								up = false;
								down = true;
							}
							
							//불닭이 점프를 유지할 때 계속 다운시켜줘서 붉닭이 한번에 올라가지 못하도록 저항감을 준다.
							if(firejump && y >= 80 && y <= 350) { 
								up = false;
								down = true;
								jump = 5;
							}
						}
						
						if(down) { //점프 높이 최대치를 찍고 내려올때 실행 구문
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
	
	//버튼 이벤트 적용.
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == jbutton_Restart) { //게임 다시 시작 메뉴 버튼
			jdialog_GameOver.setVisible(false);
			jtextField_Name.setText("");
			gameRestart(); 
		}
		if(obj == jbutton_SaveScore) { //점수 저장 메뉴 버튼
			if(saveScoreSignal) { //게임 종료시에만 점수저장이 가능하도록 하는 조건문.
				saveScore();
			} else { //점수저장 가능 여부 알림창
				label[12].setText("점수저장은 게임 종료 후에");
				label[13].setText("한 번만 가능합니다.");
				jdialog_Notice.setLayout(new GridLayout(6, 1));
				jdialog_Notice.getContentPane().setBackground(new Color(255, 153, 0));
				jdialog_Notice.add(jlabel[1]); //공백
				jdialog_Notice.add(label[12]); //멘트
				jdialog_Notice.add(label[13]); //멘트
				jdialog_Notice.add(jlabel[2]); //공백
				jdialog_Notice.add(jbutton_Notice); //확인버튼
				jdialog_Notice.add(jlabel[3]); //공백
				jdialog_Notice.setSize(300, 300);
				jdialog_Notice.setLocationRelativeTo(null);
				jdialog_Notice.setVisible(true);
			}
		}
		if(obj == jbutton_Notice) { //점수저장 가능 여부 알림창 확인 버튼
			jdialog_Notice.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_Save) { //점수 저장 버튼 
			Date date = new Date();
			SimpleDateFormat fdate = new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss");
			obj = fdate.format(date);
			String dateString = obj.toString(); //날짜 시간 정보  
			String name = jtextField_Name.getText(); //이름 
			SaveScore saveScore = new SaveScore(dateString, name, score + (cornScore*10)); //점수 저장 클래스
			SaveScoreDB_Insert saveScoreDB_Insert = new SaveScoreDB_Insert(); //점수 DB저장 객체 생성
			saveScoreDB_Insert.setSaveScoreDB_Insert(saveScore); //점수 DB저장 메소드 호출
			saveScoreSignal = false; //점수를 중복저장 못하도록 변수 초기화 
			jdialog_SaveScore.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_Cancel) { //점수 저장 취소 버튼
			jdialog_SaveScore.setVisible(false);
			jtextField_Name.setText("");
		}
		if(obj == jbutton_MainScreen) { //메인 화면 메뉴 버튼
			jdialog_GameOver.setVisible(false);
			loadingCnt = 0;
			loadingCompletion = false;
			gameRestart();
			mainScreenSignal = true;
		}
		if(obj == jbutton_Confirm) { //랭킹 보기 확인 버튼
			jdialog_Ranking.setVisible(false);
		}
	}
	
	//옥수수 위치 초기화 메소드
	public void cornPosition(int i) {
		for(int j = 0; j < cornX.length; j++) {
			if(i != j) {
				//현재 옥수수가 앞에 있는 옥수수와 위치를 비교해서 간격 60보다 작으면 x좌표의 간격을 뒤로 60만큼 띄워준다.
				boolean	result1 = (cornX[i] - cornX[j]) < 60 && (cornX[i] - cornX[j]) >= 0;
				if(result1) {
					cornX[i] += 60;
				}
				
				//현재 옥수수가 뒤에 있는 옥수수와 위치를 비교해서  간격이 60보다 작으면 x좌표의 간격을  앞으로 60만큼 띄워준다.
				boolean	result2 = (cornX[i] - cornX[j]) > -60 && (cornX[i] - cornX[j]) <= 0;
				if(result2) {
					cornX[i] -= 60;
				}
			}
		}
	}
	
	//게임 다시 시작
	public void gameRestart() {
		
		//점수를 중복저장 못하도록 변수 초기화 
		saveScoreSignal = false; 
		
		//캐릭터 병아리로 셋팅
		characterChange = 0;
		
		//병아리 및 장애물 초기화 
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
		
		//여우 움직이는 속도 초기화 
		foxSpeed = 6;
		
		//점수 및 점수 기준 초기화 
		score = 0;
		cornScore = 0;
		level = 1;
		
		//배경이미지 움직이기 
		mapRun = true;
		
		//키조작 적용
		ctrl = true;
		
		//여우 마릿수 초기화
		if(num1 == 3) {
			num1 = ((int)(Math.random()*2)+1); //첫 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
		}
		if(num2 == 3) {
			num2 = ((int)(Math.random()*2)+1); //두 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
		}
		if(num3 == 3) {
			num3 = ((int)(Math.random()*2)+1); //세 번째 위치 여우. 이미지랜덤 적용을 위한 난수.
		}
		
		//불닭변신 시간제한 감소 기준 초기화
 	   	num5 = 1;
 	   	
 	   	//일시무적 시간제한 감소 기준 초기화 
 	   	num6 = 1;
		
 	   	//일시무적 제한시간 초기화;
 	   	protectCnt = 0; //일시무적 제한시간 초기화를 
 	   	
 	   	//조건문해제를 위한 게임오버 신호 초기화 
 	   	gameOverSignal = true; //보호막해제 
 	   	
 	   	//병아리 보호적용 초기화 
		protectRun = false;
		
		//옥수수 x좌표 초기화 
		int sum = 560;
        for(int i = 0; i < cornX.length; i++) {
        	cornX[i] = sum += 60;
        }
        
        //옥수수 y좌표 초기화 
        for(int i = 0; i < cornY.length; i++) {
        	cornY[i] = 200;
        }
	}
	
	//점수저장 화면
	public void saveScore() {
		//제목
		label[1].setText("점 수 저 장");
		label[1].setFont(gameOverFont);
		
		//옥수수 점수 판넬 
		jlabel[0].setHorizontalAlignment(JLabel.RIGHT);
		label[2].setText(cornScore + "  x  " + 10 + "  =  ");
		label[3].setText("" + (cornScore*10));
		label[3].setAlignment(Label.LEFT);
		jpanel_cornScore.setLayout(new GridLayout(1, 3));
		jpanel_cornScore.setBackground(new Color(255, 153, 0));
		jpanel_cornScore.add(jlabel[0]); //옥수수 이미지
		jpanel_cornScore.add(label[2]);
		jpanel_cornScore.add(label[3]);
		
		//이름 입력 판넬
		label[9].setText("이  름");
		jpanel_Name.setLayout(new BorderLayout());
		jpanel_Name.setBackground(new Color(255, 153, 0));
		jpanel_Name.add(label[9], BorderLayout.WEST);
		jpanel_Name.add(jtextField_Name, BorderLayout.CENTER);
		jpanel_Name.add(label[10], BorderLayout.EAST);
		jpanel_Name.add(label[11], BorderLayout.SOUTH);
		
		//점수 및 총점수 
		label[4].setText("점수 : "+ score);
		label[5].setText("총점수 : "+ (score + (cornScore*10)));
		jdialog_SaveScore.setLayout(new GridLayout(7, 1));
		jdialog_SaveScore.getContentPane().setBackground(new Color(255, 153, 0));
		jdialog_SaveScore.add(label[1]); //제목
		jdialog_SaveScore.add(jpanel_cornScore); //옥수수 점수
		jdialog_SaveScore.add(label[4]); //점수
		jdialog_SaveScore.add(label[5]); //총점수
		jdialog_SaveScore.add(jpanel_Name); //이름입력 판넬 
		jdialog_SaveScore.add(jbutton_Save); //저장버튼
		jdialog_SaveScore.add(jbutton_Cancel); //취소버튼
		jdialog_SaveScore.setSize(300, 400);
		jdialog_SaveScore.setLocationRelativeTo(null);
		jdialog_SaveScore.setVisible(true);
	}
	
	//랭킹
	public void ranking() {
		SaveScoreDB_SelectAll saveScoreDB_SelectAll = new SaveScoreDB_SelectAll(); //DB에서 모든 점수를 가져오는 객체
		rankingList = saveScoreDB_SelectAll.getRankingList(); //모든 점수를 가져와서 rankingList 컬렉션에 저장
		jtextArea.setText("");
		jtextArea.setBackground(new Color(255, 153, 0));
		label[6].setText("랭 킹 순 위");
		label[6].setFont(scoreFont);
		for(int i = 0; i < rankingList.size(); i++) {
			jtextArea.append("일시 : " + rankingList.get(i).getDate() +"\n" +
					  "이름 : " + rankingList.get(i).getName() +"\n" + 
					  "점수 : " + rankingList.get(i).getScore() +"\n" + 
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
	
	//게임 종료시 기능 정지
	public void gameOver() {
		//기능정지
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
 	    protectRun = true; //캐릭터가 장애물에 닿아서 쓰레드로 인해 게임오버 메소드가 계속 호출되는 것을 막기 위해 true준다.
 	    gameOverSignal = false; //보호막을 해제하는 것을 막아준다.
		mapRun = false;
		ctrl = false;
		
		//알림창 
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
	
	//그림을 그리는 메소드
	public void paint(Graphics g) {
		//버퍼 적용전
//		g.clearRect(0, 0, f_width, f_height);
//		g.drawImage(mapImg, 0, 0, this);//실제 화면(g)으로 오프스크(buffg)에 그려진 이미지(buffImage)를 옮김.
//		g.drawImage(characterImg, x, y, this);//실제 화면(g)으로 오프스크(buffg)에 그려진 이미지(buffImage)를 옮김.
		memori_Img = createImage(this.getWidth(), this.getHeight()); //버퍼이미지 
		buffg = memori_Img.getGraphics(); //이미지를 그리기 위해 그래픽 객체를 얻어온다.
		
		buffg.drawImage(map_Img[imgCnt], 0, 0, this); //맵	
		
		buffg.drawImage(mole_Img, x5, 400, this); //두더지
		buffg.drawImage(mole_Img, x9, 400, this); //두더지
		
		if(characterChange == 0) {
			buffg.drawImage(character_Img, x, y, this); //병아리
		} else {
			if(characterChange >= 9) {
				buffg.drawImage(firechicken2_Img, x, y, this); //불뿜는 붉닭
				buffg.drawImage(firenoodle_Img, 220, 40, this); //불닭볶음면
				buffg.setColor(Color.WHITE);
				buffg.setFont(scoreFont);
				buffg.drawString(""+ characterChange , 280, 70); //불닭효과 남으신간
			} else {
				buffg.drawImage(firechicken_Img, x, y, this); //붉닭
				buffg.drawImage(firenoodle_Img, 220, 40, this); //불닭볶음면
				buffg.setColor(Color.WHITE);
				buffg.setFont(scoreFont);
				buffg.drawString(""+ characterChange , 280, 70); //불닭효과 남으신간
			}
		}	
		
		buffg.drawImage(fox_Img[num1], x2, 360, this); //여우이미지 위치1
		buffg.drawImage(fox_Img[num2], x3, 360, this); //여우이미지 위치2
		buffg.drawImage(fox_Img[num3], x4, 360, this); //여우이미지 위치3
		
		buffg.drawImage(firenoodle_Img, x6, 140, this); //불닭볶음면
		
		buffg.drawImage(bird_Img, x7, 100, this); //새 
		buffg.drawImage(bird_Img, x8, 100, this); //새2
		
		for(int i = 0; i < corns_Img.length; i++) {
			buffg.drawImage(corns_Img[i], cornX[i], cornY[i], this); //옥수수 이미지 
		}
		
		buffg.setColor(Color.WHITE);
		buffg.setFont(scoreFont);
		buffg.drawImage(corn_Img, 100, 50, this); 
		buffg.drawString(" X " + cornScore, 140, 75); //옥수수 점수판
		buffg.drawString("점 수 : " + score, 100, 115); //run 점수판
		
		if(protectRun && protectCnt != 0 && characterChange == 0) { //불닭에서 병이리로 돌아갔을 때 실행.
			buffg.setColor(Color.WHITE);
			buffg.setFont(scoreFont);
			buffg.drawString("일시 무적 : "+ protectCnt , x -10, y - 10); //불닭에서 병이리로 돌아갔을 때 무적시간
		}
		
		if(mainScreenSignal) { //메인화면 호출
			g.drawImage(mainScreen_Img, 0, 0, this); 
			g.drawImage(gameStart_Img, 480, 330, this); 
			g.drawImage(ranking_Img, 480, 390, this); 
		} else { //게임시작시 호출
			if(loadingCompletion) { //이미지 로딩완료시 실행.
				g.drawImage(memori_Img, 0, 0, this); //병아리, 여우, 맵 이미지 등.
				//실제 화면(g)으로 오프스크(buffg)에 그려진 이미지(buffImage)를 옮김.
			} else {
				g.drawImage(loading_Img, 0, 0, this);//로딩이미지
			}
		}
	}
	
	public static void main(String[] args) {
		new GameStart();
	}

}

