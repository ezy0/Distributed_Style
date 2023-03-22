package es.ssdd.Practica.Models;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private Map<Integer, Product> products;
    private Map<Integer, Review> reviews;

    public Shop() {
        products = new HashMap<>();
        reviews = new HashMap<>();
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public void addReview(Review review) {
        reviews.put(review.getId(), review);
    }

    public Review getReviews(int id) {
        return reviews.get(id);
    }
}
