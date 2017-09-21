package net.impacthub.app.ui.controllers.profile;

import android.support.v4.app.Fragment;

import net.impacthub.app.ui.base.BaseControllerFragment;
import net.impacthub.app.ui.features.profile.ProfileFragment;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ProfileControllerFragment extends BaseControllerFragment {
    @Override
    protected Fragment createFragment() {
        return ProfileFragment.newInstance();
    }
}
