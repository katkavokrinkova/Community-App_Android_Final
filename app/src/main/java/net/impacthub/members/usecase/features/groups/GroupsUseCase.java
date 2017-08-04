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

import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupsUseCase extends BaseUseCaseGenerator<Single<GroupsResponse>, GroupsResponse> {

    private final String mContactId;

    public GroupsUseCase(String contactId) {
        mContactId = contactId;
    }

    @Override
    public Single<GroupsResponse> getUseCase() {
        return Single.fromCallable(new Callable<GroupsResponse>() {
            @Override
            public GroupsResponse call() throws Exception {
                return getFiltersApiCall().getResponse(getSoqlRequestFactory().createGroupRequest(mContactId), GroupsResponse.class);
            }
        });
    }
}
