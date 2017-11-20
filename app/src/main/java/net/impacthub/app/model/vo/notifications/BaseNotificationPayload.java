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

package net.impacthub.app.model.vo.notifications;

import java.io.Serializable;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/17/2017.
 */

public class BaseNotificationPayload implements Serializable {

    private String type;

    public String getType() {
        return type;
    }
}
