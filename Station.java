import java.lang.StringBuilder;

/**
 * * @Name: Kyle Gildea
 * Course: CNT 4714 Spring 2017  
 * Assignment title: Project 2 – Multi-threaded programming in Java  
 * Date:  February 12, 2017  
 * Class:  Station.java
 */
public class Station implements Runnable
{
	final int sleepWait = 1000;
	private int stationID;
	private int workload;
	private Conveyer frontConveyer;
	private Conveyer rearConveyer;



	/**
	 * @param stationID Station integer ID number 1 - number of stations
	 * @param workload number of packages to be worked
	 * @param frontConveyer station's front conveyer
	 * @param rearConveyer station's rear conveyer
	 */
	public Station(int stationID, int workload, Conveyer frontConveyer, Conveyer rearConveyer) {
		super();
		this.stationID = stationID;
		this.workload = workload;
		this.frontConveyer = frontConveyer;
		this.rearConveyer = rearConveyer;
	}



	/**
	 * @return station idnumber
	 */
	public int getStationID() {
		return stationID;
	}



	/**
	 * @param stationID sets station id number
	 */
	public void setStationID(int stationID) {
		this.stationID = stationID;
	}



	/**
	 * @return station workload
	 */
	public int getWorkload() {
		return workload;
	}



	/**
	 * @param workload sets station workload size
	 */
	public void setWorkload(int workload) {
		this.workload = workload;
	}



	/**
	 * @return station front conveyer
	 */
	public Conveyer getFrontConveyer() {
		return frontConveyer;
	}


	/**
	 * @param frontConveyer set station front conveyer
	 */
	public void setFrontConveyer(Conveyer frontConveyer) {
		this.frontConveyer = frontConveyer;
	}


	/**
	 * @return station rear conveyer
	 */
	public Conveyer getRearConveyer() {
		return rearConveyer;
	}


	/**
	 * @param rearConveyer set station rear conveyer
	 */
	public void setRearConveyer(Conveyer rearConveyer) {
		this.rearConveyer = rearConveyer;
	}

	/*
	 * prints when lock is achieved
	 */
	private void printGotLock(int conveyerID)
	{
		System.out.println("Station " + this.getStationID() + ": granted access to conveyer " 
				+ conveyerID);
	}

	/*
	 * prints when lock is given up
	 */
	private void printEndLock(int conveyerID)
	{
		System.out.println("Station " + this.getStationID() + ": released access to conveyer " 
				+ conveyerID);
	}
	/**
	 * run method
	 * runs while station workload > 0;
	 * Attempts to get lock on front conveyer if successful, prints, then attempts to get rear conveyer lock
	 * If successful, prints, then calls doWork() to send packages down conveyer. AFter do work, unlocks front
	 * and rear conveyer and prints.
	 * 
	 * If front lock is achieved, but rear lock is not, prints both success and release of front lock, and unlocks front lock then sleeps for a random
	 * time period. If front lock can not be achieved, thread prints nothing, just sleeps..
	 */
	@Override
	public void run() {
		while(this.getWorkload() > 0)
		{
			if(this.frontConveyer.getLock())
			{
				printGotLock(this.frontConveyer.getConveyerId());

				if(this.rearConveyer.getLock())
				{
					printGotLock(this.rearConveyer.getConveyerId());

					doWork();
					this.frontConveyer.unlockConveyer();
					printEndLock(this.frontConveyer.getConveyerId());
					this.rearConveyer.unlockConveyer();
					printEndLock(this.rearConveyer.getConveyerId());
					try {
						Thread.sleep((int)(Math.random()*sleepWait));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else
				{
					this.frontConveyer.unlockConveyer();
					printEndLock(this.frontConveyer.getConveyerId());

					try {
						Thread.sleep((int)(Math.random()*sleepWait));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else
			{
				try {
					Thread.sleep((int)(Math.random()*sleepWait));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * decreases station workload by 1. Announces if work is complete. Runs dummy loop to simulate work time
	 */
	public void doWork()
	{
		this.setWorkload(this.getWorkload() - 1);
		if(this.getWorkload() > 0)
		{
			
			System.out.println("Station " + this.getStationID() + ": moves packages on conveyer " + this.rearConveyer.getConveyerId() + ", "+ this.workload + " remaining.");
			for(int i =0; i < 300000; i++);
		}
		if(this.getWorkload() == 0)
		{
			System.out.println("*************************Station " + this.getStationID() + ": workload successfully completed!*************************");
		}
	}



	@Override
	public String toString() 
	{
			StringBuilder s = new StringBuilder();
			
			s.append("Station " + getStationID() + ": In-Connection set to conveyer "
					+ getFrontConveyer().getConveyerId());
			s.append("\nStation " + getStationID() + ": Out-Connection set to conveyer "
					+ getRearConveyer().getConveyerId());
			s.append("\nStation " + getStationID() + ": Workload set to "
					+ getWorkload());
			
			return s.toString();
	}	
}