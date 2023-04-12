package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/*
Test Coverage of assigned methods

	Email   addBcc(String... emails)................100%

	Email   addCc(String email).....................100%

	void    addHeader(String name, String value)....100%

	Email   addReplyTo(String email, String name)...100%
	
	void    buildMimeMessage()......................73.8%

	String  getHostName()...........................100%

	Session getMailSession()........................83.4%

	Date    getSentDate()...........................100%

	int     getSocketConnectionTimeout()............100%

	Email   setFrom(String email)...................100%
*/

public class EmailTest {
	
	//Concrete Email class for testing
	private Email email;
	
	//Sets up Email instance, invoked before each test case
	@Before
	public void setUpEmailTest() throws Exception
	{
		email = new EmailConcrete();
	}
	
	//Cleans up any resources that have been allocated
	@After
	public void tearDownEmailTest() throws Exception
	{
		//Nothing needed
	}
	
	/*
	 * addBcc(String... emails) Tests
	 */
	
	//Add multiple BCCs to an email then assert that first BCC of email matches what was added
	@Test
	public void addBccTest() throws EmailException
	{
		//Add BCCs to email
		String[] testEmails = { "ab@bc.com", "a.b@c.org", 
		"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
		email.addBcc(testEmails);
		//Get list of email's BCCs
		List<InternetAddress> bcc = email.getBccAddresses();
		//Check if first BCC of email matches what was added
		assertEquals("ab@bc.com", bcc.get(0).toString());
	}
	
	//Add multiple BCCs to an email then assert that number of BCCs on email matches amount added
	@Test
	public void bccCountTest() throws EmailException
	{
		//Add BCCs to email
		String[] testEmails = { "ab@bc.com", "a.b@c.org", 
		"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
		email.addBcc(testEmails);
		//Check if number of BCCs on email matches how many were added
		assertEquals(3, email.getBccAddresses().size());
	}
	
	//Add no BCCs to email and check if exception is thrown
	@Test(expected = EmailException.class)
	public void checkNoEmailsTest() throws EmailException
	{
		//Add empty array of BCCs
		String[] testEmails = null;
		email.addBcc(testEmails);
	}
	
	/*
	 * addCc(String email) Tests
	 */
	
	//Add CC to email then assert that first CC of email matches what was added
	@Test
	public void checkAddCcTest() throws EmailException
	{
		//Add CC to email
		String testCC = "abc@gmail.com";
		email.addCc(testCC);
		//Get list of email's CCs
		List<InternetAddress> ccs = email.getCcAddresses();
		//Check if first CC of email matches what was added
		assertEquals("abc@gmail.com", ccs.get(0).toString());
	}
	
	//Add invalid CC to email then check if exception is thrown
	@Test(expected = EmailException.class)
	public void checkExceptionTest() throws EmailException
	{
		//Add invalid CC to email 
		String invalidEmail = "abc";
		email.addCc(invalidEmail);
	}
	
	/*
	 * addHeader(String name, String value) Tests
	 */
	
	//Add a header to email then assert that first header of email matches what was added
	@Test
	public void addHeaderTest() throws EmailException
	{
		//Add header to email
		email.addHeader("1", "Sendmail");
		String testHeader = email.headers.get("1");
		//Check that email's header matches what was added
		assertEquals("Sendmail", testHeader);
	}
	
	//Add header with invalid name to email then check if exception is thrown
	@Test(expected = IllegalArgumentException.class)
	public void addHeaderWithInvalidNameTest() throws EmailException
	{
		//Add header with null name to email
		String testHeaderValue = "Sendmail";
		email.addHeader(null, testHeaderValue);
	}
	
	//Add header with invalid value to email then check if exception is thrown
	@Test(expected = IllegalArgumentException.class)
	public void addHeaderWithInvalidValueTest() throws EmailException
	{
		//Add header with null value to email
		String testHeaderName = "abc@gmail.com";
		email.addHeader(testHeaderName, null);
	}
	
	/*
	 * addReplyTo(String email, String name)
	 */
	
	//Add a replyTo address to email then asert that address of first replyTo matches what was added
	@Test
	public void addReplyToTest() throws EmailException
	{
		//Add replyTo to email
		String testEmail = "JohnSmith@gmail.com";
		String testName = "John Smith";
		email.addReplyTo(testEmail, testName);
		//Get list of email's reply to addresses
		List<InternetAddress> replyTos = email.getReplyToAddresses();
		//Check if address of first reply to matches email and name that was added
		assertEquals("John Smith <JohnSmith@gmail.com>", replyTos.get(0).toString());
	}
	
	//Add a replyTo address with an invalid email and assert that an exception was thrown
	@Test(expected = EmailException.class)
	public void addReplyToWithInvalidEmailTest() throws EmailException
	{
		//Add replyTo to email
		String testEmail = "JohnSmith";
		String testName = "John Smith";
		email.addReplyTo(testEmail, testName);
	}
	
	/*
	 * buildMimeMessage() Tests
	 */
	
	//Set a mimeMessage's information, build it, then assert that the message is not null
	@Test
	public void buildMimeMessageTest() throws EmailException
	{		
		//Set message's hostname
		email.setHostName("localhost");
		//Set message's subject and charset
		email.setSubject("Message subject");
		email.setCharset("US-ASCII");
		//Set message's fromAddress
		email.setFrom("abc@gmail.com");
		//Set message's recipient addresses
		email.addTo("def@gmail.com");
		email.addBcc("ghi@gmail.com");
		email.addCc("jkl@gmail.com");
		email.addReplyTo("mno@gmail.com");
		//Set message's header
		email.addHeader("1", "Sendmail");
		//Set message's date to current date
		email.setSentDate(null);
		//Build the message
		email.buildMimeMessage();
		//Check that the email's mimeMessage has been created
		assertNotNull(email.getMimeMessage());
	}
	
	//Set a mimeMessage's information up until its fromAddress and assert that an exception was thrown
	@Test(expected = EmailException.class)
	public void buildMimeMessageWithNoSenderTest() throws EmailException
	{
		//Set message's information
		email.setHostName("localhost");
		email.setSubject("Message subject");
		//Attempt to build incomplete message
		email.buildMimeMessage();
	}
	
	//Set a mimeMessage's information up until its toAddress and assert that an exception was thrown
	@Test(expected = EmailException.class)
	public void buildMimeMessageWithNoRecipientTest() throws EmailException
	{
		//Set message's information
		email.setHostName("localhost");
		email.setSubject("Message subject");
		email.setFrom("abc@gmail.com");
		//Attempt to build incomplete message
		email.buildMimeMessage();
	}
	
	//Set a mimeMessage's information, then build it twice and assert that an exception was thrown
	@Test(expected = IllegalStateException.class)
	public void buildDuplicateMimeMessageTest() throws EmailException
	{
		//Set mimeMessage's necessary information
		email.setHostName("localhost");
		email.setSubject("Message subject");
		email.setFrom("abc@gmail.com");
		email.addTo("ghi@gmail.com");
		email.setSentDate(null);
		//Attempt to build the message twice
		email.buildMimeMessage();
		email.buildMimeMessage();
	}
	
	/*
	 * getHostName() Tests
	 */
	
	//Set email's session then assert that the session's hostname matches what was set
	@Test
	public void getHostNameWithSessionTest()
	{
		//Create property and set hostname
		Properties prop = new Properties();
		prop.setProperty(email.MAIL_HOST, "localhost");
		//Create email session and set to instance of property
		Session scn = Session.getInstance(prop);
		email.setMailSession(scn);
		//Check that hostname of email matches what was set
		String hostName = email.getHostName();
		assertEquals("localhost", hostName);
	}
	
	//Set email's hostname then assert that hostname of email matches what was set
	@Test
	public void getHostNameWithoutSessionTest()
	{
		//Set hostname to localhost
		email.setHostName("localhost");
		//Check that hostname of email matches what was set
		String hostname = email.getHostName();
		assertEquals("localhost", hostname);
	}
	
	//Test email with no hostname and assert that default hostname of email is null
	@Test
	public void getNullHostName() throws EmailException
	{
		//Check that default hostname of email is null
		String hostname = email.getHostName();
		assertEquals(null, hostname);
	}
	
	/*
	 * getMailSession() Tests
	 */
	
	//Set an email's information then assert that its session is not null
	@Test
	public void getMailSessionTest() throws EmailException
	{	
		//Set hostname to localhost
		email.setHostName("localhost");
		//Enable email session's SSLOnConnect and SSLCheckServerIdentity booleans
		email.setSSLOnConnect(true);
		email.setSSLCheckServerIdentity(true);
		//Check that email session is not null
		assertNotNull(email.getMailSession());
	}
	
	/*
	 * getSentDate() Tests
	 */
	
	//Set email's sentDate and assert that sentDate of email matches what was set 
	@Test
	public void getSentDateTest() throws EmailException
	{
		//Create new sentDate and set email's sentDate to it
		Date testSentDate = new Date(2023, 3, 11, 13, 45, 0);
		email.setSentDate(testSentDate);
		//Check that sentDate of email matches what was set
		assertEquals(testSentDate, email.getSentDate());
	}
	
	//Set email's sentDate with a null value and assert that it defaults to current date
	@Test
	public void getNullSentDateTest() throws EmailException
	{
		//Set email's sentDate to null
		email.setSentDate(null);
		//Create new Date with current date
		Date testSentDate = new Date();
		//Assert that email's sentDate matches current date
		assertEquals(testSentDate, email.getSentDate());
	}
	
	/*
	 *  getSocketConnectionTimeout() Tests
	 */
	
	//Assert that email's socketConnectionTimeout value is default of 60 seconds (60000 ms)
	@Test
	public void getSocketConnectionTimeoutTest() throws EmailException
	{
		//Create int with email's default socketConnectionTimeout
		int testConnectionTimeout = email.getSocketConnectionTimeout();
		//Check that the email's socketConnectionTimeout matches default
		assertEquals(testConnectionTimeout, 60000);
	}
	
	/*
	 * setFrom(String email) Tests
	 */
	
	//Set the email's from address then assert that from address of email matches what was set
	@Test
	public void setFromTest() throws EmailException
	{
		//Set from address of email
		String testEmailAddress = "abc@gmail.com";
		email.setFrom(testEmailAddress);
		//Check that from address of email matches what was added
		assertEquals("abc@gmail.com", email.fromAddress.toString());
	}

}
