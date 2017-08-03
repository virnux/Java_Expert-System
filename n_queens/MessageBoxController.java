package n_queens;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;



class MessageBoxController {
	private Timer gameTimer;
	private long timerStartTime;
	private JLabel messageBox;
	private ChessBoardStatus chessBoardStatus;
	
	public MessageBoxController(ChessBoardStatus currentGameStatus){
		this.chessBoardStatus = currentGameStatus;
		this.messageBox = new JLabel("", SwingConstants.CENTER);
		this.messageBox.setBackground(Color.GRAY);
		this.messageBox.setOpaque(true);
		this.messageBox.setFont(new Font("�з���", Font.PLAIN, 50));
		initializeMessageBox();
	}
	
	public JLabel getMessageBox(){
		return this.messageBox;
	}
	
	private Mode getCurrentGameMode(){
		return chessBoardStatus.getGameMode();
	}
	
	public void repaint(){
		chessBoardStatus.refreshChessBoard();
	}
	
	public void setMessageText(String text){
		this.messageBox.setText(text);
	}
	
	public void setMessageCompleted(){
		this.messageBox.setText("Completed");
	}
	
	public void setMessageNoAnswer(){
		this.messageBox.setText("�����L��");
	}
	
	public void setMessageCorrectAnswer(){
		this.messageBox.setText("���ץ��T");
	}
	
	public void setGameMode(Mode mode){
		this.chessBoardStatus.setGameMode(mode);
	}
	
	public void addChessMove(int i, int y ){
		this.chessBoardStatus.addChessMove(i, y);
	}
	
	/**
	 * ��l�ƶi�J�C�����w��T���M�C�����A�ê�l�ƭp�ɾ�
	 * 
	 * @param messageBox
	 *            �t�d��X�w��T����JLabel����
	 */

	private void initializeMessageBox() {
		this.messageBox.setText("�w��i�J�C��");
		showTimer();
	}

	/**
	 * �N�p�ɾ��k�s
	 * 
	 */
	public void setMessageTextDemoMode(){
		messageBox.setText("�п�N���ѽL���I���}�l�i�ܥH�ܽd�ѵ�");
	}

	public void StartTimer(){
		timerStartTime = System.currentTimeMillis();
	}


	/**
	 * ��l�ƭp�ɾ�
	 * 
	 * actionPerformed�ݸɥR
	 * 
	 * @param messageBox
	 *            �t�d��X�����C���g�L�h�[�ɶ���JLabel����
	 */

	private void showTimer() {
		gameTimer = new Timer(100, new ActionListener() {
			int hourPassed = 0;
			int minutePassed = 0;
			int secondPassed = 0;

			public void actionPerformed(ActionEvent e) {
				
				if (getCurrentGameMode() == Mode.normal) {			

					long currentTime = System.currentTimeMillis();
					int timePassed = (int) (currentTime - timerStartTime) / 1000;

					if (timePassed >= 60 * 60) { // 3600��@�p�� �C���}�l�ạ�@�p�ɥH�W
						hourPassed = (timePassed) / 3600; // �i�঳��
						minutePassed = ((timePassed) % 3600) / 60; // �i�঳��
						secondPassed = (timePassed) % 60; // �i�঳��

						messageBox.setText("�L�F" + hourPassed + "��" + minutePassed + "��" + secondPassed + "��");
					} else if (timePassed < 3600 && timePassed >= 60) { // 60��@����
																		// �C���}�l�ạ�@����
																		// �����@�p��
						minutePassed = (timePassed) / 60;
						secondPassed = (timePassed) % 60;

						messageBox.setText("�L�F" + minutePassed + "��" + secondPassed + "��");
					} else { // timePassed<60 �C���}�l�᥼���@����
						messageBox.setText("�L�F" + timePassed + "��");
					}
				}
			}
		});
		gameTimer.start();
	}
}
