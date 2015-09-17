package com.mevelopers.tabdilak;

import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public class DecimalInputFilter implements InputFilter {
	private static final String ALLOWED_INPUT_CHARCTERS = "0123456789,";
	
	/*
	 * From https://developer.android.com/reference/android/text/InputFilter.html:
	 * This method is called when the buffer is going to replace the range dstart - dend of dest with the new text from the range start - end of source.
	 * Return the CharSequence that you would like to have placed there instead, including an empty string if appropriate, or null to accept the original
	 * replacement. Be careful to not to reject 0-length replacements, as this is what happens when you delete text. Also beware that you should not attempt
	 * to make any changes to dest from this method; you may only examine it for context. Note: If source is an instance of Spanned or Spannable, the span
	 * objects in the source should be copied into the filtered result (i.e. the non-null return value). copySpansFrom(Spanned, int, int, Class, Spannable, int)
	 * can be used for convenience.
	 */
	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		
		if(source instanceof Spanned || source instanceof Spannable) {
			SpannableStringBuilder sourceAsSpannable = (SpannableStringBuilder) source;
			for(int i=end -1 ; i >= start; i--) {
				if(! isCharacterAllowed(sourceAsSpannable.charAt(i), ALLOWED_INPUT_CHARCTERS)) {
					sourceAsSpannable.delete(i,i + 1);
				}
			} //for
			return source;
		} else {
			String result = "";
			for(int i=start; i< end; i++) {
				if(isCharacterAllowed(source.charAt(i), ALLOWED_INPUT_CHARCTERS)) {
					result += source.charAt(i);
				}
			}
			return result;
		}
	}
	
	//FIXME:: May be we can remove this and substitute it with str1.toLower.contains(str2)
	private boolean isCharacterAllowed (Character c, String allowed) {
		for (int i=0; i<allowed.length(); i++) {
			if(c == allowed.charAt(i))
				return true;
		}
		return false;
	}
	

}
