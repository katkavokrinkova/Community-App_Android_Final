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

package net.impacthub.app.presenter.features.events;

import net.impacthub.app.model.vo.events.EventVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public interface EventsUiContract extends ErrorHandlerUiContract {

    void onLoadAllEvents(List<EventVO> eventDTOs);

    void onLoadEventsAttending(List<EventVO> eventDTOs);

    void onLoadEventsHosting(List<EventVO> eventDTOs);

    void onShowTick(Map<String, List<String>> filters);

    void onHideTick();
}
