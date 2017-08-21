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
import net.impacthub.members.model.vo.events.EventVO;
import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.features.members.Records;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.events.AllEventsUseCase;
import net.impacthub.members.usecase.features.events.EventsYouManageUseCase;
import net.impacthub.members.usecase.features.events.YourEventsUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsUiPresenter extends UiPresenter<EventsUiContract> {

    private final UseCaseGenerator<Single<EventsResponse>> mAllEventsUseCase = new AllEventsUseCase();
    private final UseCaseGenerator<Single<MembersResponse>> mProfileUseCase = new ProfileUseCase();

    public EventsUiPresenter(EventsUiContract uiContract) {
        super(uiContract);
    }

    public void getEvents() {
        subscribeWith(mAllEventsUseCase.getUseCase(), new DisposableSingleObserver<EventsResponse>() {
            @Override
            public void onSuccess(@NonNull EventsResponse response) {
                List<EventVO> eventDTOs = new EventsMapper().map(response);
                getUi().onLoadAllEvents(eventDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });

        Single<EventsResponse> singleEventYouManage = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<EventsResponse>>() {
                    @Override
                    public SingleSource<EventsResponse> apply(@NonNull MembersResponse profileResponse) throws Exception {
                        Records record = profileResponse.getRecords()[0];
                        return new EventsYouManageUseCase(record.getId()).getUseCase();
                    }
                });
        subscribeWith(singleEventYouManage, new DisposableSingleObserver<EventsResponse>() {
            @Override
            public void onSuccess(@NonNull EventsResponse response) {
                List<EventVO> eventDTOs = new EventsMapper().map(response);
                getUi().onLoadEventsYouManage(eventDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });



        Single<EventsResponse> singleYourEvents = mProfileUseCase.getUseCase()
                .flatMap(new Function<MembersResponse, SingleSource<EventsResponse>>() {
                    @Override
                    public SingleSource<EventsResponse> apply(@NonNull MembersResponse profileResponse) throws Exception {
                        Records record = profileResponse.getRecords()[0];
                        return new YourEventsUseCase(record.getId()).getUseCase();
                    }
                });
        subscribeWith(singleYourEvents, new DisposableSingleObserver<EventsResponse>() {
            @Override
            public void onSuccess(@NonNull EventsResponse response) {
                List<EventVO> eventDTOs = new EventsMapper().map(response);
                getUi().onLoadYourEvents(eventDTOs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
