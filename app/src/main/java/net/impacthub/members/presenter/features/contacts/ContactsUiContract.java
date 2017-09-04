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

package net.impacthub.members.presenter.features.contacts;

import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.presenter.features.error.ErrorHandlerUiContract;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/22/2017.
 */

public interface ContactsUiContract extends ErrorHandlerUiContract {

    void onLoadApprovedContacts(List<ContactVO> contactVOs);

    void onLoadOutstandingContacts(List<ContactVO> contactVOs);

    void onLoadDeclinedContacts(List<ContactVO> contactVOs);
}
