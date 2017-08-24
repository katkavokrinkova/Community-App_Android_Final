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

package net.impacthub.members.usecase.features.groups;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.model.features.groups.chatter.ChatterResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/24/2017.
 */

public class GetMyGroupUseCase extends BaseUseCaseGenerator<Single<ChatterResponse>, ChatterResponse> {

    @Override
    public Single<ChatterResponse> getUseCase() {
        return Single.fromCallable(new Callable<ChatterResponse>() {
            @Override
            public ChatterResponse call() throws Exception {
                UserAccount userAccount = getUserAccount();
                return getApiCall().getResponse(getSoqlRequestFactory().createGetMyGroupsRequest(userAccount.getCommunityId(), userAccount.getUserId()), ChatterResponse.class);
            }
        });
    }
}
