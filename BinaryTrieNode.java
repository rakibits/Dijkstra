public class BinaryTrieNode 
{	
	private int data, bits;
	private String binaryCode;
	private BinaryTrieNode left, right;
	
	
	public BinaryTrieNode(String binaryCode, int data)
	{
		this.data = data;
		this.binaryCode = binaryCode;
		this.left = null;
		this.right = null;
	}
	
	public BinaryTrieNode(int bits, int data)
	{
		this.data = data;
		this.bits = bits;
		this.binaryCode = "";
		this.left = null;
		this.right = null;
	}
	
	public BinaryTrieNode getLeft() 
	{
		return left;
	}
	
	public boolean setLeft(BinaryTrieNode newleft) 
	{
		left = newleft;
		return true;
	}
	
	public BinaryTrieNode getRight() 
	{
		return right;
	}
	
	public boolean setRight(BinaryTrieNode newright) 
	{
		right = newright;
		return true;
	}
	
	public int getdata() 
	{
		return data;
	}
	
	public boolean setdata(int newdata) 
	{
		data = newdata;
		return true;
	}
	
	public int getBits() 
	{
		return bits;
	}
	
	public boolean setBits(int newbits) 
	{
		bits = newbits;
		return true;
	}

	public boolean setBinaryCode(String newbinaryCode) 
	{
		binaryCode = newbinaryCode;
		return true;
	}
	
	public String getBinaryCode() 
	{
		return binaryCode;
	}
}
