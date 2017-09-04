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

package net.impacthub.members.model.features.events;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/14/2017.
 */

public class Organiser__r {

    private String Name;
    private Attributes attributes;

    public String getName() {
        return Name;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Organiser__r [Name = " + Name + ", attributes = " + attributes + "]";
    }
}
