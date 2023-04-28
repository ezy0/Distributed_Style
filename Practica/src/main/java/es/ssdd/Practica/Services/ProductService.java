package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Product;

import es.ssdd.Practica.Repositories.CompositionRepository;
import es.ssdd.Practica.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    public ProductService(){
    }

    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product saveProduct(Product product, long shopId){
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

    @Transactional
    public Product setComposition(Product product, long compositionId){
       product.setComposition(this.compositionRepository.findById(compositionId).get());
       return product;
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