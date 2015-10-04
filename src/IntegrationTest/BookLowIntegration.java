//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package IntegrationTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.entities.Book;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookLowIntegration
{
	IBookHelper testBookHelper;
	IBook testBook1;
	IBook testBook2;
	BookMapDAO testBookMap;
	
	@Before
	public void setUp()
	{
		testBookHelper = new BookHelper();
		testBook1 = new Book("name", "title", "callNumber", 117);
		testBookMap = new BookMapDAO(testBookHelper);
	}
	
	@After
	public void reset()
	{
		testBookHelper = null;
		testBook1 = null;
		testBookMap = null;
	}
	
	@Test
	public void testCreateBookMap()
	{
		testBookMap = new BookMapDAO(testBookHelper);
		
		assertFalse(testBookMap.equals(null));
	}
	
	@Test
	public void testAddBook()
	{
		IBook testIBook = testBookMap.addBook("name", "title", "callNumber");
		
		assertFalse(testIBook.equals(null));
		assertEquals("author", testIBook.getAuthor());
		assertEquals("title", testIBook.getTitle());
		assertEquals("callNumber", testIBook.getCallNumber());
	}
	
	@Test
	public void testGetBookByID()
	{

		IBook testIBook = testBookMap.addBook("name", "title", "callNumber");

		IBook bookMap = testBookMap.getBookByID(117);

		assertEquals(bookMap, testIBook);
	}

	@Test
	public void testListBooks()
	{
		IBook testBook1 = testBookMap.addBook("author", "title", "callNumber");
		IBook testBook2 = testBookMap.addBook("name", "bookName", "refNumber");

		List<IBook> listBooks = testBookMap.listBooks();

		assertEquals(2, listBooks.size());
		assertTrue(listBooks.contains(testBook1));
		assertTrue(listBooks.contains(testBook2));
	}

	@Test
	public void testFindBooksByAuthor()
	{
		BookMapDAO bookMap = new BookMapDAO(testBookHelper);

		bookMap.addBook("author", "title", "callNumber");
		bookMap.addBook("name", "bookName", "refNumber");

		List<IBook> listBooks = bookMap.findBooksByAuthor("author");

		IBook bookResult = listBooks.get(0);

		assertEquals(1, listBooks.size());
		assertEquals("author", bookResult.getAuthor());
	}

	@Test
	public void testFindBooksByTitle()
	{
		BookMapDAO bookMap = new BookMapDAO(testBookHelper);

		bookMap.addBook("author", "title", "callNumber");
		bookMap.addBook("name", "bookName", "refNumber");

		List<IBook> listBooks = bookMap.findBooksByTitle("title");

		IBook titleResult = listBooks.get(0);
		
		assertEquals(1, listBooks.size());
		assertEquals("title", titleResult.getTitle());
	}

	@Test
	public void testFindBooksByAuthorTitle()
	{
		BookMapDAO bookMap = new BookMapDAO(testBookHelper);

		IBook testBook1 = testBookMap.addBook("author", "title", "callNumber");

		List<IBook> listBooks = bookMap.findBooksByAuthorTitle("author", "title");

		assertEquals(1, listBooks.size());
		assertTrue(listBooks.contains(testBook1));
	}
}
