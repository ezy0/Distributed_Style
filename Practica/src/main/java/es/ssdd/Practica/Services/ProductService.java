package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Product;

import es.ssdd.Practica.Repositories.CompositionRepository;
import es.ssdd.Practica.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductService(){
    }

    public Product createProduct1(Product product) {
        return this.productRepository.save(product);
    }

    public Product createProduct(Product product, long shopId){
        product.setShopId(shopId);
        this.productRepository.save(product);
        return product;
    }

    public Collection<Product> getProducts(){
        return this.productRepository.findAll();
    }

    /*public Collection<Product> getProductsShop(long shopId){
        ArrayList<Product> listProducts = new ArrayList<>();
        for (HashMap.Entry<Long, Product> entry : products.entrySet()) { //Search in the map for products whose shopId is correct
            if (entry.getValue().getShopId().equals(shopId))
                listProducts.add(entry.getValue());
        }
        return listProducts;
    }*/

    public Product getProduct(long id) {
        if (this.productRepository.existsById(id)) {
            return this.productRepository.findById(id).get();
        }
        return null;
    }

    public Product getProductByName(String name) {
        Optional<Product> product = this.productRepository.findByName(name);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Transactional
    public Product deleteProduct(long id) {
        if (this.productRepository.existsById(id)) {
            Product product = this.productRepository.findById(id).get();
            this.productRepository.deleteById(id);
            return product;
        }
        return null;
    }

    @Transactional
    public Product modifyProduct(long id, long idShop, Product modifiedProduct){
        if (this.productRepository.existsById(id)) {
            Product product = this.productRepository.findById(id).get();
            product.setName(modifiedProduct.getName());
            product.setPrize(modifiedProduct.getPrize());
            product.setDescription(modifiedProduct.getDescription());
            product.setImage(modifiedProduct.getImage());
            this.productRepository.save(product);
            return product;
        }
        return null;
    }
}