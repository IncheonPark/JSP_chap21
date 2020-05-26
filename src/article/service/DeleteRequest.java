package article.service;

import java.util.Map;

public class DeleteRequest {
	
	private String userId;
	private int articleNumber;
	
	public DeleteRequest (String userId, int articleNumber) {
		this.userId = userId;
		this.articleNumber = articleNumber;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getArticleNumber() {
		return articleNumber;
	}
	
	//delete기능은 validate 메서드 구현 안 헸음. article_no는 프라이머리 키라 null일 수가 없음.

		
	

}
