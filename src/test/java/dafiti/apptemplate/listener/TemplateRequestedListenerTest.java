package devxp-tech.apptemplate.listener;

import devxp-tech.apptemplate.config.mesassing.Queues;
import devxp-tech.bootstrap.test.BaseSqsListenerTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TemplateRequestedListenerTest extends BaseSqsListenerTest {

    private final String queue = "template-template_requested";
//    private final String dlq = "template-template_requested_dlq"; //FIXME it not work with localstack

    @Test
    public void receive_messages_success() {
        sendMessages(Queues.TEMPLATE_REQUESTED, "json/listener/mail_message_success.json", 5);

        //        assertQueueMessagesCount(dlq, 0);
        assertQueueMessagesCount(Queues.TEMPLATE_REQUESTED, 0);
        assertQueueMessagesCount("template-notification", 5);
    }

    @Test
    public void receive_messages_parse_error() {
        sendMessages(queue, "json/listener/mail_message_error.json", 5);

        //        assertQueueMessagesCount(dlq, 5);
        assertQueueMessagesCount(queue, 0);
        assertQueueMessagesCount("template-notification", 0);
    }

    @Test
    public void receive_messages_broken_contract() {
        convertAndSendMessages(queue, Arrays.asList("a", "b"));

        //        assertQueueMessagesCount(dlq, 2);
        assertQueueMessagesCount(queue, 0);
        assertQueueMessagesCount("template-notification", 0);
    }
}
