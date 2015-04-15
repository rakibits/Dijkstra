package part1;

public class FibHeapNode 
{
	private int degree, nodeNum;
	private long data;
	private FibHeapNode child, left, right, parent;
	private boolean childCut;
	
	public FibHeapNode(int nodenum, long data) 
	{
		this.data = data;
		this.nodeNum = nodenum;
		this.degree = 0;
		this.child = null;
		this.left = this;
		this.right = this;
		this.parent = null;
		this.childCut = false;
	}

	public FibHeapNode(int nodenum, int degree, long data, FibHeapNode child, FibHeapNode left,
			FibHeapNode right, FibHeapNode parent, boolean childCut) {
		this.nodeNum = nodenum;
		this.data = data;
		this.degree = degree;
		this.child = child;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.childCut = childCut;
	}
	
	public FibHeapNode getChild() 
	{
		return child;
	}
	
	public long getData() 
	{
		return data;
	}
	
	public int getnodeNum() 
	{
		return nodeNum;
	}
	
	public void setChild(FibHeapNode child) 
	{
		this.child = child;
	}
	
	public int getDegree() 
	{
		return degree;
	}
	
	public void setDegree(int degree) 
	{
		this.degree = degree;
	}
	
	public FibHeapNode getLeft() 
	{
		return left;
	}
	
	public void setLeft(FibHeapNode left) 
	{
		this.left = left;
	}
	
	public FibHeapNode getRight() {
		return right;
	}
	
	public void setRight(FibHeapNode right) {
		this.right = right;
	}
	
	public FibHeapNode getParent() {
		return parent;
	}
	
	public void setParent(FibHeapNode parent) {
		this.parent = parent;
	}
	
	public void setValue(long toValue) {
		this.data = toValue;
	}
	
	public boolean isChildCut() {
		return childCut;
	}
	
	public void setChildCut(boolean childCut) {
		this.childCut = childCut;
	}
}
