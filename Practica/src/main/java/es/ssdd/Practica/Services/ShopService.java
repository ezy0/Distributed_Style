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

    //private HashMap<Long, Shop> shops = new HashMap<>();
    //private AtomicLong lastId = new AtomicLong();

    @PostConstruct
    public void shopInit(){

        Shop footLocker = createShop(new Shop("Foot Locker", "/assets/img/footlocker.png", "C. de la Palma, 69, 28015 Madrid"));
        Product footLockerP = new Product("Nike Air Jordan", "Nike Air Jordan 1 Black", 100F, null, "/assets/img/locker.jpg", footLocker.getId());
        this.productService.createProduct(footLockerP, footLocker.getId());
        Composition composition1 = new Composition("100% leather");
        this.compositionService.createComposition(composition1,footLockerP.getId());


        Shop nudeProject = createShop(new Shop("Nude Project", "/assets/img/NUDE_PROJECT_COCOA_2.png", "C/ Gran Vía, 18, 28013 Madrid"));
        Product nudeProjectP = new Product("Nude Sweater", "Nude Project brow sweater", 69.99F, null, "/assets/img/nude.jpg", nudeProject.getId());
        this.productService.createProduct(nudeProjectP, nudeProject.getId());
        Composition composition2 = new Composition("100% cotton");
        this.compositionService.createComposition(composition2,footLockerP.getId());

        Shop martinValen = createShop(new Shop("Martin Valen", "/assets/img/67237831_101270447883552_3670809205297643520_n.jpg", "C. de Fuente Chica, 28034 Madrid"));
        Product martinValenP = new Product("White Sneakers", "White sneakers from Martin Valen", 79.99F, null, "/assets/img/martin.jpg", martinValen.getId());
        this.productService.createProduct(martinValenP, martinValen.getId());

        Supplier supplier = new Supplier("Global Suppliers", "Supplying shops around the world since 1995", new ArrayList<>(this.getShops()));
        this.supplierService.createSupplier(supplier);
        for (Shop shop : this.getShops())
            shop.getSuppliers().add(supplier);
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
