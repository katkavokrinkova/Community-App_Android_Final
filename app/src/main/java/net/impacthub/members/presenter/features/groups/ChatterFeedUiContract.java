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

package net.impacthub.members.presenter.features.groups;

import net.impacthub.members.model.features.chatterfeed.FeedElements;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

public interface ChatterFeedUiContract extends ErrorHandlerUiContract {

    void onLoadChatterfeed(FeedElements feedElements);
}
