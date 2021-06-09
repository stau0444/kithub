package com.toyproject.kithub.repository;

import com.toyproject.kithub.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item){
        //DB에 등록이 되어있지만 영속성컨텍스트에는 안되있을 경우
        if(item.getId() == null){
            em.persist(item);
        } else {
            em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
