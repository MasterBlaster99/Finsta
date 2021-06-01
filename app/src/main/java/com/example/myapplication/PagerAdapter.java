package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;
    public PagerAdapter(@NonNull FragmentManager fm , int behaviour, int tabs){
        super(fm, behaviour);
        this.tabsNumber= tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new users_fragment();
            case 1: return new postsViewFragment();
            case 2:  return new ChatsFragment();
            case 3: return new posts_fragment();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
