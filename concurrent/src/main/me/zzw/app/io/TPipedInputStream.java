package me.zzw.app.io;

import java.io.*;

/**
 * Created by infosea on 2016-09-12.
 */
public class TPipedInputStream {
    PipedInputStream pis ;
    BufferedReader br;
    PipedOutputStream pos;

    public TPipedInputStream(PipedOutputStream pos, BufferedReader br) {
        try {
            this.pis = new PipedInputStream(pos);

        this.br = br;
        this.pos = pos;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            TPipedInputStream pis = new TPipedInputStream(new PipedOutputStream(), new BufferedReader(new FileReader(new File("c:/csb.log"))));
            byte[] bytes ;
            String line ="";
            while((line = pis.br.readLine())!= null) {
                bytes = line.getBytes();
                pis.pos.write(bytes);
                pis.pos.flush();
                int len = pis.pis.read(bytes);
                System.out.println(new String(bytes, 0, len));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
