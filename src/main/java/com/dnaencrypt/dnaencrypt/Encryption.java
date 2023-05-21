package com.dnaencrypt.dnaencrypt;

import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;

public class Encryption {

	static int texlength;
	static int keylength;
	static int asciic[]; //to store message in ASCII code
	static int asciikey[]; //to store key in ASCII code
	static String rearr[]; //to store rearranged array
	static String hex[]; //to store message in hexadecimal code
	static String hexkey[]; //to store key in hexadecimal code
	static String dnacode[];//to store DNA values
	static String revdnacode[];//to store 3142 DNA order
	String intermedcode="";//to store the intermediate code
	
	
	//function to convert to ASCII
		static void ASCIISentence(String sentence,int ascii[])
		    {
		        int l = sentence.length();
		        int convert;
		        
		        for (int i = 0; i < l; i++)
		        {
		            convert = sentence.charAt(i);//converting to ASCII
		            ascii[i]=convert;
//		            System.out.print(ascii[i]);
		        }
		    }
		
		 //converting ASCII to hexadecimal
		 static void toHexadecimal(String arr[],int asciistr[],int len)
		 {
			 for (int i=0;i<len;i++) 
			 { 
				 arr[i]=Integer.toHexString(asciistr[i]); //converting to hexadecimal 
			 }
		 }
		 
		//rearranging columns in the order 2,4,1,3
		 static void rearrange(String arr[],String hexadeci[],int len) 
		 {
			 for(int i=0;i<len;i=i+4) 
			 {
				 arr[i]=hexadeci[i+1];
			 }
			 for(int i=1;i<len;i=i+4) 
			 {
				 arr[i]=hexadeci[i+2];
			 }
			 for(int i=2;i<len;i=i+4) 
			 {
				 arr[i]=hexadeci[i-2];
			 }
			 for(int i=3;i<len;i=i+4) 
			 {
				 arr[i]=hexadeci[i-1];
			 }
		 }
		 
		 //function to convert hexadecimal to binary coded decimal
		 public String hextoBCD(String s) 
		 {
			 String result="";
			 int len=s.length();
			 for(int i=0;i<len;i++) 
			 {
				 if(Character.isDigit(s.charAt(i))) 
				 {
					 String res=Integer.toBinaryString((int)s.charAt(i));
					 //System.out.print(res.substring(res.length() - 4) + " ");
					 result=result.concat(res.substring(res.length()-4));
				 }
				 else 
				 {
					 String res=Integer.toBinaryString((int)s.charAt(i)-55);
					 //System.out.print(res.substring(res.length() - 4) + " ");
					 result=result.concat(res.substring(res.length()-4));
				 }
			 }
			 return result;
		 }
		 
		 //function to x-or two numbers
		 static String xoring(String a,String b,int n) 
		 {
			 String ans="";
			 for(int i=0;i<n;i++) 
			 {
				if(a.charAt(i) == b.charAt(i)) 
					ans+="0";
				else
					ans+="1";
			 }
			return ans; 
		 }
		 
		 //function to convert to DNA code
		 static String dna(String a) 
		 {
			 String ans="";
			 int alength=a.length();
			 for(int i=0;i<alength;i=i+2) 
			 {
				 if(a.substring(i,i+2).equals("00"))
					 ans+="A";
				 else if(a.substring(i,i+2).equals("01"))
					 ans+="C";
				 else if(a.substring(i,i+2).equals("10"))
					 ans+="G";
				 else if(a.substring(i,i+2).equals("11"))
					 ans+="T";
			 }
			return ans;
		 }
		
		
//	public static void main(String[] args)throws InputMismatchException, Exception {
	public static String encrypt(String str1, String str2)throws InputMismatchException, Exception {
		// TODO Auto-generated method stub
		Encryption ob=new Encryption();
		String plaint=str1;
		String key=str2;
//		System.out.println("Enter the message to be encrypted");
//		String plaint=sc.nextLine();//to store message from input
		texlength=plaint.length();
		
		//addition of padding
				if(texlength%4!=0) 
				{
					int p=texlength%4;
					for(int i=p;i<4;i++) 
					{
						texlength+=1;
						plaint=plaint+" ";
					}
				}
				
//		System.out.println("Enter the key");
//		String key=sc.nextLine();//to store key
		keylength=key.length();
//		sc.close();	
		
		//converting to ASCII
		asciic=new int[texlength];
		ASCIISentence(plaint,asciic);
				
				
		//convert to hexadecimal and printing
		hex=new String[texlength];
		toHexadecimal(hex,asciic,texlength);
				
				
		//rearranging columns in the order 2,4,1,3
		rearr= new String[texlength];
		rearrange(rearr,hex,texlength);
				
				
		//converting key to ASCII and printing->which is to be removed
		asciikey=new int[keylength];
		ASCIISentence(key,asciikey);
				
				
		//convert key to hexadecimal and printing->which is to be removed
		hexkey=new String[keylength];
		toHexadecimal(hexkey,asciikey,keylength);
				
				
		//converting to DNA code
		dnacode=new String[texlength];
		int ctr=0;
		for(int i=0;i<texlength;i++) 
		{
			if(i%4==0)
				ctr=0;
					
			String sptr=rearr[i];
			String keyptr=hexkey[ctr];

			String dut=ob.hextoBCD(sptr);
			String dut1=ob.hextoBCD(keyptr);
			int k=dut.length();
			String xorval=xoring(dut,dut1,k);
			dnacode[i]=dna(xorval);
			ctr++;
		}
		
				
				
		//reordering DNA code in 3,1,4,2 order
		revdnacode=new String[texlength];
		for(int i=0;i<texlength;i++) 
		{
			if(i%4==0)
				revdnacode[i]=dnacode[i+3];
			else if(i%4==1)
				revdnacode[i]=dnacode[i+1];
			else if(i%4==2)
				revdnacode[i]=dnacode[i-1];
			else if(i%4==3)
				revdnacode[i]=dnacode[i-3];
		}
		
		//reading the text column by column (top to bottom)
		for(int i=0;i<texlength;i=i+4) 
		{
			ob.intermedcode+=revdnacode[i];
		}
		for(int i=1;i<texlength;i=i+4) 
		{
			ob.intermedcode+=revdnacode[i];
		}
		for(int i=2;i<texlength;i=i+4) 
		{
			ob.intermedcode+=revdnacode[i];
		}
		for(int i=3;i<texlength;i=i+4) 
		{
			ob.intermedcode+=revdnacode[i];
		}	
		 
//		System.out.println("Final DNA encrypted code");
//		System.out.println(intermedcode);
		
		//AES encryption begins
		
		//System.out.print("Enter the key value: ");
        String keyString = "ABCDEF0123456789";
        
        // Convert the key string to bytes
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);

        // Create a SecretKeySpec object from the key bytes
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // Create a Cipher object and initialize it for encryption
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Prompt the user to input the plain text to be encrypted
        //System.out.print("Enter the plain text to be encrypted: ");
        String plaintext = ob.intermedcode;

        // Convert the plain text string to bytes
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);
        // Encrypt the plain text using the Cipher object
        byte[] ciphertextBytes = cipher.doFinal(plaintextBytes);

        // Encode the cipher text as a Base64 string for easy display
        String ciphertext = Base64.getEncoder().encodeToString(ciphertextBytes);
        System.out.println("AES encrytped data: " + ciphertext);
//        plaint="";
        return ciphertext;
        
        //file generation
//        PrintWriter out=new PrintWriter("output.txt");
//        out.print(ciphertext);//writing the cipher text into the file
//        out.close();

	}

}

