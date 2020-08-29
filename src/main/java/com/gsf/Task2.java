package com.gsf;

import static java.util.Comparator.reverseOrder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task2 {
	
	private static Logger LOGGER = Logger.getLogger(Task2.class.getName());

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

	public static void main(String[] args) throws URISyntaxException {

		//load file from resource path
		URL url = Thread.currentThread().getContextClassLoader().getResource("input2.txt");

		Path inputPath = Paths.get(url.toURI());
 
		List<String> wordList = new ArrayList<>(); 
		 
		try {
			//read all lines and add those into a list
			List<String> result = Files.readAllLines(inputPath, StandardCharsets.UTF_8);

			//as the lines are more than one, we need to create a list with single words 
			//in order to iterate into that list and count. We need to replace . with empty String to split the words
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String string = iterator.next();
				String[] wordLine = string.replace('.', ' ').replaceAll("\\s+", " ").split(" ");
				wordList.addAll(Arrays.asList(wordLine));
			}

			//Regular expression to filter words having numbers
			Predicate<String> theFilter = Pattern.compile("^[a-zA-Z\\-]*$").asPredicate();

			//filter numeric and count each word by ordering alphabetical
			wordList.stream().filter(theFilter)

					.map(i -> i.toLowerCase()).sorted(Comparator.reverseOrder())
					.collect(Collectors.groupingBy(i -> i, Collectors.counting())).entrySet().stream()
					.sorted(Map.Entry.<String, Long>comparingByValue(reverseOrder())
							.thenComparing(Map.Entry.comparingByKey()))
					.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
					.forEach((k, v) -> LOGGER.log(Level.INFO,   "{0} - {1}" ,new String[] { k.getKey(), String.valueOf( k.getValue() )} )  );
 
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
