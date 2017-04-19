package br.com.goline2.model.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.goline2.model.dao.GenericDAO;
import br.com.goline2.model.entity.impl.User;
import br.com.goline2.util.StringUtil;

public class UserDAO extends GenericDAO<Long, User> {

	private static UserDAO instance;

	private UserDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public static synchronized UserDAO getInstance(EntityManager entityManager) {
		if (instance == null) {
			instance = new UserDAO(entityManager);
		}else{
			instance.setEntityManager(entityManager);
		}
		return instance;
	}

	/**
	 * Retorna um {@link User} por email e senha
	 * 
	 * @param user
	 * @return {@link List}
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public User getByEmailAndPassword(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		EntityManager entityManagger = super.getEntityManager();

		String myQuery = "SELECT obj FROM " + user.getClass().getSimpleName().toString()
				+ " obj WHERE obj.email = :email and obj.password = :password AND obj.status = :status";

		Query query = entityManagger.createQuery(myQuery);
		//query.setParameter("email", user.getEmail());
		query.setParameter("password", StringUtil.SHA1(user.getPassword()));
		query.setParameter("status", true);
		
		List<User> resultList = (List<User>) query.getResultList();

		if (!resultList.isEmpty()) {
			return resultList.get(0);
		}else{
			return null;
		}

	}
	
	public List<User> getByProfile(User.UserProfiles profile) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		EntityManager entityManagger = super.getEntityManager();

		// Administrador
		TypedQuery<User> query = entityManagger.createQuery("SELECT obj FROM User obj WHERE obj.profile = :profile AND obj.status = :status", User.class);
		query.setParameter("profile", profile);
		query.setParameter("status", true);

		List<User> resultList = query.getResultList();

		if (!resultList.isEmpty()) {
			return resultList;
		}else{
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public List<User> getByEmail(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		EntityManager entityManagger = super.getEntityManager();

		//String emailReceived = user.getEmail();
		//System.out.println("Email received -> " + emailReceived);
		// Lista que será retornada no metódo
		List<User> returnListForMethod = new ArrayList<User>();

		// Administrador
		Query query = entityManagger.createQuery("SELECT obj FROM Administrador obj WHERE obj.email = :email AND obj.status = :status");
		//query.setParameter("email", emailReceived);
		query.setParameter("status", true);
		
		List<User> resultListAdministrator = (List<User>) query.getResultList();

		if (!resultListAdministrator.isEmpty()) {
			returnListForMethod.addAll(resultListAdministrator);
		}

		return returnListForMethod;

	}

}
