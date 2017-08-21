package net.impacthub.members.presenter.features.members;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.dto.members.MemberDTO;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.members.MembersUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class MembersPresenter extends UiPresenter<MembersUiContract> {

    private final UseCaseGenerator<Single<MembersResponse>> mObservableGenerator = new MembersUseCase();

    public MembersPresenter(MembersUiContract uiContract) {
        super(uiContract);
    }

    public void loadMembers() {
        getUi().onChangeStatus(true);
        subscribeWith(mObservableGenerator.getUseCase(), new DisposableSingleObserver<MembersResponse>() {
            @Override
            public void onSuccess(@NonNull MembersResponse response) {
                List<MemberDTO> memberDTOs = new MembersMapper().mapMembers(response);
                getUi().onLoadMembers(memberDTOs);
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
