package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Repositories.ProductRepository;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ShopService shopService;

    @Autowired
    CompositionService compositionService;

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/shops/{idShop}/products")
    public String getShopProducts(Model model, @PathVariable long idShop){

        Shop shop = shopService.getShop(idShop);
        model.addAttribute("products",shop.getProducts());
        model.addAttribute("idShop", shop.getId());
        model.addAttribute("shopName", shop.getName());
        return "showProducts";
    }

    @GetMapping("/shops/{idShop}/products/")
    public String getProductsOrderBy(Model model, @RequestParam String sortString, @PathVariable long idShop){

        //Convert String to Sort
        String[] sortParams = sortString.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Sort sort = Sort.by(direction, sortParams[0]);

        Shop shop = shopService.getShop(idShop);

        List<Product> products = new ArrayList<>();
        if (sort.toString().equals("name: ASC")) {
            products = productRepository.findAllByShopId(idShop, Sort.by("name").ascending());
        } else if (sort.toString().equals("name: DESC")) {
            products = productRepository.findAllByShopId(idShop, Sort.by("name").descending());
        } else if (sort.toString().equals("prize: ASC")) {
            products = productRepository.findAllByShopId(idShop, Sort.by("prize").ascending());
        } else if (sort.toString().equals("prize: DESC")) {
            products = productRepository.findAllByShopId(idShop, Sort.by("prize").descending());
        }

        model.addAttribute("products", products);
        model.addAttribute("idShop", shop.getId());
        model.addAttribute("shopName", shop.getName());
        return "showProducts";
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}")
    public String getProduct(Model model, @PathVariable long idShop, @PathVariable long idProduct) {
        Product product = this.productService.getProduct(idProduct);
        Shop shop = this.shopService.getShop(idShop);
        Composition composition = product.getComposition();

        model.addAttribute("nameP", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("idProduct", product.getId());

        model.addAttribute("idShop",shop.getId());
        model.addAttribute("nameShop", shop.getName());

        if(composition!=null) { //If product has composition, the add composition button is not shown
            model.addAttribute("existsComposition", true);
            model.addAttribute("content",composition.getContent());
        }
        else {
            model.addAttribute("existsComposition", false);
            model.addAttribute("content","This product hasn't got a composition yet");
        }

        return "viewProduct";
    }

    @GetMapping("/shops/{idShop}/newProduct")
    public String newProduct(Model model, @PathVariable long idShop) {
        Shop shop = this.shopService.getShop(idShop);
        model.addAttribute("idShop",shop.getId());
        return "newProduct";
    }

    @GetMapping("/shops/{idShop}/redirectNewProduct")
    public String newProduct(@PathVariable long idShop, @RequestParam String name, @RequestParam float prize, @RequestParam String description, @RequestParam String image) {
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "/assets/img/new.jpg"; //Set default image
        //Create new product with the information received in the forms
        Product product = new Product(name, description, prize, null, image, idShop);
        this.productService.saveProduct(product, idShop);
        this.shopService.getShop(idShop).getProducts().add(product);
        this.productService.saveProduct(product, idShop);
        this.shopService.saveShop(this.shopService.getShop(idShop));
        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/deleteProduct")
    public String deleteProduct(@PathVariable long id, @PathVariable long idShop) {
        Product product = this.productService.getProduct(id);
        if (product == null || this.shopService.getShop(idShop) == null || !this.shopService.getShop(idShop).getProducts().contains(product)) {
            return "redirect:/error";
        }
        Composition composition = product.getComposition();
        if (composition != null) { //If product has composition, also delete the composition
            product.setComposition(null);
            compositionService.deleteComposition(composition.getId());
        }

        this.shopService.getShop(idShop).getProducts().remove(this.productService.saveProduct(product));
        this.productService.deleteProduct(id);
        this.shopService.saveShop(this.shopService.getShop(idShop));

        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/modifyProduct")
    public String modifyProduct(Model model, @PathVariable long id, @PathVariable long idShop){
        Product product = this.productService.getProduct(id);
        //Send the data to HTML of the product that you want to modify
        model.addAttribute("idShop",idShop);
        model.addAttribute("name", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("id", product.getId());
        return "modifyProduct";
    }

   @GetMapping("/shops/{idShop}/products/redirectModifyProduct")
    public String redirectModifyProduct(@RequestParam("id") long id,@RequestParam("name") String name, @RequestParam("prize") float prize,
                                 @RequestParam("description") String description,@RequestParam("image") String image, @PathVariable long idShop){
        //Receive new data and modify the product
        this.productService.modifyProduct(id, idShop, new Product(name, description, prize,null, image, idShop));
        return "redirect:/shops/"+idShop+"/products/"+id;
    }
}
