public class BookDepartment extends Department 
{

	BookDepartment(String name, int ID)
	{
		super(name, ID);
	}

	@Override
	void accept(Visitor visitor)
	{
		visitor.visit(this);
	}	
}
