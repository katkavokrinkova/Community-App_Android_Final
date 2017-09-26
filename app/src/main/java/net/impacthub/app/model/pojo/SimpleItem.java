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

package net.impacthub.app.model.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class SimpleItem<M extends Searchable> implements ListItemType<M> {

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

    @Override
    public boolean isSearchable(String query) {
        return mModel.isSearchable(query);
    }

    @Override
    public boolean isFilterable(Map<String, List<String>> filters) {
        return false;
    }
}
