package net.impacthub.members.presenter.features.members;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.contacts.ContactsResponse;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractBigFunction;
import net.impacthub.members.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.members.usecase.features.members.MembersUseCase;
import net.impacthub.members.usecase.features.profile.ProfileUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class MembersPresenter extends UiPresenter<MembersUiContract> {

    public MembersPresenter(MembersUiContract uiContract) {
        super(uiContract);
    }

    public void loadMembers() {
        getUi().onChangeStatus(true);

        Single<List<MemberVO>> listSingle = new ProfileUseCase().getUseCase()
                .flatMap(new Function<MemberVO, SingleSource<List<MemberVO>>>() {
                    @Override
                    public SingleSource<List<MemberVO>> apply(@NonNull MemberVO memberVO) throws Exception {
                        String contactId = memberVO.mContactId;
                        return Single.zip(
                                new MembersUseCase().getUseCase(),
                                new DMGetContactsUseCase(contactId).getUseCase(),
                                new AbstractBigFunction<String, List<MemberVO>, ContactsResponse, List<MemberVO>>(contactId) {
                                    @Override
                                    protected List<MemberVO> apply(List<MemberVO> memberVOs, ContactsResponse response, String subject) {
                                        return new MembersMapper().mapMembersList(memberVOs, response, subject);
                                    }
                                }
                        );
                    }
                });

        subscribeWith(listSingle, new DisposableSingleObserver<List<MemberVO>>() {
            @Override
            public void onSuccess(@NonNull List<MemberVO> memberVOList) {
                getUi().onLoadMembers(memberVOList);
                getUi().onChangeStatus(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onChangeStatus(false);
            }
        });
    }
}
