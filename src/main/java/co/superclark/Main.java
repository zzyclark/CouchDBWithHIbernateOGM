package co.superclark;

import co.superclark.entity.Breed;
import co.superclark.entity.Dog;
import org.hibernate.Transaction;
import org.hibernate.ogm.OgmSession;
import org.hibernate.ogm.util.impl.Log;
import org.hibernate.ogm.util.impl.LoggerFactory;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

/**
 * @Author clark
 * @Date 30-Nov-2015
 */
public class Main {
    private static final Log logger = LoggerFactory.make();

    public static void main(String[] args) {
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

        //build the EntityManagerFactory as you would build in in Hibernate Core
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "couchdb-hibernateogm" );

        try {
            //start transaction
            tm.begin();
            logger.infof( "About to store dog and breed" );

            /**
             * Initialize Hibernate OgmSession api from EntityManager, if
             * you want use plain JPA, just directly use the EntityManger to persist entity
             */
            EntityManager em = emf.createEntityManager();
            OgmSession ogmSession = em.unwrap(OgmSession.class);

            Dog dina = new Dog();
            dina.setName( "Nancy" );
            Breed breed = new Breed();
            breed.setName("Ding");
            dina.setBreed(breed);

            Transaction tx = ogmSession.beginTransaction();
            ogmSession.save(dina);
            Long dinaId = dina.getId();
            tx.commit();
            ogmSession.close();

            /**
             * Retrieve your entities the way you are used to in plain JPA,
             * you can also use OgmSession initialized before to the retrieve
             */
            logger.infof( "About to retrieve dog and breed" );
            em = emf.createEntityManager();
            dina = em.find( Dog.class, dinaId );
            System.out.println("Found dog: " + dina.getName() + " of breed: " + dina.getBreed().getName());
            em.flush();
            em.close();
            tm.commit();

            emf.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
