package net.impacthub.members.presenter.features.filters;

import net.impacthub.members.model.features.filters.SeparatedFilters;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.filters.FiltersUseCase;

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
