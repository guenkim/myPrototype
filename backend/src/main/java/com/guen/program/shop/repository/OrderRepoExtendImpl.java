package com.guen.program.shop.repository;

import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.entity.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepoExtendImpl extends QuerydslRepositorySupport implements OrderRepoExtend {

    private EntityManager em;
    private JPAQueryFactory jpaQueryFactory;
    public OrderRepoExtendImpl() {
        super(Order.class);
    }

    @Autowired
    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
        super.setEntityManager(entityManager);
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * JPA Criteria
     */
    public List<Order> findAllByCriteria(ReqOrderSearchDto orderSearch) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("crew", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 아이디 검색
        if (orderSearch.getCrewId()!=0) {
            Predicate id =
                    cb.equal(m.<Long>get("id"), orderSearch.getCrewId());
            criteria.add(id);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }
}
