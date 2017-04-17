import java.util.ArrayList;

public class Store 
{
	String name;
	ArrayList<Department> departments = new ArrayList<>();
	ArrayList<Customer> customers = new ArrayList<>();
	
	private static Store instance = null;
	private Store() {}
	public static Store getInstance()
	{
		if(instance == null) 
		{
			instance = new Store();
		}
		return instance;
	}
	
	void enter(Customer e)
	{
		customers.add(e);
	}
	void exit(Customer e)
	{
		customers.remove(e);
	}
	ShoppingCart getShoppingCart(double budget)
	{
		return new ShoppingCart(budget);
	}
	ArrayList<Customer> getCustomers()
	{
		return customers;
	}
	ArrayList<Department> getDepartments()
	{
		return departments;
	}
	void addDepartment(Department D)
	{
		departments.add(D);
	}
	Department getDepartment(int id)
	{
		for(Department dep : this.departments)
		{
			if(dep.ID == id)
				return dep;
		}
		return null;
	}
	
	public String toString()
	{
		return "Store name: " + name + "\nDepartments:\n" + departments.toString() + "\ncustomers:\n" +
				customers.toString();
	}
}
