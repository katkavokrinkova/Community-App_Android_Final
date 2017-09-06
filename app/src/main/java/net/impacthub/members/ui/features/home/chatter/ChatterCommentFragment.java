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

package net.impacthub.members.ui.features.home.chatter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.chatter.ChatComment;
import net.impacthub.members.model.vo.chatter.CommentVO;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 9/1/2017.
 */

public class ChatterCommentFragment extends BaseChildFragment {

    public static final String EXTRA_NESTED_COMMENTS = "net.impacthub.members.ui.features.home.chatter.EXTRA_NESTED_COMMENTS";

    @BindView(R.id.comment_items) protected RecyclerView mCommentList;
    @BindView(R.id.text_comment_entry) protected TextView mCommentField;

    public static ChatterCommentFragment newInstance(CommentVO commentVO) {
        
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NESTED_COMMENTS, commentVO);
        ChatterCommentFragment fragment = new ChatterCommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button_add_comment)
    protected void onAddComment() {
        showToast("Adding new comments...");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_chatter_comment;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.label_comments);

        CommentVO commentVO = (CommentVO) getArguments().getSerializable(EXTRA_NESTED_COMMENTS);
        List<ChatComment> comments = new LinkedList<>();
        if (commentVO != null) {
            comments.addAll(commentVO.getComments());
        }

//        mCommentList.setHasFixedSize(true);
        mCommentList.addItemDecoration(new LinearItemsMarginDecorator(getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap)));
        ChatterCommentListAdapter listAdapter = new ChatterCommentListAdapter(LayoutInflater.from(getContext()));
        listAdapter.setItems(comments);
        mCommentList.setAdapter(listAdapter);
    }
}
