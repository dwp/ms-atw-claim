package uk.gov.dwp.health.atw.msclaim.models.requests;

import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder
public class EmailNotificationRequest {

  @NotNull
  @NonNull
  String notificationId;

  @NotNull
  @NonNull
  String notificationType;

  @NotNull
  @NonNull
  String notificationTemplate;

  @NotNull
  @NonNull
  String clientApiKey;

  @NotNull
  @NonNull
  String notificationDestination;

  @NotNull
  @NonNull
  Map<String, String> notificationData;
}
