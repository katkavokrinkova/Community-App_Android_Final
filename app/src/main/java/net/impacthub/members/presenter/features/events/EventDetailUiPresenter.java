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

import com.google.gson.Gson;

import net.impacthub.members.mapper.location.LocationMapper;
import net.impacthub.members.model.features.location.LocationResponse;
import net.impacthub.members.model.vo.events.EventRequestBody;
import net.impacthub.members.model.vo.location.LocationVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.features.events.AttendEventUseCase;
import net.impacthub.members.usecase.features.events.UnAttendEventUseCase;
import net.impacthub.members.usecase.features.location.GetLocationUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.OkHttpClient;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class EventDetailUiPresenter extends UiPresenter<EventDetailUiContract> {

    private final OkHttpClient mClient = new OkHttpClient();

    public EventDetailUiPresenter(EventDetailUiContract uiContract) {
        super(uiContract);
    }

    public void getLocationFor(String location) {
        String encodedURL = "";
        try {
            encodedURL = URLEncoder.encode(location, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        subscribeWith(new GetLocationUseCase(mClient, encodedURL).getUseCase(), new DisposableSingleObserver<LocationResponse>() {
            @Override
            public void onSuccess(@NonNull LocationResponse locationResponse) {
                LocationVO locationVO = new LocationMapper().map(locationResponse);
                getUi().onLoadLocation(locationVO);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }

    public void attendEvent(String eventID) {

        getUi().onShowProgressBar(true);

        Single<Object> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<String, MemberVO, SingleSource<?>>(eventID) {
                    @Override
                    protected SingleSource<?> apply(MemberVO response, String subject) throws Exception {
                        return new AttendEventUseCase(new JSONObject(new Gson().toJson(new EventRequestBody(response.mContactId, subject)))).getUseCase();
                    }
                });

        subscribeWith(single, new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                getUi().onSetButtonClickable();
                getUi().onUpdateAttendEventButtonLabel(true);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onSetButtonClickable();
                getUi().onShowProgressBar(false);
            }
        });
    }

    public void unattendEvent(String eventID) {

        getUi().onShowProgressBar(true);

        Single<Object> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<String, MemberVO, SingleSource<?>>(eventID) {
                    @Override
                    protected SingleSource<?> apply(MemberVO response, String subject) throws Exception {
                        return new UnAttendEventUseCase(new JSONObject(new Gson().toJson(new EventRequestBody(response.mContactId, subject)))).getUseCase();
                    }
                });

        subscribeWith(single, new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                getUi().onSetButtonClickable();
                getUi().onUpdateAttendEventButtonLabel(false);
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onSetButtonClickable();
                getUi().onShowProgressBar(false);
            }
        });
    }
}
