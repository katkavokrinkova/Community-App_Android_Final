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

import net.impacthub.members.model.vo.events.EventVO;
import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.model.features.events.Organiser__r;
import net.impacthub.members.model.features.events.Records;
import net.impacthub.members.utilities.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsMapper {

    public List<EventVO> map(EventsResponse response) {
        List<EventVO> eventDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        EventVO eventDTO = new EventVO();
                        eventDTO.mName = record.getName();
                        eventDTO.mImageURL = record.getEvent_Image_URL__c();
                        eventDTO.mDescription = record.getEvent_Description__c();
                        eventDTO.mLocation = record.getEvent_City__c();

                        try {
                            DateFormat dateFormat = new SimpleDateFormat(DateUtils.ISO_8601_FORMAT);
                            Date startDate = dateFormat.parse(record.getEvent_Start_DateTime__c());
                            eventDTO.mDate = new SimpleDateFormat(DateUtils.DAY_MONTH_FORMAT).format(startDate);
                            Date endDate = dateFormat.parse(record.getEvent_End_DateTime__c());

                            String start = new SimpleDateFormat(DateUtils.TIME_FORMAT_12_HOUR).format(startDate);
                            String end = new SimpleDateFormat(DateUtils.TIME_FORMAT_12_HOUR).format(endDate);

                            eventDTO.mTime = start + "-" + end;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Organiser__r organiser__r = record.getOrganiser__r();
                        if (organiser__r != null) {
                            eventDTO.mOrganizerName = organiser__r.getName();
                        }
                        eventDTOs.add(eventDTO);
                    }
                }
            }
        }
        return eventDTOs;
    }
}
