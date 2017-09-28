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

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

public class FilterData implements Serializable {

    public final static String KEY_FILTER_HUB = "hub";
    public final static String KEY_FILTER_CITY = "city";
    public final static String KEY_FILTER_SECTOR = "sector";

    private final Map<String, List<String>> mFilters = new LinkedHashMap<>();

    public Map<String, List<String>> getFilters() {
        return mFilters;
    }
}
