package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Supplier;
import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CompositionService compositionService;
    @Autowired
    private SupplierService supplierService;

    @PostConstruct
    public void init(){

        // SHOPS
        Shop footLocker = new Shop("Foot Locker", "/assets/img/footlocker.png", "C. de la Palma, 69, 28015 Madrid");
        Shop nudeProject = new Shop("Nude Project", "/assets/img/NUDE_PROJECT_COCOA_2.png", "C/ Gran Vía, 18, 28013 Madrid");
        Shop martinValen = new Shop("Martin Valen", "/assets/img/67237831_101270447883552_3670809205297643520_n.jpg", "C. de Fuente Chica, 28034 Madrid");

        // SUPPLIER
        Supplier supplier = new Supplier("Global Suppliers", "Supplying shops around the world since 1995", new ArrayList<>(this.getShops()));

        // PRODUCTS
        Product footLockerP = new Product("Nike Air Jordan", "Nike Air Jordan 1 Black", 100F, null, "/assets/img/locker.jpg", footLocker.getId());
        Product nudeProjectP = new Product("Nude Sweater", "Nude Project brow sweater", 69.99F, null, "/assets/img/nude.jpg", nudeProject.getId());
        Product martinValenP = new Product("White Sneakers", "White sneakers from Martin Valen", 79.99F, null, "/assets/img/martin.jpg", martinValen.getId());

        // COMPOSITIONS
        Composition composition1 = new Composition("100% leather");
        Composition composition2 = new Composition("100% cotton");
        Composition composition3 = new Composition("100% fur");

        this.compositionService.createComposition1(composition1);
        this.compositionService.createComposition1(composition2);
        this.compositionService.createComposition1(composition3);

        footLockerP.setComposition(composition1);
        nudeProjectP.setComposition(composition2);
        martinValenP.setComposition(composition3);

        this.productService.createProduct1(footLockerP);
        this.productService.createProduct1(nudeProjectP);
        this.productService.createProduct1(martinValenP);

        footLocker.getProducts().add(footLockerP);
        nudeProject.getProducts().add(nudeProjectP);
        martinValen.getProducts().add(martinValenP);

        this.createShop(footLocker);
        this.createShop(nudeProject);
        this.createShop(martinValen);

        long shop1 = this.shopRepository.findByName("Foot Locker").get().getId();
        long shop2 = this.shopRepository.findByName("Nude Project").get().getId();
        long shop3 = this.shopRepository.findByName("Martin Valen").get().getId();


    }

    public Shop createShop (Shop shop) {
        this.shopRepository.save(shop);
        return shop;
    }

    public Collection<Shop> getShops() {
        return this.shopRepository.findAll();
    }

    public Shop getShop(long id) {
        if (this.shopRepository.existsById(id)) {
            return this.shopRepository.findById(id).get();
        }
        return null;
    }

    @Transactional
    public Shop deleteShop(long id){
        if (this.shopRepository.existsById(id)) {
            Shop shop = this.shopRepository.findById(id).get();
            this.shopRepository.deleteById(id);
            return shop;
        }
        return null;
    }

    @Transactional
    public Shop modifyShop(long id, Shop modifiedShop){
        if (this.shopRepository.existsById(id)){
            Shop shop = this.shopRepository.findById(id).get();
            shop.setName(modifiedShop.getName());
            shop.setImage(modifiedShop.getImage());
            shop.setDirection(modifiedShop.getDirection());
            this.shopRepository.save(shop);
            return shop;
        }
        return null;
    }
}