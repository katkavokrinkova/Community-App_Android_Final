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

package net.impacthub.members.ui.features.home.groups;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.chatter.ChatterVO;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.presenter.features.groups.ChatterFeedPresenter;
import net.impacthub.members.presenter.features.groups.ChatterFeedUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;
import net.impacthub.members.ui.features.home.chatter.ChatterFeedListAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/17/2017.
 */

public class GroupDetailFragment extends BaseChildFragment<ChatterFeedPresenter> implements ChatterFeedUiContract {

    private static final String EXTRA_CHATTER_FEED_ID = "net.impacthub.members.ui.features.home.groups.EXTRA_CHATTER_FEED_ID";
    private static final String EXTRA_GROUP_NAME = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_NAME";
    private static final String EXTRA_GROUP_DESCRIPTION = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_DESCRIPTION";
    private static final String EXTRA_GROUP_IMAGE_URL = "net.impacthub.members.ui.features.home.groups.EXTRA_GROUP_IMAGE_URL";

    @BindView(R.id.image_detail) protected ImageView mImageDetail;
    @BindView(R.id.text_title) protected TextView mTitle;
    @BindView(R.id.text_sub_title) protected TextView mSubTitle;
    @BindView(R.id.text_info_title) protected TextView mHeaderTitle;
    @BindView(R.id.list_items) protected RecyclerView mChatterList;

    private ChatterFeedListAdapter mAdapter;

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
    protected ChatterFeedPresenter onCreatePresenter() {
        return new ChatterFeedPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_detail_group;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        String chatterFeedId = arguments.getString(EXTRA_CHATTER_FEED_ID);
        String groupName = arguments.getString(EXTRA_GROUP_NAME);
        String groupDescription = arguments.getString(EXTRA_GROUP_DESCRIPTION);
        String groupImageURL = arguments.getString(EXTRA_GROUP_IMAGE_URL);
        setUpToolbar(groupName);

        mHeaderTitle.setText("DISCUSSION");
        mTitle.setText(groupName);
        mSubTitle.setText(groupDescription);
        ImageLoaderHelper.loadImage(getContext(), buildUrl(groupImageURL), mImageDetail);

        mChatterList.setHasFixedSize(true);
        mAdapter = new ChatterFeedListAdapter(getLayoutInflater(getArguments()));
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mChatterList.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mChatterList.setAdapter(mAdapter);

        getPresenter().loadChatterFeed(chatterFeedId);
    }

    @Override
    public void onLoadChatterFeed(List<ChatterVO> chatterDTOs) {
        mAdapter.setItems(chatterDTOs);
    }
}
