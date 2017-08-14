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

import net.impacthub.members.mapper.events.EventsMapper;
import net.impacthub.members.model.dto.events.EventDTO;
import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.events.EventsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsUiPresenter extends UiPresenter<EventsUiContract> {

    private final UseCaseGenerator<Single<EventsResponse>> mEventsUseCase = new EventsUseCase();

    public EventsUiPresenter(EventsUiContract uiContract) {
        super(uiContract);
    }

    public void getEvents() {
        subscribeWith(mEventsUseCase.getUseCase(), new DisposableSingleObserver<EventsResponse>() {
            @Override
            public void onSuccess(@NonNull EventsResponse response) {
                List<EventDTO> eventDTOs = new EventsMapper().map(response);
                getUi().onLoadEvents(eventDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
