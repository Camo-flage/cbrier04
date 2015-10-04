//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package BookTest;

import library.daos.BookMapDAO;
import library.entities.Book;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookMapTest 
{
	  private IBookDAO bookMap_;
	  
	  @Mock
	  private IBookHelper testHelper_;

	  @Before
	  public void setUp() throws Exception
	  {
		  testHelper_ = mock(IBookHelper.class);

		  testHelper_.makeBook("author", "title", "callNumber", 117);
		  testHelper_.makeBook("name", "bookName", "refNumber", 118);

		  bookMap_ = new BookMapDAO(testHelper_); 
	  }
	  
	  @After
	  public void emptyData() throws Exception
	  {
		  bookMap_ = null;
		  testHelper_ = null;
	  }
	  
	  @Test
	  public void testBookMapDaoCreation()
	  {
		  bookMap_ = new BookMapDAO(testHelper_);
		  assertTrue(bookMap_ instanceof IBookDAO);
	  }
	  
	  
	  @Test(expected=IllegalArgumentException.class)
	  public void testBookMapDaoCreationError()
	  {
		  bookMap_ = new BookMapDAO(null);
	  }
	  
	  @Test
	  public void  testAddBook()
	  {
		  assertTrue(bookMap_.addBook("author", "title", "callNumber") instanceof IBook);
		  assertEquals(117, bookMap_.listBooks().size());
	  }
	  
	  @Test
	  public void testAddMultipleBook()
	  {
		  assertTrue(bookMap_.addBook("author", "title", "callNumber") instanceof IBook);
		  assertTrue(bookMap_.addBook("name", "bookName", "refNumber") instanceof IBook);
		  
		  assertEquals(118, bookMap_.listBooks().size());
	  }
	  
	  @Test
	  public void testGetBookByID()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");
		  
		  IBook testBook1 = bookMap_.getBookByID(117);
		  assertTrue(testBook1.getID() == 117);
		  
		  IBook testBook2 = bookMap_.getBookByID(118);
		  assertTrue(testBook2.getID() == 118);
	  }

	  @Test
	  public void testGetIDNotFound()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  IBook testBookID = bookMap_.getBookByID(1);
		  assertTrue(testBookID == null);
	  }	  
	  
	  @Test
	  public void testListBooks()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.listBooks();
	    
		  assertEquals(2, listBooks.size());

		  assertEquals(117, listBooks.get(0).getID());
		  assertEquals(118, listBooks.get(1).getID());
	  }	  
	  
	  @Test
	  public void testFindBooksByAuthor()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByAuthor("author");

		  assertEquals(1, listBooks.size());
	  }
	  
	  @Test
	  public void testFindBooksByAuthorNull()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByAuthor("Cameron");
		  assertEquals(0, listBooks.size());
	  }
	  
	  @Test(expected=IllegalArgumentException.class)
	  public void testFindBooksByAuthorNullError()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  @SuppressWarnings("unused")
		  List<IBook> listBooks = bookMap_.findBooksByAuthor(null);
	  }
	  
	  @Test
	  public void testFindBooksByTitle()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByTitle("title");
		  assertEquals(1, listBooks.size());
	  }
	  
	  @Test
	  public void testFindBooksByTitleNull()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByTitle("A Book");
		  assertEquals(0, listBooks.size());
	  }
	  
	  
	  @Test(expected=IllegalArgumentException.class)
	  public void testFindBooksByTitleNullError()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");
		  
		  @SuppressWarnings("unused")
		  List<IBook> listBooks = bookMap_.findBooksByTitle(null);
	  }
	  
	  @Test
	  public void testFindAuthorTitle()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByAuthorTitle("author", "title");
		  assertEquals(1, listBooks.size());
	  }
	  
	  
	  @Test
	  public void testFindAuthorTitleNull()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  List<IBook> listBooks = bookMap_.findBooksByAuthorTitle("name", "something");
		  assertEquals(0, listBooks.size());
	  }
	  
	  
	  @Test(expected=IllegalArgumentException.class)
	  public void testFindAuthorTitleNullAuthor()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  @SuppressWarnings("unused")
		  List<IBook> listBooks = bookMap_.findBooksByAuthorTitle(null, "title");
	  }
	  
	  @Test(expected=IllegalArgumentException.class)
	  public void testFindAuthorTitleNullTitle()
	  {
		  bookMap_.addBook("author", "title", "callNumber");
		  bookMap_.addBook("name", "bookName", "refNumber");

		  @SuppressWarnings("unused")
		  List<IBook> bookList = bookMap_.findBooksByAuthorTitle("name", null);
	  }
}