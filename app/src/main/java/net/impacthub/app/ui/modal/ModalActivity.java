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

package net.impacthub.app.ui.modal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.ui.base.BaseActivity;
import net.impacthub.app.ui.features.home.members.ConnectMemberFragment;
import net.impacthub.app.ui.widgets.TypefaceToolbar;
import net.impacthub.app.utilities.DrawableUtils;
import net.impacthub.app.utilities.KeyboardUtils;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ModalActivity extends BaseActivity {

    public static final String EXTRA_CONTACT_ID = "net.impacthub.members.ui.modal.EXTRA_CONTACT_ID";
    public static final String MODAL_TYPE_CONNECT = "net.impacthub.members.ui.modal.MODAL_TYPE_CONNECT";
    public static final String MODAL_TYPE_COMPOSE = "net.impacthub.members.ui.modal.MODAL_TYPE_COMPOSE";

    @Override
    protected int getContentView() {
        return R.layout.common_container_with_toolbar;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TypefaceToolbar toolbar = (TypefaceToolbar) findViewById(R.id.toolbar);

        toolbar.setCustomTitle("Introduction Message");
        toolbar.setNavigationIcon(DrawableUtils.tintDrawable(this, R.mipmap.ic_close_white_24dp, R.color.greyish_brown));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyboardUtils.hideNativeKeyboard(getApplicationContext(), view);
                finish();
            }
        });

        Intent intent = getIntent();
        String contactId = intent.getStringExtra(EXTRA_CONTACT_ID);
        if (intent.getBooleanExtra(MODAL_TYPE_CONNECT, true)) {

        } else {

        }
        replaceFragment(R.id.common_container, ConnectMemberFragment.newInstance(contactId), "CONNECT_FRAG");
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
