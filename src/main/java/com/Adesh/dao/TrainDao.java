package com.Adesh.dao;
import com.Adesh.entity.Train;
import jakarta.persistence.EntityManager;
import java.util.List;
public class TrainDao {

        // 1. Add a new train
        public void addTrain(EntityManager em, Train train) {
            em.persist(train);
        }

        // 2. Find train by ID
        public Train getTrainById(EntityManager em, int trainId) {
            return em.find(Train.class, trainId);
        }

        // 3. Display all trains
        public List<Train> getAllTrains(EntityManager em) {
            return em.createQuery(
                    "SELECT t FROM Train t",
                    Train.class
            ).getResultList();
        }

        // 4. Search trains by source and destination
        public List<Train> searchTrains(
                EntityManager em,
                String source,
                String destination) {

            return em.createQuery(
                            "SELECT t FROM Train t " +
                                    "WHERE LOWER(t.source) = LOWER(:source) " +
                                    "AND LOWER(t.destination) = LOWER(:destination)",
                            Train.class
                    )
                    .setParameter("source", source)
                    .setParameter("destination", destination)
                    .getResultList();
        }

        // 5. Update train details
        public void updateTrain(EntityManager em, Train train) {
            em.merge(train);
        }

        // 6. Delete train by ID
        public void deleteTrain(EntityManager em, int trainId) {
            Train train = em.find(Train.class, trainId);

            if (train != null) {
                em.remove(train);
            }
        }

}
