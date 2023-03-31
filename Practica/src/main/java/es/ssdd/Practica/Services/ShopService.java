package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShopService {
    private HashMap<Long, Shop> shops = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public ShopService(){
        createShop(new Shop("Foot Looker", "../assets/img/footlocker.jpg", ""));
        createShop(new Shop("Nude Project", "../assets/img/NUDE_PROJECT_COCOA_2.jpg", ""));
        createShop(new Shop("Martin Valen", "../assets/img/67237831_101270447883552_3670809205297643520_n.jpg", ""));
    }

    public Shop createShop (Shop shop) {
        shop.setId(lastId.incrementAndGet());
        shops.put(lastId.get(), shop);

        return shop;
    }

    public Collection<Shop> getShops() {
        return this.shops.values().stream().toList();
    }

    public Shop getShop(long id) {
        if (this.shops.containsKey(id)) {
            return this.shops.get(id);
        }
        return null;
    }

    public Shop deleteShop(long id){
        if (this.shops.containsKey(id)) {
            return this.shops.remove(id);
        }
        return null;
    }

    // Modify
    public Shop modifyShop(long id, Shop modifiedShop){
        Shop shop = this.getShop(id);
        if (shop == null)
            return null;
        shop.setName(modifiedShop.getName());
        shop.setImage(modifiedShop.getImage());
        shop.setDirection(modifiedShop.getDirection());
        return shop;
    }

}
