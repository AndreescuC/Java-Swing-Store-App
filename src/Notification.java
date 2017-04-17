import java.util.Date;

public class Notification
{	
	Date issued;
	NotificationType type;
	int dep_ID;
	int prod_ID;
	
	Notification(NotificationType N, int dep_ID, int prod_ID)
	{
		issued = new Date();
		type = N;
		this.dep_ID=  dep_ID;
		this.prod_ID = prod_ID;		
	}
	public String toString()
	{
		return type + ";" + prod_ID + ";" + dep_ID;
	}
}
