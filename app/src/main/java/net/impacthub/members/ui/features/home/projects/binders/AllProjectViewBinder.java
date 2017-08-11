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

package net.impacthub.members.ui.features.home.projects.binders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import net.impacthub.members.model.dto.projects.ProjectDTO;
import net.impacthub.members.ui.binder.ViewBinder;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/11/2017.
 */

public class AllProjectViewBinder implements ViewBinder<List<ProjectDTO>> {

    @Override
    public View getView(Context context, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return null;
    }

    @Override
    public void bindView(List<ProjectDTO> model) {

    }
}
