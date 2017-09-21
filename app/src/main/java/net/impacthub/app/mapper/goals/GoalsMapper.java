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

package net.impacthub.app.mapper.goals;

import net.impacthub.app.model.vo.goals.GoalVO;
import net.impacthub.app.model.features.goals.GoalsResponse;
import net.impacthub.app.model.features.goals.Records;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/9/2017.
 */

public class GoalsMapper {

    public List<GoalVO> map(GoalsResponse response) {
        List<GoalVO> goals = new LinkedList<>();
        if (response != null) {
            Records[] records = response.getRecords();
            if (records != null) {
                for (Records record : records) {
                    if (record != null) {
                        GoalVO goalDTO = new GoalVO();
                        goalDTO.mImageURL = record.getImageURL__c();
                        goalDTO.mName = record.getName();
                        goalDTO.mSummary = record.getSummary__c();
                        goalDTO.mDescription = record.getDescription__c();
                        goals.add(goalDTO);
                    }
                }
            }
        }
        return goals;
    }
}
