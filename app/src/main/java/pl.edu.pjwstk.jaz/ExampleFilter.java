package pl.edu.pjwstk.jaz;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExampleFilter extends HttpFilter {
    private final UserSession userSession;

    public ExampleFilter(UserSession userSession){
        this.userSession= userSession;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        if(isUserLogged() || isSiteAllowed(request)){
            chain.doFilter (request,response);
        }else{
            response.setStatus (HttpStatus.UNAUTHORIZED.value() ); //401
        }
    }

   private boolean isSiteAllowed(HttpServletRequest request){
      return request.getServletPath ().equals ("/login") || request.getServletPath ().equals ("/register") || request.getServletPath ().equals ("/average");
   }

    private boolean isUserLogged () {
        return userSession.isLoggedIn ();
    }

}
