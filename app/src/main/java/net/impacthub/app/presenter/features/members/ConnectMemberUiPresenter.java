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

package net.impacthub.app.presenter.features.members;

import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.Gson;

import net.impacthub.app.model.vo.contacts.ConnectMemberBody;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractFunction;
import net.impacthub.app.usecase.features.contacts.DMCreateRequestUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import org.json.JSONObject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ConnectMemberUiPresenter extends UiPresenter<ConnectMemberUiContract> {

    public ConnectMemberUiPresenter(ConnectMemberUiContract uiContract) {
        super(uiContract);
    }

    public void connectMember(String contactID, String message) {
        getUi().onShowProgressBar(true);
        if (TextUtils.isEmpty(message)) {
            getUi().onError(new Throwable("Intro message should not be empty."));
            getUi().onShowProgressBar(false);
            return;
        }

        Single<Object> single = new ProfileUseCase().getUseCase()
                .flatMap(new AbstractFunction<Pair<String, String>, MemberVO, SingleSource<?>>(new Pair<>(contactID, message)) {
                    @Override
                    protected SingleSource<?> apply(MemberVO response, Pair<String, String> subject) throws Exception {
                        String contactId = response.mContactId;
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(new ConnectMemberBody(contactId, subject.first, subject.second)));
                        return new DMCreateRequestUseCase(jsonObject).getUseCase();
                    }
                });

        subscribeWith(single, new DisposableSingleObserver<Object>() {
            @Override
            public void onSuccess(@NonNull Object o) {
                getUi().onShowProgressBar(false);
                getUi().onDismissModal();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
            }
        });
    }
}
