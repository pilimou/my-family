package com.example.demo.utils;

public class CommonTool {

	public static String todbc(String str) {
		for (char c : str.toCharArray()) {
			str = str.replaceAll("　", " ");
			if ((int) c >= 65281 && (int) c <= 65374) {
				str = str.replace(c, (char) (((int) c) - 65248));
			}
		}
		return str;
	}
}
