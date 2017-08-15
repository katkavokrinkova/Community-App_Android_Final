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

package net.impacthub.members.presenter.features.events;

import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public interface EventsUiContract extends ErrorHandlerUiContract {

    void onLoadAllEvents(List<EventDTO> eventDTOs);

    void onLoadEventsYouManage(List<EventDTO> eventDTOs);

    void onLoadYourEvents(List<EventDTO> eventDTOs);
}
