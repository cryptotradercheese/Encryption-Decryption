import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here
        final Scanner scanner = new Scanner(System.in);

        ArrayList<Integer> numbers = new ArrayList<>();
        for (String i : scanner.nextLine().split(" ")) {
            numbers.add(Integer.parseInt(i));
        }

        int n = scanner.nextInt();

        int minDif = Math.abs(n - numbers.get(0));
        for (int item : numbers) {
            if (minDif > Math.abs(n - item)) {
                minDif = Math.abs(n - item);
            }
        }

        ArrayList<Integer> closest = new ArrayList<>();

        for (int item : numbers) {
            if (minDif == Math.abs(n - item)) {
                closest.add(item);
            }
        }

        Object[] sortedClosest = closest.toArray();
        Arrays.sort(sortedClosest);
        for (Object item : sortedClosest) {
            System.out.print(item + " ");
        }
    }
}