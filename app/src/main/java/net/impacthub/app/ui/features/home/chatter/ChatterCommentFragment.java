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

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnCommentAddedCallback;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.chatter.ChatComment;
import net.impacthub.app.model.vo.chatter.ChatterVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.chatter.ChatterCommentsUiContract;
import net.impacthub.app.presenter.features.chatter.ChatterCommentsUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.utilities.KeyboardUtils;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class ChatterCommentFragment extends BaseChildFragment<ChatterCommentsUiPresenter> implements ChatterCommentsUiContract {

    public static final String EXTRA_CHATTER_COMMENT_ITEM = "net.impacthub.members.ui.features.home.chatter.EXTRA_CHATTER_COMMENT_ITEM";

    @BindView(R.id.comment_items) protected RecyclerView mCommentList;
    @BindView(R.id.text_comment_entry) protected EditText mCommentField;

    private ChatterCommentListAdapter mListAdapter;
    private ChatterVO mChatterVO;
    private OnCommentAddedCallback mCommentCallback;
    private int mCommentRefreshPosition;
    private int mScrollPosition;

    public static ChatterCommentFragment newInstance(ChatterVO chatterVO) {
        
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CHATTER_COMMENT_ITEM, chatterVO);
        ChatterCommentFragment fragment = new ChatterCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button_add_comment)
    protected void onAddComment(View view) {
        KeyboardUtils.hideNativeKeyboard(getContext(), view);
        getPresenter().addComment(mChatterVO.mUserId, mChatterVO.mCommentId, mCommentField.getText().toString());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_chatter_comment;
    }

    @Override
    protected ChatterCommentsUiPresenter onCreatePresenter() {
        return new ChatterCommentsUiPresenter(this);
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.label_comments);

        mChatterVO = (ChatterVO) getArguments().getSerializable(EXTRA_CHATTER_COMMENT_ITEM);
        List<ChatComment> comments = new LinkedList<>();
        if (mChatterVO != null) {
            comments.addAll(mChatterVO.mComments.getComments());
        }

        mCommentList.setHasFixedSize(true);
        mCommentList.addItemDecoration(new LinearItemsMarginDecorator(getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap)));
        mListAdapter = new ChatterCommentListAdapter(LayoutInflater.from(getContext()));
        mListAdapter.setItems(comments);
        mListAdapter.setItemClickListener(new OnListItemClickListener<ChatComment>() {
            @Override
            public void onItemClick(int viewId, ChatComment model, int position) {
                getPresenter().getMemberBy(model.mUserId);
            }
        });
        mCommentList.setAdapter(mListAdapter);
        mCommentList.smoothScrollToPosition(mScrollPosition > 0 ? mScrollPosition : comments.size());
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
    }

    @Override
    public void onAppendComment(ChatComment chatComment) {
        mCommentField.setText(null);
        List<ChatComment> comments = mChatterVO.mComments.getComments();
        comments.add(chatComment);
        mChatterVO.mCommentCount++;
        mListAdapter.appendItem(chatComment);
        if (mCommentCallback != null) {
            mCommentCallback.onRefreshCommentItem(mCommentRefreshPosition);
        }
        mCommentList.smoothScrollToPosition(comments.size());
    }

    public void setCommentCallback(OnCommentAddedCallback commentCallback) {
        mCommentCallback = commentCallback;
    }

    public void setCommentRefreshPosition(int commentRefreshPosition) {
        mCommentRefreshPosition = commentRefreshPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        mScrollPosition = scrollPosition;
    }
}
