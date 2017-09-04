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

package net.impacthub.members.model.features.goals;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class Records {

    private String Name;
    private String Summary__c;
    private String Active__c;
    private String Id;
    private String Description__c;
    private Attributes attributes;
    private String ImageURL__c;

    public String getName() {
        return Name;
    }

    public String getSummary__c() {
        return Summary__c;
    }

    public String getActive__c() {
        return Active__c;
    }

    public String getId() {
        return Id;
    }

    public String getDescription__c() {
        return Description__c;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public String getImageURL__c() {
        return ImageURL__c;
    }

    @Override
    public String toString() {
        return "Records [Name = " + Name + ", Summary__c = " + Summary__c + ", Active__c = " + Active__c + ", Id = " + Id + ", Description__c = " + Description__c + ", attributes = " + attributes + ", ImageURL__c = " + ImageURL__c + "]";
    }
}
