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

package net.impacthub.app.usecase.features.search;

import com.google.gson.Gson;

import net.impacthub.app.mapper.searches.SearchMapper;
import net.impacthub.app.model.features.search.SearchBody;
import net.impacthub.app.model.features.search.SearchResponse;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/6/2017.
 */

public class GlobalSearchUseCase extends BaseUseCaseGenerator<Single<List<ListItemType>>, SearchResponse> {

    private final String mSearchTerm;

    public GlobalSearchUseCase(String searchTerm) {
        mSearchTerm = searchTerm;
    }

    @Override
    public Single<List<ListItemType>> getUseCase() {
        return Single.fromCallable(new Callable<SearchResponse>() {
            @Override
            public SearchResponse call() throws Exception {
                SearchBody searchBody = new SearchBody(mSearchTerm);
                return getApiCall().getResponse(getSoqlRequestFactory().createGlobalSearchRequest(new JSONObject(new Gson().toJson(searchBody))), SearchResponse.class);
            }
        }).map(new Function<SearchResponse, List<ListItemType>>() {
                    @Override
                    public List<ListItemType> apply(@NonNull SearchResponse searchResponse) throws Exception {
                        return new SearchMapper().map(searchResponse);
                    }
                });
    }
}
