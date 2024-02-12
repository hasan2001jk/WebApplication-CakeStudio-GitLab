package ru.samgups.cakestudio.controller;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.samgups.cakestudio.dto.EmailRequest;
import ru.samgups.cakestudio.dto.OrderDto;
import ru.samgups.cakestudio.entity.*;
import ru.samgups.cakestudio.entity.enums.Stuffing;
import ru.samgups.cakestudio.exceptions.ProductNotFoundException;
import ru.samgups.cakestudio.repository.*;
import ru.samgups.cakestudio.service.EmailSenderService;
import ru.samgups.cakestudio.service.OrderService;
import ru.samgups.cakestudio.service.ShoppingCartService;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;
import static org.springframework.beans.MethodInvocationException.ERROR_CODE;

@Controller
public class ProductController {

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        List<Product> prods = productRepository.findByProductCategory_Id(10L);
        model.addAttribute("products", prods);
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);

        return "index"; // Redirect to a default URL if the referer is not available


    }

    @GetMapping("/production")
    public String production(Model model, HttpServletRequest request) {
        List<Product> cards = productRepository.findByProductCategory_Id(10L);
        model.addAttribute("card", cards);

        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line

        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }


        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);

        return "production"; // Redirect to a default URL if the referer is not available


    }

    @GetMapping("/production/{id}")
    public String productionByCategoryId(Model model, HttpServletRequest request, @PathVariable Long id) {
        List<Product> cards = productRepository.findByProductCategory_Id(id);

        model.addAttribute("card", cards);
        System.out.println("FIS");
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }


        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);

        return "production"; // Redirect to a default URL if the referer is not available

    }

    @GetMapping("/production/sort/{product_category_id}")
    public String productionSortByCategoryId(HttpServletRequest request, Model model, @PathVariable Long product_category_id, @RequestParam(defaultValue = "price_sort") String sort) {
        List<Product> products = productRepository.findByProductCategory_Id(product_category_id);

        if ("name_sort".equals(sort)) {
            products.sort(Comparator.comparing(Product::getName));
        } else if ("hits_sort".equals(sort)) {
            Collections.shuffle(products);
        } else if ("price_sort".equals(sort)) {
            products.sort(Comparator.comparingDouble(Product::getPrice));
        } else if ("customers_marks_sort".equals(sort)) {
            Collections.shuffle(products);
        } else if ("date_sort".equals(sort)) {
            Collections.shuffle(products);
        } else {
            // default sorting
            Collections.shuffle(products);
        }
        model.addAttribute("card", products);
        System.out.println("FICK");
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }


        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);


        return "production"; // Redirect to a default URL if the referer is not available

    }

    @GetMapping("/production/search/")
    public String searchByProductName(Model model, @RequestParam(name = "Search") String search, HttpServletRequest request) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(search);

        model.addAttribute("card", products);
        if (products.isEmpty()) {
            model.addAttribute("message", "По вашему запросу ничего не найдено");
        }
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }


        return "search"; // Redirect to a default URL if the referer is not available

    }


    @GetMapping("/cart_noregisted")
    public String viewCart(HttpServletRequest request, Model model) {
        List<Product> prods = productRepository.findByProductCategory_Id(10L);

        model.addAttribute("products", prods);

        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        for (CartItem cart:cartItems) {
            System.out.println(cart.getStuffing());
        }
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        String referer = request.getHeader("Referer");
        System.out.println("Referer: " + referer);


        System.out.println(referer.contains("/order/proverka"));
        if (referer.contains("/order/proverka")){
            return "redirect:/order/proverka";
        }else if(referer != null){
            return "redirect:" + referer;
        }else{
            return "redirect:/"; // Redirect to a default URL if the referer is not available
        }

    }


    @GetMapping("/basket/add")
    public String addToCart(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") Long productId) {
        // Retrieve the current cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);


        // Check if the product is already in the cart
        boolean isExistingItem = false;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)) {
                isExistingItem = true;
                // Increment the quantity of the existing cart item
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                break;
            }
        }
        System.out.println(isExistingItem);
        if (!isExistingItem) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                cartItems.add(new CartItem(product, 1));
            }
        }

        // Update the cart items in the cookie
        updateCartItemsInCookie(response, cartItems);

        // Redirect to the cart or basket page
        return "redirect:/cart_noregisted";
    }

    @RequestMapping(value = "/basket/add", method = RequestMethod.POST)
    public String addToCartInView(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") Long productId) {
        // Retrieve the current cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        Integer quantity = request.getParameter("quantity")==null?1:Integer.valueOf(request.getParameter("quantity"));
        System.out.println(quantity);
        System.out.println(request.getParameter("cream"));
//        System.out.println(Stuffing.valueOf(request.getParameter("cream")));
        Stuffing stuffing = null;
        if(request.getParameter("cream")!=null) {
            switch (request.getParameter("cream")) {
                case "STUFFING_RAFFAELLO_PREMIUM":
                    stuffing = Stuffing.STUFFING_RAFFAELLO_PREMIUM;
                    break;
                case "STUFFING_OREO_PREMIUM":
                    stuffing = Stuffing.STUFFING_OREO_PREMIUM;
                    break;
                case "STUFFING_MOUSSE_RASPBERRY_PREMIUM":
                    stuffing = Stuffing.STUFFING_MOUSSE_RASPBERRY_PREMIUM;
                    break;
                case "STUFFING_PRAGUE_PREMIUM":
                    stuffing = Stuffing.STUFFING_PRAGUE_PREMIUM;
                    break;
                case "STUFFING_THREE_CHOCOLATES_PREMIUM":
                    stuffing = Stuffing.STUFFING_THREE_CHOCOLATES_PREMIUM;
                    break;
                case "STUFFING_TRUFFLE_PREMIUM":
                    stuffing = Stuffing.STUFFING_TRUFFLE_PREMIUM;
                    break;
                case "STUFFING_ESTERHAZY_PREMIUM":
                    stuffing = Stuffing.STUFFING_ESTERHAZY_PREMIUM;
                    break;
                case "STUFFING_MILK_GIRL_PREMIUM":
                    stuffing = Stuffing.STUFFING_MILK_GIRL_PREMIUM;
                    break;
                default:
                    System.out.println("Has not been chosen any stuffing!");
                    break;
            }
        }


        // Check if the product is already in the cart
        boolean isExistingItem = false;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)) {
                isExistingItem = true;
                // Increment the quantity of the existing cart item
                if (quantity > 1) {
                    cartItem.setQuantity(quantity);
                    if(stuffing != null) {
                        cartItem.setStuffing(stuffing);
                    }
                } else {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    if(stuffing != null) {
                        cartItem.setStuffing(stuffing);
                    }
                }
                break;
            }
        }

        System.out.println(isExistingItem);
        if (!isExistingItem) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                if (quantity > 1) {
                    if(stuffing != null) {
                        cartItems.add(new CartItem(product, quantity, stuffing));
                    }else{
                        cartItems.add(new CartItem(product, quantity));
                    }
                } else {
                    if(stuffing != null) {
                        cartItems.add(new CartItem(product, 1, stuffing));
                    }else{
                        cartItems.add(new CartItem(product, 1));
                    }
                }
            }
        }

        // Update the cart items in the cookie
        updateCartItemsInCookie(response, cartItems);

        // Redirect to the cart or basket page
        return "redirect:/cart_noregisted";
    }


    @GetMapping("/basket/delete")
    public String deleteFromCart(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") Long productId) {
        // Retrieve the current cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        // Find the CartItem with matching productId and remove it from the list
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId().equals(productId));

        // Update the cart items in the cookie
        updateCartItemsInCookie(response, cartItems);

        // Redirect to the cart or basket page
        return "redirect:/cart_noregisted";
    }

    private List<CartItem> getCartItemsFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Optional<Cookie> cartItemsCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("cartItems"))
                    .findFirst();

            if (cartItemsCookie.isPresent()) {
                String encodedCartItemsString = cartItemsCookie.get().getValue();
                String cartItemsString = URLDecoder.decode(encodedCartItemsString, StandardCharsets.UTF_8);

                return Arrays.stream(cartItemsString.split(","))
                        .filter(item -> !item.isEmpty())
                        .map(item -> {
                            String[] parts = item.split(":");
                            if (parts.length == 2) {
                                Long productId = Long.valueOf(parts[0]);
                                int quantity = Integer.parseInt(parts[1]);
                                Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
                                return new CartItem(product, quantity);
                            } else {
                                Long productId = Long.valueOf(parts[0]);
                                int quantity = Integer.parseInt(parts[1]);
                                System.out.println(parts[2]);
                                System.out.println(parts[2].equals("null"));
                                if(parts[2].equals("null")){
                                    Product product = productRepository.findById(productId)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
                                    return new CartItem(product, quantity);
                                }else{
                                    Stuffing stuffing = Stuffing.valueOf(valueOf(parts[2]));
                                    Product product = productRepository.findById(productId)
                                            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
                                    return new CartItem(product, quantity,stuffing);
                                }

                            }
                        })
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }


    private void updateCartItemsInCookie(HttpServletResponse response, List<CartItem> cartItems) {
        String cartItemsString = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getId() + ":" + cartItem.getQuantity() + ":" + cartItem.getStuffing())
                .collect(Collectors.joining(","));

        String encodedCartItemsString = URLEncoder.encode(cartItemsString, StandardCharsets.UTF_8);

        Cookie cookie = new Cookie("cartItems", encodedCartItemsString);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("cartItems")){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    @GetMapping("/item/{id}")
    public String viewItemByProductId(Model model, HttpServletRequest request, @PathVariable Long id) {
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        Optional<Product> product = productRepository.findById(id);


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }


        model.addAttribute("quantity", cartItems.stream().filter(cart -> cart.getProduct().getId().equals(id)).collect(Collectors.toList()));
        model.addAttribute("cartItem", product);
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "item";
    }

    @GetMapping("/to-basket")
    public String goToBasket(Model model, HttpServletRequest request) {

        List<CartItem> cartItems = getCartItemsFromCookie(request);

        if (cartItems.size() > 0) {
            System.out.println("Cart Items: " + cartItems); // Add this line
            // Get the total price
            double totalPrice = 0.0;
            for (CartItem cartItem : cartItems) {
                totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
            }
            cartItems.get(0);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails principal = (UserDetails) authentication.getPrincipal();
                System.out.println(principal);
                String email = principal.getUsername().toString();
                User user = userRepository.findByEmail(email);
                model.addAttribute("is_auth",true);
                model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            } else if (authentication.getPrincipal().equals("anonymousUser")) {
                System.out.println("User is anonymous");
                model.addAttribute("is_auth",false);
            } else {
                UserDetails principal = (UserDetails) authentication.getPrincipal();
                System.out.println(principal);
                String email = principal.getUsername().toString();
                User user = userRepository.findByEmail(email);
                model.addAttribute("is_auth",true);
                model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            }

            model.addAttribute("cart_item", cartItems);
            model.addAttribute("total_price", totalPrice);
            return "basket";
        } else {
            return "basket_empty";
        }
    }

    @GetMapping("/order/proverka")
    public String order(Model model, HttpServletRequest request,HttpServletResponse response) {

        Boolean changeCream=(request.getParameter("cream") !=null || request.getParameter("quantity") != null)?true:false;


        List<CartItem> cartItems = getCartItemsFromCookie(request);

        //HERE PROBLEM !!!!
        if(changeCream){
            // Retrieve the cart items from the cookie
            System.out.println("PROVERKA------------------");

            if(request.getParameterValues("quantity")!=null) {
                String[] values = request.getParameterValues("quantity");
                Integer[] integerValues = Arrays.stream(values)
                        .map(Integer::parseInt)
                        .toArray(Integer[]::new);
                Arrays.stream(integerValues)
                        .forEach(System.out::println);

                for (int i = 0; i < integerValues.length; i++) {
                    cartItems.get(i).setQuantity(integerValues[i]);
                }
            }

            if(request.getParameterValues("cream")!=null) {
                String[] creamNames = request.getParameterValues("cream");
                Arrays.stream(creamNames).forEach(System.out::println);
                System.out.println("creamNames.length: " + creamNames.length);
                for (String cream : creamNames) {
                    String[] parts = cream.split(",");
                    String part1 = parts[0]; // STUFFING_PRAGUE_PREMIUM
                    System.out.println(part1);
                    Long part2 = Long.parseLong(parts[1]); // 79
                    System.out.println("part2: " + part2);
                    for (int i = 0; i < cartItems.size(); i++) {
                        if (cartItems.get(i).getProduct().getId() == part2) {
                            System.out.println("part1: " + part1);
                            switch (part1) {
                                case "STUFFING_RAFFAELLO_PREMIUM":
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_RAFFAELLO_PREMIUM);
                                    System.out.println("STUFFING_RAFFAELLO_PREMIUM");
                                    break;
                                case "STUFFING_OREO_PREMIUM":
                                    System.out.println("STUFFING_OREO_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_OREO_PREMIUM);
                                    break;
                                case "STUFFING_MOUSSE_RASPBERRY_PREMIUM":
                                    System.out.println("STUFFING_MOUSSE_RASPBERRY_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_MOUSSE_RASPBERRY_PREMIUM);
                                    break;
                                case "STUFFING_PRAGUE_PREMIUM":
                                    System.out.println("STUFFING_PRAGUE_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_PRAGUE_PREMIUM);
                                    break;
                                case "STUFFING_THREE_CHOCOLATES_PREMIUM":
                                    System.out.println("STUFFING_THREE_CHOCOLATES_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_THREE_CHOCOLATES_PREMIUM);
                                    break;
                                case "STUFFING_TRUFFLE_PREMIUM":
                                    System.out.println("STUFFING_TRUFFLE_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_TRUFFLE_PREMIUM);
                                    break;
                                case "STUFFING_ESTERHAZY_PREMIUM":
                                    System.out.println("STUFFING_ESTERHAZY_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_ESTERHAZY_PREMIUM);
                                    break;
                                case "STUFFING_MILK_GIRL_PREMIUM":
                                    System.out.println("STUFFING_MILK_GIRL_PREMIUM");
                                    cartItems.get(i).setStuffing(Stuffing.STUFFING_MILK_GIRL_PREMIUM);
                                    break;
                                default:
                                    System.out.println("Has not been chosen any stuffing!");
                                    break;
                            }

                        }
                    }
                }
            }
            updateCartItemsInCookie(response,cartItems);
            cartItems.clear();
            cartItems=getCartItemsFromCookie(request);

            System.out.println("Cart Items: " + cartItems); // Add this line
            //Get the total price
            double totalPrice = 0.0;
            double totalOldPrice=0.0;
            for (CartItem cartItem : cartItems) {
                totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
                if(cartItem.getProduct().getOldPrice()==0){
                    totalOldPrice +=cartItem.getProduct().getPrice()*cartItem.getQuantity();
                }else{
                    totalOldPrice +=cartItem.getProduct().getOldPrice()*cartItem.getQuantity();
                }
            }

            double priceForBox=0.0;
            double oldPriceForBox=0.0;
            Integer quantityForBoxes=0;

            for(CartItem cart : cartItems){
                if(cart.getProduct().getProductCategory().getId()!=9){
                    priceForBox+=100 * cart.getQuantity();
                    oldPriceForBox += priceForBox+50;
                    quantityForBoxes += cart.getQuantity();
                }
            }

            for(CartItem cart:cartItems){
                System.out.println("CartItem.id(): "+cart.getProduct().getId());
                System.out.println("CartItem.name(): "+cart.getProduct().getName());
                System.out.println("CartItem.stuffing(): "+cart.getStuffing());
            }

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();

            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails principal = (UserDetails) authentication.getPrincipal();
                System.out.println(principal);
                String email = principal.getUsername().toString();
                User user = userRepository.findByEmail(email);
                model.addAttribute("is_auth",true);
                model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            } else if (authentication.getPrincipal().equals("anonymousUser")) {
                System.out.println("User is anonymous");
                model.addAttribute("is_auth",false);
            } else {
                UserDetails principal = (UserDetails) authentication.getPrincipal();
                System.out.println(principal);
                String email = principal.getUsername().toString();
                User user = userRepository.findByEmail(email);
                model.addAttribute("is_auth",true);
                model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            }


            model.addAttribute("cart_item", cartItems);
            model.addAttribute("total_price", totalPrice);
            model.addAttribute("total_old_price",String.format("%.2f", totalOldPrice));
            model.addAttribute("price_for_box",priceForBox);
            model.addAttribute("old_price_for_box",oldPriceForBox);
            model.addAttribute("quantity_for_boxes",quantityForBoxes);

        }else{
            // Retrieve the cart items from the cookie

            System.out.println("Cart Items: " + cartItems); // Add this line
            //Get the total price
            double totalPrice = 0.0;
            double totalOldPrice=0.0;
            for (CartItem cartItem : cartItems) {
                totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
                if(cartItem.getProduct().getOldPrice()==0){
                    totalOldPrice +=cartItem.getProduct().getPrice()*cartItem.getQuantity();
                }else{
                    totalOldPrice +=cartItem.getProduct().getOldPrice()*cartItem.getQuantity();
                }
            }

            double priceForBox=0.0;
            double oldPriceForBox=0.0;
            Integer quantityForBoxes=0;

            for(CartItem cart : cartItems){
                if(cart.getProduct().getProductCategory().getId()!=9){
                    priceForBox+=100 * cart.getQuantity();
//                    System.out.println("priceForBox=: "+priceForBox);
                    oldPriceForBox += priceForBox+50;
//                    System.out.println("oldPriceForBox=: "+oldPriceForBox);
                    quantityForBoxes += cart.getQuantity();
                }
            }

            //Random Stuffing
            Stuffing[] stuffings = Stuffing.values();

            for(CartItem cart : cartItems){
                Stuffing randomStuffing = stuffings[(int) (Math.random() * stuffings.length)];
                cart.setStuffing(randomStuffing);
            }

            model.addAttribute("cart_item", cartItems);
            model.addAttribute("total_price", totalPrice);
            model.addAttribute("total_old_price",String.format("%.2f", totalOldPrice));
            model.addAttribute("price_for_box",priceForBox);
            model.addAttribute("old_price_for_box",oldPriceForBox);
            model.addAttribute("quantity_for_boxes",quantityForBoxes);
//
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
        }

        return "order";


    }

    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String processOrder(Model model, HttpServletRequest request, HttpServletResponse response) throws MessagingException {
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        EmailRequest emailRequest = new EmailRequest();
        for (String parametr : request.getParameterValues("face")){
            emailRequest.setSubjectFace(parametr);
            System.out.println(parametr);
        }

        for (String parametr : request.getParameterValues("buyer_city")){
            emailRequest.setCity(parametr);
            System.out.println(parametr);
        }

        for (String parametr : request.getParameterValues("delivery_way")){
            emailRequest.setDeliveryWay(parametr);
            System.out.println(parametr);
        }

        for (String parametr : request.getParameterValues("pay_method")){
            emailRequest.setPayMethod(parametr);
            System.out.println(parametr);
        }

        for (String parametr : request.getParameterValues("buyer_and_check")){
            System.out.println(parametr);
        }

        String []parts=request.getParameterValues("buyer_and_check")[0].split(" ");
        System.out.println(parts[0]);
        System.out.println(parts[1]);
        System.out.println(parts[2]);
        emailRequest.setFirstName(parts[1]);
        emailRequest.setLastName(parts[0]);
        emailRequest.setPhone(request.getParameterValues("buyer_and_check")[2]);
        emailRequest.setEmail(request.getParameterValues("buyer_and_check")[1]);
        emailRequest.setDateAndTime(request.getParameterValues("buyer_and_check")[3]);
        emailRequest.setReceivingStation(request.getParameterValues("buyer_and_check")[5]);
        emailRequest.setTrainNumber(request.getParameterValues("buyer_and_check")[4]);
        if(!request.getParameterValues("buyer_and_check")[6].equals(null)){
            emailRequest.setVagonNum(request.getParameterValues("buyer_and_check")[6]);
        }

        emailSenderService.sendHtmlMessage(emailRequest,cartItems);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            /*for (int i = 0; i < cartItems.size(); i++) {
                System.out.println(cartItems.get(i));
                cartItems.get(i).setId(i + 1L);
                System.out.println(cartItems.get(i));
            }*/
            for(CartItem cart: cartItems){
                System.out.println(cart);
                System.out.println("Stuffing: "+cart.getStuffing());
                cartItemRepository.save(cart);
            }
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setCartItems(cartItems);
            shoppingCart.setUserId(user.getId());

            System.out.println("---------------------");

            shoppingCartService.createShoppingCart(shoppingCart);
            orderService.createOrder(orderService.placeOrder(new OrderDto(user.getId(),user.getName(),user.getEmail(),emailRequest.getReceivingStation(),cartItems,emailRequest.getPayMethod())));

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setCartItems(cartItems);
            shoppingCart.setUserId(user.getId());
            shoppingCartService.createShoppingCart(shoppingCart);
            orderService.createOrder(orderService.placeOrder(new OrderDto(user.getId(),user.getName(),user.getEmail(),emailRequest.getReceivingStation(),cartItems,emailRequest.getPayMethod())));
        }
        deleteAllCookies(request,response);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletRequest request){
        System.out.println("WE ARE HERE!!!");
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("Username: " + principal.getUsername());
            System.out.println("Password: " + principal.getPassword());
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            String username = authentication.getPrincipal().toString();
            System.out.println("Username: " + username);
        }

        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "auth";
    }

    @GetMapping("/login/error")
    public String getLoginErrorPage(Model model, HttpServletRequest request){
        System.out.println("OLA-------------------");
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }


        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "auth";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model, HttpServletRequest request){
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        // Pass the cart items and total price to the view
        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "registration";
    }


    @GetMapping("/my_account")
    public String getMyAccountPage(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "my_account";
    }


    @GetMapping("/my_orders")
    public String getMyAccountPageOrders(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
            List<Order> orders = orderService.getOrdersByUserId(user.getId());

            model.addAttribute("orders",orders);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
            List<Order> orders = orderService.getOrdersByUserId(user.getId());

            model.addAttribute("orders",orders);


        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "my_orders";
    }

    @GetMapping("/my_details")
    public String getMyAccountPageDetails(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "my_details";
    }

    @PostMapping("my_details/change")
    public String changeMyAccountPageDetails(HttpServletRequest request,HttpServletResponse response){
        String emailAddress = request.getParameter("emailAddress");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            user.setName(firstName);
            user.setSurname(lastName);
            user.setEmail(emailAddress);
            userRepository.save(user);
            if(emailAddress.equals(email)){
                userRepository.save(user);
                return "redirect:/my_details";
            }else{
                deleteAllCookies(request,response);
                userRepository.save(user);
                return "redirect:/";
            }
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            return "";
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            user.setName(firstName);
            user.setSurname(lastName);
            user.setEmail(emailAddress);
            if(emailAddress.equals(email)){
                userRepository.save(user);
                return "redirect:/my_details";
            }else{
                deleteAllCookies(request,response);
                userRepository.save(user);
                return "redirect:/";
            }

        }

    }


    @GetMapping("/payment_method")
    public String getMyAccountPagePaymentMethod(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "payment_method";
    }

    @GetMapping("/contact_preferences")
    public String getMyAccountPageContactPreferences(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "contact_preferences";
    }


    @GetMapping(value = "/saved_items/add")
    public String addToSavedItems(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") Long productId) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            if (user.getSavedProducts().isEmpty()) {
                List<Product> savedProducts = productRepository.findAll()
                        .stream()
                        .filter(x -> x.getId().equals(productId))
                        .collect(Collectors.toList());
                user.setSavedProducts(savedProducts);
                userRepository.save(user);
            } else {
                List<Product> savedProducts = user.getSavedProducts();
                Product product = productRepository.findAll()
                        .stream()
                        .filter(x -> x.getId().equals(productId))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    System.out.println(savedProducts.stream().filter(x->x.getId().equals(productId)).collect(Collectors.toList())!=null);
                    Boolean is_here=false;
                    for(Product pr: savedProducts){
                        if(pr.getId().equals(productId)){
                            is_here=true;
                            break;
                        }
                    }

                    if (is_here == false) {
                        savedProducts.add(product);
                    }
                    user.setSavedProducts(savedProducts);
                    userRepository.save(user);
                }
            }
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            if (user.getSavedProducts().isEmpty()) {
                List<Product> savedProducts = productRepository.findAll()
                        .stream()
                        .filter(x -> x.getId().equals(productId))
                        .collect(Collectors.toList());
                user.setSavedProducts(savedProducts);
                userRepository.save(user);
            } else {
                List<Product> savedProducts = user.getSavedProducts();
                Product product = productRepository.findAll()
                        .stream()
                        .filter(x -> x.getId().equals(productId))
                        .findFirst()
                        .orElse(null);
                if (product != null) {
                    System.out.println(savedProducts.stream().filter(x->x.getId().equals(productId)).collect(Collectors.toList())!=null);
                    Boolean is_here=false;
                    for(Product pr: savedProducts){
                        if(pr.getId().equals(productId)){
                            is_here=true;
                            break;
                        }
                    }

                    if (is_here == false) {
                        savedProducts.add(product);
                    }
                    user.setSavedProducts(savedProducts);
                    userRepository.save(user);
                }
            }
        }

        // Pass the cart items and total price to the view

        String referer = request.getHeader("Referer");
        System.out.println("Referer: " + referer);

        System.out.println(referer.contains("/order/proverka"));
        if (referer.contains("/order/proverka")){
            return "redirect:/order/proverka";
        }else if(referer != null){
            return "redirect:" + referer;
        }else{
            return "redirect:/"; // Redirect to a default URL if the referer is not available
        }

    }


    @GetMapping("/saved_items")
    public String getMyAccountPageSavedItems(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);

        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "saved_items";
    }

    @GetMapping("/saved_items/delete")
    public String savedItemsDeleteItem(HttpServletRequest request, HttpServletResponse response, @RequestParam("productId") Long productId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            List<Product> savedItems = user.getSavedProducts();
            savedItems.removeIf(x->x.getId().equals(productId));
            userRepository.save(user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");

        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            List<Product> savedItems = user.getSavedProducts();
            savedItems.removeIf(x->x.getId().equals(productId));
            userRepository.save(user);
        }
        return "redirect:/saved_items";
    }

    @GetMapping("/saved_items/filter")
    public String savedItemsFilter(Model model,HttpServletRequest request, HttpServletResponse response, @RequestParam("filterId") Integer filterId){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            List<Product> savedProducts = user.getSavedProducts();
            if(filterId == 2){
                Collections.sort(savedProducts, (p1, p2) -> {
                    if (p1.getOldPrice() != 0 && p2.getOldPrice() == 0) {
                        return -1; // Move p1 to the top
                    } else if (p1.getOldPrice() == 0 && p2.getOldPrice() != 0) {
                        return 1; // Move p2 to the top
                    } else {
                        return 0; // Preserve the relative order
                    }
                });
            }else if(filterId == 3){
                Collections.sort(savedProducts, Comparator.comparingDouble(Product::getPrice).reversed());
            }else{
                Collections.sort(savedProducts, Comparator.comparingDouble(Product::getPrice));
            }
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            List<Product> savedProducts = user.getSavedProducts();
            if(filterId == 2){
                Collections.sort(savedProducts, (p1, p2) -> {
                    if (p1.getOldPrice() != 0 && p2.getOldPrice() == 0) {
                        return -1; // Move p1 to the top
                    } else if (p1.getOldPrice() == 0 && p2.getOldPrice() != 0) {
                        return 1; // Move p2 to the top
                    } else {
                        return 0; // Preserve the relative order
                    }
                });
            }else if(filterId == 3){
                Collections.sort(savedProducts, Comparator.comparingDouble(Product::getPrice).reversed());
            }else{
                Collections.sort(savedProducts, Comparator.comparingDouble(Product::getPrice));
            }
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        return "saved_items";
    }


    public String getPage(Model model, HttpServletRequest request){
        // Retrieve the cart items from the cookie
        List<CartItem> cartItems = getCartItemsFromCookie(request);
        System.out.println("Cart Items: " + cartItems); // Add this line
        // Get the total price
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);

        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
            model.addAttribute("is_auth",false);
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            String email = principal.getUsername().toString();
            User user = userRepository.findByEmail(email);
            model.addAttribute("is_auth",true);
            model.addAttribute("auth_name",user.getName()+" "+user.getSurname());
            model.addAttribute("logo_user", user);
        }

        // Pass the cart items and total price to the view

        model.addAttribute("cart_item", cartItems);
        model.addAttribute("total_price", totalPrice);
        String referer = request.getHeader("Referer");
        String address = request.getRequestURI();
        System.out.println("Referer: " + referer);
        System.out.println(address);

        return address.substring(1, address.length());
    }


    public String generatePassayPassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }

    @PostMapping("/repair_password")
    public String repairPassword(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("USER_LOGIN");
        if(request.getParameter("USER_LOGIN")!=null){
            User user = userRepository.findByEmail(email);
            String password =generatePassayPassword();
            if(user != null){
                System.out.println("Password gen :" + password);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
                userRepository.save(user);
            }else{
                System.out.println("There is no such user!");
            }
            emailSenderService.sendHtmlMessage(user,password);
        }
        return "redirect:/registration";
    }

    public List<CartItem> getDiscountForCartItems(List<CartItem> cartItems){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            for(CartItem cart: cartItems){
                Double discount = cart.getProduct().getPrice()-(cart.getProduct().getPrice()*0.1);
                cart.getProduct().setPrice(discount);
            }
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            for(CartItem cart: cartItems){
                Double discount = cart.getProduct().getPrice()-(cart.getProduct().getPrice()*0.1);
                cart.getProduct().setPrice(discount);
            }
        }
        return cartItems;
    }

    public List<Product> getDiscountForProducts(List<Product> cards){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            for(Product card: cards){
                Double discount = card.getPrice()-(card.getPrice()*0.1);
                card.setPrice(discount);
            }
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            for(Product card: cards){
                Double discount = card.getPrice()-(card.getPrice()*0.1);
                card.setPrice(discount);
            }
        }
        return cards;
    }

    public Optional<Product> getDiscountForProduct(Optional<Product> product){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            Double discount = product.get().getPrice()-(product.get().getPrice()*0.1);
            product.get().setPrice(discount);
        } else if (authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("User is anonymous");
        } else {
            UserDetails principal = (UserDetails) authentication.getPrincipal();
            System.out.println(principal);
            System.out.println("User authenticated!!!");
            Double discount = product.get().getPrice()-(product.get().getPrice()*0.1);
            product.get().setPrice(discount);
        }
        return product;
    }

}
