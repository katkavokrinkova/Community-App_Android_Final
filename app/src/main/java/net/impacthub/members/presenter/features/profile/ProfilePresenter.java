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

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.features.members.MembersResponse;
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

    private final UseCaseGenerator<Single<MembersResponse>> mProfileUseCase = new ProfileUseCase();

    public ProfilePresenter(ProfileUiContract uiContract) {
        super(uiContract);
    }

    public void getProfile() {
        subscribeWith(mProfileUseCase.getUseCase(),
                new DisposableSingleObserver<MembersResponse>() {
                    @Override
                    public void onSuccess(@NonNull MembersResponse profileResponse) {
                        MemberVO memberDTO = new MembersMapper().map(profileResponse);
                        getUi().onLoadCurrentMemberProfile(memberDTO);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getUi().onError(e);
                    }
                });
    }
}
