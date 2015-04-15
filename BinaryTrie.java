package part1;

import java.util.Stack;
import java.util.regex.Pattern;

class SearchOutput
{
	String binaryCode;
	int searchvalue;
	SearchOutput(String binaryCode, int searchvalue)
	{
		this.binaryCode = binaryCode;
		this.searchvalue = searchvalue;
	}
	
	SearchOutput()
	{}
}

public class BinaryTrie 
{
	BinaryTrieNode head;
	
	public void Insert(String ip, int nodedata)
	{
		String finalCode = IptoString(ip);
		int charcount = 0;
		if(head == null)
		{
			head = new BinaryTrieNode(finalCode, nodedata);
		}
		else
		{
			BinaryTrieNode node = head, prevnode = head;; 
			
			while(node.getdata() == -1)
			{
				charcount = node.getBits();
				prevnode = node;
				if(finalCode.charAt(charcount) == '1')
					node = node.getRight();
				else
					node = node.getLeft();
			}
			int similar = getMatchedBinaryDigits(node.getBinaryCode(), finalCode).length();
			
			if(similar > prevnode.getBits())
			{
				
				BinaryTrieNode currnewnode = new BinaryTrieNode(finalCode, nodedata);
				BinaryTrieNode prevnewnode = new BinaryTrieNode(node.getBinaryCode(), node.getdata());
				node.setdata(-1);
				node.setBinaryCode("");
				node.setBits(similar);
				if(finalCode.charAt(similar) == '1')
				{
					node.setRight(currnewnode);
					node.setLeft(prevnewnode);
				}
				else
				{
					node.setRight(prevnewnode);
					node.setLeft(currnewnode);
				}

			}
			else
			{
				node = head;
				
				while(node.getdata() == -1 && node.getBits() < similar)
				{
					charcount = node.getBits();
					prevnode = node;
					if(finalCode.charAt(charcount) == '1')
						node = node.getRight();
					else
						node = node.getLeft();
				}
				
				if(node == head)
				{
					charcount = prevnode.getBits();
					BinaryTrieNode currnewnode = new BinaryTrieNode(finalCode, nodedata);
					BinaryTrieNode prevnewnode = new BinaryTrieNode(similar, -1);
					
					if(finalCode.charAt(similar) == '1')
					{
						prevnewnode.setRight(currnewnode);
						prevnewnode.setLeft(node);
					}
					else
					{
						prevnewnode.setRight(node);
						prevnewnode.setLeft(currnewnode);
					}
					head = prevnewnode;
				}
				else
				{
					charcount = prevnode.getBits();
					BinaryTrieNode currnewnode = new BinaryTrieNode(finalCode, nodedata);
					BinaryTrieNode prevnewnode = new BinaryTrieNode(similar, -1);
					
					if(finalCode.charAt(charcount) == '1')
						prevnode.setRight(prevnewnode);
					else
						prevnode.setLeft(prevnewnode);
					
					if(finalCode.charAt(similar) == '1')
					{
						prevnewnode.setRight(currnewnode);
						prevnewnode.setLeft(node);
					}
					else
					{
						prevnewnode.setRight(node);
						prevnewnode.setLeft(currnewnode);
					}
				}
			}
		}
	}
	
	public void Prune()
	{
        BinaryTrieNode poped = new BinaryTrieNode("", -1);
        boolean flag = true;
        if(head == null)
            return ;
        Stack<BinaryTrieNode> S = new Stack<BinaryTrieNode>();
        BinaryTrieNode root = head;
        
		while(flag)
	    {
	        while(root != null)
	        {
	            S.push(root);
	            root = root.getLeft();
	        }
	        if(!S.isEmpty())
	        {
	            root = (BinaryTrieNode)S.peek();
	            if(root.getRight() == poped)
	            {
	                if(root.getRight().getdata() != -1 && root.getRight().getdata() == root.getLeft().getdata())
	                {
	                	root.setdata(root.getLeft().getdata());
	                	root.setBits(-1);
	                	root.setBinaryCode("");
	                	root.setLeft(null);
	                	root.setRight(null);
	                	poped = root;
	                	S.pop();
	                	root = null;
	                }
	                else
	                {
	                	poped = root;
	                	S.pop();
	                	root = null;
	                }
	            }
	            else
	            {
	                if(root.getRight() == null)
	                {
	                    poped = root;
	                    S.pop();
	                }
	                root = root.getRight();
	            }
	        }
	        else
	            flag = false;
	    }
	}
	
	public String getMatchedBinaryDigits(String ip1, String ip2)
	{
		int i = 0;
		StringBuilder sb = new StringBuilder();
		
		while(ip1.charAt(i) == ip2.charAt(i))
		{
			sb.append(ip1.charAt(i));
			i++;
		}
		return sb.toString();
	}
	
	public SearchOutput SearchandPrint(String ip)
	{
		String finalCode = IptoString(ip);
		BinaryTrieNode currNode = head;
		int data = 0, bits = 0;
		while(true)
		{
			data = currNode.getdata();
			if(data == -1)
			{
				bits = currNode.getBits();
				if(finalCode.charAt(bits) == '1')
					currNode = currNode.getRight();
				else
					currNode = currNode.getLeft();
			}
			else
				break;
		}
		SearchOutput so = new SearchOutput(finalCode.substring(0, bits+1), currNode.getdata());
		return so;
	}
	
	public String IptoString(String ip)
	{
		String[] inp = ip.split(Pattern.quote("."));
		int num = 0;
		StringBuilder binaryCode = new StringBuilder(); 
		String currbinaryCode = "";
		for(int i = 0; i < inp.length; i++)
		{
			num = Integer.parseInt(inp[i]);
			currbinaryCode = Integer.toBinaryString(num);
			if(currbinaryCode.length() != 8)
			{
				currbinaryCode = AppendZeros(currbinaryCode);
			}
			binaryCode.append(currbinaryCode);
		}
		return binaryCode.toString();
	}
	
	public String AppendZeros(String code)
	{
		StringBuilder sb = new StringBuilder();
		int numzeros = 8 - code.length();
		
		for(int i = 0; i < numzeros; i++)
		{
			sb.append("0");
		}
		sb.append(code);
		return sb.toString();
	}
}
