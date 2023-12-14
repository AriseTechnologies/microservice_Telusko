package com.arise.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.arise.questionservice.dao.QuestionDao;
import com.arise.questionservice.model.Question;
import com.arise.questionservice.model.QuestionWrapper;
import com.arise.questionservice.model.Response;

@Service
public class QuestionService {

	@Autowired
	QuestionDao questionDao;
	
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			List<Question> ques= questionDao.findAll();
			return new ResponseEntity<>(ques, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		questionDao.save(question);
		return new ResponseEntity<>("Success",HttpStatus.CREATED);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categotyName, Integer numOfQues) {
		List<Integer> questions = questionDao.findRandomQuestionsByCategory(categotyName, numOfQues);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
//		List<Question> questions = new ArrayList<>();
		
		for(Integer id : questionIds) {
			Question ques = questionDao.findById(id).get();
			
			QuestionWrapper qw = new QuestionWrapper();
			qw.setId(ques.getId());
			qw.setQuestionTitle(ques.getQuestionTitle());
			qw.setOption1(ques.getOption1());
			qw.setOption2(ques.getOption2());
			qw.setOption3(ques.getOption3());
			qw.setOption4(ques.getOption4());
			
			wrappers.add(qw);
		}
		return new ResponseEntity<>(wrappers,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int right = 0;
		
		for(Response response: responses) {
			Question question = questionDao.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<>(right,HttpStatus.OK);
	}

}
