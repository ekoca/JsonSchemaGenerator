package com.emre.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtil {

	private static final Pattern WHITE_SPACE = Pattern.compile("\\s+");
	private static final Charset utf8 = Charset.forName("UTF-8");

	public static String stringVal(Object val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}

	public static boolean isEmpty(String string) {
		return (string == null || string.trim().length() == 0);
	}

	public static String formatPhoneNumber(String phoneNumber) {
		String formattedNumber = phoneNumber.replaceAll("[^0-9]", "");

		if (phoneNumber.length() == 11) {
			formattedNumber = "+" + phoneNumber.substring(0, 1);
			formattedNumber += " (" + phoneNumber.substring(1, 4) + ")";
			formattedNumber += " " + phoneNumber.substring(4, 7);
			formattedNumber += "-" + phoneNumber.substring(7, 11);
		} else if (phoneNumber.length() == 10) {
			formattedNumber = "(" + phoneNumber.substring(0, 3) + ")";
			formattedNumber += " " + phoneNumber.substring(3, 6);
			formattedNumber += "-" + phoneNumber.substring(6, 10);
		} else if (phoneNumber.length() == 7) {
			formattedNumber = phoneNumber.substring(0, 3);
			formattedNumber += "-" + phoneNumber.substring(4, 7);
		}

		return formattedNumber;
	}

	public static String toCamelCase(String input) {
		StringBuilder sb = new StringBuilder();
		for (String oneString : input.split("_")) {
			sb.append(oneString.substring(0, 1));
			sb.append(oneString.substring(1).toLowerCase());
		}
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		return sb.toString();
	}

	public static String join(List<String> list, char textQualifier,
			char separator) {
		int size = list.size();
		if (size == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		sb.append(textQualifier).append(list.get(0)).append(textQualifier);

		for (int i = 1; i < size; i++) {
			sb.append(separator).append(textQualifier).append(list.get(i))
					.append(textQualifier);
		}
		return sb.toString();
	}

	/**
	 * Return the first X characters of a string.
	 */
	public static String cut(String s, int maxSize) {
		if (s.length() <= maxSize)
			return s;

		return s.substring(0, maxSize);
	}

	/**
	 * Return a cleaned string or null (if the cleaned string is empty). This
	 * method never returns an empty string or a whitespace string. In those
	 * cases it will return null. Useful from preventing bad data in the DB,
	 * such as empty strings or whitespace.
	 */
	public static String clean(String s) {
		if (s == null)
			return null;
		s = s.trim();
		if (s.length() == 0)
			return null;

		return s;
	}

	/**
	 * Normalize the string for DB queries. This means uppercasing and ,
	 * normalizing white space.
	 */
	// TODO: Code review this method comparing to previous implementation
	public static String dbNormalize(String s) {
		s = normalizeEmptyAndWhitespace(s);
		if (s == null)
			return null;
		return s.toUpperCase();
	}

	public static String normalizeEmptyAndWhitespace(String s) {
		s = clean(s);
		if (s == null)
			return null;

		s = WHITE_SPACE.matcher(s).replaceAll(" ");
		return s;
	}

	public static void write(File f, String content) throws IOException {
		write(f, content, utf8);
	}

	public static void write(File f, String content, final Charset chs)
			throws FileNotFoundException, IOException {
		FileOutputStream fo = new FileOutputStream(f);
		try {
			write(content, fo, chs);
		} finally {
			fo.close();
		}
	}

	private static void write(String content, OutputStream out, Charset charset)
			throws IOException {
		OutputStreamWriter w = new OutputStreamWriter(out, charset);
		try {
			w.write(content);
		} finally {
			w.close();
		}
	}

	/**
	 * Return the number as a string padded with leading zeros. Final result
	 * will have the specified number of digits, meaning the return string is
	 * always the same length
	 */
	public static CharSequence padNum(long num, final int digits) {
		final String s = Long.toString(num);
		final int length = s.length();

		if (length > digits)
			throw new IllegalArgumentException(s + " has more than " + digits
					+ " digits");

		if (length == digits)
			return s;

		int zeros = digits - length;
		StringBuilder sb = new StringBuilder(digits);
		for (int i = 0; i < zeros; i++)
			sb.append('0');

		sb.append(s);
		return sb;
	}

	/**
	 * Convert a string to a long. Null input returns null output.
	 */
	public static Long longValue(String s) throws NumberFormatException{
		if (s == null)
			return null;
		
		return Long.valueOf(s);
	}
}