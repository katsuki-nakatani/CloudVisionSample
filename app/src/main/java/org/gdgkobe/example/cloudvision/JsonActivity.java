package org.gdgkobe.example.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JsonActivity extends AppCompatActivity {

    public static String PARAM_TEXT = "param_text";

    @Bind(R.id.text_view)
    TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        mText = ButterKnife.findById(this, R.id.text_view);

        String textString = getIntent().getStringExtra(PARAM_TEXT);

        if (textString != null) {
            textString = textString.replace("},", ",}\n");
            mText.setText(textString);
        }
    }

    /**
     * Activity表示用のIntentを生成する
     *
     * @param context Context
     * @param param   表示する文字列
     * @return
     */
    public static Intent createIntent(Context context, String param) {
        Intent intent = new Intent(context, JsonActivity.class);
        intent.putExtra(PARAM_TEXT, param);
        intent.setAction(Intent.ACTION_VIEW);
        return intent;
    }

}
