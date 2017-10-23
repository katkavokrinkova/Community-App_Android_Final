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

package net.impacthub.app.ui.features.home.chatter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.impacthub.app.R;
import net.impacthub.app.presenter.features.chatter.ChatterUiContract;
import net.impacthub.app.presenter.features.chatter.ChatterUiPresenter;
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

public class CreatePostActivity extends ModalActivity<ChatterUiPresenter> implements ChatterUiContract {

    public static final String EXTRA_GROUP_ID = "net.impacthub.members.ui.features.home.members.EXTRA_GROUP_ID";

    @BindView(R.id.text_input_message) protected EditText mInputMessageField;

    private String mGroupId;

//    public static ConnectMemberActivity newInstance(String contactId) {
//
//        Bundle args = new Bundle();
//        args.putString(EXTRA_CONTACT_CONNECT_ID, contactId);
//        ConnectMemberActivity fragment = new ConnectMemberActivity();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected ChatterUiPresenter onCreatePresenter() {
        return new ChatterUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_input_modal;
    }

    @OnClick(R.id.fab)
    protected void onCreatePostClicked() {
        getPresenter().createPost(mInputMessageField.getText().toString(), mGroupId);
//        getPresenter().connectMember(mContactId, mIntroMessageField.getText().toString());
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mInputMessageField.setHint("Please type a message to post.");
        mGroupId = getIntent().getStringExtra(EXTRA_GROUP_ID);
        if (mToolbar != null) {
            mToolbar.setCustomTitle("Create Post");
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
