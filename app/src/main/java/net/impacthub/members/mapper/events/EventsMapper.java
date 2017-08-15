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

package net.impacthub.members.mapper.events;

import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.model.features.events.Organiser__r;
import net.impacthub.members.model.features.events.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsMapper {

    public List<EventDTO> map(EventsResponse response) {
        List<EventDTO> eventDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        EventDTO eventDTO = new EventDTO();
                        eventDTO.mImageURL = record.getEvent_Image_URL__c();
                        eventDTO.mImageURL = record.getEvent_Image_URL__c();
                        eventDTO.mImageURL = record.getEvent_Image_URL__c();
                        eventDTO.mImageURL = record.getEvent_Image_URL__c();
                        Organiser__r organiser__r = record.getOrganiser__r();
                        if (organiser__r != null) {

                        }
                        eventDTOs.add(eventDTO);
                    }
                }
            }
        }
        return eventDTOs;
    }
}
