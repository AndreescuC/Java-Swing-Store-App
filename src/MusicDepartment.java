public class MusicDepartment extends Department 
{

	MusicDepartment(String name, int ID)
	{
		super(name, ID);
	}

	@Override
	void accept(Visitor visitor)
	{
		visitor.visit(this);
	}		
}