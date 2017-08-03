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
	// private Image mainImage; //�@���Ҧ�Ĳ�o�ϰ�ϥΪ��ϼ�
	private JLabel imageLabel; // �@���Ҧ���Ĳ�o�ϰ� ���o�ӥi�H�}�ұq��L��J�@���X ���ݥH�᪺������@
	private JButton completeNow; // �b�C���Ҧ��U�ߨ觹���ѧ� (���ݹ�@:�}�ҧ@���Ҧ���~�i�H��ܨèϥ�)
	private JButton showNextAnswer; // �t��k�Ҧ��Ushow�X�U�@�ո� �P�ɷ|��s���U�譱�O����r ��ܥثe�O ��X of
									// Y�ո�
	private JButton showLastAnswer; // �W�@�ո�
	private JLabel leftBlankArea; // ����ť�
	private JLabel rightBlankArea; // �k��ť�
	private JLabel buttomBlankArea; // �U��ť�
	private GridBagLayout leftLayout;
	private GridBagConstraints leftConstraints;
	private ButtonController buttonController;
	private PrologEightQueensCommand prologEightQueensCommand;
	//private NQueensQuerySolutionNum querySolutionNum;
	private RightControlPanel rightControlPanel;
	private NQueensQuerySolutionDemo querySolutionDemo;
	private LeftControlPanel leftControlPanel;
	private DemoCount demoCount;
	// solutionCount = �쥻��flag
	static int solutionCount = 0; // ����t��k�Ҧ��Ushow�X��N�ոѱ���X��
	static List<String> tempAnswer = new ArrayList<String>(); // �s���SWI
																// Prolog�d�ߤK�ӦZ���D�᪺�U�إi���
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
	 * �]�w���䱱��O�ťհϰ쪺��m�򥬧�
	 * 
	 */
	private void setBlankArea() {
		// ���䱱��O���䪺�ťհϰ�
		leftBlankArea = new JLabel();

		leftConstraints.gridx = 0;
		leftConstraints.gridy = 6;
		leftConstraints.gridwidth = 1;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(leftBlankArea, leftConstraints);

		// ���䱱��O�k�䪺�ťհϰ�
		rightBlankArea = new JLabel();

		leftConstraints.gridx = 5;
		leftConstraints.gridy = 6;
		leftConstraints.gridwidth = 1;
		leftConstraints.gridheight = 6;
		leftConstraints.weightx = 100;
		leftConstraints.weighty = 100;
		leftConstraints.fill = GridBagConstraints.BOTH;
		leftLayout.setConstraints(rightBlankArea, leftConstraints);

		// ���䱱��O�U�誺�ťհϰ�
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
	 * �]�w���䱱��O�@���Ҧ���Ĳ�o�ϰ쪺�����A�ثe�٨S����@�\����϶K�W�h
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
	 * �]�w���䱱��O�W"�ߨ觹��"���s��������\��
	 * 
	 */
	private void setCompleteNowButton() {
		completeNow = new JButton("�ߨ觹��");
		completeNow.setFont(new Font("�s�ө���", Font.PLAIN, 40));

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
				// ChessBoard.chessMoves.size()<8�Q�g���F ���ӭn���ѽL���ưʺA�s��
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
					completeAnswer(temp); // �ǤJ��e�ѧ��Ȧs���x�s���B��
				}
			}
		});
		add(completeNow);
	}

	/**
	 * ���e�ѧ��Ȧs���x�s���B���ഫ��SWI Prolog�d�߻y�y
	 * 
	 * @param answer
	 *            ��e�ѧ��Ȧs���x�s���B��
	 */
	private void completeAnswer(ArrayList<int[]> answer) {
		Iterator<int[]> mapit = chessBoardStatus.getChessMoves().iterator();
		HashMap<Integer, Integer> maptemp = new HashMap<Integer, Integer>();
		while (mapit.hasNext()) {
			int[] moves = mapit.next();
			maptemp.put(moves[0], moves[1]); // ��y�Ъ�X�ȷ�map��key
		}
		int y[] = new int[8];
		// new int[8]�Q�g���F ���ӭn���ѽL���ưʺA�s��
		Iterator<int[]> listit = chessBoardStatus.getChessMoves().iterator();
		// ��Ȧs�B�ƪ�arraylist�নmap �A�����̪����״N��o�����Ӫ�arraylist�̬O�_���y��X�ȬۦP���ѨB
		if (((int) maptemp.size()) == ((int) answer.size())) {
			while (listit.hasNext()) {
				int[] moves = listit.next();
				// switch case�Q�g���F ���ӭn���ѽL���ưʺA�s��
				switch ((int) moves[0]) {// ���e�ѧ��Ȧs���x�s���B�� Y�y��+1 �ŦX�@��H�����Шt
											// �]���{����P�_�Ӧ�m�w���ӦZ
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
			// String query�Q�g���F ���ӭn���ѽL���ưʺA�s��
			// �r�ꪺ�걵�Ĳv�n�ﵽ
			String query = "eight_queens([";
			// for(int i = 1;i<9;i++)�Q�g���F ���ӭn���ѽL���ưʺA�s��
			for (int i = 1; i < 9; i++) {
				if (y[i - 1] != 0) { // if y[i-1] != 0 �N�����ѽL�W�����쪺��l����ӦZ
					query = query + "(" + i + "," + y[i - 1];
				} else {
					query = query + "(" + i + "," + "Y" + i;
				}
				// if(i !=8) ���ӭn���ѽL���ưʺA�s��
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
	 * �]�w���䱱��O�W"�U�@�ո�"���s��������\��
	 * 
	 */
	private void setShowNextAnswerButton() {
		showNextAnswer = new JButton("�U�@�ո�");
		showNextAnswer.setFont(new Font("�s�ө���", Font.PLAIN, 40));

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
				chessBoardStatus.clearChessMove(); // �M�ŴѨB
				int tempGrid;
				if (getCurrentGameMode() == Mode.demo) { // �t��k�Ҧ��U�~�i��
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
	 * �]�w���䱱��O�W"   "���s��������\�� �ثe�S���Ψ� ���ݥH���X�R�ΩҥH�]���������i��
	 * 
	 */
	private void setShowLastAnswerButton() {
		showLastAnswer = new JButton("�W�@�ո�");
		showLastAnswer.setFont(new Font("�s�ө���", Font.PLAIN, 40));
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
		 * �p�G�����ե�setVisible(false); ���s���Ӫ��ϰ�|�����Q�л\�L�h �n�אּ ���e���s��ڤW�����e�h�F�����æ��O�e�Ŷ������s
		 */
		// no.setBorderPainted(false); //���e���s�����
		// no.setOpaque(false); //���s�]���z��
		showLastAnswer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessBoardStatus.clearChessMove(); // �M�ŴѨB
				int tempGrid;
				if (getCurrentGameMode() == Mode.demo) { // �t��k�Ҧ��U�~�i��
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
		if (getCurrentGameMode() == Mode.none) { // none�Ҧ��U
			completeNow.setEnabled(false); // ���i�H�I�����䱱��O��"�ߨ觹��"���s
			showNextAnswer.setEnabled(false); // ���i�H�I�����䱱��O��"�U�@�ո�"���s
			showLastAnswer.setEnabled(false);
			// ���ӭn��M��flag������O���a��h��{ flag�O�Ψӭp��demo�Ҧ��ثedemo��ĴX�ոѥΪ� ��button���A�L��
		} else if (getCurrentGameMode() == Mode.normal) { // normal�Ҧ��U
			completeNow.setEnabled(true); // �i�H�I�����䱱��O��"�ߨ觹��"���s
			showNextAnswer.setEnabled(false); // ���i�H�I�����䱱��O��"�U�@�ո�"���s
			showLastAnswer.setEnabled(false);
			// ���ӭn��M��flag�o�ӥ\�ಾ��O���a��h��{ flag�O�Ψӭp��demo�Ҧ��ثedemo��ĴX�ոѥΪ� ��button���A�L��
		} else { // demo�Ҧ��Udemo

			/*
			 * ����bdemo�Ҧ�����l�ƤU�Ԧ��M�� ���M�������U�Ԧ��M�檺�ƭ� �ҥH���l�ƤU�Ԧ��M�檺�u�@��Ѧb��L�C�����A�ɰ���
			 */
			completeNow.setEnabled(false); // ���i�H�I�����䱱��O��"�ߨ觹��"���s
			// showNextAnswer.setEnabled(false); //���i�H�I�����䱱��O��"�U�@�ո�"���s

			// �U�Ԧ��M���諸�ѽL���ӳ��n��demo �ҥH�H��u�n demoGrids == null �N�i�H�I��"�U�@�ո�"���s
			// demoGrids == 8�g���F ���ӭn��ʺA�s����ثe���ѽL����

			// if(demoGrids == 8){ //�p�Gdemo���ѽL���ƬO8 �i�H�I��"�U�@�ո�"���s
			// rightControlPanel.getCurrentSelcetDemoGrids();
			// ���g �[�c���V
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