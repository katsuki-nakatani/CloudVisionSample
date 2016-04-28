package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.squareup.moshi.Moshi;

import org.gdgkobe.example.cloudvision.api.VisionApi;
import org.gdgkobe.example.cloudvision.entity.Feature;
import org.gdgkobe.example.cloudvision.entity.Poly;
import org.gdgkobe.example.cloudvision.entity.Request;
import org.gdgkobe.example.cloudvision.entity.Response;
import org.gdgkobe.example.cloudvision.listener.ImageUpdateListener;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class FaceDetectionActivity extends VisionBaseActivity implements ImageUpdateListener {


    //パーツ検出エリアのボーダーカラー
    private static final int LANDMARK_AREA_COLOR = Color.argb(255, 255, 255, 255);
    //顔検出エリアのボーダーカラー
    private static final int POLY_AREA_COLOR = Color.argb(255, 255, 0, 0);
    //パーツ検出エリアのエリアサイズ
    private static final int LANDMARK_AREA_SIZE = 10;

    /**
     * Activity表示用Intentを作成する
     *
     * @param context Context
     * @return intent
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, FaceDetectionActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facedetection);
        ButterKnife.bind(this);
        super.restoreView();
    }


    @OnClick(R.id.upload)
    void callCloudVision() {
        //Imageが選択されていないかどうかのチェック
        if (mBitmap == null) {
            Snackbar.make(mCoordinator, R.string.not_selected_image, Snackbar.LENGTH_LONG).show();
            return;
        }

        //HttpClientの生成
        OkHttpClient client = new OkHttpClient.Builder().
                addInterceptor(new JsonInterceptor()).
                build();
        // RetrofitAdapterの生成
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(new Moshi.Builder().build()))
                .client(client)
                .build();
        // 非同期処理の実行
        Request request = new Request();
        request.addRequest(mBitmap, new Feature(Feature.FeatureType.FACE_DETECTION));
        adapter.create(VisionApi.class).post(getString(R.string.vision_api), request)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body().getResponses().get(0).getFaceAnnotations().size() <= 0) {
                            Snackbar.make(mCoordinator, R.string.face_annotation_error, Snackbar.LENGTH_LONG).show();
                        } else {
                            mOriginalJson = response.message();
                            showFaceDetectionFragment(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
        Snackbar.make(mCoordinator, R.string.vision_api_upload, Snackbar.LENGTH_LONG).show();
    }


    /**
     * 顔認識情報を表示させる画面を表示
     *
     * @param json 対象のJson文字列
     */
    void showFaceDetectionFragment(String json) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (f instanceof FaceDetectionFragment) {
            ((FaceDetectionFragment) f).update(json);
        } else {
            getSupportFragmentManager().
                    beginTransaction()
                    .replace(R.id.fragment, FaceDetectionFragment.newInstance(json))
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void setPoint(float x, float y) {
        Canvas canvas;
        Bitmap newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(newBitmap);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(LANDMARK_AREA_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(x - LANDMARK_AREA_SIZE / 2, y - LANDMARK_AREA_SIZE / 2, x + LANDMARK_AREA_SIZE + 2, y + LANDMARK_AREA_SIZE + 2, paint);
        mImage.setImageBitmap(newBitmap);
        collapseBottomSheet();
    }

    @Override
    public void setPoly(Poly boundingPoly, Poly fbBoundingPoly) {
        Canvas canvas;
        Bitmap newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(newBitmap);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setColor(POLY_AREA_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(boundingPoly.getVertices().get(0).getX(),
                boundingPoly.getVertices().get(0).getY(),
                boundingPoly.getVertices().get(2).getX(),
                boundingPoly.getVertices().get(2).getY()
                , paint);

        canvas.drawRect(fbBoundingPoly.getVertices().get(0).getX(),
                fbBoundingPoly.getVertices().get(0).getY(),
                fbBoundingPoly.getVertices().get(2).getX(),
                fbBoundingPoly.getVertices().get(2).getY()
                , paint);
        mImage.setImageBitmap(newBitmap);
    }

    /**
     * 座標を選択されたときにメインViewが表示されるように
     * ページ下部の情報シートを閉じる
     */
    private void collapseBottomSheet() {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(mBottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


}
