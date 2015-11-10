package fileManagers;
import java.util.ArrayList;

import nameServer.NameServerInterface;
import nodeP.NodeData;

public class FileOwnershipT extends Thread
{
	NodeData nodedata1;
	NameServerInterface nameserver;
	public FileOwnershipT(NodeData nodedata1)
	{
		this.nodedata1=nodedata1;
	}
	
	public void run()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {e1.printStackTrace();
		}
		ArrayList<FileData> tempreplFiles=(ArrayList<FileData>) nodedata1.replFiles.clone();
	for (FileData file1 : tempreplFiles) 
	{
		
		boolean newRepOwner =file1.refreshReplicateOwner(nodedata1,file1);
		if (newRepOwner)
		{
			System.out.println("Changing owner to: "+ file1.getReplicateOwnerID());
			if(nodedata1.replFiles.contains(file1))
			{
				file1.setRemoveAfterSend(true);
			nodedata1.sendQueue.add(file1);
			nodedata1.replFiles.remove(file1);
			
			}
		}
	}

	}
}
