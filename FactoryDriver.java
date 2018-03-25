import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Name: Kyle Gildea
 * Course: CNT 4714 Spring 2017  
 * Assignment title: Project 2 – Multi-threaded programming in Java  
 * Date:  February 12, 2017  
 * Class:  FactoryDriver.java
 */
public class FactoryDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int stations = getStations();

		ArrayList<Station> factory = new ArrayList<Station>(stations);
		ExecutorService e = Executors.newFixedThreadPool(stations);

		/*Conveyer c0 = new Conveyer(0);
		Conveyer c1 = new Conveyer(1);
		Conveyer c2 = new Conveyer(2);
		Conveyer c3 = new Conveyer(3);*/

		/*Station test = new Station(0, 3, c0, c1);
		Station test1 = new Station(1, 5, c1, c2);
		Station test2 = new Station(2, 8, c2, c3);*/

		factory = generateFactory(stations);

		try
		{
			for(int i = stations - 1; i >= 0; i--)
			{
				e.execute(factory.get(i));
			}
		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		e.shutdown();


	}

	/*
	 * Gets # of stations passed in as parameter
	 * Creates factory objects where each station's conveyers are connected
	 * reads workload in from config.txt
	 * in the front to the rear conveyer from the previous station
	 * returns ArrayList of those stations
	 */

	private static ArrayList<Station> generateFactory(int numStations) 
	{
		File configFile = new File("config.txt");
		FileReader fr;

		ArrayList<Station> factory = new ArrayList<Station>();

		try {
			fr = new FileReader(configFile);
			BufferedReader line = new BufferedReader(fr);
			line.readLine();

			for(int i = 0; i < numStations; i++)
			{
				if(i == 0)
					factory.add(new Station(i, Integer.parseInt(line.readLine()), 
							new Conveyer(numStations - 1), new Conveyer(i)));
				else if(i == numStations - 1)
				{
					factory.add(new Station(i, Integer.parseInt(line.readLine()), 
							factory.get(i - 1).getRearConveyer(), factory.get(0).getFrontConveyer()));
				}
				else
				{
					factory.add(new Station(i, Integer.parseInt(line.readLine()), 
							factory.get(i - 1).getRearConveyer(), new Conveyer(i)));
				}
				System.out.println(factory.get(i).toString());
			}
			System.out.println("\n");
			line.close();
			fr.close();
		} catch (IOException|NumberFormatException e) 
		{
			System.out.println("Config file not found.\n");
		}
		return factory;
	}

	/*
	 * prints station information on creation
	 */
	
	/**
	 * @return number of stations read in from file
	 */
	public static int getStations()
	{
		File configFile = new File("config.txt");
		FileReader fr;
		int numStations = -1;
		try {
			fr = new FileReader(configFile);
			BufferedReader line = new BufferedReader(fr);
			numStations = Integer.parseInt(line.readLine());
			line.close();
			fr.close();

		} catch (IOException|NumberFormatException e) 
		{
			System.out.println("Config file not found.\n");
		}
		return numStations;
	}
}
