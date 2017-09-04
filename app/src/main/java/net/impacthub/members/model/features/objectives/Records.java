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

package net.impacthub.members.model.features.objectives;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class Records {

    private String Name;
    private String Goal_Summary__c;
    private String Directory__c;
    private String Goal__c;
    private String Id;
    private Attributes attributes;

    public String getName() {
        return Name;
    }

    public String getGoal_Summary__c() {
        return Goal_Summary__c;
    }

    public String getDirectory__c() {
        return Directory__c;
    }

    public String getGoal__c() {
        return Goal__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Records [Name = " + Name + ", Goal_Summary__c = " + Goal_Summary__c + ", Directory__c = " + Directory__c + ", Goal__c = " + Goal__c + ", Id = " + Id + ", attributes = " + attributes + "]";
    }
}
