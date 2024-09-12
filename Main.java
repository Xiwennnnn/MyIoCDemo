@XiWenScan("Service")
public class Main {
    public static void main(String[] args) {
        XiwenContext ctx = XiwenBootStart.run(Main.class);
        UserService userService = (UserService)ctx.getBean("UserService");
    }
}
