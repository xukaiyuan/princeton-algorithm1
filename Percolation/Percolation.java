import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
 
 private WeightedQuickUnionUF grid;
 private int[][] originalGrid;
 private int size;
 private int count = 0;
 
 private int xyTo1D( int i, int j){return (size*(i-1)+j-1);}
//xy to 1 dimension index
 
 public Percolation(int n)      
 {
  if (n <= 0 ) throw new IndexOutOfBoundsException("illegal input");
  
  originalGrid = new int[n][n];
//totally n^2 points
  grid = new WeightedQuickUnionUF(n*n + 2);
//top index n^2, bottom index n^2+1
  size = n;
 }          
// create n-by-n grid, with all sites blocked
 
 
 public    void open(int row, int col)   
 {
  validate(row, col);
  if(isOpen(row, col)) return;
  else
  {
   count++;   
   originalGrid[row-1][col-1] = 1;
   //check neighbor is open or not, and merge
   if(row == 1)
   {
    grid.union(xyTo1D(row, col), size*size);
//merge top 
    
   }
   if(row == size)
   {
    grid.union(xyTo1D(row, col), size*size + 1);
//merge bottom
   
   }
      
      //merge 4 neighbours
       if((row < size) && isOpen(row + 1, col))  {grid.union(xyTo1D(row, col), xyTo1D(row + 1, col));}
       if((row > 1) && isOpen(row - 1, col))  {grid.union(xyTo1D(row, col), xyTo1D(row - 1, col));}
       
       if((col > 1) && (isOpen(row, col - 1)))  {grid.union(xyTo1D(row, col), xyTo1D(row, col - 1));}
       
       if((col < size)   && (isOpen(row, col + 1)))  {grid.union(xyTo1D(row, col), xyTo1D(row, col + 1));}
       
   
  }
 } 
// open site (row, col) if it is not open already
 public boolean isOpen(int row, int col)  
 {
     validate(row, col);
     return (originalGrid[row-1][col-1] == 1);
 }
// is site (row, col) open?
 public boolean isFull(int row, int col)  
 {
     validate(row, col);
     return (grid.connected(xyTo1D(row, col),size*size));
 }
// is site (row, col) full?
 
 public     int numberOfOpenSites()      
 {
     return count;
 }
 // number of open sites
 public boolean percolates()             
 {
     return (grid.connected(size*size,size*size + 1));
 } 
// does the system percolate?
 
private void validate(int row, int col) {
  if (row < 1 || row > size) 
             throw new IndexOutOfBoundsException("row index i out of bounds");
     if (col < 1 || col > size)
            throw new IndexOutOfBoundsException("column index j out of bounds");
 }
 
 public static void main(String[] args)   
 {
     In in = new In(args[0]);
//input
     int num = in.readInt();
//input n
     
     Percolation p = new Percolation(num);
//initialize
     boolean judge = false;
 
     while(!in.isEmpty())
     {
         int i = in.readInt();
//input row
         int j = in.readInt();
//input collum
        
         p.open(i,j);
         judge = p.percolates();
         if(judge) break;
     }
     if (judge) StdOut.println("percolates");
        else StdOut.println("does not percolate");
 }
// test client (optional)
}
