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

package net.impacthub.app.mapper.companies;

import android.support.annotation.NonNull;

import net.impacthub.app.model.features.companies.services.ServicesResponse;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.model.features.companies.CompaniesResponse;
import net.impacthub.app.model.features.companies.CompaniesRecords;
import net.impacthub.app.model.vo.companies.ServiceVO;

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
            CompaniesRecords[] records = response.getRecords();
            if (records != null) {
                for (CompaniesRecords record : records) {
                    if (record != null) {
                        companyDTOs.add(mapCompanyVO(record));
                    }
                }
            }
        }
        return companyDTOs;
    }


    @NonNull
    private CompanyVO mapCompanyVO(CompaniesRecords record) {
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
        return company;
    }

    public List<ListItemType> mapAsListItemType(ServicesResponse response) {
        List<ListItemType> listItemTypes = new LinkedList<>();
        if (response != null) {
            net.impacthub.app.model.features.companies.services.Records[] records = response.getRecords();
            if (records != null) {
                for (net.impacthub.app.model.features.companies.services.Records record : records) {
                    ServiceVO serviceVO = new ServiceVO();
                    serviceVO.mTitle = record.getName();
                    serviceVO.mDescription = record.getService_Description__c();
                    listItemTypes.add(new SimpleItem<ServiceVO>(serviceVO, 2));
                }
            }
        }
        return listItemTypes;
    }

    public void mapCompanyRecordsAsListType(List<ListItemType> searchListItems, CompaniesRecords[] records) {
        if (records != null) {
            for (CompaniesRecords record : records) {
                searchListItems.add(new SimpleItem<>(mapCompanyVO(record), 3));
            }
        }
    }
}
