package com.arise.questionservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.arise.questionservice.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{

	List<Question> findByCategory(String category);
	
	@Query(value = "SELECT q.id from question q where q.category=:category LIMIT :numQ", nativeQuery = true)
	List<Integer> findRandomQuestionsByCategory(String category, int numQ);
	
}
