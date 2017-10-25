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

package net.impacthub.app.ui.features.home.members;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.impacthub.app.R;
import net.impacthub.app.presenter.features.members.ConnectMemberUiContract;
import net.impacthub.app.presenter.features.members.ConnectMemberUiPresenter;
import net.impacthub.app.ui.modal.ModalActivity;
import net.impacthub.app.utilities.DrawableUtils;
import net.impacthub.app.utilities.KeyboardUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ConnectMemberActivity extends ModalActivity<ConnectMemberUiPresenter> implements ConnectMemberUiContract {

    public static final String EXTRA_CONTACT_CONNECT_ID = "net.impacthub.members.ui.features.home.members.EXTRA_CONTACT_CONNECT_ID";

    @BindView(R.id.text_input_message) protected EditText mIntroMessageField;

    private String mContactId;

    @Override
    protected ConnectMemberUiPresenter onCreatePresenter() {
        return new ConnectMemberUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_input_modal;
    }

    @OnClick(R.id.fab)
    protected void onSendIntroMessageClicked() {
        getPresenter().connectMember(mContactId, mIntroMessageField.getText().toString());
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIntroMessageField.setHint("Please type an introduction message here.");
        mContactId = getIntent().getStringExtra(EXTRA_CONTACT_CONNECT_ID);
        if (mToolbar != null) {
            mToolbar.setCustomTitle("Introduction Message");
            mToolbar.setNavigationIcon(DrawableUtils.tintDrawable(this, R.mipmap.ic_close_white_24dp, R.color.greyish_brown));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KeyboardUtils.hideNativeKeyboard(getApplicationContext(), view);
                    finish();
                }
            });
        }
    }

    @Override
    public void onDismissModal() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onShowProgressBar(boolean showProgressBar) {
        if (showProgressBar) {
            showDialog("Connecting Member...");
        } else dismissDialog();
    }
}
