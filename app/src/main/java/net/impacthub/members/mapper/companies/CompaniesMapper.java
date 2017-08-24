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

package net.impacthub.members.mapper.companies;

import net.impacthub.members.model.features.companies.services.ServicesResponse;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.pojo.SimpleItem;
import net.impacthub.members.model.vo.companies.CompanyVO;
import net.impacthub.members.model.features.companies.CompaniesResponse;
import net.impacthub.members.model.features.companies.Records;
import net.impacthub.members.model.vo.companies.ServiceVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class CompaniesMapper {

    public List<CompanyVO> map(CompaniesResponse response) {
        List<CompanyVO> companyDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        CompanyVO company = new CompanyVO();
                        company.mCompanyId = record.getId();
                        company.mCompanyName = record.getName();

                        company.mLinkFacebook = record.getFacebook__c();
                        company.mLinkInstagram = record.getInstagram__c();
                        company.mLinkLinkedin = record.getLinkedIn__c();
                        company.mLinkTwitter = record.getTwitter__c();

                        company.mCompanyDescription = record.getAbout_Us__c();
                        company.mCompanyWebsite = record.getWebsite();
                        company.mCompanySector = record.getSector_Industry__c();
                        company.mCompanyLogo = record.getLogo_Image_Url__c();
                        company.mCompanyBanner = record.getBanner_Image_Url__c();
                        company.mCompanyMemberCount = record.getNumber_of_Employees__c();
                        company.mCompanyLocation = record.getImpact_Hub_Cities__c();
                        companyDTOs.add(company);
                    }
                }
            }
        }
        return companyDTOs;
    }

    public List<ListItemType> mapAsListItemType(ServicesResponse response) {
        List<ListItemType> listItemTypes = new LinkedList<>();
        if (response != null) {
            net.impacthub.members.model.features.companies.services.Records[] records = response.getRecords();
            if (records != null) {
                for (net.impacthub.members.model.features.companies.services.Records record : records) {
                    ServiceVO serviceVO = new ServiceVO();
                    serviceVO.mTitle = record.getName();
                    serviceVO.mDescription = record.getService_Description__c();
                    listItemTypes.add(new SimpleItem<ServiceVO>(serviceVO, 2));
                }
            }
        }
        return listItemTypes;
    }
}
