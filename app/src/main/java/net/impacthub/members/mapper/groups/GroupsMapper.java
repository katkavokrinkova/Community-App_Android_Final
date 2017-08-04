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

package net.impacthub.members.mapper.groups;

import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.features.groups.GroupsResponse;
import net.impacthub.members.model.features.groups.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class GroupsMapper {

    public List<GroupDTO> map(GroupsResponse response) {
        List<GroupDTO> groups = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (int i = 0; i < records.length; i++) {
                    Records record = records[i];
                    if (record != null) {
                        GroupDTO group = new GroupDTO();
                        group.mImageURL = record.getImageURL__c();
                        group.mName = record.getName();
                        group.mCities = record.getImpact_Hub_Cities__c();
                        group.mMemberCount = record.getCountOfMembers__c();
                        groups.add(group);
                    }
                }
            }
        }
        return groups;
    }
}
