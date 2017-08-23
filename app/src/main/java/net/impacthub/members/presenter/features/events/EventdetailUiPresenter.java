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

import net.impacthub.members.mapper.location.LocationMapper;
import net.impacthub.members.model.features.location.LocationResponse;
import net.impacthub.members.model.vo.location.LocationVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.location.GetLocationUseCase;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.OkHttpClient;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class EventdetailUiPresenter extends UiPresenter<EventdetailUiContract> {

    private final OkHttpClient mClient = new OkHttpClient();

    public EventdetailUiPresenter(EventdetailUiContract uiContract) {
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
}
