package ch.kerbtier.webb.index;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Lucene {

  private Directory dir = null;
  private IndexReader reader = null;
  private IndexSearcher searcher = null;
  private IndexWriter writer = null;

  public Lucene(String path) {
    try {
      // remove old index, has problems when locked otherwise
      FileUtils.deleteDirectory(new File(path));

      dir = FSDirectory.open(Paths.get(path));

      Analyzer analyzer = new StandardAnalyzer();
      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

      iwc.setOpenMode(OpenMode.CREATE);

      writer = new IndexWriter(dir, iwc);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void destroy() {
    try {
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private synchronized IndexSearcher getSearcher() {
    if (searcher == null) {
      try {
        reader = DirectoryReader.open(dir);
        searcher = new IndexSearcher(reader);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return searcher;
  }

  private synchronized IndexReader getIndexReader() {
    getSearcher();
    return reader;
  }

  public int hits(String defaultField, String query) {
    try {
      TotalHitCountCollector collector = new TotalHitCountCollector();
      getSearcher().search(getQuery(defaultField, query), collector);
      return collector.getTotalHits();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public List<Integer> search(String defaultField, String query, int from, int docs, String... order) {
    List<Integer> ids = new ArrayList<Integer>();
    try {

      TopDocs td = null;
      if (order.length > 0) {

        SortField[] sfs = new SortField[order.length];
        for (int cnt = 0; cnt < order.length; cnt++) {
          sfs[cnt] = new SortField(order[cnt], SortField.Type.STRING);
        }
        Sort sort = new Sort(sfs);
        td = getSearcher().search(getQuery(defaultField, query), from + docs, sort);
      } else {
        td = getSearcher().search(getQuery(defaultField, query), from + docs);
      }

      for (int cnt = from; cnt < td.scoreDocs.length; cnt++) {
        Document doc = getIndexReader().document(td.scoreDocs[cnt].doc);
        ids.add(Integer.parseInt(doc.get("id")));
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return ids;
  }

  private Query getQuery(String query) throws ParseException {
    return getQuery(null, query);
  }

  private Query getQuery(String defaultField, String query) throws ParseException {
    if (query.trim().length() > 0) {
      Analyzer analyzer = new StandardAnalyzer();
      QueryParser parser = new QueryParser(defaultField, analyzer);
      return parser.parse(query);
    }
    return new MatchAllDocsQuery();
  }

  public void delete(Object obj) {
    String query = DocumentCreator.createQuery(obj);
    try {
      writer.deleteDocuments(getQuery(query));
      writer.commit();
      searcher = null;
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void update(Object obj) {
    Document doc = DocumentCreator.create(obj);
    String query = DocumentCreator.createQuery(obj);
    try {
      writer.deleteDocuments(getQuery(query));
      writer.addDocument(doc);
      writer.commit();
      searcher = null;
    } catch (IOException | ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void updateAll(Collection<? extends Object> list) {
    try {
      for (Object obj : list) {
        Document doc = DocumentCreator.create(obj);
        writer.addDocument(doc);
      }
      writer.commit();
      searcher = null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
