package edu.iup.cosc310.wordcount;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner; 

import edu.iup.cosc310.util.BSTDictionary;

/**
 * word counter class, accepts a text file and prints how many times each word
 * occurs in the file.
 * 
 * @author David Kornish
 * 
 */
public class Wordcount {

	/**
	 * Main method to run the program, takes a text file as an argument
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BSTDictionary<String, Integer> wordCounts = new BSTDictionary<String, Integer>();

		Scanner scan = new Scanner(new File(args[0]));

		// ignore the whitespace and punctuation with the following delimiter regex
		scan.useDelimiter("\\W+");

		// fill dictionary with items from input file
		while (scan.hasNext()) {
			String word = scan.next().trim();

			if (word.length() == 0) {
				continue;
			}

			Integer count = (Integer) wordCounts.get(word);

			if (count == null) {
				wordCounts.put(word, 1);
			} else {
				wordCounts.put(word, count + 1);
			}
		}
		
		// instantiate an iterator
		Iterator iterator = wordCounts.keys();
		BSTDictionary dictionary = new BSTDictionary();
		
		// cycle through iterator
		while (iterator.hasNext()) {
			dictionary = (BSTDictionary) iterator.next();
			String key = (String) dictionary.getHighestKey();
			int value = (int) dictionary.get(key);
			System.out.println(key + " " + value);
		}

	}

}
