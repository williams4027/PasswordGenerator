package edu.osu.com.passwordgenerator.utility;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by blakew on 4/15/2015.
 */
public class WordModule implements Serializable{

    private String word;
    private Set<Character> extraCharacterSet;

    public WordModule(String word){
        this.word = word;
        this.extraCharacterSet = new HashSet<>();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Character> getExtraCharacterSet() {
        return extraCharacterSet;
    }

    public void setExtraCharacterSet(Set<Character> extraCharacterSet) {
        this.extraCharacterSet = extraCharacterSet;
    }

    @Override
    public String toString() {
        return "WordModule{" +
                "word='" + word + '\'' +
                ", extraCharacterSet=" + extraCharacterSet +
                '}';
    }
}