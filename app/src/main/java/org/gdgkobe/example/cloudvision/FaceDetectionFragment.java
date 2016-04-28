package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.gdgkobe.example.cloudvision.adapter.LandmarkAdapter;
import org.gdgkobe.example.cloudvision.entity.FaceAnnotation;
import org.gdgkobe.example.cloudvision.entity.Landmark;
import org.gdgkobe.example.cloudvision.entity.Response;
import org.gdgkobe.example.cloudvision.listener.ImageUpdateListener;
import org.gdgkobe.example.cloudvision.listener.RecyclerItemClickListener;
import org.gdgkobe.example.cloudvision.view.RatingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
import icepick.State;

public class FaceDetectionFragment extends Fragment implements RecyclerItemClickListener {

    private static final String PARAM_JSON = "json";
    private static final String TAG = FaceDetectionActivity.class.getSimpleName();

    ImageUpdateListener mListener;

    @State
    String mJson;
    Response mEntity;

    @Bind(R.id.face_selector)
    Spinner mFaceSelectSpinner;
    @Bind(R.id.detection_confidence)
    TextView mDetectionConfidence;
    @Bind(R.id.landmarking_confidence)
    TextView mLandmarkingConfidence;
    @Bind(R.id.angle)
    TextView mAngle;
    @Bind(R.id.joy)
    RatingView mJoy;
    @Bind(R.id.sorrow)
    RatingView mSorrow;
    @Bind(R.id.anger)
    RatingView mAnger;
    @Bind(R.id.surprise)
    RatingView mSurprise;
    @Bind(R.id.under_exposed)
    RatingView mUnderExposed;
    @Bind(R.id.blurred)
    RatingView mBlurred;
    @Bind(R.id.headwear)
    RatingView mHeadWear;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facedetection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ImageUpdateListener) {
            mListener = (ImageUpdateListener) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parse();
        setSpinner();
    }

    /**
     * JsonParse
     * JSON文字列をオブジェクトに変換
     */
    private void parse() {
        try {
            mEntity = Response.fromJson(mJson);
        } catch (Exception e) {
            Log.d(TAG, "おちる");
        }
    }

    /**
     * データのアップデート
     * 外部からコールされる
     *
     * @param newJson 新しいResponseのJson
     */
    public void update(String newJson) {
        mJson = newJson;
        parse();
        setSpinner();
        refreshView();
    }

    /**
     * 感情情報をセット
     * 驚く、楽しんでるなど
     */
    private void setLikeLihood() {
        FaceAnnotation annotation = mEntity.getResponses().get(0).getFaceAnnotations().get(mFaceSelectSpinner.getSelectedItemPosition());
        mJoy.setText(getString(R.string.joy));
        mSorrow.setText(getString(R.string.sorrow));
        mAnger.setText(getString(R.string.anger));
        mSurprise.setText(getString(R.string.surprise));
        mUnderExposed.setText(getString(R.string.underexposed));
        mBlurred.setText(getString(R.string.blurred));
        mHeadWear.setText(getString(R.string.headwear));

        mJoy.setRating(annotation.getRating(annotation.getJoyLikelihood()));
        mSorrow.setRating(annotation.getRating(annotation.getSorrowLikelihood()));
        mAnger.setRating(annotation.getRating(annotation.getAngerLikelihood()));
        mSurprise.setRating(annotation.getRating(annotation.getSurpriseLikelihood()));
        mUnderExposed.setRating(annotation.getRating(annotation.getUnderExposedLikelihood()));
        mBlurred.setRating(annotation.getRating(annotation.getBlurredLikelihood()));
        mHeadWear.setRating(annotation.getRating(annotation.getHeadwearLikelihood()));

        mJoy.setVisible();
        mSorrow.setVisible();
        mAnger.setVisible();
        mSurprise.setVisible();
        mUnderExposed.setVisible();
        mBlurred.setVisible();
        mHeadWear.setVisible();
    }

    /**
     * 解析率をテキストにセット
     */
    private void setText() {
        FaceAnnotation annotation = mEntity.getResponses().get(0).getFaceAnnotations().get(mFaceSelectSpinner.getSelectedItemPosition());
        mDetectionConfidence.setText(getString(R.string.detection_confidence, annotation.getDetectionConfidence() * 100));
        mLandmarkingConfidence.setText(getString(R.string.landmark_confidence, annotation.getLandmarkingConfidence() * 100));
        mAngle.setText(getString(R.string.angle,
                annotation.getRollAngle(),
                annotation.getPanAngle(),
                annotation.getTiltAngle()
        ));

    }

    /**
     * スピナーのセット
     * (Face1,Face2,Face N ...のコンボボックスのセット)
     */
    private void setSpinner() {
        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < mEntity.getResponses().get(0).getFaceAnnotations().size(); i++) {
            adapter.add(i + 1 + " Face");
        }
        // SpinnerにAdapterを設定
        mFaceSelectSpinner.setAdapter(adapter);
        mFaceSelectSpinner.setSelection(0);

        mFaceSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * リサイクラービューのバインド
     * （Landmark情報の一覧）
     */
    private void bindRecycler() {
        if (mRecyclerView.getLayoutManager() == null) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setHasFixedSize(false);
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        if (mRecyclerView.getAdapter() == null) {
            mRecyclerView.setAdapter(new LandmarkAdapter(getActivity(),
                    mEntity.getResponses().get(0).getFaceAnnotations().get(mFaceSelectSpinner.getSelectedItemPosition()).getLandmarks()
                    ,
                    this));
        }
    }

    /**
     * ビューの更新
     */
    private void refreshView() {
        mRecyclerView.setAdapter(null);
        bindRecycler();
        setText();
        setLikeLihood();
        showBoundingPoly();
    }

    /**
     * 顔エリアの描画
     */
    private void showBoundingPoly() {
        if (mListener != null) {
            mListener.setPoly(
                    mEntity.getResponses().get(0).
                            getFaceAnnotations()
                            .get(mFaceSelectSpinner.getSelectedItemPosition()).getBoundingPoly(),
                    mEntity.getResponses().get(0).
                            getFaceAnnotations()
                            .get(mFaceSelectSpinner.getSelectedItemPosition()).getFdBoundingPoly());

        }
    }

    /**
     * インスタンスの生成
     *
     * @param json
     * @return
     */
    public static FaceDetectionFragment newInstance(String json) {
        FaceDetectionFragment fragment = new FaceDetectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_JSON, json);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mJson = getArguments().getString(PARAM_JSON);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onItemClick(View view) {
        if (mListener != null) {
            Landmark landmark = ((LandmarkAdapter) mRecyclerView.getAdapter()).getItem(
                    mRecyclerView.getChildAdapterPosition(view));
            mListener.setPoint(landmark.getPosition().getX(), landmark.getPosition().getY());
        }
    }
}
