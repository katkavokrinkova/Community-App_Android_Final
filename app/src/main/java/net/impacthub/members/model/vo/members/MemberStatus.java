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

package net.impacthub.members.model.vo.members;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static net.impacthub.members.model.vo.members.MemberStatus.APPROVED;
import static net.impacthub.members.model.vo.members.MemberStatus.DECLINED;
import static net.impacthub.members.model.vo.members.MemberStatus.NOT_CONTACTED;
import static net.impacthub.members.model.vo.members.MemberStatus.OUTSTANDING;
import static net.impacthub.members.model.vo.members.MemberStatus.APPROVE_DECLINE_BY_ME;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/29/2017.
 */

@Retention(RetentionPolicy.RUNTIME)
@IntDef({OUTSTANDING, APPROVE_DECLINE_BY_ME, APPROVED, DECLINED, NOT_CONTACTED})
public @interface MemberStatus {

    int OUTSTANDING = 0;
    int APPROVE_DECLINE_BY_ME = 1;
    int APPROVED = 2;
    int DECLINED = 3;
    int NOT_CONTACTED = 4;
}
