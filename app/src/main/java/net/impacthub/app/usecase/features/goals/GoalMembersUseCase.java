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

package net.impacthub.app.usecase.features.goals;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.members.MembersResponse;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class GoalMembersUseCase extends BaseUseCaseGenerator<Single<List<MemberVO>>, MembersResponse> {

    private final String mGoalName;

    public GoalMembersUseCase(String goalName) {
        mGoalName = goalName;
    }

    @Override
    public Single<List<MemberVO>> getUseCase() {
        return Single.fromCallable(new Callable<MembersResponse>() {
            @Override
            public MembersResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createGoalMembersRequest(mGoalName), MembersResponse.class);
            }
        }).map(new Function<MembersResponse, List<MemberVO>>() {
            @Override
            public List<MemberVO> apply(@NonNull MembersResponse membersResponse) throws Exception {
                return new MembersMapper().mapMembers(membersResponse);
            }
        });
    }
}
