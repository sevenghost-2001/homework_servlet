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
		Cookie[] cookies = req.getCookies();
        boolean signedIn = false;
        String role = "";
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sUserName")) {
                    signedIn = true;
                    System.out.println("Bạn vượt màn lọc log in thành công");
                }
                if (cookie.getName().equals("sRole")) {
                    role = cookie.getValue();
                }
            }
        }
        if (signedIn) {
            if (role.equals("ROLE_ADMIN")) {
                System.out.println("Bạn là admin và có quyền truy cập của admin");
                chain.doFilter(req, res);
            } else {
            	System.out.println("Bạn không có quyền truy cập của Admin");
            	res.sendRedirect("login");
            }
        } else {
            res.sendRedirect("login");
        }
		
		
	}
}
