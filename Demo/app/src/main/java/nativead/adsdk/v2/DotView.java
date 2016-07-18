package nativead.adsdk.v2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jerry on 16/7/6.
 */
public class DotView extends View {

    private int mSize = 1;
    private int mIndex;
    private static final int DOT_INTERVAL = 4;
    private static final int DOT_SIZE = 10;

    private Paint mPaint;

    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        mPaint = new Paint();
        mPaint.setColor(0xaaca1c3d);
        mPaint.setAntiAlias(true);
    }

    public void setSize(int size) {
        mSize = size;
        requestLayout();
    }

    public void setIndex(int index) {
        mIndex = index;
        invalidate();
    }

    int dp2px(float dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mSize == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        final int width = mSize * dp2px(DOT_SIZE) + (mSize - 1) * dp2px(DOT_INTERVAL);
        final int height = dp2px(DOT_SIZE);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int r = dp2px(DOT_SIZE) / 2;
        for (int i = 0; i < mSize; i++) {
            int left = i * (dp2px(DOT_SIZE + DOT_INTERVAL));
            if (mIndex == i) {
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(left + r, r, r, mPaint);
            } else {
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(dp2px(1));
                canvas.drawCircle(left + r, r, r - dp2px(0.5f), mPaint);
            }

        }
    }
}
