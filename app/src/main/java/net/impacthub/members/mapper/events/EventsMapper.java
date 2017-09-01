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

import java.text.ParseException;
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
                        eventDTO.mCity = record.getEvent_City__c();
                        eventDTO.mCountry = record.getEvent_Country__c();
                        eventDTO.mStreet = record.getEvent_Street__c();
                        eventDTO.mZipCode = record.getEvent_ZipCode__c();
                        eventDTO.mRegisteredLink = record.getEvent_RegisterLink__c();
                        eventDTO.mType = record.getEvent_Type__c();
                        eventDTO.mSubType = record.getEvent_SubType__c();
                        eventDTO.mVisibilityPrice = record.getEvent_Visibility__c();

                        try {
                            Date startDate = DateUtils.getDate(DateUtils.ISO_8601_FORMAT_1, record.getEvent_Start_DateTime__c());
                            Date endDate = DateUtils.getDate(DateUtils.ISO_8601_FORMAT_1, record.getEvent_End_DateTime__c());

                            eventDTO.mDate = DateUtils.getStringFromDate(startDate, DateUtils.DAY_MONTH_YEAR_FORMAT);

                            String startTime = DateUtils.getStringFromDate(startDate, DateUtils.TIME_FORMAT_24_HOUR);
                            eventDTO.mTime = startTime;
                            eventDTO.mTimeFromTo = startTime + " - "+  DateUtils.getStringFromDate(endDate, DateUtils.TIME_FORMAT_24_HOUR) ;
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
