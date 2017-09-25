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

package net.impacthub.app.mapper.objectives;

import net.impacthub.app.model.features.objectives.ObjectivesResponse;
import net.impacthub.app.model.features.objectives.Records;
import net.impacthub.app.model.pojo.FilterableString;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.pojo.SimpleItem;
import net.impacthub.app.model.vo.objectives.ObjectiveVO;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class ObjectivesMapper {

    public List<ObjectiveVO> map(ObjectivesResponse response) {
        List<ObjectiveVO> objectiveDTOs = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    ObjectiveVO objectiveDTO = new ObjectiveVO();
                    objectiveDTO.mTitle = record.getGoal__c();
                    objectiveDTO.mSummary = record.getGoal_Summary__c();
                    objectiveDTOs.add(objectiveDTO);
                }
            }
        }
        return objectiveDTOs;
    }

    public List<ListItemType> mapAsListItem(ObjectivesResponse response) {
        List<ListItemType> listItems = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                int length = records.length;
                if (length > 0) {
                    listItems.add(new SimpleItem<>(new FilterableString("GOALS"), 0));
                }
                for (int i = 0; i < length; i++) {
                    Records record = records[i];
                    if (record != null) {
                        ObjectiveVO objectiveDTO = new ObjectiveVO();
                        objectiveDTO.mTitle = record.getGoal__c();
                        objectiveDTO.mSummary = record.getGoal_Summary__c();
                        objectiveDTO.mCount = (i + 1);
                        objectiveDTO.mHideLastTimeLine = ((i + 1) == length);
                        listItems.add(new SimpleItem<>(objectiveDTO, 1));
                    }
                }
            }
        }
        return listItems;
    }
}
