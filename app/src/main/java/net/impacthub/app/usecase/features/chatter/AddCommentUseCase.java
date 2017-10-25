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

package net.impacthub.app.usecase.features.chatter;

import com.google.gson.Gson;

import net.impacthub.app.mapper.chatter.ChatterMapper;
import net.impacthub.app.model.features.chatter.PostCommentPayload;
import net.impacthub.app.model.features.chatterfeed.comment.CommentResponse;
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/24/2017.
 */

public class AddCommentUseCase extends BaseUseCaseGenerator<Single<ChatComment>, CommentResponse> {

    private final String mCommentID;
    private final PostCommentPayload mPayLoad;

    public AddCommentUseCase(String commentID, PostCommentPayload payload) {
        mCommentID = commentID;
        mPayLoad = payload;
    }

    @Override
    public Single<ChatComment> getUseCase() {
        return Single.fromCallable(new Callable<CommentResponse>() {
            @Override
            public CommentResponse call() throws Exception {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(mPayLoad));
                String communityId = getUserAccount().getCommunityId();
                return getApiCall().getResponse(getSoqlRequestFactory().createAddCommentRequest(communityId, mCommentID, jsonObject), CommentResponse.class);
            }
        }).map(new Function<CommentResponse, ChatComment>() {
            @Override
            public ChatComment apply(@NonNull CommentResponse commentResponse) throws Exception {
                return new ChatterMapper().mapCommentResponse(commentResponse);
            }
        });
    }
}
