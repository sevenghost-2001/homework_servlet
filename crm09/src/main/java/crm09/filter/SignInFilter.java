package crm09.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "signInFilter", urlPatterns = {"/user-add","/user"})
public class SignInFilter extends HttpFilter{
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie2 : cookies) {
			String name = cookie2.getName();
			String value = cookie2.getValue();
			if(name.equals("sUserName")) {
				System.out.println("Bạn vượt màng lọc Sign In");
				chain.doFilter(req, res);
				return;
			}
		}
		res.sendRedirect("login");
	}
}
