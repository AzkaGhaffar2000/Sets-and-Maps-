
import java.util.*;
import java.util.Scanner;
import java.io.*;

public class AminoAcid {
	/* The main method asks the user for two text files(DNA sequence file and amino acid file)
	and then uses File to hold the file and Scanner to read from both of those files. It calls 
	a method called getData and gives the scanners as parameters to that method */
    public static void main(String[] args) throws Exception {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the DNA sequence file ending with .txt: ");
		String dnaFile = keyboard.nextLine();
		System.out.print("Enter the amino acid file ending with .txt: ");
		String aminoFile = keyboard.nextLine();
		File file = new File(dnaFile); 
		Scanner DNAreader = new Scanner(file);
		File file1 = new File(aminoFile); 
		Scanner aminoReader = new Scanner(file1);				
		getData(DNAreader, aminoReader);
	}
	/* This method gets both scanners as parameters and then reads all the lines of
	DNAreader scanner and stores them into a stringbuffer. It reads the codon and 
	amino acid of aminoReader scanner seperately and stores them into a Map. To avoid 
	errors it removes all the digits and white spaces from the DNA string and then 
	stores the new list into another stringbuffer*/
	public static void getData(Scanner DNAreader, Scanner aminoReader) {
		MyListMap<String, String> theMap= new MyListMap<String, String>();
		StringBuffer nString= new StringBuffer();
		StringBuffer newString= new StringBuffer();
		while (DNAreader.hasNextLine()) { 
			nString.append(DNAreader.nextLine());
		}
		while (aminoReader.hasNextLine()) {
			String base = aminoReader.nextLine();
			Scanner sc = new Scanner(base);
			while (sc.hasNext()) {
				String codon= sc.next();
				String Amino= sc.next();
				theMap.put(codon, Amino);
			}
		}
	for (int l= 0; l< nString.length(); l++) {
			if(!Character.isWhitespace(nString.charAt(l)) && !Character.isDigit(nString.charAt(l))) {
				 newString.append(nString.charAt(l));
			}
		}
		nString.setLength(0);
		getPosition(nString, newString, theMap);
	}
	/* This method gets the length of new stringbuffer and goes through a for loop,
	in the for loop it stores every nth character of new String into the old String 
	which is null in the beginning of loop. After every the length of nString reaches 
	3 it uses Map to get those three characters out and store them into a string, which
	is the amino acid in this case. If Map has it, it returns them else null. It them 
	uses another Map to get that string, if it returns null, then it just simply add 
	the position of that into Set. If it is not null then it will make that Set equal to 
	the amino acid and add the position to Set. Then it puts both the amino acid String 
	and Set containing position into Map, makes the old String empty and add 3 to the position(i)
	since the codon are three character long.*/
	public static void getPosition(StringBuffer nString, StringBuffer newString, MyListMap<String, String> theMap) {
		MyListMap<String, MyListSet<Integer>> theMap2= new MyListMap<String, MyListSet<Integer>>();
		int i= 0;
		int length1= newString.length();
		for (int n= 0; n < length1; n++) {
			MyListSet<Integer> theSet = new MyListSet<>();
			nString.append(newString.charAt(n));
			if (nString.length()==3) {
				String threeChar= theMap.get(nString.toString());
				if(theMap2.get(threeChar)== null) {
					theSet.add(i);
				} else {
					theSet= theMap2.get(threeChar);
					theSet.add(i);
				}
				theMap2.put(threeChar, theSet);
				nString.setLength(0);
				i= i+3;
			}
		}
		printResult(theMap2);
	}
	/* This method uses and itertor to Store the map data since map has no toString method
	and then goes through line and prints it.*/ 
	public static void printResult(MyListMap<String, MyListSet<Integer>> theMap2) {
		Iterator<MapEntry<String, MyListSet<Integer>>> iterate = theMap2.iterator();
		while(iterate.hasNext()) {
			System.out.println(iterate.next());
		}
	}
}