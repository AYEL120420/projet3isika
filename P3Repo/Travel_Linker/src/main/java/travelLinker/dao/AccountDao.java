package travelLinker.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import travelLinker.entity.Account;
import travelLinker.entity.Customer;
import travelLinker.entity.Partner;

import travelLinker.entity.RoleUser;
import travelLinker.entity.TravelPlanner;
import travelLinker.utils.PasswordUtils;

import travelLinker.viewModel.AccountViewModel;

@Stateless
public class AccountDao {

	@PersistenceContext(unitName = "travelLinker")
	private EntityManager entityManager;
	private List<Partner> partners = new ArrayList<>();

	public AccountDao() {
	}

	public Long insert(AccountViewModel accountVM) {
		try {
			Account accountbean = new Account();
			accountbean.setEmail(accountVM.getEmail());
			// Hash the password before storing it in the database
			String hashedPassword = PasswordUtils.hashPassword(accountVM.getPassword());
			accountbean.setPassword(hashedPassword);
			accountbean.setRole(accountVM.getRole());
			accountbean.setLastName(accountVM.getLastName());
			accountbean.setFirstName(accountVM.getFirstName());

			if (accountVM.getRole() == RoleUser.Customer) {
				Customer customer = new Customer();
				customer.setEmail(accountVM.getEmail());
				customer.setLastName(accountVM.getLastName());
				customer.setFirstName(accountVM.getFirstName());
				customer.setAccount(accountbean); // Set the account relationship
				entityManager.persist(customer);
			} else if (accountVM.getRole() == RoleUser.TravelPlanner) {
				TravelPlanner travelPlanner = new TravelPlanner();
				travelPlanner.setEmail(accountVM.getEmail());
				travelPlanner.setLastName(accountVM.getLastName());
				travelPlanner.setFirstName(accountVM.getFirstName());
				travelPlanner.setAccount(accountbean); // Set the account relationship
				entityManager.persist(travelPlanner);
			} else if (accountVM.getRole() == RoleUser.Partner) {
				Partner partner = new Partner();
				partner.setFirstName(accountVM.getFirstName());
				partner.setLastName(accountVM.getLastName());
				partner.setEmail(accountVM.getEmail());
				partner.setPhoneNumber(accountVM.getPhoneNumber());
				partner.setAddress(accountVM.getAddress());
				partner.setSiret(accountVM.getSiret());
				partner.setAccount(accountbean); // Set the account relationship
				entityManager.persist(partner);
			}

			entityManager.persist(accountbean);
			entityManager.flush(); // Flush to synchronize changes

			return accountbean.getId();
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
			return null;
		}
	}

	public List<Partner> displayPartners() {
		try {
			TypedQuery<Partner> query = entityManager.createQuery("SELECT p FROM Partner p", Partner.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null; // Ou gérer l'exception de manière appropriée
		}
	}

	// -------------------------------------------------------
	public void delete(Long accountId) {
        // On cherche le compte dans la BDD avec l'Id
        Account accountBean = entityManager.find(Account.class, accountId);
        //Si le compte est trouvé, on le supprime de la BDD
        if (accountBean != null) {
            entityManager.remove(accountBean);
        } else {
            System.out.println("Compte introuvable pour l'ID : " + accountId);
            }
	}

//-------------------------------------------------

	public int getTotalPartnersCount() {
		TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(p) FROM Partner p", Long.class);
		Long count = query.getSingleResult();
		return count.intValue();
	}

//----------------------------------------------------
	public void update(Account updatedAccount) {
		// Rechercher le compte dans la base de données avec l'ID
		Account existingAccount = entityManager.find(Account.class, updatedAccount.getId());

		// Vérifier si le compte existe dans la base de données
		if (existingAccount != null) {
			// Mettre à jour les champs du compte avec les nouvelles valeurs
			existingAccount.setFirstName(updatedAccount.getFirstName());
			existingAccount.setEmail(updatedAccount.getEmail());
			existingAccount.setLastName(updatedAccount.getLastName());
			existingAccount.setPassword(updatedAccount.getPassword());
			existingAccount.setRole(updatedAccount.getRole());

			// Enregistrer les modifications dans la base de données
			entityManager.merge(existingAccount);
		} else {
			System.out.println("Compte introuvable pour l'ID : " + updatedAccount.getId());
		}

	}

//-------------------------------------------------
	public Account getAccountById(Long accountId) {
		try {
			return entityManager.find(Account.class, accountId);
		} catch (Exception e) {
			e.printStackTrace(); // Gérez les exceptions de manière appropriée
			return null;
		}
	}
//---------------------------------------------------

	public RoleUser getUserRoleById(Long userId) {
		Account account = getAccountById(userId);
		if (account != null) {
			return account.getRole();
		}
		return null;

	}
}