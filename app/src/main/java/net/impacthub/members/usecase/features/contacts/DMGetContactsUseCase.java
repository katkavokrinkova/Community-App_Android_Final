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

package net.impacthub.members.usecase.features.contacts;

import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public class DMGetContactsUseCase extends BaseUseCaseGenerator<Single<ContactsResponse>, ContactsResponse> {

    private final String mContactId;

    public DMGetContactsUseCase(String contactId) {
        mContactId = contactId;
    }

    @Override
    public Single<ContactsResponse> getUseCase() {
        return Single.fromCallable(new Callable<ContactsResponse>() {
            @Override
            public ContactsResponse call() throws Exception {
                return getApiCall().getResponse(getSoqlRequestFactory().createDMGetContactsRequest(mContactId), ContactsResponse.class);
            }
        });
    }
}
