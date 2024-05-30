package com.example.demo.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.EntQuiz;
@Repository
public class QuizDao {
	public List<EntQuiz> searchDb() {
	    String sql = "SELECT * FROM sample ORDER BY RAND()";
	    List<Map<String, Object>> resultDb1 = db.queryForList(sql);

	    List<EntQuiz> resultDb2 = new ArrayList<>();
	    for (Map<String, Object> result1 : resultDb1) {
	        EntQuiz entformdb = new EntQuiz();
	        entformdb.setId((Integer) result1.get("id"));
	        entformdb.setName((String) result1.get("name"));
	        entformdb.setQuestion((String) result1.get("question"));
	        resultDb2.add(entformdb);
	    }
	    return resultDb2;
	}

	
	private final JdbcTemplate db;
	public QuizDao(JdbcTemplate db) {
		this.db = db;
	}
}	
//	// 重複を避けるための問題リスト
//	private List<Integer> usedQuestions = new ArrayList<>();
//	// 重複のないランダムな問題を取得
//	public EntQuiz getUniqueRandomQuestion() {
//		// データベースからランダムに1つの問題を取得
//		String sql = "SELECT * FROM sample ORDER BY RAND() LIMIT 1";
//		Map<String, Object> result = db.queryForMap(sql);
//		// 取得したデータをEntQuizオブジェクトにマッピング
//		EntQuiz question = new EntQuiz();
//		question.setId((Integer) result.get("id"));
//		question.setName((String) result.get("name"));
//		question.setQuestion((String) result.get("question"));
//		// 使用した問題のIDをリストに追加して重複を避ける
//		usedQuestions.add(question.getId());
//		return question;
//	}
//}