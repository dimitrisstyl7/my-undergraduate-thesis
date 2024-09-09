package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietPlanRepository extends JpaRepository<DietPlan, Integer> {

    Optional<DietPlan> findByIdAndUserInfo_Id(int dietPlanId, int userInfoId);

    List<DietPlan> findAllByUserInfo_IdOrderByCreatedOnDesc(int userInfoId);

    boolean existsByUserInfo_IdAndCreatedOn(int userInfoId, LocalDate createdOn);

}
