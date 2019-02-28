package com.ft.xml;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class TestFile {
    @Test
    public void test() throws IOException {
        String file = "./results/12345.json";
        File f = new File("results");
        //boolean b = f.mkdirs();
        RandomAccessFile f1 = new RandomAccessFile(file, "rw");
        f1.write("hello".getBytes());
        f1.close();
    }
}
