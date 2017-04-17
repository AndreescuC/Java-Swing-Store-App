import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Item
{
	String name;
	int ID;
	double price;
	int depID;
	NumberFormat formatter = new DecimalFormat("#0.00"); 
	Item(String name, int ID, double price, int D)
	{
		this.name = name;
		this.ID = ID;
		this.price = price;
		depID = D;
	}
	public String toString()
	{
		return name + ";" + ID + ";" + price + "\n" ;
	}
	
}
