package net.impacthub.app.presenter.features.conversations;

import net.impacthub.app.mapper.messages.MessagesMapper;
import net.impacthub.app.model.features.messages.MessageResponse;
import net.impacthub.app.model.vo.messages.MessageVO;
import net.impacthub.app.presenter.base.UiPresenter;
import net.impacthub.app.usecase.base.UseCaseGenerator;
import net.impacthub.app.usecase.features.messages.MessagesUseCase;

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

public class MessagesUiPresenter extends UiPresenter<MessagesUiContract> {

    private final UseCaseGenerator<Single<MessageResponse>> mMessagesUseCase = new MessagesUseCase();

    public MessagesUiPresenter(MessagesUiContract uiContract) {
        super(uiContract);
    }

    public void getConversations() {
        Single<List<MessageVO>> single = mMessagesUseCase.getUseCase()
                .map(new Function<MessageResponse, List<MessageVO>>() {
                    @Override
                    public List<MessageVO> apply(@NonNull MessageResponse messageResponse) throws Exception {
                        return new MessagesMapper().map(messageResponse, getUserAccount().getUserId());
                    }
                });
        subscribeWith(single, new DisposableSingleObserver<List<MessageVO>>() {
            @Override
            public void onSuccess(@NonNull List<MessageVO> messageVOList) {
                getUi().onLoadMessages(messageVOList);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                getUi().onError(e);
            }
        });
    }
}
