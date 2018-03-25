import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Name: Kyle Gildea
 * Course: CNT 4714 Spring 2017  
 * Assignment title: Project 2 – Multi-threaded programming in Java  
 * Date:  February 12, 2017  
 * Class:  Conveyer.java
 */
public class Conveyer 
{
	private int conveyerId;
	private Lock lock;

	/**
	 * @param conveyerId
	 */
	public Conveyer(int conveyerId) 
	{
		super();
		this.conveyerId = conveyerId;
		this.lock = new ReentrantLock();
	}

	/**
	 * @return the conveyerId
	 */
	public int getConveyerId() 
	{
		return conveyerId;
	}

	/**
	 * @param conveyerId the conveyerId to set
	 */
	public void setConveyerId(int conveyerId) 
	{
		this.conveyerId = conveyerId;
	}

	/**
	 * @return true if lock is obtained
	 */
	public Boolean getLock()
	{
		return this.lock.tryLock();
	}


	/**
	 * unlocks locked conveyer
	 */
	public void unlockConveyer()
	{
		this.lock.unlock();
	}
}

