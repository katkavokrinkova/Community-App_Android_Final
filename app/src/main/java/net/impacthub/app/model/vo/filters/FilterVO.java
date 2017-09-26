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

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

public class FilterVO implements Filterable {

    public final static String KEY_FILTER_CITY = "city";
    public final static String KEY_FILTER_SECTOR = "sector";

    private final String name, grouping, id;

    private boolean selected;

    public FilterVO(String name, String grouping, String id) {
        this.name = name;
        this.grouping = grouping;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getGrouping() {
        return grouping;
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isFilterable(String query) {
        return false;
    }
}
