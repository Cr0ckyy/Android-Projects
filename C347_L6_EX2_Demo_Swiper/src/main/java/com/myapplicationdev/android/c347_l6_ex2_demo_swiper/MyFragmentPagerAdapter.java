package com.myapplicationdev.android.c347_l6_ex2_demo_swiper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
/* TODO: 2. Create a FragmentPagerAdapter subclass.
It should have a constructor that passes
        FragmentManager  to super constructor.
It should also implement getItem(position) to return the
        Fragment object at the position requested and getCount()  to
        return the number of fragment available (number of  screens the ViewPager has).
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragments;

    //  TODO:  Retrieve  FragmentManager  in argument and collection of  Fragments (ArrayList) in argument
    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> al) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        fragments = al;
    }
    @Override
    @NonNull
    //  TODO: ViewPager will call  getItem() to request for  fragment for screen at the  position
    public Fragment getItem(int position) {
        //  TODO:  implement getItem(position) to return the
        //   Fragment object at the position requested
        return fragments.get(position);
    }

    @Override
    //  TODO: ViewPager will call  getCount() to find out the  number of screens it has  navigate
    public int getCount() {
        //  TODO:  implement getCount() to return the number of fragment available
        //   (number of  screens the ViewPager has).
        return fragments.size();
    }
}

