package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.squareup.moshi.Moshi;

import org.gdgkobe.example.cloudvision.api.VisionApi;
import org.gdgkobe.example.cloudvision.entity.Feature;
import org.gdgkobe.example.cloudvision.entity.Request;
import org.gdgkobe.example.cloudvision.entity.Response;
import org.gdgkobe.example.cloudvision.entity.Vertex;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class TextDetectionActivity extends VisionBaseActivity {

    private static final String TAG = TextDetectionActivity.class.getSimpleName();

    //テキスト検出エリアのボーダーカラー
    private static final int POLY_AREA_COLOR = Color.argb(255, 255, 0, 0);

    @Bind(R.id.language)
    TextView mLanguage;
    @Bind(R.id.result_text)
    TextView mResultText;
    private Response mResponse;

    /**
     * Activity表示用Intentを作成する
     *
     * @param context Context
     * @return intent
     */
    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, TextDetectionActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textdetection);
        ButterKnife.bind(this);
        super.restoreView();
    }

    /**
     * JsonをParse
     */
    void parse(){
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
        request.addRequest(mBitmap, new Feature(Feature.FeatureType.TEXT_DETECTION));
        adapter.create(VisionApi.class).post(getString(R.string.vision_api), request)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body().getResponses().get(0).getTextAnnotations().size() <= 0) {
                            Snackbar.make(mCoordinator, R.string.text_annotation_error, Snackbar.LENGTH_LONG).show();
                        } else {
                            mOriginalJson = response.message();
                            showResult();
                        }
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
     * OCR結果を表示
     */
    void showResult() {
        parse();
        if (mResponse != null) {
            mLanguage.setText(mResponse.getResponses().get(0).getTextAnnotations().get(0).getLocale());
            mResultText.setText(mResponse.getResponses().get(0).getTextAnnotations().get(0).getDescription());
            List<Vertex> vertexList = mResponse.getResponses().get(0).getTextAnnotations().get(0).getBoundingPoly().getVertices();
            Canvas canvas;
            Bitmap newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(newBitmap);
            canvas.drawBitmap(mBitmap, 0, 0, null);
            Paint paint = new Paint();
            paint.setColor(POLY_AREA_COLOR);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawRect(vertexList.get(0).getX(),
                    vertexList.get(0).getY(),
                    vertexList.get(2).getX(),
                    vertexList.get(2).getY()
                    , paint);
            mImage.setImageBitmap(newBitmap);
        }
    }


}
