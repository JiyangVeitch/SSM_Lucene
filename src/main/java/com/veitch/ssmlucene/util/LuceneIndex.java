package com.veitch.ssmlucene.util;

import com.veitch.ssmlucene.pojo.Lucene;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class LuceneIndex {
    private Directory dir = null;

    private IndexWriter getWriter() throws Exception {
        dir = FSDirectory.open(Paths.get("D:\\lucene"));
        IKAnalyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        return new IndexWriter(dir, config);
    }

    public void addIndex(Lucene lucene) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new TextField("id", String.valueOf(lucene.getId()), Field.Store.YES));
        doc.add(new TextField("name", lucene.getName(), Field.Store.YES));
        doc.add(new TextField("price", lucene.getPrice(), Field.Store.YES));
        doc.add(new TextField("place", lucene.getPlace(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public void addListIndex(List<Lucene> luceneList) throws Exception {
        int total = luceneList.size();
        int count = 0;
        int per = 0;
        int oldPer = 0;
        for (Lucene lucene : luceneList) {
            addIndex(lucene);
            count++;
            per = count * 100 / total;
            if (per != oldPer) {
                oldPer = per;
                System.out.printf("索引中，总共要添加 %d 条记录，当前添加进度是： %d%% %n", total, per);
            }
        }
    }

    public void updateIndex(Lucene lucene) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new TextField("id", String.valueOf(lucene.getId()), Field.Store.YES));
        doc.add(new TextField("name", lucene.getName(), Field.Store.YES));
        doc.add(new TextField("price", lucene.getPrice(), Field.Store.YES));
        doc.add(new TextField("place", lucene.getPlace(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(lucene.getId())), doc);
        writer.close();
    }

    public void deleteIndex(String id) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", id));
        writer.forceMergeDeletes(); // 强制删除
        writer.commit();
        writer.close();
    }

    public List<Lucene> select(String keyword) throws Exception {
        /*
         * 注意的是查询索引的位置得是存放索引的位置，不然会找不到。
         */
        dir = FSDirectory.open(Paths.get("D:\\lucene"));
        IKAnalyzer analyzer = new IKAnalyzer();
        Query query = new QueryParser("name", analyzer).parse(keyword);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        int numberPerPage = 10;
        ScoreDoc[] hits = searcher.search(query, numberPerPage).scoreDocs;

        //关键字高亮显示
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));

        List<Lucene> selectList = new ArrayList<>();
        for (ScoreDoc scoreDoc : hits) {
            int docId = scoreDoc.doc;
            Document d = searcher.doc(docId);
            List<IndexableField> fields = d.getFields();
            Lucene lucene = new Lucene();
            //循环打印出单行搜索结果的不同字段中的值
            for (IndexableField f : fields) {

                if ("id".equals(f.name())) {
                    lucene.setId(Integer.valueOf(d.get(f.name())));
                }

                if ("name".equals(f.name())) {
                    TokenStream tokenStream = analyzer.tokenStream(f.name(), new StringReader(d.get(f.name())));
                    String fieldContent = highlighter.getBestFragment(tokenStream, d.get(f.name()));
                    lucene.setName(fieldContent);
                }

                if ("price".equals(f.name())) {
                    lucene.setPrice((d.get(f.name())));
                }

                if ("place".equals(f.name())) {
                    lucene.setPlace((d.get(f.name())));
                }

            }
            selectList.add(lucene);
        }
        reader.close();
        return selectList;
    }
}
