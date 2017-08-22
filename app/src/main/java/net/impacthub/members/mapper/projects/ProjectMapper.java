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

package net.impacthub.members.mapper.projects;

import android.support.annotation.NonNull;

import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.model.features.projects.Organisation__r;
import net.impacthub.members.model.features.projects.ProjectResponse;
import net.impacthub.members.model.features.projects.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class ProjectMapper {

    public List<ProjectVO> map(ProjectResponse response) {
        List<ProjectVO> projectDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        ProjectVO projectDTO = mapProjectVO(record);
                        projectDTOs.add(projectDTO);
                    }
                }
            }
        }
        return projectDTOs;
    }

    @NonNull
    private ProjectVO mapProjectVO(Records record) {
        ProjectVO projectDTO = new ProjectVO();
        projectDTO.mProjectId = record.getId();
        projectDTO.mName = record.getName();
        Organisation__r organisation__r = record.getOrganisation__r();
        if (organisation__r != null) {
            projectDTO.mOrganizationName = organisation__r.getName();
        }
        projectDTO.mChatterGroupId = record.getChatterGroupId__c();
        projectDTO.mMemberCount = record.getCountOfMembers__c();
        projectDTO.mLocation = record.getImpact_Hub_Cities__c();
        projectDTO.mImageURL = record.getImageURL__c();
        return projectDTO;
    }

    public List<ListItemType> mapAsListItemType(ProjectResponse projectResponse) {
        List<ListItemType> listItemTypes = new LinkedList<>();
        if (projectResponse != null) {
            Records[] records = projectResponse.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        ProjectVO projectDTO = mapProjectVO(record);
                        listItemTypes.add(new SimpleItem<>(projectDTO, 3));
                    }
                }
            }
        }
        return listItemTypes;
    }
}
