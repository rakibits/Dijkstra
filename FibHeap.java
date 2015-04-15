package part1;

import java.util.HashMap;
import java.util.Map;

public class FibHeap 
{
	FibHeapNode minNode;
	
	public boolean insert(FibHeapNode n)
	{
		if(minNode == null)
			minNode = n;
		else
		{
			if(minNode.getLeft() == minNode)
			{
				n.setLeft(minNode);
				n.setRight(minNode);
				minNode.setRight(n);
				minNode.setLeft(n);
			}
			else
			{
				n.setLeft(minNode.getLeft());
				n.setRight(minNode);
				minNode.getLeft().setRight(n);
				minNode.setLeft(n);
			}
			if(minNode.getData() > n.getData())
				minNode = n;
		}
		return true;
	}
	
	public FibHeapNode Join(Map<Integer, FibHeapNode> inp, FibHeapNode node)
	{
		int ndegree = node.getDegree();
		if(inp.containsKey(ndegree))
		{
			FibHeapNode temp = inp.get(ndegree);
			
			if(temp.getData() < node.getData())
			{
				FibHeapNode child = temp.getChild();
				temp.setChild(node);
				node.setParent(temp);
				temp.setDegree(temp.getDegree()+1);
				if(child != null)
				{
					node.setLeft(child.getLeft());
					child.getLeft().setRight(node);
					node.setRight(child);
					child.setLeft(node);
				}
				inp.remove(ndegree);
				return Join(inp, temp);
			}
			else
			{
				FibHeapNode child = node.getChild();
				node.setChild(temp);
				temp.setParent(node);
				node.setDegree(node.getDegree()+1);
				if(child != null)
				{
					temp.setLeft(child.getLeft());
					child.getLeft().setRight(temp);
					temp.setRight(child);
					child.setLeft(temp);
				}
				inp.remove(ndegree);
				return Join(inp, node);
			}
		}
		else
		{
			inp.put(node.getDegree(), node);
			return node;
		}
	}
	
	public boolean removeMin()
	{
		//System.out.println(minNode.getnodeNum() + "   " + minNode.getData());
		FibHeapNode node = minNode.getLeft();
		FibHeapNode child = minNode.getChild();
		
		if(node == minNode && child == null)
		{
			minNode = null;
			return true;
		}
		
		Map<Integer, FibHeapNode> map = new HashMap<Integer, FibHeapNode>();
		int ndegree = 0;
		if(child != null)
		{
			FibHeapNode tmp = child, current2 = child;
			do 
			{
				current2 = tmp;
				ndegree = current2.getDegree();
				current2.setChildCut(false);
				current2.setParent(null);
				tmp = current2.getLeft();
				current2.setLeft(current2);
				current2.setRight(current2);
				if(map.containsKey(ndegree))
					Join(map, current2);
				else
					map.put(ndegree, current2);
			
			} while(tmp != child);
			
			
			FibHeapNode temp = node;
			while(temp != minNode)
			{
				node = temp;
				ndegree = node.getDegree();
				temp = node.getLeft();
				node.setLeft(node);
				node.setRight(node);
				if(map.containsKey(ndegree))
					Join(map, node);
				else
					map.put(ndegree, node);
			}
		}
		else
		{
			FibHeapNode temp = node;
			while(temp != minNode)
			{
				node = temp;
				ndegree = node.getDegree();
				temp = node.getLeft();
				node.setLeft(node);
				node.setRight(node);
				if(map.containsKey(ndegree))
					Join(map, node);
				else
					map.put(ndegree, node);
			}
		}
		minNode = null;
		
		for (FibHeapNode value : map.values())
		{
			meld(value);
		}
		map.clear();
		return true;
	}
	
	public FibHeapNode meld(FibHeapNode min2)
	{
		if(minNode == null)
		{
			minNode = min2;
			return minNode;
		}
		FibHeapNode temp1 = minNode.getRight(),temp2 = min2.getRight(); 
		if(temp1 == minNode && temp2 == min2)
		{
			minNode.setLeft(min2);
			minNode.setRight(min2);
			min2.setLeft(minNode);
			min2.setRight(minNode);
		}
		else if(temp1 == minNode)
		{
			minNode.setLeft(min2.getLeft());
			minNode.setRight(min2);
			min2.getLeft().setRight(minNode);
			min2.setLeft(minNode);
		}
		else if(temp2 == min2)
		{
			min2.setLeft(minNode.getLeft());
			min2.setRight(minNode);
			minNode.getLeft().setRight(min2);
			minNode.setLeft(min2);
		}
		else
		{
			minNode.setRight(temp2);
			temp2.setLeft(minNode);
			min2.setRight(temp1);
			temp1.setLeft(min2);
		}
		if(minNode.getData() > min2.getData())
			minNode = min2;
		return minNode;
	}
	
	public FibHeapNode remove(FibHeapNode node, boolean flag)
	{	
		if(!flag)
		{
			if(node.getParent() == null)
				node.setChildCut(false);
			else
			{
				if(node.getParent().getChild() == node)
				{
					if(node.getLeft() == node)
						node.getParent().setChild(null);
					else
					{
						node.getParent().setChild(node.getLeft());
						node.getLeft().setRight(node.getRight());
						node.getRight().setLeft(node.getLeft());
						node.setLeft(node);
						node.setRight(node);
					}
				}
				else
				{
					node.getLeft().setRight(node.getRight());
					node.getRight().setLeft(node.getLeft());
					node.setLeft(node);
					node.setRight(node);
				}
				node.getParent().setDegree(node.getParent().getDegree()-1);
				node.setChildCut(false);
				meld(node);
				FibHeapNode tmp = node.getParent();
				if(tmp.isChildCut())
					remove(tmp, false);
				else
					tmp.setChildCut(true);
				node.setParent(null);
			}
		}
		return minNode;
	}
	
	public boolean decreaseKey(FibHeapNode node, long tovalue)
	{
		if(node.getParent() == null)
		{
			node.setValue(tovalue);
			if(minNode.getData() > node.getData())
				node = minNode;
		}
		else
		{
			if(tovalue > node.getParent().getData())
				node.setValue(tovalue);
			else
			{
				node.setValue(tovalue);
				remove(node, false);
			}
		}
		return true;
	}
	
	public int getSize()
	{
		if(minNode == null)
			return 0;
		else 
			return 1;
	}	
}