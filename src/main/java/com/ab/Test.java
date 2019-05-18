package com.ab;

import java.io.File;
import java.io.IOException;

import com.ab.util.FileUtil;

public class Test {
	public static void main(String[] args) {
		File file = FileUtil.getFileFromClassPath("data/bank.txt");
		try {
			FileUtil.writeToFile(10000, 90000, file,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
