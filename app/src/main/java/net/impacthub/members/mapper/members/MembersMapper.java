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
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.dto.members.SkillsDTO;
import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.model.features.members.Affiliation;
import net.impacthub.members.model.features.members.Affiliations;
import net.impacthub.members.model.features.members.Member;
import net.impacthub.members.model.features.members.MemberResponse;
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

    public List<MemberDTO> map(MemberResponse responses) {
        List<MemberDTO> memberDTOs = new LinkedList<>();
        if (responses != null) {
            List<Member> members = responses.getMembers();
            if (members != null) {
                for (Member member : members) {
                    if (member != null) {
                        MemberDTO memberDTO = new MemberDTO();
                        memberDTO.mMemberId = member.getId();
                        memberDTO.mProfilePicURL = member.getProfilePic();
                        memberDTO.mFullName = member.getFirstName() + " " + member.getLastName();
                        memberDTO.mLinkInstagram = member.getInstagram();
                        memberDTO.mLinkFacebook = member.getFacebook();
                        memberDTO.mLinkTwitter = member.getTwitter();
                        memberDTO.mLinkLinkedin = member.getLinkedIn();
                        memberDTO.mLocation = member.getImpactHubCities();
                        memberDTO.mAboutMe = member.getAboutMe();
                        memberDTO.mProfession = member.getProfession();
                        memberDTOs.add(memberDTO);
                    }
                }
            }
        }
        return memberDTOs;
    }

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
                            memberProjectDTO.mProjectId = affiliation.getId();
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
                            group.mGroupDescription = affiliation.getGroupDesc();
                            group.mCities = affiliation.getImpactHubCities();
                            group.mMemberCount = affiliation.getCountOfMembers();
                            group.mChatterGroupId = affiliation.getChatterGroupId();
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
