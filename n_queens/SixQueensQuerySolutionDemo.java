package n_queens;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jpl7.Term;

class SixQueensQuerySolutionDemo extends NQueensQuerySolutionDemo {
	private int sixQueensSolutoinCount = 4;
	public void inferNQueensSolutionNum() {
		prolog.link(ruleBase);
		List result = prolog.drive("findall([Y1,Y2,Y3,Y4,Y5,Y6],six_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6)]),R), length(R,N).");
		Iterator<Map<String, Term>> it = result.iterator();

		while (it.hasNext()) {
			Map<String, Term> key = it.next();
			String temp = key.toString();
			int firstindex = temp.lastIndexOf(" N=");
			int lastindex = temp.lastIndexOf("}");
			String count = temp.substring(firstindex + 3, lastindex);

			messageBoxController.setMessageText("���ӦZ���D�@��" + count + "�ո�");
		}
	}
	
	public void NQueensLastSolutionDemo(int grids) {
		String query = "six_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6)]).";
		// �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D

		List result = inferNqueensSolutionDemo(query);
		ListIterator<String> it = result.listIterator();

		while (it.hasNext()) {
			tempAnswer.add(it.next());
		}

		if (demoCount < getSixQueensSolutionCounts()) { // flag 0 ~ 3�̦h�@4��
			flagCountMinus();
			if (demoCount > -1) {
				String temp = tempAnswer.get(demoCount);
				for (int i = 0; i < grids; i++) {
					int index = temp.indexOf("Y" + (i + 1) + "=");
					String y = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(y) - 1;
					chessBoardStatus.addChessMove(i, coordinateY);
				}
				messageBoxController.setMessageText("���ӦZ���D�@��"+getSixQueensSolutionCounts()+"�ո� �ثe�O��" + (demoCount + 1) + "�ظ�");
				// messageBox.setText�Q�g���F ���ӭn����X�Ӫ�N�ӦZ���D�ѼưʺA�s��
				leftControlPanel.enabledShowLastAnswer();
			}
		}
		disabledShowAnswerButton(getSixQueensSolutionCounts()-1);
	}

	public void NQueensNextSolutionDemo(int grids) {
		String query = "six_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6)]).";
		// �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D

		List result = inferNqueensSolutionDemo(query);
		ListIterator<String> it = result.listIterator();

		while (it.hasNext()) {
			tempAnswer.add(it.next());
		}

		if (demoCount < getSixQueensSolutionCounts()) { // flag 0 ~ 3�̦h�@4��
			flagCountAddOne();
			if (demoCount > -1) {
				String temp = tempAnswer.get(demoCount);
				for (int i = 0; i < grids; i++) {
					int index = temp.indexOf("Y" + (i + 1) + "=");
					String y = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(y) - 1;
					chessBoardStatus.addChessMove(i, coordinateY);
				}
				messageBoxController.setMessageText("���ӦZ���D�@��"+getSixQueensSolutionCounts()+"�ո� �ثe�O��" + (demoCount + 1) + "�ظ�");
				// messageBox.setText�Q�g���F ���ӭn����X�Ӫ�N�ӦZ���D�ѼưʺA�s��
				leftControlPanel.enabledShowLastAnswer();
			}
		}
		disabledShowAnswerButton(getSixQueensSolutionCounts()-1);
	}
	
	private int getSixQueensSolutionCounts(){
		return sixQueensSolutoinCount;
	}
}
