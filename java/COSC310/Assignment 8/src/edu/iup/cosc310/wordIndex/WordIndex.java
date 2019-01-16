package edu.iup.cosc310.wordIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import edu.iup.cosc310.util.HashDictionary;

/**
 * word counter class, accepts a text file and prints how many times each word
 * occurs in the file.
 * 
 * @author David Kornish
 * 
 */
public class WordIndex {

	/**
	 * Main method to run the program, takes a text file as an argument
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		HashDictionary<String, ArrayList> wordIndex = new HashDictionary<String, ArrayList>();

		Scanner scan = new Scanner(new File(args[0]));

		// ignore the whitespace and punctuation with the following delimiter regex
		scan.useDelimiter("\\W+");

		// fill dictionary with items from input file
		// keep track of lines, a page is 50 lines
		int currentLine = 0;
		int currentPage = 1;

		while (scan.hasNext()) {
			// to keep track of pages, read input file by lines
			String inputLine = scan.nextLine().trim();

			// split current line into words
			String[] splitted = inputLine.split("\\W+");

			// add words into word index
			for (String word : splitted) {
				if (word.length() == 0) {
					continue;
				}

				// lists of pages found for each word will be stored in ArrayLists
				ArrayList list = (ArrayList) wordIndex.get(word);

				if (list == null) {
					// word is not in index yet, add it
					// create a new ArrayList
					ArrayList newList = new ArrayList();
					newList.add(currentPage);
					wordIndex.put(word, newList);

				} else {
					// the word already was indexed

					// if the current page is not in the list, add it
					if (!list.contains(currentPage)) {
						list.add(currentPage);
						wordIndex.put(word, list);
					}
				}

			}
			currentLine++;
			// since line and page are ints, division truncates and rounds down,
			// so add one when calculating page
			currentPage = (currentLine / 50) + 1;
			// currentPage = currentLine;
		}

		// index dictionary finished, sort the keys

		// instantiate an iterator
		Iterator<String> iterator = wordIndex.keys();

		// create arrayList of Keys to sort
		ArrayList<String> keys = new ArrayList<String>();

		// cycle through iterator adding keys
		while (iterator.hasNext()) {
			String nextKey = iterator.next();
			if (!keys.contains(nextKey)) {
				keys.add(nextKey);
			}
		}

		Collections.sort(keys);

		for (String k : keys) {
			ArrayList pages = (ArrayList) wordIndex.get(k);
			System.out.printf("%-20s", k);
			for (Object i : pages) {
				System.out.print(i + " ");
			}
			System.out.println();
		}

	}

}
