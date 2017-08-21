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

package net.impacthub.members.model.vo.filters;

import android.support.annotation.NonNull;

import net.impacthub.members.model.features.filters.Filter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/21/2017.
 */

public class FilterVO implements Serializable {

    private List<Filter> filters = new LinkedList<>();

    public FilterVO(@NonNull List<Filter> filters) {
        this.filters = filters;
    }

    public List<Filter> getFilters() {
        return filters;
    }
}
