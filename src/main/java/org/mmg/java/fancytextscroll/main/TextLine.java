
package org.mmg.java.fancytextscroll.main;


/**
 * @author Manuel Medina González
 *
 */
public class TextLine {
	private String text;
	private int x;
	private int y;
	private int originalx;
	private int originaly;

	public TextLine() {
		this("",0,0);
	}
	
	public TextLine(String text) {
		this(text,0,0);
	}
	
	public TextLine(String text, int x, int y) {
		this.text = text;
		this.x = originalx = x;
		this.y = originaly = y;
	}
	
	public String getText() {
		return text;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getOriginalX() {
		return originalx;
	}
	
	public int getOriginalY() {
		return originaly;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setOriginalX(int originalx) {
		this.originalx = originalx;
	}
	
	public void setOriginalY(int originaly) {
		this.originaly = originaly;
	}

}
