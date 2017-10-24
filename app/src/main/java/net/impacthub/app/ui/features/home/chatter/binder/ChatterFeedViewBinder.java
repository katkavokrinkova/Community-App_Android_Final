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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.groups.ChatterFeedPresenter;
import net.impacthub.app.presenter.features.groups.ChatterFeedUiContract;
import net.impacthub.app.ui.binder.ViewBinderAdapter;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.home.chatter.ChatterFeedListAdapter;

import java.util.List;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/24/2017.
 */

public class ChatterFeedViewBinder extends ViewBinderAdapter<ChatterFeedPresenter> implements ChatterFeedUiContract {

    private final String mChatterGroupId;
    private final OnChatterFeedActionListener mChatterFeedActionListener;
    private ChatterFeedListAdapter mAdapter;

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
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.common_list_layout, new LinearLayout(context), false);
        recyclerView.setHasFixedSize(true);
        mAdapter = new ChatterFeedListAdapter(inflater);
        mAdapter.setItemClickListener(new OnListItemClickListener<ChatterVO>() {
            @Override
            public void onItemClick(int viewId, ChatterVO model, int position) {
                switch (viewId) {
                    case R.id.member_image:
                        getPresenter().getMemberBy(model.mUserId);
                        break;
                    case R.id.comment_bar:
                        //addChildFragment(ChatterCommentFragment.newInstance(model), "FRAG_CHATTER_COMMENTS");
                        break;
                    case R.id.like_bar:
                        if(model.mIsLikedByMe) {
                            getPresenter().unlikePost(model.mLikeId, position);
                        } else {
                            getPresenter().likePost(model.mUserId, model.mCommentId, position);
                        }
                        break;
                }
            }
        });
        int offset = context.getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        recyclerView.addItemDecoration(new LinearItemsMarginDecorator(offset));
        recyclerView.setAdapter(mAdapter);
        getPresenter().loadChatterFeed(mChatterGroupId);
        return recyclerView;
    }

    public void updateViewPostCreated() {}

    @Override
    public void onLoadChatterFeed(List<ChatterVO> chatterDTOs) {
        mAdapter.setItems(chatterDTOs);
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        showToast("Loading member");
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

    }

    public interface OnChatterFeedActionListener {

        void onShowProgressBar(boolean showProgressBar);
    }
}
