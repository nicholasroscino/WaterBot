package com.ncoding.infrastructure;

import com.ncoding.core.ports.MessagePicker;

public class RandomMessagePicker implements MessagePicker {
    @Override
    public String getMessage() {
        return "Hello, go drink some water now!";
    }
}
