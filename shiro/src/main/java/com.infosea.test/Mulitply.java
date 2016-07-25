package com.infosea.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by infosea on 2016/7/5.
 */
public class Mulitply {


    public static void main(String[] args) throws IOException {
        File dir = new File("setsglog");
        File[] files = dir.listFiles();
        String line="";
        String subline = "";
        int count=0;
        BufferedWriter bw = new BufferedWriter((new FileWriter("ip.csv")));
        BufferedReader br = null;
        for(File file : files) {
           br = new BufferedReader(new FileReader((file)));
            while((line = br.readLine())!=null){
                if(!line.startsWith("[")){
                    continue;
                }else {
//                    System.out.println("--"+line);
                    subline =line.substring("[2016-07-01 00:16:02] ".length());
//                    System.out.println(subline);
                    if(!subline.startsWith("192")){
                        continue;
                    }else {
                        count++;
                        subline = subline.substring(0,subline.indexOf(",")+1);

                        bw.write(subline+"\r\n");
                        if(count%100==0){
                            bw.flush();
                        }
                    }
                }
            }

        }
        bw.flush();
        bw.close();
        br.close();

    }

}
