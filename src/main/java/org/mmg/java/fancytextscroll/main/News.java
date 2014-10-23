/*
 * 作成日: 2005/02/11
 */
package org.mmg.java.fancytextscroll.main;

import java.util.Vector;
import java.awt.Font;
/**
 * @author Manuel Medina González
 *
 */
public class News {
	private Vector<TextLine> title;
	private Vector<TextLine> contents;
	private Font titlefont;
	private Font contentsfont;
	private int titlecolor;
	private int titlehighlightcolor;
	private int contentscolor;
	private int contentshighlightcolor;
	private int currentcolor;
	private String url;
	private String target;
	
	public static final String TITLE = "title";
	public static final String CONTENTS = "contents";
	public static final String PLAIN = "PLAIN";
	public static final String BOLD = "BOLD";
	public static final String ITALIC = "ITALIC";
	public static final String SELF_PAGE = "_self";
	public static final String BLANK_PAGE = "_blank";
	public static final String NO_URL = "nolink";
	
	public static final int N_BASIC_ELEMENTS = 12;
	
	public News() {
		title = new Vector<TextLine>();
		contents = new Vector<TextLine>();
		titlecolor = 0xFFA221;
		titlehighlightcolor = 0x0000FF;
		contentscolor = 0xFFFF02;
		contentshighlightcolor = 0xFF0000;
		currentcolor = 0;
	}
	
	public void setTitle(String title, int x, int y) {
		this.title.add(new TextLine(title,x,y));
	}
	
	public void setFont(String toc, String fontname, int fontstyle, int fontsize) {
		if (toc.equals("title"))
			titlefont = new Font(fontname, fontstyle, fontsize);
		else
			contentsfont = new Font(fontname, fontstyle, fontsize);
	}
	
	public void setContentsColor(int rgbcolor) {
		contentscolor = rgbcolor;
	}
	
	public void setContentsHighlightColor(int rgbcolor) {
		contentshighlightcolor = rgbcolor;
	}
	
	public void setTitleColor(int rgbcolor) {
		titlecolor = rgbcolor;
    }
	
	public void setTitleHighlightColor(int rgbcolor) {
		titlehighlightcolor = rgbcolor;
	}
	
	public void setCurrentColor(int colorstate) {
		currentcolor = colorstate;
	}
	
	public void addContents(String text, int x, int y) {
		contents.add(new TextLine(text, x, y));
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public int getTitleColor() {
		return titlecolor;
	}
	
	public int getTitleHighlightColor() {
		return titlehighlightcolor;
	}
	
	public int getContentsColor() {
		return contentscolor;
	}
	
	public int getContentsHighlightColor() {
		return contentshighlightcolor;
	}
	
	public int getCurrentColor() {
		return currentcolor;
	}
	
	public Vector<TextLine> getTitle() {
		return title;
	}
	
	public Font getTitleFont() {
		return titlefont;
	}
	
	public Font getContentsFont() {
		return contentsfont;
	}
	
	public Vector<TextLine> getContents() {
		return contents;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getTarget() {
		return target;
	}

}