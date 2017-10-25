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

package net.impacthub.app.ui.features.home.groups;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnCommentAddedCallback;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.features.home.chatter.ChatterCommentFragment;
import net.impacthub.app.ui.features.home.chatter.binder.ChatterFeedViewBinder;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class GroupDetailFragment extends BaseChildFragment implements ChatterFeedViewBinder.OnChatterFeedActionListener {

    private static final String EXTRA_CHATTER_FEED_ID = "net.impacthub.members.ui.features.home.groups.EXTRA_CHATTER_FEED_ID";
    private static final String EXTRA_GROUP_NAME = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_NAME";
    private static final String EXTRA_GROUP_DESCRIPTION = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_DESCRIPTION";
    private static final String EXTRA_GROUP_IMAGE_URL = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_IMAGE_URL";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.text_title) protected TextView mTitle;
    @BindView(R.id.text_sub_title) protected TextView mSubTitle;
    @BindView(R.id.text_info_title) protected TextView mHeaderTitle;
    @BindView(R.id.chatter_feed_container) protected FrameLayout mChatterFeedContainer;

    private ChatterFeedViewBinder mFeedViewBinder;

    public static GroupDetailFragment newInstance(GroupVO groupDTO) {

        Bundle args = new Bundle();
        args.putString(EXTRA_CHATTER_FEED_ID, groupDTO.mChatterGroupId);
        args.putString(EXTRA_GROUP_NAME, groupDTO.mName);
        args.putString(EXTRA_GROUP_DESCRIPTION, groupDTO.mGroupDescription);
        args.putString(EXTRA_GROUP_IMAGE_URL, groupDTO.mImageURL);
        GroupDetailFragment fragment = new GroupDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_detail_group;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        Bundle arguments = getArguments();
        String chatterFeedId = arguments.getString(EXTRA_CHATTER_FEED_ID);
        String groupName = arguments.getString(EXTRA_GROUP_NAME);
        String groupDescription = arguments.getString(EXTRA_GROUP_DESCRIPTION);
        String groupImageURL = arguments.getString(EXTRA_GROUP_IMAGE_URL);
        setUpToolbar(groupName);

        mHeaderTitle.setText("DISCUSSION");
        mTitle.setText(groupName);
        mSubTitle.setText(groupDescription);
        Context context = getContext();
        ImageLoaderHelper.loadImage(context, buildUrl(groupImageURL), mImageDetail);

        mFeedViewBinder = new ChatterFeedViewBinder(chatterFeedId, this);
        mChatterFeedContainer.addView(mFeedViewBinder.getView(context, -1));
    }

    @Override
    public void onDestroy() {
        if (mFeedViewBinder != null) {
            mFeedViewBinder.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void openComments(ChatterVO model, OnCommentAddedCallback callback, int position) {
        ChatterCommentFragment commentFragment = ChatterCommentFragment.newInstance(model);
        commentFragment.setCommentCallback(callback);
        commentFragment.setCommentRefreshPosition(position);
        addChildFragment(commentFragment, "FRAG_CHATTER_COMMENTS");
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
    }
}
