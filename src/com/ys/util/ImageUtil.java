package com.ys.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	
	/**
	 *  
	 * @Title: ǿ��ѹ��/�Ŵ�ͼƬ���̶��Ĵ�С 
	 * @Description: TODO
	 * @param srcImageFile
	 * @param destPath
	 * @param length
	 * @throws IOException
	 */
    public final static void scale(String path,String Filename, int toWidth,int toHeight,String subPath) throws IOException { 
    	
    	//����Դ�ļ�
        Image src = ImageIO.read(new File(path+Filename));      // ����Image����  
        int width = src.getWidth(null);    // �õ�Դͼ��  
        int height = src.getHeight(null);  // �õ�Դͼ��  
        
        int destWidth = 0;
        int destHeight = 0;
        
        int x,y = 0;
        
        float ratio = (float)width/height;
        float toRatio = (float)toWidth/toHeight;
                   	    	
        // SCALE_SMOOTH �������㷨 �������ͼƬ��ƽ���ȵ� ���ȼ����ٶȸ� ��ɵ�ͼƬ�����ȽϺ� ���ٶ���  
        BufferedImage image = new BufferedImage(toWidth, toHeight,BufferedImage.TYPE_INT_RGB );   
        
        //����
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, toWidth, toHeight);
        
        if (ratio > toRatio){
        	destWidth = toWidth;
        	destHeight = destWidth * height / width;    
        	x = 0;
        	y = (toHeight-destHeight)/2;
        }else{
        	destHeight = toHeight;        	
        	destWidth = destHeight * width / height;          	
        	x = (toWidth-destWidth)/2;
        	y = 0;
        }
                
        g.drawImage(src.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH), x,y,destWidth, destHeight,Color.white, null);
        
        g.dispose();
        
        String destPath = path + subPath;
        File dir = new File(destPath);
        
        if(dir!=null&&!dir.exists()){
        	dir.mkdirs();
        } 
        
        File destFile = new File(destPath + "/" + Filename);  
        FileOutputStream out = new FileOutputStream(destFile); // ������ļ���  
        // ������ʵ��bmp��png��gifתjpg  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        encoder.encode(image); // JPEG����  
        out.close();  
    }  

}
