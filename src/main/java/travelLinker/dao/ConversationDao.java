package travelLinker.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import travelLinker.entity.Account;
import travelLinker.entity.Message;
import travelLinker.utils.SessionUtils;
import travelLinker.viewModel.AccountViewModel;

@Stateless
public class ConversationDao {

	@PersistenceContext(unitName = "travelLinker")
	private EntityManager entityManager;
	@Inject
	private LoginDao loginDao;
	
//Methode pour ajouter un message à la base de donnée 
	public void sendMessage(AccountViewModel accVM) {
		Account account = SessionUtils.getAccount();
		String senderEmail = account.getEmail();
		Long idSender = account.getId();
		Account recipient = loginDao.findAccountByEmail(accVM.getRecepientEmail());
		Long idRecipient = recipient.getId();

		if (recipient != null) {

			Message message = new Message();
			message.setRecipientEmail(accVM.getRecepientEmail());
			message.setContent(accVM.getContent());
			message.setMessageResume(accVM.getMessageResume());
			message.setSenderEmail(senderEmail);
			message.setSenderId(idSender);
			message.setRecepientId(idRecipient);
			message.setRead(false);
			entityManager.persist(message);
		}
	}

	public String getEmail() {
		Account account=SessionUtils.getAccount();
		String EmailSender = account.getEmail();
		return EmailSender;
	}

	public List<Message> getReceivedMessages(String recipientEmail) {
		TypedQuery<Message> query = entityManager
				.createQuery("SELECT m FROM Message m WHERE m.recipientEmail = :recipientEmail", Message.class);
		query.setParameter("recipientEmail", recipientEmail);
		return query.getResultList();
	}

	public List<Message> getSentMessages(String senderEmail) {
		TypedQuery<Message> query = entityManager
				.createQuery("SELECT m FROM Message m WHERE m.senderEmail = :senderEmail", Message.class);
		query.setParameter("senderEmail", senderEmail);
		return query.getResultList();
	}

	public List<Message> getAllMessages(String senderEmail, String recipientEmail) {
		TypedQuery<Message> query = entityManager.createQuery(
				"SELECT m FROM Message m WHERE m.senderEmail = :senderEmail OR m.recipientEmail = :recipientEmail",
				Message.class);
		query.setParameter("senderEmail", senderEmail);
		query.setParameter("recipientEmail", recipientEmail);
		return query.getResultList();
	}

	public List<Message> getReadMessages() {
		TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.isRead = true",
				Message.class);
		return query.getResultList();
	}

	public List<Message> getUnreadMessages() {
		TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.isRead = false",
				Message.class);
		return query.getResultList();
	}

	public void markMessageAsRead(Long messageId) {
		Message message = entityManager.find(Message.class, messageId);
		if (message != null) {
			message.setRead(true);
			entityManager.merge(message);
		}
	}


	public void deleteMessage(Long messageId) {

		Message message = entityManager.find(Message.class, messageId);

		if (message != null) {
			entityManager.remove(message);
		}
	}

	public Message getMessageById(Long messageId) {
		Message message = entityManager.find(Message.class, messageId);
		return message;
	}
}