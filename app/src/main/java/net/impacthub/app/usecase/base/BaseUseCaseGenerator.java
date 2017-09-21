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

package net.impacthub.app.usecase.base;

import com.google.gson.Gson;
import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.app.usecase.provider.ApiCall;
import net.impacthub.app.usecase.provider.SoqlRequestFactory;

import static net.impacthub.app.application.salesforce.SalesforceModuleDependency.userAccountProvider;
import static net.impacthub.app.usecase.provider.UseCaseModule.apiCallProvider;
import static net.impacthub.app.usecase.provider.UseCaseModule.soqlRequestFactoryProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public abstract class BaseUseCaseGenerator<UC, R> implements UseCaseGenerator<UC> {

    private final Gson mGson = new Gson();
    private final ApiCall<R> mApiCall = apiCallProvider();
    private final SoqlRequestFactory mSoqlRequestFactory = soqlRequestFactoryProvider();
    private final UserAccount mUserAccount;

    public BaseUseCaseGenerator() {
        this(userAccountProvider());
    }

    private BaseUseCaseGenerator(UserAccount userAccount) {
        mUserAccount = userAccount;
    }

    protected ApiCall<R> getApiCall() {
        return mApiCall;
    }

    protected SoqlRequestFactory getSoqlRequestFactory() {
        return mSoqlRequestFactory;
    }

    protected UserAccount getUserAccount() {
        return mUserAccount;
    }

    protected  <T> T convertJson(String json, Class<T> type) {
        return mGson.fromJson(json, type);
    }
}
