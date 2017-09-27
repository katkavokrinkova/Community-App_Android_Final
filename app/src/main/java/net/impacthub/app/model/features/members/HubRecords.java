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

package net.impacthub.app.model.features.members;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/27/2017.
 */

public class HubRecords {

    private String Hub_Name__c;
    private Attributes attributes;

    public String getHub_Name__c() {
        return Hub_Name__c;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "HubRecords [Hub_Name__c = " + Hub_Name__c + ", attributes = " + attributes + "]";
    }
}
