package com.example.demo.quiz;
public class QuizForm {
	//クラスのコンストラクタ
	public QuizForm() {}
	
	String selectedAnswer;
	String correctAnswer;
	//selectedAnswerのゲッター・セッター
	public String getSelectedAnswer() {
		return selectedAnswer;
	}
	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}
	
	
	//correctAnswerのゲッター・セッター
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}



