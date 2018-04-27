package codeu.model.data;

import java.util.ArrayList;

public class SecurityQuestion {

    public static ArrayList<String> questions;
    private final String value;
    private final String answer;
    
    public SecurityQuestion(String value, String answer)
    {
        this.answer = answer;
        this.value = value;
        
        
    }
    
    static void addQuestion(String question)
    {
        questions.add(question);
    }
    
    public boolean checkAnswer(String guess)
    {
        return guess.trim().equalsIgnoreCase(this.answer.trim());
        
    }
    
    static void deleteQuestion(String question)
    {
        questions.remove(question);
    }
    
    static void deleteQuestion(int questionIndex)
    {
        questions.remove(questionIndex);
    }
}
