package ru.samgups.cakestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.samgups.cakestudio.entity.CartItem;
import ru.samgups.cakestudio.entity.Product;
import ru.samgups.cakestudio.entity.ShoppingCart;
import ru.samgups.cakestudio.entity.User;
import ru.samgups.cakestudio.repository.CartItemRepository;
import ru.samgups.cakestudio.repository.ProductRepository;
import ru.samgups.cakestudio.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(
            ShoppingCartRepository shoppingCartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository
    ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }


    @Autowired
    UserService userService;



    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingCart> getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public void addToCart(Long shoppingCartId, Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
            if (optionalShoppingCart.isPresent()) {
                ShoppingCart shoppingCart = optionalShoppingCart.get();
                CartItem cartItem = new CartItem(product, quantity);
                shoppingCart.getCartItems().add(cartItem);
                shoppingCartRepository.save(shoppingCart);
            }
        }
    }

    public void removeFromCart(Long shoppingCartId, Long cartItemId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            List<CartItem> cartItems = shoppingCart.getCartItems();
            cartItems.removeIf(cartItem -> cartItem.getId().equals(cartItemId));
            shoppingCartRepository.save(shoppingCart);
        }
    }

    public void updateCartQuantity(Long shoppingCartId, Long cartItemId, int quantity) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (optionalShoppingCart.isPresent()) {
            ShoppingCart shoppingCart = optionalShoppingCart.get();
            List<CartItem> cartItems = shoppingCart.getCartItems();
            for (CartItem cartItem : cartItems) {
                if (cartItem.getId().equals(cartItemId)) {
                    cartItem.setQuantity(quantity);
                    break;
                }
            }
            shoppingCartRepository.save(shoppingCart);
        }
    }

    public void clearCart(Long shoppingCartId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(shoppingCartId);
        optionalShoppingCart.ifPresent(shoppingCart -> {
            shoppingCart.getCartItems().clear();
            shoppingCartRepository.save(shoppingCart);
        });
    }


    public ShoppingCart getShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Assuming you have a service to get the user by username
        User user = userService.getUserByUsername(username);

        ShoppingCart shoppingCart = (ShoppingCart) shoppingCartRepository.findByUserId(user.getId());

        return shoppingCart;
    }

    public void addToCart(CartItem cartItem) {
        // Retrieve the current shopping cart
        ShoppingCart shoppingCart = getShoppingCart();

        // Check if the product already exists in the shopping cart
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(cartItem.getProduct().getId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // If the product exists, update the quantity
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
        } else {
            // If the product doesn't exist, create a new cart item
            shoppingCart.getCartItems().add(cartItem);
        }

        // Save the updated shopping cart
        shoppingCartRepository.save(shoppingCart);
    }

    public void updateCart(ShoppingCart shoppingCart) {
        // Retrieve the current shopping cart
        ShoppingCart currentCart = getShoppingCart();

        // Update the cart items in the current shopping cart
        currentCart.setCartItems(shoppingCart.getCartItems());

        // Save the updated shopping cart
        shoppingCartRepository.save(currentCart);
    }

    public void removeCartItem(Long itemId) {
        // Retrieve the current shopping cart
        ShoppingCart shoppingCart = getShoppingCart();

        // Find the cart item with the specified itemId
        CartItem cartItemToRemove = null;
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            if (cartItem.getId().equals(itemId)) {
                cartItemToRemove = cartItem;
                break;
            }
        }

        // Remove the cart item if found
        if (cartItemToRemove != null) {
            shoppingCart.getCartItems().remove(cartItemToRemove);
        }

        // Save the updated shopping cart
        shoppingCartRepository.save(shoppingCart);
    }

}
