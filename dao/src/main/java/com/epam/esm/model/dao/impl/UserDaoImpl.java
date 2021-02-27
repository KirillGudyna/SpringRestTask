package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private static final String JPQL_FIND_ALL = "select u from User u";
    private static final String SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM =
            "SELECT user_id FROM \n" +
                    "(SELECT user_id, SUM(cost) AS total_cost FROM `user_order`\n" +
                    "GROUP BY user_id order BY total_cost DESC LIMIT 1) AS tmp";
    private static final String JPQL_FIND_USER_BY_NAME = "SELECT e FROM User e WHERE e.email = :email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findUserIdWithHighestOrderSum() {
        BigInteger bigInteger = (BigInteger) entityManager.
                createNativeQuery(SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM)
                .getSingleResult();
        return bigInteger.longValue();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        TypedQuery<User> query = entityManager.createQuery(JPQL_FIND_ALL, User.class);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query.getResultList();
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        TypedQuery<User> query = entityManager.createQuery(JPQL_FIND_USER_BY_NAME,User.class);
        query.setParameter("email",email);
        return Optional.ofNullable(query.getSingleResult());
    }

}
