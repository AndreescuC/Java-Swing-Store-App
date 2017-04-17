import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Test
{
	static Item return_value = null;
	static Store mystore = null;
	static Object[][] load_ShoppingCart()
	{
		Object[][] ret = new Object[50][4];
		if(GUI.currentCustomer.cart.isEmpty())
			return ret;
		int i=0;
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) GUI.currentCustomer.cart.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			ret[i][0] =iterator.current.item.name;
			ret[i][1] =iterator.current.item.ID;
			ret[i][2] =iterator.current.item.depID;
			ret[i][3] =iterator.current.item.price;
			i++;
		}
		return ret;
	}
	static Object[][] load_WishList()
	{
		Object[][] ret = new Object[50][4];
		if(GUI.currentCustomer.wlist.isEmpty())
			return ret;
		int i=0;
		ItemList.ItemIterator iterator = (ItemList.ItemIterator) GUI.currentCustomer.wlist.listIterator();
		while(iterator.hasNext())
		{
			iterator.current = iterator.current.next;
			ret[i][0] =iterator.current.item.name;
			ret[i][1] =iterator.current.item.ID;
			ret[i][2] =iterator.current.item.depID;
			ret[i][3] =iterator.current.item.price;
			i++;
		}
		return ret;
	}
	static Object[] load_Clients()
	{
		ArrayList<String> clients_names = new ArrayList<>();
		for(Customer cl: mystore.customers)
		{
			clients_names.add(cl.name);
		}
		return clients_names.toArray();
	}
	static Object[][] load_Products()
	{
		ArrayList<Object> ret = new ArrayList<>();
		for (Department dep: mystore.departments)
		{
			for(Item it: dep.items)
			{
				ret.add(it.name);
				ret.add(it.ID);
				ret.add(it.depID);
				ret.add(it.price);
			}
		}
		Object[][] twoDmatrix = new Object[1800][4];
		int j=0,k=0;
		for(Object i : ret)
		{
			twoDmatrix[k][j] = i;
			switch(j)
			{
				case 3:
					k++;
					j=0;
					break;
				default:
					j ++;
					break;
			}
		}
		return twoDmatrix;
	}
	public static void InitialLoad()
	{
		BufferedReader buffi;
		String line;
		String[] information;
		int i, j = 0, n, depID;		
		Department currentD = null; 
////////////////////////////////////////////////////////////////////////////////////////////////////////
////// STORE.TXT		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		try
		{
			buffi = new BufferedReader(new FileReader(new File("store.txt")));		
			mystore = Store.getInstance();
			line = buffi.readLine();
			mystore.name = line;
			while ((line = buffi.readLine()) != null)
			{		
				information = line.split(";");
				depID = Integer.parseInt(information[1]);
				switch (information[0])
				{
				case "BookDepartment": 
					currentD = new BookDepartment(information[0],depID);
					break;
				case "VideoDepartment": 
					currentD = new VideoDepartment(information[0],depID);
					break;
				case "SoftwareDepartment": 
					currentD = new SoftwareDepartment(information[0],depID);
					break;
				case "MusicDepartment": 
					currentD = new MusicDepartment(information[0],depID);
					break;
				default:
					currentD = new BookDepartment(information[0],depID);
					break;
				}			
				mystore.addDepartment(currentD);
				n = Integer.parseInt(buffi.readLine());
				for(i=0; i<n; i++)
				{
					line = buffi.readLine();
					information = line.split(";");
					mystore.departments.get(j).addItem(new Item(information[0], Integer.parseInt(information[1]), Double.parseDouble(information[2]), depID));
				}
				j++;
			}
			buffi.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////CUSTOMERS.TXT		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		try
		{
			buffi = new BufferedReader(new FileReader(new File("customers.txt")));
			n = Integer.parseInt(buffi.readLine());
			for(i=0; i<n; i++)
			{
				line = buffi.readLine();
				information = line.split(";");
				switch (information[2])
				{
				case "A":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyA())));
					break;
				case "B":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyB())));
					break;
				case "C":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyC())));
					break;
				default:
					System.out.println("Failed to identify a strategy");
				}
				
			}			
			buffi.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	public static void main(String[] args) 
	{ 
		BufferedReader buffi;
		BufferedWriter buffo;
		String line;
		String[] information;
		int i, j = 0, n, depID;		
		Department currentD = null;
		NumberFormat formatter = new DecimalFormat("#0.00");
////////////////////////////////////////////////////////////////////////////////////////////////////////
////// STORE.TXT		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		try
		{
			buffi = new BufferedReader(new FileReader(new File("store.txt")));		
			mystore = Store.getInstance();
			line = buffi.readLine();
			mystore.name = line;
			while ((line = buffi.readLine()) != null)
			{		
				information = line.split(";");
				depID = Integer.parseInt(information[1]);
				switch (information[0])
				{
				case "BookDepartment": 
					currentD = new BookDepartment(information[0],depID);
					break;
				case "VideoDepartment": 
					currentD = new VideoDepartment(information[0],depID);
					break;
				case "SoftwareDepartment": 
					currentD = new SoftwareDepartment(information[0],depID);
					break;
				case "MusicDepartment": 
					currentD = new MusicDepartment(information[0],depID);
					break;
				default:
					currentD = new BookDepartment(information[0],depID);
					break;
				}			
				mystore.addDepartment(currentD);
				n = Integer.parseInt(buffi.readLine());
				for(i=0; i<n; i++)
				{
					line = buffi.readLine();
					information = line.split(";");
					mystore.departments.get(j).addItem(new Item(information[0], Integer.parseInt(information[1]), Double.parseDouble(information[2]), depID));
				}
				j++;
			}
			buffi.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////CUSTOMERS.TXT		
////////////////////////////////////////////////////////////////////////////////////////////////////////
		try
		{
			buffi = new BufferedReader(new FileReader(new File("customers.txt")));
			n = Integer.parseInt(buffi.readLine());
			for(i=0; i<n; i++)
			{
				line = buffi.readLine();
				information = line.split(";");
				switch (information[2])
				{
				case "A":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyA())));
					break;
				case "B":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyB())));
					break;
				case "C":
					mystore.enter(new Customer(information[0],new ShoppingCart(Double.parseDouble(information[1])), new WishList(new StrategyC())));
					break;
				default:
					System.out.println("Failed to identify a strategy");
				}
				
			}			
			buffi.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////
//////EVENTS.TXT		
////////////////////////////////////////////////////////////////////////////////////////////////////////			
		try
		{
			buffi = new BufferedReader(new FileReader(new File("events.txt")));
			buffo = new BufferedWriter(new FileWriter(new File("output.txt")));
			n = Integer.parseInt(buffi.readLine());
			Item item = null;
			int index;
			Customer newclient;
			for(i=0; i<n; i++)
			{
				line = buffi.readLine();
				information = line.split(";");
				System.out.println(information[0] + "\n" + mystore.departments.get(0).observers);
				switch(information[0])
				{
					case "addItem":	
						outerloop:
						for (Department dep: mystore.departments)
						{
							for(Item it: dep.items)
							{
								if(it.ID == Integer.parseInt(information[1]))
								{
									currentD = dep;
									item = it;
									break  outerloop;
								}
							}
						}
						for (Customer cust: mystore.customers)
						{
							  if(cust.name.equals(information[3]))
							  {
								  newclient = cust;
								  if(information[2].equals("WishList"))
								  {
									  newclient.wlist.add(item);
									  newclient.wlist.latest = item;
									  System.out.println(item.ID + " :Added observer to " + currentD.ID + " : " + newclient.name);
									  currentD.addObserver(newclient);
								  }
								  else
								  {
									  newclient.cart.add(item);
									  currentD.clients.add(newclient);
								  }
								  mystore.customers.set(mystore.customers.indexOf(cust), newclient);
							  }
						}
						System.out.println(currentD.observers);
						break;			
					case "delItem":
						outerloop:
						for (Department dep: mystore.departments)
						{
							for(Item it: dep.items)
							{
								if(it.ID == Integer.parseInt(information[1]))
								{
									item = it;
									break  outerloop;
								}
							}
						}
						for (Customer cust: mystore.customers)
						{
							  if(cust.name.equals(information[3]))
							  {
								  newclient = cust;
								  if(information[2].equals("WishList"))
									  newclient.wlist.remove(item);
								  else
									  newclient.cart.remove(item);
								  mystore.customers.set(mystore.customers.indexOf(cust), newclient);
							  }
						}
						break;
					case "addProduct":
						for (Department dep: mystore.departments)
						{	
							if(dep.ID == Integer.parseInt(information[1]))
							{	
								index = mystore.departments.indexOf(dep);
								depID = dep.ID;
								dep.items.add(new Item(information[4], Integer.parseInt(information[2]), Double.parseDouble(information[3]), depID));
								mystore.departments.set(index, dep);
								dep.notifyAllObservers(new Notification(NotificationType.ADD,dep.ID,Integer.parseInt(information[2])));
								break;
							}						
						}
						break;
					case "modifyProduct":
						outerloop:
						for (Department dep: mystore.departments)
						{	
							if(dep.ID == Integer.parseInt(information[1]))
							{
								for(Item it: dep.items)
								{
									if(it.ID == Integer.parseInt(information[2]))
									{
										String name = it.name;
										depID = dep.ID;
										index = mystore.departments.indexOf(dep);
										dep.items.set(index ,new Item(name, Integer.parseInt(information[2]), Double.parseDouble(information[3]), Integer.parseInt(information[1])));
										mystore.departments.set(index, dep);
										dep.notifyAllObservers(new Notification(NotificationType.MODIFY,dep.ID,Integer.parseInt(information[2])));
										break outerloop;
									}
								}
								break;
							}						
						}
						break;
					case "delProduct":
						outerloop:
						for (Department dep: mystore.departments)
						{
							for(Item it: dep.items)
							{
								if(it.ID == Integer.parseInt(information[1]))
								{
									index = mystore.departments.indexOf(dep);
									dep.items.remove(it);
									mystore.departments.set(index, dep);
									dep.notifyAllObservers(new Notification(NotificationType.REMOVE,dep.ID,it.ID));
									break outerloop;
								}
							}
						}
						break;
					case "getItem":
						for(Customer c: mystore.customers)
						{
							if(c.name.equals(information[1]))
							{
								newclient = c;
								newclient.wlist.executeStrategy();
								newclient.wlist.remove(return_value);
								newclient.cart.add(return_value);
								mystore.customers.set(mystore.customers.indexOf(c), newclient);
								buffo.write(return_value.toString());
								break;
							}				
						}						
						break;
					case "getItems":
						for(Customer cust: mystore.customers)
						{
							if(cust.name.equals(information[2]))
							{
								if(information[1].equals("WishList"))
									buffo.write(cust.wlist.toString());
								else
									buffo.write(cust.cart.toString());
							}
						}
						break;
					case "getTotal":
						for(Customer cust: mystore.customers)
						{
							if(cust.name.equals(information[2]))
							{
								if(information[1].equals("WishList"))
									buffo.write(formatter.format(cust.wlist.getTotalPrice()) + "\n");
								else
									buffo.write(formatter.format(cust.cart.getTotalPrice()) + "\n");
							}
						}
						break;
					case "accept":
						outerloop:
						for (Department dep: mystore.departments)
						{
							if(dep.ID == Integer.parseInt(information[1]))
							{
								for(Customer cust: dep.clients)
								{
									if(cust.name.equals(information[2]))
									{
										dep.accept(cust.cart);
										break outerloop;
									}
								}
							}
						}
						break;
					case "getObservers":
						for(Department dep: mystore.departments)
						{
							if(dep.ID == Integer.parseInt(information[1]))
							{	
								buffo.write(dep.observers.toString() + "\n");
							}
						}
						break;
					case "getNotifications":
						for(Customer cust: mystore.customers)
						{
							if(cust.name.equals(information[1]))
							{
								buffo.write(cust.notifications.toString() + "\n");
							}
						}
						break;
				}
			}			
			buffi.close();
			buffo.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
