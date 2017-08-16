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

package net.impacthub.members.ui.features.home.jobs;

import android.os.Bundle;

import net.impacthub.members.model.dto.jobs.JobDTO;
import net.impacthub.members.ui.base.BaseChildFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/16/2017.
 */

public class JobDetailFragment extends BaseChildFragment {

    public static JobDetailFragment newInstance(JobDTO jobDTO) {

        Bundle args = new Bundle();

        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
