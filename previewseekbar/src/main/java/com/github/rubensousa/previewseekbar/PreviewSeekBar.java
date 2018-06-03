package com.github.rubensousa.previewseekbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.github.rubensousa.previewseekbar.base.PreviewLoader;
import com.github.rubensousa.previewseekbar.base.PreviewView;

import java.util.ArrayList;
import java.util.List;

/**
 * A SeekBar that should be used inside PreviewSeekBarLayout
 */
public class PreviewSeekBar extends AppCompatSeekBar implements PreviewView,
        SeekBar.OnSeekBarChangeListener {

    private List<PreviewView.OnPreviewChangeListener> listeners;

    public PreviewSeekBar(Context context) {
        super(context);
        init();
    }

    public PreviewSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PreviewSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        listeners = new ArrayList<>();
        super.setOnSeekBarChangeListener(this);
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        // No-op
    }

    @Override
    public void attachPreviewFrameLayout(FrameLayout frameLayout) {

    }

    @Override
    public void setPreviewColorTint(int color) {
        Drawable drawable = DrawableCompat.wrap(getThumb());
        DrawableCompat.setTint(drawable, color);
        setThumb(drawable);

        drawable = DrawableCompat.wrap(getProgressDrawable());
        DrawableCompat.setTint(drawable, color);
        setProgressDrawable(drawable);
    }

    @Override
    public void setPreviewColorResourceTint(int color) {
        setPreviewColorTint(ContextCompat.getColor(getContext(), color));
    }

    @Override
    public int getDefaultColor() {
        ColorStateList list = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            list = getThumbTintList();
        }
        if (list != null) {
            return list.getDefaultColor();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isShowingPreview() {
        return false;
    }

    @Override
    public void showPreview() {

    }

    @Override
    public void hidePreview() {

    }

    @Override
    public void setPreviewLoader(PreviewLoader previewLoader) {

    }

    @Override
    public void addOnPreviewChangeListener(OnPreviewChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void removeOnPreviewChangeListener(OnPreviewChangeListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        for (OnPreviewChangeListener listener : listeners) {
            listener.onPreview(this, progress, fromUser);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        for (OnPreviewChangeListener listener : listeners) {
            listener.onStartPreview(this, seekBar.getProgress());
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        for (OnPreviewChangeListener listener : listeners) {
            listener.onStopPreview(this, seekBar.getProgress());
        }
    }


}
