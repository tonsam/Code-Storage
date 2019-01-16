// Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
// This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.util;

public class Comparison {
	public static boolean isEqual(String a, String b) {
		if (a == b) {
			return true;
		} else if (a == null || b== null) {
			return false;
		} else {
			return a.equals(b);
		}
	}
	
	public static boolean isEqual(java.sql.Date a, java.sql.Date b) {
		if (a == b) {
			return true;
		} else if (a == null || b== null) {
			return false;
		} else {
			return a.equals(b);
		}
	}
	
	public static boolean isEqual(java.util.Date a, java.util.Date b) {
		if (a == b) {
			return true;
		} else if (a == null || b== null) {
			return false;
		} else {
			return a.equals(b);
		}
	}
	
	public static boolean isEqual(byte[] a, byte[] b) {
		if (a == b) {
			return true;
		} else if (a == null || b== null) {
			return false;
		} else {
			return a.equals(b);
		}
	}
	
	public static boolean isEqual(byte a, byte b) {
		return a == b;
	}
	
	public static boolean isEqual(short a, short b) {
		return a == b;
	}
	
	public static boolean isEqual(int a, int b) {
		return a == b;
	}
	
	public static boolean isEqual(long a, long b) {
		return a == b;
	}
	
	public static boolean isEqual(float a, float b) {
		return a == b;
	}
	
	public static boolean isEqual(double a, double b) {
		return a == b;
	}
	
	public static boolean isEqual(boolean a, boolean b) {
		return a == b;
	}
}
