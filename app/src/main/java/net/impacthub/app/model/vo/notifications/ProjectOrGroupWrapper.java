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

package net.impacthub.app.model.vo.notifications;

import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.projects.ProjectVO;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/24/2017.
 */

public class ProjectOrGroupWrapper {

    private final ProjectVO mProjectVO;
    private final GroupVO mGroupVO;

    public ProjectOrGroupWrapper(ProjectVO projectVO, GroupVO groupVO) {
        mProjectVO = projectVO;
        mGroupVO = groupVO;
    }

    public GroupVO getGroupVO() {
        return mGroupVO;
    }

    public ProjectVO getProjectVO() {
        return mProjectVO;
    }
}
