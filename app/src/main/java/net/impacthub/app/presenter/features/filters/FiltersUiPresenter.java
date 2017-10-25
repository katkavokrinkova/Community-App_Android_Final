package net.impacthub.app.presenter.features.filters;

import net.impacthub.app.model.vo.filters.FilterBarVO;
import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.DisposableSingleObserverAdapter;
import net.impacthub.app.usecase.features.filters.CitiesFilterUseCase;
import net.impacthub.app.usecase.features.filters.HubFilterUsecase;
import net.impacthub.app.usecase.features.filters.SectorFilterUsecase;

import java.util.List;

import io.reactivex.annotations.NonNull;

import static net.impacthub.app.model.vo.filters.FilterData.KEY_FILTER_CITY;
import static net.impacthub.app.model.vo.filters.FilterData.KEY_FILTER_HUB;
import static net.impacthub.app.model.vo.filters.FilterData.KEY_FILTER_SECTOR;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public class FiltersUiPresenter extends UiPresenter<FiltersUiContract> {

    public FiltersUiPresenter(FiltersUiContract uiContract) {
        super(uiContract);
    }

    public void getFiltersByName(FilterBarVO filterBarVO) {
        if (filterBarVO != null) {
            String filterName = filterBarVO.getFilterName();
            if(KEY_FILTER_CITY.equalsIgnoreCase(filterName)) {
                fetchCityFilters(filterBarVO);
            } else if(KEY_FILTER_SECTOR.equalsIgnoreCase(filterName)) {
                fetchSectorsFilters(filterBarVO);
            } else if(KEY_FILTER_HUB.equalsIgnoreCase(filterName)) {
                fetchHubFilters(filterBarVO);
            }
        }
    }

    private void fetchHubFilters(FilterBarVO filterBarVO) {
        subscribeWith(new HubFilterUsecase().getUseCase(), new DisposableSingleObserverAdapter<FilterBarVO, List<FilterVO>>(filterBarVO) {

            @Override
            protected void onSuccess(List<FilterVO> filterVOs, FilterBarVO subject) {
                getUi().onLoadFilters(subject, filterVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    private void fetchSectorsFilters(FilterBarVO filterBarVO) {
        subscribeWith(new SectorFilterUsecase().getUseCase(), new DisposableSingleObserverAdapter<FilterBarVO, List<FilterVO>>(filterBarVO) {

            @Override
            protected void onSuccess(List<FilterVO> filterVOs, FilterBarVO subject) {
                getUi().onLoadFilters(subject, filterVOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    private void fetchCityFilters(FilterBarVO filterBarVO) {
        subscribeWith(new CitiesFilterUseCase().getUseCase(), new DisposableSingleObserverAdapter<FilterBarVO, List<FilterVO>>(filterBarVO) {

            @Override
            protected void onSuccess(List<FilterVO> filterVOs, FilterBarVO subject) {
                getUi().onLoadFilters(subject, filterVOs);
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
