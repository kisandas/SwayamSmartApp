/*
 * Copyright 2013 Thomas Hoffmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sngs.swayam.temp.pademeter.pedemer.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.PermissionChecker;

import com.sngs.swayam.temp.Activity.HomeActivity;
import com.sngs.swayam.temp.BuildConfig;
import com.sngs.swayam.temp.pademeter.SensorListener;
import com.sngs.swayam.temp.pademeter.ui.Fragment_Overview;


public class Activity_Main extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);

        startService(new Intent(this, SensorListener.class));
        if (b == null) {
            Fragment newFragment = new Fragment_Overview();
            FragmentManager fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(android.R.id.content, newFragment);
            fragmentTransaction.commit();
        }

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 26 && PermissionChecker
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
        Intent i = new Intent(Activity_Main.this, HomeActivity.class);
        startActivity(i);
    }


}