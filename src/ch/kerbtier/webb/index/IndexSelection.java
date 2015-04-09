package ch.kerbtier.webb.index;

import java.util.ArrayList;
import java.util.List;

public class IndexSelection {
  private String query = "";
  private int page;
  private int pageSize = 20;
  private List<String> order = new ArrayList<String>();

  public void addOrder(String name) {
    order.add(name);
  }
  
  public List<Integer> list(Lucene index) {
    return index.search("content", query, page * pageSize, pageSize, order.toArray(new String[order.size()]));
  }

  public int maxPage(Lucene index) {
    int mp = index.hits("content", query);

    if (mp % pageSize > 0) {
      mp = mp / pageSize + 1;
    } else {
      mp = mp / pageSize;
    }

    return mp;
  }

  public List<Page> pages(Lucene index) {
    int min = page - 10;
    int max = page + 10;

    if (min < 0) {
      min = 0;
    }

    int maxPage = maxPage(index);

    if (max > maxPage) {
      max = maxPage;
    }

    List<Page> ints = new ArrayList<Page>();

    for (int cnt = min; cnt < max; cnt++) {
      ints.add(new Page(cnt, cnt == page));
    }

    return ints;
  }

  public Page previous() {
    if(page > 0) {
      return new Page(page - 1, false);
    }
    return null;
  }

  public Page next(Lucene index) {
    if(page < maxPage(index) - 1) {
      return new Page(page + 1, false);
    }
    return null;
  }

  
  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }


  public class Page {
    private int number;
    private boolean active;
    
    public Page(int number, boolean active) {
      this.number = number;
      this.active = active;
    }

    public int getNumber() {
      return number;
    }

    public boolean isActive() {
      return active;
    }
  }


  public void setQuery(String query) {
    this.query = query;
  }
  
  public String getQuery() {
    return query;
  }

}
