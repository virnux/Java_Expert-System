package n_queens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class RightControlPanel extends JPanel{
	//private Image queenImage;        		//本來要放在顯示剩下的皇后數旁邊的圖案 等待以後實作   
	private JLabel imageLabel;          	//承上 放置該圖案的區域 目前為空
	private JLabel queenLeft;    	//顯示在遊戲模式下目前還剩下幾個皇后要擺
	private JButton newGame;    		//"重開新局"的按鈕
	private JButton confirmAnswer;   //"確認答案"的按鈕
	private JButton undo;			//"悔棋"的按鈕
	private JButton startNormalGame; //"一般遊戲"的按鈕
	private JButton startDemo;		//"Demo"的按鈕
	private JComboBox<Integer> selcetDemoGrids;   //Demo模式選擇demo幾路棋盤的下拉式清單 裡面的元素類型是Integer
	//private JLabel leftBlankArea;   		//控制面板左邊的空白區域
	//private JLabel rightBlankArea;  		//控制面板右邊的空白區域
	//private JLabel buttomBlankArea; 		//控制面板下方的空白區域
	//private JLabel topBlankArea;			//控制面板上方的空白區域
	private int demoGrids;			//Demo模式啟動時下拉式清單所選擇的元素
	private static final long serialVersionUID = 1L;
	private GridBagLayout rightLayout;
	private GridBagConstraints rightConstraints;	
	private ChessBoardStatus chessBoardStatus;
	private ButtonController buttonController;
	private PrologEightQueensCommand prologCommand;
	private MessageBoxController messageBoxController;
	private NQueensQuerySolutionDemo queryCommand;
	private DemoCount demoCount;
	
	
	public RightControlPanel(ChessBoardStatus currentGameStatus,  MessageBoxController messageBoxController, PrologEightQueensCommand prologCommand ,DemoCount demoCount){
		this.messageBoxController = messageBoxController;
		this.chessBoardStatus = currentGameStatus;
		this.prologCommand = prologCommand;
		this.demoCount = demoCount;
		rightLayout= new GridBagLayout();
		rightConstraints= new GridBagConstraints();
		
		setRightShowPanel();
		setPreferredSize(new Dimension(400, 400));
	}
	
	public void setButtonController(ButtonController buttonController){
		this.buttonController = buttonController;
	}
	
	public int getCurrentGrids(){
		return chessBoardStatus.getCurrentGrids();
	}
	
	public void setRightShowPanel() {		
		setLayout(rightLayout);
		rightLayout.setConstraints(this, rightConstraints);
		
		setBackground(Color.GRAY);
		//setBlankArea();
		setImageLabel();
		setQueenLeft();
		setNewGame();
		setConfirmAnswer();
		setUndo();
		setStartNormalGame();
		setStartDemo();
		setDemoGrids();			
		
	}

	public int getDemoGrids(){
		return demoGrids;	
	}
	
	private int getCurrentChessMOveCount(){
		return chessBoardStatus.getChessMOveCount();
	}
	
	private ArrayList<int[]> getCurrentChessMOves(){
		return chessBoardStatus.getChessMoves();
	}
	
	/**
	 * 設定放置圖案區域的布局 目前無圖案等以後再新增圖案
	 * 
	 */
	public void setImageLabel(){
		imageLabel = new JLabel();
		rightConstraints.gridx= 1;
		rightConstraints.gridy= 3;
		rightConstraints.gridwidth =4;
		rightConstraints.gridheight = 2;
		rightConstraints.weightx= 30;
		rightConstraints.weighty= 30;
		rightConstraints.fill = GridBagConstraints.BOTH;
		rightLayout.setConstraints(imageLabel, rightConstraints);
		
		add(imageLabel);
	}
	
	/**
	 * 設定顯示皇后數區域的布局
	 * 
	 */
	public void setQueenLeft(){
		queenLeft = new JLabel("",SwingConstants.LEFT);		
		queenLeft.setFont(new Font("新細明體",Font.PLAIN, 40));
		initializeQueenLeftText();
		
		rightConstraints.insets=new Insets(100,10,10,10);
		rightConstraints.gridx= 0;
		rightConstraints.gridy= 2;
		rightConstraints.gridwidth =5;
		rightConstraints.gridheight = 3;
		
		rightLayout.setConstraints(queenLeft, rightConstraints);
		
		queenLeft.setVisible(true);
		add(queenLeft);
	}
	
	
	private void setQueenLeftText(){
		queenLeft.setText("剩下的皇后數 : " + (8-getCurrentChessMOveCount()));
	}
	
	private void initializeQueenLeftText(){
		queenLeft.setText("                    ");
	}
	/**
	 * 
	 * 設定"重開新局"按鈕的布局和功能
	 */
	public void setNewGame(){
		newGame = new JButton("重開新局");
		newGame.setFont(new Font("新細明體",Font.PLAIN, 15));
		
		rightConstraints.insets=new Insets(100,10,100,10);
		rightConstraints.gridx= 0;
		rightConstraints.gridy= 5;
		rightConstraints.gridwidth =1;
		rightConstraints.gridheight = 1;
		
		rightLayout.setConstraints(newGame, rightConstraints);
		newGame.setEnabled(false);
		
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					
				chessBoardStatus.setGameMode( Mode.normal);
				clearTempAndRepaintChessBoard(); 		//初始化並重繪棋盤
				
				messageBoxController.StartTimer(); //重新開始計時器
				buttonController.checkButtonStatus();							
			}					
		});
		
		add(newGame);
	}	
	
	private void clearTempAndRepaintChessBoard(){
		chessBoardStatus.setCurrentChessBoardGrids(8);
		chessBoardStatus.initializeChessBoard();  	
		chessBoardStatus.refreshChessBoard();
	}	
	
	private void undoTempAndRepaintChessBoard(){
		chessBoardStatus.setCurrentChessBoardGrids(8);
		chessBoardStatus.undo();
		chessBoardStatus.refreshChessBoard();
	}
	
	private  ArrayList<int[]> getChessMove(){
		return chessBoardStatus.getChessMoves();
	}
	
	/**
	 * 
	 * 設定"確認答案"按鈕的布局和功能
	 */
	public void setConfirmAnswer(){
		confirmAnswer = new JButton("確認答案");
		confirmAnswer.setFont(new Font("新細明體",Font.PLAIN, 15));
		
		rightConstraints.insets=new Insets(100,10,100,10);
		rightConstraints.gridx= 3;
		rightConstraints.gridy= 5;
		rightConstraints.gridwidth =1;
		rightConstraints.gridheight = 1;
		rightConstraints.weightx= 10;
		rightConstraints.weighty= 10;

		rightLayout.setConstraints(confirmAnswer, rightConstraints);
			
		confirmAnswer.setEnabled(false);		
		confirmAnswer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){								
				//讀取暫存裡的座標
				//ChessBoard.chessMoves.size()==8 這邊被寫死了 應該要跟棋盤路數動態連結
				if(getChessMove()!=null && getChessMove().size()==getCurrentGrids()){
					Iterator<int[]> it = getChessMove().iterator();
					//用map來排除掉X座標重複的問題
					HashMap<Integer,Integer> temp = new HashMap<Integer,Integer>();	
					while(it.hasNext()){				
						int[] moves  = it.next();	
						temp.put(moves[0],moves[1]);
					}
					//temp.size()!=8這邊被寫死了 應該要跟棋盤路數動態連結
					if(temp.size()!=getCurrentGrids()){       //八個棋步的暫存轉成map後的size()!=8 代表作為key值的X座標有重複的
						JOptionPane.showMessageDialog(null, "請再檢查看看","答案錯誤",JOptionPane.ERROR_MESSAGE);
						//跳出小視窗告知使用者目前答案有誤
					}else{      			
						confirmAnswer(temp);    //傳入SWI Prolog進行比較
						//判斷答案是否正確 並執行對應的操作
					}			
				}										
				buttonController.checkButtonStatus();						
			}					
		});
		
		add(confirmAnswer);
	}
	
	
	/**
	 * 
	 * 設定"悔棋"按鈕的布局和功能
	 */
	public void setUndo(){
		undo = new JButton("悔棋");
		undo.setFont(new Font("新細明體",Font.PLAIN, 15));	
		
		rightConstraints.insets=new Insets(100,10,100,10);
		rightConstraints.gridx= 2;
		rightConstraints.gridy= 5;
		rightConstraints.gridwidth =1;
		rightConstraints.gridheight = 1;
		rightConstraints.weightx= 10;
		rightConstraints.weighty= 10;
	
		rightLayout.setConstraints(undo, rightConstraints);
		undo.setEnabled(false);
		
		undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
			
				if(getCurrentGameMode()== Mode.normal){  
					undoTempAndRepaintChessBoard();
					buttonController.checkButtonStatus();					
				}
			}					
		});
			
		add(undo);	
	}
	
	/**
	 * 
	 * 設定"一般遊戲"按鈕的布局和功能
	 */
	public void setStartNormalGame(){
		startNormalGame = new JButton("一般遊戲");
		startNormalGame.setFont(new Font("新細明體",Font.PLAIN, 20));		
		
		rightConstraints.insets=new Insets(200,20,20,0);
		rightConstraints.gridx= 0;
		rightConstraints.gridy= 7;
		rightConstraints.gridwidth =2;
		rightConstraints.gridheight = 1;
		rightConstraints.weightx= 20;
		rightConstraints.weighty= 20;
		rightConstraints.fill = GridBagConstraints.BOTH;
		rightLayout.setConstraints(startNormalGame, rightConstraints);
			
		startNormalGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearTempAndRepaintChessBoard();  //初始化並重繪棋盤
				chessBoardStatus.setGameMode(Mode.normal);
				buttonController.checkButtonStatus();
				demoCount.clearDemoCounts();   
				messageBoxController.StartTimer(); //重新開始計時器
			}					
		});
		
		add(startNormalGame);
	}

	private Mode getCurrentGameMode(){
		return chessBoardStatus.getGameMode();
	}
	
	/*
	private void setGameToNormalMode(){
		chessBoardStatus.setGameMode(Mode.normal);
	}
	*/
	private void setGameToDemoMode(){
		chessBoardStatus.setGameMode(Mode.demo);
	}
	/*
	private void setGameTONoneMode(){
		chessBoardStatus.setGameMode(Mode.none);
	}
	*/
	
	/**
	 * 
	 * 設定"Demo"按鈕的布局和功能
	 */
	public void setStartDemo(){
		startDemo = new JButton("開始展示");
		startDemo.setFont(new Font("新細明體",Font.PLAIN, 20));
		
		rightConstraints.insets=new Insets(0,20,200,0);
		rightConstraints.gridx= 0;
		rightConstraints.gridy= 10;
		rightConstraints.gridwidth =2;
		rightConstraints.gridheight = 1;
		rightConstraints.weightx= 20;
		rightConstraints.weighty= 20;

		rightLayout.setConstraints(startDemo, rightConstraints);
		
		startDemo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setGameToDemoMode();
	
				buttonController.checkButtonStatus();   
				clearTempAndRepaintChessBoard();		 //初始化並重繪棋盤
				demoCount.clearDemoCounts();
				messageBoxController.setMessageTextDemoMode();
				
				if(selcetDemoGrids.getSelectedItem() != null){		
					//System.out.println(selcetDemoGrids.getSelectedIndex());
					demoGrids = (int) selcetDemoGrids.getSelectedItem();
					buttonController.checkButtonStatus();	
					//根據選擇的元素 連接對應的prolog檔案並畫出對應的棋盤
					//應該把demoGrids作為參數傳入 呼叫連接prolog的方法 由該方法依據參數判斷該連哪個prolog檔案
					switch(demoGrids){
									
						case 4:
							checkButtonStatus();
							queryCommand = new FourQueensQuerySolutionDemo();
							queryCommand.setCommand("four_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();
							queryCommand.repaintChessBoard(4);		
							break;
						case 5:
							checkButtonStatus();
							queryCommand = new FiveQueensQuerySolutionDemo();
							queryCommand.setCommand("five_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();
							queryCommand.repaintChessBoard(5);	
							break;	
						case 6:
							checkButtonStatus();
							queryCommand = new SixQueensQuerySolutionDemo();
							queryCommand.setCommand("six_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();	
							queryCommand.repaintChessBoard(6);	
							break;
						case 7:
							checkButtonStatus();
							queryCommand = new SevenQueensQuerySolutionDemo();
							queryCommand.setCommand("seven_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();	
							queryCommand.repaintChessBoard(7);	
							break;	
						case 8:							
							checkButtonStatus();
							queryCommand = new EightQueensQuerySolutionDemo();
							queryCommand.setCommand("eight_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();		
							queryCommand.repaintChessBoard(8);	
							break;
						case 9:							
							checkButtonStatus();
							queryCommand = new NineQueensQuerySolutionDemo();
							queryCommand.setCommand("nine_queens.pl",messageBoxController,chessBoardStatus);
							queryCommand.inferNQueensSolutionNum();	
							queryCommand.repaintChessBoard(9);	
							break;							
					}				
				}
			}					
		});
		
		add(startDemo);	
	}

	/**
	 * 
	 * 設定Demo模式中下拉式清單的布局和功能
	 */
	public void setDemoGrids(){
		selcetDemoGrids = new JComboBox<Integer>();		   //此清單內的元素都是Integer物件
		selcetDemoGrids.setFont(new Font("新細明體",Font.PLAIN, 20));
		
		rightConstraints.gridx= 2;
		rightConstraints.gridy= 10;
		rightConstraints.gridwidth =2;
		rightConstraints.gridheight = 1;
		rightConstraints.weightx= 20;
		rightConstraints.weighty= 20;
		rightLayout.setConstraints(selcetDemoGrids, rightConstraints);
				
		selcetDemoGrids.addItem(null);
		selcetDemoGrids.setSelectedIndex(0);
		for(int i = 0 ;i<6;i++){
			selcetDemoGrids.addItem(i+4);
		}	
		add(selcetDemoGrids);
	}
	
	public int getCurrentSelcetDemoGrids(){
		if(selcetDemoGrids.getSelectedItem()== null){
			return 0;
		}else{
			return selcetDemoGrids.getSelectedIndex();
		}
	}
	
	/**
	 * 
	 * 依據目前遊戲模式和棋局狀態檢查並更新按鈕狀態
	 */
	public void checkButtonStatus(){
		if(getCurrentGameMode() == Mode.none){         	//none模式下 
			newGame.setEnabled(false);								//不可以點擊"重開新局"按鈕
			confirmAnswer.setEnabled(false);						//不可以點擊"確認答案"按鈕
			undo.setEnabled(false);									//不可以點擊"悔棋"按鈕
			startNormalGame.setEnabled(true); 						//可以點擊"一般遊戲"按鈕   此舉會導致遊戲模式轉換成normal模式
			startDemo.setEnabled(true); 							//可以點擊"Demo"按鈕         此舉會導致遊戲模式轉換成demo模式
			initializeQueenLeftText();
			selcetDemoGrids.setEnabled(false);  					//不可以點選下拉式清單
			selcetDemoGrids.setSelectedIndex(0);					//把下拉式清單初始化
			demoCount.clearDemoCounts();	

		}else if (getCurrentGameMode() == Mode.normal){  	//normal模式下
			newGame.setEnabled(true);								//可以點擊"重開新局"按鈕
			startNormalGame.setEnabled(false); 						//不可以點擊"一般遊戲"按鈕
			startDemo.setEnabled(true); 							//可以點擊"Demo"按鈕         此舉會導致遊戲模式轉換成demo模式
			setQueenLeftText();
			selcetDemoGrids.setEnabled(false);						//不可以點選下拉式清單
			selcetDemoGrids.setSelectedIndex(0);					//把下拉式清單初始化
			demoCount.clearDemoCounts();	
			
			if((8-getCurrentChessMOveCount())== 0){  			//如果此局的暫存中的皇后沒有放滿八個 就不能點擊"確認答案"按鈕
				confirmAnswer.setEnabled(true);
			}else{
				confirmAnswer.setEnabled(false);  
			}
			
			if(getCurrentChessMOveCount()>0 ){   				//如果此局的暫存為空 就不能點擊"悔棋"按鈕
				undo.setEnabled(true);
			}else{
				undo.setEnabled(false);
			}			
			

			if(getCurrentChessMOves() != null){        
				//(8-chessBoard.getChessMovesCount())寫死了 應該要能動態連接到目前的棋盤路數
				queenLeft.setText("剩下的皇后數 : " + (8-getCurrentChessMOveCount()));			
			}
		}else{  													//demo模式下demo
			newGame.setEnabled(false);								//不可以點擊"重開新局"按鈕
			confirmAnswer.setEnabled(false);						//不可以點擊"確認答案"按鈕
			undo.setEnabled(false);									//不可以點擊"悔棋"按鈕
			initializeQueenLeftText();
			startNormalGame.setEnabled(true); 						//可以點擊"一般遊戲"按鈕
			selcetDemoGrids.setEnabled(true);						//可以點選下拉式清單
			demoCount.clearDemoCounts();	
			 /*	不能在demo模式中初始化下拉式清單   不然抓取不到下拉式清單的數值
			  * 所以把初始化下拉式清單的工作交由在其他遊戲狀態時執行
			  */
		}		
	}
	
	/**
	 * 檢查目前完成的棋局是否為正確答案之一
	 * 
	 * @param answer 下過的棋步暫存
	 */
	public void confirmAnswer(HashMap<Integer, Integer> answer){
		int y[]=new int[8];

		//這部分的code好像也出現過 考慮封裝起來 
		Iterator<Map.Entry<Integer, Integer>> it = answer.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, Integer> entry = it.next();
			switch((int)entry.getKey()){
				case 0:
					y[0] = (int)entry.getValue()+1;
					break;
				case 1:
					y[1] = (int)entry.getValue()+1;
					break;
				case 2:
					y[2] = (int)entry.getValue()+1;
					break;
				case 3:
					y[3] = (int)entry.getValue()+1;
					break;
				case 4:
					y[4] = (int)entry.getValue()+1;
					break;
				case 5:
					y[5] = (int)entry.getValue()+1;
					break;
				case 6:
					y[6] = (int)entry.getValue()+1;
					break;
				case 7:
					y[7] = (int)entry.getValue()+1;
					break;			
			}									
		}		
		
		String query = "eight_queens([(1,"+y[0]+"),(2,"+y[1]+"),(3,"+y[2]+"),(4,"+y[3]+"),(5,"+y[4]+"),(6,"+
						y[5]+"),(7,"+y[6]+"),(8,"+y[7]+")]). ";		
		prologCommand.infer_Eightqueens_Solution_TrueorFalse(query);
		
	}	
	
}