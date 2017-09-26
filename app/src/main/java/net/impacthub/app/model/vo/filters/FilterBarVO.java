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

package net.impacthub.app.model.vo.filters;

import net.impacthub.app.model.pojo.Filterable;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

public class FilterBarVO implements Filterable {

    private final String mFilterName;
    private final List<String> mSelectedFilters = new LinkedList<>();

    public FilterBarVO(String filterName) {
        mFilterName = filterName;
    }

    public void setSelectedFilters(List<String> filters) {
        mSelectedFilters.clear();
        mSelectedFilters.addAll(filters);
    }

    public List<String> getSelectedFilters() {
        return mSelectedFilters;
    }

    public String getFilterName() {
        return mFilterName;
    }

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}
