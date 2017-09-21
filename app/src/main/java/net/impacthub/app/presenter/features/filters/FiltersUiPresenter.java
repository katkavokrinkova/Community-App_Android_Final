package net.impacthub.app.presenter.features.filters;

import net.impacthub.app.model.features.filters.SeparatedFilters;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.filters.FiltersUseCase;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/28/2017.
 */

public class FiltersUiPresenter extends UiPresenter<FiltersUiContract> {

    private final UseCaseGenerator<Single<SeparatedFilters>> mObservableGenerator = new FiltersUseCase();

    public FiltersUiPresenter(FiltersUiContract uiContract) {
        super(uiContract);
    }

    public void getFiltersList() {
        subscribeWith(mObservableGenerator.getUseCase(), new DisposableSingleObserver<SeparatedFilters>() {
            @Override
            public void onSuccess(@NonNull SeparatedFilters separatedFilters) {
                getUi().onLoadFilters(separatedFilters);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
