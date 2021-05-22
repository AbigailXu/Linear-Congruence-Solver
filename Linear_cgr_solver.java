/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package math_calculators;

import java.util.Scanner;

/**
 *
 * @author abigail
 */
public class Linear_cgr_solver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int pairs=0;
        
        System.out.println("------------------------------------------------------------");
        System.out.println("|                Linear Congruence Solver                  |");
        System.out.println("------------------------------------------------------------");
        
        System.out.println("                    solve:    ax ≡ c (mod m)\n  "
                         + "linear Diophantine form:    ax + my = c");
        System.out.println("------------------------------------------------------------");
        System.out.print("  (I)     Enter variables:    a = ");
        int n1 = input.nextInt();
        System.out.print("                              m = ");
        int n2 = input.nextInt();
        System.out.print("                              c = ");
        int rem = input.nextInt();
        System.out.println("------------------------------------------------------------");
        int gcd = getGCD(n1,n2);
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
        while (times<pairs){
            System.out.printf("\n%s%d%s%d \t%5s%d%s%d", 
                    "X", times, " = ", ((s1*(rem/r1)) + (times*s2)), 
                    "Y", times, " = ", ((t1*(rem/r1)) + (times*t2)));
            times++;
        }
        times--;
        System.out.println("------------------------------------------------------------");
        int result1 = ((n1*s1)+n2*t1);
        int mult = rem / result1;
        int sp = s1 * mult;
        int tp = t1 * mult;
        int resultp = ((n1*sp)+n2*tp);
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
    
    public static int getGCD (int n1, int n2){
        int div = 0;
        int nextDiv = div;
        while (true){
            nextDiv = div + 1;
            if ((n1%nextDiv == 0) && (n2%nextDiv == 0)) div=nextDiv;
            else break;
        }
        return div;
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
