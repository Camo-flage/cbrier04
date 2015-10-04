//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package IntegrationTest;

import static org.junit.Assert.*;
import library.daos.LoanMapDAO;
import library.daos.LoanHelper;
import library.entities.Book;
import library.entities.Member;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class EntityEntityLowIntegration
{
	private IBook eeBook_;
	private IMember eeMember_;
	private ILoan eeLoan_;
	private ILoanDAO eeLoanMap_; 
	
	@Before
	public void setUp() throws Exception
	{
		eeBook_ = new Book("name", "title", "callNumber", 117);
		eeMember_ = new Member("Cameron", "Brierley", "123456789", "camo_brierley@hotmail.com", 1);
		
		eeLoanMap_ = new LoanMapDAO(new LoanHelper());
		eeLoan_ = eeLoanMap_.createLoan(eeMember_, eeBook_);
	}
	
	@After
	public void reset() throws Exception
	{
		eeBook_ = null;
		eeMember_ = null;
		eeLoan_ = null;
		eeLoanMap_ = null; 
	}
	
	@Test
	public void eeTestBorrow()
	{
		eeBook_.borrow(eeLoan_);
		assertTrue(eeBook_.getState().equals(EBookState.ON_LOAN));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eeTestBorrowUnavailable()
	{
		eeLoan_ = null;
		eeBook_.borrow(eeLoan_);
	}
	
	@Test
	public void eeTestGetLoan()
	{
		eeBook_.borrow(eeLoan_);
		assertTrue(eeLoan_ == eeBook_.getLoan());
	}
	   
	@Test
	public void eeTestGetLoanWhenLose()
	{
		eeLoan_ = eeBook_.getLoan();
	    assertTrue(eeLoan_ == null);
	}
}
