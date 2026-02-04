package com.fresh.temp.demo.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CreateFile {

    public static void main(String[] args) throws IOException
    {
        String filePath = "F:\\temp";
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        long start = System.currentTimeMillis();
        for(int i=0; i < 1; i++){
            String fileName = "auto-test1" + i;
            File f = new File(filePath, fileName);
            if(!f.exists()){
                createFile(f,30000000000l);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("total times " + (end - start));

        start = System.currentTimeMillis();
        for(int i=0; i < 1; i++){
            String fileName = "auto-test2" + i;
            File f = new File(filePath, fileName);
            if(!f.exists()){
                createFixLengthFile(f,30000000000l);
            }
        }
        end = System.currentTimeMillis();
        System.out.println("total times "+(end-start));
    }

    public static void createFixLengthFile(File file, long length) throws IOException {
        FileOutputStream fos = null;
        FileChannel output = null;
        try {
            fos = new FileOutputStream(file);
            output = fos.getChannel();
            output.write(ByteBuffer.allocate(1), length-1);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createFile(File file, long length) throws IOException {
        RandomAccessFile ff = null;
        try {
            ff = new RandomAccessFile(file,"rw");
            ff.setLength(length);
        } finally {
            if (ff != null) {
                try {
                    ff.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
