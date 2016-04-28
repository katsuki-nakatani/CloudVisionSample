package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.squareup.moshi.Moshi;

import org.gdgkobe.example.cloudvision.api.VisionApi;
import org.gdgkobe.example.cloudvision.entity.Feature;
import org.gdgkobe.example.cloudvision.entity.LabelAnnotation;
import org.gdgkobe.example.cloudvision.entity.Request;
import org.gdgkobe.example.cloudvision.entity.Response;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class LabelDetectionActivity extends VisionBaseActivity {

    @Bind(R.id.text_view)
    TextView mTextView;

    /**
     * Activity表示用Intentを作成する
     *
     * @param context Context
     * @return intent
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, LabelDetectionActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labeldetection);
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
        request.addRequest(mBitmap, new Feature(Feature.FeatureType.LABEL_DETECTION));
        adapter.create(VisionApi.class).post(getString(R.string.vision_api), request)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body().getResponses().get(0).getLabelAnnotations().size() <= 0) {
                            Snackbar.make(mCoordinator, R.string.label_annotation_error, Snackbar.LENGTH_LONG).show();
                        } else {
                            mOriginalJson = response.message();
                            setText(response.body().getResponses().get(0).getLabelAnnotations());
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
        Snackbar.make(mCoordinator, R.string.vision_api_upload, Snackbar.LENGTH_LONG).show();
    }

    /**
     * 物体認識結果をセット
     *
     * @param labelAnnotations 結果をテキストにセット
     */
    void setText(List<LabelAnnotation> labelAnnotations) {

        StringBuilder stringBuilder = new StringBuilder();
        for (LabelAnnotation labelAnnotation : labelAnnotations) {
            stringBuilder.append(getString(R.string.label_detection_result, labelAnnotation.getDescription(), labelAnnotation.getScore() * 100));
        }
        mTextView.setText(stringBuilder.toString());
    }
}
