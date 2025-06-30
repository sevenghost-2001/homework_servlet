package crm09.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//add vào filter của hệ thống			//urlPatterns: filter sẽ kích hoạt khi người dùng gọi đường dẫn
@WebFilter(filterName = "authentication",urlPatterns = {"/user-add","/user"})
//kế thừa HttpFilter để biến class trở thành 1 filter
public class AuthenticationFilter extends HttpFilter{
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		//code chạy ở đây
		Cookie[] cookie = req.getCookies();
		String role = "";
		
		for (Cookie cookie2 : cookie) {
			String name = cookie2.getName();
			String value = cookie2.getValue();
			if(name.equals("sRole")) {
				role = value;
				System.out.println(role);
			}
		}	
		if(role.equals("ROLE_ADMIN")) {
			//Cho phép đi tiếp
			chain.doFilter(req, res);
		}else {
			res.sendRedirect("login");
		}
		
	}
}
