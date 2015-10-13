package za.co.lawrence.runthreads;

public class ThreadCo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		RunThread rt = new RunThread();

		Thread t = new Thread(rt ,"Lawrence");
		Thread t2 = new Thread(rt , "Mukesh");
		
		t.start();
		t2.start();
	}

}
