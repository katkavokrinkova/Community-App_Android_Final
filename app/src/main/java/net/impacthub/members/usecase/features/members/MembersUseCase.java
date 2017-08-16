package net.impacthub.members.usecase.features.members;

import net.impacthub.members.model.features.members.MemberResponse;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.concurrent.Callable;

import io.reactivex.Single;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class MembersUseCase extends BaseUseCaseGenerator<Single<MemberResponse>, MemberResponse> {

    @Override
    public Single<MemberResponse> getUseCase() {
        return Single.fromCallable(new MembersCallable());
    }

    private class MembersCallable implements Callable<MemberResponse> {
        @Override
        public MemberResponse call() throws Exception {
            return getApiCall().getResponse(getSoqlRequestFactory().createMemberListRequest(), MemberResponse.class);
        }
    }
}
