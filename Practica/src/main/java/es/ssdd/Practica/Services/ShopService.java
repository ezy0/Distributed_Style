package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Shop;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShopService {
    private HashMap<Long, Shop> shops = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public ShopService(){
        createShop(new Shop("Foot Looker", "/assets/img/footlocker.png", "C. de la Palma, 69, 28015 Madrid"));
        createShop(new Shop("Nude Project", "/assets/img/NUDE_PROJECT_COCOA_2.png", "C/ Gran Vía, 18, 28013 Madrid"));
        createShop(new Shop("Martin Valen", "/assets/img/67237831_101270447883552_3670809205297643520_n.jpg", "C. de Fuente Chica, 28034 Madrid"));
    }

    public Shop createShop (Shop shop) {
        shop.setId(lastId.incrementAndGet());

        return shops.put(lastId.get(), shop);
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
