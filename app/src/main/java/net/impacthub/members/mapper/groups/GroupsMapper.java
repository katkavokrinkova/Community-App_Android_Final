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

import net.impacthub.members.model.vo.groups.GroupVO;
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

    public List<GroupVO> map(GroupsResponse response) {
        List<GroupVO> groups = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        GroupVO group = new GroupVO();
                        group.mImageURL = record.getImageURL__c();
                        group.mName = record.getName();
                        group.mGroupDescription = record.getGroup_Desc__c();
                        group.mCities = record.getImpact_Hub_Cities__c();
                        group.mMemberCount = record.getCountOfMembers__c();
                        group.mChatterGroupId = record.getChatterGroupId__c();
                        groups.add(group);
                    }
                }
            }
        }
        return groups;
    }
}
