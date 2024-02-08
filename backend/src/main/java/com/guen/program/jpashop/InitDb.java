package com.guen.program.jpashop;


import com.guen.program.jpashop.domain.*;
import com.guen.program.jpashop.domain.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * 종 주문 2개
 * * userA
 * 	 * JPA1 BOOK
 * 	 * JPA2 BOOK
 * * userB
 * 	 * SPRING1 BOOK
 * 	 * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            System.out.println("Init1" + this.getClass());
            Crew crew = createMember("userA", "서울", "1", "1111");
            em.persist(crew);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(crew);
            Order order = Order.createOrder(crew, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Crew crew = createMember("userB", "진주", "2", "2222");
            em.persist(crew);

            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);

            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(crew);
            Order order = Order.createOrder(crew, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Crew createMember(String name, String city, String street, String zipcode) {
            Crew crew = new Crew();
            crew.setName(name);
            crew.setAddress(new Address(city, street, zipcode));
            return crew;
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book1 = new Book();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            return book1;
        }

        private Delivery createDelivery(Crew crew) {
            Delivery delivery = new Delivery();
            delivery.setAddress(crew.getAddress());
            return delivery;
        }
    }
}

