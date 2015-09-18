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

import org.junit.After;
import org.junit.Before;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.test.ActivityInstrumentationTestCase2;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mevelopers.tabdilak.*;
import com.mevelopers.tabdilak.R;

import static android.test.ViewAsserts.*;

public class AboutTests extends ActivityInstrumentationTestCase2<com.mevelopers.tabdilak.About> {
	private Activity mActivity;
	private TextView mTvVersionNumber;
	private TextView mTvAboutTitle;
	private TextView mTvWebsiteAddress;
	private LinearLayout mlLMainLayout;
    private ImageView mIvAboutLogo;
	
	public AboutTests() {
		super(com.mevelopers.tabdilak.About.class);
	}
	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mTvAboutTitle = (TextView) mActivity.findViewById(com.mevelopers.tabdilak.R.id.TvAboutTitle);
		mTvVersionNumber = (TextView) mActivity.findViewById(com.mevelopers.tabdilak.R.id.TvVersionNumber);
		mTvWebsiteAddress = (TextView) mActivity.findViewById(com.mevelopers.tabdilak.R.id.TvWebsiteAddress);
		mlLMainLayout = (LinearLayout) mActivity.findViewById(com.mevelopers.tabdilak.R.id.lLmain);
        mIvAboutLogo = (ImageView) mActivity.findViewById(R.id.Iv_about_logo);
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPreconditions() {
		assertNotNull(mActivity);
		assertNotNull(mTvAboutTitle);
		assertNotNull(mTvVersionNumber);
		assertNotNull(mTvWebsiteAddress);
		assertNotNull(mlLMainLayout);
        assertNotNull(mIvAboutLogo);
	}
	
	public void testVersionNumberInTvVersionNumberIsCorrect() throws NameNotFoundException {
		PackageManager pk = mActivity.getPackageManager();
		PackageInfo pi = pk.getPackageInfo("com.mevelopers.tabdilak", 0);
		String expected = pi.versionName;
		assertEquals("نسخه:" + expected, mTvVersionNumber.getText().toString());
	}

	public void testFieldsOnScreen() {
		View origin = mActivity.getWindow().getDecorView();
		assertOnScreen(origin, mTvAboutTitle);
		assertOnScreen(origin, mTvVersionNumber);
		assertOnScreen(origin, mTvWebsiteAddress);
	}
	
	
	public void testAllTextViewsMustBeCenterAligned() {
		assertEquals(true, isTextViewCenterAligned(mTvAboutTitle));
		assertEquals(true, isTextViewCenterAligned(mTvWebsiteAddress));
		assertEquals(true, isTextViewCenterAligned(mTvVersionNumber));
	}
	
	public void testTvWebsiteAddressIsClickable() {
		int actual = mTvWebsiteAddress.getAutoLinkMask();
		assertEquals(Linkify.WEB_URLS, actual);
	}
	
	
	//Helper methods
	public boolean isTextViewCenterAligned(TextView tv) {
		int actual = tv.getGravity();
		if(actual != Gravity.CENTER) 
			return false;
		//For showing in center either layout_gravity must be center or layout_width must be match_parent
		LayoutParams lparams =  tv.getLayoutParams();
        return lparams.width == LayoutParams.MATCH_PARENT;
    }
	
	

}
