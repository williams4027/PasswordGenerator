package edu.osu.com.passwordgenerator.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by blakew on 4/15/2015.
 */
public class WordModule implements Serializable{

    private String word;
    private List<Character> extraCharacterList;

    public WordModule(String word){
        this.word = word;
        this.extraCharacterList = new ArrayList<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Character> getExtraCharacterList() {
        return extraCharacterList;
    }

    public void setExtraCharacterList(List<Character> extraCharacterList) {
        this.extraCharacterList = extraCharacterList;
    }

    @Override
    public String toString() {
        return "WordModule{" +
                "word='" + word + '\'' +
                ", extraCharacterList=" + extraCharacterList +
                '}';
    }
}