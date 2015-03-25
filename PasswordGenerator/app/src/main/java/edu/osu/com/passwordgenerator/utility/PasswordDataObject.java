package edu.osu.com.passwordgenerator.utility;

import java.util.List;
import java.util.Set;

public class PasswordDataObject {
    private int passwordLength;
    private int uppercaseCount;
    private int numberCount;
    private int specialCharacterCount;

    private List<WordModule> wordModuleList;

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

    private class WordModule{
        private String word;
        private Set<String>  extraCharacterSet;

    }
}
