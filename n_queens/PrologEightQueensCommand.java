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
		 * �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // �Pprolog�ɮ׶i��s��

		if (prolog.drive_TrueORFalse(query)) { // �I�sProlog���O������k�i��P�_�����O���T�ѵ����@
			
			messageBoxController.setMessageCorrectAnswer(); // ����p�ɾ�
			messageBoxController.setGameMode(Mode.none);
			
		} else { // �������O���T�ѵ�
			JOptionPane.showMessageDialog(null, "�ЦA�ˬd�ݬ�", "���׿��~", JOptionPane.ERROR_MESSAGE);
			// ��ܤp�����i���ϥΪ̵��׿��~
		}

	}
	

	/**
	 * �s��prolog�ɮרöǦ^�Ҧ��K�ӦZ���D�i��Ѫ���C�C
	 * 
	 * @param query
	 *            �ǵ�SWI-prolog�P�_�K�ӦZ���D�i��Ѧ����Ǫ��y�y
	 * @return �Ҧ��K�ӦZ���D�i���
	 */

	public List infer_Eightqueens_SolutionDemo(String query) {
		/*
		 * �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // �Pprolog�ɮ׶i��s��
		List result = prolog.drive_demo(query); // �I�sProlog���O������k�öǦ^�i�檺���צ�C
		return result;
	}
	
	/**
	 * ��X�K�ӦZ���D���X�ո�
	 * 
	 */

	/*
	 * ������key.toString()�X�ӷ|�O ...N=92 ,Y8=... �n��s�@�U
	 */
	public void inferEightqueensSolutionNumber() {
		/*
		 * �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D
		 */
		prolog.link(EIGHTQUEENS_RULEBASE);
		List result = prolog.drive(
				"findall([Y1,Y2,Y3,Y4,Y5,Y6,Y7,Y8],eight_queens([(1,Y1),(2,Y2),(3,Y3),(4,Y4),(5,Y5),(6,Y6),(7,Y7),(8,Y8)]),R), length(R,N).");
		Iterator<Map<String, Term>> it = result.iterator();

		while (it.hasNext()) {
			Map<String, Term> key = it.next();
			String temp = key.toString();
			/* �o�����Q�g���F ���ӭn����ʺA����ƪ��� �Ӥ��O�̾alastIndexOf(Y8)�ӧP�_ */
			int firstindex = temp.lastIndexOf(" N=");
			int lastindex = temp.lastIndexOf("Y8");
			String count = temp.substring(firstindex + 3, lastindex - 2);
			
			
			//�H��B�z
			//messageBox.setText("�K�ӦZ���D�@��" + count + "�ո�");
		}
	}
	
	/**
	 * �s��prolog�ɮרçP�_��e�K�ӦZ�ѧ��i�঳���ǥi��ѡC �Y�L�ѫh�h���ܿ�X���ϥΪ̪��T���ð���p�ɾ��C �Y���ѫh�۰ʧ����Ѿl���ѧ��C
	 * 
	 * @param query
	 *            �ǵ�SWI-prolog�P�_��e�K�ӦZ�ѧ��i�঳���ǥi��Ѫ��y�y
	 */

	public void infer_Eightqueens_Solution(String query) {
		/*
		 * �H�����ӭn�H�۶ǤJN�ӦZ�ӰʺA���ܳs����prolog�ɮ� ��k�]�ݭn���s�R�W �H��i��|�h�@�ӰѼ�size �N��ѽL�j�p �]�N��N�ӦZ���D
		 */
		prolog.link(EIGHTQUEENS_RULEBASE); // �Pprolog�ɮ׶i��s��
		if (prolog.drive_TrueORFalse(query)) { // ��e�K�ӦZ�ѧ�����
			List result = prolog.drive(query); // �I�sProlog���O������k�öǦ^�����i�檺���צ�C
			/*
			 * ��SWI Prolog�Ǧ^�Ӫ��K�ӦZ���D���צ�C�^�����r��
			 */
			Iterator<Map<String, Term>> it = result.iterator();
			String temp = new String();
			while (it.hasNext()) {
				Map<String, Term> key = it.next();
				temp = key.toString();
			}

			/*
			 * ��SWI Prolog�Ǧ^�Ӫ��K�ӦZ���D���צ�C�^�����r���A��r���ন�y��
			 */
			for (int i = 0; i < 8; i++) { // Y1��Y8
				int index = temp.indexOf("Y" + (i + 1)); // ����������r�� index����
				if (index != -1) { // �p�G�S�����������r�� index���ȴN�|�O-1
					String count = temp.substring(index + 3, index + 4);
					int coordinateY = Integer.valueOf(count) - 1;
					messageBoxController.addChessMove( i, coordinateY );
				}
			}
			messageBoxController.setMessageCompleted();
			messageBoxController.repaint();
		} else { // �Y��e�K�ӦZ�ѧ��L�ѫh���ܿ�X���ϥΪ̪��T���ð���p�ɾ�
				
			messageBoxController.setMessageNoAnswer();
			
		}
		messageBoxController.setGameMode( Mode.none);
	}
}