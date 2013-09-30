package characters;

public class Farmer 
{

	private final int ownerID;
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private int phoneNumber;
	private String email;
	
	public Farmer(int ownerID, String userName, String passWord,
			String firstName, String lastName, int phoneNumber, String email) 
	{
		this.ownerID = ownerID;
		this.email = email;
		this.userName = email;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		int firstNameLength = firstName.length();
		if(firstNameLength < 3 && firstNameLength > 12)
			{
			this.firstName = firstName;
			}
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		int lastNameLength = lastName.length();
		if(lastNameLength < 3 && lastNameLength > 12)
			{
			this.lastName = lastName;
			}
	}

	
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
//		if(email.contains(krøllalfa, men fikk error på å teste etter @)) 
//		{
//			this.email = email;
//		}
		this.email = email;
	}
	
	public String getUserName() {
		return userName;
	}

	
	public int getOwnerID() {
		return ownerID;
	}
	
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		if(phoneNumber >= 10000000 && phoneNumber <= 99999999)
		{
			this.phoneNumber = phoneNumber;
		}
	}
	
	
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) 
	{
		int passWordLength = passWord.length();
		if (passWordLength < 10 && passWordLength > 6)
		{
			this.passWord = passWord;
		}
	}
	
	
	
	
	
}
