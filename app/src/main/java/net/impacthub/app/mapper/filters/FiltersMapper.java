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

package net.impacthub.app.mapper.filters;

import net.impacthub.app.model.features.filters.Filter;
import net.impacthub.app.model.features.filters.Filters;
import net.impacthub.app.model.vo.filters.FilterVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

public class FiltersMapper {

    public List<FilterVO> map(Filters filters) {
        List<FilterVO> filterVOs = new LinkedList<>();
        if (filters != null) {
            List<Filter> filterList = filters.getFilters();
            if (filterList != null) {
                for (Filter filter : filterList) {
                    if (filter != null) {
                        filterVOs.add(new FilterVO(filter.getName(), filter.getGrouping(), filter.getId()));
                    }
                }
            }
        }
        return filterVOs;
    }
}
