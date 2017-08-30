package net.impacthub.members.usecase.features.members;

import net.impacthub.members.mapper.members.MembersMapper;
import net.impacthub.members.model.features.members.MembersResponse;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public class MembersUseCase extends BaseUseCaseGenerator<Single<List<MemberVO>>, MembersResponse> {

    @Override
    public Single<List<MemberVO>> getUseCase() {
        return Single.fromCallable(new MembersCallable())
                .map(new Function<MembersResponse, List<MemberVO>>() {
                    @Override
                    public List<MemberVO> apply(@NonNull MembersResponse membersResponse) throws Exception {
                        return new MembersMapper().mapMembers(membersResponse);
                    }
                });
    }

    private class MembersCallable implements Callable<MembersResponse> {
        @Override
        public MembersResponse call() throws Exception {
            return getApiCall().getResponse(getSoqlRequestFactory().createMemberListRequest(), MembersResponse.class);
        }
    }
}