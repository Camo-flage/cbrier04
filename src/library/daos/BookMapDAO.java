//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip

package library.daos;

import java.util.*;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookMapDAO implements IBookDAO
{
	private int ID;
	private Map<Integer, IBook> bookMap;
	private IBookHelper helper;

	public BookMapDAO(IBookHelper helper)
	{
		if (helper == null )
		{
			throw new IllegalArgumentException(String.format("BookMapDAO; the helper cannot be null."));
		}
		
		ID = 1;
		this.helper = helper; 
		bookMap = new HashMap<Integer, IBook>();
	}
	
	public BookMapDAO(IBookHelper helper, Map<Integer, IBook> bookMap)
	{
		this(helper);
		
		if (helper == null )
		{
			throw new IllegalArgumentException(String.format("BookDAO; the bookMap cannot be null."));
		}
		
		this.bookMap = bookMap;
	}
	
	public IBook addBook(String author, String title, String callNumber)
	{
		int ID = getNextID();
		IBook book = helper.makeBook(author, title, callNumber, ID);
		bookMap.put(Integer.valueOf(ID), book);
		
		return book;
	}

	public IBook getBookByID(int ID)
	{
		if (bookMap.containsKey(Integer.valueOf(ID)))
		{
			return bookMap.get(Integer.valueOf(ID));
		}
		
		return null;
	}

	public List<IBook> listBooks()
	{
		List<IBook> ibooklist = new ArrayList<IBook>(bookMap.values());
		
		return Collections.unmodifiableList(ibooklist);
	}

	public List<IBook> findBooksByAuthor(String author)
	{
		if ( author == null || author.isEmpty())
		{
			throw new IllegalArgumentException(String.format("BookMapDAO; the author cannot be null"));
		}
		
		List<IBook> ibooklist = new ArrayList<IBook>();
		
		for (IBook b : bookMap.values())
		{
			if (author.equals(b.getAuthor()))
			{
				ibooklist.add(b);
			}
		}
		
		return Collections.unmodifiableList(ibooklist);
	}

	public List<IBook> findBooksByTitle(String title)
	{
		if ( title == null || title.isEmpty())
		{
			throw new IllegalArgumentException(String.format("BookMapDAO; author cannot be null"));
		}
		
		List<IBook> list = new ArrayList<IBook>();
		
		for (IBook book : bookMap.values())
		{
			if (title.equals(book.getTitle()))
			{
				list.add(book);
			}
		}
		
		return Collections.unmodifiableList(list);
	}

	public List<IBook> findBooksByAuthorTitle(String author, String title)
	{
		if ( title == null || title.isEmpty() ||  author == null || author.isEmpty())
		{
			throw new IllegalArgumentException(String.format("BookMapDAO; the author and title cannot be null"));
		}
		
		List<IBook> list = new ArrayList<IBook>();
		
		for (IBook book : bookMap.values())
		{
			if (author.equals(book.getAuthor()) && title.equals(book.getTitle()))
			{
				list.add(book);
			}
		}
		
		return Collections.unmodifiableList(list);
	}
	
	private int getNextID() {
		return ID++;
	}
}

