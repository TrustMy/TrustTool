package com.trust.demo.trustalgorithm;

/**
 * Created by Trust on 2017/10/31.
 */

public class Algorithm {
    public static void main(String[] args) {
        boxProblem();
    }



    //----------------------背包问题  贪婪算法-------------------------------------------------------------------------
    private static void boxProblem() {
        double wi[] = {35,30,60,50,40,10,25};//重量
        int pi[] = {10,40,30,50,35,40,30};//价值
        double si[] = new double[7];//价值比
        for (int i = 0; i < wi.length; i++) {
            si[i] = pi[i]/wi[i];
        }
        logArray(si);
        /*
        logArray(wi);
        logArray(pi);
        */
        int C = 150;
        double weight = 0;
        int p = 0;

        int num = 0;
        int number = 1;
        while (weight<C)
        {
            for (int i = 0; i < si.length; i++) {
                if(si[i] > si[num]){
                    num = i;
                }
            }
            System.out.println("num:"+num);
            System.out.println("weight:"+weight);

            if(weight<C){
                System.out.println("wi[num]:"+wi[num]);
                System.out.println("si[num]:"+si[num]);
                System.out.println("pi[num]:"+pi[num]);
                if((weight+wi[num]) <= C){
                    weight=weight+wi[num];
                    p= p+pi[num];
                    pi[num] = 0;
                    wi[num] = 0;
                    si[num] = -1;
                }else{
                    number++;
                    if (number==7) {
                        logArray(pi);
                        logArray(wi);
                        logArray(si);
                        break;
                    }
                    si[num] = -1;

                }
            }else{
            }

            System.out.println("weight:"+weight);
            System.out.println("p:"+p);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int[] bubbleSort(int[] numbers){
        int temp = 0;
        int size = numbers.length;
        for(int i = 0 ; i < size-1; i ++)
        {
            for(int j = 0 ;j < size-1-i ; j++)
            {
                if(numbers[j] > numbers[j+1])  //交换两数位置
                {
                    temp = numbers[j];
                    numbers[j] = numbers[j+1];
                    numbers[j+1] = temp;
                }
            }
        }
        return numbers;
    }


    public  static void logArray(int [] numbers){
        System.out.println("------------");
        for (int number : numbers) {
            System.out.println(number+"");
        }
        System.out.println("------------");
    }

    public  static void logArray(double [] numbers){
        System.out.println("------------");
        for (double number : numbers) {
            System.out.println(number+"");
        }
        System.out.println("------------");
    }

    public class Box{
        double weight;
        int price;
        int status;//0未选中 1以选中 2 已经不可选
        double wp;//价值比
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------













}
