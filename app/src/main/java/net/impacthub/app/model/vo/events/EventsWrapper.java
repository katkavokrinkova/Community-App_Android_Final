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

package net.impacthub.app.model.vo.events;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class EventsWrapper {

    private final List<EventVO> mAllEvents = new LinkedList<>();
    private final List<EventVO> mAttendingEvents = new LinkedList<>();

    public List<EventVO> getAllEvents() {
        return mAllEvents;
    }

    public List<EventVO> getAttendingEvents() {
        return mAttendingEvents;
    }
}
