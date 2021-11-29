package com.epam.esm.zotov.mjcschool.service.order;

import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Order;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.order.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepo;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    @Override
    public List<Order> getPage(int pageSize, int pageNumber) {
        return orderRepo.getPage(pageSize, pageNumber);
    }

    @Override
    public Optional<Order> getById(long id) {
        return orderRepo.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepo.save(order);
    }

}