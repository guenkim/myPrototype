package com.guen.program.jpashop.repository;


import com.guen.program.jpashop.model.entity.Crew;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CrewRepository {

    private final EntityManager em;

    public void save(Crew crew) {
        em.persist(crew);
    }

    public Crew findOne(Long id) {
        return em.find(Crew.class, id);
    }

    public List<Crew> findAll() {
        return em.createQuery("select c from Crew c", Crew.class)
                .getResultList();
    }

    public List<Crew> findByName(String name) {
        return em.createQuery("select c from Crew c where c.name = :name", Crew.class)
                .setParameter("name", name)
                .getResultList();
    }
}
