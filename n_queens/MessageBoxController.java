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
		this.messageBox.setFont(new Font("標楷體", Font.PLAIN, 50));
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
		this.messageBox.setText("此局無解");
	}
	
	public void setMessageCorrectAnswer(){
		this.messageBox.setText("答案正確");
	}
	
	public void setGameMode(Mode mode){
		this.chessBoardStatus.setGameMode(mode);
	}
	
	public void addChessMove(int i, int y ){
		this.chessBoardStatus.addChessMove(i, y);
	}
	
	/**
	 * 初始化進入遊戲的歡迎訊息和遊戲狀態並初始化計時器
	 * 
	 * @param messageBox
	 *            負責輸出歡迎訊息的JLabel元件
	 */

	private void initializeMessageBox() {
		this.messageBox.setText("歡迎進入遊戲");
		showTimer();
	}

	/**
	 * 將計時器歸零
	 * 
	 */
	public void setMessageTextDemoMode(){
		messageBox.setText("請選N路棋盤並點擊開始展示以示範解答");
	}

	public void StartTimer(){
		timerStartTime = System.currentTimeMillis();
	}


	/**
	 * 初始化計時器
	 * 
	 * actionPerformed待補充
	 * 
	 * @param messageBox
	 *            負責輸出此局遊戲經過多久時間的JLabel元件
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

					if (timePassed >= 60 * 60) { // 3600秒一小時 遊戲開始後滿一小時以上
						hourPassed = (timePassed) / 3600; // 可能有錯
						minutePassed = ((timePassed) % 3600) / 60; // 可能有錯
						secondPassed = (timePassed) % 60; // 可能有錯

						messageBox.setText("過了" + hourPassed + "時" + minutePassed + "分" + secondPassed + "秒");
					} else if (timePassed < 3600 && timePassed >= 60) { // 60秒一分鐘
																		// 遊戲開始後滿一分鐘
																		// 未滿一小時
						minutePassed = (timePassed) / 60;
						secondPassed = (timePassed) % 60;

						messageBox.setText("過了" + minutePassed + "分" + secondPassed + "秒");
					} else { // timePassed<60 遊戲開始後未滿一分鐘
						messageBox.setText("過了" + timePassed + "秒");
					}
				}
			}
		});
		gameTimer.start();
	}
}
