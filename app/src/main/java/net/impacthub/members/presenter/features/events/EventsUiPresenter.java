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
import net.impacthub.members.model.features.events.EventsResponse;
import net.impacthub.members.model.vo.events.EventVO;
import net.impacthub.members.model.vo.events.EventsWrapper;
import net.impacthub.members.model.vo.members.MemberVO;
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
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class EventsUiPresenter extends UiPresenter<EventsUiContract> {

    private final UseCaseGenerator<Single<EventsResponse>> mAllEventsUseCase = new AllEventsUseCase();
    private final UseCaseGenerator<Single<MemberVO>> mProfileUseCase = new ProfileUseCase();

    public EventsUiPresenter(EventsUiContract uiContract) {
        super(uiContract);
    }

    public void getEvents() {
        getUi().onShowProgressBar(true);
        Single<EventsWrapper> eventsSingle = Single.zip(
                mAllEventsUseCase.getUseCase(),
                mProfileUseCase.getUseCase()
                        .flatMap(new Function<MemberVO, SingleSource<? extends EventsResponse>>() {
                            @Override
                            public SingleSource<? extends EventsResponse> apply(@NonNull MemberVO memberVO) throws Exception {
                                return new YourEventsUseCase(memberVO.mContactId).getUseCase();
                            }
                        }),
                new BiFunction<EventsResponse, EventsResponse, EventsWrapper>() {
                    @Override
                    public EventsWrapper apply(@NonNull EventsResponse allEventsResponse, @NonNull EventsResponse attendingEventsResponse) throws Exception {
                        return new EventsMapper().mapAll(allEventsResponse, attendingEventsResponse);
                    }
                });

        subscribeWith(eventsSingle, new DisposableSingleObserver<EventsWrapper>() {
            @Override
            public void onSuccess(@NonNull EventsWrapper eventsWrapper) {
                getUi().onLoadAllEvents(eventsWrapper.getAllEvents());
                getUi().onLoadEventsAttending(eventsWrapper.getAttendingEvents());
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });


        Single<List<EventVO>> hostingEventsSingle = mProfileUseCase.getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<? extends EventsResponse>>() {
                    @Override
                    public SingleSource<? extends EventsResponse> apply(@NonNull MemberVO memberVO) throws Exception {
                        return new EventsYouManageUseCase(memberVO.mContactId).getUseCase();
                    }
                })
                .map(new Function<EventsResponse, List<EventVO>>() {
                    @Override
                    public List<EventVO> apply(@NonNull EventsResponse response) throws Exception {
                        return new EventsMapper().map(response, false);
                    }
                });
        subscribeWith(hostingEventsSingle, new DisposableSingleObserver<List<EventVO>>() {
            @Override
            public void onSuccess(@NonNull List<EventVO> eventVOList) {
                getUi().onLoadEventsHosting(eventVOList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
