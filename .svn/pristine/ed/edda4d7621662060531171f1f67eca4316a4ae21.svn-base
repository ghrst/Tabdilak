package com.mevelopers.tabdilak;

import com.mevelopers.tabdilak.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.TextView;

public class About extends Activity {
	private TextView TvVersionNumber;

    @TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
        //Enabling Ancestral Navigation on API-level 11+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(NavUtils.getParentActivityName(this) != null) {
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
		TvVersionNumber = (TextView) findViewById(R.id.TvVersionNumber);
		try {
			PackageInfo pi = this.getPackageManager().getPackageInfo("com.mevelopers.tabdilak", 0);
			TvVersionNumber.setText("نسخه:" + pi.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(NavUtils.getParentActivityName(this) != null)
                    NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
