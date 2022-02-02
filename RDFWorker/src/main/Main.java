package main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import adapter.ParseRdf;
import input.Input;
import output.Output;
import visual.Visual;

class AdapterWindow {
	JFrame frame = new JFrame();
	
	JButton importSifButton = new JButton("Import sif file");
	JButton openFileChooserButton = new JButton("Select sif file");
	
	JLabel filePathLabel = new JLabel();
	
	String sifFilePath = null;
	
	JPanel panel = new JPanel();	
	
	AdapterWindow(){
		prepareGUI();
	}
	
	class onClickHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == openFileChooserButton){
            	JFileChooser chooser = new JFileChooser();
                // Dialog zum Oeffnen von Dateien anzeigen
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
            	ProcessBuilder builder = new ProcessBuilder(
                        "cmd.exe", "/c", "cd \"C:\\\\Users\\\\josef\\\\workspace\\\\Projektgruppe_NFDI4Phys\\\\SIFReaderSDK\\\\x64\\\\Debug\" && .\\SIFREADERSDK.exe \"" + sifFilePath + "\"");
                    Process p = builder.start();
                    sifFilePath = null;
                    filePathLabel.setText(sifFilePath);
                    importSifButton.setEnabled(false);

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println(e1.getMessage());
						e1.printStackTrace();
					}
            }
        }
    } 
	
	public void prepareGUI() {
		frame.setTitle("NFDI4Phys");
		frame.setSize(500, 200);
		
		panel.add(openFileChooserButton);
		panel.add(filePathLabel);
		panel.add(importSifButton);
		frame.getContentPane().add(panel);		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		openFileChooserButton.addActionListener(new onClickHandler());
		importSifButton.addActionListener(new onClickHandler());
		importSifButton.setEnabled(false);;
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		new AdapterWindow();
        
		try {
			Input input = new Input();
			input.input("input/experiments");
			Output output = new Output();
			output.output(input.getExperiment(), "output//");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ParseRdf rdf = new ParseRdf("output/2021-11-15.owl");
		rdf.printExperiment();
		
		// Visual v = new Visual(rdf.getExperiment());
	}		
}
