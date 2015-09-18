/*
 Copyright: 2015 Gholamreza Sabery Tabrizy

  This file is part of Tabdilak.

    Tabdilak is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Tabdilak is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Tabdilak.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mevelopers.tabdilak;

import java.util.HashMap;

public class PersianUtils {
	private static final HashMap<Character, Character> PERSIAN_TO_ENGLISH = new HashMap<Character, Character>();
	private static final HashMap<Character, Character> ENGLISH_TO_PERSIAN = new HashMap<Character, Character>();
	
	static {
		PERSIAN_TO_ENGLISH.put('\u06F0', '0');
		PERSIAN_TO_ENGLISH.put('\u06F1', '1');
		PERSIAN_TO_ENGLISH.put('\u06F2', '2');
		PERSIAN_TO_ENGLISH.put('\u06F3', '3');
		PERSIAN_TO_ENGLISH.put('\u06F4', '4');
		PERSIAN_TO_ENGLISH.put('\u06F5', '5');
		PERSIAN_TO_ENGLISH.put('\u06F6', '6');
		PERSIAN_TO_ENGLISH.put('\u06F7', '7');
		PERSIAN_TO_ENGLISH.put('\u06F8', '8');
		PERSIAN_TO_ENGLISH.put('\u06F9', '9');
		
		ENGLISH_TO_PERSIAN.put('0', '\u06F0');
		ENGLISH_TO_PERSIAN.put('1', '\u06F1');
		ENGLISH_TO_PERSIAN.put('2', '\u06F2');
		ENGLISH_TO_PERSIAN.put('3', '\u06F3');
		ENGLISH_TO_PERSIAN.put('4', '\u06F4');
		ENGLISH_TO_PERSIAN.put('5', '\u06F5');
		ENGLISH_TO_PERSIAN.put('6', '\u06F6');
		ENGLISH_TO_PERSIAN.put('7', '\u06F7');
		ENGLISH_TO_PERSIAN.put('8', '\u06F8');
		ENGLISH_TO_PERSIAN.put('9', '\u06F9');
	}
	
	public static String convertPersianNumeralToEnglish(String number) {
		if(number == null || number.length() == 0)
			throw new IllegalArgumentException("Provided argument is null!");
		if(!isPersianNumberLegal(number))
			throw new IllegalArgumentException("String must only contain Persian numerals");
		String result = "";
		for(int i=0;i<number.length();i++)
			result += PERSIAN_TO_ENGLISH.get(number.charAt(i));
		return result;
	}
	
	public static String convertEnglishNumeralToPersian(String number) {
		if(number == null || number.length() == 0)
			throw new IllegalArgumentException("Provided argument is null!");
		if(!isEnglishNumberLegal(number))
			throw new IllegalArgumentException("String must only contain numerals!");
		String result = "";
		for(int i=0; i<number.length(); i++) 
			result += ENGLISH_TO_PERSIAN.get(number.charAt(i));
		return result;
	}
	
	private static boolean isEnglishNumberLegal(String number) {
		for(int i=0;i<number.length();i++) 
			if(!ENGLISH_TO_PERSIAN.containsKey(number.charAt(i)))
				return false;
		return true;
	}
	
	private static boolean isPersianNumberLegal(String number) {
		for(int i=0; i<number.length();i++) 
			if(!PERSIAN_TO_ENGLISH.containsKey(number.charAt(i)))
					return false;
		return true;
		
	}
	
}
