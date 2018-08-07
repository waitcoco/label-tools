package com.richinfo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String[]> getFiles(String path,String exend) {
        List<String[]> files = new ArrayList<String[]>();
        File file = new File(path);
        System.out.println("path==="+path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()&&(tempList[i].getName().toLowerCase().endsWith(exend))) {
              String[] ss = new String[2];
              ss[0]=file.getName();
              ss[1]=tempList[i].getName();
              files.add(ss);
            }
            if(tempList[i].isDirectory()){
                files.addAll(getFiles(tempList[i].getAbsolutePath(),exend));
            }
        }
        return files;
    }
    public static void main(String[] args) {
//        List<String[]> filenames = FileUtils.getFiles("/Users/robinjie/Documents/数据标注/吸毒笔录/111","html");
//        for(String[] filename:filenames){
//            System.out.println(filename[0]+"--->"+filename[1]);
//        }
        List<String[]> picurls = FileUtils.getPicUrlByFile("/Users/robinjie/Documents/数据标注/person_res2.txt");
        for(String picurl[]:picurls){
            System.out.println(picurl[0]);
        }
    }
    public static List<String[]> getPicUrlByFile(String filepath){
        List<String[]> urls = new ArrayList<>();
        File file = new File(filepath);
        BufferedReader reader=null;
        String temp="";
        try{
            reader=new BufferedReader(new FileReader(file));
            while((temp=reader.readLine())!=null){
                String[] ss = new String[2];
                ss[0]=file.getName();
                ss[1] = temp;
                urls.add(ss);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try{
                    reader.close();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return urls;
    }

}
