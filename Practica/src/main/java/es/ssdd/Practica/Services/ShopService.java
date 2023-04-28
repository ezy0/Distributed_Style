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
import java.util.Collection;

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

        // SUPPLIER
        Supplier supplier = new Supplier("Global Suppliers", "Supplying shops around the world since 1995");

        // SHOPS
        Shop footLocker = new Shop("Foot Locker", "/assets/img/footlocker.png", "C. de la Palma, 69, 28015 Madrid");
        Shop nudeProject = new Shop("Nude Project", "/assets/img/NUDE_PROJECT_COCOA_2.png", "C/ Gran Vía, 18, 28013 Madrid");
        Shop martinValen = new Shop("Martin Valen", "/assets/img/67237831_101270447883552_3670809205297643520_n.jpg", "C. de Fuente Chica, 28034 Madrid");

        footLocker.getSuppliers().add(supplier);
        nudeProject.getSuppliers().add(supplier);
        martinValen.getSuppliers().add(supplier);

        // PRODUCTS
        Product footLockerP = new Product("Nike Air Jordan", "Nike Air Jordan 1 Black", 100F, null, "/assets/img/locker.jpg", footLocker.getId());
        Product nudeProjectP = new Product("Nude Sweater", "Nude Project brow sweater", 69.99F, null, "/assets/img/nude.jpg", nudeProject.getId());
        Product martinValenP = new Product("White Sneakers", "White sneakers from Martin Valen", 79.99F, null, "/assets/img/martin.jpg", martinValen.getId());

        // COMPOSITIONS
        Composition composition1 = new Composition("100% leather");
        Composition composition2 = new Composition("100% cotton");
        Composition composition3 = new Composition("100% fur");

        // GUARDAMOS SUPPLIER
        this.supplierService.saveSupplier(supplier);

        // GUARDAMOS TIENDAS
        this.saveShop(footLocker);
        this.saveShop(nudeProject);
        this.saveShop(martinValen);

        // GUARDAMOS PRODUCTOS
        long idTienda1 = this.shopRepository.findByName("Foot Locker").get(0).getId();
        long idTienda2 = this.shopRepository.findByName("Nude Project").get(0).getId();
        long idTienda3 = this.shopRepository.findByName("Martin Valen").get(0).getId();

        footLockerP.setShopId(idTienda1);
        nudeProjectP.setShopId(idTienda2);
        martinValenP.setShopId(idTienda3);

        Product pr1 = this.productService.saveProduct(footLockerP);
        Product pr2 = this.productService.saveProduct(nudeProjectP);
        Product pr3 = this.productService.saveProduct(martinValenP);

        // GUARDAMOS COMPOSITIONS
        long idProduct1 = pr1.getId();
        long idProduct2 = pr2.getId();
        long idProduct3 = pr3.getId();

        pr1.setComposition(this.compositionService.saveComposition(composition1, idProduct1));
        pr2.setComposition(this.compositionService.saveComposition(composition2, idProduct2));
        pr3.setComposition(this.compositionService.saveComposition(composition3, idProduct3));

        this.productService.saveProduct(pr1);
        this.productService.saveProduct(pr2);
        this.productService.saveProduct(pr3);

        // GUARDAR PRODUCTOS EN TIENDAS
        footLocker.getProducts().add(footLockerP);
        nudeProject.getProducts().add(nudeProjectP);
        martinValen.getProducts().add(martinValenP);

        this.shopRepository.save(footLocker);
        this.shopRepository.save(nudeProject);
        this.shopRepository.save(martinValen);
    }

    public Shop saveShop(Shop shop) {
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