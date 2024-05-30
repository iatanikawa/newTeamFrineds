package com.example.demo.entity;

import java.util.Objects;

public class EntQuiz {
	
	private int id;
	private String name;
	private String question;

	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    
    @Override
    public String toString() {
        return "EntQuiz{id=" + id + ", question='" + question + "'}";
    }

    @Override
    public boolean equals(Object obj) {
    	// 自分自身と引数のオブジェクトが同じインスタンスである場合は、trueを返す
        if (this == obj) {
            return true;
        }
        // 引数のオブジェクトがnullであるか、クラスが異なる場合は、falseを返す
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // 引数のオブジェクトをEntQuizクラスにキャスト
        EntQuiz entQuiz = (EntQuiz) obj;
     // idフィールドとquestionフィールドが等しいかどうかを比較し、その結果を返す
        return Objects.equals(id, entQuiz.id) &&
               Objects.equals(question, entQuiz.question);
    }

}
