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

package net.impacthub.app.mapper.groups;

import net.impacthub.app.mapper.chatter.ChatterMapper;
import net.impacthub.app.model.features.groups.GroupsResponse;
import net.impacthub.app.model.features.groups.GroupsRecords;
import net.impacthub.app.model.features.groups.chatter.ChatterResponse;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.groups.GroupVO;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupsMapper {

    public List<GroupVO> mapGroups(GroupsResponse response) {
        List<GroupVO> groups = new LinkedList<>();
        if (response != null) {
            GroupsRecords[] records = response.getRecords();
            groups.addAll(mapGroupsRecords(records));
        }
        return groups;
    }

    private GroupVO mapGroup(GroupsRecords record) {
        GroupVO group = new GroupVO();
        group.mImageURL = record.getImageURL__c();
        group.mName = record.getName();
        group.mGroupDescription = record.getGroup_Desc__c();
        group.mCities = record.getImpact_Hub_Cities__c();
        group.mMemberCount = record.getCountOfMembers__c();
        group.mChatterGroupId = record.getChatterGroupId__c();
        return group;
    }

    public List<GroupVO> mapFiltered(ChatterResponse myChatterGroupResponse, GroupsResponse allGroupResponse) {
        List<GroupVO> groups = new LinkedList<>();
        if (allGroupResponse != null) {
            GroupsRecords[] records = allGroupResponse.getRecords();
            if (records != null) {
                Set<String> chatterIds = new ChatterMapper().mapChatterIdForGroups(myChatterGroupResponse);
                for (GroupsRecords record : records) {
                    if("Public".equalsIgnoreCase(record.getChatterGroupType__c()) || chatterIds.contains(record.getChatterGroupId__c())) {
                        groups.add(mapGroup(record));
                    }
                }
            }
        }
        return groups;
    }

    public List<GroupVO> mapGroupsRecords(GroupsRecords[] records) {
        List<GroupVO> groups = new LinkedList<>();
        if (records != null) {
            for (GroupsRecords record : records) {
                if (record != null) {
                    groups.add(mapGroup(record));
                }
            }
        }
        return groups;
    }

    public void mapGroupsRecordsAsListType(List<ListItemType> searchListItems, GroupsRecords[] records) {
        if (records != null) {
            for (GroupsRecords record : records) {
                if (record != null) {
                    searchListItems.add(new SimpleItem<>(mapGroup(record), 1));
                }
            }
        }
    }
}
