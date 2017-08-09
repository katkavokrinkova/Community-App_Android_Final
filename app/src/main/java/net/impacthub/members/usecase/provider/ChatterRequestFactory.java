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

package net.impacthub.members.usecase.provider;

import android.support.annotation.NonNull;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestRequest;

import net.impacthub.members.model.features.conversations.OutgoingMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

public class ChatterRequestFactory {

    private final UserAccount mUserAccount;

    ChatterRequestFactory() {
        this(userAccountProvider());
    }

    private ChatterRequestFactory(UserAccount userAccount) {
        mUserAccount = userAccount;
    }

    RestRequest createConversationsRequest() {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath("users/me/", "conversations?filterGroup=Small"));
    }

    RestRequest createConversationMessagesRequest(String conversationId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath("users/me/", "conversations/" + conversationId + "?filterGroup=Small"));
    }

    RestRequest createMarkConversationReadRequest(String conversationId) {
        return new RestRequest(RestRequest.RestMethod.PATCH,
                getPath("users/me/", "conversations/" + conversationId + "?include=/read"),
                createBodyWith("{\"read\":true}"));
    }

    RestRequest createSendMessageRequest(String messageBody, String messageId) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath("users/me/", "messages?include=/id"),
                new OutgoingMessage.Builder()
                        .body(messageBody)
                        .inReplyTo(messageId)
                        .build().toJson());
    }

    public RestRequest createChatterfeedRequest(String feedId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath("feeds/record/", feedId + "/feed-elements?filterGroup=Medium"));
    }

    @NonNull
    private JSONObject createBodyWith(String jsonText) {
        try {
            return new JSONObject(jsonText);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private String getPath(final String path, final String subject) {
        return ApiVersionStrings.getBaseConnectPath() +
                "communities/" +
                mUserAccount.getCommunityId() +
                "/chatter/" + path + subject;
    }
}