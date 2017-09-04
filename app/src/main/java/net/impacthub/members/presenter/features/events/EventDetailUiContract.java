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

package net.impacthub.members.presenter.features.events;

import net.impacthub.members.model.vo.location.LocationVO;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public interface EventDetailUiContract extends ErrorHandlerUiContract {

    void onLoadLocation(LocationVO locationVO);

    void onSetButtonClickable();

    void onUpdateAttendEventButtonLabel(boolean attending);
}
