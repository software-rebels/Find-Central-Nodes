package AnalysisTools;

import java.util.*;
import java.io.*;
import java.text.*;

public class Analyse 
{
	public directedGraph graph;
	
	public Analyse()
	{
		graph = new directedGraph();
	}
	
	public void createGraph(String traceFile)
	{
		String readLine = "";
		String[] temp = new String[15];
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(traceFile)));
			
			while(readLine.contains("nodedef>")==false)
			{
				readLine = br.readLine();
			}
			
			while(true)
			{
				readLine = br.readLine();
				if(readLine.contains("edgedef>"))
				{
					break;
				}
				temp = readLine.split(",");
				graph.addVertex(temp[0]);
				
				if(temp[8].equals("1"))
				{
					graph.setPageRank(temp[0], -1.0);
				}
			}
			
			while(true)
			{
				readLine = br.readLine();
				if(readLine == null)
				{
					break;
				}
				
				temp = readLine.split(",");
				graph.addEdge(temp[0], temp[1]);
			}
			
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void computePageRanks()
	{
		DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		LinkedList<String> vertices = graph.getVertices();
		Iterator<String> i = vertices.iterator();
		while(i.hasNext())
		{
			String node = i.next();
			if(!(graph.getPageRank(node) == -1.0))
			{
				graph.setPageRank(node, 1.0);
			}
		}
		
		for(int j=0; j<3; j++)
		{
			Iterator<String> k = vertices.iterator();
			while(k.hasNext())
			{
				String s = k.next();
				double pageRank = 0.5;
				
				//neighbours refer to outgoing edges
				LinkedList<String> neighbours = graph.getNeighbours(s);
				Iterator<String> l = neighbours.iterator();
				while(graph.getPageRank(s)!=-1.0 && l.hasNext())
				{
					String t = l.next();
					pageRank+=(0.5*(graph.getPageRank(t)/graph.getInDegree(t)));
				}
				
				if(graph.getPageRank(s)!=-1.0)
				{
					graph.setPageRank(s, pageRank);
				}
			}
			Date date = new Date();
			System.out.println(j + " iteration completed at "+dateformat.format(date));
		}
	}
	
	public void outputHubs(int number)
	{
		ArrayList<String> keys = new ArrayList<>(graph.pageRanks.keySet());
		ArrayList<Double> values = new ArrayList<>(graph.pageRanks.values());
		Collections.sort(keys, Collections.reverseOrder());
		Collections.sort(values, Collections.reverseOrder());
		
		Iterator<Double> valueIt = values.iterator();
		for(int i=0; i<number; i++)
		{
			Double comp1 = valueIt.next();
			
			Iterator<String> keyIt = keys.iterator();
			while(keyIt.hasNext())
			{
				String temp = keyIt.next();
				Double comp2 = graph.pageRanks.get(temp);
				
				if(comp1.doubleValue() == comp2.doubleValue())
				{
					System.out.println((i+1)+": "+temp+" --> "+comp1);
					break;
				}
			}
		}
	}
	
	public void print()
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/Users/Owais/Desktop/vtkAnalysis.txt")));
			Iterator<String> i = graph.pageRanks.keySet().iterator();
			while(i.hasNext())
			{
				String s = i.next();
				bw.write(s+" : "+graph.pageRanks.get(s));
				bw.newLine();
			}
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Analyse graphAnalysis = new Analyse();
		graphAnalysis.createGraph("/home/owais/vtk/trace.gdf");
		System.out.println("Graph Created");
		System.out.println(graphAnalysis.graph.getVertices());
//		graphAnalysis.computePageRanks();
//		graphAnalysis.outputHubs(20);
	}
}
