@XiWenScan("Service")
public class Main {
    public static void main(String[] args) {
        XiwenContext ctx = XiwenBootStart.run(Main.class);
        UserService userService = (UserService)ctx.getBean("UserService");
        OrderService orderService = (OrderService)ctx.getBean("OrderService");
        System.out.println(userService.orderService);
        System.out.println(orderService.userService);
    }
}
