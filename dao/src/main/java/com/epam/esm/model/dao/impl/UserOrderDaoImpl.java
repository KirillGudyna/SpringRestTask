package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserOrderDao;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class UserOrderDaoImpl implements UserOrderDao {
    private static final String JPQL_FIND_USER_ORDERS = "select o from Order o where o.user.id = ?1";
    private static final String SELECT_MOST_POPULAR_TAG_OF_USER =
            "SELECT tag_id FROM (SELECT tag_id, COUNT(tag_id) AS most_popular \n" +
                    "FROM `user_order` JOIN gift_to_tag ON `user_order`.certificate_id=gift_to_tag.gift_certificate_id \n" +
                    "WHERE user_id = ?\n" +
                    "GROUP BY tag_id\n" +
                    "order BY most_popular DESC\n" +
                    "LIMIT 1) AS temp";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Order add(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        TypedQuery<Order> query = entityManager.createQuery(JPQL_FIND_USER_ORDERS, Order.class);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public Long findMostPopularTagIdOfUser(long userId) {
        BigInteger bigInteger = (BigInteger) entityManager
                .createNativeQuery(SELECT_MOST_POPULAR_TAG_OF_USER)
                .setParameter(1, userId)
                .getSingleResult();
        return bigInteger.longValue();
    }
}
