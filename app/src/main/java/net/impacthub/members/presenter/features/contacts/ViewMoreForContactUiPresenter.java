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

package net.impacthub.members.presenter.features.contacts;

import com.google.gson.Gson;

import net.impacthub.members.model.vo.contacts.UpdateContactBody;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.features.contacts.UpdateDMRequestStatusUseCase;

import org.json.JSONObject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/25/2017.
 */

public class ViewMoreForContactUiPresenter extends UiPresenter<ViewMoreForContactUiContract> {

    public ViewMoreForContactUiPresenter(ViewMoreForContactUiContract uiContract) {
        super(uiContract);
    }

    public void updateContactRequest(String id, String pushUserId, String status) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(new UpdateContactBody(id, status, pushUserId)));
            subscribeWith(new UpdateDMRequestStatusUseCase(jsonObject).getUseCase(), new DisposableSingleObserver<Object>() {
                @Override
                public void onSuccess(@NonNull Object o) {
                    if ("Success".equals(o)) {
                        getUi().onContactStateChanged();
                    } else {
                        getUi().onError(new Throwable(o.toString()));
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    getUi().onError(e);
                }
            });
        } catch (Exception e) {
        }
    }
}
