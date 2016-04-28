package org.gdgkobe.example.cloudvision;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.face_detection)
    void showFaceDetectionActivity(View view) {
        startActivity(FaceDetectionActivity.createIntent(this));
    }

    @OnClick(R.id.label_detection)
    void showLabelDetectionActivity(View view) {
        startActivity(LabelDetectionActivity.createIntent(this));
    }

    @OnClick(R.id.text_detection)
    void showTextDetectionActivity(View view) {
        startActivity(TextDetectionActivity.createIntent(this));
    }

    @OnClick(R.id.safe_search_detection)
    void showSafeSearchDetectionActivity(View view) {
        startActivity(SafeSearchDetectionActivity.createIntent(this));
    }
}
