package com.example.Minor_Project_01.config;

import com.example.Minor_Project_01.entity.Order;
import com.example.Minor_Project_01.entity.OrderStatus;
import com.example.Minor_Project_01.repo.OrderRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MoveOrderDraftToExpireTask {
    private static Logger LOGGER = LoggerFactory.getLogger(MoveOrderDraftToExpireTask.class);

    @Autowired
    private OrderRepo orderRepo;

    @Scheduled(fixedDelay = 5000)
    public void markOrderExpired() {
        LOGGER.info("Moving orders from draft to expired");

        /*
        Fetch all orders with status draft and update their status to expired
         */

//        11:41

        LOGGER.info("Orders moved successfully");
    }
}
