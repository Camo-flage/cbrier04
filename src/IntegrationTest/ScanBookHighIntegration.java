//ITC205 Assignment 3
//Files Added By: Cameron Brierley, ID:11497472
//Program Created By: J. Tulip
package IntegrationTest;

import java.util.*;
import org.junit.*;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import library.BorrowUC_CTL;
import library.BorrowUC_UI;
import library.daos.*;
import library.hardware.*;
import library.interfaces.EBorrowState;
import library.interfaces.daos.*;
import library.interfaces.entities.*;
import library.interfaces.hardware.*;

public class ScanBookHighIntegration
{	
	private BorrowUC_CTL testControlClass_;
	private IBookDAO testBookMap_;
	private ILoanDAO testLoanMap_;
	private IMemberDAO testMemberMap_;
	
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
	  
	Date takenDate;
	Date dueReturnDate;
	Calendar calender_;
	
	@Before
	public void setUp() throws Exception
	{
		testCardReader_ = mock(CardReader.class);
		testScanner_ = mock(IScanner.class);
		testPrinter_ = mock(IPrinter.class);
		testDisplay_ = mock(IDisplay.class);
		testUI_ = mock(BorrowUC_UI.class);

		testBookMap_ = new BookMapDAO(new BookHelper());
		testMemberMap_ = new MemberMapDAO(new MemberHelper());
		testLoanMap_ = new LoanMapDAO(new LoanHelper());

		testControlClass_ = new BorrowUC_CTL(testCardReader_, testScanner_, testPrinter_, testDisplay_, testBookMap_, testLoanMap_, testMemberMap_);

		IBook[] book = new IBook[15];
		IMember[] member = new IMember[6];

		book[0]  = testBookMap_.addBook("author1", "title1", "callNumber1");
		book[1]  = testBookMap_.addBook("author1", "title2", "callNumber2");
		book[2]  = testBookMap_.addBook("author1", "title3", "callNumber3");
		book[3]  = testBookMap_.addBook("author1", "title4", "callNumber4");
		book[4]  = testBookMap_.addBook("author2", "title5", "callNumber5");
		book[5]  = testBookMap_.addBook("author2", "title6", "callNumber6");
		book[6]  = testBookMap_.addBook("author2", "title7", "callNumber7");
		book[7]  = testBookMap_.addBook("author2", "title8", "callNumber8");
		book[8]  = testBookMap_.addBook("author3", "title9", "callNumber9");
		book[9]  = testBookMap_.addBook("author3", "title10", "callNumber10");
		book[10] = testBookMap_.addBook("author4", "title11", "callNumber11");
		book[11] = testBookMap_.addBook("author4", "title12", "callNumber12");
		book[12] = testBookMap_.addBook("author5", "title13", "callNumber13");
		book[13] = testBookMap_.addBook("author5", "title14", "callNumber14");
		book[14] = testBookMap_.addBook("author5", "title15", "callNumber15");

		member[0] = testMemberMap_.addMember("firstName0", "lastName0", "0001", "email0");
		member[1] = testMemberMap_.addMember("firstName1", "lastName1", "0002", "email1");
		member[2] = testMemberMap_.addMember("firstName2", "lastName2", "0003", "email2");
		member[3] = testMemberMap_.addMember("firstName3", "lastName3", "0004", "email3");
		member[4] = testMemberMap_.addMember("firstName4", "lastName4", "0005", "email4");
		member[5] = testMemberMap_.addMember("firstName5", "lastName5", "0006", "email5");

		calender_ = Calendar.getInstance();
		Date now = calender_.getTime();
		
		for(int i = 0; i < 2; i++)
		{
			ILoan testLoan = testLoanMap_.createLoan(member[1], book[i]);
			testLoanMap_.commitLoan(testLoan);
		}
		
	    calender_.setTime(now);
	    calender_.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
	    Date checkDate = calender_.getTime();     
	    testLoanMap_.updateOverDueStatus(checkDate);
	    
	    member[2].addFine(10.0f);
	    
	    for(int i = 2; i < 7; i++)
	    {
	    	ILoan testLoan = testLoanMap_.createLoan(member[3], book[i]);
	    	testLoanMap_.commitLoan(testLoan);
	    }
	}

	@After
	public void reset() throws Exception
	{
	    testControlClass_ = null;
	    
	    testBookMap_ = null;
	    testLoanMap_ = null;
	    testMemberMap_ = null;
	    
	    testCardReader_ = null;
	    testScanner_ = null;
	    testPrinter_ = null;
	    testDisplay_ = null;
	    testUI_ = null;
	}
	
	@Test
	public void scanBookTest()
	{
		testControlClass_.setState(EBorrowState.INITIALIZED);
		testControlClass_.cardSwiped(1);
		verify(testCardReader_).setEnabled(false);
		verify(testScanner_).setEnabled(true);
		verify(testUI_).setState(EBorrowState.SCANNING_BOOKS);
		verify(testUI_).displayMemberDetails(1, "firstName0 lastName0", "0001");
		verify(testUI_).displayExistingLoan(any(String.class));    
		assertEquals(EBorrowState.SCANNING_BOOKS, testControlClass_.getState());

		testControlClass_.bookScanned(15);
		verify(testCardReader_).setEnabled(true);
		verify(testScanner_).setEnabled(true);
		verify(testUI_).setState(EBorrowState.SCANNING_BOOKS);
		verify(testUI_).displayScannedBookDetails("");
		verify(testUI_).displayPendingLoan("");
		assertEquals(EBorrowState.SCANNING_BOOKS, testControlClass_.getState());
	}
}