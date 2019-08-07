package org.world.tool.barcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

/**
 * @Description
 * @DAuthor Double
 */
public class ZxingBarcodeUtil {
	private static String CHARSET = "UTF-8";
	private static int QRCODE_SIZE = 100;

	public static BitMatrix getShapeCode(String code){
		Hashtable<EncodeHintType,String> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
		BitMatrix bitMatrix = null;
		try{
			bitMatrix = new MultiFormatWriter().encode(code, BarcodeFormat.CODE_128,100,25,hints);
		}catch (Exception e){
			e.printStackTrace();
		}
		return bitMatrix;
	}


	public static void main(String[] args) {
//		BitMatrix code = ZxingBarcodeUtil.getShapeCode("Hello");
//		System.out.println(code);


	}
}
