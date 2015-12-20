package nodeGUI;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import nameServer.StartNameServer;
import nodeFileManagers.FileData;
import nodeStart.StartNode;

public class NodeGUI {
	public static Object frame;
	public JFrame nodeframe;
	public JTextField textField;
	public String nodenaam;
	public StartNode node1;
	public JButton btnDLFile;
	public TreeMap<Integer, FileData> tempLocalFiles;
	public TreeMap<Integer, TreeMap<Integer, FileData>> tempAllNetworkFiles;
	public DefaultListModel<String> filelist = new DefaultListModel<String>();
	public DefaultListModel<String> allfilelist = new DefaultListModel<String>();
	public volatile JList<String> displayAllList;
	public volatile JList<String> displayList;
	
	public NodeGUI(){
		
		//startupframe where user can start nameserver and choose name of node
		JFrame nameframe = new JFrame();
		nameframe.setTitle("Node startup");
		nameframe.getContentPane().setForeground(Color.BLACK);
		nameframe.setResizable(false);
		nameframe.getContentPane().setBackground(Color.WHITE);
		nameframe.setBackground(Color.WHITE);
		nameframe.setBounds(20, 20, 300, 160);
		nameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nameframe.getContentPane().setLayout(null);
               
        JTextField NN = new JTextField();
        NN.setBounds(142, 20, 132, 20);
        nameframe.getContentPane().add(NN);
        NN.setColumns(10);
        
        JTextPane txtpnGeefDeNodenaam = new JTextPane();
        txtpnGeefDeNodenaam.setEditable(false);
        txtpnGeefDeNodenaam.setText("Geef de nodenaam in:");
        txtpnGeefDeNodenaam.setBounds(10, 20, 132, 20);
        nameframe.getContentPane().add(txtpnGeefDeNodenaam);  
        
        nodeframe = new JFrame();
		nodeframe.getContentPane().setForeground(Color.BLACK);
		nodeframe.setResizable(false);
		nodeframe.getContentPane().setBackground(Color.WHITE);
		nodeframe.setBackground(Color.WHITE);
		nodeframe.setBounds(20, 20, 700, 500);
		nodeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nodeframe.getContentPane().setLayout(null);
		
		nameframe.setVisible(true);
        JButton btnStartNameserver = new JButton("Start NameServer");
        btnStartNameserver.setBounds(130, 60 , 155, 23);
        nameframe.getContentPane().add(btnStartNameserver);
        btnStartNameserver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	StartNameServer nameserver = null;
				try {
					//start nameserver
					nameserver = new StartNameServer();
					nameserver.startNameServer();
					JTextField started = new JTextField();
					started.setForeground(Color.GREEN);
					started.setText("NameServer started.");
					started.setFont(new Font("Tahoma", Font.BOLD, 13));
					started.setBorder(null);
					started.setBounds(10, 100, 290, 20);
					started.setColumns(10);        			
        	        nameframe.getContentPane().add(started);
				} catch (RemoteException e1) {} catch (IOException e1) {}        		
            }
    });
		
		
		nameframe.setVisible(true);
        JButton btnStartNode = new JButton("Start Node");
        btnStartNode.setBounds(10, 60 , 110, 23);
        nameframe.getContentPane().add(btnStartNode);
        btnStartNode.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		nodenaam = NN.getText();
        		node1=new StartNode(nodenaam);
    			node1.startNewNode();
        		if(nodenaam.contains(" "))
        		{
        			//bad nodename
        			JTextField errortext = new JTextField();
        			errortext.setForeground(Color.RED);
        			errortext.setText(nodenaam + " is geen geldige nodenaam.");
        			errortext.setFont(new Font("Tahoma", Font.BOLD, 13));
        			errortext.setBorder(null);
        	        errortext.setBounds(10, 100, 290, 20);
        	        errortext.setColumns(10);        			
        	        nameframe.getContentPane().add(errortext);
        		}
        		else if(node1.nodedata1.getNumberOfNodesStart() == 0)
        		{
        			//name already exists
        			JTextField errortext = new JTextField();
        			errortext.setForeground(Color.RED);
        			errortext.setText("Name already exists, try another one");
        			errortext.setFont(new Font("Tahoma", Font.BOLD, 13));
        			errortext.setBorder(null);
        	        errortext.setBounds(10, 100, 290, 20);
        	        errortext.setColumns(10);        			
        	        nameframe.getContentPane().add(errortext);
        		}
        		else if(node1.nodedata1.getNumberOfNodesStart() >= 1 )
        		{	
        			//good name, node starts
        			nameframe.setVisible(false);
        			nodeframe.setTitle("Node " + nodenaam);
        			
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
        	        
        	        JTextPane allfiles = new JTextPane();
        	        allfiles.setFont(new Font("Tahoma", Font.BOLD, 13));
        	        allfiles.setText("All Files:");
        	        allfiles.setBounds(230, 30, 100, 20);
        	        nodeframe.getContentPane().add(allfiles);       	        
        	        
        	        //refresh button, not needed because list refreshes automatically
        	        /*JButton btnaddFile = new JButton("Refresh Files");
                    btnaddFile.setBounds(500, 50, 150, 30);
                    nodeframe.getContentPane().add(btnaddFile);
                    btnaddFile.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            		System.out.println("lijstenfixke knop");
                                    generateLists();                                       
                            }
                    });*/
                   
                   
                    JButton btnRMFile = new JButton("Remove File");
                    btnRMFile.setBounds(500, 100 , 150, 30);
                    nodeframe.getContentPane().add(btnRMFile);
                    btnRMFile.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            	//let the user choose which files to remove
                            	JFrame rmframe = new JFrame();
                            	rmframe.setTitle("Remove File");
                            	rmframe.getContentPane().setForeground(Color.BLACK);
                            	rmframe.setResizable(true);
                            	rmframe.getContentPane().setBackground(Color.WHITE);
                            	rmframe.setBackground(Color.WHITE);
                            	rmframe.setBounds(200, 200, 300, 400);
                            	rmframe.setResizable(false);
                            	rmframe.getContentPane().setLayout(null);
                            	
                            	JTextPane dltxtpnfilename = new JTextPane();
                                dltxtpnfilename.setFont(new Font("Tahoma", Font.BOLD, 13));
                                dltxtpnfilename.setEditable(false);
                                dltxtpnfilename.setText("Files to remove: ");
                                dltxtpnfilename.setBounds(5, 10, 280, 20);
                                rmframe.getContentPane().add(dltxtpnfilename);
                                
                                JList<String> displayRemoveList = new JList<String>(allfilelist);
                                JScrollPane filestorm = new JScrollPane(displayRemoveList);
                                filestorm.setBounds(5, 30, 275, 280);
                                filestorm.setBackground(Color.WHITE);
                                rmframe.getContentPane().add(filestorm);
                                
                                JButton dlbutton = new JButton("Remove");
                                dlbutton.setBounds(75, 320 , 150, 30);
                                rmframe.getContentPane().add(dlbutton);
                                dlbutton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										List<String> selectedRMValues = displayRemoveList.getSelectedValuesList();
										//iterate selected values
										for(String value : selectedRMValues)
										{
											//send lockrequest for file to remove
											System.out.println(value);
											node1.nodedata1.lockRequestList.put(Math.abs(value.hashCode()%32768), "rm");
										}
										
										rmframe.setVisible(false);
									}
                                
                                }); 
                                rmframe.setVisible(true);
                            	
                            }
                    });
                   
                    btnDLFile = new JButton("Download File");
                    btnDLFile.setBounds(500, 150, 150, 30);
                    nodeframe.getContentPane().add(btnDLFile);
                    btnDLFile.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								//let user choose which files to download
								JFrame dlframe = new JFrame();
                            	dlframe.setTitle("Download File");
                            	dlframe.getContentPane().setForeground(Color.BLACK);
                            	dlframe.setResizable(true);
                            	dlframe.getContentPane().setBackground(Color.WHITE);
                            	dlframe.setBackground(Color.WHITE);
                            	dlframe.setBounds(200, 200, 300, 400);
                            	dlframe.setResizable(false);
                            	dlframe.getContentPane().setLayout(null);
                            	
                            	JTextPane dltxtpnfilename = new JTextPane();
                                dltxtpnfilename.setFont(new Font("Tahoma", Font.BOLD, 13));
                                dltxtpnfilename.setEditable(false);
                                dltxtpnfilename.setText("Files to download: ");
                                dltxtpnfilename.setBounds(5, 10, 280, 20);
                                dlframe.getContentPane().add(dltxtpnfilename);
                                
                                JList<String> displayDownloadList = new JList<String>(allfilelist);
                                JScrollPane filestodl = new JScrollPane(displayDownloadList);
                                filestodl.setBounds(5, 30, 275, 280);
                                filestodl.setBackground(Color.WHITE);
                                dlframe.getContentPane().add(filestodl);
                                
                                JButton dlbutton = new JButton("Download");
                                dlbutton.setBounds(75, 320 , 150, 30);
                                dlframe.getContentPane().add(dlbutton);
                                dlbutton.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										List<String> selectedValues = displayDownloadList.getSelectedValuesList();
										//iterate selected files
										for(String value : selectedValues)
										{
											//send lockrequest for file to download
											System.out.println(value);
											node1.nodedata1.lockRequestList.put(Math.abs(value.hashCode()%32768), "dl");
										}
										
										dlframe.setVisible(false);
									}
                                
                                }); 
                                dlframe.setVisible(true);
                            	
                            }
                    });
                   
                    JButton btnOpenFolder = new JButton("Open Folder");
                    btnOpenFolder.setBounds(500, 200, 150, 30);
                    nodeframe.getContentPane().add(btnOpenFolder);
                    btnOpenFolder.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    try {
                    	Desktop.getDesktop().open(new File(node1.nodedata1.getMyLocalFolder()));
                    	} catch (IOException e1) {}
                    }
                    });
                   
                    JButton btnQuit = new JButton("Quit Node");
                    btnQuit.setBounds(500, 250, 150, 30);
                    nodeframe.getContentPane().add(btnQuit);
                    btnQuit.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                    node1.nodedata1.setToQuit(true);
                                    }
                    });                   
                    
                    nodeframe.setVisible(true);
        			
                    //listen for changes and update lists in GUI
        			new Thread() {
        	            public void run() {
        	            	
        	            	while(true){
        	            		if(node1.nodedata1.isChanged())
        	            		{
        	            			System.out.println("changed list");
        	            			generateLists();
        	            			node1.nodedata1.setChanged(false);
        	            		}
        	            		else
        	            		{
        	            			try { Thread.sleep(1000);} catch (InterruptedException e) {}
        	            		}
        	            	}
        	            }
        	        }.start();
        		}
        		
        		else
        		{
        			//no nameserver found, node does not start
        			JTextField errortext = new JTextField();
        			errortext.setForeground(Color.RED);
        			errortext.setText("No nameserver found");
        			errortext.setFont(new Font("Tahoma", Font.BOLD, 13));
        			errortext.setBorder(null);
        	        errortext.setBounds(10, 100, 290, 20);
        	        errortext.setColumns(10);        			
        	        nameframe.getContentPane().add(errortext);
        		}
        	}
        });

	}
	
	public void generateLists(){
		
		//method to update the lists with new values
		
		tempLocalFiles = node1.nodedata1.localFiles;
        tempAllNetworkFiles = node1.nodedata1.allNetworkFiles;
		
		//update local files
        DefaultListModel<String> filelisttemp = filelist;
        displayList = new JList<String>(filelisttemp);
        filelist.clear();
        if(tempLocalFiles.size() != 0)
        {
        	for (FileData value : tempLocalFiles.values())
        	{
        		filelist.addElement(value.getFileName());
        	}
        }        
        displayList = new JList<String>(filelist);
        JScrollPane ownfile = new JScrollPane(displayList);
        ownfile.setBounds(5, 50, 220, 410);
        ownfile.setBackground(Color.WHITE);
        nodeframe.getContentPane().add(ownfile);
        
        //update all files
        DefaultListModel<String> allfilelisttemp = allfilelist;
        displayAllList = new JList<String>(allfilelisttemp);
        allfilelist.clear();
        if (tempAllNetworkFiles.size() > 0)
        {
	        for (TreeMap<Integer, FileData> value : tempAllNetworkFiles.values())
	        {
	        	for (FileData temp : value.values())
	        	{
	        		allfilelist.addElement(temp.getFileName());
	        	}
	        }
        }
        displayAllList = new JList<String>(allfilelist);
        JScrollPane allfile = new JScrollPane(displayAllList);
        allfile.setBounds(230, 50, 220, 410);
        allfile.setBackground(Color.WHITE);
        nodeframe.getContentPane().add(allfile);
        
	}	
}
