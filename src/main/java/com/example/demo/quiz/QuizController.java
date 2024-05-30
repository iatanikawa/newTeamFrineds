package com.example.demo.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.QuizDao;
import com.example.demo.entity.EntQuiz;

@Controller
public class QuizController {
	// QuizDaoの用意
	private final QuizDao quizdao;

	@Autowired
	public QuizController(QuizDao quizdao) {
		this.quizdao = quizdao;
	}

	@RequestMapping("/index")
	public String start(Model model) {
		model.addAttribute("title", "この人誰だろな？");
		return "index";
	}

	@RequestMapping("/quiz1")
	public String quiz1(Model model) {
		//データベースからランダムに1問の問題を取得
		List<EntQuiz> questions = getRandomQuestions(quizdao.searchDb(), 1);
		//取得した問題リストを Model オブジェクトに追加し、questions という名前でビューに渡す
		model.addAttribute("questions", questions);

		//各問題とその選択肢のマップを作成し、choiceMap という名前でビューに渡す
		Map<Integer, List<EntQuiz>> choiceMap = new HashMap<>();
		for (EntQuiz question : questions) {
			List<EntQuiz> choices = generateChoices(question);
			choiceMap.put(question.getId(),choices);
		}
		
		model.addAttribute("choiceMap", choiceMap);
		return "quiz/quiz1";
	}

	private List<EntQuiz> generateChoices(EntQuiz question) {
		List<EntQuiz> choices = new ArrayList<>();
		choices.add(question); // 正解を最初に追加
		//選択肢候補29個のリストを用意する。
		List<EntQuiz> wrongChoices = quizdao.searchDb();
		wrongChoices.remove(question);
		
		for (EntQuiz choice : wrongChoices) {
			
			if (choice != question && choices.size() < 4) {
				choices.add(choice);
			}if(choice == question){
				wrongChoices.remove(question);//正解を除外をfor文に入れる
			}
		}
		// 選択肢をシャッフルしてランダム化
		Collections.shuffle(choices); 
		//択肢は、正解と不正解の問題を含むシャッフルされたリストとして返される
		return choices; 
	}
	
	// getRandomQuestionsメソッド
	private List<EntQuiz> getRandomQuestions(List<EntQuiz> questions, int numQuestions){
		//リストをシャッフルしてランダム化
		Collections.shuffle(questions); 
		 // インデックスの上限をリストのサイズに合わせる
		int endIndex = Math.min(numQuestions, questions.size());
		// ランダムに選択された問題を返す
		return questions.subList(0, endIndex); 
	}

	@RequestMapping("/right")
	public String right(Model model) {
		return "quiz/right";
	}

	@RequestMapping("/wrong")
	public String wrong(Model model) {
		return "quiz/wrong";
	}

	@RequestMapping("/finish")
	public String finish(Model model) {
		return "quiz/finish";
	}
}