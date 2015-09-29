//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip

package library.daos;

import library.entities.Book;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookHelper implements IBookHelper
{
	public IBook makeBook(String author, String title, String callNumber, int ID) 
	{
		return new Book(author, title, callNumber, ID);
	}
}
