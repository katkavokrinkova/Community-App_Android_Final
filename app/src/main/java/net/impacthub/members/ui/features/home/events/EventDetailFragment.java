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

package net.impacthub.members.ui.features.home.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.impacthub.members.R;
import net.impacthub.members.model.vo.events.EventVO;
import net.impacthub.members.model.vo.location.LocationVO;
import net.impacthub.members.navigator.Navigator;
import net.impacthub.members.presenter.features.events.EventdetailUiContract;
import net.impacthub.members.presenter.features.events.EventdetailUiPresenter;
import net.impacthub.members.ui.base.BaseChildFragment;
import net.impacthub.members.ui.common.AbstractOnMarkerClickListener;
import net.impacthub.members.ui.common.ImageLoaderHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Filippo Ash
 * @version 1.0
 * @date 8/15/2017.
 */

public class EventDetailFragment extends BaseChildFragment<EventdetailUiPresenter> implements OnMapReadyCallback, EventdetailUiContract {

    private static final String EXTRA_EVENT_NAME = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_NAME";
    private static final String EXTRA_EVENT_ORGANIZATION_NAME = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_ORGANIZATION_NAME";
    private static final String EXTRA_IMAGE_URL = "net.impacthub.members.ui.features.home.events.EXTRA_IMAGE_URL";
    private static final String EXTRA_EVENT_DESCRIPTION = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_DESCRIPTION";
    private static final String EXTRA_EVENT_TYPE = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_TYPE";
    private static final String EXTRA_EVENT_TIME = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_TIME";
    private static final String EXTRA_EVENT_TIME_FROM_TO = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_TIME_FROM_TO";
    private static final String EXTRA_EVENT_SUB_TYPE = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_SUB_TYPE";
    private static final String EXTRA_EVENT_DATE = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_DATE";
    private static final String EXTRA_EVENT_COUNTRY = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_COUNTRY";
    private static final String EXTRA_EVENT_CITY = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_CITY";
    private static final String EXTRA_EVENT_ZIP_CODE = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_ZIP_CODE";
    private static final String EXTRA_EVENT_STREET = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_STREET";
    private static final String EXTRA_EVENT_REGISTERED_LINK = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_REGISTERED_LINK";
    private static final String EXTRA_EVENT_VISIBILITY_PRICE = "net.impacthub.members.ui.features.home.events.EXTRA_EVENT_VISIBILITY_PRICE";

    @BindView(R.id.scrollview_parent) protected NestedScrollView mScroll;
    @BindView(R.id.image_header) protected ImageView mHeaderImage;
    @BindView(R.id.text_title) protected TextView mEventTitle;
    @BindView(R.id.text_info_title) protected TextView mEventDescriptionTitle;
    @BindView(R.id.image_map_overlay) protected ImageView mOverlayImage;

    @BindView(R.id.text_event_locations) protected TextView mEventLocation;
    @BindView(R.id.text_event_name) protected TextView mEventName;
    @BindView(R.id.text_event_date) protected TextView mEventDate;
    @BindView(R.id.text_event_time) protected TextView mEventTime;
    @BindView(R.id.text_event_type) protected TextView mEventeType;
    @BindView(R.id.text_event_price) protected TextView mEventPrice;
    @BindView(R.id.text_event_description) protected TextView mEventDescription;

    @BindView(R.id.done) protected TextView mAttendButton;

    private GoogleMap mGoogleMap;

    public static EventDetailFragment newInstance(EventVO model) {

        Bundle args = new Bundle();
        args.putString(EXTRA_EVENT_NAME, model.mName);
        args.putString(EXTRA_EVENT_ORGANIZATION_NAME, model.mOrganizerName);
        args.putString(EXTRA_IMAGE_URL, model.mImageURL);
        args.putString(EXTRA_EVENT_DESCRIPTION, model.mDescription);
        args.putString(EXTRA_EVENT_TYPE, model.mType);
        args.putString(EXTRA_EVENT_TIME, model.mTime);
        args.putString(EXTRA_EVENT_TIME_FROM_TO, model.mTimeFromTo);
        args.putString(EXTRA_EVENT_SUB_TYPE, model.mSubType);
        args.putString(EXTRA_EVENT_DATE, model.mDate);
        args.putString(EXTRA_EVENT_COUNTRY, model.mCountry);
        args.putString(EXTRA_EVENT_CITY, model.mCity);
        args.putString(EXTRA_EVENT_ZIP_CODE, model.mZipCode);
        args.putString(EXTRA_EVENT_STREET, model.mStreet);
        args.putString(EXTRA_EVENT_REGISTERED_LINK, model.mRegisteredLink);
        args.putString(EXTRA_EVENT_VISIBILITY_PRICE, model.mVisibilityPrice);
        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected EventdetailUiPresenter onCreatePresenter() {
        return new EventdetailUiPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_detail_event;
    }

    @OnClick(R.id.text_event_visit_website)
    protected void onVisitWebsiteClicked() {
        Navigator.startOtherWebActivity(getContext(), getArguments().getString(EXTRA_EVENT_REGISTERED_LINK));
    }

    @OnClick(R.id.done)
    protected void onAttendUnattendClicked() {
        showToast("Attending...Unattenting");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpToolbar(R.string.label_events);
        mAttendButton.setText("Attend Event");

        Bundle arguments = getArguments();

        mEventTitle.setText(arguments.getString(EXTRA_EVENT_NAME));

        mEventDescriptionTitle.setText("DESCRIPTION");
        mEventLocation.setText(arguments.getString(EXTRA_EVENT_CITY));
        mEventName.setText(arguments.getString(EXTRA_EVENT_SUB_TYPE));
        mEventDate.setText(arguments.getString(EXTRA_EVENT_DATE));
        mEventTime.setText(arguments.getString(EXTRA_EVENT_TIME_FROM_TO));
        mEventeType.setText(arguments.getString(EXTRA_EVENT_TYPE));
        mEventPrice.setText(arguments.getString(EXTRA_EVENT_VISIBILITY_PRICE));
        mEventDescription.setText(arguments.getString(EXTRA_EVENT_DESCRIPTION));

        ImageLoaderHelper.loadImage(getActivity().getApplicationContext(), arguments.getString(EXTRA_IMAGE_URL), mHeaderImage);

        mOverlayImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mScroll.requestDisallowInterceptTouchEvent(true);
                        return false;
                    case MotionEvent.ACTION_UP:
                        mScroll.requestDisallowInterceptTouchEvent(false);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        mScroll.requestDisallowInterceptTouchEvent(true);
                        return false;
                    default:
                        return true;
                }
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //googleMap.setMyLocationEnabled(true); NEEDS PERMISSION
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setCompassEnabled(true);

        String zipCode = getArguments().getString(EXTRA_EVENT_ZIP_CODE);
        String city = getArguments().getString(EXTRA_EVENT_CITY);
        String country = getArguments().getString(EXTRA_EVENT_COUNTRY);

        getPresenter().getLocationFor(String.format("%s%s%s", zipCode, city, country));
    }

    @Override
    public void onLoadLocation(LocationVO locationVO) {
        LatLng latLng = new LatLng(locationVO.mLatitude, locationVO.mLongitude);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(13)
                .build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mGoogleMap.addMarker(new MarkerOptions()
                .anchor(0.0f, 1.0f)
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mGoogleMap.setOnMarkerClickListener(new AbstractOnMarkerClickListener<LatLng>(latLng) {
            @Override
            protected boolean onMarkerClick(Marker marker, LatLng subject) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + subject.latitude + "," + subject.longitude));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    showToast("Maps not found on this device!");
                }
                return true;
            }
        });
    }
}
