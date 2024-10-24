package com.timetracker.timetrackerapptesttask.service;

import com.timetracker.timetrackerapptesttask.dto.*;

public interface IOrderControl {
    Long startNewOrder(OrderDto orderDto);
}
