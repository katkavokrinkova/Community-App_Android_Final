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

package net.impacthub.app.model.features.projects;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class Organisation__r {

    private String Name;
    private String Number_of_Employees__c;
    private String Id;
    private Attributes attributes;
    private String Impact_Hub_Cities__c;

    public String getName() {
        return Name;
    }

    public String getNumber_of_Employees__c() {
        return Number_of_Employees__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getImpact_Hub_Cities__c() {
        return Impact_Hub_Cities__c;
    }

    @Override
    public String toString() {
        return "Organisation__r [Name = " + Name + ", Number_of_Employees__c = " + Number_of_Employees__c + ", Id = " + Id + ", attributes = " + attributes + ", Impact_Hub_Cities__c = " + Impact_Hub_Cities__c + "]";
    }
}
