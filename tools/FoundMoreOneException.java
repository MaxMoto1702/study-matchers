package com.rstyle.qa.utils.matchers.tools;

/**
 * Created by maksim.serebryanskiy on 09.02.2015.
 */
public class FoundMoreOneException extends Exception {
    // тут описать ошибку о том, что неверная регулярка, т.е. по регулярке находится более одного файла
    public FoundMoreOneException (int count){
        super("Уточните регулярное выражение, количество найденых файлов = "+ count);
    }
}
