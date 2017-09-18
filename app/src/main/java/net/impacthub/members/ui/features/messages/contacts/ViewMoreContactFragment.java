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

package net.impacthub.members.ui.features.messages.contacts;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.contacts.ContactVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.features.contacts.ViewMoreForContactUiContract;
import net.impacthub.members.presenter.features.contacts.ViewMoreForContactUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.DateTimeAgoHelper;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.widgets.drawables.RoundedDrawable;
import net.impacthub.members.utilities.DrawableUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/25/2017.
 */

public class ViewMoreContactFragment extends BaseChildFragment<ViewMoreForContactUiPresenter> implements ViewMoreForContactUiContract {

    public static final String EXTRA_DM_ID = "net.impacthub.members.ui.features.messages.contacts.EXTRA_DM_ID";
    public static final String EXTRA_DM_INTRO_MESSAGE = "net.impacthub.members.ui.features.messages.contacts.EXTRA_DM_INTRO_MESSAGE";
    public static final String EXTRA_DM_CREATED_DATE = "net.impacthub.members.ui.features.messages.contacts.EXTRA_DM_CREATED_DATE";
    public static final String EXTRA_MEMBER_ID = "net.impacthub.members.ui.features.messages.contacts.EXTRA_MEMBER_ID";
    public static final String EXTRA_MEMBER_FULL_NAME = "net.impacthub.members.ui.features.messages.contacts.EXTRA_MEMBER_FULL_NAME";
    public static final String EXTRA_MEMBER_PROFILE_PIC_URL = "net.impacthub.members.ui.features.messages.contacts.EXTRA_MEMBER_PROFILE_PIC_URL";

    @BindView(R.id.image_contact) protected ImageView mContactImage;
    @BindView(R.id.text_intro_message) protected TextView mIntroTxt;
    @BindView(R.id.date_time) protected TextView mCreatedDate;

    private String mDM_ID;
    private String mDM_IntroMessage;
    private String mDM_CreatedDate;
    private String mMemberId;
    private String mFullName;
    private String mProfilePicURL;

    public static ViewMoreContactFragment newInstance(ContactVO contactVO) {

        Bundle args = new Bundle();

        if (contactVO != null) {
            args.putString(EXTRA_DM_INTRO_MESSAGE, contactVO.mIntroMessage);
            args.putString(EXTRA_DM_CREATED_DATE, contactVO.mCreatedDate);

            MemberVO member = contactVO.mMember;
            if (member != null) {
                args.putString(EXTRA_DM_ID, member.mDM_ID);
                args.putString(EXTRA_MEMBER_ID, member.mUserId);
                args.putString(EXTRA_MEMBER_FULL_NAME, member.mFullName);
                args.putString(EXTRA_MEMBER_PROFILE_PIC_URL, member.mProfilePicURL);
            }
        }
        ViewMoreContactFragment fragment = new ViewMoreContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ViewMoreForContactUiPresenter onCreatePresenter() {
        return new ViewMoreForContactUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_view_more_contact;
    }

    @OnClick(R.id.button_accept_contact)
    void onApproveContact() {
        getPresenter().updateContactRequest(mDM_ID, mMemberId, "Approved");
    }

    @OnClick(R.id.button_decline_contact)
    void onDeclineContact() {
        getPresenter().updateContactRequest(mDM_ID, mMemberId, "Declined");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mDM_ID = arguments.getString(EXTRA_DM_ID);
        mDM_IntroMessage = arguments.getString(EXTRA_DM_INTRO_MESSAGE);
        mDM_CreatedDate = arguments.getString(EXTRA_DM_CREATED_DATE);
        mMemberId = arguments.getString(EXTRA_MEMBER_ID);
        mFullName = arguments.getString(EXTRA_MEMBER_FULL_NAME);
        mProfilePicURL = arguments.getString(EXTRA_MEMBER_PROFILE_PIC_URL);
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(mFullName);

        mIntroTxt.setText(mDM_IntroMessage);
        new DateTimeAgoHelper(mCreatedDate, mDM_CreatedDate);

        String profilePictureURL = buildUrl(mProfilePicURL);
        ImageLoaderHelper.loadImage(getContext(), profilePictureURL, mContactImage);
        Glide.with(getContext().getApplicationContext()).asBitmap().load(profilePictureURL).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                if (mToolbar != null) {
                    RoundedDrawable roundedDrawable = new RoundedDrawable(resource);
                    roundedDrawable.setOval(true);
                    int thumbnailSize = getResources().getDimensionPixelOffset(R.dimen.toolbar_thumbnail_size);
                    Drawable drawable = DrawableUtils.resize(getResources(), roundedDrawable.toBitmap(), thumbnailSize, thumbnailSize);
                    mToolbar.setLogo(drawable);
                }
            }
        });
    }

    @Override
    public void onContactStateChanged() {
        popChildFragment();
    }
}
