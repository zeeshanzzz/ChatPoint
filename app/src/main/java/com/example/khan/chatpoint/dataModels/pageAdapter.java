package com.example.khan.chatpoint.dataModels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.khan.chatpoint.Fragments.BlankFragment;
import com.example.khan.chatpoint.Fragments.TAB1;
import com.example.khan.chatpoint.Fragments.Tab3;

public class pageAdapter extends FragmentPagerAdapter {
    public pageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return BlankFragment.newInstance();
            case 1:
                return TAB1.newInstance();
            case 2:
                return Tab3.newInstance();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position){
        switch(position){
            case 0:
                return "Request";
            case 1:
                return "chats";
            case 2:
                return "Calls";

        }


        return null;
    }
}
