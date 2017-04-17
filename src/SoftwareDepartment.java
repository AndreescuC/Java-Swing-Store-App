public class SoftwareDepartment extends Department 
{

	SoftwareDepartment(String name, int ID)
	{
		super(name, ID);
	}

	@Override
	void accept(Visitor visitor)
	{
		visitor.visit(this);
	}		
}