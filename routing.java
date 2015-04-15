package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Router
{
	String ip;
	BinaryTrie bTrie;
	
	Router(String ip)
	{
		this.ip = ip;
	}
}

public class routing {
	public static void main(String[] args) throws IOException 
	{
		String paths = args[0];
		int src = Integer.parseInt(args[2]), dst = Integer.parseInt(args[3]);
		Map<Integer, ArrayList<Node>> map = new HashMap<Integer, ArrayList<Node>>();
		map = ssp.CreateNetwork(paths);
		int nVertices = map.size();
		Router[] routers = new Router[nVertices];
		
		int i = 0;
		int[] nexthop = new int[nVertices];

		String sCurrentLine; 
		BufferedReader br = new BufferedReader(new FileReader(args[1]));
		
		while ((sCurrentLine = br.readLine()) != null) 
		{
			if(!(sCurrentLine.trim().isEmpty()))
			{
				routers[i] = new Router(sCurrentLine);
				i++;
			}
		}
		br.close();
		
		Map<Integer, ArrayList<Node>> mapCpy = new HashMap<Integer, ArrayList<Node>>(map);
		System.out.println(ssp.FindShortestPath(src, dst, mapCpy).distance);
		
		
		for(int k = 0; k < nVertices ; k++)
		{
			BinaryTrie bTrie = new BinaryTrie();
			int j = 0;
			if(k == nVertices-1)
				j = 0;
			else
				j = nVertices-1;
			
			Map<Integer, ArrayList<Node>> mapCopy = new HashMap<Integer, ArrayList<Node>>(map);
			nexthop = ssp.FindShortestPath(k, j, mapCopy).prev;
			Stack<Integer> s = new Stack<Integer>();
			for(int p = 0; p < nVertices; p++)
			{
				s.clear();
				int destination = p, source = k; 
				if(p != k)
				{
					s.push(destination);
					while(destination != source)
					{
						s.push(nexthop[destination]);
						destination = nexthop[destination];
					}
					s.pop();
					bTrie.Insert(routers[p].ip, s.peek());
				}
			}
			bTrie.Prune();
			//bTrie.levelOrder();
			routers[k].bTrie = bTrie;
		}
		
		int currrouter = src;
		BinaryTrie bTrie;
		String dstip = routers[dst].ip;
		StringBuilder sb = new StringBuilder();
		SearchOutput so = new SearchOutput();
		while(currrouter != dst)
		{
			bTrie = routers[currrouter].bTrie;
			so = bTrie.SearchandPrint(dstip);
			currrouter = so.searchvalue;
			sb.append(so.binaryCode);
			sb.append(" ");
		}
		String op = sb.toString();
		System.out.println(op.substring(0, op.length()-1));
	}
}
