//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package BookTest;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import static org.mockito.Mockito.*;

public class BookJavaTest implements IBook
{

	@Override
	public void borrow(ILoan loan)
	{
		
	}

	@Override
	public ILoan getLoan()
	{
		return null;
	}

	@Override
	public void returnBook(boolean damaged)
	{
		
	}

	@Override
	public void lose()
	{
		
	}

	@Override
	public void repair()
	{
		
	}

	@Override
	public void dispose()
	{
		
	}

	@Override
	public EBookState getState()
	{
		return null;
	}

	@Override
	public String getAuthor()
	{
		return null;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public String getCallNumber()
	{
		return null;
	}

	@Override
	public int getID() 
	{
		return 0;
	}

}
