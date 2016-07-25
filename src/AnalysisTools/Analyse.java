package AnalysisTools;

import java.util.*;
import java.io.*;
import java.text.*;

public class Analyse 
{
	public DirectedGraph graph;

	public String traceGdfLocation;
	public String resultsStorageLocation;
	
	public Analyse()
	{
		graph = new DirectedGraph();
	}
	
	public static void main(String[] args)
	{
		Analyse graphAnalysis = new Analyse();
		graphAnalysis.getPropValues();

		graphAnalysis.createGraph(graphAnalysis.traceGdfLocation);
		System.out.println("Graph Created");

		graphAnalysis.pageRanks();
		System.out.println("Page ranks calculated");

		graphAnalysis.print(graphAnalysis.resultsStorageLocation);
		System.out.println("Done");
	}

	public void getPropValues()
	{
		try
		{
			Properties prop = new Properties();
			String propFileName = "config.properties";
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if(inputStream != null)
			{
				prop.load(inputStream);
			}
			else
			{
				throw new FileNotFoundException("property file '"+propFileName+"' npt found in the classpath.");
			}

			inputStream.close();

			this.traceGdfLocation = prop.getProperty("traceGdfLocation");
			this.resultsStorageLocation = prop.getProperty("resultsStorageLocation");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Traverses the trace.gdf file and creates a graph using hashmaps.
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
					graph.setPageRank(temp[0], (double)(-1));
				}
				else if(temp[8].equals("0"))
				{
					graph.setPageRank(temp[0], (double)(1));
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
				graph.addInDegree(temp[0], temp[1]);
			}
			
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Calculates pageranks.
	public void pageRanks()
	{		
		LinkedList<String> vertices = graph.getVertices();
		
		double d = 0.85;
		
		for(int j=0; j<100; j++)
		{
			Iterator<String> k = vertices.iterator();
			
			while(k.hasNext())
			{
				String curNode = k.next();
				double pagerank = 1-d;
				
				if(graph.getPageRank(curNode) != -1.0)
				{
					LinkedList<String> inDegrees = graph.getNeighbours(curNode);
					Iterator<String> iter = inDegrees.iterator();
					
					while(iter.hasNext())
					{
						String neighIn = iter.next();
						if(graph.getPageRank(neighIn) != -1.0)
						{
							//Two Different Formulas
							//First one is the one used by google to rank webpages according to their authority.
							//Second one is a slight modification of the first since the direction of arrows have different meanings
							//in file dependency graphs than they do in graph for the www.
							//pagerank += d*((graph.getPageRank(neighIn))/(graph.getOutDegree(neighIn)));
							pagerank += d*((graph.getPageRank(neighIn))/(graph.getInDegree(neighIn)));
						}
					}
					
					graph.setPageRank(curNode, pagerank);
				}
			}
		}
		
	}
	
	//Prints sorted page ranks to file	
	public void print(String resultsStorageLocation)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(resultsStorageLocation)));

			LinkedList<Node> nodes = new LinkedList<Node>();
			Iterator<String> itr = graph.pageRanks.keySet().iterator();
			while(itr.hasNext())
			{
				String node = itr.next();
				nodes.add(new Node(node, graph.pageRanks.get(node)));
			}

			Collections.sort(nodes);
			Iterator iter = nodes.iterator();
			while(iter.hasNext())
			{
				Object element = iter.next();

				//Dont print phony targets.
				if(((Node)element).getRank() != -1.0)
				{
					bw.write(element.toString());
					bw.newLine();
				}
			}

			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
