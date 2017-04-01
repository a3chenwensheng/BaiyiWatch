package com.baiyi.watch.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

	// 图像保存到文件中
	public static File saveFile(Bitmap bitmap, String path) {
		BufferedOutputStream os = null;
		File file = null;
		try {
			file = new File(path);
			int end = path.lastIndexOf(File.separator);
			String _filePath = path.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					Log.e("", e.getMessage(), e);
				}
			}
		}
		return file;
	}
	
	   public static Bitmap compressImageFromFile(String srcPath) {
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inJustDecodeBounds = true;//只读边,不读内容
			Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			float hh = 800f;//
			float ww = 480f;//
			int be = 1;
			if (w > h && w > ww) {
				be = (int) (newOpts.outWidth / ww);
			} else if (w < h && h > hh) {
				be = (int) (newOpts.outHeight / hh);
			}
			if (be <= 0)
				be = 1;
			newOpts.inSampleSize = be;//设置采样率
			
			newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设
			newOpts.inPurgeable = true;// 同时设置才会有效
			newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
			
			bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//			return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
										//其实是无效的,大家尽管尝试
			return bitmap;
		}
	   
	   public static Bitmap doBlur(Bitmap sentBitmap, int radius,
				boolean canReuseInBitmap) {
			Bitmap bitmap;
			if (canReuseInBitmap) {
				bitmap = sentBitmap;
			} else {
				bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
			}

			if (radius < 1) {
				return (null);
			}

			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int[] pix = new int[w * h];
			bitmap.getPixels(pix, 0, w, 0, 0, w, h);

			int wm = w - 1;
			int hm = h - 1;
			int wh = w * h;
			int div = radius + radius + 1;

			int r[] = new int[wh];
			int g[] = new int[wh];
			int b[] = new int[wh];
			int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
			int vmin[] = new int[Math.max(w, h)];

			int divsum = (div + 1) >> 1;
			divsum *= divsum;
			int dv[] = new int[256 * divsum];
			for (i = 0; i < 256 * divsum; i++) {
				dv[i] = (i / divsum);
			}

			yw = yi = 0;

			int[][] stack = new int[div][3];
			int stackpointer;
			int stackstart;
			int[] sir;
			int rbs;
			int r1 = radius + 1;
			int routsum, goutsum, boutsum;
			int rinsum, ginsum, binsum;

			for (y = 0; y < h; y++) {
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				for (i = -radius; i <= radius; i++) {
					p = pix[yi + Math.min(wm, Math.max(i, 0))];
					sir = stack[i + radius];
					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);
					rbs = r1 - Math.abs(i);
					rsum += sir[0] * rbs;
					gsum += sir[1] * rbs;
					bsum += sir[2] * rbs;
					if (i > 0) {
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					} else {
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}
				}
				stackpointer = radius;

				for (x = 0; x < w; x++) {

					r[yi] = dv[rsum];
					g[yi] = dv[gsum];
					b[yi] = dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer - radius + div;
					sir = stack[stackstart % div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if (y == 0) {
						vmin[x] = Math.min(x + radius + 1, wm);
					}
					p = pix[yw + vmin[x]];

					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer + 1) % div;
					sir = stack[(stackpointer) % div];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi++;
				}
				yw += w;
			}
			for (x = 0; x < w; x++) {
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				yp = -radius * w;
				for (i = -radius; i <= radius; i++) {
					yi = Math.max(0, yp) + x;

					sir = stack[i + radius];

					sir[0] = r[yi];
					sir[1] = g[yi];
					sir[2] = b[yi];

					rbs = r1 - Math.abs(i);

					rsum += r[yi] * rbs;
					gsum += g[yi] * rbs;
					bsum += b[yi] * rbs;

					if (i > 0) {
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					} else {
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}

					if (i < hm) {
						yp += w;
					}
				}
				yi = x;
				stackpointer = radius;
				for (y = 0; y < h; y++) {
					// Preserve alpha channel: ( 0xff000000 & pix[yi] )
					pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
							| (dv[gsum] << 8) | dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer - radius + div;
					sir = stack[stackstart % div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if (x == 0) {
						vmin[y] = Math.min(y + r1, hm) * w;
					}
					p = x + vmin[y];

					sir[0] = r[p];
					sir[1] = g[p];
					sir[2] = b[p];

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer + 1) % div;
					sir = stack[stackpointer];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi += w;
				}
			}

			bitmap.setPixels(pix, 0, w, 0, 0, w, h);

			return (bitmap);
		}

}
