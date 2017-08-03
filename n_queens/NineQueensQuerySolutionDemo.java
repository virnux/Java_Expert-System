package n_queens;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jpl7.Term;

class NineQueensQuerySolutionDemo extends NQueensQuerySolutionDemo {
	private int nineQueensSolutoinCount = 352;
	public void inferNQueensSolutionNum()  {
		prolog.link(ruleBase);
		List result = prolog.drive(
				"findall([Y1,Y2,Y3,Y4,Y5,Y6,Y7,Y8,Y9],nine_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6),(7,Y7),(8,Y8),(9,Y9)]),R), length(R,N).");
		Iterator<Map<String, Term>> it = result.iterator();
		
		while (it.hasNext()) {
		
			Map<String, Term> key = it.next();
			String temp = key.toString();
			/* 這部分被寫死了 應該要能抓到動態的資料長度 而不是依靠lastIndexOf(Y8)來判斷 */
			int firstindex = temp.lastIndexOf(" N=");
			int lastindex = temp.lastIndexOf(", ");
			String count = temp.substring(firstindex+3, lastindex);
			messageBoxController.setMessageText("九皇后問題共有" + count + "組解");
			
		}
	}

	public void NQueensLastSolutionDemo(int grids) {
		String query = "nine_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6),(7,Y7),(8,Y8),(9,Y9)]).";
		// 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題

		/*
		 * prolog.link(ruleBase); // 與prolog檔案進行連結 List result =
		 * prolog.drive_demo(query); // 呼叫Prolog類別內的方法並傳回可行的答案串列
		 */

		List result = inferNqueensSolutionDemo(query);
		ListIterator<String> it = result.listIterator();

		while (it.hasNext()) {
			tempAnswer.add(it.next());
		}

		if (demoCount < getNineQueensSolutionCounts()) { // flag 0 ~ 351最多共352次
			flagCountMinus();
			if (demoCount > -1) {
				String temp = tempAnswer.get(demoCount);
				for (int i = 0; i < grids; i++) {
					int index = temp.indexOf("Y" + (i + 1) + "=");
					String y = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(y) - 1;
					chessBoardStatus.addChessMove(i, coordinateY);
				}
				messageBoxController
						.setMessageText("九皇后問題共有" + getNineQueensSolutionCounts() + "解 目前是第" + (demoCount + 1) + "種解");
				leftControlPanel.enabledShowLastAnswer();
				// messageBox.setText被寫死了 應該要能跟算出來的N皇后問題解數動態連結
			}
		}
		disabledShowAnswerButton(getNineQueensSolutionCounts() - 1);
	}

	public void NQueensNextSolutionDemo(int grids) {
		String query = "nine_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6),(7,Y7),(8,Y8),(9,Y9)]).";
		// 以後應該要隨著傳入N皇后而動態改變連結的prolog檔案 方法也需要重新命名 以後可能會多一個參數size 代表棋盤大小 也代表N皇后問題

		/*
		 * prolog.link(ruleBase); // 與prolog檔案進行連結 List result =
		 * prolog.drive_demo(query); // 呼叫Prolog類別內的方法並傳回可行的答案串列
		 */

		List result = inferNqueensSolutionDemo(query);
		ListIterator<String> it = result.listIterator();

		while (it.hasNext()) {
			tempAnswer.add(it.next());
		}

		if (demoCount < getNineQueensSolutionCounts()) { // flag 0 ~ 351最多共352次
			flagCountAddOne();
			if (demoCount > -1) {
				String temp = tempAnswer.get(demoCount);
				for (int i = 0; i < grids; i++) {
					int index = temp.indexOf("Y" + (i + 1) + "=");
					String y = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(y) - 1;
					chessBoardStatus.addChessMove(i, coordinateY);
				}
				messageBoxController
						.setMessageText("九皇后問題共有" + getNineQueensSolutionCounts() + "解 目前是第" + (demoCount + 1) + "種解");
				leftControlPanel.enabledShowLastAnswer();
				// messageBox.setText被寫死了 應該要能跟算出來的N皇后問題解數動態連結
			}
		}
		disabledShowAnswerButton(getNineQueensSolutionCounts() - 1);
	}

	private int getNineQueensSolutionCounts() {
		return nineQueensSolutoinCount;
	}

	
}
