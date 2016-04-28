package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.moshi.Moshi;

import org.gdgkobe.example.cloudvision.api.VisionApi;
import org.gdgkobe.example.cloudvision.entity.Feature;
import org.gdgkobe.example.cloudvision.entity.Request;
import org.gdgkobe.example.cloudvision.entity.Response;
import org.gdgkobe.example.cloudvision.entity.SafeSearchAnnotation;
import org.gdgkobe.example.cloudvision.view.RatingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class SafeSearchDetectionActivity extends VisionBaseActivity {

    private static final String TAG = SafeSearchDetectionActivity.class.getSimpleName();

    @Bind(R.id.adult)
    RatingView mAdult;
    @Bind(R.id.spoof)
    RatingView mSpoof;
    @Bind(R.id.medical)
    RatingView mMedical;
    @Bind(R.id.violence)
    RatingView mViolence;
    private Response mResponse;

    /**
     * Activity表示用Intentを作成する
     *
     * @param context Context
     * @return intent
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, SafeSearchDetectionActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_searchdetection);
        ButterKnife.bind(this);
        super.restoreView();
    }

    /**
     * JsonをParse
     */
    void parse() {
        try {
            if (!TextUtils.isEmpty(mOriginalJson)) {
                mResponse = Response.fromJson(mOriginalJson);
            }
        } catch (Exception e) {
            Log.d(TAG, "json parse error");
        }
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
        request.addRequest(mBitmap, new Feature(Feature.FeatureType.SAFE_SEARCH_DETECTION));
        adapter.create(VisionApi.class).post(getString(R.string.vision_api), request)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        mOriginalJson = response.message();
                        showResult();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
        Snackbar.make(mCoordinator, R.string.vision_api_upload, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResult();
    }

    /**
     * SafeSearch結果を表示
     */
    void showResult() {
        parse();
        if (mResponse != null) {
            SafeSearchAnnotation annotation = mResponse.getResponses().get(0).getSafeSearchAnnotation();
            mAdult.setRating(annotation.getRating(annotation.getAdult()));
            mSpoof.setRating(annotation.getRating(annotation.getSpoof()));
            mMedical.setRating(annotation.getRating(annotation.getMedical()));
            mViolence.setRating(annotation.getRating(annotation.getViolence()));


            mAdult.setText(getString(R.string.adult));
            mSpoof.setText(getString(R.string.spoof));
            mMedical.setText(getString(R.string.medical));
            mViolence.setText(getString(R.string.violence));

        }
    }


}
