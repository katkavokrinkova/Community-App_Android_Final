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

package net.impacthub.app.usecase.features.members;

import net.impacthub.app.model.features.members.Skills;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class MemberSkillsUseCase extends BaseUseCaseGenerator<Single<Skills>, Skills> {

    private final String mMemberId;

    public MemberSkillsUseCase(String memberId) {
        mMemberId = memberId;
    }

    @Override
    public Single<Skills> getUseCase() {
        return Single.fromCallable(new Callable<Skills>() {
            @Override
            public Skills call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createMemberSkillsRequest(mMemberId), Skills.class);
            }
        });
    }
}