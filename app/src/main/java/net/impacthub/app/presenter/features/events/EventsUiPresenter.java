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

import net.impacthub.app.mapper.events.EventsMapper;
import net.impacthub.app.model.features.events.EventsResponse;
import net.impacthub.app.model.vo.events.EventVO;
import net.impacthub.app.model.vo.events.EventsWrapper;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.events.AllEventsUseCase;
import net.impacthub.app.usecase.features.events.EventsYouManageUseCase;
import net.impacthub.app.usecase.features.events.YourEventsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.List;
import java.util.Map;

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

    public void handleFilters(FilterData filterData) {
        boolean atLeastOneFilterChecked = false;
        if (filterData != null) {
            Map<String, List<String>> filters = filterData.getFilters();
            if (filters != null) {
                for (Map.Entry<String, List<String>> entry : filters.entrySet()) {
                    List<String> value = entry.getValue();
                    atLeastOneFilterChecked = !value.isEmpty();
                    if(atLeastOneFilterChecked) break;
                }
            }
            if(atLeastOneFilterChecked) {
                getUi().onShowTick(filters);
            } else {
                getUi().onHideTick();
            }
        } else {
            getUi().onHideTick();
        }
    }
}
