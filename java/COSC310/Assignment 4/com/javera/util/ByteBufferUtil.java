/*
 * ByteBufferUtil   1/3/98
 *
 * Copyright (c) 1997 Javera Software Inc. All Rights Reserved.
 */

package com.javera.util;

/**
 * ByteBufferUtil contains methods for getting and putting shorts, ints, and longs into
 * a byte array
 */
public class ByteBufferUtil
{
    public static final int byteSize = 1;
    public static final int shortSize = 2;
    public static final int intSize = 4;
    public static final int longSize = 8;
    
    public static void putString(byte[] data, int offset, int len, String value) {
    	for (int i = 0; i < len; i++) {
    		if (value != null && i < value.length()) {
				data[offset + i] = (byte) value.charAt(i);
    		} else {
				data[offset + i] = 0;
    		}
    	}
    }
    
    public static String getString(byte[] data, int offset, int len) {
    	StringBuffer sb = new StringBuffer();
    	
    	for (int i = 0; i < len && data[offset + i] != 0; i++) {
    		sb.append((char) data[offset + i]);
    	}
    	
    	return sb.toString();
    }
    
	/**
	 * Put a short into a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to put the value
	 * @param value the value to be inserted
	 */
    public static final void putShort (byte[] data, int offset, short value)
    {
        data[offset++] = (byte)((value >> 8) & 0x0ff);
        data[offset]   = (byte) (value       & 0x0ff);
    }

	/**
	 * Get a short from a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to get the value
	 * @return the value
	 */
    public static final short getShort (byte[] data, int offset)
    {
        return (short) (((data[offset++] << 8) & 0xff00) |
			             (data[offset]         & 0x00ff));
    }

	/**
	 * Put an int into 3 bytes of a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to put the value
	 * @param value the value to be inserted
	 */
	public static final void putInt3 (byte[] data, int offset, int value)
	{
		data[offset++] = (byte)((value >> 16) & 0x0ff);
		data[offset++] = (byte)((value >> 8)  & 0x0ff);
		data[offset]   = (byte) (value        & 0x0ff);
	}

	/**
	 * Get an int from 3 bytes of a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to gett the value
	 * @return the value
	 */
	public static final int getInt3 (byte[] data, int offset)
	{
		return (((data[offset++] << 24) & 0xff000000) >> 8 |
				((data[offset++] << 8)  & 0x0000ff00) |
				 (data[offset]          & 0x000000ff));

	}

	/**
	 * Put an int into a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to put the value
	 * @param value the value to be inserted
	 */
    public static final void putInt (byte[] data, int offset, int value)
    {
        data[offset++] = (byte)((value >> 24) & 0x0ff);
        data[offset++] = (byte)((value >> 16) & 0x0ff);
        data[offset++] = (byte)((value >> 8)  & 0x0ff);
        data[offset]   = (byte) (value        & 0x0ff);
    }

	/**
	 * Get an int from a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to gett the value
	 * @return the value
	 */
    public static final int getInt (byte[] data, int offset)
    {
        return (((data[offset++] << 24) & 0xff000000) |
                ((data[offset++] << 16) & 0x00ff0000) |
                ((data[offset++] << 8)  & 0x0000ff00) |
                 (data[offset]          & 0x000000ff));

    }

	/**
	 * Put a long into a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to put the value
	 * @param value the value to be inserted
	 */
    public static final void putLong (byte[] data, int offset, long value)
    {
        data[offset++] = (byte)((value >> 56) & 0x0ff);
        data[offset++] = (byte)((value >> 48) & 0x0ff);
        data[offset++] = (byte)((value >> 40) & 0x0ff);
        data[offset++] = (byte)((value >> 32) & 0x0ff);
        data[offset++] = (byte)((value >> 24) & 0x0ff);
        data[offset++] = (byte)((value >> 16) & 0x0ff);
        data[offset++] = (byte)((value >> 8)  & 0x0ff);
        data[offset]   = (byte)(value         & 0x0ff);
    }

	/**
	 * Get a long from a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to gett the value
	 * @return the value
	 */
    public static final long getLong (byte[] data, int offset)
    {
        return (((data[offset++] << 56) & 0xff00000000000000L) |
                ((data[offset++] << 48) & 0x00ff000000000000L) |
                ((data[offset++] << 40) & 0x0000ff0000000000L) |
                ((data[offset++] << 32) & 0x000000ff00000000L) |
                ((data[offset++] << 24) & 0x00000000ff000000L) |
                ((data[offset++] << 16) & 0x0000000000ff0000L) |
                ((data[offset++] << 8)  & 0x000000000000ff00L) |
                 (data[offset++]        & 0x00000000000000ffL));
    }

	/**
	 * Put bytes into a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to put the value
	 * @param maxLength maximum number of bytes to insert
	 * @param value the value to be inserted
	 */
    public static final void putBytes (byte[] data, int offset, int maxLength, byte[] value)
    {
        for (int i = 0; i < value.length && maxLength > 0; i++)
        {
            data[offset++] = value[i];
            maxLength--;
        }
    }

	/**
	 * Get bytes from a byte array at an offset
	 *
	 * @param data the byte array
	 * @param offset offset in the byte array where to gett the value
	 * @param length the number of bytes to get
	 * @return the value
	 */
    public static final byte[] getBytes (byte[] data, int offset, int length)
    {
        byte[] value = new byte[length];

        for (int i = 0; i < length; i++)
            value[i] = data[offset++];

        return value;
    }
}