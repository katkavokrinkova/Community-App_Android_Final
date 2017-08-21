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
        subscribeWith(mGoalsUseCase.getUseCase(), new DisposableSingleObserver<GoalsResponse>() {
            @Override
            public void onSuccess(@NonNull GoalsResponse response) {
                List<GoalVO> goals = new GoalsMapper().map(response);
                getUi().onLoadGoals(goals);
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
