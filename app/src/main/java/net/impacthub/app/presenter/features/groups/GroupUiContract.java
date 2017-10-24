/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.presenter.features.groups;

import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;
import java.util.Map;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/4/2017.
 */

public interface GroupUiContract extends ErrorHandlerUiContract {

    void onLoadAllGroups(List<GroupVO> groupList);

    void onLoadGroupsYouManage(List<GroupVO> groupVOs);

    void onLoadYourGroups(List<GroupVO> groupList);

    void onShowTick(Map<String, List<String>> filters);

    void onHideTick();
}
