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

package net.impacthub.app.usecase.features.filters;

import net.impacthub.app.mapper.filters.FiltersMapper;
import net.impacthub.app.model.features.filters.Filters;
import net.impacthub.app.model.vo.filters.FilterVO;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/26/2017.
 */

public class HubFilterUsecase extends BaseUseCaseGenerator<Single<List<FilterVO>>, Filters> {

    @Override
    public Single<List<FilterVO>> getUseCase() {
        return Single.fromCallable(new Callable<Filters>() {
            @Override
            public Filters call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createHubFilterCriteriaRequest(), Filters.class);
            }
        }).map(new Function<Filters, List<FilterVO>>() {
            @Override
            public List<FilterVO> apply(@NonNull Filters filters) throws Exception {
                return new FiltersMapper().map(filters);
            }
        });
    }
}