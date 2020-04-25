import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats
{

  private static final double CONFIDENCE_95 = 1.96;
  private final int trials;
  private final double mean;
  private final double stddev;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials)
  {
    this.trials = trials;
    validateInputs(n, trials);
    double[] thresholds = new double[trials];
    for (int trial = 0; trial < trials; trial++)
    {
      Percolation percolationSystem = new Percolation(n);
      double totalSites = n * n;
      while (!percolationSystem.percolates())
      {
        int randomRow = StdRandom.uniform(n) + 1;
        int randomCol = StdRandom.uniform(n) + 1;
        percolationSystem.open(randomRow, randomCol);
      }
      thresholds[trial] = percolationSystem.numberOfOpenSites() / totalSites;
    }
    mean = StdStats.mean(thresholds);
    stddev = StdStats.stddev(thresholds);
  }

  // sample mean of percolation threshold
  public double mean()
  {
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev()
  {
    return stddev;
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo()
  {
    return mean - (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi()
  {
    return mean + (CONFIDENCE_95 * stddev) / Math.sqrt(trials);
  }

  // test client (see below)
  public static void main(String[] args)
  {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats percolationStats = new PercolationStats(n, t);
    double mean = percolationStats.mean();
    double stddev = percolationStats.stddev();
    double confidenceLo = percolationStats.confidenceLo();
    double confidenceHi = percolationStats.confidenceHi();
    System.out.println("mean                    = " + mean);
    System.out.println("stddev                  = " + stddev);
    System.out.println("95% confidence interval = [" + confidenceLo + ", " + confidenceHi + "]");
  }


  // private methods
  private void validateInputs(int n, int t)
  {
    if (n <= 0 || t <= 0)
    {
      throw new IllegalArgumentException();
    }
  }

}