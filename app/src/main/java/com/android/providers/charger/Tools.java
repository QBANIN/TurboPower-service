/*
 * Copyright (C) 2015-2016 Willi Ye <williye97@gmail.com>
 * Copyright (C) 2016 Felipe de Leon fglfgl27@gmail.com
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.android.providers.charger;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.providers.charger.root.RootFile;

public class Tools implements Constants {
	static void DoAToast(String message, Context context) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		TextView view = (TextView) toast.getView().findViewById(android.R.id.message);

		if (view != null) {
			view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.moto_ic_turbopower_light, 0, 0, 0);
			view.setCompoundDrawablePadding(context.getResources().getDimensionPixelSize(R.dimen.padding_toast));
			view.setGravity(Gravity.CENTER);
		}

		toast.show();
	}

	static void saveBoolean(String name, boolean value, Context context) {
		context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(name, value).apply();
	}

	static boolean getBoolean(String name, boolean defaults, Context context) {
		try {
			return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(name, defaults);
		} catch (Exception ignored) {
			return false;
		}
	}

	static String getChargingType() {
		return new RootFile(BATTERY_CHARGING_TYPE).readFile();
	}

	static String getChargeCapacity() {
		return new RootFile(BATTERY_CAPACITY).readFile();
	}
}
