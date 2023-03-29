package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private HashMap<Long, Product> products = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    @Autowired
    CompositionService compositionService;
    @Autowired
    ShopService shopService;

    public ProductService(){
        //createProduct(new Product("White Alien Hoodie", "This crisp white hoodie is a wardrobe staple that can be dressed up or down. Made from soft and breathable fabric, it will keep you comfortable all day long. The clean and classic design features a drawstring hood, kangaroo pocket, and ribbed cuffs and hem for a secure fit. The versatile white color allows you to pair it with almost any outfit, from jeans and sneakers to skirts and boots. Perfect for layering, this hoodie is a must-have for any fashion-conscious individual.", 80.00f, null, "../assets/img/alienSudaderaSin.png", (long)1));
        //createProduct(new Product("Purple Alien Hoodie", "This cozy purple hoodie is made from soft, high-quality material that will keep you warm and comfortable. The vibrant shade of purple adds a pop of color to your wardrobe, while the relaxed fit and kangaroo pocket make it perfect for everyday wear. The drawstring hood allows for adjustable coverage, and the ribbed cuffs and hem provide a secure and snug fit. Whether you're lounging at home or out and about, this stylish and comfortable hoodie is sure to become a go-to in your wardrobe.", 80.00f, null, "../assets/img/alienSudadera2Sin.png", (long)2));
        //createProduct(new Product("Black Alien Hoodie", "This sleek black hoodie is a versatile addition to your wardrobe. Made from a soft and cozy material, it features a comfortable and relaxed fit that's perfect for everyday wear. The black color gives it a timeless and sophisticated look that can be dressed up or down. The hoodie also comes with a drawstring that allows you to adjust the fit for optimal coverage. The kangaroo pocket provides a convenient place to store your phone or other small essentials. Whether you're running errands or going out with friends, this black hoodie is sure to keep you stylish and comfortable.", 80.00f, null, "../assets/img/alienSudadera3Sin.png", (long)3));
    }

    public Product createProduct(Product product, long shopId){
        product.setId(lastId.incrementAndGet());
        product.setShopId(shopId);
        shopService.getShop(product.getShopId()).getProducts().add(product);
        products.put(lastId.get(), product);
        return product;
    }
    public Collection<Product> getProducts(){
        return this.products.values().stream().toList();
    }

    public Product getProduct(long id) {
        if (this.products.containsKey(id)) {
            return this.products.get(id);
        }
        return null;
    }

    public Product deleteProduct(long id){
        Composition composition = compositionService.deleteComposition(id);
        this.shopService.getShop(this.products.get(id).getShopId()).getProducts().remove(this.products.get(id));
        return products.remove(id);
    }

    public Product modifyProduct(long id, long idShop, Product modifiedProduct){
        Product product = this.getProduct(id);
        Product shopProduct = null;
        for (Product productAux : this.shopService.getShop(idShop).getProducts())
            if (productAux .getId() == id)
                shopProduct = productAux;

        if (shopProduct == null)
            return null;
        shopProduct.setName(modifiedProduct.getName());
        shopProduct.setPrize(modifiedProduct.getPrize());
        shopProduct.setDescription(modifiedProduct.getDescription());
        shopProduct.setImage(modifiedProduct.getImage());

        product.setName(modifiedProduct.getName());
        product.setPrize(modifiedProduct.getPrize());
        product.setDescription(modifiedProduct.getDescription());
        product.setImage(modifiedProduct.getImage());
        return product;
    }
}
