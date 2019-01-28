package ru.debian17.debouncedonclicklistener;

import android.os.SystemClock;
import android.view.View;

public abstract class DebouncedOnClickListener implements View.OnClickListener {

    private static final long DEFAULT_MIN_DELAY = 1000;

    private long delay;
    private long lastTime = 0;

    public DebouncedOnClickListener() {
        delay = DEFAULT_MIN_DELAY;
    }

    public DebouncedOnClickListener(long delay) {
        this.delay = delay;
    }

    public abstract void onClicked(View view);

    @Override
    public void onClick(View v) {
        long now = SystemClock.elapsedRealtime();
        if (now - lastTime > delay) {
            onClicked(v);
        }
        lastTime = now;
    }

    public static DebouncedOnClickListener wrap(final View.OnClickListener onClickListener) {
        return new DebouncedOnClickListener() {
            @Override
            public void onClicked(View view) {
                onClickListener.onClick(view);
            }
        };
    }

    public static DebouncedOnClickListener wrap(final View.OnClickListener onClickListener, long delay) {
        return new DebouncedOnClickListener(delay) {
            @Override
            public void onClicked(View view) {
                onClickListener.onClick(view);
            }
        };
    }

}