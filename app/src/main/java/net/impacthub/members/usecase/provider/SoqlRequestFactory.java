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

package net.impacthub.members.usecase.provider;

import com.salesforce.androidsdk.rest.RestRequest;

import net.impacthub.members.application.salesforce.RestRequestFactory;

import java.io.UnsupportedEncodingException;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.restRequestFactoryProvider;

public class SoqlRequestFactory {

    private static final String CONTACT = "select id, firstname,lastname, ProfilePic__c, Profession__c, Impact_Hub_Cities__c," +
            " User__c,Skills__c, About_Me__c,Status_Update__c,Directory_Summary__c, Interested_SDG__c," +
            "How_Do_You_Most_Identify_with_Your_Curre__c,Twitter__c,Instagram__c,Facebook__c,Linked_In__c FROM Contact WHERE User__c = '%s'";

    private static final String memberListQuery =
            "select id, firstname, lastname, ProfilePic__c, Profession__c, Impact_Hub_Cities__c,"
                    + "User__c, Skills__c, About_Me__c, Twitter__c, Instagram__c, Facebook__c, Linked_In__c "
                    + "FROM Contact where User__c != null";

    private static final String filterCriteriaQuery =
            "select name, Grouping__c, Id from taxonomy__c "
                    + "where (Grouping__c = 'City' or Grouping__c = 'Sector') and active__c = true";

    private static final String memberDetailQuery =
            "select id, name, CountOfMembers__c, ImageURL__c, Group_Desc__c, Impact_Hub_Cities__c, "
                    + "ChatterGroupId__c, Organisation__r.Name, Organisation__c, Directory_Style__c "
                    + "from Directory__c where (Directory_Style__c ='Project' or Directory_Style__c = 'Group') "
                    + "and id in (select DirectoryID__c from Directory_Member__c where ContactID__c ='%s')";

    private static final String memberSkillsQuery =
            "select id, name, Skill_Description__c from Contact_Skills__c where Contact__r.id ='%s'";

    private final RestRequestFactory mRestRequestFactory = restRequestFactoryProvider();

    public RestRequest createGetProfileRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(CONTACT, memberId));
    }

    public RestRequest createMemberListRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(memberListQuery);
    }

    public RestRequest createFilterCriteriaRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(filterCriteriaQuery);
    }

    public RestRequest createMemberDetailRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(memberDetailQuery, memberId));
    }

    public RestRequest createMemberSkillsRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(memberSkillsQuery, memberId));
    }
}
