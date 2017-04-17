import java.util.ArrayList;

abstract class Department implements Subject
{
	String name;
	int ID;
	ArrayList<Item> items = new ArrayList<>();
	ArrayList<Customer> clients = new ArrayList<>();
	ArrayList<Customer> observers = new ArrayList<>();
	
	Department(String name, int ID)
	{
		this.name = name;
		this.ID = ID;
		items = new ArrayList<>();
		clients = new ArrayList<>();
		observers = new ArrayList<>();
	}
	
	void enter(Customer buyer)
	{
		clients.add(buyer);
	}
	void exit(Customer client)
	{
		clients.remove(clients.indexOf(client));
	}
	ArrayList<Customer> getCustomers()
	{
		return clients;
	}
	void addItem(Item p)
	{
		items.add(p);
	}
	ArrayList<Item> getItems()
	{
		return items;
	}
	public void addObserver(Customer o)
	{
		if(!(observers.contains(o)))
			observers.add(o);
	}
	public void removeObserver(Customer o)
	{
		observers.remove(o);
	}
	public void notifyAllObservers(Notification N)
	{
		ArrayList<Customer> observers_copy = (ArrayList<Customer>) observers.clone();
		for (Customer Johnny: observers_copy)
		{
			//System.out.println("Updating " + Johnny);
			Johnny.update(N);
		}
	}
	public String toString()
	{
		return "Department name: " + name + "\nDepartment ID: " + ID + "\nItems:\n" + items.toString() + "\nclients:\n" +
				clients.toString() + "\nObservers:\n" + observers.toString();
	}
	abstract void accept(Visitor visitor);
}