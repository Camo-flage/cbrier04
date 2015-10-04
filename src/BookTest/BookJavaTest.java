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
		testData_ = new Book("name", "title", "callNumber", 117);
		testLoan_ = mock(ILoan.class);
	}
	
	@After
	public void emptyData() throws Exception
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
		testData_ = new Book(null, "title", "callNumber", 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataTitleInvalid(){
		testData_ = new Book("name", null, "callNumber", 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataCallNumberInvalid(){
		testData_ = new Book("name", "title", null, 117);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testDataInvalidID(){
		testData_ = new Book("name", "title", "callNumber", -1);
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
	public void testBorrowLoan()
	{
		testData_.borrow(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBorrowOnLoan()
	{
		testData_.borrow(testLoan_);
		testData_.borrow(testLoan_);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetLoan()
	{
		testData_.borrow(testLoan_);
		assertTrue(testData_.getLoan() == testLoan_);
	}
	
	@Test
	public void testGetLoanAvailable()
	{
		assertTrue(testData_.getLoan() == null);
	}
	
	@Test
	public void testBookReturnUndamaged()
	{
		testData_.borrow(testLoan_);
		testData_.returnBook(false);
		assertEquals(EBookState.AVAILABLE, testData_.getState());
	}
	
	  @Test
	  public void testBookReturnDamaged()
	  {
	    testData_.borrow(testLoan_);
	    testData_.returnBook(true);
	    assertEquals(EBookState.DAMAGED, testData_.getState());
	  }
	  
	  @Test
	  public void testBookReturnRemovedLoan()
	  {
		  testData_.borrow(testLoan_);
		  testData_.returnBook(false);
		  assertEquals(EBookState.AVAILABLE, testData_.getState());
		  assertTrue(testData_.getLoan() == null);
	  }
	  
	  @Test(expected = RuntimeException.class)
	  public void testBookReturnAvailable()
	  {
	    testData_.returnBook(false);
	  }
	  
	  @Test
	  public void testLose()
	  {
	    testData_.borrow(testLoan_);
	    testData_.lose();
	    assertEquals(EBookState.LOST, testData_.getState());
	  }
	  
	  @Test(expected = RuntimeException.class)
	  public void testLoseAvailable()
	  {
	    testData_.lose();
	  }
	  
	  
	  @Test
	  public void testRepair()
	  {
	    testData_.borrow(testLoan_);
	    testData_.returnBook(true);
	    testData_.repair();
	    assertEquals(EBookState.AVAILABLE, testData_.getState());
	  }
	  
	  
	  @Test(expected = RuntimeException.class)
	  public void testRepairUndamaged()
	  {
	    testData_.repair();
	  }

	  @Test
	  public void testDispose() 
	  {
	    testData_.dispose();
	    assertEquals(EBookState.DISPOSED, testData_.getState());
	  }
	  
	  @Test(expected = RuntimeException.class)
	  public void testDisposeUnavailable()
	  {
	    testData_.borrow(testLoan_);
	    testData_.dispose();
	  }
	  
	  
	  @Test(expected = RuntimeException.class)
	  public void testDisposeAlreadyDisposed()
	  {
	    testData_.dispose();
	    assertEquals(EBookState.DISPOSED, testData_.getState());
	    testData_.dispose();
	  }
	  
	  @Test
	  public void testGetStateAvailablility()
	  {
	    assertEquals(EBookState.AVAILABLE, testData_.getState());
	  }
	  
	  
	  @Test
	  public void testGetStateOnLoan()
	  {
	    testData_.borrow(testLoan_);
	    assertEquals(EBookState.ON_LOAN, testData_.getState());
	  }
	  
	  
	  @Test
	  public void testGetStateDamaged()
	  {
	    testData_.borrow(testLoan_);
	    testData_.returnBook(true);
	    assertEquals(EBookState.DAMAGED, testData_.getState());
	  }
	  
	  
	  @Test
	  public void testGetStateLost()
	  {
	    testData_.borrow(testLoan_);
	    testData_.lose();
	    assertEquals(EBookState.LOST, testData_.getState());
	  }
	  
	  
	  @Test
	  public void testGetStateDisposed()
	  {
	    testData_.dispose();
	    assertEquals(EBookState.DISPOSED,  testData_.getState());
	  }

	  @Test
	  public void testGetAuthor()
	  {
	    assertEquals("author", testData_.getAuthor());
	  }

	  @Test
	  public void testGetTitle()
	  {
	    assertEquals("title", testData_.getTitle());
	  }

	  @Test
	  public void testGetCallNumber()
	  {
	    assertEquals("callNumber", testData_.getCallNumber());
	  }
	  
	  @Test
	  public void testGetID()
	  {
	    assertEquals(117, testData_.getID());
	  }
}