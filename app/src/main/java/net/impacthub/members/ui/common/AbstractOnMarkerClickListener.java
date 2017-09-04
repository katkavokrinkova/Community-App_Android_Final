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

package net.impacthub.members.ui.common;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/31/2017.
 */

public abstract class AbstractOnMarkerClickListener<S> implements GoogleMap.OnMarkerClickListener {

    private final S mSubject;

    public AbstractOnMarkerClickListener(S subject) {
        mSubject = subject;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return onMarkerClick(marker, mSubject) ;
    }

    protected abstract boolean onMarkerClick(Marker marker, S subject);
}
