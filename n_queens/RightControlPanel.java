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
	//private Image queenImage;        		//���ӭn��b��ܳѤU���ӦZ�Ʈ��䪺�Ϯ� ���ݥH���@   
	private JLabel imageLabel;          	//�ӤW ��m�ӹϮת��ϰ� �ثe����
	private JLabel queenLeft;    	//��ܦb�C���Ҧ��U�ثe�ٳѤU�X�ӬӦZ�n�\
	private JButton newGame;    		//"���}�s��"�����s
	private JButton confirmAnswer;   //"�T�{����"�����s
	private JButton undo;			//"����"�����s
	private JButton startNormalGame; //"�@��C��"�����s
	private JButton startDemo;		//"Demo"�����s
	private JComboBox<Integer> selcetDemoGrids;   //Demo�Ҧ����demo�X���ѽL���U�Ԧ��M�� �̭������������OInteger
	//private JLabel leftBlankArea;   		//����O���䪺�ťհϰ�
	//private JLabel rightBlankArea;  		//����O�k�䪺�ťհϰ�
	//private JLabel buttomBlankArea; 		//����O�U�誺�ťհϰ�
	//private JLabel topBlankArea;			//����O�W�誺�ťհϰ�
	private int demoGrids;			//Demo�Ҧ��ҰʮɤU�Ԧ��M��ҿ�ܪ�����
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
	 * �]�w��m�Ϯװϰ쪺���� �ثe�L�Ϯ׵��H��A�s�W�Ϯ�
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
	 * �]�w��ܬӦZ�ưϰ쪺����
	 * 
	 */
	public void setQueenLeft(){
		queenLeft = new JLabel("",SwingConstants.LEFT);		
		queenLeft.setFont(new Font("�s�ө���",Font.PLAIN, 40));
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
		queenLeft.setText("�ѤU���ӦZ�� : " + (8-getCurrentChessMOveCount()));
	}
	
	private void initializeQueenLeftText(){
		queenLeft.setText("                    ");
	}
	/**
	 * 
	 * �]�w"���}�s��"���s�������M�\��
	 */
	public void setNewGame(){
		newGame = new JButton("���}�s��");
		newGame.setFont(new Font("�s�ө���",Font.PLAIN, 15));
		
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
				clearTempAndRepaintChessBoard(); 		//��l�ƨí�ø�ѽL
				
				messageBoxController.StartTimer(); //���s�}�l�p�ɾ�
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
	 * �]�w"�T�{����"���s�������M�\��
	 */
	public void setConfirmAnswer(){
		confirmAnswer = new JButton("�T�{����");
		confirmAnswer.setFont(new Font("�s�ө���",Font.PLAIN, 15));
		
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
				//Ū���Ȧs�̪��y��
				//ChessBoard.chessMoves.size()==8 �o��Q�g���F ���ӭn��ѽL���ưʺA�s��
				if(getChessMove()!=null && getChessMove().size()==getCurrentGrids()){
					Iterator<int[]> it = getChessMove().iterator();
					//��map�ӱư���X�y�Э��ƪ����D
					HashMap<Integer,Integer> temp = new HashMap<Integer,Integer>();	
					while(it.hasNext()){				
						int[] moves  = it.next();	
						temp.put(moves[0],moves[1]);
					}
					//temp.size()!=8�o��Q�g���F ���ӭn��ѽL���ưʺA�s��
					if(temp.size()!=getCurrentGrids()){       //�K�ӴѨB���Ȧs�নmap�᪺size()!=8 �N��@��key�Ȫ�X�y�Ц����ƪ�
						JOptionPane.showMessageDialog(null, "�ЦA�ˬd�ݬ�","���׿��~",JOptionPane.ERROR_MESSAGE);
						//���X�p�����i���ϥΪ̥ثe���צ��~
					}else{      			
						confirmAnswer(temp);    //�ǤJSWI Prolog�i����
						//�P�_���׬O�_���T �ð���������ާ@
					}			
				}										
				buttonController.checkButtonStatus();						
			}					
		});
		
		add(confirmAnswer);
	}
	
	
	/**
	 * 
	 * �]�w"����"���s�������M�\��
	 */
	public void setUndo(){
		undo = new JButton("����");
		undo.setFont(new Font("�s�ө���",Font.PLAIN, 15));	
		
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
	 * �]�w"�@��C��"���s�������M�\��
	 */
	public void setStartNormalGame(){
		startNormalGame = new JButton("�@��C��");
		startNormalGame.setFont(new Font("�s�ө���",Font.PLAIN, 20));		
		
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
				clearTempAndRepaintChessBoard();  //��l�ƨí�ø�ѽL
				chessBoardStatus.setGameMode(Mode.normal);
				buttonController.checkButtonStatus();
				demoCount.clearDemoCounts();   
				messageBoxController.StartTimer(); //���s�}�l�p�ɾ�
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
	 * �]�w"Demo"���s�������M�\��
	 */
	public void setStartDemo(){
		startDemo = new JButton("�}�l�i��");
		startDemo.setFont(new Font("�s�ө���",Font.PLAIN, 20));
		
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
				clearTempAndRepaintChessBoard();		 //��l�ƨí�ø�ѽL
				demoCount.clearDemoCounts();
				messageBoxController.setMessageTextDemoMode();
				
				if(selcetDemoGrids.getSelectedItem() != null){		
					//System.out.println(selcetDemoGrids.getSelectedIndex());
					demoGrids = (int) selcetDemoGrids.getSelectedItem();
					buttonController.checkButtonStatus();	
					//�ھڿ�ܪ����� �s��������prolog�ɮרõe�X�������ѽL
					//���ӧ�demoGrids�@���ѼƶǤJ �I�s�s��prolog����k �ѸӤ�k�̾ڰѼƧP�_�ӳs����prolog�ɮ�
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
	 * �]�wDemo�Ҧ����U�Ԧ��M�檺�����M�\��
	 */
	public void setDemoGrids(){
		selcetDemoGrids = new JComboBox<Integer>();		   //���M�椺���������OInteger����
		selcetDemoGrids.setFont(new Font("�s�ө���",Font.PLAIN, 20));
		
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
	 * �̾ڥثe�C���Ҧ��M�ѧ����A�ˬd�ç�s���s���A
	 */
	public void checkButtonStatus(){
		if(getCurrentGameMode() == Mode.none){         	//none�Ҧ��U 
			newGame.setEnabled(false);								//���i�H�I��"���}�s��"���s
			confirmAnswer.setEnabled(false);						//���i�H�I��"�T�{����"���s
			undo.setEnabled(false);									//���i�H�I��"����"���s
			startNormalGame.setEnabled(true); 						//�i�H�I��"�@��C��"���s   ���|�|�ɭP�C���Ҧ��ഫ��normal�Ҧ�
			startDemo.setEnabled(true); 							//�i�H�I��"Demo"���s         ���|�|�ɭP�C���Ҧ��ഫ��demo�Ҧ�
			initializeQueenLeftText();
			selcetDemoGrids.setEnabled(false);  					//���i�H�I��U�Ԧ��M��
			selcetDemoGrids.setSelectedIndex(0);					//��U�Ԧ��M���l��
			demoCount.clearDemoCounts();	

		}else if (getCurrentGameMode() == Mode.normal){  	//normal�Ҧ��U
			newGame.setEnabled(true);								//�i�H�I��"���}�s��"���s
			startNormalGame.setEnabled(false); 						//���i�H�I��"�@��C��"���s
			startDemo.setEnabled(true); 							//�i�H�I��"Demo"���s         ���|�|�ɭP�C���Ҧ��ഫ��demo�Ҧ�
			setQueenLeftText();
			selcetDemoGrids.setEnabled(false);						//���i�H�I��U�Ԧ��M��
			selcetDemoGrids.setSelectedIndex(0);					//��U�Ԧ��M���l��
			demoCount.clearDemoCounts();	
			
			if((8-getCurrentChessMOveCount())== 0){  			//�p�G�������Ȧs�����ӦZ�S���񺡤K�� �N�����I��"�T�{����"���s
				confirmAnswer.setEnabled(true);
			}else{
				confirmAnswer.setEnabled(false);  
			}
			
			if(getCurrentChessMOveCount()>0 ){   				//�p�G�������Ȧs���� �N�����I��"����"���s
				undo.setEnabled(true);
			}else{
				undo.setEnabled(false);
			}			
			

			if(getCurrentChessMOves() != null){        
				//(8-chessBoard.getChessMovesCount())�g���F ���ӭn��ʺA�s����ثe���ѽL����
				queenLeft.setText("�ѤU���ӦZ�� : " + (8-getCurrentChessMOveCount()));			
			}
		}else{  													//demo�Ҧ��Udemo
			newGame.setEnabled(false);								//���i�H�I��"���}�s��"���s
			confirmAnswer.setEnabled(false);						//���i�H�I��"�T�{����"���s
			undo.setEnabled(false);									//���i�H�I��"����"���s
			initializeQueenLeftText();
			startNormalGame.setEnabled(true); 						//�i�H�I��"�@��C��"���s
			selcetDemoGrids.setEnabled(true);						//�i�H�I��U�Ԧ��M��
			demoCount.clearDemoCounts();	
			 /*	����bdemo�Ҧ�����l�ƤU�Ԧ��M��   ���M�������U�Ԧ��M�檺�ƭ�
			  * �ҥH���l�ƤU�Ԧ��M�檺�u�@��Ѧb��L�C�����A�ɰ���
			  */
		}		
	}
	
	/**
	 * �ˬd�ثe�������ѧ��O�_�����T���פ��@
	 * 
	 * @param answer �U�L���ѨB�Ȧs
	 */
	public void confirmAnswer(HashMap<Integer, Integer> answer){
		int y[]=new int[8];

		//�o������code�n���]�X�{�L �Ҽ{�ʸ˰_�� 
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