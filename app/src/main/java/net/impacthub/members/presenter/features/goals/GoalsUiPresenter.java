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

package net.impacthub.members.presenter.features.goals;

import net.impacthub.members.mapper.goals.GoalsMapper;
import net.impacthub.members.model.vo.goals.GoalVO;
import net.impacthub.members.model.features.goals.GoalsResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.goals.GoalsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class GoalsUiPresenter extends UiPresenter<GoalsUiContract> {

    private final UseCaseGenerator<Single<GoalsResponse>> mGoalsUseCase = new GoalsUseCase();

    public GoalsUiPresenter(GoalsUiContract uiContract) {
        super(uiContract);
    }

    public void getGoals() {
        getUi().onChangeStatus(true);
        Single<List<GoalVO>> goalsSingle = mGoalsUseCase.getUseCase()
                .map(new Function<GoalsResponse, List<GoalVO>>() {
                    @Override
                    public List<GoalVO> apply(@NonNull GoalsResponse goalsResponse) throws Exception {
                        return new GoalsMapper().map(goalsResponse);
                    }
                });
        subscribeWith(goalsSingle, new DisposableSingleObserver<List<GoalVO>>() {
            @Override
            public void onSuccess(@NonNull List<GoalVO> goalVOs) {
                getUi().onLoadGoals(goalVOs);
                getUi().onChangeStatus(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }
}
