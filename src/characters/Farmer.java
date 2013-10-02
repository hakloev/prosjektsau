package characters;

public class Farmer 
{

	private final int ownerID;
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	
	/**
	 * 
	 * @param ownerID the farmer's owner ID
	 * @param userName the farmer's user name
	 * @param passWord the farmer's password
	 * @param firstName the farmers first name
	 * @param lastName the farmer's last name
	 * @param phoneNumber the farmer's phone number
	 * @param email the farmers Email address
	 */
	public Farmer(int ownerID, String userName, String passWord,
			String firstName, String lastName, String phoneNumber, String email) {
		this.ownerID = ownerID;
		setEmail(email);
		this.userName = email;
		setPassWord(passWord);
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		
	}
	
	/**
	 * 
	 * @return returns the farmer's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * sets the farmer's first name
	 * 
	 * @param firstName
	 */
	//Trenger exception handler
	private void setFirstName(String firstName) {
		int firstNameLength = firstName.length();
		if(firstNameLength < 3 && firstNameLength > 12)
			{
			this.firstName = firstName;
			}
	}
	/**
	 * 
	 * @return returns the farmer's last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * sets the farmer's last name
	 * 
	 * @param lastName
	 */
	//Trenger exception handler
	private void setLastName(String lastName) {
		int lastNameLength = lastName.length();
		if(lastNameLength < 3 && lastNameLength > 12)
			{
			this.lastName = lastName;
			}
	}

	/**
	 * 
	 * @return returns the farmer's Email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * sets the farmer's Email address
	 * 
	 * @param email
	 */
	//Trenger exception handler
	private void setEmail(String email) {
		if(email.contains("@")){
			this.email = email;
		}
	}
	
	/**
	 * 
	 * @return returns the farmer's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @return returns the farmer's owner ID
	 */
	public int getOwnerID() {
		return ownerID;
	}
	
	/**
	 * 
	 * @return returns the farmer's phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	private void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.matches("[0-9]+") && phoneNumber.length() == 8) {
			this.phoneNumber = phoneNumber;
		}
	}
	
	/**
	 * 
	 * @return returns the farmer's password
	 */
	public String getPassWord() {
		return passWord;
	}
	
	/**
	 * sets the farmer's password
	 * 
	 * @param passWord
	 */
	private void setPassWord(String passWord) {
		int passWordLength = passWord.length();
		if (passWordLength < 10 && passWordLength > 6)
		{
			this.passWord = passWord;
		}
	}
}
