package nodeP;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import fileManagers.FileData;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class NodeGUI {
	public static Object frame;
	public JTextField textField;
	private JTextField textField_1;
	public String nodenaam;
	
	
	public NodeGUI(){
		
		//TODO functies achter knoppen
		//TODO errormsg nameservertimeout, nodenaam al in gebruik 
		//TODO eventueel aantal nodes
		
		JFrame nameframe = new JFrame();
		nameframe.setTitle("Node startup");
		nameframe.getContentPane().setForeground(Color.BLACK);
		nameframe.setResizable(true);
		nameframe.getContentPane().setBackground(Color.WHITE);
		nameframe.setBackground(Color.WHITE);
		nameframe.setBounds(20, 20, 300, 300);
		nameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nameframe.getContentPane().setLayout(null);
               
        JTextField NN = new JTextField();
        NN.setBounds(142, 80, 132, 20);
        nameframe.getContentPane().add(NN);
        NN.setColumns(10);
        
        JTextPane txtpnGeefDeNodenaam = new JTextPane();
        txtpnGeefDeNodenaam.setEditable(false);
        txtpnGeefDeNodenaam.setText("Geef de nodenaam in:");
        txtpnGeefDeNodenaam.setBounds(10, 80, 132, 20);
        nameframe.getContentPane().add(txtpnGeefDeNodenaam);  
        
        JFrame nodeframe = new JFrame();
		nodeframe.getContentPane().setForeground(Color.BLACK);
		nodeframe.setResizable(true);
		nodeframe.getContentPane().setBackground(Color.WHITE);
		nodeframe.setBackground(Color.WHITE);
		nodeframe.setBounds(20, 20, 700, 300);
		nodeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodeframe.getContentPane().setLayout(null);
		
		
		nameframe.setVisible(true);
        JButton btnStartNode = new JButton("START Node");
        btnStartNode.setBounds(90, 140, 120, 23);
        nameframe.getContentPane().add(btnStartNode);
        btnStartNode.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		nodenaam = NN.getText();
        		if(nodenaam.contains(" "))
        		{
        			JTextField errortext = new JTextField();
        			errortext.setForeground(Color.RED);
        			errortext.setText(nodenaam + " is geen geldige nodenaam.");
        			errortext.setFont(new Font("Tahoma", Font.BOLD, 13));
        			errortext.setBorder(null);
        	        errortext.setBounds(10, 200, 300, 20);
        	        errortext.setColumns(10);        			
        	        nameframe.getContentPane().add(errortext);      			
        		}
        		else
        		{	
        			//created node
        			nameframe.setVisible(false);
        			nodeframe.setTitle("Node " + nodenaam);
        			
        			StartNode node1=new StartNode(nodenaam);
        			node1.startNewNode();
        			
        			JTextPane Nodenaam = new JTextPane();
        			Nodenaam.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        Nodenaam.setText("Naam: " +nodenaam);
        	        Nodenaam.setBounds(5, 5, 180, 20);
        	        nodeframe.getContentPane().add(Nodenaam);
        	        
        	        JTextPane Hash = new JTextPane();
        	        Hash.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        Hash.setText("| Hash: " +node1.nodedata1.getMyNodeID());
        	        Hash.setBounds(190, 5, 100, 20);
        	        nodeframe.getContentPane().add(Hash);
        	        
        	        JTextPane IP = new JTextPane();
        	        IP.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        IP.setText("| IP: " +node1.nodedata1.getMyIP());
        	        IP.setBounds(295, 5, 150, 20);
        	        nodeframe.getContentPane().add(IP);
        	        
        	        JTextPane NameIP = new JTextPane();
        	        NameIP.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        NameIP.setText("| NameServer IP: " +node1.nodedata1.getNameServerIP());
        	        NameIP.setBounds(450, 5, 300, 20);
        	        nodeframe.getContentPane().add(NameIP);
        	        
        	        JTextPane line = new JTextPane();
        	        line.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        line.setText("-----------------------------------------------------------------------------------------------------------------------------------------");
        	        line.setBounds(0, 14, 700, 20);
        	        nodeframe.getContentPane().add(line);
        	        
        	        JTextPane filelijst = new JTextPane();
        	        filelijst.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        filelijst.setText("Own Files:");
        	        filelijst.setBounds(5, 30, 100, 20);
        	        nodeframe.getContentPane().add(filelijst);
        	        
        	        ArrayList<FileData> tempLocalFiles = node1.nodedata1.localFiles;
        	        String[] localFileNames = new String[tempLocalFiles.size()];
        	        if(tempLocalFiles.size() != 0)
        	        {
        	        	for(int i=0;i<tempLocalFiles.size();i++)
        	        	{
        	        		System.out.println("test");
        	        		localFileNames[i] = tempLocalFiles.get(i).getFileName();
        	        	}
        	        }
        	        JList<String> displayList = new JList<>(localFileNames);
        	        JScrollPane ownfile = new JScrollPane(displayList);
        	        ownfile.setBounds(5, 50, 220, 230);
        	        ownfile.setBackground(Color.WHITE);
        	        nodeframe.getContentPane().add(ownfile);
        	        
        	        JTextPane allfiles = new JTextPane();
        	        allfiles.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        allfiles.setText("All Files:");
        	        allfiles.setBounds(230, 30, 100, 20);
        	        nodeframe.getContentPane().add(allfiles);
        	        
        	        JScrollPane allfile = new JScrollPane();
        	        allfile.setBounds(230, 50, 220, 230);
        	        allfile.setBackground(Color.WHITE);
        	        nodeframe.getContentPane().add(allfile);
        	        
        	        JButton btnaddFile = new JButton("Add File");
        	        btnaddFile.setBounds(500, 50, 150, 30);
        	        nodeframe.getContentPane().add(btnaddFile);
        	        btnaddFile.addActionListener(new ActionListener() {
        	        	public void actionPerformed(ActionEvent e) {
        	        	}
        	        });
        	        
        	        JButton btnRMFile = new JButton("Remove File");
        	        btnRMFile.setBounds(500, 100 , 150, 30);
        	        nodeframe.getContentPane().add(btnRMFile);
        	        btnRMFile.addActionListener(new ActionListener() {
        	        	public void actionPerformed(ActionEvent e) {
        	        	}
        	        });
        	        
        	        JButton btnDLFile = new JButton("Download File");
        	        btnDLFile.setBounds(500, 150, 150, 30);
        	        nodeframe.getContentPane().add(btnDLFile);
        	        btnDLFile.addActionListener(new ActionListener() {
        	        	public void actionPerformed(ActionEvent e) {
        	        	}
        	        });
        	        
        	        JButton btnQuit = new JButton("Quit Node");
        	        btnQuit.setBounds(500, 200, 150, 30);
        	        nodeframe.getContentPane().add(btnQuit);
        	        btnQuit.addActionListener(new ActionListener() {
        	        	public void actionPerformed(ActionEvent e) {
        	        		node1.nodedata1.setToQuit(true);
        	        		nodeframe.setVisible(false);
        	        		}
        	        });

        	        
        			nodeframe.setVisible(true);

        		}
        	}
        });

	}
	
	
}
