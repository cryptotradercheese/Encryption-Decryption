public class Main {

    public static void main(String[] args) {
    // write your program here
        int count = 0;
        for (Secret secret : Secret.values()) {
            if (secret.toString().contains("STAR")) {
                count++;
            }
        }
        System.out.println(count);
    }
}

/* sample enum for inspiration
   enum Secret {
    STAR, CRASH, START, // ...
}
*/