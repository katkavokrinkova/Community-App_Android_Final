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

package net.impacthub.members.mapper.profile;

import net.impacthub.members.model.dto.profile.ProfileDTO;
import net.impacthub.members.model.features.profile.ProfileResponse;
import net.impacthub.members.model.features.profile.Records;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProfileMapper {

    public ProfileDTO map(ProfileResponse profileResponse) {
        ProfileDTO profileDTO = new ProfileDTO();
        if (profileResponse != null) {
            Records[] records = profileResponse.getRecords();
            if (records != null && records.length > 0) {
                Records record = records[0];
                if (record != null) {
                    String firstName = record.getFirstName();
                    String lastName = record.getLastName();
                    String profilePic__c = record.getProfilePic__c();
                    String status_update__c = record.getStatus_Update__c();
                    String cities__c = record.getImpact_Hub_Cities__c();
                    profileDTO.mFirstName = firstName;
                    profileDTO.mLastName = lastName;
                    profileDTO.mAvatar = profilePic__c;
                    profileDTO.mStatusUpdate = status_update__c;
                    profileDTO.mCity = cities__c;
                }
            }
        }
        return profileDTO;
    }
}
