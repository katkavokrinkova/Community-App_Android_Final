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

package net.impacthub.app.model.vo.members;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/8/2017.
 */

public class AllMembersVO {

    private final List<MemberVO> mMemberVOS;
    private final boolean mDone;

    public AllMembersVO(List<MemberVO> memberVOS, boolean done) {
        mMemberVOS = memberVOS;
        mDone = done;
    }

    public List<MemberVO> getMemberVOS() {
        return mMemberVOS;
    }

    public boolean isDone() {
        return mDone;
    }
}
