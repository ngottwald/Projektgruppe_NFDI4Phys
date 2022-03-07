package main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import adapter.ParseRdf;
import input.Input;
import output.Output;
import visual.Visual;

class AdapterWindow {
	JFrame frame = new JFrame();
	
	JButton importSifButton = new JButton("Import experiment files");
	JButton openFileChooserButton = new JButton("Select experiment files");
	JButton convertButton = new JButton("Create RDF-File");
	JButton uploadButton = new JButton("Upload");
	JButton visualButton = new JButton("Show visualization");
	JButton importButton = new JButton("Import experiment");
	JLabel lbUni = new JLabel("University");
	JTextField exUni = new JTextField("");
	JLabel lbRm = new JLabel("Room number");
	JTextField exRoom = new JTextField("");
	JLabel lbMb = new JLabel("Team members");
	JTextField exMember = new JTextField("");
	JLabel lbImport = new JLabel("Import experiment from database");
	JTextField importQuery = new JTextField("");
	
	JLabel filePathLabel = new JLabel();
	
	String sifFilePath = null;
	
	String rdfFilePath = null;
	
	JPanel panel = new JPanel();
	
	BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
	
	AdapterWindow(){
		prepareGUI();
	}
	
	class onClickHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == openFileChooserButton){
            	JFileChooser chooser = new JFileChooser();
 
                int chooserResult = chooser.showOpenDialog(null);
                
                if(chooserResult == JFileChooser.APPROVE_OPTION)
                {   
                	sifFilePath = chooser.getSelectedFile().getAbsolutePath();
                	importSifButton.setEnabled(true);
                	filePathLabel.setText(sifFilePath);
                	
                	System.out.println("Die zu öffnende Datei ist: " +
                            sifFilePath);
                }
            } else if(e.getSource() == importSifButton) {
            	try {
//            	ProcessBuilder builder = new ProcessBuilder(
//                        "cmd.exe", "/c", "cd \"C:\\\\Users\\\\josef\\\\workspace\\\\Projektgruppe_NFDI4Phys\\\\SIFReaderSDK\\\\x64\\\\Debug\" && .\\SIFREADERSDK.exe \"" + sifFilePath + "\"");
            		String timestamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            		new File("input\\experiments\\"+timestamp+"\\").mkdirs();
            		ProcessBuilder builder = new ProcessBuilder(
                            "cmd.exe", "/c", "cd \"resources\" && .\\SIFREADERSDK.exe \"" + sifFilePath + "\"  ..\\input\\experiments\\"+timestamp+"\\sif.xml");
            		Process p = builder.start();
                    sifFilePath = null;
                    filePathLabel.setText(sifFilePath);
                    importSifButton.setEnabled(false);
                    convertButton.setEnabled(true);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
						e1.printStackTrace();
					}
            } else if(e.getSource() == convertButton) {
            	try {
        			Input input = new Input(exUni.getText(), exRoom.getText(), exMember.getText());
        			exUni.setText("");
        			exRoom.setText("");
        			exMember.setText("");
        			input.input("input/experiments");
        			Output output = new Output();
        			output.output(input.getExperiment(), "output/rdf//");
        			rdfFilePath = "output/rdf/" + input.getExperiment().getName();
        			System.out.println("RDF-File generated");
        			uploadButton.setEnabled(true);
        			visualButton.setEnabled(true);
        		} catch (ParserConfigurationException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		} catch (SAXException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		} catch (IOException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		}           	
            } else if(e.getSource() == uploadButton) {
            	try {
            		ProcessBuilder builder = new ProcessBuilder(
                            "cmd.exe", "/c", "python main.py 1 output/rdf/rdfFilePath" + ".owl");
            		Process p = builder.start();
        			System.out.println("RDF-File uploaded");
        		}  catch (IOException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		}           	
            } else if(e.getSource() == visualButton) {
            	System.out.println(rdfFilePath + ".owl");
            	ParseRdf rdf = null;
        		try {			
        			rdf = new ParseRdf(rdfFilePath + ".owl");
        		}  catch (Exception ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		}
        		
        		try {
					Visual v = new Visual(rdf.getExperiment());
			    File f = new File("output/png/" + rdf.getExperiment().getName() + ".png");
				    Desktop dt = Desktop.getDesktop();
				    dt.open(f);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } else if(e.getSource() == importButton) {
            	try {
            		ProcessBuilder builder = new ProcessBuilder(
                            "cmd.exe", "/c", "python main.py 2 \"FIND RECORD " + importQuery.getText() + "\" import/" + importQuery.getText() + ".rdf");
            		Process p = builder.start();
            		importQuery.setText("");
        			System.out.println("RDF-File imported");
        		}  catch (IOException ex) {
        			// TODO Auto-generated catch block
        			ex.printStackTrace();
        		}           	
            }
        }
    } 
	
	public void prepareGUI() {
		frame.setTitle("NFDI4Phys");
		frame.setSize(500, 350);
		
		panel.setLayout(layout);
		
		panel.add(openFileChooserButton);
		panel.add(filePathLabel);
		panel.add(importSifButton);
		panel.add(lbUni);
		panel.add(exUni);
		panel.add(lbRm);
		panel.add(exRoom);
		panel.add(lbMb);
		panel.add(exMember);
		panel.add(convertButton);
		panel.add(visualButton);
		panel.add(uploadButton);
		panel.add(new JSeparator());
		panel.add(new JSeparator());
		panel.add(lbImport);
		panel.add(importQuery);
		frame.getContentPane().add(panel);
		panel.add(importButton);
		panel.add(importQuery);
		convertButton.setEnabled(false);
		uploadButton.setEnabled(false);
		visualButton.setEnabled(false);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		openFileChooserButton.addActionListener(new onClickHandler());
		importSifButton.addActionListener(new onClickHandler());
		importSifButton.setEnabled(false);
		convertButton.addActionListener(new onClickHandler());
		uploadButton.addActionListener(new onClickHandler());
		visualButton.addActionListener(new onClickHandler());
		importButton.addActionListener(new onClickHandler());
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		new AdapterWindow();
        
//		try {
//			Input input = new Input("Uni-Siegen", "H-C3306", "J.Matwich,N.Gottwald");
//			input.input("input/experiments");
//			Output output = new Output();
//			output.output(input.getExperiment(), "output/rdf//");
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ParseRdf rdf = null;
//		try {			
//			rdf = new ParseRdf("output/rdf/0000.owl");
//			//rdf.printExperiment();
//		}  catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Visual v = new Visual(rdf.getExperiment());
	}		
}
