package edu.osu.com.passwordgenerator.utility;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by blakew on 4/15/2015.
 */
public class WordModule{

    private String word;
    private Set<String> extraCharacterSet;

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

    public Set<String> getExtraCharacterSet() {
        return extraCharacterSet;
    }

    public void setExtraCharacterSet(Set<String> extraCharacterSet) {
        this.extraCharacterSet = extraCharacterSet;
    }

}