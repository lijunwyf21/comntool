package com.comntool.ext;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.comntool.util.utility.Comn;

public class ComnImg {
	public static void main2(String[] args) {
		allFont();
	}
	
	public static void allFont() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for(Font font : ge.getAllFonts()) {
			Comn.pl(font.getFontName());
		}
		GraphicsDevice[] gs = ge.getScreenDevices();
		for (int j = 0; j < gs.length; j++) { 
	      GraphicsDevice gd = gs[j];
	      GraphicsConfiguration[] gc =
	        gd.getConfigurations();
	      for (int i=0; i < gc.length; i++) {
	         JFrame f = new JFrame(gs[j].getDefaultConfiguration());
	         f.setBounds(100, 100, 400, 300);
	         Canvas c = new Canvas(gc[i]); 
	         Rectangle gcBounds = gc[i].getBounds();
	         int xoffs = gcBounds.x;
	         int yoffs = gcBounds.y;
	           f.getContentPane().add(c);
	           f.setLocation((i*50)+xoffs, (i*60)+yoffs);
	         f.show();
	      }
	   }
	}

	public static ImageIcon createImageClose() {
        Font font=new Font("楷体", Font.BOLD, 20);
        try {
        	// http://www.chezaiyi.cn/symbol/2071.html
        	return createImage("X", font, 20, 20);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return null;
	}
	
	public static ImageIcon createImage(String str, Font font,   
            Integer width, Integer height) throws Exception {  
        // 创建图片  
        BufferedImage image = new BufferedImage(width, height,  
                BufferedImage.TYPE_INT_BGR);  
        Graphics g = image.getGraphics();  
        g.setClip(0, 0, width, height);  
        g.setColor(Color.white);  
        g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景  
        g.setColor(Color.black);// 在换成黑色  
        g.setFont(font);// 设置画笔字体  
        /** 用于获得垂直居中y */  
        Rectangle clip = g.getClipBounds();  
        FontMetrics fm = g.getFontMetrics(font);  
        int ascent = fm.getAscent();  
        int descent = fm.getDescent();  
        int y = (clip.height - (ascent + descent)) / 2 + ascent;  
        for (int i = 0; i < 6; i++) {// 256 340 0 680  
            g.drawString(str, i * 680, y);// 画出字符串  
        }  
        g.dispose();
        ImageIcon im=new ImageIcon(image);
        return im;
    }
}
