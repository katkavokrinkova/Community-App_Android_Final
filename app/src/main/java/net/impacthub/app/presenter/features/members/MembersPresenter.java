package net.impacthub.app.presenter.features.members;

import net.impacthub.app.model.vo.filters.FilterData;
import net.impacthub.app.model.vo.members.AllMembersVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.features.members.MembersUseCase;

import java.util.List;
import java.util.Map;

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
