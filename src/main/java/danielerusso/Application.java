package danielerusso;

import com.github.javafaker.Faker;
import danielerusso.entities.Customer;
import danielerusso.entities.Order;
import danielerusso.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        Faker faker = new Faker(Locale.ENGLISH);

        LocalDate order1Date = LocalDate.of(2021, 2, 25);
        LocalDate order2Date = LocalDate.of(2022, 3, 6);
        LocalDate order3Date = LocalDate.of(2021, 3, 30);
        LocalDate order4Date = LocalDate.of(2021, 12, 25);
        LocalDate delivery1Date = order1Date.plusDays(3);
        LocalDate delivery2Date = order2Date.plusDays(3);
        LocalDate delivery3Date = order3Date.plusDays(3);
        LocalDate delivery4Date = order4Date.plusDays(3);

        LocalDate dayOne = LocalDate.of(2021, 2, 1);
        LocalDate lastDay = LocalDate.of(2021, 4, 1);

        Customer customer1 = new Customer(812903901, faker.leagueOfLegends().champion(), 2);
        Customer customer2 = new Customer(736453901, faker.leagueOfLegends().champion(), 1);
        Customer customer3 = new Customer(5872355, faker.leagueOfLegends().champion(), 3);
        Customer customer4 = new Customer(4587215, faker.leagueOfLegends().champion(), 2);

        Product book1 = new Product(1452365874, "That Hideous Strength", "Books", 50);
        Product book2 = new Product(1723423874, "A Song Of Ice and Fire", "Books", 178.50);
        Product book3 = new Product(1552365874, "Six of Crows", "Books", 101.29);
        Product book4 = new Product(345236584, "The Witcher", "Books", 55);

        Product boys1 = new Product(552365874, "Healing Potion", "Boys", 50);
        Product boys2 = new Product(952365874, "Skateboard", "Boys", 70.40);
        Product boys3 = new Product(1252365874, "Playstation 5", "Boys", 559.99);

        Product baby1 = new Product(65523674, "Diaper", "Baby", 20.50);
        Product baby2 = new Product(74523874, "Baby Bottle", "Baby", 40.12);
        Product baby3 = new Product(95865874, "Baby Oil", "Baby", 105);

        List<Product> productsInStock = new ArrayList<>();
        Collections.addAll(productsInStock, book1, book2, book3, book4, boys1, boys2, boys3, baby1, baby2, baby3);

        List<Product> itemsOrder1 = new ArrayList<>();
        Collections.addAll(itemsOrder1, book1, boys1, baby1);
        List<Product> itemsOrder2 = new ArrayList<>();
        Collections.addAll(itemsOrder2, book2, boys3, boys2);
        List<Product> itemsOrder3 = new ArrayList<>();
        Collections.addAll(itemsOrder3, book4, baby2, baby3);
        List<Product> itemsOrder4 = new ArrayList<>();
        Collections.addAll(itemsOrder4, book1, boys2);

        Order order1 = new Order(19293303, "Delivered", order1Date, delivery1Date, itemsOrder1, customer1);
        Order order2 = new Order(3654303, "Delivered Faster", order2Date, delivery2Date, itemsOrder2, customer2);
        Order order3 = new Order(984123, "Refunded", order3Date, delivery3Date, itemsOrder3, customer3);
        Order order4 = new Order(9244123, "Delivered on site", order4Date, delivery4Date, itemsOrder4, customer4);

        System.out.println(order1.getTotal());

        List<Order> orderList = new ArrayList<>();
        Collections.addAll(orderList, order1, order2, order3, order4);

        // -------------------------------- Exercise 1 -------------------------------

        Map<Customer, List<Order>> orderByCustomer = orderList.stream().collect(Collectors.groupingBy(order -> order.getCustomer()));
        orderByCustomer.forEach((customer, orders) -> System.out.println("Customer: " + customer + ", " + orders));

        // -------------------------------- Exercise 2 -------------------------------

        Map<Customer, Double> totalForCustomer = orderList.stream().collect(Collectors.groupingBy(order -> order.getCustomer(), Collectors.summingDouble(order -> order.getTotal())));
        totalForCustomer.forEach((customer, price) -> System.out.println("Nome: " + customer + ", Totale: " + price));

        // -------------------------------- Exercise 3 -------------------------------

        List<Product> mostExpensiveOne = productsInStock.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).toList();
        mostExpensiveOne.stream().limit(4).forEach(product -> System.out.println(product));

        // -------------------------------- Exercise 4 -------------------------------

        // Media tra tutti i prodotti all'interno degli ordini.
        OptionalDouble averagePrice = orderList.stream().flatMap(order -> order.getProducts().stream()).mapToDouble(Product::getPrice).average();

        // Media del totale degli ordini
        OptionalDouble averageOrderTotal = orderList.stream()
                .mapToDouble(order -> order.getTotal())
                .average();

        System.out.println("The global average price between all products is: " + averagePrice.getAsDouble());
        System.out.println("The global average price between all products is: " + averageOrderTotal.getAsDouble());

        // -------------------------------- Exercise 5 -------------------------------

        Map<String, Double> categorySum = productsInStock.stream().collect(Collectors.groupingBy(product -> product.getCategory(), Collectors.summingDouble(product -> product.getPrice())));

        categorySum.forEach((category, average) -> System.out.println("Category: " + category + ", " + average));
    }
}
