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

package net.impacthub.app.model.vo.contacts;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ContactsWrapper {

    private final List<ContactVO> mApprovedContacts = new LinkedList<>();
    private final List<ContactVO> mDeclinedContacts = new LinkedList<>();
    private final List<ContactVO> mOutstandingContacts = new LinkedList<>();

    public List<ContactVO> getApprovedContacts() {
        return mApprovedContacts;
    }

    public List<ContactVO> getDeclinedContacts() {
        return mDeclinedContacts;
    }

    public List<ContactVO> getOutstandingContacts() {
        return mOutstandingContacts;
    }
}
