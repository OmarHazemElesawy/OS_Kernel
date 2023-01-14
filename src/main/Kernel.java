package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
public class Kernel {
	Hashtable<String,Object> memory;

	public Kernel() {
		memory= new Hashtable<String,Object>();
	}

	public void print(String str) {
				if(memory.get(str)==null){
					System.out.println(str);
				}
				else 
				System.out.println(memory.get(str));
				}
	//Readfile a should return a string of the data from a file named a on the disk. 
	//Without an assign or print, this string will not be shown to the user or stored.
	public String Readfile(String source) throws FileNotFoundException {
		String filename = (this.memory.get(source)==null)? source : (String) this.memory.get(source);
		String result="";
		try {
			File file = new File(filename);
		  
		  BufferedReader br = new BufferedReader(new FileReader(file));
		  
		  String st;
		  
			while ((st = br.readLine()) != null) {
			    result+=st;
				result+= "\n";
			}
		br.close();
		} catch (Exception e) {
			throw new FileNotFoundException("This file does not exist.");
		}
		return result;
		
	}
	public void Writefile(String destination,String data) {
		String filename = (this.memory.get(destination)==null)? destination : (String) this.memory.get(destination);
		String value = (this.memory.get(data)==null)? data : "" + this.memory.get(data);
		File f = new File(filename);
		if(f.exists() && !f.isDirectory()) { 
			    BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(filename, true));
				
			    writer.append("\n");
			    writer.append(value);
			    
			    writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		else {
			
		    BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(filename));
			
		    writer.write(value);
		    
		    writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}
	public Object input() {
		 Scanner input = new Scanner(System.in);
		 	if(input.hasNextInt())
		 	{
		 		return input.nextInt();
		 	}
		 	else {
		 		return input.nextLine();
		 	}
		 	
	}
	public Object readFromMem(String varName) {
		if(this.memory.get(varName)==null)
		{
			return null;
		}
		else
			return this.memory.get(varName); 
	}
	public void WriteToMem(String Varname,Object val) {
			this.memory.put(Varname, val);
	}
	public static void main(String[] args) {
		Kernel k = new Kernel();
		Interpreter I = new Interpreter();
		System.out.println(I.interpret(k,"Program 1.txt"));
		I.interpret(k,"Program 2.txt");
		I.interpret(k,"Program 3.txt");
		

		
		
	} 
}




