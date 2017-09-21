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

package net.impacthub.app.mapper.events;

import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.events.EventVO;
import net.impacthub.app.model.features.events.EventsResponse;
import net.impacthub.app.model.features.events.Organiser__r;
import net.impacthub.app.model.features.events.EventRecords;
import net.impacthub.app.model.vo.events.EventsWrapper;
import net.impacthub.app.utilities.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsMapper {

    public List<EventVO> map(EventsResponse response, boolean attending) {
        List<EventVO> eventDTOs = new LinkedList<>();
        if (response != null) {
            EventRecords[] records = response.getRecords();
            if (records != null) {
                eventDTOs.addAll(mapEventRecords(attending, records));
            }
        }
        return eventDTOs;
    }

    public List<EventVO> mapEventRecords(boolean attending, EventRecords[] records) {
        List<EventVO> eventDTOs = new LinkedList<>();
        for (EventRecords record : records) {
            if (record != null) {
                eventDTOs.add(mapEvent(record, attending));
            }
        }
        return eventDTOs;
    }

    private EventVO mapEvent(EventRecords record, boolean attending) {
        EventVO eventDTO = new EventVO();
        eventDTO.mId = record.getId();
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
        eventDTO.mAttending = attending;

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
        return eventDTO;
    }

    public EventsWrapper mapAll(EventsResponse allEventsResponse, EventsResponse attendingEventsResponse) {
        EventsWrapper eventsWrapper = new EventsWrapper();
        List<EventVO> eventVOs = map(attendingEventsResponse, true);
        eventsWrapper.getAttendingEvents().addAll(eventVOs);
        Map<String, Boolean> asMap = mapListAsMap(eventVOs);
        List<EventVO> allEvents = new LinkedList<>();
        if (allEventsResponse != null) {
            EventRecords[] records = allEventsResponse.getRecords();
            if (records != null) {
                for (EventRecords record : records) {
                    Boolean eventExist = asMap.get(record.getId());
                    boolean attending = eventExist != null && eventExist;
                    allEvents.add(mapEvent(record, attending));
                }
            }
        }
        eventsWrapper.getAllEvents().addAll(allEvents);
        return eventsWrapper;
    }

    private Map<String, Boolean> mapListAsMap(List<EventVO> eventVOs) {
        Map<String, Boolean> attendingEvents = new ConcurrentHashMap<>();
        for (EventVO eventVO : eventVOs) {
            attendingEvents.put(eventVO.mId, true);
        }
        return attendingEvents;
    }

    public void mapEventsRecordsAsListType(List<ListItemType> searchListItems, EventRecords[] records) {
        if (records != null) {
            for (EventRecords record : records) {
                searchListItems.add(new SimpleItem<>(mapEvent(record, false), 4));
            }
        }
    }
}
