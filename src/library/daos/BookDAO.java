//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip

package library.daos;

import java.util.*;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookDAO implements IBookDAO
{
	private IBookHelper helper;
	private List<IBook> bookList;
	private int ID;
	
	public BookDAO(IBookHelper helper)
	{
		if(helper == null)
		{
			throw new IllegalArgumentException("Helper cannot be null");		
		}
		
		this.helper = helper;
		this.bookList = new ArrayList<IBook>();
		this.ID = 1;
	}
	
	public IBook addBook(String author, String title, String callNumber)
	{
		IBook books = helper.makeBook(author, title, callNumber, ID);
		bookList.add(books);
		return books;
	}

	public IBook getBookByID(int ID)
	{
		for(IBook books : bookList)
		{
			if(books.getID() == ID)
			{
				return books;
			}
		}
		return null;
	}

	public List<IBook> listBooks()
	{
		return bookList;
	}

	public List<IBook> findBooksByAuthor(String author)
	{
		List<IBook> authorResult = new ArrayList<IBook>();
		
		for(IBook books : bookList)
		{
			if(author.compareTo(books.getAuthor()) == 0)
			{
				authorResult.add(books);
			}
		}
		
		return authorResult;
	}

	public List<IBook> findBooksByTitle(String title)
	{
		List<IBook> titleResult = new ArrayList<IBook>();
		
		for(IBook books : bookList)
		{
			if(title.compareTo(books.getAuthor()) == 0)
			{
				titleResult.add(books);
			}
		}
		
		return titleResult;
	}

	public List<IBook> findBooksByAuthorTitle(String author, String title)
	{
		List<IBook> atResult = new ArrayList<IBook>();
		
		for (IBook books : bookList)
		{
			if (title.compareTo(books.getTitle()) == 0 && author.compareTo(books.getAuthor()) == 0)
			{
				atResult.add(books);
			}
		}
		
		return atResult;
	}
}
