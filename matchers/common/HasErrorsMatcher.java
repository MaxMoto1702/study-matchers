package com.rstyle.qa.utils.matchers.matchers.common;

import com.rstyle.qa.utils.matchers.tools.ArchiveTool;
import com.rstyle.qa.utils.matchers.tools.FoundMoreOneException;
import com.rstyle.qa.utils.matchers.tools.XMLTool;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

/**
 * Created by maksim.serebryanskiy on 09.02.2015.
 */
public class HasErrorsMatcher extends TypeSafeMatcher<String> {
    private String[] errors;
    protected List nodes = new ArrayList();

    public HasErrorsMatcher() {}

    public HasErrorsMatcher(String... errors) {
        this.errors = errors;
    }

    @Override
    protected boolean matchesSafely(String filePath) {
        String regexp = ".*";
        //String filePath = "C:\\Test\\xpath.zip";
        String xpathExpression = "//РЕЕСТР_ПЕРЕДАЧИ_СПН_ПО_УМЕРШИМ_ПО_ДОГОВОРАМ_НЕ_ВСТУПИВШИМ_В_СИЛУ/СтраховойНомер[.='172890100']/../ВзносыОПС";
        File xmlFile = null;
        // пример использования тулзов
        try {
            ArchiveTool archiveFile = new ArchiveTool(new File(filePath));
            xmlFile = archiveFile.fileNameFilter(regexp);
            nodes = new  XMLTool().findNodes(xmlFile,xpathExpression);
            archiveFile.clean();
        }catch (FoundMoreOneException ex){
            ex.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return hasCode(errors);
    }

    // тут логика проверки наличия ошибок
    protected boolean hasCode(String... errors) {
        boolean b = false;
        for(String error : errors) {
            if (nodes.contains(error)) {
                b=true;
            }else {
                b=false;
            }
        }
        return b;
    };






    @Override
    public void describeTo(Description description) {
        // тут описание в случае ошибки
    }

    @Factory
    public static Matcher<String> hasErrors(final String... errors) {
        return new HasErrorsMatcher(errors);
    }
}
