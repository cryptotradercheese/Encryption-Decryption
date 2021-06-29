import java.util.Scanner;
import java.util.regex.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        final Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        int index = -1;
        Matcher matcher = Pattern.compile("(?i)the").matcher(sentence);
        if (matcher.find()) {
            index = matcher.start();
        }

        System.out.println(index);
        scanner.close();
    }
}