import java.util.Collection;
import java.util.Comparator;

public class WishList extends ItemList
{
	Strategy strategy;
	Item latest;
	WishList(Strategy strategy)
	{
		this.strategy = strategy;
		c = new Comparator<Node<Item>>()
		{		
			public int compare(Node<Item> arg0, Node<Item> arg1)
			{
				return arg1.item.name.compareTo(arg0.item.name);
			}
		};
	}

	void executeStrategy()
	{
		strategy.execute(this);
	}
	
	public boolean remove(Item item)
	{
		//TODO NU MERGE REMOVE DE ULTIMUL
		boolean stillObserver = false;
		boolean ret = false;
		Customer subject = null;
		for(Customer Johnny: Test.mystore.customers)
		{
			if(Johnny.wlist.equals(this))
				subject = Johnny;
		}
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if((iterator.current.item.equals(item)) || ((iterator.current.item.name.equals(item.name))&&(iterator.current.item.ID == item.ID)))
			{
				iterator.current.next.prev = iterator.current.prev;
				iterator.current.prev.next = iterator.current.next;
				ret = true;
				break;
			}
		}
		iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.depID == item.depID)
				stillObserver = true;
		}
		if(!stillObserver)
		{
			for(Department dep : Test.mystore.departments)
			{
				if(dep.ID == item.depID)
				{
					dep.removeObserver(subject);
				}
			}
		}
		return ret;
	}
	public Item remove(int index)
	{
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(index == 0)
				this.remove(iterator.current.item);
			index = index - 1;
		}
		return null;
	}
	public boolean set(int id, double newPrice)
	{
		ItemIterator iterator = (ItemIterator) this.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			if(iterator.current.item.ID == id)
			{
				iterator.current.item.price = newPrice;
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
	public boolean removeAll(Collection<? extends Item> c) {return false;}
	
}
