package com.guen.program.shop.repository;

import com.guen.program.shop.model.entity.Crew;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CrewRepo extends JpaRepository<Crew, Long>, CrewRepoExtend , JpaSpecificationExecutor<Crew> , QuerydslPredicateExecutor<Crew> {

}
