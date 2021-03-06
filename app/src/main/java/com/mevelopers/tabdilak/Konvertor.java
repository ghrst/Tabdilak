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

import java.util.Locale;

import com.mevelopers.tabdilak.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class Konvertor extends Activity {
	public static final String TV_RESULT_KEY = "TC_RESULT_KEY";
	public static final String TE_NUMBER_KEY = "TE_NUMBER_KEY";
	
	EditText mTeNumber;
	TextView mTvResult;
	ImageButton mBtnConvert;
    TextView mTvAboutUs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//We do not want to see the title-bar!
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_konvertor);
		mTeNumber = (EditText) findViewById(R.id.TeNumber);
		//Setting max length and decimal filter
		MyLengthFilter lf = new MyLengthFilter(15);
		DecimalInputFilter df = new DecimalInputFilter();
		InputFilter [] f = new InputFilter[2];
		f[0] = lf;
		f[1] = df;
		mTeNumber.setFilters(f);

		//Adding a text watcher for separating numbers by comma
		SeparateNumbersWithComma separator = new SeparateNumbersWithComma();
		mTeNumber.addTextChangedListener(separator);
        //This code here is for handling times when user presses done button on soft-keyboard
        mTeNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (i == EditorInfo.IME_ACTION_DONE) {
                        mBtnConvert.performClick();
                        //For hiding soft keyboard after entering text!
                        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if(inputManager != null)
                            inputManager.hideSoftInputFromWindow(mTeNumber.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return true;
                    }
                    return false;
                }
            });



        mTvResult = (TextView) findViewById(R.id.TvResult);
		mBtnConvert = (ImageButton) findViewById(R.id.BtnConvert);

		//FIXME:: Maybe it is better to use a TextWatcher in order to convert instead of a Button.
		mBtnConvert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String result = "";
				/* Notice that all leading zeros will be suppressed by Long.parseLong so we will not have any
				 * problem with automatic radix conversion of Octal to Decimal. For more information refer to test cases! 
				 */
				String userInput = mTeNumber.getText().toString().trim();
				if(userInput.length() == 0 || userInput == null) {
					result = "لطفا عدد مورد نظر را وارد کنید.";
					mTvResult.setText(result);
					return;
				}
				long convertedUserInput;
				try{
					userInput = userInput.replace(",", "");
					convertedUserInput = Long.parseLong(userInput);
				} catch (Exception e) {
					result = "ورودی  قابل پذیرش نیست. فقط اعداد صحیح 12 رقمی قابل تبدیل هستند.";
					mTvResult.setText(result);
					return;
				}
				
				try {
					result = PersianNumeralToWords.numberToString(convertedUserInput);
				} catch (Exception e) {
					result = "فقط اعداد صحیح با حداکثر 12 رقم قابل تبدیل هستند.";
					mTvResult.setText(result);
					return;
				}

                //TODO:: Hide the keyboard when user presses this button!

				mTvResult.setText(result);
                //Hiding soft keyboard
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    View temp = getCurrentFocus();
                    if (temp != null)
                        inputManager.hideSoftInputFromWindow(temp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
				
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.konvertor, menu);
		return true;
	}


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_about:
            Intent i = new Intent(getApplicationContext(), About.class);
            startActivity(i);
		}
		return true;
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(TV_RESULT_KEY, mTvResult.getText().toString());
		outState.putString(TE_NUMBER_KEY, mTeNumber.getText().toString());
	}


	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mTvResult.setText(savedInstanceState.getString(TV_RESULT_KEY));
		mTeNumber.setText(savedInstanceState.getString(TE_NUMBER_KEY));
	}
	
	
	// A length filter that has getMax and can be used inside test cases!
	public class MyLengthFilter extends LengthFilter {
		private int maxLength;
		public MyLengthFilter(int max) {
			super(max);
			maxLength = max;
		}
		
		public int getMax() {
			return maxLength;
		}
		
	}
	
	//TextWatcher implementation for separating numbers with a comma
	class SeparateNumbersWithComma implements TextWatcher {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			//Removing the listener temporarily to avoid infinite calls to this function
			mTeNumber.removeTextChangedListener(this);
			String currentValue = mTeNumber.getText().toString();
			String commaSeparatedValue = null;
			try {
				/* May be when we read the text it already has commas in it
				 * In this case a NumberFormatException will occur so we must prevent it
				 * by removing comma.
				 */
				if(currentValue.contains(",")) {
					currentValue = currentValue.replace(",", "");
				}
				
				if(currentValue.length() > 0)
					commaSeparatedValue = String.format(Locale.US,"%,d", Long.parseLong(currentValue));
			} catch(NumberFormatException e) {
				mTvResult.setText("لطفا فقط از مقادیر عددی استفاده کنید");
			}
			mTeNumber.setText(null);
			mTeNumber.setText(commaSeparatedValue);
			/*
			 * After changing the text cursor position will be at the beginning of it; if current length of
			 * text if greater than zero we must put the cursor at then end of our text.
			 */
			if(mTeNumber.getText().length() > 0) {
				mTeNumber.setSelection(mTeNumber.getText().length());
			}
			mTeNumber.addTextChangedListener(this);
		}
	}
}



