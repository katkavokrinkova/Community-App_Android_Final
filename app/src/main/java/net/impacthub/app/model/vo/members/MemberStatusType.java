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

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public enum MemberStatusType {

    APPROVED(MemberStatus.APPROVED, "Approved"),
    DECLINED(MemberStatus.DECLINED, "Declined"),
    OUTSTANDING(MemberStatus.OUTSTANDING, "Outstanding"),
    APPROVE_DECLINE(MemberStatus.APPROVE_DECLINE_BY_ME, "Needs Approval or Declining"),
    NOT_CONTACTED(MemberStatus.NOT_CONTACTED, "Not Contacted");

    private final int mStatus;
    private final String mStatusText;

    MemberStatusType(@MemberStatus int status, String statusText) {
        mStatus = status;
        mStatusText = statusText;
    }

    @MemberStatus public int getStatus() {
        return mStatus;
    }

    public String getStatusText() {
        return mStatusText;
    }

    public static MemberStatusType fromStatus(@MemberStatus int type) {
        MemberStatusType theType = MemberStatusType.NOT_CONTACTED;
        MemberStatusType[] values = values();
        for (MemberStatusType value : values) {
            if (type == value.getStatus()) {
                theType = value;
                break;
            }
        }
        return theType;
    }
}
