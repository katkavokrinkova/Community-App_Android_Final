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

package net.impacthub.members.ui.features.home.members;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import net.impacthub.members.R;
import net.impacthub.members.presenter.features.members.ConnectMemberUiContract;
import net.impacthub.members.presenter.features.members.ConnectMemberUiPresenter;
import net.impacthub.members.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/30/2017.
 */

public class ConnectMemberFragment extends BaseFragment<ConnectMemberUiPresenter> implements ConnectMemberUiContract {

    public static final String EXTRA_CONTACT_CONNECT_ID = "net.impacthub.members.ui.features.home.members.EXTRA_CONTACT_CONNECT_ID";

    @BindView(R.id.text_introduction_message) protected EditText mIntroMessageField;

    public static ConnectMemberFragment newInstance(String contactId) {

        Bundle args = new Bundle();
        args.putString(EXTRA_CONTACT_CONNECT_ID, contactId);
        ConnectMemberFragment fragment = new ConnectMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ConnectMemberUiPresenter onCreatePresenter() {
        return new ConnectMemberUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_input_editor;
    }

    @OnClick(R.id.fab)
    protected void onSendIntroMessageClicked() {
        String contactID = getArguments().getString(EXTRA_CONTACT_CONNECT_ID);
        getPresenter().connectMember(contactID, mIntroMessageField.getText().toString());
    }

    @Override
    public void onDismissModal() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }
}
