package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;
        
	private Category electro;
        private Category kitchen;
	private Product  flashlight;
	private Product  kitchenRobot;
        private Product  plate;
        
        @BeforeClass
	public void onceExecutedBeforeAll() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            
            electro = new Category();
            kitchen = new Category();
	    flashlight = new Product();
	    kitchenRobot = new Product();
            plate = new Product();
            
            electro.setName("Electro");
            kitchen.setName("Kitchen");
            flashlight.setName("Flashlight");
            kitchenRobot.setName("Kitchen robot");
            plate.setName("Plate");
            
            flashlight.addCategory(electro);
            kitchenRobot.addCategory(electro);
            kitchenRobot.addCategory(kitchen);
            plate.addCategory(kitchen);
            
            em.persist(electro);
            em.persist(kitchen);
            em.persist(flashlight);
            em.persist(kitchenRobot);
            em.persist(plate);
            
            em.getTransaction().commit();
            em.close();
        }

	@Test
	public void electroTest(){
		EntityManager em = emf.createEntityManager();
		Category electro1 = em.find(Category.class, electro.getId());
		assertContainsProductWithName(electro1.getProducts(), "Flashlight");
		em.close();
        }
        
        @Test
	public void kitchenTest(){
		EntityManager em = emf.createEntityManager();
		Category kitchen1 = em.find(Category.class, kitchen.getId());
		assertContainsProductWithName(kitchen1.getProducts(), "Kitchen robot");
		em.close();
        }
        
        @Test
	public void flashlightTest(){
		EntityManager em = emf.createEntityManager();
		Product flashlight1 = em.find(Product.class, flashlight.getId());
                
		assertContainsCategoryWithName(flashlight1.getCategories(), "Electro");
		em.close();
        }
        
        @Test
	public void kitchenRobotTest(){
		EntityManager em = emf.createEntityManager();
		Product kitchenRobot1 = em.find(Product.class, kitchenRobot.getId());
                
		assertContainsCategoryWithName(kitchenRobot.getCategories(), "Electro");
                assertContainsCategoryWithName(kitchenRobot.getCategories(), "Kitchen");
		em.close();
        }
        
        @Test
	public void plateTest(){
		EntityManager em = emf.createEntityManager();
		Product plate1 = em.find(Product.class, plate.getId());
                
                assertContainsCategoryWithName(plate.getCategories(), "Kitchen");
		em.close();
        }
        
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}

	
}