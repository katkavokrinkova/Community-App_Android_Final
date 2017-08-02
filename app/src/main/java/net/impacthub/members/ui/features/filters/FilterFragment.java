package net.impacthub.members.ui.features.filters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.impacthub.members.R;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.widgets.TypefaceTextView;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/1/2017.
 */

public class FilterFragment extends BaseChildFragment {

    public static FilterFragment newInstance() {

        Bundle args = new Bundle();

        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_filters;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(mCloseFilterListener);
        TypefaceTextView doneButton = (TypefaceTextView) view.findViewById(R.id.done);
        doneButton.setOnClickListener(mCloseFilterListener);
    }

    private final View.OnClickListener mCloseFilterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };
}
