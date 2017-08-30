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

import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.features.members.Affiliation;
import net.impacthub.members.model.features.members.Affiliations;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Organisation;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.model.features.members.Skill;
import net.impacthub.members.model.features.members.Skills;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.conversations.RecipientVO;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberStatus;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.members.SkillsVO;
import net.impacthub.members.model.vo.projects.ProjectVO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/10/2017.
 */

public class MembersMapper {

    public List<MemberVO> mapMembers(MembersResponse responses) {
        List<MemberVO> memberDTOs = new LinkedList<>();
        if (responses != null) {
            Records[] records = responses.getRecords();
            if (records != null) {
                for (Records member : records) {
                    if (member != null) {
                        MemberVO memberDTO = new MemberVO();
                        mapRecord(memberDTO, member);
                        memberDTOs.add(memberDTO);
                    }
                }
            }
        }
        return memberDTOs;
    }

    public MemberVO map(MembersResponse profileResponse) {
        MemberVO memberDTO = new MemberVO();
        if (profileResponse != null) {
            Records[] records = profileResponse.getRecords();
            if (records != null && records.length > 0) {
                Records record = records[0];
                if (record != null) {
                    mapRecord(memberDTO, record);
                }
            }
        }
        return memberDTO;
    }

    private void mapRecord(MemberVO memberDTO, Records record) {
        memberDTO.mContactId = record.getId();
        memberDTO.mUserId = record.getUser__c();
        memberDTO.mFirstName = record.getFirstName();
        memberDTO.mLastName = record.getLastName();
        memberDTO.mFullName = record.getFirstName() + " " + record.getLastName();
        memberDTO.mProfilePicURL = record.getProfilePic__c();
        memberDTO.mLinkInstagram = record.getInstagram__c();
        memberDTO.mLinkFacebook = record.getFacebook__c();
        memberDTO.mLinkTwitter = record.getTwitter__c();
        memberDTO.mLinkLinkedin = record.getLinked_In__c();
        memberDTO.mLocation = record.getImpact_Hub_Cities__c();
        memberDTO.mAboutMe = record.getAbout_Me__c();
        memberDTO.mStatusUpdate = record.getStatus_Update__c();
        memberDTO.mProfession = record.getProfession__c();
    }

    public List<ProjectVO> mapProjects(Affiliations response) {
        List<ProjectVO> memberProjectDTOs = new LinkedList<>();
        if (response != null) {
            List<Affiliation> affiliations = response.getAffiliations();
            if (affiliations != null) {
                for (int i = 0; i < affiliations.size(); i++) {
                    Affiliation affiliation = affiliations.get(i);
                    if (affiliation != null) {
                        if ("Project".equals(affiliation.getDirectoryStyle())) {
                            ProjectVO memberProjectDTO = new ProjectVO();
                            memberProjectDTO.mProjectId = affiliation.getId();
                            memberProjectDTO.mName = affiliation.getName();
                            memberProjectDTO.mChatterGroupId = affiliation.getChatterGroupId();
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

    public List<GroupVO> mapGroups(Affiliations response) {
        List<GroupVO> groupDTOList = new LinkedList<>();
        if (response != null) {
            List<Affiliation> affiliations = response.getAffiliations();
            if (affiliations != null) {
                for (int i = 0; i < affiliations.size(); i++) {
                    Affiliation affiliation = affiliations.get(i);
                    if (affiliation != null) {
                        if ("Group".equals(affiliation.getDirectoryStyle())) {
                            GroupVO group = new GroupVO();
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

    public List<ListItemType> mapAsListItemType(Skills skills) {
        List<ListItemType> itemTypes = new LinkedList<>();
        if (skills != null) {
            List<Skill> skillList = skills.getSkills();
            if (skillList != null) {
                for (int i = 0; i < skillList.size(); i++) {
                    Skill skill = skillList.get(i);
                    if (skill != null) {
                        SkillsVO skillsDTO = new SkillsVO();
                        skillsDTO.mTitle = skill.getName();
                        skillsDTO.mDescription = skill.getSkillDescription();
                        itemTypes.add(new SimpleItem<SkillsVO>(skillsDTO, 2));
                    }
                }
            }
        }
        return itemTypes;
    }

    public RecipientVO mapRecipient(MembersResponse response) {
        RecipientVO recipientVO = new RecipientVO();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null && records.length > 0) {
                Records record = records[0];
                recipientVO.mDisplayName = record.getFirstName();
                recipientVO.mImageURL = record.getProfilePic__c();
            }
        }
        return recipientVO;
    }

    private Map<String, MemberVO> mapListAsMapWithId(List<MemberVO> memberVOs) {
        Map<String, MemberVO> map = new HashMap<>();
        for (MemberVO memberVO : memberVOs) {
            map.put(memberVO.mContactId, memberVO);
        }
        return map;
    }

    public List<MemberVO> mapMembersList(List<MemberVO> memberVOs, ContactsResponse contactsResponse, String contactId) {
        List<MemberVO> memberVOList = new LinkedList<>();
        if (contactsResponse != null) {
            net.impacthub.members.model.features.contacts.Records[] records = contactsResponse.getRecords();
            if (records != null && records.length > 0) {
                Map<String, MemberVO> memberVOMap = mapListAsMapWithId(memberVOs);
                memberVOMap.remove(contactId);

                for (net.impacthub.members.model.features.contacts.Records record : records) {

                    String contactTo__c = record.getContactTo__c();
                    String contactFrom__c = record.getContactFrom__c();

                    String status = record.getStatus__c();

                    int memberStatus = MemberStatus.NOT_CONTACTED;

                    if ("Approved".equalsIgnoreCase(status)) {
                        memberStatus = MemberStatus.APPROVED;
                    } else if ("Declined".equalsIgnoreCase(status)) {
                        memberStatus = MemberStatus.DECLINED;
                    } else if ("Outstanding".equalsIgnoreCase(status)) {
                        if (contactId.equals(contactTo__c)) {
                            memberStatus = MemberStatus.APPROVE_DECLINE_BY_ME;
                        } else {
                            memberStatus = MemberStatus.OUTSTANDING;
                        }
                    }

                    if(memberVOMap.containsKey(contactTo__c)) {
                        memberVOMap.get(contactTo__c).mMemberStatus = memberStatus;
                    } else if(memberVOMap.containsKey(contactFrom__c)) {
                        memberVOMap.get(contactFrom__c).mMemberStatus = memberStatus;
                    }
                }
                memberVOList.addAll(memberVOMap.values());
            }
        }
        return memberVOList;
    }
}
