package com.example.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chat.Fragenment.Chat;
import com.example.chat.Fragenment.Friend;
import com.example.chat.Fragenment.Request;

class SectionPageAdapter extends FragmentPagerAdapter {
     int counttab;
    public SectionPageAdapter(FragmentManager fm, int counttab) {
        super(fm);

        this.counttab=counttab;
    }

    @Override
    public Fragment getItem(int position) {


        switch(position) {
            case 0:
                Friend request = new Friend();
                return request;
            case 1:
                Chat chats = new Chat();
                return chats;
            case 2:
                Request Req = new Request();
                return Req;

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return counttab;
    }

}
