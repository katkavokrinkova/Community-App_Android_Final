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

package net.impacthub.app.presenter.features.groups;

import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

public interface ChatterFeedUiContract extends ErrorHandlerUiContract {

    void onLoadChatterFeed(List<ChatterVO> chatterDTOs);

    void onLoadMember(MemberVO memberVO);

    void onPostLiked(Integer position);

    void onPostUnLiked(Integer subject);

    void onScrollToComment(int position);

    void onScrollToCommentAndOpenCommentReplies(ChatterVO chatterVO, int position, int commentPosition);
}
