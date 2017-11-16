package com.trust.demo.trusttool.work.second;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Trust on 2017/11/11.
 */

public class TestReversePolishExpression {
    static String operator = "+-*/%^()";

    /**
     * 预处理表达式，正、负号前加0(如果一个加号（减号）出现在最前面或左括号后面，则该加号（减号）为正负号)  比如  -1-(-1+1) 这种表达式 会处理成 0-1-(0-1+1)
     */
    public static String pretreatment(String str) {
        StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (operator.indexOf(c) >= 0) {
                if (i == 0) {
                    sb.insert(0, '0');
                    i++;
                } else if (sb.charAt(i - 1) == '(') {
                    sb.insert(i, '0');
                    i++;
                }
            }
        }
        return sb.toString();
    }

    /***
     * 0 优先级相等 ； -1 op1 优先级大于op2； 1 op2 优先级大于 op1
     */
    public static int opcompare(char op1, char op2) {
        if (op1 == '(') { // 遇到括号 就直接入栈 所以 op2 大
            return 1;
        }
        if ('^' == op1) {
            if (op2 == '^') {
                return 0;
            }
            return -1;
        } else if ("+-".indexOf(op1) >= 0) {
            if ("+-".indexOf(op2) >= 0) {
                return 0;
            }
            return 1;
        } else // if("*/%".indexOf(op1) >=0) 没必要 再判断是否为 */% 了
        {
            if ("+-".indexOf(op2) >= 0) {
                return -1;
            } else if ('^' == op2) {
                return 1;
            }
            return 0;
        }
    }
    /**
     * 计算传入的算术表达式
     */
    public static double Calculator2(String s) throws Exception {
        //预处理式子
        String prestr = pretreatment(s);
        //用于保存  逆波兰式 的队列
        LinkedBlockingQueue<String> polish = new LinkedBlockingQueue<String>();
        // 拼接 数字 char ---> numble
        StringBuffer temp = new StringBuffer();

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < prestr.length(); i++) {

            char c = prestr.charAt(i);
            //如果找到 操作符
            if (operator.indexOf(c) >= 0) {
                if (temp.length() > 0) {//如果 有数字 就压栈
                    polish.offer(temp.toString());
                    temp = new StringBuffer();
                }
                switch (c) {
                    case '(':
                        stack.push(c);
                        break;
                    case ')':
                        while (stack.size() > 0) {
                            char op = stack.pop();
                            if (op != '(') {
                                polish.offer(String.valueOf(op));
                            } else {
                                break;
                            }
                        }
                        break;
                    default:
                        if (stack.size() == 0) {
                            stack.push(c);
                        } else {
                            while (stack.size() > 0) {
                                char op1 = stack.lastElement();
                                int com = opcompare(op1, c);
                                if (com <= 0) {
                                    polish.offer(String.valueOf(stack.pop()));
                                } else {
                                    stack.push(c);
                                    break;
                                }
                            }
                            if (stack.size() == 0) {
                                stack.push(c);
                            }
                        }
                        break;
                }
            } else {
                temp.append(c);
            }
        }
        if (temp.length() > 0) {
            polish.offer(temp.toString());
        }
        while (stack.size() > 0) {
            polish.offer(String.valueOf(stack.pop()));
        }
        System.out.println("Calcstra Queue:" + polish.toString());

        return CalcstraWithQueue(polish);
    }
    /**
     *  计算 逆波兰表达式  用队列表示
     * @throws Exception  各种错误都有可能
     */
    public static double CalcstraWithQueue(Queue<String> que) throws Exception {
        Stack<Double> stack = new Stack<Double>();
        while(true)
        {
            String str = que.poll();
            if(str == null)
            {
                break;
            }
            if (operator.indexOf(str) >= 0) {

                double num2 = stack.pop();
                double num1 = stack.pop();
                double tempresult = 0;
                switch (str.charAt(0)) {
                    case '+':
                        tempresult = num1 + num2;
                        break;
                    case '-':
                        tempresult = num1 - num2;
                        break;
                    case '*':
                        tempresult = num1 * num2;
                        break;
                    case '/':
                        if(num2 == 0)
                        {
                            throw new Exception("出错:除数为 0");
                        }
                        tempresult = num1 / num2;
                        break;
                    case '%':
                        tempresult = num1 % num2;
                        break;
                    case '^':
                        tempresult = Math.pow(num1, num2);
                        break;
                    default:
                        throw new Exception("运算符: " + str + " 未识别!");
                }
                stack.push(tempresult);
            } else {
                stack.push(Double.valueOf(str));
            }
        }
        return stack.pop();
    }
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        while(true)
        {
            String s = reader.nextLine();
            double result = 0;
            try {
                result = Calculator2(s);
            } catch (Exception e) {

                e.printStackTrace();
            }
            System.out.println(result);
        }
    }
}
