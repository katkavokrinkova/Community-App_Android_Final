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

import android.support.annotation.NonNull;

import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestRequest;

import net.impacthub.members.application.salesforce.RestRequestFactory;
import net.impacthub.members.model.features.conversations.OutgoingMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.restRequestFactoryProvider;

public class SoqlRequestFactory {

    private static final String CONTACT = "id, firstname,lastname, ProfilePic__c, Profession__c, Impact_Hub_Cities__c," +
            " User__c,Skills__c, About_Me__c,Status_Update__c,Directory_Summary__c, Interested_SDG__c," +
            "How_Do_You_Most_Identify_with_Your_Curre__c,Twitter__c,Instagram__c,Facebook__c,Linked_In__c";

    private static final String PROFILE = "SELECT " + CONTACT + " FROM Contact WHERE User__c = '%s'";

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

    private static final String NOTIFICATIONS = "SELECT CreatedDate,FromUserId__c,Id,isRead__c,Name,RelatedId__c,Sent__c,Type__c," +
            "ProfilePicURL__c,Message__c, ChatterGroupId__c FROM PushNotification__c" +
            " WHERE toUserId__c = '%s' ORDER BY CreatedDate DESC LIMIT 250";

    private static final String COMPANIES = "SELECT id, name, Number_of_Employees__c, Impact_Hub_Cities__c, Sector_Industry__c," +
            " Logo_Image_Url__c, Banner_Image_Url__c,Affiliated_SDG__c, Twitter__c, Instagram__c, Facebook__c, LinkedIn__c," +
            " Website, About_Us__c FROM account where id IN (SELECT accountid FROM contact WHERE user__c != null)";

    private static final String GROUP = "id, name, CountOfMembers__c, ImageURL__c, Group_Desc__c,Related_Impact_Goal__c, Impact_Hub_Cities__c, ChatterGroupId__c, Directory_Style__c,Sector__c";
    private static final String ALL_GROUPS = "SELECT "+ GROUP + " FROM Directory__c WHERE Directory_Style__c = 'Group'";
    private static final String YOUR_GROUPS = "SELECT "+ GROUP + " FROM Directory__c " +
            "WHERE Directory_Style__c = 'Group' AND id IN (SELECT DirectoryID__c FROM Directory_Member__c WHERE ContactID__c ='%s')";

    private static final String GOALS = "select id, name,  Active__c, ImageURL__c, Summary__c, Description__c from Taxonomy__c where Grouping__c ='SDG'";

    private static final String JOB_COLUMNS = "id, name, Description__c, Salary__c, Job_Type__c, Sector__c,Contact__c, Location__c, Applications_Close_Date__c,Related_Impact_Goal__c,Company__c,Company__r.name,Company__r.Number_of_Employees__c, Company__r.Impact_Hub_Cities__c,Company__r.Company_Summary__c, Company__r.Sector_Industry__c, Company__r.Logo_Image_Url__c, Company__r.Banner_Image_Url__c, Company__r.Twitter__c, Company__r.Instagram__c, Company__r.Facebook__c, Company__r.LinkedIn__c, Company__r.Website, Company__r.About_Us__c,Job_Application_URL__c";

    private static final String JOBS = "SELECT " + JOB_COLUMNS + " FROM Job__c WHERE Applications_Close_Date__c >= %s";

    private static final String EVENTS_COLUMNS = "CreatedDate,Event_RegisterLink__c,Event_Description__c,Directory__c,Event_City__c,Event_Country__c,Event_Street__c,Event_ZipCode__c,Event_Classification__c,Event_Discount_Code__c,Event_Organiser_Type__c,Event_Quantity__c,Event_Sector__c,Event_Start_DateTime__c,Event_End_DateTime__c,Event_SubType__c,Event_Type__c,Event_Visibility__c,Id,LastModifiedDate,Name,Organiser__c,Organiser__r.name,OwnerId, Event_Image_URL__c FROM Event__c";

    private static final String ALL_EVENTS = "SELECT " + EVENTS_COLUMNS;
    private static final String EVENTS_YOU_MANAGE = "SELECT " + EVENTS_COLUMNS + " where Organiser__c = '%s'";
    private static final String YOUR_EVENTS = "SELECT " + EVENTS_COLUMNS + " where id in (SELECT Event__c FROM Event_Attendance__c where Registered__c = true and Contact__c = '%s')";

    private static final String PROJECT = "id,CreatedById, name,Related_Impact_Goal__c,ChatterGroupId__c ,Group_Desc__c, ImageURL__c, CountOfMembers__c, Impact_Hub_Cities__c, Directory_Style__c,Sector__c, Organisation__r.id, Organisation__r.Number_of_Employees__c, Organisation__r.Impact_Hub_Cities__c, Organisation__r.name";

    private static final String ALL_PROJECTS = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project'";
    private static final String PROJECTS_YOU_MANAGE = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project'";
    private static final String YOUR_PROJECTS = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project' AND id IN (select DirectoryID__c FROM Directory_Member__c WHERE ContactID__c ='%s')";

    private static final String COMPANY_PROJECT = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c ='Project' AND Organisation__c ='%s'";
    private static final String COMPANY_MEMBER = "SELECT " + CONTACT + " FROM Contact WHERE User__c != NULL AND accountid='%s'";

    private final RestRequestFactory mRestRequestFactory = restRequestFactoryProvider();

    public RestRequest createGetProfileRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(PROFILE, memberId));
    }

    public RestRequest createMemberListRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(memberListQuery);
    }

    public RestRequest createFilterCriteriaRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(filterCriteriaQuery);
    }

    public RestRequest createNotificationsRequest(String userId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(NOTIFICATIONS, userId));
    }

    public RestRequest createCompaniesRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(COMPANIES);
    }

    public RestRequest createAllGroupRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(ALL_GROUPS);
    }

    public RestRequest createYourGroupRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_GROUPS, contactId));
    }

    public RestRequest createGoalsRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(GOALS);
    }

    public RestRequest createJobsRequest(int skip, int top, String date) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(JOBS, date));
    }

    public RestRequest createAllProjectsRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(ALL_PROJECTS);
    }

    public RestRequest createYourProjectsRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_PROJECTS, contactId));
    }

    public RestRequest createAllEventsRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(ALL_EVENTS);
    }

    public RestRequest createYourEventsRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_EVENTS, contactId));
    }

    public RestRequest createEventsYouManageRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(EVENTS_YOU_MANAGE, contactId));
    }

    public RestRequest createMemberDetailRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(memberDetailQuery, memberId));
    }

    public RestRequest createCompanyProjectDetailRequest(String companyId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(COMPANY_PROJECT, companyId));
    }

    public RestRequest createCompanyMemberDetailRequest(String companyId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(COMPANY_MEMBER, companyId));
    }

    public RestRequest createMemberSkillsRequest(String memberId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(memberSkillsQuery, memberId));
    }

    public RestRequest createConversationsRequest(String communityId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/me/", "conversations"));
    }

    public RestRequest createConversationsRequest_Old(String communityId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/me/", "conversations?filterGroup=Small"));
    }

    public RestRequest createConversationMessagesRequest(String communityId, String conversationId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/me/", "conversations/" + conversationId + "?filterGroup=Small"));
    }

    public RestRequest createMarkConversationReadRequest(String communityId, String conversationId) {
        return new RestRequest(RestRequest.RestMethod.PATCH,
                getPath(communityId, "users/me/", "conversations/" + conversationId + "?include=/read"),
                createBodyWith("{\"read\":true}"));
    }

    public RestRequest createSendMessageRequest(String communityId, String messageBody, String messageId) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath(communityId, "users/me/", "messages?include=/id"),
                new OutgoingMessage.Builder()
                        .body(messageBody)
                        .inReplyTo(messageId)
                        .build().toJson());
    }

    public RestRequest createChatterFeedRequest(String communityId, String feedId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "feeds/record/", feedId + "/feed-elements?filterGroup=Medium"));
    }

    @NonNull
    private JSONObject createBodyWith(String jsonText) {
        try {
            return new JSONObject(jsonText);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private String getPath(final String communityId, final String path, final String subject) {
        return ApiVersionStrings.getBaseConnectPath() +
                "communities/" + communityId +
                "/chatter/" + path + subject;
    }
}
