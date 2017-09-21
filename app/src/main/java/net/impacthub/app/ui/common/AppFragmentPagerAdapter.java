/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.ui.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class AppFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new LinkedList<>();
    private final PagerAdapterInterface mAdapterInterface;

    public AppFragmentPagerAdapter(FragmentManager fm, @NonNull PagerAdapterInterface adapterInterface) {
        super(fm);
        mAdapterInterface = adapterInterface;
    }

    @Override
    public Fragment getItem(int position) {
        return mAdapterInterface.getItem(position);
    }

    @Override
    public int getCount() {
        return mAdapterInterface.getCount();
    }

    public interface PagerAdapterInterface {

        Fragment getItem(int position);

        int getCount();
    }
}
