package net.impacthub.app.presenter.features.filters;

import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.filters.CitiesFilterUseCase;
import net.impacthub.app.usecase.features.filters.SectorFilterUsecase;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public class FiltersUiPresenter extends UiPresenter<FiltersUiContract> {

    public FiltersUiPresenter(FiltersUiContract uiContract) {
        super(uiContract);
    }

    public void getFiltersByName(String filterName) {
        if(FilterVO.KEY_FILTER_CITY.equalsIgnoreCase(filterName)) {
            fetchCityFilters();
        } else if(FilterVO.KEY_FILTER_SECTOR.equalsIgnoreCase(filterName)) {
            fetchSectorsFilters();
        }
    }

    private void fetchSectorsFilters() {
        subscribeWith(new SectorFilterUsecase().getUseCase(), new DisposableSingleObserver<List<FilterVO>>() {

            @Override
            public void onSuccess(@NonNull List<FilterVO> filterVOs) {
                getUi().onLoadFilters(FilterVO.KEY_FILTER_SECTOR, filterVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    private void fetchCityFilters() {
        subscribeWith(new CitiesFilterUseCase().getUseCase(), new DisposableSingleObserver<List<FilterVO>>() {

            @Override
            public void onSuccess(@NonNull List<FilterVO> filterVOs) {
                getUi().onLoadFilters(FilterVO.KEY_FILTER_CITY, filterVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void loadFilters(List<FilterVO> filterVOs, List<String> selectedFilters, String filterName) {
        if (filterVOs != null) {
            for (FilterVO filterVO : filterVOs) {
                filterVO.setSelected(selectedFilters.contains(filterVO.getName()));
            }
            getUi().onLoadFilterList(filterName, filterVOs);
        } else {
            getUi().onError(new Throwable("No filters for selected filter!"));
        }
    }
}
