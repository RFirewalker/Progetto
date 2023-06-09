package Controller;
import Model.Cart;
import Model.Listed;
import Model.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "CheckSession", urlPatterns = {"/CheckSession"})
//Servlet che si occupa di recuperare l'id di un prodotto dall'header
// e controlla se il carrello è inizializzato o meno e se l'utente ha effettuato il login o meno, in caso
// in cui l'utente non abbia effettuato l'accesso inizializza il carrello con un id temporaneo.
public class CheckSession extends HttpServlet {
    @Override        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
        Cart usercart;	
        if(request.getSession().getAttribute("usercart")==null){    
            usercart= new Cart("0");
        }
        /*Controllo se la sessione è nuova e modifico il valore nome del cookie in user altrimenti 
        Recupero quelli attivi al cui interno sarà contenuto il nome dell'utente una volta effettuato
        L'accesso
        */
        else{
           usercart = (Cart)request.getSession().getAttribute("usercart");        
        }
        try{
           String action = request.getParameter("action");	   
            int id = Integer.parseInt(request.getParameter("id"));
            Listed list = (Listed)request.getSession().getAttribute("listed");
            Product selected = (list.fetchByPrId(id));
            if(action.matches("addC")){       
                if(selected.getQuantity()!=0 && !selected.isDeleted()){
                    if(usercart.checkIfPresent(selected)){ 
                        usercart.fetchById(id).setAddedToCart(1);
                    }                                           
                    else usercart.addProduct(selected);
                    request.getSession().setAttribute("usercart", usercart);
                    request.getSession().setAttribute("msg","the element was added succesfully to the cart");
                }
                else {
                    request.getSession().setAttribute("msg", "The product selected is currently unavailable");            
                }         
            }   
            if(action.matches("delete")){
                if(!usercart.checkIfPresent(selected)) request.getSession().setAttribute("msg","the selected item isn't int the cart");
                else{ 
                    usercart.deleteProduct(selected);
                    request.getSession().setAttribute("msg","the selected item was removed from the cart");
                }
            }     
            response.sendRedirect("Shop");
        }
        catch(IOException | NumberFormatException e){
            int id = (Integer.parseInt(request.getParameter("id")))-1;
            request.setAttribute("errormsg", e.getMessage()+ "id=: "+id);
            response.sendRedirect("error/error.jsp");               
            }				
        }       
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
            doPost(request, response);
	}	
     private static final long serialVersionUID = 1L;
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}