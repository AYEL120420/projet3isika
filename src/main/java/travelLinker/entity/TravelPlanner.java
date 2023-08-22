package travelLinker.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class TravelPlanner{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 

	private String lastName; 
	private String firstName; 
	private String address; 
	@Column(unique = true)
	private String email;


	private String phoneNumber;
	private String siret;
	private String companyName;
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	@OneToOne
	@JoinColumn(name="subscription_id")
	private Subscription subcription;
	
	public Long getId() {
		return id;
	}


	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}


	public Subscription getSubcription() {
		return subcription;
	}


	public void setSubcription(Subscription subcription) {
		this.subcription = subcription;
	}	
	
	

}
