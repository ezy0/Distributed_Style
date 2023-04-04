package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private HashMap<Long, Product> products = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public ProductService(){
    }

    public Product createProduct(Product product, long shopId){
        product.setId(lastId.incrementAndGet());
        product.setShopId(shopId);

        return products.put(lastId.get(), product);
    }
    public Collection<Product> getProducts(){
        return this.products.values().stream().toList();
    }

    public Collection<Product> getProductsShop(long shopId){
        ArrayList<Product> listProducts = new ArrayList<>();
        for (HashMap.Entry<Long, Product> entry : products.entrySet()) {
            if (entry.getValue().getShopId().equals(shopId))
                listProducts.add(entry.getValue());
        }
        return listProducts;
    }

    public Product getProduct(long id) {
        if (this.products.containsKey(id)) {
            return this.products.get(id);
        }
        return null;
    }

    public Product deleteProduct(long id) {
        if (this.products.containsKey(id)) {
            return this.products.remove(id);
        }
        return null;
    }

    public Product modifyProduct(long id, long idShop, Product modifiedProduct){
        Product product = this.getProduct(id);
        if (product == null)
            return null;
        product.setName(modifiedProduct.getName());
        product.setPrize(modifiedProduct.getPrize());
        product.setDescription(modifiedProduct.getDescription());
        product.setImage(modifiedProduct.getImage());
        return product;
    }
}
