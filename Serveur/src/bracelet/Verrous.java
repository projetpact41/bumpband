package bracelet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Verrous {

	public static Lock enCours = new ReentrantLock();
    public static Lock monSC = new ReentrantLock();
    public static Lock tonSC = new ReentrantLock();
    public static Semaphore sync1 = new Semaphore(0);
    public static Semaphore sync2 = new Semaphore(0);
    public static Semaphore sync3 = new Semaphore(0);
    public static Semaphore sync4 = new Semaphore(0);
	
}
