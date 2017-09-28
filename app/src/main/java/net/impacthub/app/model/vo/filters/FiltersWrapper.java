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

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class FiltersWrapper implements Serializable {

    private final String mFilterName;
    private final List<FilterVO> mFilterVOs = new LinkedList<>();

    public FiltersWrapper(String filterName, @NonNull List<FilterVO> filterVOs) {
        mFilterName = filterName;
        mFilterVOs.addAll(filterVOs);
    }

    public String getFilterName() {
        return mFilterName;
    }

    public List<FilterVO> getFilterVOs() {
        return mFilterVOs;
    }
}
