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

package net.impacthub.members.model.vo.contacts;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/25/2017.
 */

public class UpdateContactBody {

    private final String DM_id;
    private final String Req_status;
    private final String pushUserId;

    public UpdateContactBody(String DM_id, String Req_status, String pushUserId) {
        this.DM_id = DM_id;
        this.Req_status = Req_status;
        this.pushUserId = pushUserId;
    }
}
