package net.impacthub.members.presenter.features.conversations;

import net.impacthub.members.mapper.conversations.ConversationMapper;
import net.impacthub.members.model.dto.conversations.ConversationDTO;
import net.impacthub.members.model.features.conversations.ConversationsResponse;
import net.impacthub.members.presenter.base.UiPresenter;
import net.impacthub.members.presenter.rx.AbstractFunction;
import net.impacthub.members.usecase.base.UseCaseGenerator;
import net.impacthub.members.usecase.features.conversations.ConversationsUseCase;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
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

    public void getConversations(String userId) {
        getUi().onChangeStatus(true);
        Single<List<ConversationDTO>> single = mConversationsUseCase.getUseCase()
                .map(new AbstractFunction<String, ConversationsResponse, List<ConversationDTO>>(userId) {
                    @Override
                    protected List<ConversationDTO> apply(ConversationsResponse response, String subject) {
                        return new ConversationMapper().map(response, subject);
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<List<ConversationDTO>>() {
            @Override
            public void onSuccess(@NonNull List<ConversationDTO> conversationDTOs) {
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
