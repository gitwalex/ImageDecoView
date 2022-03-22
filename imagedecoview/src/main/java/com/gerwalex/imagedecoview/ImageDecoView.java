package com.gerwalex.imagedecoview;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ImageDecoView extends FrameLayout {
    public final DecoView decoView;
    public final ImageView imageView;
    public final TextView textView;

    @BindingAdapter("image")
    public static void loadImage(@NonNull ImageDecoView view, @DrawableRes int resourceId) {
        RequestCreator request = Picasso.get()
                .load(resourceId);
        request.transform(new CropCircleTransformation());
        request.into(view.imageView);
    }

    @BindingAdapter("image")
    public static void loadImage(@NonNull ImageDecoView view, @Nullable String filename) {
        if (filename != null) {
            loadImage(view, new File(filename));
        }
    }

    @BindingAdapter("image")
    public static void loadImage(@NonNull ImageDecoView view, @Nullable Uri uri) {
        if (uri != null) {
            RequestCreator request = Picasso.get()
                    .load(uri);
            request.transform(new CropCircleTransformation());
            request.into(view.imageView);
        }
    }

    @BindingAdapter("image")
    public static void loadImage(@NonNull ImageDecoView view, @Nullable File file) {
        if (file != null) {
            RequestCreator request = Picasso.get()
                    .load(file);
            request.transform(new CropCircleTransformation());
            request.into(view.imageView);
        }
    }

    public ImageDecoView(@NonNull Context context) {
        this(context, null);
    }

    public ImageDecoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageDecoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageView = new ImageView(context, attrs);
        decoView = new DecoView(context, attrs);
        textView = new TextView(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageDecoView);
        try {
            int arcColor = a.getColor(R.styleable.ImageDecoView_arcColor, context.getResources()
                    .getColor(android.R.color.transparent));
            int placeholder = a.getResourceId(R.styleable.ImageDecoView_placeholder, 0);
            int padding = (int) a.getDimension(R.styleable.ImageDecoView_dv_lineWidth, 30f);
            imageView.setPadding(padding, padding, padding, padding);
            int drawable = a.getResourceId(R.styleable.ImageDecoView_dv_image, 0);
            if (drawable != 0) {
                loadImage(this, drawable);
            } else if (placeholder != 0) {
                loadImage(this, placeholder);
            }
            if (a.getBoolean(R.styleable.ImageDecoView_fullArc, false)) {
                SeriesItem seriesItem = new SeriesItem.Builder(arcColor).setRange(0, 100, 0)
                        .build();
                decoView.addSeries(seriesItem);
                decoView.addEvent(new DecoEvent.Builder(100).setIndex(0)
                        .setDuration(0)
                        .build());
            }
            String text = a.getString(R.styleable.ImageDecoView_dv_text);
            if (text != null) {
                textView.setText(text);
                textView.setBackgroundColor(a.getColor(R.styleable.ImageDecoView_dv_textBackground,
                        context.getResources()
                                .getColor(android.R.color.transparent)));
                textView.setTextColor(a.getColor(R.styleable.ImageDecoView_dv_textColor, context.getResources()
                        .getColor(android.R.color.transparent)));
            }
        } finally {
            a.recycle();
        }
        addView(imageView);
        addView(decoView);
        addView(textView);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        decoView.setLayoutParams(lp);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        textView.setLayoutParams(lp);
    }
}
