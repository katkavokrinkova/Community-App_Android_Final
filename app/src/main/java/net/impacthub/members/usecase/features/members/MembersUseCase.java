package net.impacthub.members.usecase.features.members;

import net.impacthub.members.model.members.Member;
import net.impacthub.members.model.members.Members;
import net.impacthub.members.usecase.base.BaseUseCaseGenerator;

import java.util.ArrayList;
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

public class MembersUseCase extends BaseUseCaseGenerator<Single<List<Member>>, Members> {

    @Override
    public Single<List<Member>> getUseCase() {
        return Single.fromCallable(new MembersCallable()).map(new MemberListExtractor());
    }

    private class MembersCallable implements Callable<Members> {
        @Override
        public Members call() throws Exception {
            return getFiltersApiCall().getResponse(getSoqlRequestFactory().createMemberListRequest(), Members.class);
        }
    }
    private class MemberListExtractor implements Function<Members, List<Member>> {

        @Override
        public List<Member> apply(@NonNull Members members) throws Exception {
            if (members == null) {
                return new ArrayList<>();
            }
            return members.getMembers();
        }
    }


//    private class MemberListExtractor implements Func1<Members, List<Member>> {
//        @Override
//        public List<Member> call(Members fragment_members) {
//            if (fragment_members == null) {
//                return new ArrayList<>();
//            }
//            return fragment_members.getMembers();
//        }
//    }
}
