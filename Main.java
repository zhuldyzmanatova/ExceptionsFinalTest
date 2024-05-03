import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в следующем формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();
        scanner.close();

        try {
            UserInformation userInformation = new UserInformation(input);
            userInformation.writeToFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
