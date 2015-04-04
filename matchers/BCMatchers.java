package com.rstyle.qa.utils.matchers.matchers;


import com.rstyle.qa.utils.matchers.matchers.common.HasErrorsMatcher;
import org.hamcrest.Matcher;

/**
 * Created by maksim.serebryanskiy on 09.02.2015.
 */
public class BCMatchers {

    private BCMatchers() {
    }

    public static Matcher<String> hasErrors(String... errors) {
        return HasErrorsMatcher.hasErrors(errors);
    }
}
