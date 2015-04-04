package com.rstyle.qa.utils.matchers.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created with IntelliJ IDEA.
 * User: Anton.Tatarinov
 * Date: 02.02.15
 * Time: 7:25
 */
public class ArchiveTool {

    private File outputFile;
    private String tempDir;


    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public ArchiveTool(File file) {

        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(file)))
        {

            ZipEntry entry;
            String name;
            long size;

            while((entry=zin.getNextEntry())!=null){
                name = entry.getName(); // получим название файла
                    size=entry.getSize();  // получим его размер в байтах
                    //System.out.printf("Название: %s \t размер: %d \n", name, size);

                    //Работа с путем файла
                    String path = file.getAbsolutePath();
                    String dir=path.substring(0, path.lastIndexOf(".") );
                    setTempDir(dir+"\\");
                    createDir(dir + "\\");
                    outputFile = new File(dir+"\\"+name);
                    FileOutputStream fos = new FileOutputStream(outputFile); //открываем выходной поток и создаем фаил
                    //разархивируем фаил
                    int nLength;
                    byte[] buf = new byte[8000];
                    while(true){
                        nLength = zin.read(buf);
                        if(nLength < 0)
                            break;
                        fos.write(buf, 0, nLength);
                    }

                    fos.close();
            }
            zin.close();
        }
        catch(Exception ex){
            System.out.println(ex);


            String path = file.getAbsolutePath();
            String dir=path.substring(0, path.lastIndexOf(".") );
            deleteDir(dir);


        }
    }

    public void clean(){
        deleteDir(getTempDir());
    }

    public File fileNameFilter(String regexp) throws FoundMoreOneException{

        File file = new File(getTempDir());
        int count = 0;
        String[] list = file.list();
        for (String name : list) {
            if (checkWithRegExp(name,regexp)){
                count++;
                file = new File(getTempDir()+name);
            }
        }
        if (count>1||count==0) throw new FoundMoreOneException(count){};
        return file;
    }

    public static boolean checkWithRegExp(String string,String regexp){
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(string);
        return m.matches();
    }

    public static void createDir(String path){
        File f = new File( path );
        f.mkdirs();
    }

    public static void deleteDir(String path){
        File file = new File( path );
        if(!file.exists())
            return;
        if(file.isDirectory()){
            for(File f : file.listFiles())
                deleteDir(f.getAbsolutePath());
            file.delete();
        }else {
            file.delete();
        }
    }


}
