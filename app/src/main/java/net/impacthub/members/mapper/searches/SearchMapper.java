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

package net.impacthub.members.mapper.searches;

import net.impacthub.members.mapper.companies.CompaniesMapper;
import net.impacthub.members.mapper.events.EventsMapper;
import net.impacthub.members.mapper.groups.GroupsMapper;
import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.mapper.projects.ProjectMapper;
import net.impacthub.members.model.features.search.SearchResponse;
import net.impacthub.members.model.pojo.ListItemType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/6/2017.
 */

public class SearchMapper {

    public List<ListItemType> map(SearchResponse searchResponse) {
        List<ListItemType> searchListItems = new LinkedList<>();
        if (searchResponse != null) {
            new MembersMapper().mapMembersRecordsAsListType(searchListItems, searchResponse.getMembers());
            new GroupsMapper().mapGroupsRecordsAsListType(searchListItems, searchResponse.getGroups());
            new ProjectMapper().mapProjectsRecordsAsListType(searchListItems, searchResponse.getProjects());
            new CompaniesMapper().mapCompanyRecordsAsListType(searchListItems, searchResponse.getCompanies());
            new EventsMapper().mapEventsRecordsAsListType(searchListItems, searchResponse.getEvents());
        }
        return searchListItems;
    }
}
