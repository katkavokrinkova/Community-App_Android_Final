package net.impacthub.app.presenter.features.members;

import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.members.AllMembersVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.members.GetMemberByKeywordUseCase;
import net.impacthub.app.usecase.features.members.MembersUseCase;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
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

    public void loadMembers(int offset) {
        getUi().onLoadingStateChanged(true);
        getUi().onShowProgressBar(true);

        subscribeWith(new MembersUseCase(offset).getUseCase(), new DisposableSingleObserver<AllMembersVO>() {
            @Override
            public void onSuccess(AllMembersVO allMembersVO) {
                getUi().onLoadMembers(allMembersVO.getMemberVOS(), allMembersVO.isDone());
                getUi().onShowProgressBar(false);
                getUi().onLoadingStateChanged(false);
            }

            @Override
            public void onError(Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
                getUi().onLoadingStateChanged(false);
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
                    if (atLeastOneFilterChecked) break;
                }
            }
            if (atLeastOneFilterChecked) {
                getUi().onShowTick(filters);
            } else {
                getUi().onHideTick();
            }
        } else {
            getUi().onHideTick();
        }
    }

    public void searchMemberWith(String searchValue, int offset) {

        getUi().onLoadingStateChanged(true);
        getUi().onShowProgressBar(true);

        Single<AllMembersVO> single = new GetMemberByKeywordUseCase(searchValue, offset).getUseCase()
                .map(new Function<AllMembersVO, AllMembersVO>() {
                    @Override
                    public AllMembersVO apply(AllMembersVO allMembersVO) throws Exception {
                        List<MemberVO> newMembers = new LinkedList<>();
                        List<MemberVO> members = getUi().getMembers();
                        List<MemberVO> memberVOS = allMembersVO.getMemberVOS();
                        for (MemberVO memberVO : memberVOS) {
                            if (!members.contains(memberVO)) {
                                newMembers.add(memberVO);
                            }
                        }
                        return new AllMembersVO(newMembers, true);
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<AllMembersVO>() {
            @Override
            public void onSuccess(AllMembersVO allMembersVO) {
                getUi().onLoadSearchedMembers(allMembersVO.getMemberVOS());
                getUi().onShowProgressBar(false);
                getUi().onLoadingStateChanged(false);
            }

            @Override
            public void onError(Throwable e) {
                getUi().onError(e);
                getUi().onShowProgressBar(false);
                getUi().onLoadingStateChanged(false);
            }
        });
    }
}
