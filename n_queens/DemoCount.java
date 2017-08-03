package n_queens;

class DemoCount {
	private NQueensQuerySolutionDemo fourQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo fiveQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo sixQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo sevenQueensQuerySolutionDemo;
	private NQueensQuerySolutionDemo eightQueensQuerySolutionDemo;
	private NineQueensQuerySolutionDemo nineQueensQuerySolutionDemo;

	public DemoCount() {
		initializeDemoCount();
	}

	public void initializeDemoCount() {
		fourQueensQuerySolutionDemo = new FourQueensQuerySolutionDemo();
		fiveQueensQuerySolutionDemo = new FiveQueensQuerySolutionDemo();
		sixQueensQuerySolutionDemo = new SixQueensQuerySolutionDemo();
		sevenQueensQuerySolutionDemo = new SevenQueensQuerySolutionDemo();
		eightQueensQuerySolutionDemo = new EightQueensQuerySolutionDemo();
		nineQueensQuerySolutionDemo = new NineQueensQuerySolutionDemo();
	}

	public void clearDemoCounts() {
		fourQueensQuerySolutionDemo.cleardemoCount();
		fiveQueensQuerySolutionDemo.cleardemoCount();
		sixQueensQuerySolutionDemo.cleardemoCount();
		sevenQueensQuerySolutionDemo.cleardemoCount();
		eightQueensQuerySolutionDemo.cleardemoCount();
		nineQueensQuerySolutionDemo.cleardemoCount();
	}

	public NQueensQuerySolutionDemo getNineQueensQuerySolutionDemo() {
		return nineQueensQuerySolutionDemo;
	}

	public NQueensQuerySolutionDemo getEightQueensQuerySolutionDemo() {
		return eightQueensQuerySolutionDemo;
	}

	public NQueensQuerySolutionDemo getSevenQueensQuerySolutionDemo() {
		return sevenQueensQuerySolutionDemo;
	}

	public NQueensQuerySolutionDemo getSixQueensQuerySolutionDemo() {
		return sixQueensQuerySolutionDemo;
	}

	public NQueensQuerySolutionDemo getFiveQueensQuerySolutionDemo() {
		return fiveQueensQuerySolutionDemo;
	}

	public NQueensQuerySolutionDemo getFourQueensQuerySolutionDemo() {
		return fourQueensQuerySolutionDemo;
	}
}
