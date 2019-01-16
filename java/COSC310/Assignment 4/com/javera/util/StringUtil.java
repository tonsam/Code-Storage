package com.javera.util;

/**
 * @author dtsmith
 */
public class StringUtil {
	public static String trimLeadingDigits(String s) {
		int i = 0;
		while (i < s.length() && Character.isDigit(s.charAt(i))) {
			i++;
		}
		
		if (i == s.length()) {
			return "";
		} else {
			return s.substring(i);
		}
	}
	
	public static int parseLeadingDigits(String s) {
		int i = 0;
		int num = 0;
		
		while (i < s.length() && Character.isDigit(s.charAt(i))) {
			num = num * 10 + s.charAt(i++) - '0';
		}
		
		return num;
	}
	
}
