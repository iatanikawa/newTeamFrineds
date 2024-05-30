package com.example.demo.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.QuizDao;
import com.example.demo.entity.EntQuiz;

@Controller
public class QuizController {
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
        List<EntQuiz> questions = getRandomQuestions(quizdao.searchDb(), 1);
        model.addAttribute("questions", questions);

        Map<Integer, List<EntQuiz>> choiceMap = new HashMap<>();
        for (EntQuiz question : questions) {
            List<EntQuiz> choices = generateChoices(question);
            choiceMap.put(question.getId(), choices);
        }

        // クイズフォームオブジェクトを作成し、最初の質問の正しい回答を設定
        QuizForm quizForm = new QuizForm();
        if (!questions.isEmpty()) {
            quizForm.setCorrectAnswer(questions.get(0).getName());
        }

        model.addAttribute("choiceMap", choiceMap);
        model.addAttribute("quizform", quizForm); // quizformオブジェクトをモデルに追加
        return "quiz/quiz1";
    }

    private List<EntQuiz> generateChoices(EntQuiz question) {
        List<EntQuiz> choices = new ArrayList<>();
        choices.add(question);

        List<EntQuiz> wrongChoices = quizdao.searchDb();
        wrongChoices.removeIf(wrongChoice -> wrongChoice.getId() == question.getId());

        Collections.shuffle(wrongChoices);

        for (int i = 0; i < 3 && i < wrongChoices.size(); i++) {
            choices.add(wrongChoices.get(i));
        }

        Collections.shuffle(choices);
        return choices;
    }

    private List<EntQuiz> getRandomQuestions(List<EntQuiz> questions, int numQuestions) {
        Collections.shuffle(questions);
        int endIndex = Math.min(numQuestions, questions.size());
        return questions.subList(0, endIndex);
    }

    @RequestMapping("/checkAnswer")
    public String checkAnswer(@ModelAttribute QuizForm quizForm, Model model) {
        String selectedAnswer = quizForm.getSelectedAnswer();
        String correctAnswer = quizForm.getCorrectAnswer();
        Logger logger = LoggerFactory.getLogger(QuizController.class);

        logger.info("Selected Answer: " + selectedAnswer);
        logger.info("Correct Answer: " + correctAnswer);

        if (selectedAnswer == null) {
            // エラーメッセージを設定し、再度クイズ画面に戻す
            model.addAttribute("errorMessage", "回答を選択してください。");
            return "quiz/quiz1";
        }

        if (selectedAnswer.equals(correctAnswer)) {
            return "quiz/right";
        } else {
            return "quiz/wrong";
        }
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

