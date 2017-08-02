package net.impacthub.members.presenter.features.filters;

import net.impacthub.members.model.filters.SeparatedFilters;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public interface FiltersUiContract extends ErrorHandlerUiContract {

    void onLoadFilters(SeparatedFilters response);
}
