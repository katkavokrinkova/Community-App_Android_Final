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

package net.impacthub.app.mapper.jobs;

import net.impacthub.app.model.features.jobs.Contact__r;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.model.features.jobs.Company;
import net.impacthub.app.model.features.jobs.JobsResponse;
import net.impacthub.app.model.features.jobs.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobsMapper {

    public List<JobVO> map(JobsResponse response) {
        List<JobVO> jobs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        JobVO job = new JobVO();
                        job.mJobId = record.getId();
                        job.mName = record.getName();
                        job.mCompanyC = record.getCompany__c();
                        job.mJobType = record.getJob_Type__c();
                        job.mLocation = record.getLocation__c();
                        job.mDescription = record.getDescription__c();
                        job.mSalary = record.getSalary__c();

                        Contact__r contact__r = record.getContact__r();
                        if (contact__r != null) {
                            job.mAccountId = contact__r.getAccountId();
                        }

                        Company company = record.getCompany__r();
                        if (company != null) {
                            job.mCompanyName = company.getName();
                            job.mWebsite = company.getWebsite();
                            job.mLogoURL = company.getLogo_Image_Url__c();
                            job.mMemberCount = company.getNumber_of_Employees__c();
                            job.mBannerImageURL = company.getBanner_Image_Url__c();
                        }
                        jobs.add(job);
                    }
                }
            }
        }
        return jobs;
    }
}
