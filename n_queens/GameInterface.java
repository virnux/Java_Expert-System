package n_queens;

/* 
 * ClassName		 :
 * GameInterface 
 * 
 * Description 	     :
 * ���v�n�u�Ӥ@�ҵ{�{���y����s�����@�~ - �K�ӦZ�C��
 * �\��:�}�s�C���B���ѡB�\���K�ӬӦZ�i�H�T�{����
 *     �p��N�ӦZ���D���i��Ѽ�(N = 4 ~10)
 *     �i�H�i�ܥX�C�@�ؤK�ӦZ���D����
 * 
 * Author		     :
 * �\�ղE
 * 
 * History	         :	    
 * Date		 2016/11/16
 * Version   1.0
 * 
 * Date		 2016/12/19
 * Version   1.1
 * 
 * Copyright notice  :
 * �D�g��@�̱��v�\�i�A�T�������ŧ�C
 * 
 * ���Ӫ����:���c�ﵽ�[�c
 *          �Ѯv����ĳ:1.Chessboard���O���ӭn����  �Ҽ{�b�t�d�B�zgui���a��Τ@�ӰƵ{���h�I�sø(N���ѽL)�Ϫ��覡	
 * 					  2.��j�w�bLeft/Right/MidJPanel�ƦܬOChessboard�̪��ݩʵ��󥭤�
 *                      �]�N�O����JFrame���O�U�i��ŧi�A�o�˳o���ܼƴN�ܦ�����i��
 *          �ﵽ�걵�r�ꪺ�į�
 *          GameInterface���O�����\��Ӧh�n��L�̤��X��
 *          ��L�ѷӵ��Ѷi����
 *          �b�\��K�ӬӦZ���� ���ӭn�i�H�����ҵ��� ���O������ߨ觹��
 *          �Ҽ{�s�W�զ⪺�ӦZ �b�ծ��¦⪺�ӦZ �b�®��զ⪺�ӦZ
 *          
 *          �z�Lprolog�ʺA����count�ƩMquerycommand�C
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
		mainFrame = new JFrame("�K�ӦZ�C��");
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
