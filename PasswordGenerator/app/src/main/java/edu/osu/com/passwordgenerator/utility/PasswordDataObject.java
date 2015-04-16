package edu.osu.com.passwordgenerator.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PasswordDataObject implements Serializable {

    private int passwordLength;
    private int uppercaseCount;
    private int numberCount;
    private int specialCharacterCount;

    private List<WordModule> wordModuleList;

    // List of supported special characters
    List<Character> specialCharacterList;

    public PasswordDataObject() {

        wordModuleList = new ArrayList<>();

        char[] specialCharacterArray = new char[]{
                '@', '%', '+', '\\', '/', '\'', '!', '#', '$', '^', '?', ':', ',',
                '(', ')', '{', '}', '[', ']', '~', '-', '_'
        };

        specialCharacterList = new ArrayList<>();
        for (char currentChar : specialCharacterArray) {
            specialCharacterList.add(currentChar);
        }
    }

    /*
    List<WordModule>
    Dog         _   3
    cAt         _   &
    haT         _   4

    Length: 12
    Upper: 3    x
    Num: 2      x
    SC: 1       x

    toString: Dog3cAt&haT
     */


    public void clearCharacterSet() {
        for (WordModule currentWordModule : this.getWordModuleList()) {
            List<Character> removeList = new ArrayList<>();
            for (Character currentChar : currentWordModule.getExtraCharacterSet()) {
                if (this.specialCharacterList.contains(currentChar)) {
                    removeList.add(currentChar);
                }
            }
            for (Character removeChar : removeList) {
                currentWordModule.getExtraCharacterSet().remove(removeChar);
            }
        }
    }

    public void clearNumberSet() {
        for (WordModule currentWordModule : this.getWordModuleList()) {
            List<Character> removeList = new ArrayList<>();
            for (Character currentChar : currentWordModule.getExtraCharacterSet()) {
                if (!this.specialCharacterList.contains(currentChar)) {
                    removeList.add(currentChar);
                }
            }
            for (Character removeChar : removeList) {
                currentWordModule.getExtraCharacterSet().remove(removeChar);
            }
        }

    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
    }

    public int getUppercaseCount() {
        return uppercaseCount;
    }

    public void setUppercaseCount(int uppercaseCount) {
        this.uppercaseCount = uppercaseCount;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    public int getSpecialCharacterCount() {
        return specialCharacterCount;
    }

    public void setSpecialCharacterCount(int specialCharacterCount) {
        this.specialCharacterCount = specialCharacterCount;
    }

    public void addWordModule(WordModule currentWordModule) {
        this.wordModuleList.add(currentWordModule);
    }

    @Override
    public String toString() {
        return "PasswordDataObject{" +
                "passwordLength=" + passwordLength +
                ", uppercaseCount=" + uppercaseCount +
                ", numberCount=" + numberCount +
                ", specialCharacterCount=" + specialCharacterCount +
                ", wordModuleList=" + wordModuleList +
                '}';
    }

    public List<WordModule> getWordModuleList() {
        return wordModuleList;
    }
}
