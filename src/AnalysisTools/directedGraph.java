package AnalysisTools;

import java.util.*;

public class directedGraph
{
	HashMap<String, LinkedList<String>> vertices;
	HashMap<String, Double> pageRanks;
	
	public directedGraph()
	{
		vertices = new HashMap<String, LinkedList<String>>();
		pageRanks = new HashMap<String, Double>();
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
	
	//This method return the list of vertices that the vertex s depends on.
	public int getInDegree(String s)
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
		
		return l.size();
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
