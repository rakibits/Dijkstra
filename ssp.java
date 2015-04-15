import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

class Node
{
	int data;
	FibHeapNode fnode;
	Node(int data, FibHeapNode fnode)
	{
		this.data = data;
		this.fnode = fnode;
	}
}


class Output
{
	long distance;
	String path;
	int[] prev;
	Output(long distance, int[] prev, String path)
	{
		this.distance = distance;
		this.prev = prev;
		this.path = path;
	}
}

public class ssp 
{
	public static void main(String[] args) throws IOException 
	{
		int src = Integer.parseInt(args[1]), dst = Integer.parseInt(args[2]);
		//long t1 = System.currentTimeMillis();
		String pathname = args[0];
		Map<Integer, ArrayList<Node>> map = new HashMap<Integer, ArrayList<Node>>();
		map = CreateNetwork(pathname);
		//System.out.println("Input Reading Over");
		//long t2 = System.currentTimeMillis();
		//System.out.println((t2-t1)/100);
		long shortestdist = FindShortestPath(src, dst, map).distance;
		String shortestpath = FindShortestPath(src, dst, map).path;
		System.out.println(shortestdist);
		System.out.println(shortestpath);
		//long t3 = System.currentTimeMillis();
		//System.out.println((t3-t2)/100);
	}
	
	public static Map<Integer, ArrayList<Node>> CreateNetwork(String pathname) throws IOException
	{
		Scanner scanner = new Scanner(new File(pathname));
		//Scanner scanner = new Scanner(new File("C:\\Users\\RamakrishnaReddy\\Desktop\\ACADS\\Semester - II\\Advanced Data Structures\\Project\\input_5000_1_part1.txt"));
		//Scanner scanner = new Scanner(new File("C:\\Users\\RamakrishnaReddy\\Desktop\\ACADS\\Semester - II\\Advanced Data Structures\\Project\\fla.txt"));
		
		scanner.nextInt();
		scanner.nextInt();
		Map<Integer, ArrayList<Node>> map = new HashMap<Integer, ArrayList<Node>>();
		
		int source = 0, destination = 0, wgt = 0;
		while(scanner.hasNextInt())
		{
			source = scanner.nextInt();
			destination = scanner.nextInt();
			wgt = scanner.nextInt();
			FibHeapNode fn1 = new FibHeapNode(destination, wgt);
			FibHeapNode fn2 = new FibHeapNode(source, wgt);
			Node n1 = new Node(destination, fn1);
			Node n2 = new Node(source, fn2);
			
			if(map.containsKey(source))
			{
				ArrayList<Node> l = new ArrayList<Node>();
				l.addAll(map.get(source));
				l.add(n1);
				map.remove(source);
				map.put(source, l);
			}
			else
			{
				ArrayList<Node> l = new ArrayList<Node>();
				l.add(n1);
				map.put(source, l);
			}
			if(map.containsKey(destination))
			{
				ArrayList<Node> l = new ArrayList<Node>();
				l.addAll(map.get(destination));
				l.add(n2);
				map.remove(destination);
				map.put(destination, l);
			}
			else
			{
				ArrayList<Node> l = new ArrayList<Node>();
				l.add(n2);
				map.put(destination, l);
			}
		}
		scanner.close();
		return map;
	}

	public static Output FindShortestPath(int source, int destination, Map<Integer, ArrayList<Node>> map)
	{
		int dd = destination;
		int vertices = map.size();
		long[] dist = new long[vertices];
		int[] prev = new int[vertices];
		dist[source] = 0;                      // Initialization
		FibHeap heap = new FibHeap();
		Map<Integer, FibHeapNode> hmap = new HashMap<Integer, FibHeapNode>();
		for(int i = 0; i < vertices; i++)
		{          
			if(i != source)
			{
				dist[i] = Integer.MAX_VALUE;            // Unknown distance from source to v
				prev[i] = -1;           // Predecessor of v
			}
			FibHeapNode n = new FibHeapNode(i, dist[i]);
			heap.insert(n);
			hmap.put(i, n);
		} 
		while(heap.getSize() > 0) 
		{	// The main loop
			FibHeapNode minNode = heap.minNode;            // Remove and return best vertex
			heap.removeMin();
			int currvertex = minNode.getnodeNum();
			List<Node> l = map.get(currvertex);
			for(int j = 0; j < l.size(); j++)
			{
				Node n = l.get(j);
				long alt = minNode.getData() + n.fnode.getData();
				if(alt < dist[n.data])
				{
					dist[n.data] = alt;
					prev[n.data] = currvertex;
					FibHeapNode fibNode = hmap.get(n.data);
					heap.decreaseKey(fibNode, alt);
				}
			}
		}
		
		//System.out.println(dist[destination]);
		Stack<Integer> s = new Stack<Integer>();
		while(destination != source)
		{
			s.push(prev[destination]);
			destination = prev[destination];
		}
		
		StringBuilder sb = new StringBuilder();
		while(s.size() > 0)
			sb.append(s.pop() + " ");
		sb.append(dd);
		//System.out.println(sb.toString()); 
		Output o = new Output(dist[dd], prev, sb.toString());
		return o;
	}
}
