package article.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp";
	
	private DeleteArticleService deleteService = new DeleteArticleService();
	private ReadArticleService readService = new ReadArticleService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
		
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			
			ArticleData articleData = readService.getArticle(no, false);
			User authUser = (User) req.getSession().getAttribute("authUser");
			if (!canDelete(authUser, articleData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			DeleteRequest delReq = new DeleteRequest(authUser.getId(), no);
			req.setAttribute("delReq", delReq);
			
			return FORM_VIEW;
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
	
	private boolean canDelete(User authUser, ArticleData articleData) {
		String writerId = articleData.getArticle().getWriter().getId();
		return authUser.getId().equals(writerId);
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User authUser = (User) req.getSession().getAttribute("authUser");
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		DeleteRequest delReq = new DeleteRequest(authUser.getId(), no);
		req.setAttribute("delReq", delReq);
		
		//delete기능은 validate 메서드 구현 안 헸음. article_no는 프라이머리 키라 null일 수가 없음.
		try {
			deleteService.delete(delReq);
			return "/WEB-INF/view/deleteSuccess.jsp";
		} catch (ArticleNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		
		
	}

}
