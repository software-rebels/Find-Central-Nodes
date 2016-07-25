package AnalysisTools;

import java.util.*;

public class DirectedGraph
{
	HashMap<String, LinkedList<String>> vertices;
	HashMap<String, Double> pageRanks;
	HashMap<String, LinkedList<String>> inDegrees;
	
	public DirectedGraph()
	{
		vertices = new HashMap<String, LinkedList<String>>();
		pageRanks = new HashMap<String, Double>();
		inDegrees = new HashMap<String, LinkedList<String>>();
	}
	
	public void addVertex(String s)
	{
		if(vertices.containsKey(s))
		{
			return;
		}
		vertices.put(s, new LinkedList<String>());
	}
	
	public void addEdge(String s1, String s2)
	{
		if(!vertices.containsKey(s1))addVertex(s1);
		if(!vertices.containsKey(s2))addVertex(s2);
		
		LinkedList<String> edges = vertices.get(s1);
		if(!edges.contains(s2))edges.addLast(s2);
	}

	public void addInDegree(String s1, String s2)
	{
		if(!inDegrees.containsKey(s2))
		{
			inDegrees.put(s2, new LinkedList<String>());
			LinkedList<String> inDeg = inDegrees.get(s2);
			inDeg.addLast(s1);
			return;
		}
		else
		{
			LinkedList<String> inDeg = inDegrees.get(s2);
			inDeg.addLast(s1);
			return;			
		}
	}
	
	public LinkedList<String> getNeighbours(String s)
	{
		return vertices.get(s);
	}
	
	public LinkedList<String> getVertices()
	{
		Iterator<String> i = vertices.keySet().iterator();
		LinkedList<String> l = new LinkedList<String>();
		while(i.hasNext())
		{
			l.addLast(i.next());
		}
		
		return l;
	}
	
	public LinkedList<String> getInDegrees(String s)
	{
		return inDegrees.get(s);
	}
	
	public int getOutDegree(String s)
	{
		return vertices.get(s).size();
	}
	
	//Return in degree of vertex s
	public int getInDegree(String s)
	{
		return inDegrees.get(s).size();
	}
	
	//Calculate all in degrees
	public void calcInDegrees()
	{
		LinkedList<String> vertices = this.getVertices();
		Iterator<String> i = vertices.iterator();
		
		while(i.hasNext())
		{
			String v = i.next();
			inDegrees.put(v, inDeg(v));
		}
	}
	
	//Helper Method
	public LinkedList<String> inDeg(String s)
	{
		LinkedList<String> l = new LinkedList<String>();
		Iterator<String> i = vertices.keySet().iterator();
		while(i.hasNext())
		{
			String v = i.next();
			if(vertices.get(v).contains(s))
			{
				l.addLast(v);
			}
		}
		
		return l;
	}
	
	public void setPageRank(String node, Double rank)
	{
		pageRanks.put(node, rank);
	}
	
	public double getPageRank(String node)
	{
		if(pageRanks.containsKey(node))
		{
			return pageRanks.get(node).doubleValue();
		}
		else
		{
			return 0;
		}
	}
	
    public String toString() {
        return "Vertices: \n" + vertices.toString() + "\npageRanks:\n"+pageRanks;
    }
}
