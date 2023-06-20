package uk.gov.dwp.health.atw.msclaim.temp.messaging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest(classes = CustomerNotificationPublisher.class)
class CustomerNotificationPublisherTest {

  @InjectMocks
  CustomerNotificationPublisher customerNotificationPublisher;

  @Test
  void publishTest() throws Exception {
    ReflectionTestUtils.setField(customerNotificationPublisher, "queueUrl",
        "http://localhost:8080");
    final AmazonSQS mockAmazonSQS = mock(AmazonSQS.class);

    ReflectionTestUtils.setField(customerNotificationPublisher, "amazonSqs",
        mockAmazonSQS);

    when(mockAmazonSQS.sendMessage(any(), any())).thenReturn(new SendMessageResult());

    String samplePayload = "mockPayload";
    SendMessageRequest expectedRequest = new SendMessageRequest()
        .withQueueUrl("http://localhost:8080")
        .withMessageBody(samplePayload);

    customerNotificationPublisher.publish(samplePayload);

    verify(mockAmazonSQS, times(1)).sendMessage(eq(expectedRequest));
  }


  @Test
  void publishTestWhenAmazonSqsIsNull() throws Exception {
    ReflectionTestUtils.setField(customerNotificationPublisher, "queueUrl",
        "http://localhost:8080");
    final AmazonSQS mockAmazonSQS = mock(AmazonSQS.class);

    ReflectionTestUtils.setField(customerNotificationPublisher, "amazonSqs",
        null);

    final MockedStatic<AmazonSQSClientBuilder> amazonSQSClientBuilderMockedStatic = mockStatic(AmazonSQSClientBuilder.class);
    final AmazonSQSClientBuilder mockAmazonSQSClientBuilder = mock(AmazonSQSClientBuilder.class);
    when(mockAmazonSQSClientBuilder.defaultClient()).thenReturn(mockAmazonSQS);

    when(mockAmazonSQS.sendMessage(any(), any())).thenReturn(new SendMessageResult());

    String samplePayload = "mockPayload";
    SendMessageRequest expectedRequest = new SendMessageRequest()
        .withQueueUrl("http://localhost:8080")
        .withMessageBody(samplePayload);

    customerNotificationPublisher.publish(samplePayload);
    amazonSQSClientBuilderMockedStatic.close();

    verify(mockAmazonSQS, times(1)).sendMessage(eq(expectedRequest));
  }
}
