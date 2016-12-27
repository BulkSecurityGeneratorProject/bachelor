/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package box.repository;

import box.domain.GreenHouseManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emil
 */
@Repository
public class CustomGreenHouseManagerRepository {

    @PersistenceContext
    private EntityManager em;
    
      public GreenHouseManager getGreenHouseManager(Long id){
          TypedQuery querry = em.createQuery("Select a from green_house_manager where a.id = ?1", GreenHouseManager.class);
          querry.setParameter(1, id);
          return (GreenHouseManager)querry.getResultList().get(0);
      }

}
