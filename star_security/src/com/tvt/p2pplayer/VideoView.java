package com.tvt.p2pplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class VideoView extends View {

	private Bitmap bitmap;

	public VideoView(Context paramContext) {
		super(paramContext);
	}

	public VideoView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	protected void onDraw(Canvas paramCanvas) {

		// while (true) {
		if (this.bitmap == null)
			return;
		Bitmap localBitmap = this.bitmap;
		paramCanvas.drawBitmap(localBitmap, 50, 50, null);
		super.onDraw(paramCanvas);
		// }
	}

	public void setBitmap(Bitmap paramBitmap) {
		this.bitmap = paramBitmap;
		postInvalidate();
	}
}
