package com.moon.utility.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorRes;

import com.moon.utility.R;

public class Triangle extends View {
    private Path path;
    private Paint shapePaint;
    private int fillColor;
    private int type;
    private float corner;

    public Triangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.TriangleShape, 0, 0);
        type = attr.getInt(R.styleable.TriangleShape_triangleType, Type.CENTER_TOP);
        fillColor = attr.getColor(R.styleable.TriangleShape_triangleColor, Color.WHITE);
        path = new Path();
        shapePaint = new Paint();
        shapePaint.setPathEffect(new CornerPathEffect(50.4f));
        attr.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (type) {
            case Type.LEFT_BOTTOM: {
                path.moveTo(0, 0);
                path.lineTo(0, getHeight());
                path.lineTo(getWidth(), getHeight());
                path.close();
                break;
            }
            case Type.RIGHT_BOTTOM: {
                path.moveTo(0, getHeight());
                path.lineTo(getWidth(), getHeight());
                path.lineTo(getWidth(), 0);
                path.close();
                break;
            }
            case Type.CENTER_BOTTOM: {
                path.moveTo(0, 0);
                path.lineTo(getWidth() / 2, getHeight());
                path.lineTo(getWidth(), 0);
                path.close();
                break;
            }
            case Type.LEFT_TOP: {
                path.moveTo(0, 0);
                path.lineTo(0, getHeight());
                path.lineTo(getWidth(), 0);

                break;
            }
            default: {
                path.moveTo(0, getHeight());
                path.lineTo(getWidth() / 2, 0);
                path.lineTo(getWidth(), getHeight());
                path.close();
                break;
            }
        }

        shapePaint.setStrokeJoin(Paint.Join.ROUND);
        shapePaint.setAntiAlias(true);
        shapePaint.setColor(getFillColor());
        shapePaint.setStyle(Paint.Style.FILL);

        canvas.drawPath(path, shapePaint);
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(@ColorRes int fillColor) {
        this.fillColor = fillColor;
    }

    static class Type {
        public static final int CENTER_TOP = 0;
        public static final int LEFT_BOTTOM = 1;
        public static final int RIGHT_BOTTOM = 2;
        public static final int CENTER_BOTTOM = 3;
        public static final int LEFT_TOP = 4;
    }
}
