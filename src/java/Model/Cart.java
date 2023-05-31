package Model;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Product{
    private List<Product> products;	
    private String email_cliente;

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }
    public Cart(String email_cliente) {
	products = new ArrayList<>();
        this.email_cliente = email_cliente;
    }
    public Product fetchById(int id){
         return products.get(id);
    }
    public void addProduct(Product product) {
	products.add(product);
    }	
    public void deleteProduct(Product product) {
	for(Product prod : products) {
            if(prod.getId() == product.getId()) {
                products.remove(prod);
                break;
            }
        }
    }
    public List<Product> getProducts() {
	return  products;
    }
}
