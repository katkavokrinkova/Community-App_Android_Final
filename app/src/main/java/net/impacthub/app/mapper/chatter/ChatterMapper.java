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

package net.impacthub.app.mapper.chatter;

import android.support.annotation.NonNull;

import net.impacthub.app.model.features.chatterfeed.Actor;
import net.impacthub.app.model.features.chatterfeed.Body;
import net.impacthub.app.model.features.chatterfeed.Capabilities;
import net.impacthub.app.model.features.chatterfeed.ChatterFeedResponse;
import net.impacthub.app.model.features.chatterfeed.ChatterLikes;
import net.impacthub.app.model.features.chatterfeed.Comment;
import net.impacthub.app.model.features.chatterfeed.Comments;
import net.impacthub.app.model.features.chatterfeed.CommentsPage;
import net.impacthub.app.model.features.chatterfeed.Element;
import net.impacthub.app.model.features.chatterfeed.LikesPage;
import net.impacthub.app.model.features.chatterfeed.MyLike;
import net.impacthub.app.model.features.chatterfeed.Photo;
import net.impacthub.app.model.features.chatterfeed.comment.CommentResponse;
import net.impacthub.app.model.features.chatterfeed.comment.User;
import net.impacthub.app.model.features.groups.chatter.ChatterResponse;
import net.impacthub.app.model.features.groups.chatter.Groups;
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.chatter.CommentVO;
import net.impacthub.app.utilities.DateUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ChatterMapper {

    public List<ChatterVO> map(ChatterFeedResponse feedElements) {
        List<ChatterVO> chatterDTOs = new LinkedList<>();
        if (feedElements != null) {
            List<Element> elements = feedElements.getElements();
            if (elements != null) {
                for (int i = 0; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    if (element != null) {
                        ChatterVO chatterDTO = mapChatterVO(element);
                        chatterDTOs.add(chatterDTO);
                    }
                }
            }
        }
        return chatterDTOs;
    }

    @NonNull
    public ChatterVO mapChatterVO(Element element) {
        ChatterVO chatterDTO = new ChatterVO();
        CommentVO commentVO = new CommentVO();

        chatterDTO.mCommentId = element.getId();
        chatterDTO.mDate = element.getCreatedDate();

        Actor actor = element.getActor();
        if (actor != null) {
            chatterDTO.mUserId = actor.getId();
            chatterDTO.mDisplayName = actor.getDisplayName();
            Photo photo = actor.getPhoto();
            if (photo != null) {
                chatterDTO.mImageURL = photo.getMediumPhotoUrl();
            }
        }
        Body body = element.getBody();
        if (body != null) {
            chatterDTO.mComment = body.getText();
        }
        Capabilities capabilities = element.getCapabilities();
        if (capabilities != null) {
            ChatterLikes chatterLikes = capabilities.getChatterLikes();
            if (chatterLikes != null) {
                LikesPage likesPage = chatterLikes.getLikesPage();
                if (likesPage != null) {
                    chatterDTO.mLikeCount = likesPage.getTotal();
                }
                MyLike myLike = chatterLikes.getMyLike();
                if (myLike != null) {
                    chatterDTO.mLikeId = myLike.getId();
                }
                chatterDTO.mIsLikedByMe = chatterLikes.getIsLikedByCurrentUser();
            }
            Comments comments = capabilities.getComments();
            if (comments != null) {
                CommentsPage page = comments.getPage();
                if (page != null) {
                    List<Comment> pageComments = page.getComments();
                    if (pageComments != null) {

                        for (Comment pageComment : pageComments) {
                            ChatComment chatComment = new ChatComment();

                            chatComment.mChatCommentId = pageComment.getId();
                            chatComment.mDate = DateUtils.getElapsedDateTime(pageComment.getCreatedDate());

                            Actor actor2 = pageComment.getActor();
                            if (actor2 != null) {
                                chatComment.mUserId = actor2.getId();
                                chatComment.mDisplayName = actor2.getDisplayName();
                                Photo actor2Photo = actor2.getPhoto();
                                if (actor2Photo != null) {
                                    chatComment.mImageURL = actor2Photo.getMediumPhotoUrl();
                                }
                            }
                            Body pageCommentBody = pageComment.getBody();
                            if (pageCommentBody != null) {
                                chatComment.mCommentTxt = pageCommentBody.getText();
                            }
                            commentVO.getComments().add(chatComment);
                        }
                    }
                    chatterDTO.mCommentCount = page.getTotal();
                }
            }
        }
        chatterDTO.mComments = commentVO;
        return chatterDTO;
    }

    public Set<String> mapChatterIdForGroups(ChatterResponse chatterGroupResponse) {
        Set<String> ids = new HashSet<>();
        if (chatterGroupResponse != null) {
            Groups[] groups = chatterGroupResponse.getGroups();
            if (groups != null) {
                for (Groups group : groups) {
                    ids.add(group.getId());
                }
            }
        }
        return ids;
    }

    public ChatComment mapCommentResponse(CommentResponse commentResponse) {
        ChatComment chatComment = new ChatComment();
        if (commentResponse != null) {
            chatComment.mChatCommentId = commentResponse.getId();
            chatComment.mDate = DateUtils.getElapsedDateTime(commentResponse.getCreatedDate());

            User user = commentResponse.getUser();
            if (user != null) {
                chatComment.mUserId = user.getId();
                chatComment.mDisplayName = user.getDisplayName();

                net.impacthub.app.model.features.chatterfeed.comment.Photo userPhoto = user.getPhoto();
                if (userPhoto != null) {
                    chatComment.mImageURL = userPhoto.getMediumPhotoUrl();
                }
            }

            net.impacthub.app.model.features.chatterfeed.comment.Body body = commentResponse.getBody();
            if (body != null) {
                chatComment.mCommentTxt = body.getText();
            }
        }
        return chatComment;
    }
}
