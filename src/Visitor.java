public interface Visitor
{
	public void visit(BookDepartment dep);
	public void visit(SoftwareDepartment dep);
	public void visit(VideoDepartment dep);
	public void visit(MusicDepartment dep);
}