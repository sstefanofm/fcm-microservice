package com.fcm_ms.token_api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.fcm_ms.token_api.dto.TokenRequestDTO;
import com.fcm_ms.token_api.entity.Device;
import com.fcm_ms.token_api.repository.DeviceRepository;

@Service
@RequiredArgsConstructor
public class DeviceService {

  private final DeviceRepository deviceRepository;

  public Optional<Device> findByUuid(String uuid) {
    return this.deviceRepository.findByUuid(uuid);
  }

  @Transactional
  public Device getFromTokenRequest(TokenRequestDTO tokenRequest) {
    Device device = Device.of(
      tokenRequest.getDeviceUuid(),
      tokenRequest.getDeviceType()
    );

    return this.findByUuid(device.getUuid())
      .orElseGet(() -> this.deviceRepository.save(device));
  }
}
