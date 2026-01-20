package com.booking.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JmsConfig {

    public static final String AUDIT_TOPIC = "audit.topic";

    @Bean
    public ConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory artemis = new ActiveMQConnectionFactory("tcp://activemq:61616", "artemis", "artemis");
        SingleConnectionFactory factory = new SingleConnectionFactory(artemis);
        factory.setClientId("booking-app");

        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(mapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("type");
        return converter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory jmsConnectionFactory, MessageConverter messageConverter) {
        JmsTemplate template = new JmsTemplate(jmsConnectionFactory);
        template.setMessageConverter(messageConverter);
        template.setPubSubDomain(true);
        return template;
    }
}
