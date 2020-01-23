package com.search.services;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.search.models.SummaryDTO;


public class SearchService {

	private static final Logger log = LoggerFactory.getLogger(SearchService.class);
	
	//private String URL_STRING = "http://127.0.0.1:8983/solr/blog";
	private String URL_STRING = "http://solr-master:8983/solr/blog";

	public List<SummaryDTO> searchDocumentsByTerm(String queryTerm) {
		log.info("Searching documents by term {}", queryTerm);
		List<SummaryDTO> summaryList = new ArrayList<>();		
		try {

			SolrClient solr = new HttpSolrClient.Builder(URL_STRING).build();
			
			SolrQuery query = new SolrQuery();
			query.set("fl", "id, summary, title, creationDate, tags, userName, bannerUrl");
			query.set("q", queryTerm);
			//query.addFilterQuery("cat:electronics","store:amazon.com");
			//query.set("defType", "edismax");
			// Submit the query
			QueryResponse response = solr.query(query);
			// Retrieve the list of documents
			SolrDocumentList list = response.getResults();
			log.info("Documents retrivied: {}", list.size());
			
			list.forEach(solrDoc -> {
				SummaryDTO summaryDTO = this.convertSolrDocumentToSummary(solrDoc);
				if(summaryDTO != null) {
					summaryList.add(summaryDTO);
				}else {
					log.info("Conversion error. "
							+ "The summary was not added to the summaries list.");
				}
			});
			
		} catch (SolrServerException e) {
			log.info("SolrServerException occurred when talking to Solr. {}", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.info("IOexception occurred. {}", e.getMessage());
		}
		return summaryList;
	}

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


	private SummaryDTO convertSolrDocumentToSummary(SolrDocument solrDocument) {
		log.info("Converting solr document to SummaryDTO ...");
		try {
			List<String> tags = new ArrayList<>();
			solrDocument.getFieldValues("tags").forEach(v -> { tags.add(v.toString()); });
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = format.format(solrDocument.get("creationDate"));
			SummaryDTO summaryDTO = new SummaryDTO((Long)solrDocument.get("id"),
					solrDocument.get("title").toString(), 
					// creation date
					date,
					// lastUpdateDate
					null, 
					solrDocument.get("summary").toString(), 
					solrDocument.get("userName").toString(),
					solrDocument.get("bannerUrl").toString(),
					tags);
			log.info("SummaryDTO: {}", summaryDTO);
			return summaryDTO;
		} catch (Exception e) {
			log.info("Error. {}", e.getMessage());
			return null;
		}
	}
	
}
