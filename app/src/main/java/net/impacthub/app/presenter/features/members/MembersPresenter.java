package net.impacthub.app.presenter.features.members;

import net.impacthub.app.mapper.members.MembersMapper;
import net.impacthub.app.model.features.contacts.ContactsResponse;
import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.presenter.rx.AbstractBigFunction;
import net.impacthub.app.usecase.features.contacts.DMGetContactsUseCase;
import net.impacthub.app.usecase.features.members.MembersUseCase;
import net.impacthub.app.usecase.features.profile.ProfileUseCase;

import java.util.List;
import java.util.Map;

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
        getUi().onShowProgressBar(true);

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
                getUi().onShowProgressBar(false);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
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
