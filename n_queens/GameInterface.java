package n_queens;

/* 
 * ClassName		 :
 * GameInterface 
 * 
 * Description 	     :
 * 高師軟工碩一課程程式語言研究期末作業 - 八皇后遊戲
 * 功能:開新遊戲、悔棋、擺完八個皇后可以確認答案
 *     計算N皇后問題的可行解數(N = 4 ~10)
 *     可以展示出每一種八皇后問題的解
 * 
 * Author		     :
 * 許博淳
 * 
 * History	         :	    
 * Date		 2016/11/16
 * Version   1.0
 * 
 * Date		 2016/12/19
 * Version   1.1
 * 
 * Copyright notice  :
 * 非經原作者授權許可，禁止轉載抄襲。
 * 
 * 未來的更動:重構改善架構
 *          老師的建議:1.Chessboard類別應該要取消  考慮在負責處理gui的地方用一個副程式去呼叫繪(N路棋盤)圖的方式	
 * 					  2.把綁定在Left/Right/MidJPanel甚至是Chessboard裡的屬性給扁平化
 *                      也就是移到JFrame類別下進行宣告，這樣這些變數就變成全域可見
 *          改善串接字串的效能
 *          GameInterface類別內的功能太多要把他們分出來
 *          其他參照註解進行更動
 *          在擺放八個皇后之後 應該要可以按驗證答案 但是不能按立刻完成
 *          考慮新增白色的皇后 在白格放黑色的皇后 在黑格放白色的皇后
 *          
 *          透過prolog動態產生count數和querycommand。
 *          for( i = 0 ; i > grids ; i++)
 *          querycommand  =  XXX(Y1,1),(Y2,2)......XXXXX
 *          tempcommand = tempcommand+(Yi+1,i+1)
 *          
 *          the queens problems 1.
 *          				 	2.
 *								3.          
 * 
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameInterface {
	private JFrame mainFrame;
	private ChessBoard chessBoard;
	private Container contentPane;
	private LeftControlPanel leftControlPanel;
	private RightControlPanel rightControlPanel;
	private GridBagLayout mainLayout;
	private GridBagConstraints mainConstraints;
	private ChessBoardStatus currentGameStatus;
	private MessageBoxController messageBoxController;
	private PrologEightQueensCommand prologCommand;
	private ButtonController buttonController;
	private DemoCount demoCount;

	public GameInterface() {
		mainFrame = new JFrame("八皇后遊戲");
		initializeGUI();
	}

	private void initializeGUI() {

		mainLayout = new GridBagLayout();
		mainConstraints = new GridBagConstraints();

		contentPane = mainFrame.getContentPane();
		contentPane.setLayout(mainLayout);
		demoCount = new DemoCount();

		chessBoard = new ChessBoard(8);

		this.currentGameStatus = new ChessBoardStatus(chessBoard);

		messageBoxController = new MessageBoxController(currentGameStatus);

		prologCommand = new PrologEightQueensCommand(messageBoxController);

		rightControlPanel = new RightControlPanel(currentGameStatus, messageBoxController, prologCommand, demoCount);
		leftControlPanel = new LeftControlPanel(currentGameStatus, prologCommand, messageBoxController,
				rightControlPanel, demoCount);
		buttonController = new ButtonController(leftControlPanel, rightControlPanel);
		chessBoard.setButtonController(buttonController);
		leftControlPanel.setButtonController(buttonController);
		rightControlPanel.setButtonController(buttonController);

		placeLeftPanel();
		placeMessageBox();
		placeRightPanel();
		placeChessBoard();

		mainFrame.setPreferredSize(new Dimension(1800, 1800));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private void placeChessBoard() {
		mainConstraints.gridx = 2;
		mainConstraints.gridy = 0;
		mainConstraints.gridwidth = 4;
		mainConstraints.gridheight = 4;
		mainConstraints.weightx = 1200;
		mainConstraints.weighty = 1200;
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainLayout.setConstraints(chessBoard, mainConstraints);
		contentPane.add(chessBoard);
	}

	private JLabel getMessageBox() {
		return messageBoxController.getMessageBox();
	}

	private void placeMessageBox() {
		mainConstraints.gridx = 2;
		mainConstraints.gridy = 4;
		mainConstraints.gridwidth = 4;
		mainConstraints.gridheight = 1;
		mainConstraints.weightx = 100;
		mainConstraints.weighty = 100;
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainLayout.setConstraints(getMessageBox(), mainConstraints);
		contentPane.add(getMessageBox());
	}

	private void placeLeftPanel() {
		mainConstraints.gridx = 0;
		mainConstraints.gridy = 0;
		mainConstraints.gridwidth = 2;
		mainConstraints.gridheight = 5;
		mainConstraints.weightx = 50;
		mainConstraints.weighty = 50;
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainLayout.setConstraints(leftControlPanel, mainConstraints);
		contentPane.add(leftControlPanel);
	}

	private void placeRightPanel() {
		mainConstraints.gridx = 6;
		mainConstraints.gridy = 0;
		mainConstraints.gridwidth = 2;
		mainConstraints.gridheight = 5;
		mainConstraints.weightx = 0;
		mainConstraints.weighty = 0;
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainLayout.setConstraints(rightControlPanel, mainConstraints);
		contentPane.add(rightControlPanel);
	}

	public static void main(String args[]) {
		new GameInterface();
	}
}
