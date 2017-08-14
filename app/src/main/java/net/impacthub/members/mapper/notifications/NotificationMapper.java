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

package net.impacthub.members.mapper.notifications;

import net.impacthub.members.model.dto.notifications.NotificationDTO;
import net.impacthub.members.model.dto.notifications.NotificationType;
import net.impacthub.members.model.features.notifications.NotificationResponse;
import net.impacthub.members.model.features.notifications.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public class NotificationMapper {

    public List<NotificationDTO> map(NotificationResponse response) {
        List<NotificationDTO> notificationDTOList = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            int length;
            if (records != null && (length = records.length) > 0) {
                for (int i = 0; i < length; i++) {
                    Records record = records[i];
                    if (record != null) {
                        NotificationDTO notificationDTO = new NotificationDTO();
                        notificationDTO.mMessage = record.getMessage__c();
                        notificationDTO.mProfilePicUrl = record.getProfilePicURL__c();
                        notificationDTO.mCreatedDate = record.getCreatedDate();
                        notificationDTO.mNotificationIcon = NotificationType.fromString(record.getType__c()).getIcon();
                        notificationDTOList.add(notificationDTO);
                    }
                }
            }
        }
        return notificationDTOList;
    }
}
