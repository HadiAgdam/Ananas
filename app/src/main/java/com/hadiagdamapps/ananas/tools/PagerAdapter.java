package com.hadiagdamapps.ananas.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Integer> list;

    public PagerAdapter(ArrayList<Integer> list, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new Fragment(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
