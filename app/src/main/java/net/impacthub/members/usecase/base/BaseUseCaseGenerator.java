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

package net.impacthub.members.usecase.base;

import com.salesforce.androidsdk.accounts.UserAccount;

import net.impacthub.members.usecase.provider.ApiCall;
import net.impacthub.members.usecase.provider.SoqlRequestFactory;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;
import static net.impacthub.members.usecase.provider.UseCaseModule.apiCallProvider;
import static net.impacthub.members.usecase.provider.UseCaseModule.soqlRequestFactoryProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public abstract class BaseUseCaseGenerator<UC, R> implements UseCaseGenerator<UC> {

    private final ApiCall<R> mFiltersApiCall = apiCallProvider();
    private final SoqlRequestFactory mSoqlRequestFactory = soqlRequestFactoryProvider();
    private final UserAccount mUserAccount;

    public BaseUseCaseGenerator() {
        this(userAccountProvider());
    }

    BaseUseCaseGenerator(UserAccount userAccount) {
        mUserAccount = userAccount;
    }

    protected ApiCall<R> getFiltersApiCall() {
        return mFiltersApiCall;
    }

    protected SoqlRequestFactory getSoqlRequestFactory() {
        return mSoqlRequestFactory;
    }

    protected UserAccount getUserAccount() {
        return mUserAccount;
    }
}
