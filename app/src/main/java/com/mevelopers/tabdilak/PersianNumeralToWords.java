package com.mevelopers.tabdilak;

import java.text.DecimalFormat;

public class PersianNumeralToWords {
	
	private static final String [] mTens = {
		"", 
		"ده",
		"بیست",
		"سی",
		"چهل",
		"پنجاه",
		"شصت",
		"هفتاد",
		"هشتاد",
		"نود"};
	private static final String [] mOnes = {
		"",
		"یک",
		"دو",
		"سه",
		"چهار",
		"پنج",
		"شش",
		"هفت",
		"هشت",
		"نه",
		"ده",
		"یازده",
		"دوازده",
		"سیزده",
		"چهاردخ",
		"پانزده",
		"شانزده",
		"هفده",
		"هجده",
		"نوزده"
	};
	
	private static final String [] mHundreds = {
		"",
		"یکصد",
		"دویست",
		"سیصد",
		"چهارصد",
		"پانصد",
		"ششصد",
		"هفتصد",
		"هشتصد",
		"نهصد"
	};
	
	
	public static String numberToString(long number) {
		if(number == 0) {
			return "صفر";
		}
		/*
		 * Notice that in here we ignore sign of input number.
		 */
		number = Math.abs(number);
		String sNumber = Long.toString(number);
		//Check to see if number is in our legam range
		if(sNumber.length() > 12) {
			throw new NumberFormatException("Number is too large for parser!");
		}
		//We must pad the leftmost side with zeros
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		sNumber = df.format(number);
		//XXXnnnnnnnnn
		int billions = Integer.parseInt(sNumber.substring(0, 3));
		//nnnXXXnnnnnn
		int millions = Integer.parseInt(sNumber.substring(3, 6));
		//nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(sNumber.substring(6,9));
		//nnnnnnnnnXXX
		int thousands = Integer.parseInt(sNumber.substring(9,12));
		
		String billionsString = convertHelper(billions);
		
		String millionsString = convertHelper(millions);
		
		String hundredThousandsString = convertHelper(hundredThousands);
		
		String thousandsString = convertHelper(thousands);
		
		String result = "";
		boolean addAnd = false;
		if(billionsString != "") {
			result += billionsString + " میلیارد";
			addAnd = true;
		}
		
		if(millionsString != "") {
			if(addAnd) {
				result += " و " + millionsString + " میلیون";
			} else {
				result += millionsString + " میلیون";
			}
			addAnd = true;
		}
		
		if(hundredThousandsString != "") {
			if(addAnd) {
				result += " و " + hundredThousandsString + " هزار";
			} else {
				result += hundredThousandsString + " هزار";
			}
			addAnd = true;
		}
		
		if(thousandsString != "") {
			if(addAnd) {
				result += " و " + thousandsString;
			} else {
				result += thousandsString;
			}
		}
		
		return result;
	}
	
	public static String convertHelper (int number) {
		String result = "";
		boolean addAnd = false;
		int hundreds = number / 100;
		if(hundreds > 0) {
		
			result += mHundreds[hundreds];
			addAnd = true;
		}
		number = number % 100;
		if(number < 20 && number > 0){
			if(addAnd) {
				result += " و " + mOnes[number];
			} else {
				result += mOnes[number];
			}
		}
		
		if(number >= 20) {
			int tens = number / 10;
			if(addAnd) {
				result += " و " + mTens[tens];
				number = number % 10;
				if(number > 0)
					result += " و " + mOnes[number];
			} else {
				result += mTens[tens];
				number = number % 10;
				if(number > 0) {
					result += " و " + mOnes[number];
				}
			}	
		}
		return result;
	}

}
