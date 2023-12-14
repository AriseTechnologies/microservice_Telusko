package com.arise.quizservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arise.quizservice.model.QuestionWrapper;
import com.arise.quizservice.model.QuizDto;
import com.arise.quizservice.model.Response;
import com.arise.quizservice.service.QuizService;

@RestController
@RequestMapping("quiz")
public class QuizController {

	@Autowired
	QuizService quizService;
	

	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizdto) {
		return quizService.createQuiz(quizdto.getCategoryName(), quizdto.getNumQuestions(), quizdto.getTitle());
	}
	
	@GetMapping("allQuestions/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
		return quizService.getQuizQuestions(id);
	}
	
	
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
		return quizService.calculateResult(id, responses);
	}
	
//	@PostMapping("getScore")
//	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
//		return quizService.getScore(responses);
//	}
}
