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
package com.mevelopers.tabdilak.test;

import static android.test.ViewAsserts.assertOnScreen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.text.InputFilter;
import android.text.InputType;
import android.text.InputFilter.LengthFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mevelopers.tabdilak.Konvertor;

public class KonvertorTests extends ActivityInstrumentationTestCase2<Konvertor> {
	private Activity mActivity;
	private EditText mTeNumber;
	private TextView mTvResult;
	private ImageButton mBtnConvert;
	private Instrumentation mInstrumentation;
	private InputStreamReader mInputTestReader;
	
	public KonvertorTests() {
		super(com.mevelopers.tabdilak.Konvertor.class);
	}
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mTeNumber = (EditText) mActivity.findViewById(com.mevelopers.tabdilak.R.id.TeNumber);
		mTvResult = (TextView) mActivity.findViewById(com.mevelopers.tabdilak.R.id.TvResult);
		mBtnConvert = (ImageButton) mActivity.findViewById(com.mevelopers.tabdilak.R.id.BtnConvert);
		mInstrumentation = getInstrumentation();
		
		InputStream is = mInstrumentation.getContext().getResources().openRawResource(R.raw.inputtests);
		mInputTestReader = new InputStreamReader(is, "UTF-8");
		
		
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(mActivity);
		assertNotNull(mTeNumber);
		assertNotNull(mTvResult);
		assertNotNull(mBtnConvert);
	}
	
	public void testTeNumberIsEmpty(){
		assertEquals("", mTeNumber.getText().toString());
	}
	
	public void testTeNumbetMaxLength() {
		InputFilter [] filters = mTeNumber.getFilters();
		for(InputFilter f: filters) {
			if(f instanceof com.mevelopers.tabdilak.Konvertor.MyLengthFilter) {
				assertEquals(15, ((com.mevelopers.tabdilak.Konvertor.MyLengthFilter) f).getMax());
			}
		}
	}

    @Suppress
    public void testTeNumberhasHint() {
        String hint = mTeNumber.getHint().toString();
        assertNotNull(hint);
        assertEquals(com.mevelopers.tabdilak.R.string.TeNumberDefaultText, hint);
    }
	
	public void testTeNumberCenterGravity() {
		int actual = mTeNumber.getGravity();
		assertEquals(Gravity.CENTER, actual);
	}
	
	public void testTeNumberInputType() {
		int expected = InputType.TYPE_CLASS_NUMBER;
		assertEquals(expected, mTeNumber.getInputType());
	}
	
	public void testTeNumberSeparatesIntegers() throws InterruptedException {
		String sampleText = "1 2 3 4 5 6 7 8 9 0 1 2";
		requestFocus(mTeNumber);
		sendKeys(sampleText);
        Thread.sleep(100);
		String expectedOutput = "123,456,789,012";
		String actualValue = mTeNumber.getText().toString();
		assertEquals(expectedOutput, actualValue);
	}
	
	public void testTeNumberDisplaysCommaSeparatedGroupsCorrectly() throws InterruptedException {
		isTextCorrect(mTeNumber, "1 2 3 4", "1,234");
		isTextCorrect(mTeNumber, "1 2 3 4 5 6 7 8 9 0 1 2", "123,456,789,012");
		isTextCorrect(mTeNumber, "0 0 0 0 0 0 0 0 0 0 0 0", "0");
		isTextCorrect(mTeNumber, "1 2 3", "123");
		isTextCorrect(mTeNumber, "1 0 0 1 0", "10,010");
		isTextCorrect(mTeNumber, "1 0 0 0", "1,000");	
	}
	
	public void testErrorMessagesAreNotShownWhenWeRemoveNumber() {
		requestFocus(mTeNumber);
		sendKeys(KeyEvent.KEYCODE_1);
		sendKeys(KeyEvent.KEYCODE_DEL);
		assertEquals("", mTvResult.getText().toString());
	}
	
	public void testTvResultIsEmpty() {
		assertEquals("",mTvResult.getText().toString());
	}

	public void testTvResultValueIsCorrect() {
			BufferedReader bf = new BufferedReader(mInputTestReader);
			String line;
			try {
				while((line = bf.readLine()) != null) {
					//Ignoring comments
					if(line.charAt(0) == '#') {
						continue;
					}
					String [] items = line.split(",");
					requestFocus(mTeNumber);
					sendKeys(items[0].trim());
					requestFocus(mBtnConvert);
					sendKeys(KeyEvent.KEYCODE_ENTER);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					assertEquals("Sent: " + items[0],items[1].trim(), mTvResult.getText().toString().trim());
					clearEditText(mTeNumber);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void testFieldsOnScreen(){
		Window window = mActivity.getWindow();
		View origin = window.getDecorView();
		
		assertOnScreen(origin, mBtnConvert);
		assertOnScreen(origin, mTeNumber);
		assertOnScreen(origin, mTvResult);
	}

    @Suppress
    public void testSupportedScreenSizes() {
        //Tests to see if we have <support-screens> tags in our manifest or not?!
        fail("<supports-screens> not implemented yet!");
    }


	
	public void testOnSaveInstanceStateImplementedCorrectly() throws Throwable {
		requestFocus(mTeNumber);
		sendKeys("1 0 0 0");
		requestFocus(mBtnConvert);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		final String expectedTvValue = mTvResult.getText().toString();
		final String expectedTeValue = mTeNumber.getText().toString();
		
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Bundle b = new Bundle();
				getInstrumentation().callActivityOnSaveInstanceState(mActivity, b);
				assertEquals("TeNumber's value is not in the Bundle",expectedTeValue, b.getString(com.mevelopers.tabdilak.Konvertor.TE_NUMBER_KEY));
				assertEquals("TvResult's value is not in the Bundle",expectedTvValue, b.getString(com.mevelopers.tabdilak.Konvertor.TV_RESULT_KEY));
			}
		});
	}
	
	
	public void testOnRestoreInstanceState() throws Throwable {
		runTestOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Bundle b = new Bundle();
				b.putString(com.mevelopers.tabdilak.Konvertor.TE_NUMBER_KEY, "1000");
				b.putString(com.mevelopers.tabdilak.Konvertor.TV_RESULT_KEY, "یک هزار");
				getInstrumentation().callActivityOnRestoreInstanceState(mActivity, b);
				
			}
		});
		
		assertEquals("TeNumber's value was not set correctly.","1,000", mTeNumber.getText().toString());
		assertEquals("TvResult's value was not set correctly.","یک هزار", mTvResult.getText().toString());
		
	}
	
	@Suppress
	public void testIfScreenRotationHandledCorrectly() throws Exception {
		
		requestFocus(mTeNumber);
		sendKeys("1 0 0");
		requestFocus(mBtnConvert);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		String expectedTvValue = mTvResult.getText().toString();
		String expectedTeValue = mTeNumber.getText().toString();
		
		for(int i=0; i<=10; i++) {
			if(i % 2 == 0) {
				mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			
			} else {
				mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			//FIXME :: Fix this problem.
			/*
			 * The problem here is that after screen rotation mActivity and all of our fields still refer to the old activity that we had and so assertEquals
			 * methods wrongly return true. So first we must renew our mActivity and all other references. The following solution does not solve our problem
			 * But currently I do not have enough time to solve this issue so I @Suppress it for now. For more information refer to:
			 * http://stackoverflow.com/questions/10982370/instrumentation-test-for-android-how-to-receive-new-activity-after-orientation
			 * For now I use Instrumentation.callActivityOnSaveInstanceState.  
			 */
			mActivity.finish();
			setActivity(null);
			setUp();
			getInstrumentation().waitForIdleSync();
			assertEquals("TeNumber value is not valid after rotation", expectedTeValue, mTeNumber.getText().toString());
			assertEquals("TvResult value is not valid after rotation",expectedTvValue, mTvResult.getText().toString());
		}	
	}
	
	public void testTeNumberWidth() {
		final int expected = LayoutParams.MATCH_PARENT;
		final android.view.ViewGroup.LayoutParams lp = mTeNumber.getLayoutParams();
		assertEquals("TeNumber's layout_width is not MATCH_PARENT!",expected, lp.width);
	}

    @Suppress
	public void testTvResultFontSize() {
		final float expected = 54.0f;
		assertEquals(expected, mTvResult.getTextSize());
		
	}
	
	public void testTvResultJustification() {
		final int expected = Gravity.CENTER;
		assertEquals(expected, mTvResult.getGravity());
	}
	
	
	public void isTextCorrect(EditText v, String textToEnter, String expected) throws InterruptedException {
		clearEditText(mTeNumber);
        Thread.sleep(100);
		v.requestFocus();
		sendKeys(textToEnter);
        Thread.sleep(100);
		String actual = v.getText().toString();
		assertEquals(expected, actual);
	}
	
	public void requestFocus(final View v) {
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					v.requestFocus();
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void clearEditText(final EditText TeElement) {
		try {
			runTestOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					TeElement.setText("");
				}
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
