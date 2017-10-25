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

package net.impacthub.app.ui.features.home.chatter.binder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnCommentAddedCallback;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.groups.ChatterFeedPresenter;
import net.impacthub.app.presenter.features.groups.ChatterFeedUiContract;
import net.impacthub.app.ui.binder.ViewBinderAdapter;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.home.chatter.ChatterFeedListAdapter;
import net.impacthub.app.ui.features.home.chatter.CreatePostActivity;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/24/2017.
 */

public class ChatterFeedViewBinder extends ViewBinderAdapter<ChatterFeedPresenter> implements ChatterFeedUiContract, OnCommentAddedCallback {

    private final String mChatterGroupId;
    private final OnChatterFeedActionListener mChatterFeedActionListener;
    private ChatterFeedListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public ChatterFeedViewBinder(String chatterFeedId, OnChatterFeedActionListener feedActionListener) {
        mChatterGroupId = chatterFeedId;
        mChatterFeedActionListener = feedActionListener;
    }

    @Override
    protected ChatterFeedPresenter onCreatePresenter() {
        return new ChatterFeedPresenter(this);
    }

    @Override
    public View getView(Context context, int position) {
        onCreate(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.common_list_layout, new LinearLayout(context), false);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new ChatterFeedListAdapter(inflater);
        mAdapter.setItemClickListener(new OnListItemClickListener<ChatterVO>() {
            @Override
            public void onItemClick(int viewId, ChatterVO model, int position) {
                switch (viewId) {
                    case R.id.member_image:
                        getPresenter().getMemberBy(model.mUserId);
                        break;
                    case R.id.comment_bar:
                        if (mChatterFeedActionListener != null) {
                            mChatterFeedActionListener.openComments(model, ChatterFeedViewBinder.this, position);
                        }
                        break;
                    case R.id.like_bar:
                        if (model.mIsLikedByMe) {
                            getPresenter().unlikePost(model.mCommentId, position);
                        } else {
                            getPresenter().likePost(model.mUserId, model.mCommentId, position);
                        }
                        break;
                }
            }
        });
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mRecyclerView.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mRecyclerView.setAdapter(mAdapter);
        getPresenter().loadChatterFeed(mChatterGroupId);
        return mRecyclerView;
    }

    @Override
    public void onLoadChatterFeed(List<ChatterVO> chatterDTOs) {
        mAdapter.setItems(chatterDTOs);
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        if (mChatterFeedActionListener != null) {
            mChatterFeedActionListener.onLoadMember(memberVO);
        }
    }

    @Override
    public void onPostLiked(Integer position) {
        ChatterVO chatterVO = mAdapter.getItem(position);
        chatterVO.mIsLikedByMe = true;
        chatterVO.mLikeCount++;
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void onPostUnLiked(Integer subject) {
        ChatterVO chatterVO = mAdapter.getItem(subject);
        chatterVO.mIsLikedByMe = false;
        chatterVO.mLikeCount--;
        mAdapter.notifyItemChanged(subject);
    }

    @Override
    public void onShowProgressBar(boolean showProgressBar) {
        if (mChatterFeedActionListener != null) {
            mChatterFeedActionListener.onShowProgressBar(showProgressBar);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        showToast(throwable.getMessage());
    }

    @Override
    public void onRefreshCommentItem(int refreshPosition) {
        if (mAdapter != null) {
            mAdapter.notifyItemChanged(refreshPosition);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1234 && resultCode == Activity.RESULT_OK) {
            ChatterVO chatterVO = (ChatterVO) data.getSerializableExtra(CreatePostActivity.EXTRA_POSTED_DATA);
            if (chatterVO != null) {
                mAdapter.appendItem(0, chatterVO);
                mRecyclerView.smoothScrollToPosition(0);
            }
        }
    }

    public interface OnChatterFeedActionListener {

        void onShowProgressBar(boolean showProgressBar);

        void openComments(ChatterVO model, OnCommentAddedCallback callback, int position);

        void onLoadMember(MemberVO memberVO);
    }
}
