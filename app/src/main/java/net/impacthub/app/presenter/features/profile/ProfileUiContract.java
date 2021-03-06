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

package net.impacthub.app.presenter.features.profile;

import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.error.ErrorHandlerUiContract;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 03/08/2017.
 */

public interface ProfileUiContract extends ErrorHandlerUiContract {

    void onLoadCurrentMemberProfile(MemberVO memberDTO);

    void onStopRefreshing();
}
