public interface Strategy
{
	void execute(WishList wlist);
}

class StrategyA implements Strategy
{	
	public void execute(WishList wlist)
	{
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) wlist.listIterator();
		if(wlist.isEmpty())
		{	
			System.out.println("StrategyA: Empty Wishlist");
			return;
		}
		Item min = new Item("",0,999999,0);
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			System.out.println(iterator.current.item);
			if(iterator.current.item.price < min.price)
			{
				min = new Item(iterator.current.item.name, iterator.current.item.ID, iterator.current.item.price, iterator.current.item.depID);
			}		
		}
		Test.return_value = min;
	}
}

class StrategyB implements Strategy
{	
	public void execute(WishList wlist)
	{
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) wlist.listIterator();
		if(wlist.isEmpty())
		{
			System.out.println("StrategyB: Empty Wishlist");
			return;
		}
		else
			Test.return_value = iterator.current.next.item;
	}
}

class StrategyC implements Strategy
{	
	public void execute(WishList wlist)
	{
		Test.return_value = wlist.latest;
	}
}