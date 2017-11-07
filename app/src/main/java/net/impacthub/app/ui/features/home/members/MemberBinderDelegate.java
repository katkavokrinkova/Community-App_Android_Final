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

package net.impacthub.app.ui.features.home.members;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.members.MemberStatus;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.ui.common.ImageLoaderHelper;

import static net.impacthub.app.ui.common.UserAccountDelegate.buildUrl;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/30/2017.
 */

public class MemberBinderDelegate {

    public static void bind(Context context,
                            MemberVO item,
                            TextView name,
                            TextView profession,
                            TextView locations,
                            ImageView memberImage,
                            ImageView iconMemberStatus) {
        name.setText(item.mFullName);
        profession.setText(String.format("%s at %s", item.mProfession, item.mCompanyName));
        locations.setText(item.mLocation);
        ImageLoaderHelper.loadImage(context, buildUrl(item.mProfilePicURL), memberImage);

        switch (item.mMemberStatus) {
            case MemberStatus.APPROVED:
                ImageLoaderHelper.loadImage(context, R.mipmap.comment_bubble_small, iconMemberStatus);
                break;
            case MemberStatus.OUTSTANDING:
                ImageLoaderHelper.loadImage(context, R.mipmap.member_waiting, iconMemberStatus);
                break;
            case MemberStatus.APPROVE_DECLINE_BY_ME:
                ImageLoaderHelper.loadImage(context, R.mipmap.member_waiting, iconMemberStatus);
                break;
            case MemberStatus.NOT_CONTACTED:
                ImageLoaderHelper.loadImage(context, R.mipmap.comment_bubble_small, iconMemberStatus);
                break;
            case MemberStatus.DECLINED:
                break;
        }
    }
}
