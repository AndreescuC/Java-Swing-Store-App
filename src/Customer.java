import java.util.ArrayList;
import java.util.Collection;

public class Customer implements Observer
{
	String name;
	ShoppingCart cart = new ShoppingCart();
	WishList wlist;// = new WishList(new StrategyA());
	Collection<Notification> notifications = new ArrayList<>();
	
	Customer(String nume, ShoppingCart cart, WishList wlist)
	{
		name = nume;
		this.cart = cart;
		this.wlist = wlist;
	}
	
	public String toString()
	{
		return name;
	}

	@Override
	public void update(Notification notification)
	{
		notifications.add(notification);
		switch(notification.type)
		{
			case ADD:
				return;
			case REMOVE:
				if(wlist.indexOf(notification.prod_ID) != -1)
				{
					wlist.remove(wlist.indexOf(notification.prod_ID));
				}
				break;
			case MODIFY:					
				if(wlist.indexOf(notification.prod_ID) != -1)
				{			
					for (Department dep: Test.mystore.departments)
					{	
						if(dep.ID == notification.dep_ID)
						{
							for(Item it: dep.items)
							{
								if(it.ID == notification.prod_ID)
								{
									wlist.set(notification.prod_ID, it.price);
								}
							}
						}						
					}	
				}
				if(cart.indexOf(notification.prod_ID) != -1)
				{
					cart.remove(cart.indexOf(notification.prod_ID));
					for (Department dep: Test.mystore.departments)
					{	
						if(dep.ID == notification.dep_ID)
						{
							for(Item it: dep.items)
							{
								if(it.ID == notification.prod_ID)
								{
									cart.add(it);	
								}
							}
						}						
					}
				}
				break;
		}
	}
}
