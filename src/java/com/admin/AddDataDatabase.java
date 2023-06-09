package com.admin;
import java.io.IOException;
import Controller.DriverManagerConnectionPool;
import Filter.Filter;
import Model.Listed;
import Model.Product;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "AddDataDatabase", urlPatterns = {"/AddDataDatabase"})
public class AddDataDatabase extends HttpServlet {    
    DriverManagerConnectionPool mg= new DriverManagerConnectionPool();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
        PreparedStatement ps;
        Connection cn= mg.getConnection();
        Filter filt = new Filter();
            String brand = request.getParameter("brand");           
            String name =  request.getParameter("name");
            String desc = request.getParameter("description");   
            Double p = Double.valueOf( request.getParameter("pricetag"));           
            int q = Integer.parseInt (request.getParameter("quantity"));
            Double pr_amouunt = Double.valueOf(request.getParameter("pr_amouunt"));
            String imgpath= request.getParameter("img");  
            desc = filt.Filter(desc);
            name =  filt.Filter(name);
            brand = filt.Filter(brand);
            imgpath = filt.Filter(imgpath);
            String insertSQL = "INSERT INTO " + "Prodotto"
		+ " (Name, description, brand, price, quantity,pr_amouunt, prod_image, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";	        
	    ps = cn.prepareStatement(insertSQL);
            try {
                ps.setString(1,name);
		ps.setString(2, desc);
		ps.setString(3, brand);
		ps.setDouble(4,p);	
		ps.setInt(5,q);	
		ps.setDouble(6,pr_amouunt);
		ps.setString(7,imgpath);
                ps.setBoolean(8, false);
		ps.executeUpdate();
                
                Listed lis = (Listed)request.getSession().getAttribute("listed");
                int laste = lis.getLastOne();
                lis.addProduct(new Product(laste+1,name,desc,brand,p,q,pr_amouunt,imgpath,false));
                request.getSession().setAttribute("listed", lis);
                response.sendRedirect("admin/ViewProduct.jsp");               
            }catch(Exception e){
               response.sendError(1, "Error during connection closing");
               response.addHeader("er", "Error during connection closing");
            }
	    finally{       
              cn.commit();
	      ps.close(); 
              cn.close();     
	 }	
        }catch(SQLException e){
               response.sendError(2,"Error during query insertion");
               response.addHeader("er", "Error during query insertion");
               request.setAttribute("errormsg", e.getMessage());
               response.getWriter().print(e.getMessage());
        }
            
    }    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
