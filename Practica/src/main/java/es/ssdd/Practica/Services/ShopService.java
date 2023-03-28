package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ShopService {
    private HashMap<Long, Shop> shops = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public ShopService(){
        createShop(new Shop("Foot Looker"));
        createShop(new Shop("Nude Project"));
        createShop(new Shop("Martin Valen"));
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
        return shops.remove(id);
    }

    // Modify


}
