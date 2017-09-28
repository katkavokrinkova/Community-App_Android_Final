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

package net.impacthub.app.mapper.location;

import net.impacthub.app.model.features.location.Geometry;
import net.impacthub.app.model.features.location.Location;
import net.impacthub.app.model.features.location.LocationResponse;
import net.impacthub.app.model.features.location.Results;
import net.impacthub.app.model.vo.location.LocationVO;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/23/2017.
 */

public class LocationMapper {

    public LocationVO map(LocationResponse locationResponse) {
        LocationVO locationVO = new LocationVO();
        if (locationResponse != null) {
            Results[] results = locationResponse.getResults();
            if (results != null && results.length > 0) {
                Results result = results[0];
                if (result != null) {
                    Geometry geometry = result.getGeometry();
                    if (geometry != null) {
                        Location location = geometry.getLocation();
                        if (location != null) {
                            locationVO.mLatitude = location.getLat();
                            locationVO.mLongitude = location.getLng();
                        }
                    }
                }
            }
        }
        return locationVO;
    }
}
