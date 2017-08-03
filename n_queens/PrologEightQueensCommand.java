package n_queens;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jpl7.Term;

class PrologEightQueensCommand{
	
	private Prolog prolog;
	private MessageBoxController messageBoxController;
	private NQueensQuerySolutionDemo queryCommand;
	final private static String FOURQUEENS_RULEBASE = "four_queens.pl";
	final private static String FIVEQUEENS_RULEBASE = "five_queens.pl";
	final private static String SIXQUEENS_RULEBASE = "six_queens.pl";
	final private static String SEVENQUEENS_RULEBASE = "seven_queens.pl";
	final private static String EIGHTQUEENS_RULEBASE = "eight_queens.pl";
	final private static String NINEQUEENS_RULEBASE = "nine_queens.pl";
	final private static String TENQUEENS_RULEBASE = "ten_queens.pl";
	
	
	public PrologEightQueensCommand(MessageBoxController messageBoxController){
		prolog = new Prolog();
		this.messageBoxController = messageBoxController;
	}
	
	public void infer_Eightqueens_Solution_TrueorFalse(String query) {
		/*
		 * 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // 與prolog檔案進行連結

		if (prolog.drive_TrueORFalse(query)) { // 呼叫Prolog類別內的方法進行判斷此局是正確解答之一
			
			messageBoxController.setMessageCorrectAnswer(); // 停止計時器
			messageBoxController.setGameMode(Mode.none);
			
		} else { // 此局不是正確解答
			JOptionPane.showMessageDialog(null, "請再檢查看看", "答案錯誤", JOptionPane.ERROR_MESSAGE);
			// 顯示小視窗告知使用者答案錯誤
		}

	}
	

	/**
	 * 連結prolog檔案並傳回所有八皇后問題可行解的串列。
	 * 
	 * @param query
	 *            傳給SWI-prolog判斷八皇后問題可行解有哪些的語句
	 * @return 所有八皇后問題可行解
	 */

	public List infer_Eightqueens_SolutionDemo(String query) {
		/*
		 * 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // 與prolog檔案進行連結
		List result = prolog.drive_demo(query); // 呼叫Prolog類別內的方法並傳回可行的答案串列
		return result;
	}
	
	/**
	 * 算出八皇后問題有幾組解
	 * 
	 */

	/*
	 * 為什麼key.toString()出來會是 ...N=92 ,Y8=... 要研究一下
	 */
	public void inferEightqueensSolutionNumber() {
		/*
		 * 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題
		 */
		prolog.link(EIGHTQUEENS_RULEBASE);
		List result = prolog.drive(
				"findall([Y1,Y2,Y3,Y4,Y5,Y6,Y7,Y8],eight_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6),(7,Y7),(8,Y8)]),R), length(R,N).");
		Iterator<Map<String, Term>> it = result.iterator();

		while (it.hasNext()) {
			Map<String, Term> key = it.next();
			String temp = key.toString();
			/* 這部分被寫死了 應該要能抓到動態的資料長度 而不是依靠lastIndexOf(Y8)來判斷 */
			int firstindex = temp.lastIndexOf(" N=");
			int lastindex = temp.lastIndexOf("Y8");
			String count = temp.substring(firstindex + 3, lastindex - 2);
			
			
			//以後處理
			//messageBox.setText("八皇后問題共有" + count + "組解");
		}
	}
	
	/**
	 * 連結prolog檔案並判斷當前八皇后棋局可能有哪些可行解。 若無解則則改變輸出給使用者的訊息並停止計時器。 若有解則自動完成剩餘的棋局。
	 * 
	 * @param query
	 *            傳給SWI-prolog判斷當前八皇后棋局可能有哪些可行解的語句
	 */

	public void infer_Eightqueens_Solution(String query) {
		/*
		 * 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // 與prolog檔案進行連結
		if (prolog.drive_TrueORFalse(query)) { // 當前八皇后棋局有解
			List result = prolog.drive(query); // 呼叫Prolog類別內的方法並傳回此局可行的答案串列
			/*
			 * 把SWI Prolog傳回來的八皇后問題答案串列擷取成字串
			 */
			Iterator<Map<String, Term>> it = result.iterator();
			String temp = new String();
			while (it.hasNext()) {
				Map<String, Term> key = it.next();
				temp = key.toString();
			}

			/*
			 * 把SWI Prolog傳回來的八皇后問題答案串列擷取成字串後再把字串轉成座標
			 */
			for (int i = 0; i < 8; i++) { // Y1到Y8
				int index = temp.indexOf("Y" + (i + 1)); // 抓取對應的字串 index的值
				if (index != -1) { // 如果沒有抓到對應的字串 index的值就會是-1
					String count = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(count) - 1;
					messageBoxController.addChessMove( i, coordinateY );
				}
			}
			messageBoxController.setMessageCompleted();
			messageBoxController.repaint();
		} else { // 若當前八皇后棋局無解則改變輸出給使用者的訊息並停止計時器
				
			messageBoxController.setMessageNoAnswer();
			
		}
		messageBoxController.setGameMode( Mode.none);
	}
}