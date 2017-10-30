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

package net.impacthub.app.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import net.impacthub.app.R;
import net.impacthub.app.ui.base.BaseActivity;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.utilities.DrawableUtils;
import net.impacthub.app.utilities.KeyboardUtils;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 10/30/2017.
 */

public class PicturePreviewActivity extends BaseActivity {

    public static final String EXTRA_TOOLBAR_TITLE = "net.impacthub.app.ui.gallery.EXTRA_TOOLBAR_TITLE";
    public static final String EXTRA_IMAGE_URL = "net.impacthub.app.ui.gallery.EXTRA_IMAGE_URL";

    @BindView(R.id.imageViewPicturePreview) protected ImageView mPicturePreviewImageview;

    @Override
    protected int getContentView() {
        return R.layout.activity_picture_preview;
    }

    @Override
    protected void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getIntent();
        String toolbarTitle = intent.getStringExtra(EXTRA_TOOLBAR_TITLE);
        String imageURL = intent.getStringExtra(EXTRA_IMAGE_URL);
        if (mToolbar != null) {
            mToolbar.setCustomTitle(toolbarTitle);
            mToolbar.setNavigationIcon(DrawableUtils.tintDrawable(this, R.mipmap.ic_close_white_24dp, R.color.greyish_brown));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KeyboardUtils.hideNativeKeyboard(getApplicationContext(), view);
                    finish();
                }
            });
        }
        ImageLoaderHelper.loadImage(this, imageURL, mPicturePreviewImageview);
    }
}
