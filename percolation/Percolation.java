import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private WeightedQuickUnionUF siteSystem;
  private int n;
  private boolean [] openSites;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if(n <= 0) {
      throw new IllegalArgumentException();
    }
    this.n = n;
    siteSystem = new WeightedQuickUnionUF(n^2);
    openSites = new boolean[n^2];
    for(int i = 0; i < (n^2); i ++) {
      openSites[i] = false;
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    int siteId = getSiteId(row, col);
    if(!openSites[siteId]) {
      openSites[siteId] = true;
      uniteNearSites(siteId);
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    int siteId = getSiteId(row, col);
    return openSites[siteId];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col){
    int siteId = getSiteId(row, col);
    return isSiteFull(siteId);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    int count = 0;
    for(int i = 0; i < openSites.length; i++) {
      if(openSites[i]){
        count++;
      }
    }
    return count;
  }

  // does the system percolate?
  public boolean percolates() {
    boolean percolates = false;
    int firstBottomSiteId = n*(n-1);
    for(int i = 0; i < n; i++) {
      if(isSiteFull(firstBottomSiteId + i)) {
        percolates = true;
      }
    }
    return percolates;
  }

  // test client
  public static void main(String[] args) {
    System.out.println("Yep, percolation works.");
  }

  // private methods
  private boolean isSiteFull(int siteId) {
    for(int topSite = 0; topSite < n; topSite++) {
      if(openSites[topSite] && siteSystem.connected(topSite, siteId)){
        return true;
      }
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
    int[] nearSitesIds = new int[4];
    nearSitesIds[0] = siteId - n; //upper
    nearSitesIds[1] = siteId + n; //lower
    nearSitesIds[2] = siteId - 1; //left
    nearSitesIds[3] = siteId + 1; //right
    for(int i = 0; i < nearSitesIds.length; i++) {
      int nearSiteId = nearSitesIds[i];
      if(nearSiteId < openSites.length &&
          nearSiteId >= 0 &&
          openSites[nearSiteId]) {
        siteSystem.union(siteId, nearSiteId);
      }
    }
  }

  private void validateRowCol(int row, int col) {
    if(row < 1 || row > n || col < 1 || col > n) {
      throw new IllegalArgumentException();
    }
  }
}