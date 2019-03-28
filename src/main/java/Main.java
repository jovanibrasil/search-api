import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {

		SearchService searchService = new SearchService();
		Gson gson = new Gson();
		
//		post("/index", (req, res) -> {
//			res.type("application/json");
//			Post post = gson.fromJson(req.body(), Post.class);
//			return searchService.indexDocument(post);
//		}, gson ::toJson);
		
		get("/search", (req, res) -> {
			res.type("application/json");
			String queryTerm = req.queryParams("term");
			//List<Object> objects = new ArrayList<Object>();
			return searchService.searchDocumentsByTerm(queryTerm);
		}, gson ::toJson);
		
		// http://0.0.0.0:4567/search?term=java
		
	}

}
