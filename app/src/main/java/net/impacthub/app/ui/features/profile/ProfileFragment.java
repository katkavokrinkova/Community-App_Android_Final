package net.impacthub.app.ui.features.profile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.salesforce.androidsdk.app.SalesforceSDKManager;

import net.impacthub.app.R;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.presenter.features.profile.ProfilePresenter;
import net.impacthub.app.presenter.features.profile.ProfileUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.ImageLoaderHelper;
import net.impacthub.app.ui.widgets.CircleImageView;
import net.impacthub.app.ui.widgets.TypefaceTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ProfileFragment extends BaseChildFragment<ProfilePresenter> implements ProfileUiContract {

    @BindView(R.id.swipe_to_refresh_layout) protected SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.image_profile_avatar) protected CircleImageView mImageAvatar;
    @BindView(R.id.text_full_name) protected TypefaceTextView mTextFullName;
    @BindView(R.id.text_status_update) protected TypefaceTextView mTextStatusUpdate;
    @BindView(R.id.locations) protected TypefaceTextView mTextLocation;
    @BindView(R.id.done) protected TypefaceTextView mLogoutButton;

    public static ProfileFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ProfilePresenter onCreatePresenter() {
        return new ProfilePresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;
    }

    @OnClick(R.id.done)
    protected void onLogoutClick() {
        SalesforceSDKManager.getInstance().logout(getActivity());
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.profile);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getProfile();
            }
        });
        mLogoutButton.setText("Logout");
        getPresenter().getProfile();
    }

    @Override
    public void onLoadCurrentMemberProfile(MemberVO memberDTO) {
        mTextFullName.setText(memberDTO.mFullName);
        mTextStatusUpdate.setText(memberDTO.mStatusUpdate);
//        String statusUpdate = profileDTO.mStatusUpdate;
//        if (statusUpdate != null) {
//            ViewUtils.visible(mTextStatusUpdate);
//            mTextStatusUpdate.setText(statusUpdate);
//        } else ViewUtils.gone(mTextStatusUpdate);
        mTextLocation.setText(memberDTO.mLocation);
        ImageLoaderHelper.loadImage(getActivity() , buildUrl(memberDTO.mProfilePicURL), mImageAvatar);
    }

    @Override
    public void onStopRefreshing() {
        mSwipeRefresh.setRefreshing(false);
    }
}
