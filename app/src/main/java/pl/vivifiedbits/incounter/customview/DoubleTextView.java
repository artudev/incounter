package pl.vivifiedbits.incounter.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import pl.vivifiedbits.incounter.R;
import timber.log.Timber;

/**
 * Created by Artur Kasprzak on 24.04.2018.
 */
public class DoubleTextView extends View implements DoubleText {

	private static final float DEF_SMALL_SIZE = 12f;
	private static final float DEF_LARGE_SIZE = 16f;
	private static final float INNER_SPACING = 8;
	private static final String MAX_COUNT_STRING = "999999.99";
	private Paint mLinePaint;
	private TextPaint mLargeTextPaint;
	private TextPaint mSmallTextPaint;
	private String mSmallText;
	private String mLargeText;
	private int mInnerSpacing;

	private float mSmallTextSize;
	private float mLargeTextSize;
	private int mSmallTextColor;
	private int mLargeTextColor;

	public DoubleTextView(Context context) {
		this(context, null);
	}

	@SuppressLint("ResourceAsColor")
	public DoubleTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		if (attrs != null) {
			TypedArray attributes =
					context.obtainStyledAttributes(attrs, R.styleable.DoubleTextView, 0, 0);

			mSmallTextSize = attributes
					.getDimension(R.styleable.DoubleTextView_smallTextSize, DEF_SMALL_SIZE);
			mLargeTextSize = attributes
					.getDimension(R.styleable.DoubleTextView_largeTextSize, DEF_LARGE_SIZE);
			mSmallTextColor = attributes.getColor(R.styleable.DoubleTextView_smallTextColor,
					ContextCompat.getColor(context, R.color.textDark));
			mLargeTextColor = attributes.getColor(R.styleable.DoubleTextView_largeTextColor,
					ContextCompat.getColor(context, R.color.text));

			Timber.d("mSmallTextSize " + mSmallTextSize);
			Timber.d("mLargeTextSize " + mLargeTextSize);
			Timber.d("mSmallTextColor " + mSmallTextColor);
			Timber.d("mLargeTextColor " + mLargeTextColor);

			attributes.recycle();
		} else {
			mSmallTextSize = spToPx(DEF_SMALL_SIZE);
			mLargeTextSize = spToPx(DEF_LARGE_SIZE);
			mSmallTextColor = ContextCompat.getColor(context, R.color.textDark);
			mLargeTextColor = ContextCompat.getColor(context, R.color.text);
		}

		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
		mLinePaint.setStrokeWidth(1f);

		mSmallTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mSmallTextPaint.setColor(mSmallTextColor);
		mSmallTextPaint.setTextSize(mSmallTextSize);

		mLargeTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		mLargeTextPaint.setColor(mLargeTextColor);
		mLargeTextPaint.setTextSize(mLargeTextSize);

		mInnerSpacing = dpToPx(INNER_SPACING);

		setSmallText("");
		setLargeText("");
	}

	public int spToPx(float sp) {
		return (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
	}

	public int dpToPx(float dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final Paint.FontMetrics fontMetricsLarge = mLargeTextPaint.getFontMetrics();

		// Measure maximum possible width of text.
		final float maxTextWidth = mLargeTextPaint.measureText(MAX_COUNT_STRING) +
								   mSmallTextPaint.measureText(MAX_COUNT_STRING) +
								   2 * mInnerSpacing;
		// Estimate maximum possible height of text.
		final float maxTextHeight = -fontMetricsLarge.top + fontMetricsLarge.bottom;

		// Add padding to maximum width calculation.
		final int desiredWidth = Math.round(maxTextWidth + getPaddingLeft() + getPaddingRight());

		// Add padding to maximum height calculation.
		final int desiredHeight = Math.round(maxTextHeight + getPaddingTop() + getPaddingBottom());

		// Reconcile size that this view wants to be with the size the parent will let it be.
		final int measuredWidth = resolveSize(desiredWidth, widthMeasureSpec);
		final int measuredHeight = resolveSize(desiredHeight, heightMeasureSpec);

		// Store the final measured dimensions.
		setMeasuredDimension(measuredWidth, measuredHeight);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);

		// Grab canvas dimensions.
		final int canvasWidth = canvas.getWidth();
		final int canvasHeight = canvas.getHeight();

		// Calculate horizontal center.
		final float centerX = canvasWidth * 0.5f;

		// Draw dividing line.
		canvas.drawLine(centerX, 0, centerX, canvasHeight, mLinePaint);

		// Draw text.

		// Measure the width of text to display.
		final float textWidth = mSmallTextPaint.measureText(mSmallText);
		float textX = Math.round(centerX - textWidth - mInnerSpacing);
		// Draw small
		canvas.drawText(mSmallText, textX, canvasHeight, mSmallTextPaint);

		textX = Math.round(centerX);
		// Draw large
		canvas.drawText(mLargeText, textX + mInnerSpacing, canvasHeight, mLargeTextPaint);
	}

	@Override
	public String getSmallText() {
		return mSmallText;
	}

	@Override
	public String getLargeText() {
		return mLargeText;
	}

	@Override
	public void setSmallText(String text) {
		mSmallText = text;
		invalidate();
	}

	@Override
	public void setLargeText(String text) {
		mLargeText = text;
		invalidate();
	}

	@Override
	public void setText(String small, String large) {
		mSmallText = small;
		mLargeText = large;
		invalidate();
	}
}
