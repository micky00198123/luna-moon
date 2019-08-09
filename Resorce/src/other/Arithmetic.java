package other;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

public class Arithmetic {

	// 算数表达式支持:  + - * / ()
	public static int arithmetic(String str){

		Queue<String> expression = new ConcurrentLinkedQueue<>(); 	// 运算符队列
		Queue<String> number = new ConcurrentLinkedQueue<>();		// 数字队列
		Stack<String> calculate = new Stack<>();					// 计算栈
		
		String willOffer;
		StringBuilder numStr = new StringBuilder();
		for(int i = 0; i < str.length(); i ++){
			willOffer = "" + str.charAt(i);
			
			// 如果是运算符,入运算符队列
			if(Arithmetic.isExpression(willOffer)){
				expression.offer(willOffer);
				continue;
			}
			
			// 如果是数字,整合后入数字队列
			if(Arithmetic.isNumberByPattern(willOffer)){
				numStr.append(willOffer);
				if(i+1 >= str.length() 
					|| !Arithmetic.isNumberByPattern("" + str.charAt(i+1))){
					number.offer(numStr.toString());
					numStr = new StringBuilder();
				}
			}
		}
		
		// 开始计算
		// 数字队列先移出一数字至计算栈
		calculate.push(number.poll());
		String preExpression; // 前一个运算符
		String lastExpression = expression.poll(); // 后一个运算符
		
		// 当两个队列都不为空时
		while(!number.isEmpty() || !expression.isEmpty()){
			
			// 入栈一个运算符及数字
			preExpression = lastExpression;
			calculate.push(preExpression);
			calculate.push(number.poll());
			
			// 如果运算符队列还不是空,获取下一个运算符
			if(!expression.isEmpty())
				lastExpression = expression.poll();
			
			if(lastExpression.equals("(")){
				String num = calculate.pop();
				preExpression = lastExpression;
				calculate.push(preExpression);
				calculate.push(num);
				
				lastExpression = expression.poll();
			}

			while(lastExpression.equals(")")){

				while(!preExpression.equals("(")){
					int b = Arithmetic.stringToNumber(calculate.pop());
					String exp = calculate.pop();
					int a = Arithmetic.stringToNumber(calculate.pop());
					int resultNum = Arithmetic.doArithmetic(a, exp, b);
					
					preExpression = calculate.peek();
					calculate.push(Arithmetic.numberToString(resultNum));
				}
				
				String num = calculate.pop(); // 弹开顶部的数字
				calculate.pop(); // 弹掉"("
				preExpression = calculate.peek(); // 获取"("之前的运算符
				calculate.push(num); // 把弹开的数字重新放回去
				if(expression.isEmpty())
					break;
				else
					lastExpression = expression.poll(); // 丢掉")"
				
				
			}
			
			// 如果前一个运算符优先级较大或相等,可以先进行出栈计算
			while(Arithmetic.comparePriority(preExpression, lastExpression)){
				
				// 出栈顺序不能反
				int b = Arithmetic.stringToNumber(calculate.pop());
				String exp = calculate.pop();
				int a = Arithmetic.stringToNumber(calculate.pop());
				// 计算结果
				int resultNum = Arithmetic.doArithmetic(a, exp, b);
				
				// 1.如果计算栈空了,无需进行下面的检查,直接将计算结果入栈并退出计算即可
				if(calculate.isEmpty()){
					calculate.push(Arithmetic.numberToString(resultNum));
					break;
				}
				
				// 2.如果计算栈未空,将前运算符向前再移动,进行下轮循环
				preExpression = calculate.peek();
				calculate.push(Arithmetic.numberToString(resultNum));
				
			}
			
		}


		// 此时栈顶元素可能是结果,先行出栈,查看计算栈中是否还有剩余元素
		int result = Arithmetic.stringToNumber(calculate.pop());

		// 如果尚未计算完,将计算栈中剩余元素进行计算,此时栈底优先级小,栈顶优先级大
		while(!calculate.isEmpty()){
			// 之前出栈的元素给予b,再出栈一个运算符和一个数字
			int b = result;
			String exp = calculate.pop();
			int a = Arithmetic.stringToNumber(calculate.pop());
			// 计算结果
			result = Arithmetic.doArithmetic(a, exp, b);
		}
		
		return result;
	}
	
	
	// 数字转字符串
	private static String numberToString(int num){
		return String.valueOf(num);
	}
	
	// 字符串转数字
	private static int stringToNumber(String str){
		return Integer.valueOf(str);
	}
	
	
	// 比较运算符优先级,左大或等为true,小为false
	private static Boolean comparePriority(String str1, String str2){
		return Arithmetic.getPriority(str1) >= Arithmetic.getPriority(str2);
	}
	
	// 获取运算符优先级
	private static int getPriority(String str){
		if(str.equals("("))
			return 0;
		if(str.equals("+") || str.equals("-"))
			return 1;
		if(str.equals("*") || str.equals("/"))
			return 2;
		if(str.equals(")"))
			return 3;
		return 0;
	}
	
	
	// 判断字符串是否是算数表达式
	private static Boolean isExpression(String str){
		return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")
				|| str.equals("(") || str.equals(")");
	}
	
	// 正则表达式判断该字符串是否是数字
	private static Boolean isNumberByPattern(String str){
		if(str == null)
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(str).matches(); 
	}
	
	
	// 进行计算
	private static int doArithmetic(int a, String exp, int b){
		if(exp.equals("+"))
			return Arithmetic.addition(a, b);
		if(exp.equals("-"))
			return Arithmetic.subtraction(a, b);
		if(exp.equals("*"))
			return Arithmetic.multiplication(a, b);
		if(exp.equals("/"))
			return Arithmetic.division(a, b);
		return 0;
	}
	
	// 加法
	private static int addition(int a, int b){
		return a + b;
	}
	
	// 减法
	private static int subtraction(int a, int b){
		return a - b;
	}
	
	// 乘法
	private static int multiplication(int a, int b){
		return a * b;
	}
	
	// 除法
	private static int division(int a, int b){
		return a / b;
	}


	

}
