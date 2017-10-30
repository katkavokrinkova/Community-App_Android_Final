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

package net.impacthub.app.presenter.features.search;

import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.search.GlobalSearchUseCase;
import net.impacthub.app.utilities.TextUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/6/2017.
 */

public class SearchUiPresenter extends UiPresenter<SearchUiContract> {

    public SearchUiPresenter(SearchUiContract uiContract) {
        super(uiContract);
    }

    public void search(String searchTerm) {
        getUi().onShowProgressBar(true);
        if(TextUtils.isEmpty(searchTerm)) {
            getUi().onError(new Throwable("Search term should not be empty."));
            getUi().onShowProgressBar(false);
            return;
        }

        subscribeWith(new GlobalSearchUseCase(searchTerm).getUseCase(), new DisposableSingleObserver<List<ListItemType>>() {
            @Override
            public void onSuccess(@NonNull List<ListItemType> listItemTypes) {
                getUi().onLoadSearchResultList(listItemTypes);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onShowProgressBar(false);
                getUi().onError(e);
            }
        });
    }
}
