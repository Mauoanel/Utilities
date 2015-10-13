package za.co.lawrence.runthreads;

public class RunThread implements Runnable {

	public void race() {

		for (int i = 0; i <= 100; i++) {
			System.out.println("Distance covered by : "
					+ Thread.currentThread().getName() + " is " + i + " Kms");

			boolean t = isWinner(i);

			if (t) {
				System.out.println("The Winner is : "
						+ Thread.currentThread().getName());
				
				System.out.println(Thread.activeCount());
				break;
			}
		}
	}

	private boolean isWinner(int i) {

		if (i == 99) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.race();
	}

}
