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

package net.impacthub.members.model.pojo;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class SimpleItem<M> implements ListItemType<M> {

    private final M mModel;
    private final int mViewType;

    public SimpleItem(M model, int viewType) {
        mModel = model;
        mViewType = viewType;
    }

    @Override
    public M getModel() {
        return mModel;
    }

    @Override
    public int getItemType() {
        return mViewType;
    }
}