//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package BookTest;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.ILoan;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import library.entities.Book;

public class BookJavaTest
{
	private static Book testData_;
	
	@Mock
	private ILoan testLoan_;
	
	@Before
	public void setUp() throws Exception
	{
		testData_ = new Book("name", "title", "phoneNumber", 117);
		testLoan_ = mock(ILoan.class);
	}
	
	@After
	public void breakDown() throws Exception
	{
		testData_ = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDataAllInvalid()
	{
		testData_ = new Book(null, null, null, -1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataAuthorInvalid(){
		testData_ = new Book(null, "title", "phoneNumber", 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataTitleInvalid(){
		testData_ = new Book("name", null, "phoneNumber", 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataCallNumberInvalid(){
		testData_ = new Book("name", "title", null, 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataInvalidID(){
		testData_ = new Book("name", "title", "phoneNumber", -1);
	}
	
	@Test(expected = RuntimeException.class)
	public void testBorrowExceptionIsThrown_NullLoan()
	{
		testData_.borrow(null);
	}
	
	@Test
	public void testBorrow()
	{
		testData_.borrow(testLoan_);
		assertEquals("The return should equal the passed", EBookState.ON_LOAN, testData_.getLoan());
		assertTrue(testData_.getLoan() == testLoan_);
	}

	@Test
	public void testBorrowThrowsRuntimeException()
	{
		ILoan testLoan = null;
		testData_.setState(EBookState.AVAILABLE);
		
		try
		{
			testData_.borrow(testLoan);
			fail("ERR: Missing Runtime Exception");
		}
		
		catch(RuntimeException e)
		{
			assertTrue(true);
		}	
	}

	@Test
	public void testReturnBookFalse()
	{
		testData_.borrow(testLoan_);
		testData_.returnBook(false);
		
		assertEquals("The 'State' needs to be changed to 'Available'", EBookState.AVAILABLE, testData_.getState());
	}
	
	@Test
	public void testReturnBookTrue()
	{
		testData_.borrow(testLoan_);
		testData_.returnBook(true);
		
		assertEquals("The 'State' needs to be changed to 'Damaged'", EBookState.DAMAGED, testData_.getState());
	}
	
	///
	
	
}