/*
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.android.providers.charger.root.RootUtils;

public class TurboPowerReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (!RootUtils.rootAccess()) {
			Tools.DoAToast((context.getResources().getString(R.string.no_root_access)), context);
			return;
		}

		if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			return;
		}

		// Android is sending undesirable DISCONNECTED at boot with make a toast even if there is no action on the POWER
		Long time = SystemClock.elapsedRealtime();

		// TurboPower connected
		if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
			DoTurboToast(context);
		}

		// charge toast 150000 = 150 seconds
		if (Intent.ACTION_POWER_DISCONNECTED.equals(action) && (time > 150000)) {
			if (Tools.getBoolean("isTurbo", false, context)) {
				Tools.DoAToast((context.getResources().getString(R.string.charged) + " " + Tools.getChargeCapacity() + "%"), context);
			}
		}
	}

	public void DoTurboToast(Context context) {
		Tools.saveBoolean("isTurbo", false, context);

		for (int i = 0; i < 50; i++) {
			if (Tools.getChargingType().toLowerCase().equals("turbo")) {
				Tools.DoAToast((context.getResources().getString(R.string.connected)), context);
				Tools.saveBoolean("isTurbo", true, context);
				break;
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}

		RootUtils.getSU().close();
	}
}
