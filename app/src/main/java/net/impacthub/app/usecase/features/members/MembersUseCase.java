package net.impacthub.app.usecase.features.members;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.features.members.MembersResponse;
import net.impacthub.app.model.vo.members.AllMembersVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.usecase.base.BaseUseCaseGenerator;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class MembersUseCase extends BaseUseCaseGenerator<Single<AllMembersVO>, MembersResponse> {

    private final int mOffset;

    public MembersUseCase(int offset) {
        mOffset = offset;
    }

    @Override
    public Single<AllMembersVO> getUseCase() {
        return new ProfileUseCase().getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<AllMembersVO>>() {
                    @Override
                    public SingleSource<AllMembersVO> apply(MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(
                                Single.fromCallable(new MembersCallable()),
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                new AbstractBigFunction<String, MembersResponse, ContactsResponse, AllMembersVO>(contactId) {
                                    @Override
                                    protected AllMembersVO apply(MembersResponse membersResponse, ContactsResponse contactsResponse, String subject) {
                                        return new MembersMapper().mapAllMembers(membersResponse, contactsResponse, subject);
                                    }
                                });
                    }
                });
    }

    public class MembersCallable implements Callable<MembersResponse> {
        @Override
        public MembersResponse call() throws Exception {
            return getApiCall().getResponse(getSoqlRequestFactory().createMemberListRequest(mOffset), MembersResponse.class);
        }
    }
}