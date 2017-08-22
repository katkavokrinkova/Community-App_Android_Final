package net.impacthub.members.presenter.features.conversations;

import net.impacthub.members.mapper.conversations.ConversationMapper;
import net.impacthub.members.model.features.conversations.ConversationsResponse;
import net.impacthub.members.model.vo.conversations.ConversationVO;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.conversations.ConversationsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class ConversationsPresenter extends UiPresenter<ConversationsUiContract> {

    private final UseCaseGenerator<Single<ConversationsResponse>> mConversationsUseCase = new ConversationsUseCase();

    public ConversationsPresenter(ConversationsUiContract uiContract) {
        super(uiContract);
    }

    public void getConversations() {
        getUi().onChangeStatus(true);
        Single<List<ConversationVO>> single = mConversationsUseCase.getUseCase()
                .map(new Function<ConversationsResponse, List<ConversationVO>>() {
                    @Override
                    public List<ConversationVO> apply(@NonNull ConversationsResponse conversationsResponse) throws Exception {
                        return new ConversationMapper().map(conversationsResponse, getUserAccount().getUserId());
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<List<ConversationVO>>() {
            @Override
            public void onSuccess(@NonNull List<ConversationVO> conversationDTOs) {
                getUi().onLoadConversations(conversationDTOs);
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
