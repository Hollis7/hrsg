
package com.hdb.hrsguard.abse.demo;


import com.hdb.hrsguard.abse.scheme.*;
import com.hdb.hrsguard.constant.FilePathConstant;


public class DemoForBswabe {
	final static boolean DEBUG = true;
	//universal attribute set, any attribute is in u. 
	final static String []u={"UESTC","patient", "doctor","peter","director","deputy","male","female"};
	//attributes of the user
	final static String []attrs = {"UESTC","patient","doctor","peter","deputy","male"};
	//attributes of the policy
	final static String []policy = {"UESTC","patient","doctor","peter","deputy","male"};
	final static String []file = {"1","3","5","7","48"};//包含关键字的文件标识
	final static String word = "digestive";//要搜索的部分
//test
	private static final String doctor_plain_path= FilePathConstant.DOCTOR_PLAINTEXT_PATH;
	private static final String doctor_cipher_path=FilePathConstant.DOCTOR_CIPHERTEXT_PATH;

	public static void main(String[] args) throws Exception {
		BswabePub pub = new BswabePub();
		BswabeMsk msk = new BswabeMsk();
		BswabePrv prv;
		BswabeToken token;
		BswabeCph cph;

		boolean result = false;	

		println("//demo for bswabe: start to setup");
		Bswabe.setup(u,pub, msk);
		println("//demo for bswabe: end to setup");

		println("\n//demo for bswabe: start to enc");
		cph = Bswabe.enc(u,pub, policy, word);
		println("//demo for bswabe: end to enc");

		println("\n//demo for bswabe: start to keygen");
		prv = Bswabe.keygen(u,pub, msk, attrs);
		println("//demo for bswabe: end to keygen");

		println("\n//demo for bswabe: start to tokengen");
		token = Bswabe.tokgen(prv,pub,word);
		println("\n//demo for bswabe: end to tokengen");

		println("\n//demo for bswabe: start to search");
		result = Bswabe.search(pub, token, cph);
		println("//demo for bswabe: end to dec");
		System.out.println("this is result:"+result);

		if (result){
			for(int i=0;i<file.length;i++)
				System.out.print(file[i]+" ");
			System.out.println();
		}
		else
			System.err.println("There are no results!");
	
	
	}


	private static void println(Object o) {
		if (DEBUG)
			System.out.println(o);
	}
}
