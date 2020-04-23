import java.lang.Math;

public class PercolationStats {

  private int trials;
  private Percolation percolationSystem;
  private double mean;
  private double stddev;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials){
    validateInputs(n, trials);
    percolationSystem = new Percolation(n);
  }

  // sample mean of percolation threshold
  public double mean(){
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev(){
    return stddev;
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo(){
    return mean - (1.96 * stddev)/Math.sqrt(trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi(){
    return mean + (1.96 * stddev)/Math.sqrt(trials);
  }

  // test client (see below)
  public static void main(String[] args){

  }


  // private methods
  private void validateInputs(int n, int trials) {
    if(n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
  }

}