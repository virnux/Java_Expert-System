package n_queens;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class LeftControlPanel extends JPanel {

	private MessageBoxController messageBoxController;
	// private Image mainImage; //作弊模式觸發區域使用的圖標
	private JLabel imageLabel; // 作弊模式的觸發區域 按這個可以開啟從鍵盤輸入作弊碼 等待以後的版本實作
	private JButton completeNow; // 在遊戲模式下立刻完成棋局 (等待實作:開啟作弊模式後才可以顯示並使用)
	private JButton showNextAnswer; // 演算法模式下show出下一組解 同時會更新中下方面板的文字 顯示目前是 第X of
									// Y組解
	private JButton showLastAnswer; // 上一組解
	private JLabel leftBlankArea; // 左邊空白
	private JLabel rightBlankArea; // 右邊空白
	private JLabel buttomBlankArea; // 下方空白
	private GridBagLayout leftLayout;
	private GridBagConstraints leftConstraints;
	private ButtonController buttonController;
	private PrologEightQueensCommand prologEightQueensCommand;
	//private NQueensQuerySolutionNum querySolutionNum;
	private RightControlPanel rightControlPanel;
	private NQueensQuerySolutionDemo querySolutionDemo;
	private LeftControlPanel leftControlPanel;
	private DemoCount demoCount;
	// solutionCount = 原本的flag
	static int solutionCount = 0; // 控制演算法模式下show出第N組解控制旗標
	static List<String> tempAnswer = new ArrayList<String>(); // 存放用SWI
																// Prolog查詢八皇后問題後的各種可行解
	private static final long serialVersionUID = 1L;
	private ChessBoardStatus chessBoardStatus;
	private NQueensQuerySolutionDemo fourQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo fiveQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo sixQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo sevenQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo eightQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo nineQueensQuerySolutionDemo;

	public LeftControlPanel(ChessBoardStatus currentGameStatus, PrologEightQueensCommand prologCommand,
			MessageBoxController messageBoxController, RightControlPanel rightControlPanel, DemoCount demoCount) {
		this.rightControlPanel = rightControlPanel;
		this.messageBoxController = messageBoxController;
		this.prologEightQueensCommand = prologCommand;
		this.chessBoardStatus = currentGameStatus;
		leftLayout = new GridBagLayout();
		leftConstraints = new GridBagConstraints();
		setLeftShowPanel();
		leftControlPanel = this;
		this.demoCount = demoCount;
		initializeQuerySolutionDemo();
	}

	private void initializeQuerySolutionDemo() {
		fourQueensQuerySolutionDemo = demoCount.getFourQueensQuerySolutionDemo();
		fiveQueensQuerySolutionDemo = demoCount.getFiveQueensQuerySolutionDemo();
		sixQueensQuerySolutionDemo = demoCount.getSixQueensQuerySolutionDemo();
		sevenQueensQuerySolutionDemo = demoCount.getSevenQueensQuerySolutionDemo();
		eightQueensQuerySolutionDemo = demoCount.getEightQueensQuerySolutionDemo();
		nineQueensQuerySolutionDemo = demoCount.getNineQueensQuerySolutionDemo();
	}

	public void setButtonController(ButtonController buttonController) {
		this.buttonController = buttonController;
	}

	public void setLeftShowPanel() {
		setLayout(leftLayout);
		setBlankArea();
		setMainImage();
		setCompleteNowButton();
		setShowNextAnswerButton();
		setShowLastAnswerButton();
		leftLayout.setConstraints(this, leftConstraints);
		setBackground(Color.GRAY);
	}

	public void disabledShowNextAnswer() {
		showNextAnswer.setEnabled(false);
	}

	public void disabledShowLastAnswer() {
		showLastAnswer.setEnabled(false);
	}
	
	
	public void enabledShowLastAnswer() {
		showLastAnswer.setEnabled(true);
	}
	
	/*
	private void setGameToNormalMode() {
		chessBoardStatus.setGameMode(Mode.normal);
	}

	private void setGameToDemoMode() {
		chessBoardStatus.setGameMode(Mode.demo);
	}
*/
	
	private void setGameTONoneMode() {
		chessBoardStatus.setGameMode(Mode.none);
	}

	/*
	private int getCurrentChessBoardGrids() {
		return chessBoardStatus.getCurrentGrids();
	}
*/
	private int getDemoGrids() {
		return rightControlPanel.getDemoGrids();
	}

	/**
	 * 設定左邊控制面板空白區域的位置跟布局
	 * 
	 */
	private void setBlankArea() {
		// 左邊控制面板左邊的空白區域
		leftBlankArea = new JLabel();

		leftConstraints.gridx = 0;
		leftConstraints.gridy = 6;
		leftConstraints.gridwidth = 1;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(leftBlankArea, leftConstraints);

		// 左邊控制面板右邊的空白區域
		rightBlankArea = new JLabel();

		leftConstraints.gridx = 5;
		leftConstraints.gridy = 6;
		leftConstraints.gridwidth = 1;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(rightBlankArea, leftConstraints);

		// 左邊控制面板下方的空白區域
		buttomBlankArea = new JLabel();

		leftConstraints.gridx = 0;
		leftConstraints.gridy = 12;
		leftConstraints.gridwidth = 6;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(buttomBlankArea, leftConstraints);

		add(leftBlankArea);
		add(rightBlankArea);
		add(buttomBlankArea);
	}

	/**
	 * 設定左邊控制面板作弊模式的觸發區域的布局，目前還沒有實作功能跟把圖貼上去
	 * 
	 */
	private void setMainImage() {
		imageLabel = new JLabel();
		leftConstraints.gridx = 0;
		leftConstraints.gridy = 0;
		leftConstraints.gridwidth = 6;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 300;
		leftConstraints.weighty = 300;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(imageLabel, leftConstraints);
	}

	private ArrayList<int[]> getChessMoves() {
		return chessBoardStatus.getChessMoves();
	}

	/**
	 * 設定左邊控制面板上"立刻完成"按鈕的布局跟功能
	 * 
	 */
	private void setCompleteNowButton() {
		completeNow = new JButton("立刻完成");
		completeNow.setFont(new Font("新細明體", Font.PLAIN, 40));

		leftConstraints.gridx = 1;
		leftConstraints.gridy = 6;
		leftConstraints.gridwidth = 4;
		leftConstraints.gridheight = 2;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.insets = new Insets(50, 0, 50, 0);
		leftLayout.setConstraints(completeNow, leftConstraints);

		completeNow.setEnabled(false);
		completeNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ChessBoard.chessMoves.size()<8被寫死了 應該要能跟棋盤路數動態連結
				if (getChessMoves() != null && getChessMoves()
						.size() < 8) {/*
										 * Iterator<int[]> it =
										 * ChessBoard.chessMoves.iterator();
										 * HashMap<Integer,Integer> temp = new
										 * HashMap<Integer,Integer>();
										 * while(it.hasNext()){ int[] moves =
										 * it.next();
										 * temp.put(moves[0],moves[1]); }
										 */
					ArrayList<int[]> temp = chessBoardStatus.getChessMoves();
					completeAnswer(temp); // 傳入當前棋局暫存裡儲存的步數
				}
			}
		});
		add(completeNow);
	}

	/**
	 * 把當前棋局暫存裡儲存的步數轉換成SWI Prolog查詢語句
	 * 
	 * @param answer
	 *            當前棋局暫存裡儲存的步數
	 */
	private void completeAnswer(ArrayList<int[]> answer) {
		Iterator<int[]> mapit = chessBoardStatus.getChessMoves().iterator();
		HashMap<Integer, Integer> maptemp = new HashMap<Integer, Integer>();
		while (mapit.hasNext()) {
			int[] moves = mapit.next();
			maptemp.put(moves[0], moves[1]); // 把座標的X值當成map的key
		}
		int y[] = new int[8];
		// new int[8]被寫死了 應該要能跟棋盤路數動態連結
		Iterator<int[]> listit = chessBoardStatus.getChessMoves().iterator();
		// 把暫存步數的arraylist轉成map 再比較兩者的長度就能得知本來的arraylist裡是否有座標X值相同的棋步
		if (((int) maptemp.size()) == ((int) answer.size())) {
			while (listit.hasNext()) {
				int[] moves = listit.next();
				// switch case被寫死了 應該要能跟棋盤路數動態連結
				switch ((int) moves[0]) {// 把當前棋局暫存裡儲存的步數 Y座標+1 符合一般人的坐標系
											// 也讓程式能判斷該位置已有皇后
				case 0:
					y[0] = (int) moves[1] + 1;
					break;
				case 1:
					y[1] = (int) moves[1] + 1;
					break;
				case 2:
					y[2] = (int) moves[1] + 1;
					break;
				case 3:
					y[3] = (int) moves[1] + 1;
					break;
				case 4:
					y[4] = (int) moves[1] + 1;
					break;
				case 5:
					y[5] = (int) moves[1] + 1;
					break;
				case 6:
					y[6] = (int) moves[1] + 1;
					break;
				case 7:
					y[7] = (int) moves[1] + 1;
					break;
				}
			}
			// String query被寫死了 應該要能跟棋盤路數動態連結
			// 字串的串接效率要改善
			String query = "eight_queens([";
			// for(int i = 1;i<9;i++)被寫死了 應該要能跟棋盤路數動態連結
			for (int i = 1; i < 9; i++) {
				if (y[i - 1] != 0) { // if y[i-1] != 0 代表此局棋盤上對應到的格子有放皇后
					query = query + "(" + i + "," + y[i - 1];
				} else {
					query = query + "(" + i + "," + "Y" + i;
				}
				// if(i !=8) 應該要能跟棋盤路數動態連結
				if (i != 8) {
					query = query + "),";
				} else {
					query = query + ")";
				}
			}
			query = query + "]).";
			prologEightQueensCommand.infer_Eightqueens_Solution(query);
		} else { // ((int)maptemp.size())<((int)answer.size())
			messageBoxController.setMessageNoAnswer();
			setGameTONoneMode();
		}
		buttonController.checkButtonStatus();
	}

	public void clearDemoFlag() {
		if (querySolutionDemo != null) {
			// querySolutionDemo.clearFlag();
		}
	}

	/**
	 * 設定左邊控制面板上"下一組解"按鈕的布局跟功能
	 * 
	 */
	private void setShowNextAnswerButton() {
		showNextAnswer = new JButton("下一組解");
		showNextAnswer.setFont(new Font("新細明體", Font.PLAIN, 40));

		leftConstraints.gridx = 1;
		leftConstraints.gridy = 10;
		leftConstraints.gridwidth = 4;
		leftConstraints.gridheight = 2;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.insets = new Insets(50, 0, 50, 0);
		leftLayout.setConstraints(showNextAnswer, leftConstraints);
		showNextAnswer.setEnabled(false);

		showNextAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoardStatus.clearChessMove(); // 清空棋步
				int tempGrid;
				if (getCurrentGameMode() == Mode.demo) { // 演算法模式下才可用
					switch (getDemoGrids()) {
					case 4:
						tempGrid = 4;
						checkButtonStatus();
						fourQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						fourQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						fourQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 5:
						tempGrid = 5;
						checkButtonStatus();
						fiveQueensQuerySolutionDemo.setCommand("five_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						fiveQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						fiveQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 6:
						tempGrid = 6;
						checkButtonStatus();
						sixQueensQuerySolutionDemo.setCommand("six_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						sixQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						sixQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 7:
						tempGrid = 7;
						checkButtonStatus();
						sevenQueensQuerySolutionDemo.setCommand("seven_queens.pl", messageBoxController,
								chessBoardStatus, leftControlPanel);
						sevenQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						sevenQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 8:
						tempGrid = 8;
						checkButtonStatus();

						eightQueensQuerySolutionDemo.setCommand("eight_queens.pl", messageBoxController,
								chessBoardStatus, leftControlPanel);
						eightQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						eightQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 9:
						tempGrid = 9;
						checkButtonStatus();
						nineQueensQuerySolutionDemo.setCommand("nine_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						nineQueensQuerySolutionDemo.NQueensNextSolutionDemo(tempGrid);
						nineQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					}
				}
			}
		});
		add(showNextAnswer);
	}

	private Mode getCurrentGameMode() {
		return chessBoardStatus.getGameMode();
	}

	/**
	 * 設定左邊控制面板上"   "按鈕的布局跟功能 目前沒有用到 等待以後擴充用所以設為完全不可見
	 * 
	 */
	private void setShowLastAnswerButton() {
		showLastAnswer = new JButton("上一組解");
		showLastAnswer.setFont(new Font("新細明體", Font.PLAIN, 40));
		showLastAnswer.setEnabled(false);

		leftConstraints.gridx = 1;
		leftConstraints.gridy = 8;
		leftConstraints.gridwidth = 4;
		leftConstraints.gridheight = 2;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.insets = new Insets(50, 0, 50, 0);
		leftLayout.setConstraints(showLastAnswer, leftConstraints);
		/*
		 * 如果直接調用setVisible(false); 按鈕本來的區域會直接被覆蓋過去 要改為 不畫按鈕實際上的內容去達成隱藏但是占空間的按鈕
		 */
		// no.setBorderPainted(false); //不畫按鈕的邊界
		// no.setOpaque(false); //按鈕設為透明
		showLastAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoardStatus.clearChessMove(); // 清空棋步
				int tempGrid;
				if (getCurrentGameMode() == Mode.demo) { // 演算法模式下才可用
					switch (getDemoGrids()) {
					case 4:
						tempGrid = 4;
						checkButtonStatus();
						fourQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						fourQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);			
						fourQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 5:
						tempGrid = 5;
						checkButtonStatus();
						fiveQueensQuerySolutionDemo.setCommand("five_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);					
						fiveQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);					
						fiveQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 6:
						tempGrid = 6;
						checkButtonStatus();
						sixQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						sixQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);			
						sixQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 7:
						tempGrid = 7;
						checkButtonStatus();
						sevenQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						sevenQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);			
						sevenQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 8:
						tempGrid = 8;
						checkButtonStatus();
						eightQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						eightQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);			
						eightQueensQuerySolutionDemo.repaintChessBoard(tempGrid);
						break;
					case 9:
						tempGrid = 9;
						checkButtonStatus();
						nineQueensQuerySolutionDemo.setCommand("four_queens.pl", messageBoxController, chessBoardStatus,
								leftControlPanel);
						nineQueensQuerySolutionDemo.NQueensLastSolutionDemo(tempGrid);			
						nineQueensQuerySolutionDemo.repaintChessBoard(tempGrid);						
						break;
					}
				}
			}
		});
		add(showLastAnswer);
	}

	private void initializeSolutionCount() {
		solutionCount = 0;
	}

	public void checkButtonStatus() {
		if (getCurrentGameMode() == Mode.none) { // none模式下
			completeNow.setEnabled(false); // 不可以點擊左邊控制面板的"立刻完成"按鈕
			showNextAnswer.setEnabled(false); // 不可以點擊左邊控制面板的"下一組解"按鈕
			showLastAnswer.setEnabled(false);
			// 應該要把清空flag給移到別的地方去實現 flag是用來計算demo模式目前demo到第幾組解用的 跟button狀態無關
		} else if (getCurrentGameMode() == Mode.normal) { // normal模式下
			completeNow.setEnabled(true); // 可以點擊左邊控制面板的"立刻完成"按鈕
			showNextAnswer.setEnabled(false); // 不可以點擊左邊控制面板的"下一組解"按鈕
			showLastAnswer.setEnabled(false);
			// 應該要把清空flag這個功能移到別的地方去實現 flag是用來計算demo模式目前demo到第幾組解用的 跟button狀態無關
		} else { // demo模式下demo

			/*
			 * 不能在demo模式中初始化下拉式清單 不然抓取不到下拉式清單的數值 所以把初始化下拉式清單的工作交由在其他遊戲狀態時執行
			 */
			completeNow.setEnabled(false); // 不可以點擊左邊控制面板的"立刻完成"按鈕
			// showNextAnswer.setEnabled(false); //不可以點擊左邊控制面板的"下一組解"按鈕

			// 下拉式清單能選的棋盤應該都要能demo 所以以後只要 demoGrids == null 就可以點擊"下一組解"按鈕
			// demoGrids == 8寫死了 應該要能動態連接到目前的棋盤路數

			// if(demoGrids == 8){ //如果demo的棋盤路數是8 可以點擊"下一組解"按鈕
			// rightControlPanel.getCurrentSelcetDemoGrids();
			// 重寫 架構很糟
			if (rightControlPanel.getCurrentSelcetDemoGrids() != 0) {
				showNextAnswer.setEnabled(true);
				showLastAnswer.setEnabled(false);
			} else {
				showNextAnswer.setEnabled(false);
				showLastAnswer.setEnabled(false);
			}
		}
		initializeSolutionCount();
	}
}