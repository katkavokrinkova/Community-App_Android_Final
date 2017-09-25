package net.impacthub.app.ui.features.search;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import net.impacthub.app.R;
import net.impacthub.app.model.callback.OnListItemClickListener;
import net.impacthub.app.model.pojo.ListItemType;
import net.impacthub.app.model.vo.companies.CompanyVO;
import net.impacthub.app.model.vo.events.EventVO;
import net.impacthub.app.model.vo.groups.GroupVO;
import net.impacthub.app.model.vo.members.MemberVO;
import net.impacthub.app.model.vo.projects.ProjectVO;
import net.impacthub.app.presenter.features.search.SearchUiContract;
import net.impacthub.app.presenter.features.search.SearchUiPresenter;
import net.impacthub.app.ui.base.BaseChildFragment;
import net.impacthub.app.ui.common.LinearItemsMarginDecorator;
import net.impacthub.app.ui.features.home.companies.CompanyDetailFragment;
import net.impacthub.app.ui.features.home.events.EventDetailFragment;
import net.impacthub.app.ui.features.home.groups.GroupDetailFragment;
import net.impacthub.app.ui.features.home.members.MemberDetailFragment;
import net.impacthub.app.ui.features.home.projects.ProjectDetailFragment;
import net.impacthub.app.ui.widgets.UISearchView;

import java.util.List;

import butterknife.BindView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class SearchFragment extends BaseChildFragment<SearchUiPresenter> implements SearchUiContract, OnListItemClickListener<ListItemType> {

    @BindView(R.id.list_items) protected RecyclerView mSearchResultList;
    @BindView(R.id.search_from_list) protected UISearchView mSearchField;

    private SearchResultListAdapter mAdapter;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
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

        //mSearchResultList.setHasFixedSize(true);
        int offset = getResources().getDimensionPixelOffset(R.dimen.default_content_normal_gap);
        mSearchResultList.addItemDecoration(new LinearItemsMarginDecorator(offset));
        mAdapter = new SearchResultListAdapter(LayoutInflater.from(getContext()));
        mAdapter.setItemClickListener(this);
        mSearchResultList.setAdapter(mAdapter);

        mSearchField.setSearchActionListener(new UISearchView.OnSearchActionListener() {
            @Override
            public void onSearch(String searchValue) {
                search(searchValue);
            }

            @Override
            public void onTextChanged(String query) {
                showToast(query);
            }
        });
    }

    private void search(String searchTerm) {
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
