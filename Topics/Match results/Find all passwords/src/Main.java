import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = scanner.nextLine();

        // write your code here
        Pattern pattern = Pattern.compile("(?i)password[ :]*([\\da-z]+)");
        Matcher matcher = pattern.matcher(text);

        boolean isFound = false;
        while (matcher.find()) {
            System.out.println(matcher.group(1));
            isFound = true;
        }

        if (!isFound) {
            System.out.print("No passwords found.");
        }
    }
}