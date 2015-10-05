//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package IntegrationTest;

import java.util.*;
import library.BorrowUC_CTL;
import library.BorrowUC_UI;
import library.interfaces.*;
import library.interfaces.daos.*;
import library.interfaces.entities.*;
import library.interfaces.hardware.*;
import org.junit.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SwipeCardHighIntegration
{
	private BorrowUC_CTL testControlClass_;
	private IBookDAO testBookMap_;
	private IMemberDAO testMemberMap_;
	private ILoanDAO testLoanMap_;
	private IDisplay display_;
	
	private IMember testBorrower_;
	private int testMemberID_;
	private ILoan testLoan_;
	
	@Mock
	private ICardReader testCardReader_;
	@Mock
	private IScanner testScanner_;
	@Mock
	private IPrinter testPrinter_;
	@Mock
	private IDisplay testDisplay_;
	@Mock
	private BorrowUC_UI testUI_;

	@Before
	public void setUp() throws Exception
	{
		testBookMap_ = mock(IBookDAO.class);
		testMemberMap_ = mock(IMemberDAO.class);
		testLoanMap_ = mock(ILoanDAO.class);
		testBorrower_ = mock(IMember.class);
		testMemberID_ = 1;
		testLoan_ = mock(ILoan.class);
		
		testCardReader_ = mock(ICardReader.class);
		testScanner_ = mock(IScanner.class);
		testPrinter_ = mock(IPrinter.class);
		testDisplay_ = mock(IDisplay.class);
		testUI_ = mock(BorrowUC_UI.class);
		
		testControlClass_ = new BorrowUC_CTL(testCardReader_, testScanner_, testPrinter_, testDisplay_, testBookMap_, testLoanMap_, testMemberMap_);
	}

	@After
	public void reset() throws Exception
	{	
		testControlClass_ = null;
		
		testBookMap_ = null;
		testMemberMap_ = null;
		testLoanMap_ = null;
		
		testCardReader_ = null;
		testDisplay_ = null;
		testPrinter_ = null;
		testScanner_ = null;
		testUI_ = null;
	}
	
	@Test
	public void testSwipeCard()
	{
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		when(testBorrower_.hasOverDueLoans()).thenReturn(false);
		when(testBorrower_.hasReachedLoanLimit()).thenReturn(false);
		when(testBorrower_.hasFinesPayable()).thenReturn(false);
		when(testBorrower_.hasReachedFineLimit()).thenReturn(false);
		when(testBorrower_.getID()).thenReturn(testMemberID_);
		when(testBorrower_.getFirstName()).thenReturn("");
		when(testBorrower_.getLastName()).thenReturn("");
		when(testBorrower_.getContactPhone()).thenReturn("");
		
		List<ILoan> loanList = mock(List.class);
		when(testBorrower_.getLoans()).thenReturn(loanList);
		
		Iterator<ILoan> iterator = mock(Iterator.class);	
		when(loanList.iterator()).thenReturn(iterator);
		when(iterator.hasNext()).thenReturn(true, false);
		when(iterator.next()).thenReturn(testLoan_);
		
		when(testLoan_.toString()).thenReturn("loanDetails");
		when(display_.getDisplay()).thenReturn(null);
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		verify(testMemberMap_).getMemberByID(testMemberID_);
		verify(testBorrower_).hasOverDueLoans();
		verify(testBorrower_).hasReachedLoanLimit();
		verify(testBorrower_).hasFinesPayable();
		verify(testBorrower_).hasReachedFineLimit();
		verify(testBorrower_).getID();
		verify(testBorrower_).getFirstName();
		verify(testBorrower_).getLastName();
		verify(testBorrower_).getContactPhone();
		verify(testUI_).displayMemberDetails(testMemberID_, " ", "");
		verify(testCardReader_).setEnabled(false);
		verify(testScanner_).setEnabled(true);
		verify(testUI_).setState(EBorrowState.SCANNING_BOOKS);
		verify(testUI_).displayScannedBookDetails("");
		verify(testUI_).displayPendingLoan("");
		verify(testBorrower_, atLeast(2)).getLoans();
		assertTrue(testLoan_.toString() == "loanDetails");
		verify(testUI_).displayExistingLoan("loanDetails");	
	}
	
	@Test(expected = RuntimeException.class)
	public void testSwipeCardCancelled()
	{
		testControlClass_.cancelled();
		testControlClass_.cardSwiped(testMemberID_);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSwipedCardCompleted()
	{
		testControlClass_.scansCompleted();
		testControlClass_.cardSwiped(testMemberID_);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSwipeCardLoanConfirmed()
	{
		testControlClass_.loansConfirmed();
		testControlClass_.cardSwiped(testMemberID_);
	}
	
	@Test(expected = RuntimeException.class)
	public void testSwipeCardLoanRejected()
	{
		testControlClass_.loansRejected();
		testControlClass_.cardSwiped(testMemberID_);
	}	
	
	@Test
	public void testSwipeCardNoMember()
	{
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(null);
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		verify(testUI_).displayErrorMessage(String.format("Member ID not found", testMemberID_));
	}
	
	@Test
	public void testSwipeCardOverDue()
	{
		when(testBorrower_.hasOverDueLoans()).thenReturn(true);
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		
		assertTrue(testControlClass_.getState() == EBorrowState.BORROWING_RESTRICTED);
		verify(testCardReader_).setEnabled(false);
		verify(testScanner_, atLeast(2)).setEnabled(false);
		verify(testUI_).displayErrorMessage(String.format("Member cannot borrow.", testBorrower_.getID()));
	}
	
	@Test
	public void testSwipeCardLoanLimit() 
	{
	when(testBorrower_.hasReachedLoanLimit()).thenReturn(true);
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		
		assertTrue(testControlClass_.getState() == EBorrowState.BORROWING_RESTRICTED);
		verify(testCardReader_).setEnabled(false);
		verify(testScanner_, atLeast(2)).setEnabled(false);
		verify(testUI_).displayErrorMessage(String.format("Member cannot borrow.", testBorrower_.getID()));
	}
	
	@Test
	public void testSwipeCardFineLimit() 
	{
	when(testBorrower_.hasReachedFineLimit()).thenReturn(true);
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		
		assertTrue(testControlClass_.getState() == EBorrowState.BORROWING_RESTRICTED);
		verify(testCardReader_).setEnabled(false);
		verify(testScanner_, atLeast(2)).setEnabled(false);
		verify(testUI_).displayErrorMessage(String.format("Member cannot borrow.", testBorrower_.getID()));
	}
	
	@Test
	public void testSwipeCardFines()
	{
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		when(testBorrower_.hasFinesPayable()).thenReturn(true);
		when(testBorrower_.getFineAmount()).thenReturn(1.5f);
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		verify(testUI_).displayOutstandingFineMessage(1.5f);
	}
	
	@Test
	public void displayExisitingLoan()
	{
		when(testMemberMap_.getMemberByID(testMemberID_)).thenReturn(testBorrower_);
		testControlClass_.initialise();
		testControlClass_.cardSwiped(testMemberID_);
		verify(testUI_).displayExistingLoan("");
	}
}