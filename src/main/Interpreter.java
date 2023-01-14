package main;

import java.io.FileNotFoundException;
import java.util.Stack;

// if variables are not found in memory catch the exception and return a custom error
public class Interpreter {
	Stack<String> callbacks;
	public Interpreter() {
		callbacks = new Stack<String>();
	}
	
	public int interpret (Kernel k,String programFile) {
		try {
		String process = k.Readfile(programFile);
		String[] lines = process.split("\n"); 
		for(String line : lines) {
			String[] instructions = line.split(" ");
				for(String inst : instructions) {
					this.callbacks.push(inst);
				}	
		String operand1 ="";
		String operand2 ="";
		while(!this.callbacks.isEmpty()) {
			String poppedValue = this.callbacks.pop();
			
			switch(poppedValue) {
			case "print": k.print(operand2); break;
			case "add" : add(k,operand1,operand2); operand1 = operand2 = ""; break;
			case "assign" : assign(k,operand1,operand2); operand1 = operand2 = "" ; break;
			case "writeFile": k.Writefile(operand1, operand2); operand1 = operand2 = ""; break;
			case "readFile":callbacks.push(k.Readfile(operand2)); operand2 = ""; break;
			case "input": callbacks.push(""+k.input());break;
			default: if(operand2.equals("")) 
				operand2=poppedValue;
			else
				operand1=poppedValue;
			}
			
		}
		}
		return 0;
		}catch(NullPointerException | ClassCastException | FileNotFoundException e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
		
	}
	public void add(Kernel k,String x,String y) {
		Object temp = k.readFromMem(x);
		if(temp == null)
		throw new NullPointerException("Variable "+ x + " does not exist in memory"); //to get a custom message
		int val1 = (int) temp;
		
		temp = k.readFromMem(y);
		if(temp == null)
		throw new NullPointerException("Variable "+ y + " does not exist in memory"); //to get a custom message 
		int val2= (int) temp;
		
		k.WriteToMem(x,val1+val2);
	}
	public void assign(Kernel k, String variable,String val) {
		
		try {
			int value = Integer.parseInt(val);
			k.WriteToMem(variable, value);
		}
		catch(NumberFormatException e)
		{
			Object value;
			if(k.readFromMem(val)==null) {
				 value=val;
			}
			else
			{
				 value =  k.readFromMem(val);
			}
			k.WriteToMem(variable,value);
		}
		
	}

}
