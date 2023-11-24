package jsf.course.dao;

import jsf.course.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Map;



import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
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
        try {
            Query query = em.createQuery("SELECT u FROM User u");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // ZwrÃ³cenie pustej listy w przypadku bÅ‚Ä™du
        }
    }

    public List<User> getUserList(Map<String, Object> searchParams) {
        List<User> userList = null;

        String select = "SELECT u ";
        String from = "FROM User u ";
        String where = "";
        String orderby = "ORDER BY u.login ASC, u.permission";

        String permission = (String) searchParams.get("permission");
        if (permission != null) {
            if (where.isEmpty()) {
                where = "WHERE ";
            } else {
                where += "AND ";
            }
            where += "u.permission LIKE :permission ";
        }

        try {
            Query query = em.createQuery(select + from + where + orderby);

            if (permission != null) {
                query.setParameter("permission", permission + "%");
            }

            userList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            userList = Collections.emptyList(); // ZwrÃ³cenie pustej listy w przypadku bÅ‚Ä™du
        }

        return userList;
    }
}
