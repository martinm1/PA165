/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author martin
 */
@Repository
public class ProductDaoImpl implements ProductDao{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Product p) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        em.persist(p);
    }

    @Override
    public List<Product> findAll() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return em.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return em.find(Product.class, id);
    }

    @Override
    public void remove(Product p) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(em.contains(p)) em.remove(p);
        else {
            Product p1 = em.getReference(p.getClass(), p.getId());
            em.remove(p1);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		try {
                    return em.createQuery("select p from Product p where name = :name", Product.class).setParameter("name", name).getResultList();
		} catch (NoResultException nrf) {
                    return null;
		}
    }
    
}