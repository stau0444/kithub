package com.toyproject.kithub.repository;

import com.toyproject.kithub.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //스프링이 알아서 엔티티 매니저를 주입해준다
    //스프링 부트에서는 @autowired로 엔티티 매니저를 주입해주기 떄문에
    //생성자 인젝션이 가능하다 .
    //@PersistenceContext
    //private EntityManager em;

    private final EntityManager em;


    //엔티티 매니저 팩토리 주입
    @PersistenceUnit
    private EntityManagerFactory emf;


    public void save(Member member){
        em.persist(member);
    }

    public  Member findOne(Long id){
        return em.find(Member.class , id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
