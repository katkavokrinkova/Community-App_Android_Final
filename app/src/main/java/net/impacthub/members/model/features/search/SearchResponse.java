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

package net.impacthub.members.model.features.search;

import net.impacthub.members.model.features.companies.CompaniesRecords;
import net.impacthub.members.model.features.events.EventRecords;
import net.impacthub.members.model.features.groups.GroupsRecords;
import net.impacthub.members.model.features.members.MembersRecords;
import net.impacthub.members.model.features.projects.ProjectRecords;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/6/2017.
 */

public class SearchResponse {

    private ProjectRecords[] Projects;
    private GroupsRecords[] Groups;
    private String searchTerm;
    private EventRecords[] Events;
    private MembersRecords[] Members;
    private CompaniesRecords[] Companies;

    public ProjectRecords[] getProjects() {
        return Projects;
    }

    public GroupsRecords[] getGroups() {
        return Groups;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public EventRecords[] getEvents() {
        return Events;
    }

    public MembersRecords[] getMembers() {
        return Members;
    }

    public CompaniesRecords[] getCompanies() {
        return Companies;
    }

    @Override
    public String toString() {
        return "SearchResponse [Projects = " + Projects + ", Groups = " + Groups + ", searchTerm = " + searchTerm + ", Events = " + Events + ", Members = " + Members + ", Companies = " + Companies + "]";
    }
}
