package videoMultiThreadSerializable;

public class Main {
    static int port1 = 6000;
    static int port2 = 6001;

    public static void main(String[] args) throws Exception {
        new GrabberShow(port2, "AAAA");
//        new GrabberShow(port2, "BBBB");
    }
}