package net.impacthub.app.presenter.features.members;

import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/26/2017.
 */

public interface MembersUiContract extends ErrorHandlerUiContract {

    void onLoadMembers(List<MemberVO> memberDTOs);

    void onShowTick(Map<String, List<String>> filters);

    void onHideTick();
}
