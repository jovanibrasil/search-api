import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class Main {

	public static void indexDocument() {

		try {

			String urlString = "http://localhost:8983/solr/blog";
			SolrClient solr = new HttpSolrClient.Builder(urlString).build();

			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", "41");
			document.addField("title", "teste de batata");
			document.addField("summary", "blog");
			document.addField("body", "teste de batata");
			UpdateResponse response = solr.add(document);

			// Remember to commit your changes!

			solr.commit();

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void deleteDocument(String identification) {
		
		try {

			String urlString = "http://localhost:8983/solr/blog";
			SolrClient solr = new HttpSolrClient.Builder(urlString).build();

			solr.deleteById(identification);
			solr.commit();
	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		SolrQuery query = new SolrQuery();
//		query.set("q", "id:123456");
//		QueryResponse response = solr.query(query);
//		SolrDocumentList docList = response.getResults();
//		assertEquals(docList.getNumFound(), 0);
		
	}

	public static void findDocuments() {
		// Submmit the query
		
		try {

			String urlString = "http://localhost:8983/solr/blog";
			SolrClient solr = new HttpSolrClient.Builder(urlString).build();

			SolrQuery query = new SolrQuery();
			String queryString = "body:escrever";
			query.setQuery(queryString);
			//query.set("q", "title:post");
			//query.setQuery("post");
			// query.addFilterQuery("cat:electronics","store:amazon.com");
			//query.set("defType", "edismax");
			query.set("fl", "category,title,price");
			query.setFields("id", "summary", "title");
//					query.set("q", "category:books");

			QueryResponse response = solr.query(query);

			// Retrieve the list of documents
			SolrDocumentList list = response.getResults();
			System.out.println(list.size() + " documents retrivied");
			for (SolrDocument solrDocument : list) {
				System.out.println(solrDocument);
			}
			System.out.println("End.");

		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		//indexDocument();
		
		findDocuments();
		
	}

}
