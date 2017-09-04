package net.impacthub.members.ui.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 7/24/2017.
 */

public class LinearItemsMarginDecorator extends RecyclerView.ItemDecoration {

    private final int mLeftSpaceSize;
    private final int mRightSpaceSize;
    private final int mTopSpaceSize;
    private final int mBottomSpaceSize;

    public LinearItemsMarginDecorator(int spaceSize) {
        this(spaceSize, spaceSize, spaceSize, spaceSize);
    }

    public LinearItemsMarginDecorator(int leftSpaceSize, int rightSpaceSize, int topSpaceSize, int bottomSpaceSize) {
        mLeftSpaceSize = leftSpaceSize;
        mRightSpaceSize = rightSpaceSize;
        mTopSpaceSize = topSpaceSize;
        mBottomSpaceSize = bottomSpaceSize;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);

        if (position < 1) {
            outRect.top = mTopSpaceSize;
        } else {
            outRect.top = 0;
        }
        outRect.left = mLeftSpaceSize;
        outRect.right = mRightSpaceSize;
        outRect.bottom = mBottomSpaceSize;
    }
}
