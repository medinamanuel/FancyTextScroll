package org.mmg.java.fancytextscroll.newscreator;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JPanel;

/**
 * 
 * @author Manuel Medina González
 *
 */

public class FillAppletInfo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SEPARATOR = "|";
	private JButton btntitlecolor;
	private JButton btnthcolor;
	private JButton btnnewscolor;
	private JButton btnnhcolor;
	private JButton btnsave;
	private JButton btncancel;

	private Color titlecolor;
	private Color titlehighlightcolor;
	private Color newscolor;
	private Color newshighlightcolor;
	private JComboBox<String> jcbfonts, jcbnfonts;
	private JComboBox<String> jcbstyle, jcbnstyle; 
	private JComboBox<String> jcbsize, jcbnsize;
	private JComboBox<String> jcbtarget;
	private JTextField txttitle;
	private JTextField txtnews;
	private JTextField txtlink;
	private JLabel jltitlecolor, jlthcolor, jltfont, jltstyle, jltsize, jlttext, jltcsel, jlthcsel;
	private JLabel jlnewscolor, jlnhcolor, jlnfont, jlnstyle, jlnsize, jlntext, jlncsel, jlnhcsel, jlnurl, jlntarget;
	private int newscount;
	private File thefile;
	
	public FillAppletInfo() {
		super ("\"De ultimo minuto\"");
		
		int i;
		newscount = 0;
		//File f = new File ("applet.settings");
		/*
		if (f.exists()) {
			f.delete();
		}*/
		
		titlehighlightcolor = Color.BLUE;
		titlecolor = Color.RED;
		newshighlightcolor = Color.RED;
		newscolor = Color.BLUE;
		
		JPanel jptitle = new JPanel();
		JPanel jpnews = new JPanel();
		JPanel jpbuttons = new JPanel();
		JPanel tcolor = new JPanel();
		JPanel thcolor = new JPanel();
		JPanel ncolor = new JPanel();
		JPanel nhcolor = new JPanel();
		
		TitledBorder tb1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Titulo");
		TitledBorder tb2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Noticia");
		TitledBorder tb3 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Acciones");
		
		jptitle.setBorder(tb1);
		jpnews.setBorder(tb2);
		jpbuttons.setBorder(tb3);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String availfonts [] = ge.getAvailableFontFamilyNames();
		jcbfonts = new JComboBox<String>();
		jcbnfonts = new JComboBox<String>();
		
		for (i = 0; i < availfonts.length; i++) {
			jcbfonts.addItem(availfonts[i]);
			jcbnfonts.addItem(availfonts[i]);
		}
		
		jcbstyle = new JComboBox<String>();
		jcbnstyle = new JComboBox<String>();
		
		jcbstyle.addItem("Negritas");
		jcbstyle.addItem("Normal");
		jcbstyle.addItem("Cursiva");
		jcbnstyle.addItem("Normal");
		jcbnstyle.addItem("Negritas");
		jcbnstyle.addItem("Cursiva");
		
		jcbsize = new JComboBox<String>();
		jcbnsize = new JComboBox<String>();
		for (i = 10; i <= 20; i++) {
			jcbsize.addItem(String.valueOf(i));
			jcbnsize.addItem(String.valueOf(i-2));
		}
		
		jcbtarget = new JComboBox<String>();
		jcbtarget.addItem("Nueva ventana");
		jcbtarget.addItem("Misma ventana");
		
		jltfont = new JLabel("Fuente:");
		jltstyle = new JLabel("Estilo:");
		jltsize = new JLabel("Tamano");
		jlttext = new JLabel("Texto:");
		jlnfont = new JLabel("Fuente:");
		jlnstyle = new JLabel("Estilo:");
		jlnsize = new JLabel("Tamano");
		jlntext = new JLabel("Texto:");
		jltitlecolor = new JLabel("Color:");
		jlthcolor = new JLabel("Resalte:");
		jlnewscolor = new JLabel("Color:");
		jlnhcolor = new JLabel("Resalte:");
		jltcsel = new JLabel("Muestra");
		jltcsel.setForeground(titlecolor);
		jlthcsel = new JLabel("Muestra");
		jlthcsel.setForeground(titlehighlightcolor);
		jlncsel = new JLabel("Muestra");
		jlncsel.setForeground(newscolor);
		jlnhcsel = new JLabel("Muestra");
		jlnhcsel.setForeground(newshighlightcolor);
		jlnurl = new JLabel("Liga:");
		jlntarget = new JLabel("Muestra en:");
		
		btntitlecolor = new JButton("Seleccionar...");
		btntitlecolor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					titlecolor = JColorChooser.showDialog(getParent(),"Color del texto", Color.BLACK);
					if (titlecolor == null) {
						titlecolor = Color.RED;
					}
					
					jltcsel.setForeground(titlecolor);
				}
			}
		});
		
		btnthcolor = new JButton("Seleccionar...");
		btnthcolor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					titlehighlightcolor = JColorChooser.showDialog(getParent(),"Color del texto", Color.BLACK);
					if (titlehighlightcolor == null) {
						titlehighlightcolor = Color.BLUE;
					}
					
					jlthcsel.setForeground(titlehighlightcolor);
				}
			}
		});
		
		btnnewscolor = new JButton("Seleccionar...");
		btnnewscolor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					newscolor = JColorChooser.showDialog(getParent(),"Color del texto", Color.BLACK);
					if (newscolor == null) {
						newscolor = Color.BLUE;
					}
					
					jlncsel.setForeground(newscolor);
				}
			}
		});
		
		btnnhcolor = new JButton("Seleccionar...");
		btnnhcolor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					newshighlightcolor = JColorChooser.showDialog(getParent(),"Color del texto", Color.BLACK);
					if (newshighlightcolor == null) {
						newshighlightcolor = Color.RED;
					}
					
					jlnhcsel.setForeground(newshighlightcolor);
				}
			}
		});
		
		btnsave = new JButton("Agregar noticia");
		btnsave.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					if (!txttitle.getText().equals("") && !txtnews.getText().equals("")) {
						try {
							//File f = new File ("applet.settings");
							
							FileWriter file = new FileWriter(getFile(), true);
							StringBuffer towrite = new StringBuffer();
							String attrname = "";
							String value = "";
							
							if (getFile().getName().substring(getFile().getName().lastIndexOf(".")).equals(".xml")) {
								if (getFile().length() == 0) {
									//	If the file is empty we must add the header and the reference to the DTD
									towrite.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
									towrite.append("\n");
									towrite.append("<!DOCTYPE newscollection SYSTEM \"newscollection.dtd\">");
									towrite.append("\n");
									towrite.append("<newscollection>");
									towrite.append("\n");
								}
								
								towrite.append("<news>\n");
								
								/* The title */
								towrite.append("<title ");
								attrname = "tfname=";
								value= "\"" + (String)jcbfonts.getSelectedItem() + "\"";
								towrite.append(attrname + value + " ");
								
								attrname = "tftype=";
								value = (String)jcbstyle.getSelectedItem();
								
								if (value.equals("Normal")) {
									value = "\"PLAIN\"";
								}
								else {
									if (value.equals("Negritas")) {
										value = "\"BOLD\"";
									}
									else {
										value = "\"ITALIC\"";
									}
								}
								
								towrite.append(attrname + value + " ");
								
								attrname = "tfsize=";
								value = "\"" + (String)jcbsize.getSelectedItem() + "\"";
								
								towrite.append(attrname + value + " ");
								
								attrname = "thcolor=";
								value = int2Hex(titlehighlightcolor.getRed()) + int2Hex(titlehighlightcolor.getGreen()) + int2Hex(titlehighlightcolor.getBlue());
								value = "\"" + value + "\"";
								
								towrite.append(attrname + value + " ");
								
								attrname = "tcolor=";
								value = int2Hex(titlecolor.getRed()) + int2Hex(titlecolor.getGreen()) + int2Hex(titlecolor.getBlue());
								value = "\"" + value + "\"";
								
								towrite.append(attrname + value);
								
								towrite.append(">\n");
								
								towrite.append(txttitle.getText() + "\n");
								
								towrite.append("</title>\n");
								
								/* The contents */
								
								towrite.append("<contents ");
								
								attrname = "cfname=";
								value= "\"" + (String)jcbnfonts.getSelectedItem() + "\"";
								towrite.append(attrname + value + " ");
								
								attrname = "cftype=";
								value = (String)jcbnstyle.getSelectedItem();
								
								if (value.equals("Normal")) {
									value = "\"PLAIN\"";
								}
								else {
									if (value.equals("Negritas")) {
										value = "\"BOLD\"";
									}
									else {
										value = "\"ITALIC\"";
									}
								}
								
								towrite.append(attrname + value + " ");
								
								attrname = "cfsize=";
								value = "\"" + (String)jcbnsize.getSelectedItem() + "\"";
								
								towrite.append(attrname + value + " ");
								
								attrname = "chcolor=";
								value = int2Hex(newshighlightcolor.getRed()) + int2Hex(newshighlightcolor.getGreen()) + int2Hex(newshighlightcolor.getBlue());
								value = "\"" + value + "\"";
								
								towrite.append(attrname + value + " ");
								
								attrname = "ccolor=";
								value = int2Hex(newscolor.getRed()) + int2Hex(newscolor.getGreen()) + int2Hex(newscolor.getBlue());
								value = "\"" + value + "\"";
								
								towrite.append(attrname + value);
								
								towrite.append(">\n");
								
								towrite.append(txtnews.getText() + "\n");
								
								towrite.append("</contents>\n");
								
								if (!txtlink.getText().equals("")) {
									towrite.append("<link ");
									attrname = "target=";
									
									value = (String)jcbtarget.getSelectedItem();
									
									if (value.equals("Misma ventana")) {
										value = "_self";
									}
									else {
										value = "_blank";
									}
									
									towrite.append(attrname + "\"" + value + "\"");
									towrite.append(">\n");
																
									towrite.append(txtlink.getText() + "\n");

									towrite.append("</link>\n");
									
								}
								
								towrite.append("</news>\n");
								
							}
							else {
								// Doesn't matter if the file is empty.
								
								if (newscount != 0) {
									towrite.append("\n");
								}
								
								value = (String)jcbfonts.getSelectedItem();
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = (String)jcbstyle.getSelectedItem();
								
								if (value.equals("Normal")) {
									value = "PLAIN";
								}
								else {
									if (value.equals("Negritas")) {
										value = "BOLD";
									}
									else {
										value = "ITALIC";
									}
								}
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = (String)jcbsize.getSelectedItem();
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = int2Hex(titlehighlightcolor.getRed()) + int2Hex(titlehighlightcolor.getGreen()) + int2Hex(titlehighlightcolor.getBlue());
															
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = int2Hex(titlecolor.getRed()) + int2Hex(titlecolor.getGreen()) + int2Hex(titlecolor.getBlue());
															
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								towrite.append(txttitle.getText());
								towrite.append(SEPARATOR);
								
								value = (String)jcbnfonts.getSelectedItem();
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = (String)jcbnstyle.getSelectedItem();
								
								if (value.equals("Normal")) {
									value = "PLAIN";
								}
								else {
									if (value.equals("Negritas")) {
										value = "BOLD";
									}
									else {
										value = "ITALIC";
									}
								}
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = (String)jcbnsize.getSelectedItem();
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = int2Hex(newshighlightcolor.getRed()) + int2Hex(newshighlightcolor.getGreen()) + int2Hex(newshighlightcolor.getBlue());
															
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								value = int2Hex(newscolor.getRed()) + int2Hex(newscolor.getGreen()) + int2Hex(newscolor.getBlue());
								
								towrite.append(value);
								towrite.append(SEPARATOR);
								
								towrite.append(txtnews.getText());
															
								if (!txtlink.getText().equals("")) {
									towrite.append(SEPARATOR);
									towrite.append(txtlink.getText());
									towrite.append(SEPARATOR);
									value = (String)jcbtarget.getSelectedItem();
									if (value.equals("Misma ventana")) {
										value = "_self";
									}
									else {
										value = "_blank";
									}
									towrite.append(value);
								}
							}
							
							
							
							//if (!f.exists()) {
								//file.write(towrite.toString());
							//}
							//else {
								file.append(towrite.toString());
							//}
							
							file.close();
							newscount++;
							JOptionPane.showMessageDialog(getParent(),"Noticia agregada con exito");
							
							txttitle.setText("");
							txtnews.setText("");
							txtlink.setText("");
							
							txttitle.requestFocus();
							
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
					else {
						
						if (txttitle.equals("")) {
							JOptionPane.showMessageDialog(getParent(), "Escribe el titulo de la noticia antes de guardar", "No hay titulo", JOptionPane.INFORMATION_MESSAGE);
							txttitle.requestFocus();
						}
						else {
							JOptionPane.showMessageDialog(getParent(), "Escribe el contenido de la noticia antes de guardar", "No hay titulo", JOptionPane.INFORMATION_MESSAGE);
							txtnews.requestFocus();
						}
						
					}
				}
			}
		});
		
		btncancel = new JButton("Cerrar");
		btncancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON1) {
					if (getFile().getName().substring(getFile().getName().lastIndexOf(".")).equals(".xml")) {
						try {
							FileWriter fw = new FileWriter(getFile(), true);
							fw.append("</newscollection>");
							fw.close();
						}
						catch (IOException ioe) {
							ioe.printStackTrace();
						}
						 
					}
					
					System.exit(0);
				}
			}
		});
		
		txttitle = new JTextField(25);
		txtnews = new JTextField(25);
		txtlink = new JTextField(25);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		jptitle.setLayout(new GridLayout(6,2));
		jptitle.add(jltfont);
		jptitle.add(jcbfonts);
		jptitle.add(jltstyle);
		jptitle.add(jcbstyle);
		jptitle.add(jltsize);
		jptitle.add(jcbsize);
		jptitle.add(jltitlecolor);
		
		tcolor = new JPanel();
		tcolor.add(btntitlecolor);
		tcolor.add(jltcsel);
		
		jptitle.add(tcolor);
		
		jptitle.add(jlthcolor);
		
		thcolor = new JPanel();
		thcolor.add(btnthcolor);
		thcolor.add(jlthcsel);
		
		jptitle.add(thcolor);
		
		jptitle.add(jlttext);
		
		jptitle.add(txttitle);
		
		getContentPane().add(jptitle);
		
		jpnews.setLayout(new GridLayout(8,2));
		
		jpnews.add(jlnfont);
		jpnews.add(jcbnfonts);
		jpnews.add(jlnstyle);
		jpnews.add(jcbnstyle);
		jpnews.add(jlnsize);
		jpnews.add(jcbnsize);
		
		jpnews.add(jlnewscolor);
		
		ncolor = new JPanel();
		
		ncolor.add(btnnewscolor);
		ncolor.add(jlncsel);
		
		jpnews.add(ncolor);
		
		jpnews.add(jlnhcolor);
		
		nhcolor = new JPanel();
		
		nhcolor.add(btnnhcolor);
		nhcolor.add(jlnhcsel);
		
		jpnews.add(nhcolor);
		
		jpnews.add(jlntext);
		jpnews.add(txtnews);
		jpnews.add(jlnurl);
		jpnews.add(txtlink);
		jpnews.add(jlntarget);
		jpnews.add(jcbtarget);
		
		getContentPane().add(jpnews);
		
		jpbuttons.add(btnsave);
		jpbuttons.add(btncancel);
		
		getContentPane().add(jpbuttons);
		
		setSize(400,650);
		setResizable(false);
		
		setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - (int)getWidth() / 2, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - (int)getHeight() / 2);
	}
	
	private void setFile(File f) {
		
		if (f.exists() || f.getName().substring(f.getName().lastIndexOf(".")).equals(".xml")) {
			BufferedReader bf = null;
			try {
				//String ofilename = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(System.getProperty("file.separator")) + 1);
				String ofilename = f.getAbsolutePath();
				
				File newfile = new File (f.getAbsolutePath() + ".temp");
				FileWriter fw = new FileWriter(newfile);
				bf = new BufferedReader(new FileReader(f));
				String theline = bf.readLine();
				
				while (!theline.equals("</newscollection>")) {
					fw.write(theline + "\n");
					theline = bf.readLine();
				}
				
				fw.close();
				
				//Erase the original file
				f.delete();
				
				//Rename the temporal file like the original file
				newfile.renameTo(new File(ofilename));
				thefile = newfile;
								
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (bf != null)
						bf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		else {
			thefile = f;
		}
	}
	
	private final File getFile() {
		return thefile;
	}
	
	private String int2Hex(int value) {
		String retval = "";
		
		switch ((int)(value / 16)) {
		case 10:
			retval = "A";
			break;
			
		case 11:
			retval = "B";
			break;
		
		case 12:
			retval = "C";
			break;
			
		case 13:
			retval = "D";
			break;
		
		case 14:
			retval = "E";
			break;
			
		case 15:
			retval = "F";
			break;
			
		default:
			retval = String.valueOf(value / 16);
		}
		
		switch ((int)(value % 16)) {
		case 10:
			retval += "A";
			break;
			
		case 11:
			retval += "B";
			break;
		
		case 12:
			retval += "C";
			break;
			
		case 13:
			retval += "D";
			break;
		
		case 14:
			retval += "E";
			break;
			
		case 15:
			retval += "F";
			break;
			
		default:
			retval += String.valueOf(value / 16);
		}
		
		return retval;
	}
	
	public static void main (String args[]) {
		setDefaultLookAndFeelDecorated(true);
		
		FillAppletInfo fap = new FillAppletInfo();
		
		JFileChooser jfs = new JFileChooser();
		XMLCollectionFilter xmlfilter = new XMLCollectionFilter();
		NewsCollectionFilter nwcfilter = new NewsCollectionFilter();
		
		
		jfs.setDialogType(JFileChooser.SAVE_DIALOG);
		jfs.addChoosableFileFilter(xmlfilter);
		jfs.addChoosableFileFilter(nwcfilter);
		
		
		if (jfs.showSaveDialog(fap) == JFileChooser.APPROVE_OPTION) {
			fap.setFile(jfs.getSelectedFile());
			fap.setVisible(true);
		}
		else
			// Need to send a message to the user
			System.exit(1);
	}

}

class NewsCollectionFilter extends FileFilter {
		
	public boolean accept(File f) {
		String extension = "";
		
		if (f.isDirectory())
			return true;
		
		extension = f.getName().substring(f.getName().lastIndexOf("."));
		
		if (extension.equals(".nwc"))
			return true;
		else
			return false;
	}
	
	public String getDescription() {
		return "News Collection Formatted File";
	}
}

class XMLCollectionFilter extends FileFilter {
	public boolean accept(File f) {
		String extension = "";
		
		if (f.isDirectory())
			return true;
		
		extension = f.getName().substring(f.getName().lastIndexOf("."));
		
		if (extension.equals(".xml"))
			return true;
		else
			return false;
	}
	
	public String getDescription() {
		return "News Collection XML File";
	}
}