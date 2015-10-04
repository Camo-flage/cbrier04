//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package BookTest;

import library.daos.BookHelper;
import library.interfaces.entities.IBook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class BookHelperTest
{
	private BookHelper testHelper_;
	
	@Mock
	private IBook testBook_;
	
	@Before
	public void setUp() throws Exception
	{
		testHelper_ = new BookHelper();
		testBook_ = mock(IBook.class);
	}
	
	@After
	public void reset() throws Exception
	{
		testHelper_ = null;
		testBook_ = null;
	}
	
	@Test
	public void makingBookTest()
	{
		testBook_ = testHelper_.makeBook("name", "title", "callNumber", 117);
		assertTrue(testBook_ instanceof IBook);
	}
}
