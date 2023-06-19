import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static int[] prices = {50, 100, 200};
    private static String[] products = {"Хлеб", "Молоко", "Яйца"};
    private static File newFile = new File("basket.txt");

    public static void main(String[] args) {
        Basket basket;
        if (newFile.exists()) {
            basket = Basket.loadFromTxtFile(newFile);
            basket.printCart();
        } else {
            basket = new Basket(prices, products);
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Список возможных товаров для покупки:");
            for (int i = 0; i < products.length; i++) {
                System.out.printf("%d. %s - %d руб/шт.\n", (i + 1), products[i], prices[i]);
            }
            System.out.println("Выберите товар и количество или введите \"end\"\n");
            String choice = scanner.nextLine();

            if ("end".equals(choice)) {
                basket.printCart();
                break;
            } else {
                String[] arrChoice = choice.split(" ");
                if (arrChoice.length != 2) {
                    System.out.println("Неверный формат ввода. Попробуйте ещё раз.\n");
                } else {
                    basket.addToCart(Integer.parseInt(arrChoice[0]), Integer.parseInt(arrChoice[1]));

                    if (newFile.exists()) {
                        basket.saveTxt(newFile);
                    } else {
                        try {
                            newFile.createNewFile();
                            basket.saveTxt(newFile);
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        scanner.close();
    }
}