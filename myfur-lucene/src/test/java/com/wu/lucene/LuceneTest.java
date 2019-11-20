package com.wu.lucene;

import com.wu.lucene.ik.IKAnalyzer4Lucene7;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class LuceneTest {


    //创建索引
    @Test
    public void testCreate() throws Exception {
//        //1 创建文档对象
//        Document document = new Document();
//        //创建并添加字段信息。参数：字段的名称、字段的值、是否存储，这里选Store.YES代表存储到文档列表。Store.NO代表不存储
//        document.add(new StringField("id","1", Field.Store.YES));
//        //这里我们title字段需要用TextField,即创建索引又会被分词。StringField会创建索引，但是不会被分词
//        document.add(new TextField("title","谷歌地图之父跳槽facebook", Field.Store.YES));
//
//        //2 索引目录类，指定索引在硬盘中的位置
//        Directory directory = FSDirectory.open(new File("d:\\indexDir").toPath());
//        //3 创建分词器对象
////        Analyzer analyzer = new StandardAnalyzer();
//        //引入IK分词器
//        Analyzer analyzer = new IKAnalyzer();
//        //4 索引写出工具的配置对象
//        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
//        //5 创建索引的写出工具类。参数：索引的目录和配置信息
//        IndexWriter indexWriter = new IndexWriter(directory, conf);
//
//        //6 把文档交给IndexWriter
//        indexWriter.addDocument(document);
//        //7 提交
//        indexWriter.commit();
//        //8 关闭
//        indexWriter.close();
    }


    public static void main(String[] args) throws IOException {
        // 创建使用的分词器
        Analyzer analyzer = new IKAnalyzer4Lucene7(true);
        // 索引配置对象
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 设置索引库的打开模式：新建、追加、新建或追加
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        // 索引存放目录
        // 存放到文件系统中
        Directory directory = FSDirectory
                .open((new File("d:\\indexDir")).toPath());

        // 存放到内存中
        // Directory directory = new RAMDirectory();

        // 创建索引写对象
        IndexWriter writer = new IndexWriter(directory, config);

        // 创建document
        Document doc = new Document();
        // 往document中添加 商品id字段
        doc.add(new StoredField("prodId", "p0001"));

        // 往document中添加 商品名称字段
        String name = "ThinkPad X1 Carbon 20KH0009CD/25CD 超极本轻薄笔记本电脑联想";
        doc.add(new TextField("name", name, Field.Store.YES));

        // 将文档添加到索引
        writer.addDocument(doc);

        // .....

        // 刷新
        writer.flush();

        // 提交
        writer.commit();

        // 关闭 会提交
        writer.close();
        directory.close();
    }
}
