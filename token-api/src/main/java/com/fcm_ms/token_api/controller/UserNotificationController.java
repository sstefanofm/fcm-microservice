package com.fcm_ms.token_api.controller;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.fcm_ms.token_api.dto.BasicNotificationRequest;
import com.fcm_ms.token_api.service.UserNotificationService;

@RestController
@RequestMapping("api/notify/user")
@RequiredArgsConstructor
public class UserNotificationController {

  private final UserNotificationService userNotificationService;

  @PostMapping("{user_external_id}")
  public String notifyUser(
      @PathVariable("user_external_id") String userExternalId,
      @RequestBody BasicNotificationRequest basicNotificationRequest) {
    String response = "Notification not sent";

    /* TODO
     *  - get list of tokens of user with externalId X
     *  - create Notification with hardcoded title and body
     *  - send to all tokens
     * */

    try {
      Message message = this.userNotificationService.getMessage(userExternalId, basicNotificationRequest);
      response = FirebaseMessaging.getInstance().sendAsync(message).get();

    } catch (Exception ex) {}

    return response;
  }

}
