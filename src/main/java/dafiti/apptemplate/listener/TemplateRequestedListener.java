package devxp-tech.apptemplate.listener;

import devxp-tech.apptemplate.config.mesassing.Queues;
import devxp-tech.apptemplate.config.mesassing.Topics;
import devxp-tech.apptemplate.model.UserMailNotification;
import devxp-tech.bootstrap.aws.BaseSqsListener;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class TemplateRequestedListener extends BaseSqsListener {
    private static final Logger logger = LoggerFactory.getLogger(TemplateRequestedListener.class);

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    public TemplateRequestedListener(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    @SqsListener(value = Queues.TEMPLATE_REQUESTED, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void process(@Header("MessageId") String messageId, @Valid @Payload UserMailNotification payload) {
        logger.info("received message with id {} and content {}", messageId, payload);
        notificationMessagingTemplate.convertAndSend(Topics.NOTIFICATION_v1, payload);
        logger.info("message with id {} and content {} was notified", messageId, payload);
    }
}
