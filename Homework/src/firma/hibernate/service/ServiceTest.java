package firma.hibernate.service;

import firma.hibernate.entity.*;
import firma.hibernate.service.account.AccountService;
import firma.hibernate.service.client.ClientService;
import firma.hibernate.service.employee.EmployeeService;
import firma.hibernate.service.order.OrderService;
import firma.hibernate.service.orderPosition.OrderPositionService;
import firma.hibernate.service.product.ProductService;
import firma.hibernate.service.productType.ProductTypeService;
import firma.support.EmployeeRols;
import firma.support.OrderStatus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ServiceTest {
    public static void main(String[] args) throws ParseException {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"firma/Config.xml"});
        EmployeeService employeeService = context.getBean(EmployeeService.class);
        AccountService accountService = context.getBean(AccountService.class);
        ProductTypeService productTypeService = context.getBean(ProductTypeService.class);
        ProductService productService = context.getBean(ProductService.class);
        OrderService orderService = context.getBean(OrderService.class);
        ClientService clientService = context.getBean(ClientService.class);
        OrderPositionService orderPositionService = context.getBean(OrderPositionService.class);

//      create Accounts
        AccountEmployee account1 = new AccountEmployee("login1", "password1");
        AccountEmployee account2 = new AccountEmployee("login2", "password2");
        AccountEmployee account3 = new AccountEmployee("login3", "password3");
        AccountEmployee account4 = new AccountEmployee("login4", "password4");
        AccountEmployee account5 = new AccountEmployee("login5", "password5");
        AccountEmployee account6 = new AccountEmployee("login6", "password6");
        AccountEmployee account7 = new AccountEmployee("login7", "password7");
        AccountEmployee account8 = new AccountEmployee("login8", "password8");
        AccountEmployee account9 = new AccountEmployee("login9", "password9");
        AccountEmployee account10 = new AccountEmployee("login10", "password10");

//      create Employees
        EmployeeFirm employee1 = new EmployeeFirm("Surname1", "Name1", "LastName1", new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2001"), new SimpleDateFormat("dd.MM.yyy").parse("01.01.2017"), EmployeeRols.ADMINISTRATOR, account1);
        EmployeeFirm employee2 = new EmployeeFirm("Surname2", "Name2", "LastName2", new SimpleDateFormat("dd.MM.yyyy").parse("02.02.2002"), new SimpleDateFormat("dd.MM.yyy").parse("02.02.2017"), EmployeeRols.STORAGE_MANAGER, account2);
        EmployeeFirm employee3 = new EmployeeFirm("Surname3", "Name3", "LastName3", new SimpleDateFormat("dd.MM.yyyy").parse("03.03.2003"), new SimpleDateFormat("dd.MM.yyy").parse("03.03.2017"), EmployeeRols.STORAGE_MANAGER, account3);
        EmployeeFirm employee4 = new EmployeeFirm("Surname4", "Name4", "LastName4", new SimpleDateFormat("dd.MM.yyyy").parse("04.04.2004"), new SimpleDateFormat("dd.MM.yyy").parse("04.04.2017"), EmployeeRols.SALES_MANAGER, account4);
        EmployeeFirm employee5 = new EmployeeFirm("Surname5", "Name5", "LastName5", new SimpleDateFormat("dd.MM.yyyy").parse("05.05.2005"), new SimpleDateFormat("dd.MM.yyy").parse("05.05.2017"), EmployeeRols.SALES_MANAGER, account5);
        EmployeeFirm employee6 = new EmployeeFirm("Surname6", "Name6", "LastName6", new SimpleDateFormat("dd.MM.yyyy").parse("06.06.2006"), new SimpleDateFormat("dd.MM.yyy").parse("06.06.2017"), EmployeeRols.SALES_MANAGER, account6);
        EmployeeFirm employee7 = new EmployeeFirm("Surname7", "Name7", "LastName7", new SimpleDateFormat("dd.MM.yyyy").parse("07.07.2007"), new SimpleDateFormat("dd.MM.yyy").parse("07.07.2017"), EmployeeRols.SALES_MANAGER, account7);
        EmployeeFirm employee8 = new EmployeeFirm("Surname8", "Name8", "LastName8", new SimpleDateFormat("dd.MM.yyyy").parse("08.08.2008"), new SimpleDateFormat("dd.MM.yyy").parse("08.08.2017"), EmployeeRols.SALES_MANAGER, account8);
        EmployeeFirm employee9 = new EmployeeFirm("Surname9", "Name9", "LastName9", new SimpleDateFormat("dd.MM.yyyy").parse("09.09.2009"), new SimpleDateFormat("dd.MM.yyy").parse("09.09.2017"), EmployeeRols.SALES_MANAGER, account9);
        EmployeeFirm employee10 = new EmployeeFirm("Surname11", "Name10", "LastName10", new SimpleDateFormat("dd.MM.yyyy").parse("10.10.2010"), new SimpleDateFormat("dd.MM.yyy").parse("10.10.2017"), EmployeeRols.SALES_MANAGER, account10);

//      create Client
        Client client1 = new Client("Стіпашка", "Гаврило", "Свиридонович", "044-234-44-56", "sgs@ukr.net");
        Client client2 = new Client("Мойша", "Авраам", "Петрович", "095-204-40-96", "moysha@gmail.com");
        Client client3 = new Client("Миша", "Марина", "Петрівна", "093-603-12-82", "mysha@ex.ua");
        Client client4 = new Client("Стрєлка", "Евредіка", "Матвєєвна", "050-903-12-30", "sgs@mail.ru");
        Client client5 = new Client("Рабіновіч", "Сара", "Марковна", "096-06-50-92", "sara@rambler.ru");

//      create ProductType
        ProductType car = new ProductType("car", "Автомобілі");
        ProductType building = new ProductType("building", "Нерухомість");
        ProductType yaht = new ProductType("yaht", "Яхти");

//        create Products
        Product bmw = new Product("bmw-01", "BMW", 12000.00, 10, car);
        Product kia = new Product("kia-01", "KIA", 8000.00, 6, car);
        Product deo = new Product("deo-01", "DEO", 2000.00, 40, car);
        Product appartment = new Product("app-01", "Трикімнатна квартира", 45000.00, 3, building);
        Product office = new Product("off-01", "Офіс, 200 м2", 60000.00, 2, building);
        Product house = new Product("h-01", "Заміський котедж, 150 м2", 50000.00, 6, building);
        Product yaht1 = new Product("y-01", "Яхта, 10 м", 20000.00, 5, yaht);
        Product yaht2 = new Product("y-02", "Яхта, 20 м", 40000.00, 3, yaht);
        Product yaht3 = new Product("y-03", "Яхта, 30 м", 60000.00, 1, yaht);

//        Create Orders
//        Order order1 = new Order("000001", new SimpleDateFormat("dd.MM.yyyy").parse("01.08.2017"), OrderStatus.NEW, " ", employee4, client1);
//        Order order2 = new Order("000002", new SimpleDateFormat("dd.MM.yyyy").parse("05.08.2017"), OrderStatus.NEW, " ", employee5, client2);
//        Order order3 = new Order("000003", new SimpleDateFormat("dd.MM.yyyy").parse("06.08.2017"), OrderStatus.NEW, " ", employee6, client3);
//        Order order4 = new Order("000004", new SimpleDateFormat("dd.MM.yyyy").parse("08.08.2017"), OrderStatus.NEW, " ", employee4, client4);
//        Order order5 = new Order("000005", new SimpleDateFormat("dd.MM.yyyy").parse("10.08.2017"), OrderStatus.NEW, " ", employee4, client5);


        Order order1 = new Order("000001", new SimpleDateFormat("dd.MM.yyyy").parse("01.08.2017"), OrderStatus.PROCESSED_BY_SMANAGER, " ", client1);
        Order order2 = new Order("000002", new SimpleDateFormat("dd.MM.yyyy").parse("05.08.2017"), OrderStatus.PROCESSED_BY_SMANAGER, " ", client2);
        Order order3 = new Order("000003", new SimpleDateFormat("dd.MM.yyyy").parse("06.08.2017"), OrderStatus.PROCESSED_BY_SMANAGER, " ", client3);
        Order order4 = new Order("000004", new SimpleDateFormat("dd.MM.yyyy").parse("08.08.2017"), OrderStatus.PROCESSED_BY_SMANAGER, " ", client4);
        Order order5 = new Order("000005", new SimpleDateFormat("dd.MM.yyyy").parse("10.08.2017"), OrderStatus.PROCESSED_BY_SMANAGER, " ", client5);


        //        Create Order positions
        OrderPosition orderPosition1 = new OrderPosition(2, bmw);
        OrderPosition orderPosition2 = new OrderPosition(1, appartment);
        OrderPosition orderPosition3 = new OrderPosition(1, yaht1);

        OrderPosition orderPosition4 = new OrderPosition(5,  kia);
        OrderPosition orderPosition5 = new OrderPosition(1,  office);
        OrderPosition orderPosition6 = new OrderPosition(1, yaht2);

        OrderPosition orderPosition7 = new OrderPosition(25,  deo);
        OrderPosition orderPosition8 = new OrderPosition(1, house);
        OrderPosition orderPosition9 = new OrderPosition(1,  yaht3);

        OrderPosition orderPosition10 = new OrderPosition(15, kia);
        OrderPosition orderPosition11 = new OrderPosition(3, appartment);
        OrderPosition orderPosition12 = new OrderPosition(1,  yaht2);
//
        OrderPosition orderPosition13 = new OrderPosition(2,  bmw);
        OrderPosition orderPosition14 = new OrderPosition(11,  house);
        OrderPosition orderPosition15 = new OrderPosition(5, yaht1);

        orderPosition1.setOrder(order1);
        orderPosition2.setOrder(order1);
        orderPosition3.setOrder(order1);

        orderPosition4.setOrder(order2);
        orderPosition5.setOrder(order2);
        orderPosition6.setOrder(order2);

        orderPosition7.setOrder(order3);
        orderPosition8.setOrder(order3);
        orderPosition9.setOrder(order3);

        orderPosition10.setOrder(order4);
        orderPosition11.setOrder(order4);
        orderPosition12.setOrder(order4);

        orderPosition13.setOrder(order5);
        orderPosition14.setOrder(order5);
        orderPosition15.setOrder(order5);


// Save
// Client in DB
        clientService.create(client1);
        clientService.create(client2);
        clientService.create(client3);
        clientService.create(client4);
        clientService.create(client5);

//        Save ProductType in DB
        productTypeService.create(car);
        productTypeService.create(building);
        productTypeService.create(yaht);

//        Save Product in DB
        productService.create(bmw);
        productService.create(kia);
        productService.create(deo);
        productService.create(appartment);
        productService.create(office);
        productService.create(house);
        productService.create(yaht1);
        productService.create(yaht2);
        productService.create(yaht3);

//      seve Accounts in DB
        accountService.create(account1);
        accountService.create(account2);
        accountService.create(account3);
        accountService.create(account4);
        accountService.create(account5);
        accountService.create(account6);
        accountService.create(account7);
        accountService.create(account8);
        accountService.create(account9);
        accountService.create(account10);

//      save Employees in DB
        employeeService.create(employee1);
        employeeService.create(employee2);
        employeeService.create(employee3);
        employeeService.create(employee4);
        employeeService.create(employee5);
        employeeService.create(employee6);
        employeeService.create(employee7);
        employeeService.create(employee8);
        employeeService.create(employee9);
        employeeService.create(employee10);

        Set<EmployeeFirm> set = new LinkedHashSet<>();
        Set<EmployeeFirm> set1 = new LinkedHashSet<>();
        Set<EmployeeFirm> set2 = new LinkedHashSet<>();
        Set<EmployeeFirm> set3 = new LinkedHashSet<>();
        Set<EmployeeFirm> set4 = new LinkedHashSet<>();

        set3.add(employee1);
        set3.add(employee6);
        set4.add(employee2);
        set4.add(employee9);
        set4.add(employee7);
        set.add(employee5);
        set.add(employee6);
        set1.add(employee10);
        set1.add(employee9);
        set2.add(employee5);
        set2.add(employee3);
        set2.add(employee6);


        order1.setManagers(set3);
        order2.setManagers(set4);
        order3.setManagers(set2);
        order4.setManagers(set1);
        order5.setManagers(set);

        //        Save Orders in DB
        orderService.create(order1);
        orderService.create(order2);
        orderService.create(order3);
        orderService.create(order5);
        orderService.create(order4);

//        Save Order positions in DB
        orderPositionService.create(orderPosition1);
        orderPositionService.create(orderPosition2);
        orderPositionService.create(orderPosition3);
        orderPositionService.create(orderPosition4);
        orderPositionService.create(orderPosition5);
        orderPositionService.create(orderPosition6);
        orderPositionService.create(orderPosition7);
        orderPositionService.create(orderPosition8);
        orderPositionService.create(orderPosition9);
        orderPositionService.create(orderPosition10);
        orderPositionService.create(orderPosition11);
        orderPositionService.create(orderPosition12);
        orderPositionService.create(orderPosition13);
        orderPositionService.create(orderPosition14);
        orderPositionService.create(orderPosition15);

        System.out.println(employeeService.readByAccount(account6));

        List<AccountEmployee> list = accountService.getAll();
        for (AccountEmployee el : list) {
            System.out.println(el.getLogin() + " " + el.getPassword());
        }
//        System.out.println();
//        List<EmployeeFirm> list1 = employeeService.getAll();
//        for (EmployeeFirm el : list1) {
//            System.out.println(el.getLastName() + " " + el.getSurname());
//        }
//        System.out.println("----------------------------------------------------------------");
//        List<Product> list1 = productService.getByProductType(building);
//        for (Product el : list1) {
//            System.out.println(el.getProductCode() + " " + el.getProductName());
//        }
//        System.out.println("----------------------------------------------------------------");
//        List<OrderPosition> list2 = orderPositionService.getOrderPositionByOrder(order3);
//        for (OrderPosition el : list2) {
//            System.out.println(el.getPositionName() + " " + el.getProductAmount() + " " + el.getTotalPriceOfProduct());
//        }
//        employeeService.delete(employee9);
//        employeeService.delete(employee10);

//        оця фігня не працює, звязок мені то мені
//        System.out.println("----------------------------------------------------------------");
//        List<Order> list = orderService.getOrdersByClient(client1);
//        for(Order el: list){
//            System.out.println(el.getNumber());
//        }
//        System.out.println("----------------------------------------------------------------");
//        List<Order> list1 = orderService.getOrdersByEmployee(employee6);
//        for(Order el: list){
//            System.out.println(el.getNumber());
//        }
    }
}
