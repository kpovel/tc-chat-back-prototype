package com.example.demo.config;

import com.example.demo.sequrity.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtUtils jwtUtils;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*");
//                .withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                    String token = accessor.getFirstNativeHeader("authorization"); // Отримайте токен з заголовка
//
//                    if (token == null || !token.startsWith("Bearer ")) {
//                        // Недійсний або відсутній токен. Видати помилку і закрити WebSocket-з'єднання.
//                        StompHeaderAccessor errorHeader = StompHeaderAccessor.create(StompCommand.ERROR);
//                        errorHeader.setMessage("Invalid or missing token");
//                        Message<byte[]> errorMessage = MessageBuilder.createMessage(new byte[0], errorHeader.getMessageHeaders());
//                        throw new MessageHandlingException(errorMessage);
//                    } else {
//                        token = token.substring(7); // Видаліть "Bearer " префікс
//                        String username = jwtUtils.getUsernameFromToken(token);
//
//                        if (username == null) {
//                            // Невірний токен. Видати помилку і закрити WebSocket-з'єднання.
//                            StompHeaderAccessor errorHeader = StompHeaderAccessor.create(StompCommand.ERROR);
//                            errorHeader.setMessage("Invalid token");
//                            Message<byte[]> errorMessage = MessageBuilder.createMessage(new byte[0], errorHeader.getMessageHeaders());
//                            throw new MessageHandlingException(errorMessage);
//                        }
//
//                        // Встановіть аутентифікацію
//                        Authentication user = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//                        accessor.setUser(user);
//                    }
//                }
//                return message;
//            }
//        });
//    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.SEND.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("authorization"); // Отримайте токен з заголовка

                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7); // Видаліть "Bearer " префікс

                        if(!jwtUtils.validateAccessToken(token)){
                            throw new BadCredentialsException(" error ");
                        }
                    }
                }
                return message;
            }
        });
    }
//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        return WebSocketMessageBrokerConfigurer.super.configureMessageConverters(messageConverters);
//    }
}
