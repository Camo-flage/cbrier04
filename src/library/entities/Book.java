//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip

package library.entities;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook 
{
	private String author;
	private String title;
	private String callNumber;
	private int ID;
	private EBookState state;
	private ILoan loan;

	public Book(String author, String title, String callNumber, int ID) 
	{
		if (author != null && !author.isEmpty() && title != null && !title.isEmpty() && callNumber != null && !callNumber.isEmpty() && ID > 0)
		{
			throw new IllegalArgumentException("Constructor contains bad parameters");
		}
		
		this.author = author;
		this.title = title;
		this.callNumber = callNumber;
		this.ID = ID;
		this.state = EBookState.AVAILABLE;
		this.loan = null;
	}

	public void borrow(ILoan loan) 
	{
		if(loan == null)
		{
			throw new IllegalArgumentException(String.format("Book Boeeow loan cannot be null."));
		}
		
		if(!(state == EBookState.AVAILABLE))
		{
			throw new RuntimeException(String.format("Illegal operation in state : %s", state));
		}
		
		this.loan = loan;
		state = EBookState.ON_LOAN;
	}

	public ILoan getLoan() 
	{
		return loan;
	}

	public void returnBook(boolean damaged)
	{
		if (!(state == EBookState.ON_LOAN || state == EBookState.LOST))
		{
			throw new RuntimeException(String.format("Illegal operation in state : %s", state));
		}
		
		loan = null;
		
		if (damaged)
		{
			state = EBookState.DAMAGED;
		}
		else
		{
			state = EBookState.AVAILABLE;
		}
	}

	public void lose()
	{
		if (!(state == EBookState.ON_LOAN))
		{
			throw new RuntimeException(String.format("Illegal operation in state : %s", state));
		}
		
		state = EBookState.LOST;
	}

	public void repair()
	{
		if (!(state == EBookState.DAMAGED))
		{
			throw new RuntimeException(String.format("Illegal operation in state : %s", state));
		}
		
		state = EBookState.AVAILABLE;
	}

	public void dispose()
	{
		if (!(state == EBookState.AVAILABLE || state == EBookState.DAMAGED || state == EBookState.LOST))
		{
			throw new RuntimeException(String.format("Illegal operation in state : %s", state));
		}
		
		state = EBookState.DISPOSED;
	}

	public EBookState getState() 
	{
		return state;
	}

	public String getAuthor()
	{
		return author;
	}

	public String getTitle()
	{
		return title;
	}

	public String getCallNumber()
	{
		return callNumber;
	}

	public int getID()
	{
		return ID;
	}
}