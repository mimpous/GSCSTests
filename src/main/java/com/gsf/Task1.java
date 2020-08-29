package com.gsf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Task1 {
	
	/**
	 * 
	 */
	private static Logger LOGGER = Logger.getLogger(Task1.class.getName());

	/**
	 * 
	 */
	static {
	      InputStream stream = Thread.currentThread().getContextClassLoader().
	              getResourceAsStream("log4j.properties");
	      try {
	          LogManager.getLogManager().readConfiguration(stream);
	          LOGGER= Logger.getLogger(Task1.class.getName());

	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	  }
	
	 
	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param from
	 * @param func
	 * @param generator
	 * @return Array of Integer
	 */
	public static <T, U> U[] convertArray(T[] from, 
	                                      Function<T, U> func, 
	                                      IntFunction<U[]> generator) {
	    return Arrays.stream(from).map(func).toArray(generator);
	}

	/**
	 * 
	 * @param args
	 * @throws URISyntaxException
	 */
	public static void main(String[] args) throws URISyntaxException { 
		
		//reading file
		URL url =  Thread.currentThread().getContextClassLoader().getResource("input1.txt");
		Path path = Paths.get(url.toURI());
		
		List<String> result ; 
		int total = 0;
		ArithmeticCalculation addition = ( int l, int w, int h) -> (2*l*w+2*w*h+2*h*l + w); 
		
		try {
			//convert data from file into list of Strings
			result = Files.readAllLines(path, StandardCharsets.UTF_8);
			
			//loop in the list and parse each row
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String dimentions = iterator.next();
				
				//convert to integer Array from String, removing multiply sign from file
				Integer[] arrayOfInt = convertArray(dimentions.split("x"), Integer::parseInt, Integer[]::new); 
				
				//execute algorithm
				int sumaryPerRoom=addition.calculate(arrayOfInt[0],arrayOfInt[1],arrayOfInt[2]);
				
				//display result
				LOGGER.log(Level.INFO,  "{0} = {1}" , new String[] {dimentions, String.valueOf( sumaryPerRoom ) } );
				
				//add in total sumary
 				total+=sumaryPerRoom;
			}
			
			//print total
			LOGGER.log(Level.INFO , "total is : {0}",total); 
			
		} catch (IOException e1) { 
			e1.printStackTrace();
		}
 
	}
	 
}
