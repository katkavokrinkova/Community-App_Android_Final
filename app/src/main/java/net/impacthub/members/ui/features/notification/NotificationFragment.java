package net.impacthub.members.ui.features.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.vo.notifications.NotificationVO;
import net.impacthub.members.presenter.features.notifcations.NotificationsPresenter;
import net.impacthub.members.presenter.features.notifcations.NotificationsUiContract;
import net.impacthub.members.ui.base.BaseChildFragment;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.notifications);
        mNotificationsList.setHasFixedSize(true);
        mAdapter = new NotificationListAdapter(getLayoutInflater(getArguments()));
        mAdapter.setItemClickListener(new OnListItemClickListener<NotificationVO>() {
            @Override
            public void onItemClick(NotificationVO model) {
                showToast("Opening notification... needs implementation!");
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
}
