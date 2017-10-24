package net.impacthub.app.ui.features.notification;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.vo.conversations.ConversationVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.notifications.NotificationVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.notifcations.NotificationsPresenter;
import net.impacthub.app.presenter.features.notifcations.NotificationsUiContract;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.app.ui.features.messages.conversation.ConversationFragment;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class NotificationFragment extends BaseChildFragment<NotificationsPresenter> implements NotificationsUiContract {

    @BindView(R.id.list_items) protected RecyclerView mNotificationsList;

    private NotificationListAdapter mAdapter;

    public static NotificationFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected NotificationsPresenter onCreatePresenter() {
        return new NotificationsPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);

        setUpToolbar(R.string.notifications);
        mNotificationsList.setHasFixedSize(true);
        mAdapter = new NotificationListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(new OnListItemClickListener<NotificationVO>() {
            @Override
            public void onItemClick(int viewId, NotificationVO model) {
                switch (model.mNotificationType) {
                    case TYPE_PRIVATE_MESSAGE:
                        ConversationVO conversationVO = new ConversationVO();
                        conversationVO.mConversationId = model.mConversationId;
                        conversationVO.mRecipientUserId = model.mRecipientUserId;
                        conversationVO.mDisplayName = model.mDisplayName;
                        conversationVO.mImageURL = model.mProfilePicUrl;
                        addChildFragment(ConversationFragment.newInstance(conversationVO), "FRAG_MESSAGE_THREAD");
                        break;
                    case TYPE_DM_REQUEST_APPROVED:
                    case TYPE_DM_REQUEST_SENT:
                        getPresenter().getMemberBy(model.mRecipientUserId);
                        break;
                    case TYPE_COMMENT:
                    case TYPE_LIKE_COMMENT:
                    case TYPE_LIKE_POST:
                    case TYPE_POST_MENTION:
                        getPresenter().getGroupOrProjectBy(model.mChatterGroupId);
                        break;
                    default:
                        showToast("Can't Open notification!");
                }
            }
        });
        mNotificationsList.setAdapter(mAdapter);
    }

    @Override
    protected void onFragmentVisibilityChanged(boolean isVisible) {
        super.onFragmentVisibilityChanged(isVisible);
        if (isVisible) {
            getPresenter().getNotifications();
        }
    }

    @Override
    public void onLoadNotifications(List<NotificationVO> notificationDTOList) {
        mAdapter.setItems(notificationDTOList);
    }

    @Override
    public void onLoadMember(MemberVO memberVO) {
        addChildFragment(MemberDetailFragment.newInstance(memberVO), "FRAG_MEMBER_DETAIL");
    }

    @Override
    public void onLoadGroup(GroupVO groupVO) {
        addChildFragment(GroupDetailFragment.newInstance(groupVO), "FRAG_GROUP_DETAIL");
    }

    @Override
    public void onLoadProject(ProjectVO projectVO) {
        addChildFragment(ProjectDetailFragment.newInstance(projectVO), "FRAG_PROJECT_DETAIL");
    }
}
