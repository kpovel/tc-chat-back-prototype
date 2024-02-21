package com.example.chat.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOnboardingSteps {

    String onboardingFieldStr;

    boolean onboardingEnd = false;

    public UserOnboardingSteps() {
    }
}
