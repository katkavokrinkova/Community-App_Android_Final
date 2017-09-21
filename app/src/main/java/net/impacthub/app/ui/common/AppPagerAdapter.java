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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.impacthub.app.ui.binder.ViewBinder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class AppPagerAdapter extends PagerAdapter {

    private final List<ViewBinder> mBindersList = new LinkedList<>();
    private final Context mContext;

    public AppPagerAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBindersList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mBindersList.get(position).getView(mContext, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addVieBinder(ViewBinder viewBinder) {
        mBindersList.add(viewBinder);
        notifyDataSetChanged();
    }
}