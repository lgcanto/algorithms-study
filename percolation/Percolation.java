import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final WeightedQuickUnionUF siteSystem;
  private final int n;
  private final int topVirtualSite;
  private final int bottomVirtualSite;
  private boolean [] openSites;
  private int numberOfOpenSites;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    topVirtualSite = n*n;
    bottomVirtualSite = n*n + 1;
    siteSystem = new WeightedQuickUnionUF(n*n + 2);
    openSites = new boolean[n*n];
    for (int i = 0; i < (n*n); i++) {
      openSites[i] = false;
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    int siteId = getSiteId(row, col);
    if (!openSites[siteId]) {
      openSites[siteId] = true;
      numberOfOpenSites++;
      uniteNearSites(siteId);
      unionVirtual(siteId);
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    int siteId = getSiteId(row, col);
    return openSites[siteId];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    int siteId = getSiteId(row, col);
    return isSiteFull(siteId);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return siteSystem.find(topVirtualSite) == siteSystem.find(bottomVirtualSite);
  }

  // test client
  public static void main(String[] args) {
    System.out.println("Yep, percolation works.");
  }

  // private methods
  private boolean isSiteFull(int siteId) {
    if (openSites[siteId]) {
      return siteSystem.find(siteId) == siteSystem.find(topVirtualSite);
    }
    return false;
  }

  private int getSiteId(int row, int col) {
    validateRowCol(row, col);
    row = row - 1;
    col = col - 1;
    return col + row*n;
  }

  private void uniteNearSites(int siteId) {
    int upperSite = siteId - n;
    int lowerSite = siteId + n;
    int leftSite = siteId - 1;
    int rightSite = siteId + 1;

    if(upperSite >= 0 && openSites[upperSite]) {
      siteSystem.union(siteId, upperSite);
    }

    if(lowerSite < openSites.length && openSites[lowerSite]) {
      siteSystem.union(siteId, lowerSite);
    }

    if(leftSite >= 0 && siteId % n != 0 && openSites[leftSite]) {
      siteSystem.union(siteId, leftSite);
    }

    if(rightSite < openSites.length && (siteId + 1) % n != 0 && openSites[rightSite]) {
      siteSystem.union(siteId, rightSite);
    }
  }

  private void unionVirtual(int siteId) {
    if (siteId < n) {
      siteSystem.union(siteId, topVirtualSite);
    }
    else if (siteId >= (n*n - n)) {
      siteSystem.union(siteId, bottomVirtualSite);
    }
  }

  private void validateRowCol(int row, int col) {
    if (row < 1 || row > n || col < 1 || col > n) {
      throw new IllegalArgumentException();
    }
  }
}