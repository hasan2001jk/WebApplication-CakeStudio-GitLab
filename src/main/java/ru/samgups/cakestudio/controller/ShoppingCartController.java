package ru.samgups.cakestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samgups.cakestudio.entity.CartItem;
import ru.samgups.cakestudio.entity.Product;
import ru.samgups.cakestudio.entity.ShoppingCart;
import ru.samgups.cakestudio.repository.ProductRepository;
import ru.samgups.cakestudio.service.ShoppingCartService;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public String viewCart(Model model) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart();
        model.addAttribute("cart", shoppingCart);
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            CartItem cartItem = new CartItem(product, quantity);
            shoppingCartService.addToCart(cartItem);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@ModelAttribute("cart") ShoppingCart shoppingCart) {
        shoppingCartService.updateCart(shoppingCart);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{itemId}")
    public String removeFromCart(@PathVariable("itemId") Long itemId) {
        shoppingCartService.removeCartItem(itemId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@RequestParam("cartId") Long shoppingCartId) {
        shoppingCartService.clearCart(shoppingCartId);
        return "redirect:/cart";
    }
}
