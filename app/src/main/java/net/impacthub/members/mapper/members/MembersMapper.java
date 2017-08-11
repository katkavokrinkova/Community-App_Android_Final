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

package net.impacthub.members.mapper.members;

import net.impacthub.members.model.dto.groups.GroupDTO;
import net.impacthub.members.model.dto.members.SkillsDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.features.members.Affiliation;
import net.impacthub.members.model.features.members.Affiliations;
import net.impacthub.members.model.features.members.Organisation;
import net.impacthub.members.model.features.members.Skill;
import net.impacthub.members.model.features.members.Skills;
import net.impacthub.members.model.pojo.ListItem;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class MembersMapper {

    public List<ProjectDTO> mapProjects(Affiliations response) {
        List<ProjectDTO> memberProjectDTOs = new LinkedList<>();
        if (response != null) {
            List<Affiliation> affiliations = response.getAffiliations();
            if (affiliations != null) {
                for (int i = 0; i < affiliations.size(); i++) {
                    Affiliation affiliation = affiliations.get(i);
                    if (affiliation != null) {
                        if ("Project".equals(affiliation.getDirectoryStyle())) {
                            ProjectDTO memberProjectDTO = new ProjectDTO();
                            memberProjectDTO.mName = affiliation.getName();
                            Organisation organisation = affiliation.getOrganisation();
                            if (organisation != null) {
                                memberProjectDTO.mOrganizationName = organisation.getName();
                            }
                            memberProjectDTO.mMemberCount = affiliation.getCountOfMembers();
                            memberProjectDTO.mLocation = affiliation.getImpactHubCities();
                            memberProjectDTO.mImageURL = affiliation.getImageUrl();
                            memberProjectDTOs.add(memberProjectDTO);
                        }
                    }
                }
            }
        }
        return memberProjectDTOs;
    }

    public List<GroupDTO> mapGroups(Affiliations response) {
        List<GroupDTO> groupDTOList = new LinkedList<>();
        if (response != null) {
            List<Affiliation> affiliations = response.getAffiliations();
            if (affiliations != null) {
                for (int i = 0; i < affiliations.size(); i++) {
                    Affiliation affiliation = affiliations.get(i);
                    if (affiliation != null) {
                        if ("Group".equals(affiliation.getDirectoryStyle())) {
                            GroupDTO group = new GroupDTO();
                            group.mImageURL = affiliation.getImageUrl();
                            group.mName = affiliation.getName();
                            group.mCities = affiliation.getImpactHubCities();
                            group.mMemberCount = affiliation.getCountOfMembers();
                            groupDTOList.add(group);
                        }
                    }
                }
            }
        }
        return groupDTOList;
    }

    public void appendSkills(List<ListItem<?>> infoList, Skills skills) {
        if (skills != null) {
            List<Skill> skillList = skills.getSkills();
            if (skillList != null) {
                for (int i = 0; i < skillList.size(); i++) {
                    Skill skill = skillList.get(i);
                    if (skill != null) {
                        SkillsDTO skillsDTO = new SkillsDTO();
                        skillsDTO.mTitle = skill.getName();
                        skillsDTO.mDescription = skill.getSkillDescription();

                        ListItem<SkillsDTO> listItem = new ListItem<>(ListItem.TYPE_THREE);
                        listItem.setModel(skillsDTO);
                        infoList.add(listItem);
                    }
                }
            }
        }
    }
}
