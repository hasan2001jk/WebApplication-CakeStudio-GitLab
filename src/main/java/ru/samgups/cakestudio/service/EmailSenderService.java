package ru.samgups.cakestudio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.context.Context;

import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.samgups.cakestudio.dto.EmailRequest;
import ru.samgups.cakestudio.entity.CartItem;
import ru.samgups.cakestudio.entity.User;
import ru.samgups.cakestudio.entity.enums.Stuffing;

import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private final SpringTemplateEngine templateEngine;

    public void sendHtmlMessage(EmailRequest email, List<CartItem> cartItems) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("first_name",email.getFirstName());
        context.setVariable("last_name",email.getLastName());
        context.setVariable("subject_face",email.getSubjectFace());
        context.setVariable("city",email.getCity());
        context.setVariable("email",email.getEmail());
        context.setVariable("date_and_time",email.getDateAndTime());
        context.setVariable("delivery_way",email.getDeliveryWay());
        context.setVariable("pay_method",email.getPayMethod());
        context.setVariable("phone",email.getPhone());
        context.setVariable("receiving_station",email.getReceivingStation());
        context.setVariable("train_number",email.getTrainNumber());
        if(!email.getVagonNum().isEmpty()){
            context.setVariable("vagon_num",email.getVagonNum());
        }else{
            context.setVariable("vagon_num","empty");
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

        //Random Stuffing
        Stuffing[] stuffings = Stuffing.values();
        Stuffing randomStuffing = stuffings[(int) (Math.random() * stuffings.length)];

        context.setVariable("random_stuffing",randomStuffing.getValue());
        context.setVariable("cart_item", cartItems);
//        model.addAttribute("total_price", totalPrice);
//        context.setVariable("total_old_price",String.format("%.2f", totalOldPrice));
        context.setVariable("price_for_box",priceForBox);
        context.setVariable("old_price_for_box",oldPriceForBox);
        context.setVariable("quantity_for_boxes",quantityForBoxes);
        helper.setFrom("cakestudio2023@gmail.com");
        helper.setTo(email.getEmail());
        helper.setSubject("Confirmation");
        String html = templateEngine.process("email_template", context);
        helper.setText(html, true);

        log.info("Sending email: {} with html body: {}", email, html);
        emailSender.send(message);
    }

    public void sendHtmlMessage(User user, String password) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable("first_name",user.getName());
        context.setVariable("last_name",user.getSurname());
        context.setVariable("email",user.getEmail());
        context.setVariable("password",password);

        helper.setFrom("cakestudio2023@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Password was successfully changed!");
        String html = templateEngine.process("repair_password_template", context);
        helper.setText(html, true);
        log.info("Sending email: {} with html body: {}", user.getEmail(), html);
        emailSender.send(message);
    }
}