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

package net.impacthub.app.presenter.features.projects;

import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.vo.jobs.JobVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public interface ProjectDetailUiContract extends ErrorHandlerUiContract {

    void onLoadMembers(List<MemberVO> memberDTOs);

    void onLoadJobs(List<JobVO> jobDTOs);

    void onLoadObjectives(List<ListItemType> listItemTypes);
}
