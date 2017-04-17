import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;

abstract class ItemList
{
	protected Comparator<Node<Item>> c ;
	public class Node<T>
	{
		T item;
		Node<T> next;
		Node<T> prev;
		
		Node(T item, Node<T> next, Node<T> prev)
		{
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
		Node(T item)
		{
			this(item, null, null);
		}
		
		public String toString()
		{
			return "Nod:" + item + "|";
		}
	}
	Node<Item> last = new Node<>(null);
	Node<Item> first = new Node<>(null);
	Node<Item> current = first;
	
	class ItemIterator implements ListIterator<Item>
	{
		Node<Item> current;
		ItemIterator(int index)
		{
			Node<Item> puppet = first;
			for(int i=0; i<index; i++)
			{
				puppet = puppet.next;
			}
			current = puppet;
		}
		ItemIterator()
		{
			this(0);
		}
		@Override
		public void add(Item element){}
		@Override
		public boolean hasNext()
		{
			if( (current.next == null) || (current.next.equals(last)) )
				return false;
			else
				return true;
		}
		@Override
		public boolean hasPrevious() 
		{
			if(!(current.next.equals(first)))
				return true;
			else
				return false;
		}
		@Override
		public Item next()
		{
			return current.next.item;
		}
		@Override
		public int nextIndex(){
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public Item previous()
		{
			return current.prev.item;
		}
		@Override
		public int previousIndex(){
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public void remove(){}
		@Override
		public void set(Item arg0)
		{
			current = new Node(arg0);
		}	
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////methods		
////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean add(Item element)
	{
		Node<Item> node = new Node<>(element);
		Node<Item> puppet = first;
		if(!(this.isEmpty()))
		{
			puppet = puppet.next;
			if(this.c.compare(puppet, node) == 0)
				return false;
			while(c.compare(puppet, node)>0)
			{
				puppet = puppet.next;
				if(puppet.item == null)
					break;
				if(c.compare(puppet, node) == 0)
					return false;
			}
			puppet.prev.next = node;
			node.prev = puppet.prev;
			puppet.prev = node;
			node.next = puppet;
		}
		else
		{	
			first.next = node;
			node.prev = first;
			node.next = last;
			last.prev = node;
		}
		return true;
	}
	public boolean addAll(Collection<? extends Item> c)
	{
		for (Item elem : c)
		{
			this.add(elem);
		}
		return true;
	}
	abstract public Item getItem(int index);
	abstract public Node<Item> getNode(int index);
	abstract public int indexOf(Item item);
	abstract public int indexOf(Node<Item> node);
	abstract public boolean contains(Item item);
	abstract public boolean contains(Node<Item> node);
	abstract public Item remove(int index);
	abstract public boolean removeAll(Collection<? extends Item> c);
	abstract public boolean remove(Item item);

	public int indexOf(int item_ID)
	{
		int ret = -1;
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.ID == item_ID)
				ret = indexOf(iterator.current);
		}
		return ret;
	}
	public boolean isEmpty()
	{
		if((first.next == null) || (first.next.item == null))
			return true;
		else
			return false;
	}
	public ListIterator<Item> listIterator(int index)
	{
		return new ItemIterator(index);
	}
	public ListIterator<Item> listIterator()
	{
		return new ItemIterator();
	}
	public Double getTotalPrice()
	{
		double Total = 0;
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			Total = Total + iterator.current.item.price;		
		}
		return Total;
	}
	public String toString()
	{
		NumberFormat formatter = new DecimalFormat("#0.00"); 
		if(this.isEmpty())
			return "[]\n";
		String ret = "[";
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			ret = ret + iterator.next().name + ";"  + iterator.next().ID + ";" + formatter.format(iterator.next().price);			
			iterator.current = iterator.current.next;
			if(iterator.hasNext())
				ret = ret + ", ";
		}
		return ret+"]\n";
	}
}