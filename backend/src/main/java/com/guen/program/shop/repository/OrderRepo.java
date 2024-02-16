package com.guen.program.shop.repository;

import com.guen.program.shop.model.dto.request.ReqOrderSearchDto;
import com.guen.program.shop.model.entity.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order,Long> ,OrderRepoExtend , JpaSpecificationExecutor<Order> , QuerydslPredicateExecutor<Order> {

    @Query("select o from Order o inner join o.crew c inner join o.delivery where c.id = :crewId")
    List<Order> findOrderByCrewId(@Param("crewId")long crewId);






}
