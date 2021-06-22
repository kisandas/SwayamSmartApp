/*
 * Copyright 2014 Thomas Hoffmann
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
package com.sngs.swayam.temp.pademeter.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dueeeke.tablayout.listener.OnTabSelectListener;
import com.sngs.swayam.temp.Activity.HomeActivity;
import com.sngs.swayam.temp.Fragment.Pedometer.Custom_Fragment;
import com.sngs.swayam.temp.Fragment.Pedometer.Daily_Fragment;
import com.sngs.swayam.temp.Fragment.Pedometer.Monthly_Fragment;
import com.sngs.swayam.temp.Fragment.Pedometer.Weekly_Fragment;
import com.sngs.swayam.temp.Fragment.Pedometer.Yearly_Fragment;
import com.sngs.swayam.temp.Network.WebUtlis.Links;
import com.sngs.swayam.temp.R;
import com.sngs.swayam.temp.pademeter.SensorListener;
import com.sngs.swayam.temp.pademeter.util.API26Wrapper;

public class Fragment_Overview extends Fragment {

    com.dueeeke.tablayout.SegmentTabLayout tab_Title;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (Build.VERSION.SDK_INT >= 26) {
            API26Wrapper.startForegroundService(getActivity(),
                    new Intent(getActivity(), SensorListener.class));
        } else {
            getActivity().startService(new Intent(getActivity(), SensorListener.class));
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_overview, null);
        ImageView iv_back = v.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), HomeActivity.class);
                startActivity(i);
            }
        });
        tab_Title = v.findViewById(R.id.tab_Title);
        tab_Title.setTabData(Links.mTitles_Tab);
        if (tab_Title.getCurrentTab() == 0) {
            loadFragment(new Daily_Fragment());
            tab_Title.setCurrentTab(0);
        }
        tab_Title.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (tab_Title.getCurrentTab() == 0) {
                    loadFragment(new Daily_Fragment());
                } else if (tab_Title.getCurrentTab() == 1) {
                    loadFragment(new Weekly_Fragment());
                } else if (tab_Title.getCurrentTab() == 2) {
                    loadFragment(new Monthly_Fragment());
                } else if (tab_Title.getCurrentTab() == 3) {
                    loadFragment(new Yearly_Fragment());
                }else if (tab_Title.getCurrentTab() == 4) {
                    loadFragment(new Custom_Fragment());
                }
            }


            @Override
            public void onTabReselect(int position) {

            }
        });

        return v;
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}





