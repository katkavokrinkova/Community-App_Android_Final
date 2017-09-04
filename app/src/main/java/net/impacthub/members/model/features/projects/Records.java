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

package net.impacthub.members.model.features.projects;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class Records {

    private String Name;
    private String Group_Desc__c;
    private String Directory_Style__c;
    private String Related_Impact_Goal__c;
    private String CreatedById;
    private String Sector__c;
    private String ChatterGroupId__c;
    private String Id;
    private Attributes attributes;
    private Organisation__r Organisation__r;
    private String ImageURL__c;
    private String CountOfMembers__c;
    private String Impact_Hub_Cities__c;

    public String getName() {
        return Name;
    }

    public String getGroup_Desc__c() {
        return Group_Desc__c;
    }

    public String getDirectory_Style__c() {
        return Directory_Style__c;
    }

    public String getRelated_Impact_Goal__c() {
        return Related_Impact_Goal__c;
    }

    public String getCreatedById() {
        return CreatedById;
    }

    public String getSector__c() {
        return Sector__c;
    }

    public String getChatterGroupId__c() {
        return ChatterGroupId__c;
    }

    public String getId() {
        return Id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public net.impacthub.members.model.features.projects.Organisation__r getOrganisation__r() {
        return Organisation__r;
    }

    public String getImageURL__c() {
        return ImageURL__c;
    }

    public String getCountOfMembers__c() {
        return CountOfMembers__c;
    }

    public String getImpact_Hub_Cities__c() {
        return Impact_Hub_Cities__c;
    }

    @Override
    public String toString() {
        return "Records [Name = " + Name + ", Group_Desc__c = " + Group_Desc__c + ", Directory_Style__c = " + Directory_Style__c + ", Related_Impact_Goal__c = " + Related_Impact_Goal__c + ", CreatedById = " + CreatedById + ", Sector__c = " + Sector__c + ", ChatterGroupId__c = " + ChatterGroupId__c + ", Id = " + Id + ", attributes = " + attributes + ", Organisation__r = " + Organisation__r + ", ImageURL__c = " + ImageURL__c + "]";
    }
}
