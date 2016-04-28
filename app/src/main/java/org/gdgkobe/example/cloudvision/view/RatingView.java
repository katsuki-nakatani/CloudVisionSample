package org.gdgkobe.example.cloudvision.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.gdgkobe.example.cloudvision.R;

import butterknife.ButterKnife;
import icepick.Icepick;

public class RatingView extends LinearLayout {

    TextView mTextView;
    RatingBar mRatingBar;

    public RatingView(Context context) {
        this(context, null);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return Icepick.saveInstanceState(this, super.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(Icepick.restoreInstanceState(this, state));
    }

    public RatingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.RatingViewStyle);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        View layout = LayoutInflater.from(context).inflate(R.layout.rating_view, this);
        mRatingBar = ButterKnife.findById(layout, R.id.rating_bar);
        mTextView = ButterKnife.findById(layout, R.id.title_text);
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setRating(float rating) {
        mRatingBar.setRating(rating);
    }

    /**
     * Ratingが1以下UN_LIKELYの場合は表示をしない
     */
    public void setVisible() {
        setVisibility(mRatingBar.getRating() <= 1 ?
                GONE :
                VISIBLE);
    }
}
