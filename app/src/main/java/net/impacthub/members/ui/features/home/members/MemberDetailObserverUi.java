package net.impacthub.members.ui.features.home.members;


class MemberDetailObserverUi {

}
//
//        extends ErrorHandlerObserver<MemberDetail> implements TopOfScrollListener, ViewTreeObserver.OnGlobalLayoutListener, ViewPager.OnPageChangeListener {
//
//    private final AboutPageUi mAboutPageUi = aboutPageUiProvider();
//    private final AffiliationPageUi mMemberProjectsPageUi = memberProjectsPageUiProvider();
//    private final AffiliationPageUi mMemberGroupsPageUi = memberGroupsPageUiProvider();
//    private final ActivityFunctionCaller<PagerAdapterFactory> mPagerAdapterFactoryCaller = pagerAdapterFactoryCaller();
//    private final ObservableGenerator<MemberDetail> mObservableGenerator;
//
//    private View mRootView;
//    private ProgressBar mProgressBar;
//    private MemberDetail mResponse;
//    private View mNavBar;
//    private View mHeader;
//    private boolean mIsTopOfScroll = true;
//    private int mPosition;
//
//    MemberDetailObserverUi(Member member) {
//        mObservableGenerator = memberDetailObservableGeneratorProvider(member);
//        mAboutPageUi.setTopOfScrollListener(this);
//    }
//
//    @Override
//    public View onCreateNewViews(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedState) {
//        super.onCreateNewViews(inflater, container, savedState);
//        mRootView = inflater.inflate(R.layout.member, container, false);
//        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
//        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_bar);
//        return mRootView;
//    }
//
//    @Override
//    public void onGlobalLayout() {
//        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//        mNavBar = mRootView.getRootView().findViewById(R.id.navbar);
//        mHeader = mRootView.findViewById(R.id.header);
//        setHeaderAndFooterVisibility();
//        setViews();
//        refreshDependingOnResponse();
//    }
//
//    @Override
//    public void onClearOldViews() {
//        super.onClearOldViews();
//        resetViews();
//        mNavBar = null;
//        mHeader = null;
//        mRootView = null;
//        mProgressBar = null;
//    }
//
//    @Override
//    protected void onNext(MemberDetail response) {
//        mResponse = response;
//        mAboutPageUi.setItemsFrom(response.getSkills());
//        mMemberProjectsPageUi.setItemsFrom(response.getProjects());
//        mMemberGroupsPageUi.setItemsFrom(response.getGroups());
//        if (mProgressBar != null) {
//            mProgressBar.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    @Override
//    protected void onError(Throwable e) {
//        if (mRootView == null) {
//            mAboutPageUi.setItemsFrom(new ArrayList<CombinedMemberSkill>());
//            mMemberProjectsPageUi.setItemsFrom(new ArrayList<Affiliation>());
//            mMemberGroupsPageUi.setItemsFrom(new ArrayList<Affiliation>());
//        } else {
//            showError(e).withViewId(R.id.error_sub_handler).using(mRootView);
//        }
//        mResponse = null;
//    }
//
//    @Override
//    void refresh() {
//        getPresenter().startFirstResultFor(mObservableGenerator);
//    }
//
//    @Override
//    public void topOfScroll(boolean isTopOfScroll) {
//        mIsTopOfScroll = isTopOfScroll;
//        setHeaderAndFooterVisibility();
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        mPosition = position;
//        setHeaderAndFooterVisibility();
//    }
//
//    private void setViews() {
//        ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
//        injectViewPager(viewPager);
//        ((TabLayout) mRootView.findViewById(R.id.group_bar)).setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(this);
//    }
//
//    private void resetViews() {
//        ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.viewpager);
//        viewPager.setAdapter(null);
//        viewPager.removeOnPageChangeListener(this);
//        mNavBar.setVisibility(View.VISIBLE);
//    }
//
//    private void refreshDependingOnResponse() {
//        if (mResponse == null) {
//            refresh();
//            mProgressBar.setVisibility(View.VISIBLE);
//        } else if (getPresenter().isFetching()) {
//            mProgressBar.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private void injectViewPager(ViewPager viewPager) {
//        viewPager.setAdapter(mPagerAdapterFactoryCaller
//                .getCallerFrom(viewPager)
//                .createWith(mAboutPageUi, mMemberProjectsPageUi, mMemberGroupsPageUi));
//    }
//
//    private void setHeaderAndFooterVisibility() {
//        if (mIsTopOfScroll && mPosition == 0) {
//            mNavBar.setVisibility(View.GONE);
//            mHeader.setVisibility(View.GONE);
//        } else {
//            mNavBar.setVisibility(View.VISIBLE);
//            mHeader.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//        // not used
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        // not used
//
//    }
//}