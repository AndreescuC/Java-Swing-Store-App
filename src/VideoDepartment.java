public class VideoDepartment extends Department 
{
	VideoDepartment(String name, int ID)
	{
		super(name, ID);
	}

	@Override
	void accept(Visitor visitor)
	{
		visitor.visit(this);
	}		
}