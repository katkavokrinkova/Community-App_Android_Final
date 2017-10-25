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

package net.impacthub.app.model.features.chatterfeed.comment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/25/2017.
 */

public class ModerationFlags {

    private String flaggedByMe;
    private String flagCount;

    @Override
    public String toString() {
        return "ModerationFlags [flaggedByMe = " + flaggedByMe + ", flagCount = " + flagCount + "]";
    }
}
