package AnalysisTools;

import java.util.*;

public class Node implements Comparable
{
	private String nodeName;
	private double nodeRank;
	
	public Node(String name, double rank)
	{
		nodeName = name;
		nodeRank = rank;
	}
	
	public int compare(Node n)
	{
		int c = 0;
		if(this.nodeRank > n.getRank())
		{
			c = 1;
		}
		
		return c;
	}
	
	public String toString()
	{
		return nodeName + " : " + nodeRank;
	}
	
	public double getRank()
	{
		return this.nodeRank;
	}
	
	public String getName()
	{
		return this.nodeName;
	}

	public int compareTo(Object o1)
	{
		if(this.nodeRank == ((Node)o1).nodeRank)
		{
			return 0;
		}
		else if((this.nodeRank)>((Node)o1).nodeRank)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
}