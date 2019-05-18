package com.ab.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ab.model.Bank;

public class FileUtil {

	/**
	 * To read file from class path or resources folder.</br>
	 * NOTE : check whether <b>bank.txt</b> file is in resource folder or not,
	 * if not, first create it to avoid <b>NullPointerException</b>
	 * @param fileName
	 * @return File
	 */
	public static File getFileFromClassPath(String fileName){
		ClassLoader classLoader = new FileUtil().getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}

	/**
	 * @param startNo
	 * @param endNo
	 * @param file
	 * @throws IOException
	 */
	public static void writeToFile(int startNo, int endNo, File file,boolean deleteData) throws IOException {
		if (file == null)
			throw new FileNotFoundException("File is missing");
		if (startNo < 0 || endNo <= startNo)
			throw new IllegalArgumentException(
					"end no must be greater than startNo or startNo must not be less than 0");
		if (!file.exists()) {
			file.createNewFile();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,deleteData))) {
			System.out.println("Data is Started to Write in File at : "+file.getAbsoluteFile());
			String line = null;
			writer.write("bankId,bankName,ifsCode,estYear\n");
			for (int i = startNo; i <= endNo; ++i) {
				line = i + "," + "BANK" + i + "," + "BANK" + new Random().nextInt(999999) + ","
						+ (1950+new Random().nextInt(65));
				writer.write(line);
				if(i!=endNo) {
					writer.append("\n");
				}
			}
		}
	}
	
	public static List<Bank> getBankListFromFile(File file) throws IOException{
		List<Bank> bankList=new ArrayList<>();
		if(file==null)
			throw new FileNotFoundException("File is Missing");
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line=null;
			while((line=reader.readLine())!=null) {
				Bank bank=new Bank();
				String dataArr[]=line.split(",");
				bank.setBankId(Integer.parseInt(dataArr[0]));
				bank.setBankName(dataArr[1]);
				bank.setIfsCode(dataArr[2]);
				bank.setEstYear(Integer.parseInt(dataArr[3]));
				bankList.add(bank);
			}
		}
		return bankList;
	}
	
	

}
