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

package net.impacthub.members.model.pojo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/11/2017.
 */

public class ListItem<T> {

    private T mModel;
    private final int mItemType;

    public ListItem(@ItemIdentifier int itemType) {
        mItemType = itemType;
    }

    public void setModel(T model) {
        mModel = model;
    }

    public T getModel() {
        return mModel;
    }

    public int getItemType() {
        return mItemType;
    }

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

    @IntDef({TYPE_ONE, TYPE_TWO, TYPE_THREE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ItemIdentifier {}
}
