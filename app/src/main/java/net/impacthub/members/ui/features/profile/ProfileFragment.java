package net.impacthub.members.ui.features.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.salesforce.androidsdk.app.SalesforceSDKManager;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.presenter.features.profile.ProfilePresenter;
import net.impacthub.members.presenter.features.profile.ProfileUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.widgets.CircleImageView;
import net.impacthub.members.ui.widgets.TypefaceTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ProfileFragment extends BaseChildFragment<ProfilePresenter> implements ProfileUiContract {

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
}
