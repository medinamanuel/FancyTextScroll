/*
 * 作成日: 2004/12/16
 *
 */
package org.mmg.java.fancytextscroll.main;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.awt.Image;
import java.awt.MediaTracker;
import java.util.Iterator; 
import java.util.StringTokenizer;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Cursor;


/**
 * @author Manuel Medina González
 *
 */
public class TextDisplayArea extends Applet implements Runnable, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 2L;
	private Image background, imagebuffer;
	private Color bgcolor;
	private Graphics gbg;
	private Thread thrd;
	private int titleheight;
	private int strheight; 
	private int appletwidth;
	private int appletheight;
	private int Yposition;
	private int bgwidth; 
	private int bgheight;
	private int curtop; 
	private int topmargin; 
	private int pausetime; 
	private int scrolldelay;
	private int mousex;
	private int mousey;
	private int currenthighlighted;
	private int padding;
	private int vgap;
	private FontMetrics appletfontmetrics;
	private Vector<News> newscollection;
	private static final String PARAMNAME = "title";
	private static final String CONTENTSNAME = "news";
	private static final int ON = 1;
	private static final int OFF = 0;
	private boolean stopflag = false;
	
	//TODO: Read information from a textfile (Feb 15 2005)
	
	public void init() {
		//int vgap = 0;
		
		String curval;
		String strbg;
		
		MediaTracker mt = new MediaTracker(this);
		newscollection = new Vector<News>();
		curtop = 0;
		topmargin = 15;
		pausetime = 1000;
		scrolldelay = 50;
		padding = 3;
		vgap = 0;
		
		getAppletContext().showStatus("");
		
		strbg = getParameter("bgcolor");
		
		if (strbg != null) {
			bgcolor = new Color(Integer.parseInt(strbg,16));
		}
		else {
			bgcolor = new Color(0xFFFFFF);
		}
		
		strbg = getParameter("bgimage");
		
		if (strbg != null) {
			background = getImage(getDocumentBase(), strbg);
		}
		
		//Check if margins are present
		
		curval = getParameter("topmargin");
		
		if (curval != null) {
			topmargin = Integer.parseInt(curval);
		}
		
		//Check if scroll time was given
		
		curval = getParameter("scroll");
		
		if (curval != null) {
			scrolldelay = Integer.parseInt(curval);
		}
		
		curval = getParameter("padding");
		
		if (curval != null) {
			padding = Integer.parseInt(curval);			
		}
		
		appletwidth = this.getWidth();
		vgap = appletheight = this.getHeight() + topmargin;
		curval = getParameter("file");
		
		if (curval != null) {
			// Source is a file. Read the location
			extractValuesFromFile(curval);	
		}
		else if ((curval = getParameter("dir")) != null) {
			// Source is a directory containing only correct files
			extractValuesFromDir(curval);
		}
		else {
			// The values are passed as parameters in the Applet tag
			extractValues();
		}
		
		// End update
		
	    Yposition = appletheight + titleheight;
	    //System.out.println("First Yposition = " + Yposition);
	    
	    curval = getParameter("pause");
    	
    	if (curval != null) {
    		pausetime = Integer.parseInt(curval);
    	}

		mt.addImage(background,1);
		
		imagebuffer = createImage(appletwidth,appletheight);
		
		try {
			mt.waitForAll();
		}
		catch (InterruptedException ie) {
			System.err.println(ie.getMessage());
		}
		
		if (background != null) {
		    bgwidth = background.getWidth(this);
		    bgheight = background.getHeight(this);
		}
		
		gbg = imagebuffer.getGraphics();
		//gbg.setClip(0,0,size().width,size().height);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void start() {
		if (thrd == null) {
			thrd = new Thread(this);
			thrd.start();
		}
	}
	
	public void stop() {
		if (thrd != null) {
			thrd = null;
		}
	}
	
	// Added June 2007
	private void setNewsFontFeatures(News n, String target, String fonttype, String fontstyle, int fontsize) {
		int style = -1;
		
		if (fontstyle.equals("PLAIN")) 
			style = Font.PLAIN;
		else if (fontstyle.equals("BOLD"))
			style = Font.BOLD;
		else
			style = Font.ITALIC;
		
		n.setFont(target,fonttype,style,fontsize);
	}
	
	private void setNewsColors(News n, String target, String highlightcolor, String color) {
		if (target.equals(News.TITLE)) {
			if (highlightcolor != null)
				n.setTitleHighlightColor(Integer.parseInt(highlightcolor,16));
			
			if (color != null)
				n.setTitleColor(Integer.parseInt(color,16));
		}
		else {
			if (highlightcolor != null)
				n.setContentsHighlightColor(Integer.parseInt(highlightcolor,16));
			
			if (color != null)
				n.setContentsColor(Integer.parseInt(color,16));
		}
	}
	
	private void addContentsToNews(News n, String target, String source) {
		StringTokenizer st = new StringTokenizer(source);
		StringBuffer curline = new StringBuffer();
		String curword = "";
		
		curline.setLength(0);
		
		while (st.hasMoreTokens()) {
			curword = st.nextToken().trim();
			if (appletfontmetrics.stringWidth(curline.toString()) + appletfontmetrics.stringWidth(curword) + appletfontmetrics.stringWidth(" ") <= appletwidth) {
				if (!curline.toString().equals(""))
				    curline.append(" ");
				
				curline.append(curword);
				
				if (!st.hasMoreTokens()) {
					if (target.equals(News.TITLE)) {
						n.setTitle(curline.toString(), 0,vgap);
						vgap += titleheight;
					}
					else {
						n.addContents(curline.toString(),0,vgap);
						vgap += strheight;
					}
					
				}
			}
			else {
				if (target.equals(News.TITLE)) {
					n.setTitle(curline.toString(),0,vgap);
					vgap += titleheight;
					curline.setLength(0);
					curline.append(curword);
					
					if (!st.hasMoreTokens()) {
						n.setTitle(curline.toString(),0,vgap);
						vgap += titleheight;
					}
				}
				else {
					n.addContents(curline.toString(),0,vgap);
					vgap += strheight;
					curline.setLength(0);
					curline.append(curword);
					
					if (!st.hasMoreTokens()) {
						n.addContents(curline.toString(), 0,vgap);
						vgap += strheight;
					}
				}
				
				
			}
		}
	}
	
	private News extractValues(String [] elements) {
		News n = new News();
		
		setNewsFontFeatures(n, News.TITLE, elements[0], elements[1], Integer.parseInt(elements[2]));
		setNewsColors(n, News.TITLE, elements[3], elements[4]);
		
		appletfontmetrics = getFontMetrics(n.getTitleFont());
    	titleheight = strheight = appletfontmetrics.getHeight();
    	
    	addContentsToNews(n,News.TITLE,elements[5]);
    	
    	setNewsFontFeatures(n, News.CONTENTS, elements[6], elements[7], Integer.parseInt(elements[8]));
    	setNewsColors(n, News.CONTENTS, elements[9], elements[10]);
    	
    	appletfontmetrics = getFontMetrics(n.getContentsFont());
    	strheight = appletfontmetrics.getHeight();
    	
    	addContentsToNews(n, News.CONTENTS, elements[11]);
    	
    	if (elements.length > News.N_BASIC_ELEMENTS) {
    		n.setURL(elements[12]);
    		n.setTarget(elements[13]);
    	}
    	else {
    		n.setURL(News.NO_URL);
    		n.setTarget(News.SELF_PAGE);
    	}
    	
    	return n;
    	
    	
	}
	
	private void extractValues() {
		News n = new News();
		String curpar = "title1";
		String curval;
		String curfonttype;
		String curfontstyle;
		String cururl;
		String cururltarget;
		String curhighcolor;
		String curcolor;
		int curfontsize;
		int i = 1;
		
		curval = getParameter(curpar);
		
		while (curval != null) {
			curfonttype = getParameter(curpar + "fonttype");
	    	curfontstyle = getParameter(curpar + "fontstyle");
	    	curfontsize = Integer.parseInt(getParameter(curpar + "fontsize"));
	    	curhighcolor = getParameter(curpar + "highlightcolor");
	    	curcolor = getParameter(curpar + "color");
	    	
	    	setNewsFontFeatures(n,News.TITLE,curfonttype, curfontstyle, curfontsize);
	    	setNewsColors(n,News.TITLE,curhighcolor,curcolor);	
	    	
	    	appletfontmetrics = getFontMetrics(n.getTitleFont());
	    	titleheight = strheight = appletfontmetrics.getHeight();
	    	
	    	addContentsToNews(n,News.TITLE,curval);
	    	
	    	
			curpar = CONTENTSNAME + String.valueOf(i);
			curval = getParameter(curpar);
			curfonttype = getParameter(curpar + "fonttype");
	    	curfontstyle = getParameter(curpar + "fontstyle");
	    	curfontsize = Integer.parseInt(getParameter(curpar + "fontsize"));
	    	curhighcolor = getParameter(curpar + "highlightcolor");
	    	curcolor = getParameter(curpar + "color");
	    	
	    	setNewsFontFeatures(n,News.CONTENTS,curfonttype,curfontstyle,curfontsize);
	    	setNewsColors(n,News.CONTENTS,curhighcolor,curcolor);
	    	
	    	appletfontmetrics = getFontMetrics(n.getContentsFont());
	    	strheight = appletfontmetrics.getHeight();
	    	
	    	addContentsToNews(n,News.CONTENTS,curval);
	    	
	    	cururl = getParameter(curpar + "link");
	    	
	    	if (cururl != null)
	    		n.setURL(cururl);
	    	else
	    		n.setURL(News.NO_URL);
	    	
	    	cururltarget = getParameter(curpar + "target");
	    	
	    	if (cururltarget != null)
	    		n.setTarget(cururltarget);
	    	else
	    		n.setTarget(News.SELF_PAGE);
	    	
			i++;
			curpar = PARAMNAME + String.valueOf(i);
			curval = getParameter(curpar);
			newscollection.add(n);
			vgap += strheight * padding; //Blank lines between news
		}
		
	}
	
	// Added June 2007
	private void extractValuesFromFile(String filename) {
		String line = "";
		
		try {
			BufferedReader srcfile = new BufferedReader(new InputStreamReader(new URL(getDocumentBase(), filename).openStream()));
			//BufferedReader srcfile = new BufferedReader(new InputStreamReader(new URL(filename).openStream()));
			while ((line = srcfile.readLine()) != null) {
				String [] elements = line.split("\\|");
						
				newscollection.add(extractValues(elements));
				
				vgap += strheight * padding; //Blank lines between news
			
		} //while ((line = srcfile.readLine()) != null)
		
	} catch (MalformedURLException urle) {
		urle.printStackTrace();
	} catch (FileNotFoundException fe) {
		fe.printStackTrace();
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
	}
	
	private void extractValuesFromDir(String directory) {
		String [] file = new File(directory).list();
		int i;
		
		for (i = 0; i < file.length; i++) {
			extractValuesFromFile(directory + "\\" + file[i]);
		}
	}
	
	private int getY(int virtpos) {
		return appletheight - (strheight * virtpos);
	}
	
	public void paint(Graphics g) {
		g.drawImage(imagebuffer,0,0,this);
	}
	
	public void update (Graphics g) {
		paint(g);
	}
	
	private void pause (int milisec) {
		try {
			Thread.sleep(milisec);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void run() {
		TextLine currentline;
		Iterator<News> it;
		Iterator<TextLine> itlines;
		News n;
		
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		while (true) {
			
			pause(scrolldelay);
			
			if (background != null) {
			    for (int i = 0; i < this.getWidth(); i+=bgwidth)
				    for (int j = 0; j < this.getHeight(); j+=bgheight)
					    gbg.drawImage(background,i,j,this);
				//gbg.drawImage(background,0,0,this);
			}
			else {
				gbg.setColor(bgcolor);
				gbg.fillRect(0,0,appletwidth,appletheight);
			}
					
		
			//News iterator
			it = newscollection.iterator();
			
			while (it.hasNext()) {
				// First, draw the title
				n = (News)it.next();
				
				itlines = n.getTitle().iterator();
				
				if (mousex != 0 && mousey != 0) {
				    News nh = checkPosition(mousex, mousey);
				    if (nh != null) {
				    	getAppletContext().showStatus(nh.getURL());
				    }
				    else {
				    	getAppletContext().showStatus("");
				    }
				}
				
				while (itlines.hasNext()) {
					currentline = (TextLine)itlines.next();
					if (n.getCurrentColor() == ON)
						gbg.setColor(new Color(n.getTitleHighlightColor()));
					else 
						gbg.setColor(new Color(n.getTitleColor()));
				    gbg.setFont(n.getTitleFont());
				    gbg.drawString(currentline.getText(),1,currentline.getY());
				    currentline.setY(currentline.getY() - 1);
				}
				
				//Then, the contents
				itlines = n.getContents().iterator();
				while (itlines.hasNext()) {
					currentline = (TextLine)itlines.next();
					if (n.getCurrentColor() == ON)
						gbg.setColor(new Color(n.getContentsHighlightColor()));
					else
						gbg.setColor(new Color(n.getContentsColor()));
					gbg.setFont(n.getContentsFont());
					gbg.drawString(currentline.getText(),1,currentline.getY());
					currentline.setY(currentline.getY() - 1);
				}
			}
			
			Yposition--;
						
			
			//Check if the current top news has arrived to the top. If so, stop for some time and
			//then change the current top news to the next one or to the first one if this is the
			//last one.
			
			if (Yposition < topmargin){				
				if (curtop + 1 >= newscollection.size()) {
					
					if (!stopflag){
						pause(pausetime);
						stopflag = true;
					}
			
					n = (News)newscollection.elementAt(curtop);
					Vector<TextLine> tmp2 = n.getContents();
					TextLine tmp3 = (TextLine)tmp2.elementAt(tmp2.size() - 1);
					
					Yposition = tmp3.getY();
					//if (Yposition < -((n.getTitle().size() * titleheight) + (n.getContents().size() * strheight))) {
					if (Yposition < 0) {
						curtop = 0;
						stopflag = false;
						tmp2 = n.getTitle();
						tmp3 = (TextLine)tmp2.elementAt(0);
						
						Yposition = appletheight + titleheight;
						
						it = newscollection.iterator();
						
						while (it.hasNext()) {
						    n = (News)it.next();
						
						    itlines = n.getTitle().iterator();
						    while (itlines.hasNext()) {
							    currentline = (TextLine)itlines.next();
							    currentline.setY(currentline.getOriginalY());
						    }
						
						    itlines = n.getContents().iterator();
						    while (itlines.hasNext()) {
							    currentline = (TextLine)itlines.next();
							    currentline.setY(currentline.getOriginalY());
						    }
					    }
					}
					
				}
				else {
					pause(pausetime);
					n = (News)newscollection.elementAt(++curtop);
					Vector<TextLine> tmp1 = n.getTitle();
				    currentline = (TextLine)tmp1.elementAt(0);
				    Yposition = currentline.getY() + titleheight;
				}
			 
			}
			
			repaint();
					
		}
	}
	
	private void setHighlight(News n, int state) {
		n.setCurrentColor(state);
	}
	
	private void drawOnPlace(News n) {
		Iterator<TextLine> it;
		TextLine l;
		
		it = n.getTitle().iterator();
		
		while (it.hasNext()) {
			l = it.next();
			if (n.getCurrentColor() == ON)
				gbg.setColor(new Color(n.getTitleHighlightColor()));
			else
		        gbg.setColor(new Color(n.getTitleColor()));
			gbg.setFont(n.getTitleFont());
			gbg.drawString(l.getText(),1,l.getY() + 1);
		}
		
		it = n.getContents().iterator();
		
		while (it.hasNext()) {
			l = it.next();
			if (n.getCurrentColor() == ON)
				gbg.setColor(new Color(n.getContentsHighlightColor()));
			else
		        gbg.setColor(new Color(n.getContentsColor()));
			gbg.setFont(n.getContentsFont());
			gbg.drawString(l.getText(),1,l.getY() + 1);
		}
		
		repaint();
	}
	
	private News checkPosition(int x, int y) {
		News n;
		TextLine txtl;
		Iterator<News> it;
		Vector<TextLine> lines;
		int titlex, titley, lastx, lasty, i = 0;
		
		it = newscollection.iterator();
		while (it.hasNext()) {
			n = (News)it.next();
			if (!n.getURL().equals("nolink")) {
			    lines = n.getTitle();
			    txtl = lines.elementAt(0);
			
			    titlex = txtl.getX();
			    titley = txtl.getY();
			
			    lines = n.getContents();
			    txtl = lines.elementAt(lines.size() - 1);
			
			    lastx = txtl.getX();
			    lasty = txtl.getY();
			
			    if ((x >= 0) && (x <= appletwidth) && (y >= titley) && (y <= lasty)) {
				    setCursor(new Cursor(Cursor.HAND_CURSOR));
				    setHighlight(n, ON);
				    currenthighlighted = i;
				    return n;
			    }
			    else {
				    setHighlight(n, OFF);
			    }
			
			    i++;
			}
		}
		
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		currenthighlighted = -1;
		return null;
	}
	
	//Mouse implementation
	
	public void mouseEntered(MouseEvent me) {
		News n;
		mousex = me.getX();
		mousey = me.getY();
		
		if ((n = checkPosition(mousex, mousey)) != null) {
			if (!n.getURL().equals("nolink")) {
				getAppletContext().showStatus(n.getURL());
			}
			
			drawOnPlace(n);
		}
		else {
			getAppletContext().showStatus("");
		}
	}
	
	public void mouseExited(MouseEvent me) {
		mousex = me.getX();
		mousey = me.getY();
		
		checkPosition(mousex,mousey);
					
		getAppletContext().showStatus("");
		
		mousex = mousey = 0;
		
	}
	
	public void mouseClicked(MouseEvent me) {
		News n = checkPosition(mousex, mousey);
		
		if (me.getButton() == MouseEvent.BUTTON1) {
    		if (n != null) {
	    		try {
		    	    getAppletContext().showDocument(new URL(n.getURL()), n.getTarget());
			    }
			    catch (MalformedURLException urle) {
				    System.err.println(urle.getMessage());
			    }
		    }
		}
	}
	
	public void mousePressed(MouseEvent me) {
		
	}
	
	public void mouseReleased(MouseEvent me) {
		
	}
	
	public void mouseDragged(MouseEvent me) {
		
	}
	
	public void mouseMoved(MouseEvent me) {
		News n;
		mousex = me.getX();
		mousey = me.getY();
		
		if ((n = checkPosition(mousex, mousey)) != null) {
			if (!n.getURL().equals("nolink")) {
				getAppletContext().showStatus(n.getURL());
			}
		}
		else {
			getAppletContext().showStatus("");
		}
	}
}