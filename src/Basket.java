import java.io.*;
import java.util.Arrays;

public class Basket {
    protected int[] prices;
    protected String[] products;
    protected int[] shoppingList;
    protected int sum;

    public Basket() {
    }

    //конструктор, принимающий массив цен и названий продуктов;
    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        shoppingList = new int[products.length];
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        if (productNum < 1 || productNum > products.length) {
            System.out.println("Нет такого продукта. Попробуйте ещё раз.\n");
        } else if (amount < 1) {
            System.out.println("Количество продукта должно быть больше либо равно 1. Попробуйте ещё раз.\n");
        } else {
            shoppingList[productNum - 1] += amount;
        }
    }

    //метод вывода на экран покупательской корзины.
    public void printCart() {
        System.out.println("Ваша корзина:");
        for (int i = 0; i < shoppingList.length; i++) {
            if (shoppingList[i] != 0) {
                System.out.printf("%s - %d шт по %d руб/шт. В сумме %d руб.\n",
                        products[i], shoppingList[i], prices[i], shoppingList[i] * prices[i]);
                sum = sum + shoppingList[i] * prices[i];
            }
        }
        System.out.printf("Итого: %d руб.\n", sum);
    }

    //метод сохранения корзины в текстовый файл
    public void saveTxt(File textFile) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(textFile))) {

            bufferedWriter.write(String.join(" ", products));
            bufferedWriter.newLine();

            bufferedWriter.write(String.join(" ", Arrays.stream(prices).
                    mapToObj(String::valueOf).
                    toArray(String[]::new)));
            bufferedWriter.newLine();

            bufferedWriter.write(String.join(" ", Arrays.stream(shoppingList).
                    mapToObj(String::valueOf).
                    toArray(String[]::new)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //статический метод восстановления объекта корзины из текстового файла
    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String[] productsStr = bufferedReader.readLine().split(" ");
            String[] priceStr = bufferedReader.readLine().split(" ");
            String[] shoppingListStr = bufferedReader.readLine().split(" ");

            basket.products = productsStr;
            basket.prices = new int[priceStr.length];
            basket.shoppingList = new int[shoppingListStr.length];
            for (int i = 0; i < priceStr.length; i++) {
                basket.prices[i] = Integer.parseInt(priceStr[i]);
                basket.shoppingList[i] = Integer.parseInt(shoppingListStr[i]);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }
}