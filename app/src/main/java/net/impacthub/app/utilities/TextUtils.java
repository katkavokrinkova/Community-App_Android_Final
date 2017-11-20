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

package net.impacthub.app.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/25/2017.
 */

public final class TextUtils {

    private TextUtils() {
        throw new IllegalAccessError("Never instantiate a utility class.");
    }

    public static boolean contains(String query, String... values) {
        boolean atLeastOne = false;
        for (String value : values) {
            if (isContained(query, value)) {
                atLeastOne = true;
                break;
            }
        }
        return atLeastOne;
    }

    private static boolean isContained(String query, String value) {
        return query != null && value != null && query.isEmpty() | value.toLowerCase().contains(query.toLowerCase());
    }

    public static boolean contains(String value, List<String> filters) {
        boolean atLeastOne = false;
        if (value != null && filters != null) {
            for (int i = 0, selectedFiltersSize = filters.size(); i < selectedFiltersSize; i++) {
                if (isContained(filters.get(i), value)) {
                    atLeastOne = true;
                    break;
                }
            }
        }
        return atLeastOne;
    }

    public static boolean contains(Map<String, List<String>> filters, String... values) {
        boolean atLeastOne = false;
        if (filters != null) {
            for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                List<String> selectedFilters = entry.getValue();
                if (selectedFilters != null) {
                    if (values != null) {
                        List<String> asList = Arrays.asList(values);
                        for (int i = 0, selectedFiltersSize = selectedFilters.size(); i < selectedFiltersSize; i++) {
                            String selectedFilter = selectedFilters.get(i);
                            asList.contains(selectedFilter);
                            atLeastOne = true;
                            break;
                        }
                    }
                }
            }
        }
        return atLeastOne;
    }

    public static String arraysAsString(List<String> selectedFilters) {
        int size = selectedFilters.size();
        String filterDisplay = "";
        if (size == 0) {
            filterDisplay = "All";
        }
        for (int i = 0; i < size; i++) {
            String filter = selectedFilters.get(i);
            if (i == (size - 1)) {
                filterDisplay += filter;
            } else {
                filterDisplay += filter + ", ";
            }
        }
        return filterDisplay;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean equals(String first, String second) {
        return (first != null && first.equals(second)) || (second != null && second.equals(first)) || (first == second);
    }
}
