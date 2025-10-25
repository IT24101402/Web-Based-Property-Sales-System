package backend.Property_Sales_System.repositories;

import backend.Property_Sales_System.model.FeedbackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {

    // Find all feedback submitted by a specific user
    List<FeedbackModel> findByUser_Id(Long userId);
    
    // Find feedback by role
    List<FeedbackModel> findByRole(String role);
    
    // Find feedback by type
    List<FeedbackModel> findByType(String type);
    
    // Find feedback by rating
    List<FeedbackModel> findByRating(Integer rating);
    
    // Find feedback by date range
    List<FeedbackModel> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Find feedback by rating greater than or equal to
    List<FeedbackModel> findByRatingGreaterThanEqual(Integer rating);
    
    // Custom query to find feedback by subject containing keyword
    @Query("SELECT f FROM FeedbackModel f WHERE f.subject LIKE %:keyword%")
    List<FeedbackModel> findBySubjectContaining(@Param("keyword") String keyword);
    
    // Custom query to find feedback by description containing keyword
    @Query("SELECT f FROM FeedbackModel f WHERE f.description LIKE %:keyword%")
    List<FeedbackModel> findByDescriptionContaining(@Param("keyword") String keyword);
    
    // Find feedback ordered by date descending (newest first)
    List<FeedbackModel> findAllByOrderByDateDesc();
    
    // Find feedback ordered by rating descending (highest first)
    List<FeedbackModel> findAllByOrderByRatingDesc();
}