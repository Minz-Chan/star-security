/*
 * FileName:BitmapUtil.java
 * 
 * Package:com.starsecurity.util
 * 
 * Date:2013-05-10
 * 
 * Copyright: Copyright (c) 2013 Minz.Chan
 */
package com.starsecurity.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * @function     功能	  位图操作工具类
 * @author       创建人              陈明珍
 * @date        创建日期           2013-05-10
 * @author       修改人              陈明珍
 * @date        修改日期           2013-05-10
 * @description 修改说明
 *   2013-05-10 加入位图两种旋转功能
 */
public class BitmapUtil {
	 public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees,
                    (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(
                        b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    //b.recycle();  //Bitmap操作完应显示释放
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // 出现内存不足异常，return原始的bitmap对象
            }
        }
        return b;
	  }

	public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
				(float) bm.getHeight() / 2);
		float targetX, targetY;
		if (orientationDegree == 90) {
			targetX = bm.getHeight();
			targetY = 0;
		} else {
			targetX = bm.getHeight();
			targetY = bm.getWidth();
		}

		final float[] values = new float[9];
		m.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		m.postTranslate(targetX - x1, targetY - y1);

		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
				Bitmap.Config.ARGB_8888);

		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}
}
