package jsf.course.dao;

import jsf.course.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UserDAO {
    private final static String UNIT_NAME = "jsfcourse-simplePU";

    @PersistenceContext(unitName = UNIT_NAME)
    protected EntityManager em;

    public void create(User user) {
        em.persist(user);
    }

    public User merge(User user) {
        return em.merge(user);
    }

    public void remove(User user) {
        em.remove(em.merge(user));
    }

    public User find(Object id) {
        return em.find(User.class, id);
    }

    public List<User> getFullList() {
       List<User> list = null;
       
       Query query = em.createQuery("select u from User u");
       
       try {
    	   list = query.getResultList();
       } catch(Exception e) {
    	   e.printStackTrace();
       }
       return list;
    }

    public String getPermission(String login) {
        String sql = "SELECT u.permission FROM User u WHERE u.login = :login";
        Query query = em.createQuery(sql);
        query.setParameter("login", login);
        
        try {
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // handle situation when no result is found for given login
        }
    }
    
    public Integer getId(String login) {
        String sql = "SELECT u.id FROM User u WHERE u.login = :login";
        Query query = em.createQuery(sql);
        query.setParameter("login", login);
        
        try {
            return (Integer) query.getSingleResult();
        } catch (NoResultException e) {
            return null; // handle situation when no result is found for given login
        }
    }
    
    
    public List<User> getList(Map<String, Object> searchParams) {
		List<User> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from User u ";
		String where = "";
		String orderby = "order by u.login";

		// search for surname
		String login = (String) searchParams.get("login");
		if (login != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.login like :login ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (login != null) {
			query.setParameter("login", login+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
    
    public User findUserByLogin(String login) {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.login = :login");
            query.setParameter("login", login);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
