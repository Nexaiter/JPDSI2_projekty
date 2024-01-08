package jsf.course.dao;

import jsf.course.entities.Gameorder;
import jsf.course.entities.Product;
import jsf.course.entities.User;
import jsf.course.entities.GameOrderResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;


import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class GameOrderDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";
	
	 @PersistenceContext(unitName = UNIT_NAME)
	    protected EntityManager em;

	    public void create(Gameorder gameOrder) {
	        em.persist(gameOrder);
	    }

	    public Gameorder merge(Gameorder gameOrder) {
	        return em.merge(gameOrder);
	    }

	    public void remove(Gameorder gameOrder) {
	        em.remove(em.merge(gameOrder));
	    }

	    public Gameorder find(Object id) {
	        return em.find(Gameorder.class, id);
	    }
	
	    public List<Gameorder> getFullList() {
	        List<Gameorder> list = null;
	        
	        Query query = em.createQuery("select g from Gameorder g");
	        
	        try {
	     	   list = query.getResultList();
	        } catch(Exception e) {
	     	   e.printStackTrace();
	        }
	        return list;
	     }
	    
		    public List<Gameorder> getList(User user) {
		        List<Gameorder> list = null;
	
		        // 1. Build query string with parameters
		        String select = "SELECT g ";
		        String from = "FROM Gameorder g ";
		        String where = "";
	
		        if (user != null) {
					if (where.isEmpty()) {
						where = "where ";
					} else {
						where += "and ";
					}
					where += "g.user like :user ";
				}
		        // 2. Create query object
		        Query query = em.createQuery(select + from + where);
	
		        // 3. Set configured parameters
		        if (user != null) {
		            query.setParameter("user", user);
		        }
	
		        try {
		            list = query.getResultList();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
	
		        return list;
		    }
		    
		    public void deleteUserOrders(User user) {
		        // 1. Build query string with parameters
		        String delete = "DELETE g ";
		        String from = "FROM Gameorder g ";
		        String where = "";

		        if (user != null) {
					if (where.isEmpty()) {
						where = "where ";
					} else {
						where += "and ";
					}
					where += "g.user like :user ";
				}
		        
		        Query query = em.createQuery(delete + from + where);
		       
		        if (user != null) {
		            query.setParameter("user", user);
		        }
		    }
		    
		    public void deleteUserOrdersByUserId(Integer userId) {
		        Query query = em.createQuery("DELETE FROM Gameorder g WHERE g.user.id = :userId");
		        query.setParameter("userId", userId);

		        query.executeUpdate();
		    }
}
