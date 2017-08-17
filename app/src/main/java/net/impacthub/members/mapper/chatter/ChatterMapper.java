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

package net.impacthub.members.mapper.chatter;

import net.impacthub.members.model.dto.chatter.ChatterDTO;
import net.impacthub.members.model.features.chatterfeed.Actor;
import net.impacthub.members.model.features.chatterfeed.Body;
import net.impacthub.members.model.features.chatterfeed.Capabilities;
import net.impacthub.members.model.features.chatterfeed.ChatterLikes;
import net.impacthub.members.model.features.chatterfeed.Comments;
import net.impacthub.members.model.features.chatterfeed.CommentsPage;
import net.impacthub.members.model.features.chatterfeed.Element;
import net.impacthub.members.model.features.chatterfeed.FeedElements;
import net.impacthub.members.model.features.chatterfeed.LikesPage;
import net.impacthub.members.model.features.chatterfeed.Photo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class ChatterMapper {

    public List<ChatterDTO> map(FeedElements feedElements) {
        List<ChatterDTO> chatterDTOs = new LinkedList<>();
        if (feedElements != null) {
            List<Element> elements = feedElements.getElements();
            if (elements != null) {
                for (int i = 0; i < elements.size(); i++) {
                    Element element = elements.get(i);
                    if (element != null) {
                        ChatterDTO chatterDTO = new ChatterDTO();
                        chatterDTO.mDate = element.getCreatedDate();

                        Actor actor = element.getActor();
                        if (actor != null) {
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
                                chatterDTO.mIsLikedByMe = chatterLikes.getIsLikedByCurrentUser();
                            }
                            Comments comments = capabilities.getComments();
                            if (comments != null) {
                                CommentsPage page = comments.getPage();
                                if (page != null) {
                                    chatterDTO.mCommentCount = page.getTotal();
                                }
                            }
                        }
                        chatterDTOs.add(chatterDTO);
                    }
                }
            }
        }
        return chatterDTOs;
    }
}
