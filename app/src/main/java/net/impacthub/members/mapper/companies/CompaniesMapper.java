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

import net.impacthub.members.model.dto.companies.CompanyDTO;
import net.impacthub.members.model.features.companies.CompaniesResponse;
import net.impacthub.members.model.features.companies.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class CompaniesMapper {

    public List<CompanyDTO> map(CompaniesResponse response) {
        List<CompanyDTO> companyDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        CompanyDTO company = new CompanyDTO();
                        company.mCompanyId = record.getId();
                        company.mCompanyName = record.getName();
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
}
