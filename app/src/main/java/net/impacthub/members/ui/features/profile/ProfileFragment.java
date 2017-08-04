package net.impacthub.members.ui.features.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.app.SalesforceSDKManager;

import net.impacthub.members.R;
import net.impacthub.members.model.dto.profile.ProfileDTO;
import net.impacthub.members.presenter.features.profile.ProfilePresenter;
import net.impacthub.members.presenter.features.profile.ProfileUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.widgets.CircleImageView;
import net.impacthub.members.ui.common.ImageLoaderHelper;
import net.impacthub.members.ui.widgets.TypefaceTextView;
import net.impacthub.members.utilities.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static net.impacthub.members.application.salesforce.SalesforceModuleDependency.userAccountProvider;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class ProfileFragment extends BaseChildFragment<ProfilePresenter> implements ProfileUiContract {

    private final UserAccount mUserAccount = userAccountProvider();

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
        if (mUserAccount != null) {
            getPresenter().getProfile(mUserAccount.getUserId());
        }
    }

    @Override
    public void onLoadProfile(ProfileDTO profileDTO) {
        mTextFullName.setText(String.format("%s %s", profileDTO.mFirstName, profileDTO.mLastName));
        String statusUpdate = profileDTO.mStatusUpdate;
        if (statusUpdate != null) {
            ViewUtils.visible(mTextStatusUpdate);
            mTextStatusUpdate.setText(statusUpdate);
        } else ViewUtils.gone(mTextStatusUpdate);
        mTextLocation.setText(profileDTO.mCity);
        ImageLoaderHelper.loadImage(getActivity() , profileDTO.mAvatar + "?oauth_token=" + mUserAccount.getAuthToken(), mImageAvatar);
    }
}
