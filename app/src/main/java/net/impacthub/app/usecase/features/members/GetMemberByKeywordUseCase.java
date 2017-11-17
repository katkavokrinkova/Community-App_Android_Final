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

package net.impacthub.app.usecase.features.members;

import com.google.gson.Gson;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.members.MemberSearchResponse;
import net.impacthub.app.model.features.members.MembersSearchBody;
import net.impacthub.app.model.vo.members.AllMembersVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 11/16/2017.
 */

public class GetMemberByKeywordUseCase extends BaseUseCaseGenerator<Single<AllMembersVO>, MemberSearchResponse> {

    private final String mSearchValue;
    private final int mOffset;

    public GetMemberByKeywordUseCase(String searchValue, int offset) {
        mSearchValue = searchValue;
        mOffset = offset;
    }

    @Override
    public Single<AllMembersVO> getUseCase() {
        return Single.fromObservable(new ProfileUseCase().getUseCase().toObservable()
                .switchMap(new Function<MemberVO, ObservableSource<AllMembersVO>>() {
                    @Override
                    public ObservableSource<AllMembersVO> apply(MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(
                                Single.fromCallable(new MemberSearchCallable()),
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                new AbstractBigFunction<String, MemberSearchResponse, ContactsResponse, AllMembersVO>(contactId) {
                                    @Override
                                    protected AllMembersVO apply(MemberSearchResponse searchResponse, ContactsResponse contactsResponse, String subject) {
                                        return new MembersMapper().mapAllMembers(searchResponse, contactsResponse, subject);
                                    }
                                }).toObservable();
                    }
                }));
    }

    private class MemberSearchCallable implements Callable<MemberSearchResponse> {

        @Override
        public MemberSearchResponse call() throws Exception {
            MembersSearchBody searchBody = new MembersSearchBody(mSearchValue, mOffset);
            JSONObject jo = new JSONObject(new Gson().toJson(searchBody));
            return getApiCall().getResponse(getSoqlRequestFactory().createSearchMemberByKeywordRequest(jo), MemberSearchResponse.class);
        }
    }
}
