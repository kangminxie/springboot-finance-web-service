package com.kangmin.app.service;

import com.kangmin.app.model.payload.admin.TransitionDayRequest;

import java.util.Map;

public interface TransitionDayService {
    Map<String, String> transitionDay(TransitionDayRequest form);
}
