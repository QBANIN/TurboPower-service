package com.android.providers.charger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.android.providers.charger.root.RootUtils;

public class Main extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.info);
		setContentView(android.R.layout.simple_list_item_1);

		int pad = getResources().getDimensionPixelSize(R.dimen.padding_dialog);

		TextView textView = (TextView) findViewById(android.R.id.text1);
		textView.setText(R.string.app_hidden);
		textView.setPadding(pad, pad, pad, pad);

		if (!RootUtils.rootAccess()) {
			Tools.DoAToast((getResources().getString(R.string.no_root_access)), this);
		}

		getPackageManager().setComponentEnabledSetting(new ComponentName(this, Main.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}
}
