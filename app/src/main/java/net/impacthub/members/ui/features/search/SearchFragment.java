package net.impacthub.members.ui.features.search;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import net.impacthub.members.R;
import net.impacthub.members.model.callback.OnListItemClickListener;
import net.impacthub.members.model.pojo.ListItemType;
import net.impacthub.members.model.vo.companies.CompanyVO;
import net.impacthub.members.model.vo.events.EventVO;
import net.impacthub.members.model.vo.groups.GroupVO;
import net.impacthub.members.model.vo.members.MemberVO;
import net.impacthub.members.model.vo.projects.ProjectVO;
import net.impacthub.members.presenter.features.search.SearchUiContract;
import net.impacthub.members.presenter.features.search.SearchUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.LinearItemsMarginDecorator;
import net.impacthub.members.ui.features.home.companies.CompanyDetailFragment;
import net.impacthub.members.ui.features.home.events.EventDetailFragment;
import net.impacthub.members.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.members.ui.features.home.members.MemberDetailFragment;
import net.impacthub.members.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.members.utilities.KeyboardUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class SearchFragment  extends BaseChildFragment<SearchUiPresenter> implements SearchUiContract, OnListItemClickListener<ListItemType> {

    @BindView(R.id.list_items) protected RecyclerView mSearchResultList;
    @BindView(R.id.text_global_search_term) protected EditText mSearchField;

    private SearchResultListAdapter mAdapter;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.button_search)
    protected void onSearchButtonClicked() {
        search(mSearchField.getText().toString());
    }

    @Override
    protected SearchUiPresenter onCreatePresenter() {
        return new SearchUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_search;
    }

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        setUpToolbar(R.string.search);

        mSearchResultList.setHasFixedSize(true);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mSearchResultList.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mAdapter = new SearchResultListAdapter(LayoutInflater.from(getContext()));
        mAdapter.setItemClickListener(this);
        mSearchResultList.setAdapter(mAdapter);

        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean searchPressed = actionId == EditorInfo.IME_ACTION_SEND;
                if(searchPressed) {
                    search(mSearchField.getText().toString());
                }
                return searchPressed;
            }
        });
    }

    private void search(String searchTerm) {
        KeyboardUtils.hideNativeKeyboard(getActivity(), mSearchField);
        getPresenter().search(searchTerm);
    }

    @Override
    public void onLoadSearchResultList(List<ListItemType> listItemTypes) {
        mAdapter.setItems(listItemTypes);
    }

    @Override
    public void onItemClick(int viewId, ListItemType model) {
        switch (model.getItemType()) {
            case 0:
                addChildFragment(MemberDetailFragment.newInstance((MemberVO) model.getModel()), "FRAG_MEMBER_DETAIL");
                break;
            case 1:
                addChildFragment(GroupDetailFragment.newInstance((GroupVO) model.getModel()), "FRAG_GROUP_DETAIL");
                break;
            case 2:
                addChildFragment(ProjectDetailFragment.newInstance((ProjectVO) model.getModel()), "FRAG_PROJECT_DETAIL");
                break;
            case 3:
                addChildFragment(CompanyDetailFragment.newInstance((CompanyVO) model.getModel()), "FRAG_COMPANY_DETAIL");
                break;
            case 4:
                addChildFragment(EventDetailFragment.newInstance((EventVO) model.getModel()), "FRAG_EVENT_DETAIL");
                break;
        }
    }
}
