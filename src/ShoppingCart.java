import java.util.Collection;
import java.util.Comparator;

public class ShoppingCart extends ItemList implements Visitor
{
	double budget;
	
	ShoppingCart(double budget)
	{
		this.budget = budget;
		c = new Comparator<Node<Item>>()
		{		
			public int compare(Node<Item> arg0, Node<Item> arg1)
			{
				return (int)(arg1.item.price - arg0.item.price);
			}
		};
	}
	ShoppingCart()
	{
		this(0);
	}
	
	public boolean add(Item element)
	{
		if(budget - element.price < 0)
			return false;
		Node<Item> node = new Node<>(element);
		Node<Item> puppet = first;
		if(!(this.isEmpty()))
		{
			puppet = puppet.next;
			if(c.compare(puppet, node) == 0)
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
		this.budget = this.budget - element.price;
		return true;
	}
	
	public boolean remove(Item item)
	{
		//TODO NU MERGE REMOVE DE ULTIMUL
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.equals(item))
			{
				iterator.current.next.prev = iterator.current.prev;
				iterator.current.prev.next = iterator.current.next;
				budget =budget + item.price;
				return true;		
			}
		}
		return false;
	}
		
	public Item getItem(int index) {return null;}
	public Node<Item> getNode(int index) {return null;}
	public int indexOf(Item item) {return 0;}
	public int indexOf(Node<Item> node) {return 0;}
	public boolean contains(Item item) {return false;}
	public boolean contains(Node<Item> node) {return false;}
	public Item remove(int index) {return null;}
	public boolean removeAll(Collection<? extends Item> c) {return false;}

	public void visit(BookDepartment dep)
	{
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) this.listIterator();
		System.out.println("Sunt in accept Book, Cart: " + this);
		if(this.isEmpty())
			return;
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.depID == dep.ID)
			{
				iterator.current.item.price = 0.9*iterator.current.item.price;
			}	
		}		
	}
	@Override
	public void visit(SoftwareDepartment dep) {
		double min=999999999;
		for(Item it: dep.items)
		{
			if(it.price < min)
				min = it.price;
		}
		if(this.budget < min)
		{
			ItemList.ItemIterator iterator = (ItemList.ItemIterator) this.listIterator();
			if(this.isEmpty())
				return;
			while(iterator.hasNext())
			{
				iterator.current = iterator.current.next;
				if(iterator.current.item.depID == dep.ID)
				{
					iterator.current.item.price = 0.8*iterator.current.item.price;
				}	
			}
		}
		
	}
	@Override
	public void visit(VideoDepartment dep)
	{
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) this.listIterator();
		if(this.isEmpty())
			return;
		double Total = 0, max = 0;
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.depID == dep.ID)
			{
				Total = Total + iterator.current.item.price;
			}	
		}
		this.budget = this.budget + 0.05*Total;
		for(Item it: dep.items)
		{
			if(it.price > max)
				max = it.price;
		}
		iterator = (ItemList.ItemIterator) this.listIterator();
		if(Total > max)
		{
			while(iterator.hasNext())
			{
				iterator.current = iterator.current.next;
				if(iterator.current.item.depID == dep.ID)
				{
					iterator.current.item.price = 0.85*iterator.current.item.price;
				}
			}
		}		
	}
	@Override
	public void visit(MusicDepartment dep)
	{
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) this.listIterator();
		if(this.isEmpty())
			return;
		double total=0;
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.depID == dep.ID)
			{
				total = total + iterator.current.item.price;
			}		
		}
		this.budget = this.budget + 0.1*total;
	}
	
	/*public String toString()
	{
		String ret = "";
		if(this.isEmpty())
			return "Empty(" + budget + ")";
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			ret = ret + iterator.next().name;
			iterator.current = iterator.current.next;
		}
		return ret + "(" + budget + ")";
	}*/
}
