/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math_calculators;

import java.io.*;

/**
 *
 * @author abigail
 */
public class Linear_cgr_solver {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException
    {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int pairs = 0;
        int n1 = 0, n2 = 0, rem = 0;
        
        int args_length = args.length;
        
        System.out.println("------------------------------------------------------------");
        System.out.println("|                Linear Congruence Solver                  |");
        System.out.println("------------------------------------------------------------");
        
        System.out.println("""
                                               solve:    ax \u2261 c (mod m)
                             linear Diophantine form:    ax + my = c""");
        System.out.println("------------------------------------------------------------");
        
        if(args_length == 0)
        {
            System.out.print("  (I)     Enter variables:    a = ");

            try{
                n1 = Integer.parseInt(br.readLine());
            }catch(NumberFormatException e)
            {
                System.out.println(e);
            }

            System.out.print("                              m = ");
            try{
                n2 = Integer.parseInt(br.readLine());
            }catch(NumberFormatException e)
            {
                System.out.println(e);
            }

            System.out.print("                              c = ");
            try{
                rem = Integer.parseInt(br.readLine());
            }catch(NumberFormatException e)
            {
                System.out.println(e);
            }

            System.out.println("------------------------------------------------------------");
        }else
        {
            if(args_length < 3)
            {
                System.out.println("too few arguments");
                System.exit(-1);
            }
            
            n1 = Integer.parseInt(args[0]);
            n2 = Integer.parseInt(args[1]);
            rem = Integer.parseInt(args[2]);
        }
        
        int gcd = getGCD(n1, n2);
        System.out.println("  (II)             We get:    gcd("+n1+", "+n2+") = "+gcd);
        if (rem % gcd == 0) {
            System.out.println("                              and "+ gcd + " | "+ rem);
            System.out.println("                     Thus:    there is a solution! ");
        } else {
            System.out.println("                              and "+ rem + " is not a multiple of "+ gcd);
            System.out.println("                     Thus:    there is no solution! ");
            System.exit(0);
        }
        System.out.println("------------------------------------------------------------");
         System.out.println("  (III)    Apply the Extended Euclidean Algorithm:");

        System.out.println("\n  s\tt\tr\tq");
        System.out.println("  --------------------------");
        int s1 = 1, s2 = 0, t1 = 0, t2 = 1, r1 = n1, r2 = n2, q1 = 0, q2 = 0;
        int tempq, tempr,temps,tempt;

        do {
            System.out.println("  "+ s1 + "\t" + t1 + "\t" + r1 + "\t" + q1);
            tempq = q2;
            q2 = r1 / r2;
            q1 = tempq;
            tempr = r2;
            r2 = r1 % r2;
            r1 = tempr;
            temps = s2;
            s2 = s1 - (s2 * q2);
            s1 = temps;
            tempt = t2;
            t2 = t1 - (t2 * q2);
            t1 = tempt;
        } while (r2 != 0);

        System.out.println("  "+ s1 + "\t" + t1 + "\t" + r1 + "\t" + q1 + "  <- We look at this row");
        System.out.println("  "+ s2 + "\t" + t2 + "\t" + r2 + "\t" + q2);
        
        int times = 0;
        while (times<pairs)
        {
            System.out.printf("\n%s%d%s%d \t%5s%d%s%d", 
                    "X", times, " = ", ((s1*(rem/r1)) + (times*s2)), 
                    "Y", times, " = ", ((t1*(rem/r1)) + (times*t2)));
            times++;
        }
        times--;
        System.out.println("------------------------------------------------------------");
        int result1 = ((n1*s1) + n2*t1);
        int mult = rem / result1;
        int sp = s1 * mult;
        int tp = t1 * mult;
        int resultp = ((n1*sp) + n2*tp);
        int sp_factor = getFactor(sp, n2);
        int factored_rem = (sp + sp_factor * n2);
        
        System.out.println("  (IV)         Now we have:    "
                + "" +n1+ "(" +s1+ ") + " +n2+ "(" +t1+ ") = " + result1);
        System.out.println("        Multiply by "+mult+", get:    "
                + "" +n1+ "(" +sp+ ") + " +n2+ "(" +tp+ ") = " + resultp);
        System.out.println("      A particular solution:   x = " + sp);
        System.out.println("                       Then:   " + sp + " ≡ " 
                + sp +" + "+ sp_factor + "(" + n2 + ")");
        System.out.println("                              " + " ≡ " 
                + factored_rem + " (mod "+n2+")");
        System.out.println("------------------------------------------------------------");
        System.out.println("  (V)  Ta-dah!! ");
        System.out.println("       The set of solution to the linear congruence is given"
                + "\n       by all integers s such that:    ");
        System.out.println("                               x ≡ " + factored_rem + " (mod "+n2+")");
    }
    
    public static int getGCD(int n1, int n2)
    {
        int temp;
        int gcd = 0;
        
        while(n2 != 0)
        {
            temp = n2;
            n2 = n1 % n2;
            n1 = temp;
        }
        
        gcd = n1;
        
        return gcd;
    }
    
    public static int getFactor(int n1, int n2){
        // n1>0  ->  approch 0 from +, sub n2, add -n2
        // n1<0  ->  approch 0 from -, add n2, add n2
        int n1temp = Math.abs(n1);
        int n2temp = Math.abs(n2);
        
        int factor = 0;
        while (n1temp >= n2temp) {
            n1temp -= n2temp;
            factor++;
        }
        
        if (Math.abs(n1temp-n2temp) < n1temp) factor++;
        
        if (n1 * n2 > 0) factor = -factor;
        
        return factor;
    }
}
