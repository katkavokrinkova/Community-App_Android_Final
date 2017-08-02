package net.impacthub.members.presenter.features.members;

import net.impacthub.members.model.members.Member;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public interface MembersUiContract extends ErrorHandlerUiContract {

    void onLoadMembers(List<Member> response);
}
