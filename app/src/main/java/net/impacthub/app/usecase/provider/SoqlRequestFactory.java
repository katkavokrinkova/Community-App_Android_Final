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

package net.impacthub.app.usecase.provider;

import android.support.annotation.NonNull;

import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestRequest;

import net.impacthub.app.application.salesforce.RestRequestFactory;
import net.impacthub.app.model.features.messages.OutgoingMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import okhttp3.RequestBody;

import static net.impacthub.app.application.salesforce.SalesforceModuleDependency.restRequestFactoryProvider;

public class SoqlRequestFactory {

    //User__c is the user ID
    //id is the contact ID
    private static final String CONTACT = "id, firstname,lastname, ProfilePic__c, Profession__c, Impact_Hub_Cities__c, Name, Sector__c," +
            "Account.Name, User__c,Skills__c, AboutMe__c,Status_Update__c,Directory_Summary__c, Interested_SDG__c," +
            "How_Do_You_Most_Identify_with_Your_Curre__c,Twitter__c,Instagram__c,Facebook__c,Linked_In__c, (select Hub_Name__c from contact.Hubs__r) ";

    private static final String  GROUP_PROJECT = "id, name, CountOfMembers__c, ImageURL__c, Group_Descr__c, Impact_Hub_Cities__c, ChatterGroupId__c, Directory_Style__c,Sector__c, CreatedById, Related_Impact_Goal__c, Organisation__r.id, Organisation__r.Number_of_Employees__c, Organisation__r.Impact_Hub_Cities__c, Organisation__r.name,ChatterGroupType__c";

    private static final String PROFILE = "SELECT " + CONTACT + " FROM Contact WHERE User__c = '%s'";
    private static final String ALL_MEMBERS_PROFILE = "SELECT " + CONTACT + " FROM Contact where User__c != null and User__r.isactive = true OFFSET %d";
    private static final String CONTACT_MEMBERS_PROFILE = "SELECT " + CONTACT + " FROM Contact where id IN (%s)";

//    private static final String memberListQuery =
//            "select id, firstname, lastname, ProfilePic__c, Profession__c, Impact_Hub_Cities__c,"
//                    + "User__c, Skills__c, About_Me__c, Twitter__c, Instagram__c, Facebook__c, Linked_In__c "
//                    + "FROM Contact where User__c != null";


    private static final String HUB_FILTER =
            "SELECT Id,Name,RecordTypeId FROM Account where RecordTypeId in (SELECT Id FROM RecordType WHERE SobjectType = 'Account' AND Name = 'Hub' AND IsActive = true)";

    private static final String CITY_FILTER =
            "select name, Grouping__c, Id from taxonomy__c "
                    + "where (Grouping__c = 'City') and active__c = true";

    private static final String SECTOR_FILTER =
            "select name, Grouping__c, Id from taxonomy__c "
                    + "where (Grouping__c = 'Sector') and active__c = true";

    private static final String memberDetailQuery =
            "select id, name, CountOfMembers__c, ImageURL__c, Group_Descr__c, Impact_Hub_Cities__c, "
                    + "ChatterGroupId__c, Organisation__r.Name, Organisation__c, Directory_Style__c "
                    + "from Directory__c where (Directory_Style__c ='Project' or Directory_Style__c = 'Group') "
                    + "and id in (select DirectoryID__c from Directory_Member__c where ContactID__c ='%s')";

    private static final String memberSkillsQuery =
            "select id, name, Skill_Description__c from Contact_Skills__c where Contact__r.id ='%s'";

    private static final String NOTIFICATIONS = "SELECT CreatedDate,FromUserId__c,Id,isRead__c,Name,RelatedId__c,Sent__c,Type__c," +
            "ProfilePicURL__c,Message__c, ChatterGroupId__c FROM PushNotification__c" +
            " WHERE toUserId__c = '%s' AND FromUserId__c != '%s' ORDER BY CreatedDate DESC LIMIT 250";

    private static final String COMPANIES = "SELECT id, name, Number_of_Employees__c, Impact_Hub_Cities__c, Sector_Industry__c," +
            " Logo_Image_Url__c, Banner_Image_Url__c,Affiliated_SDG__c, Twitter__c, Instagram__c, Facebook__c, LinkedIn__c," +
            " Website, Company_About_Us__c FROM account where id IN (SELECT accountid FROM contact WHERE user__c != null and User__r.isactive = true)";

    private static final String COMPANY_SERVICE_COLUMNS = "id, name, Service_Description__c FROM Company_Service__c";
    private static final String COMPANY_SERVICES = "SELECT " + COMPANY_SERVICE_COLUMNS + "  WHERE Company__r.id ='%s'";

    private static final String GROUP = "id, CreatedById, name, CountOfMembers__c, ImageURL__c, Group_Descr__c,Related_Impact_Goal__c, Impact_Hub_Cities__c, ChatterGroupType__c, ChatterGroupId__c, Directory_Style__c,Sector__c";
    private static final String ALL_GROUPS = "SELECT "+ GROUP + " FROM Directory__c WHERE Directory_Style__c = 'Group'";
    private static final String GROUPS_YOU_MANAGE = "SELECT "+ GROUP + " FROM Directory__c WHERE Directory_Style__c = 'Group' AND id IN (SELECT DirectoryID__c FROM Directory_Member__c WHERE Member_Role__c = 'Manager' AND ContactID__c ='%s')";
    private static final String YOUR_GROUPS = "SELECT "+ GROUP + " FROM Directory__c " +
            "WHERE Directory_Style__c = 'Group' AND id IN (SELECT DirectoryID__c FROM Directory_Member__c WHERE ContactID__c ='%s')";
    private static final String GOAL_GROUPS = ALL_GROUPS + " AND Related_Impact_Goal__c LIKE '%s'";
    private static final String GOAL_MEMBERS = "SELECT " + CONTACT + " FROM Contact WHERE Interested_SDG__c INCLUDES ('%s')";
    private static final String CHATTER_GROUP_OR_PROJECT = "SELECT " + GROUP_PROJECT + " FROM Directory__c WHERE ChatterGroupId__c ='%s' AND isMakerSpecific__c = false";

    private static final String GOALS = "select id, name,  Active__c, ImageURL__c, Summary__c, Description__c from Taxonomy__c where Grouping__c ='SDG'";

    private static final String JOB_COLUMNS = "id, name, Description__c, Salary2__c, Job_Type__c, Sector__c,Contact__c, Contact__r.AccountId, " +
            " Location__c, Applications_Close_Date__c,Related_Impact_Goal__c,Company__c,Company__r.name,Company__r.Number_of_Employees__c," +
            " Company__r.Impact_Hub_Cities__c,Company__r.Company_Summary__c, Company__r.Sector_Industry__c, Company__r.Logo_Image_Url__c," +
            " Company__r.Banner_Image_Url__c, Company__r.Twitter__c, Company__r.Instagram__c, Company__r.Facebook__c, Company__r.LinkedIn__c," +
            " Company__r.Website, Company__r.Company_About_Us__c,Job_Application_URL__c";

    private static final String JOBS = "SELECT " + JOB_COLUMNS + " FROM Job__c WHERE Applications_Close_Date__c >= %s";

    private static final String EVENTS_COLUMNS = "CreatedDate,Event_RegisterLink__c,Event_Description__c,Directory__c,Event_City__c,Event_Country__c,Event_Street__c,Event_ZipCode__c,Event_Classification__c,Event_Discount_Code__c,Event_Organiser_Type__c,Event_Quantity__c,Event_Sector__c,Event_Start_DateTime__c,Event_End_DateTime__c,Event_SubType__c,Event_Type__c,Event_Visibility__c,Id,LastModifiedDate,Name,Organiser__c,Organiser__r.name,OwnerId, Event_Image_URL__c FROM Event__c";

    private static final String ALL_EVENTS = "SELECT " + EVENTS_COLUMNS + " where Event_End_DateTime__c >= %s ORDER BY Event_Start_DateTime__c ASC";
    private static final String EVENTS_YOU_MANAGE = "SELECT " + EVENTS_COLUMNS + " where Organiser__c = '%s' and Event_End_DateTime__c >= %s ORDER BY Event_Start_DateTime__c ASC";
    private static final String YOUR_EVENTS = "SELECT " + EVENTS_COLUMNS + " where id in (SELECT Event__c FROM Event_Attendance__c where Registered__c = true and Contact__c = '%s') and Event_End_DateTime__c >= %s ORDER BY Event_Start_DateTime__c ASC";

    private static final String PROJECT = "id,CreatedById, ChatterGroupType__c, name,Related_Impact_Goal__c,ChatterGroupId__c ,Group_Descr__c, ImageURL__c, CountOfMembers__c, Impact_Hub_Cities__c, Directory_Style__c,Sector__c, Organisation__r.id, Organisation__r.Number_of_Employees__c, Organisation__r.Impact_Hub_Cities__c, Organisation__r.name";

    private static final String ALL_PROJECTS = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project'";
    private static final String PROJECTS_YOU_MANAGE = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project'";
    private static final String YOUR_PROJECTS = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c = 'Project' AND id IN (select DirectoryID__c FROM Directory_Member__c WHERE ContactID__c ='%s')";
    private static final String PROJECT_MEMBER = "SELECT " + CONTACT + " FROM Contact WHERE id IN (SELECT contactID__c FROM Directory_Member__c WHERE DirectoryID__c='%s')";
    private static final String PROJECT_JOBS = "SELECT " + JOB_COLUMNS + " FROM Job__c WHERE Project__r.id='%s'";

    private static final String RELATED_PROJECTS = "SELECT Name, Contact__c, Description__c, Company__r.name ,Company__r.id, Project__c,Job_Application_URL__c," +
            " maxSalary__c, minSalary__c, Salary2__c, Sector__c, Job_Type__c, Contact__r.AccountId, " +
            "Location__c FROM Job__c WHERE id != '%s' and (Location__c = '%s'" +
            " or Contact__r.AccountId = '%s'";

    private static final String JOB_RELATED_PROJECTS = "SELECT " + PROJECT + " FROM Directory__c WHERE isMakerSpecific__c = false AND Organisation__c in (SELECT Company__c FROM Job__c WHERE id ='%s')";
    private static final String JOB_RELATED_JOBS = "SELECT " + JOB_COLUMNS + " FROM Job__c WHERE id='%s' AND (Location__c = '%s' OR Contact__r.AccountId = '%s' ";

    private static final String COMPANY_PROJECT = "SELECT " + PROJECT + " FROM Directory__c WHERE Directory_Style__c ='Project' AND Organisation__c ='%s'";
    private static final String COMPANY_MEMBER = "SELECT " + CONTACT + " FROM Contact WHERE User__c != NULL and User__r.isactive = true AND accountid='%s'";

    private static final String OBJECTIVES = "SELECT Directory__c,Goal_Summary__c,Goal__c,Id,Name FROM Directory_Goal__c WHERE Directory__c='%s'";

    private static final String DM_COLUMNS = "ContactFrom__c,ContactTo__c,CreatedDate,Id,Name,Status__c,Introduction_Message__c";
    private static final String DIRECT_MESSAGE_GET_CONTACTS_QUERY = "SELECT " + DM_COLUMNS +" FROM DM_Request__c WHERE ContactFrom__c = '%s' OR contactTo__c = '%s'";

    private final RestRequestFactory mRestRequestFactory = restRequestFactoryProvider();

    public RestRequest createGetProfileRequest(String userId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(PROFILE, userId));
    }

    public RestRequest createMemberListRequest(int offset) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(Locale.UK, ALL_MEMBERS_PROFILE, offset));
    }
    public RestRequest createContactMemberListRequest(String contactIds) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(Locale.UK, CONTACT_MEMBERS_PROFILE, contactIds));
    }
//    public RestRequest createFilterCriteriaRequest() throws UnsupportedEncodingException {
//        return mRestRequestFactory.getForQuery(filterCriteriaQuery);
//    }

    public RestRequest createHubFilterCriteriaRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(HUB_FILTER);
    }

    public RestRequest createCityFilterCriteriaRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(CITY_FILTER);
    }

    public RestRequest createSectorFilterCriteriaRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(SECTOR_FILTER);
    }

    public RestRequest createNotificationsRequest(String userId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(NOTIFICATIONS, userId, userId));
    }

    public RestRequest createCompaniesRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(COMPANIES);
    }

    public RestRequest createCompanyServicesRequest(String companyId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(COMPANY_SERVICES, companyId));
    }

    public RestRequest createAllGroupRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(ALL_GROUPS);
    }

    public RestRequest createGroupYouManageRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(GROUPS_YOU_MANAGE, contactId));
    }

    public RestRequest createYourGroupRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_GROUPS, contactId));
    }

    public RestRequest createGetMyGroupsRequest(String communityId, String userId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/", userId + "/groups?filterGroup=Medium"));
    }

    public RestRequest createGoalGroupsRequest(String goalName) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(GOAL_GROUPS, goalName));
    }

    public RestRequest createGroupOrProjectRequest(String chatterGroupId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(CHATTER_GROUP_OR_PROJECT, chatterGroupId));
    }

    public RestRequest createGoalsRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(GOALS);
    }

    public RestRequest createGoalMembersRequest(String goalName) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(GOAL_MEMBERS, goalName));
    }

    public RestRequest createProjectObjectivesRequest(String projectId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(OBJECTIVES, projectId));
    }

    public RestRequest createProjectMembersRequest(String projectId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(PROJECT_MEMBER, projectId));
    }

    public RestRequest createProjectJobsRequest(String projectId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(PROJECT_JOBS, projectId));
    }

    public RestRequest createJobRelatedJobsRequest(String jobId, String jobLocation, String accountId, String jobCompany) throws UnsupportedEncodingException {
        String query = String.format(JOB_RELATED_JOBS, jobId, jobLocation, accountId);
        if (jobCompany != null) {
            query += String.format(Locale.UK, "  OR (Company__c  != null and  Company__c = '%s') ", jobCompany);
        }
        return mRestRequestFactory.getForQuery(String.format("%s)", query));
    }

    public RestRequest createJobRelatedProjectsRequest(String jobId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(JOB_RELATED_PROJECTS, jobId));
    }

//    public RestRequest createJobRelatedProjectsRequest(String jobId, String jobLocation, String accountId, String jobCompany) throws UnsupportedEncodingException {
//        String query = String.format(RELATED_PROJECTS, jobId, jobLocation, accountId);
//        if (jobCompany != null) {
//            query += String.format(Locale.UK, " or Company__c = '%s'", jobCompany);
//        }
//        return mRestRequestFactory.getForQuery(String.format("%s)", query));
//    }

    public RestRequest createJobsRequest(int skip, int top, String date) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(JOBS, date));
    }

    public RestRequest createAllProjectsRequest() throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(ALL_PROJECTS);
    }

    public RestRequest createYourProjectsRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_PROJECTS, contactId));
    }

    public RestRequest createAllEventsRequest(String date) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(ALL_EVENTS, date));
    }

    public RestRequest createYourEventsRequest(String contactId, String date) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(YOUR_EVENTS, contactId, date));
    }

    public RestRequest createEventsYouManageRequest(String contactId, String date) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(EVENTS_YOU_MANAGE, contactId, date));
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

    public RestRequest createDMGetContactsRequest(String contactId) throws UnsupportedEncodingException {
        return mRestRequestFactory.getForQuery(String.format(DIRECT_MESSAGE_GET_CONTACTS_QUERY, contactId, contactId));
    }

    public RestRequest createDMCreateContactRequest(JSONObject jsonObject) throws UnsupportedEncodingException {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/CreateDMRequest/", jsonObject);
    }

    public RestRequest createMessageRequest(String communityId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/me/", "conversations"));
    }

    public RestRequest createGlobalSearchRequest(JSONObject jsonObject) throws UnsupportedEncodingException {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/callGlobalSearch/", jsonObject);
    }

//    public RestRequest createConversationsRequest_Old(String communityId) {
//        return new RestRequest(RestRequest.RestMethod.GET,
//                getPath(communityId, "users/me/", "conversations?filterGroup=Small"));
//    }

    public RestRequest createConversationMessagesRequest(String communityId, String conversationId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "users/me/", "conversations/" + conversationId + "?filterGroup=Small"));
    }

    public RestRequest createMarkConversationReadRequest(String communityId, String conversationId) {
        return new RestRequest(RestRequest.RestMethod.PATCH,
                getPath(communityId, "users/me/", "conversations/" + conversationId + "?include=/read"),
                createBodyWith("{\"read\":true}"));
    }

    public RestRequest createMarkNotificationReadRequest(String notificationId) {
        JSONObject body = createBodyWith(String.format("{\"notificationIds\": \"%s\"}", notificationId));
        return new RestRequest(RestRequest.RestMethod.POST,"/services/apexrest/updateReadReceipt/", body);
    }

    public RestRequest createSendMessageWithUserIdRequest(String communityId, JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath(communityId, "users/me/", "messages?include=/id"), jsonObject);
    }

    public RestRequest createSendMessageRequest(String communityId, String messageBody, String messageId) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath(communityId, "users/me/", "messages?include=/id"),
                new OutgoingMessage.Builder()
                        .body(messageBody)
                        .inReplyTo(messageId)
                        .build().toJson());
    }

    public RestRequest createSendPushRequest(JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/pushNotificationFromSF/",
                jsonObject);
    }

    public RestRequest createAttendEventRequest(JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/attendEvent/",
                jsonObject);
    }

    public RestRequest createUnAttendEventRequest(JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/unAttendEvent/",
                jsonObject);
    }

    public RestRequest createUpdateDMRequest(JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/UpdateDMRequest/",
                jsonObject);
    }

    public RestRequest createDeleteDMRequest(JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                "/services/apexrest/DeleteDMRequest/",
                jsonObject);
    }

    public RestRequest createChatterFeedRequest(String communityId, String feedId) {
        return new RestRequest(RestRequest.RestMethod.GET,
                getPath(communityId, "feeds/record/", feedId + "/feed-elements?recentCommentCount=25&filterGroup=Medium"));
    }

    public RestRequest createChatterLikePostRequest(String communityId, String commentID, boolean value) {
        return new RestRequest(RestRequest.RestMethod.PATCH,
                getPath(communityId, "feed-elements/", commentID + "/capabilities/chatter-likes/items?isLikedByCurrentUser=" + value), RequestBody.create(null, ""));
    }

//    public RestRequest createChatterUnlikePostRequest(String communityId, String likeID) {
//        return new RestRequest(RestRequest.RestMethod.DELETE, getPath(communityId, "likes/", likeID));
//    }

    public RestRequest createGroupPostRequest(String communityId, JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath(communityId, "feed-elements", ""), jsonObject);
    }

    public RestRequest createAddCommentRequest(String communityId, String commentID, JSONObject jsonObject) {
        return new RestRequest(RestRequest.RestMethod.POST,
                getPath(communityId, "feed-elements/", commentID + "/capabilities/comments/items"), jsonObject);
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
