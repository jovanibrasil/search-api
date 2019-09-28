package com.search.services;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.search.models.SummaryDTO;


public class SearchService {

	private String URL_STRING = "http://solr-master:8983/solr/blog";
	
//	public Post indexDocument(Post post) {
//
//		try {
//			SolrClient solr = new HttpSolrClient.Builder(URL_STRING).build();
//
//			SolrInputDocument document = new SolrInputDocument();
//			document.addField("id", post.getPostId());
//			document.addField("title", post.getTitle());
//			document.addField("summary", post.getSummary());
//			document.addField("body", post.getBody());
//			UpdateResponse response = solr.add(document);
//			// Commit the changes.
//			solr.commit();
//			return post;
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public void deleteDocument(String identification) {
		
		try {

			SolrClient solr = new HttpSolrClient.Builder(URL_STRING).build();

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

	public List<SummaryDTO> searchDocumentsByTerm(String queryTerm) {
		List<SummaryDTO> summaryList = new ArrayList<>();		
		try {

			SolrClient solr = new HttpSolrClient.Builder(URL_STRING).build();
			
			SolrQuery query = new SolrQuery();
			String queryString = "summary:" + queryTerm;
			query.set("fl", "id, summary, title, creationDate, tags, userName");
			query.set("q", queryTerm);
			
			//query.addFilterQuery("cat:electronics","store:amazon.com");
			//query.set("defType", "edismax");
			
			// Submit the query
			QueryResponse response = solr.query(query);

			// Retrieve the list of documents
			SolrDocumentList list = response.getResults();
			System.out.println(list.size() + " documents retrivied");
			for (SolrDocument solrDocument : list) {
				summaryList.add(convertSolrDocumentToSummary(solrDocument));
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return summaryList;
	}

	private SummaryDTO convertSolrDocumentToSummary(SolrDocument solrDocument) {
		try {
			List<String> tags = new ArrayList<>();
			solrDocument.getFieldValues("tags").forEach(v -> { tags.add(v.toString()); });;
			return new SummaryDTO((Long)solrDocument.get("id"),
					solrDocument.get("title").toString(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(solrDocument.get("creationDate").toString()),
					null, solrDocument.get("summary").toString(), solrDocument.get("userName").toString(), tags); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	
}
