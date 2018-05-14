package codeu.model.data;

import java.util.ArrayList;

public class SecurityQuestion {

    private final String value;
    private final String answer;
    
    public SecurityQuestion(String value, String answer)
    {
        this.answer = answer;
        this.value = value;
        
        
    }
    
    public boolean checkAnswer(String guess)
    {
        return guess.equalsIgnoreCase(this.answer);
        
    }

    public String getValue()
    {
        return this.value;
    }
    
    public String getAnswer()
    {
        return this.answer;
    }
}
