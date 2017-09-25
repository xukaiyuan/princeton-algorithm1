import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats { 
 private double[] threshold;

 //n*n,trials times
 public PercolationStats(int n, int trials) {
  int row;
  int col;
  double count;
  if (n <= 0 || trials <= 0) throw new IllegalArgumentException("invalid input");
  threshold = new double[trials];
  for (int i = 0; i < trials; i++){
   count = 0;
   Percolation p = new Percolation(n);
   while (p.percolates() == false) {
    row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
    if (p.isOpen(row, col)) continue;
    p.open(row, col);
    count += 1;
   }
   threshold[i] = count / (n * n);
  }
 } 

 //mean of threshold
 public double mean(){
  return StdStats.mean(threshold);
 }

 //std of threshold
 public double stddev(){
  return StdStats.stddev(threshold);
 }

 //low endpoint of 95% confidence interval
 public double confidenceLo(){
  return mean() - 1.96 * stddev() / Math.sqrt(threshold.length);
 }

 //high endpoint of 95% confidence interval
 public double confidenceHi(){
  return mean() + 1.96 * stddev() / Math.sqrt(threshold.length);
 }

 public static void main(String[] args){
  int N = Integer.parseInt(args[0]), T = Integer.parseInt(args[1]);
  PercolationStats stats = new PercolationStats(N, T);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() 
                           + ", " + stats.confidenceHi());
 }
}