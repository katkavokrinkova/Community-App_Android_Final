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

package net.impacthub.members.mapper.jobs;

import net.impacthub.members.model.dto.jobs.JobDTO;
import net.impacthub.members.model.features.jobs.Company;
import net.impacthub.members.model.features.jobs.JobsResponse;
import net.impacthub.members.model.features.jobs.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class JobsMapper {

    public List<JobDTO> map(JobsResponse response) {
        List<JobDTO> jobs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        JobDTO job = new JobDTO();
                        job.mName = record.getName();
                        job.mJobType = record.getJob_Type__c();
                        job.mLocation = record.getLocation__c();
                        Company company = record.getCompany__r();
                        if (company != null) {
                            job.mCompanyName = company.getName();
                            job.mLogoURL = company.getLogo_Image_Url__c();
                        }
                        jobs.add(job);
                    }
                }
            }
        }
        return jobs;
    }
}
