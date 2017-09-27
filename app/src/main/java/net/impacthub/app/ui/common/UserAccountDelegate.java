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

package net.impacthub.app.ui.common;

import com.salesforce.androidsdk.accounts.UserAccount;

import static net.impacthub.app.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/27/2017.
 */

public class UserAccountDelegate {

    private final static UserAccount sUserAccount = userAccountProvider();

    public static String buildUrl(String url) {
        if(url != null && !url.startsWith("http")) {
            url = "https://eu11.salesforce.com" + url;
        }
        if (sUserAccount != null) {
            return url + "?oauth_token=" + sUserAccount.getAuthToken();
        }
        return url;
    }

    public static UserAccount getAccountManager() {
        return sUserAccount;
    }
}
