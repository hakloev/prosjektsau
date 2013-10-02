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
	
	
	public String getFirstName() {
		return firstName;
	}
	
	//Trenger exception handler
	private void setFirstName(String firstName) {
		int firstNameLength = firstName.length();
		if(firstNameLength < 3 && firstNameLength > 12)
			{
			this.firstName = firstName;
			}
	}
	
	public String getLastName() {
		return lastName;
	}
	
	//Trenger exception handler
	private void setLastName(String lastName) {
		int lastNameLength = lastName.length();
		if(lastNameLength < 3 && lastNameLength > 12)
			{
			this.lastName = lastName;
			}
	}

	
	public String getEmail() {
		return email;
	}
	
	//Trenger exception handler
	private void setEmail(String email) {
		if(email.contains("@")){
			this.email = email;
		}
	}
	
	public String getUserName() {
		return userName;
	}

	
	public int getOwnerID() {
		return ownerID;
	}
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	private void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.matches("[0-9]+") && phoneNumber.length() == 8) {
			this.phoneNumber = phoneNumber;
		}
	}
	
	public String getPassWord() {
		return passWord;
	}
	
	private void setPassWord(String passWord) {
		int passWordLength = passWord.length();
		if (passWordLength < 10 && passWordLength > 6)
		{
			this.passWord = passWord;
		}
	}
}
