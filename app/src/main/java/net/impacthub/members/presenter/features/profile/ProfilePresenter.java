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

package net.impacthub.members.presenter.features.profile;

import net.impacthub.members.mapper.ProfileMapper;
import net.impacthub.members.model.dto.ProfileDTO;
import net.impacthub.members.model.profile.ProfileResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public class ProfilePresenter extends UiPresenter<ProfileUiContract> {

    private UseCaseGenerator<Single<ProfileResponse>> mProfileUseCase;

    public ProfilePresenter(ProfileUiContract uiContract) {
        super(uiContract);
    }

    public void getProfile(String userId) {
        mProfileUseCase = new ProfileUseCase(userId);
        subscribeWith(mProfileUseCase.getUseCase(),
                new DisposableSingleObserver<ProfileResponse>() {
                    @Override
                    public void onSuccess(@NonNull ProfileResponse profileResponse) {
                        ProfileDTO profileDTO = new ProfileMapper().map(profileResponse);
                        getUi().onLoadProfile(profileDTO);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getUi().onError(e);
                    }
                });

//        return Promise { fullfill, reject in
//            SFRestAPI.sharedInstance().performSOQLQuery("SELECT \(SelectFields.CONTACT) FROM Contact WHERE User__c = '\(userId)'", fail: { (error) in
//                print("error \(error?.localizedDescription as Any)")
//                reject(error ?? MyError.JSONError)
//            }) { (result) in
//                let jsonResult = JSON(result!)
//                debugPrint(jsonResult)
//                if let records = jsonResult["records"].array {
//                    let items = records.flatMap { Member(json: $0) }
//                    if let item = items.first {
//                        fullfill(Me(member: item))
//                    }
//                    else {
//                        reject(MyError.JSONError)
//                    }
//                }
//                else {
//                    reject(MyError.JSONError)
//                }
//            }
//        }
    }
}
