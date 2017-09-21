package net.impacthub.app.presenter.features.filters;

import net.impacthub.app.model.features.filters.SeparatedFilters;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public interface FiltersUiContract extends ErrorHandlerUiContract {

    void onLoadFilters(SeparatedFilters response);
}
