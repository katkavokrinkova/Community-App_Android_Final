package net.impacthub.app.presenter.features.filters;

import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public interface FiltersUiContract extends ErrorHandlerUiContract {

    void onLoadFilters(String key, List<FilterVO> filterVOs);

    void onLoadFilterList(String filterName, List<FilterVO> filterVOs);
}
