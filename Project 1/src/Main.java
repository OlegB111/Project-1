import java.io.IOException;
import java.util.TreeMap;
import java.util.Scanner;

class Main {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("Введите выражение: ");
        String input = s.nextLine();

        calc(input);

    }

        public static String calc (String input) throws IOException {

            Converter converter = new Converter();
            String[] znaki = {"+", "-", "/", "*"};
            String[] znaki2 = {"\\+", "-", "/", "\\*"}; // этот массив нужен для разделения прифм. выражения

            // Ищу арифметическое действие:
            int indexZnaka = -1;
            for (int i = 0; i < znaki.length; i++) {
                if (input.contains(znaki[i])) {
                    indexZnaka = i;
                    break;
                }
            }
            // Если не нахожу арифметическое действие, выбрасываю исключение и завершаю программу:
            if (indexZnaka == -1) {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Не правильное выражение");
                }
            }
            // ***** ИСПРАВЛЕНИЕ 1. Проверяю кол-во арифмитич. знаков и если оно больше одного выбрасываю исключение и завершаю программу:
            char[] symvoly = input.toCharArray();
            int j = 0;
            for (int k = 0; k < symvoly.length; k++) {
                if (symvoly[k] == '+' || symvoly[k] == '-' || symvoly[k] == '/' || symvoly[k] == '*') {
                    j = j + 1;
                }
            }
            if (j > 1) {
                throw new IOException("Неправильное выражение, больше одного арифмитич. знака");
            }
            // *****

            // Делю строку по найденному арифм. знаку:
            String[] data = input.split(znaki2[indexZnaka]);

            // Определяю находятся ли числа в одном формате (оба римские или оба арабские):
            if (converter.formatCheck(data[0]) == converter.formatCheck(data[1])) {
                int a, b;
                // Определяю римские ли это числа:
                boolean formatCheck = converter.formatCheck(data[0]);
                if (formatCheck) {

// ***** ИСПРАВЛЕНИЕ 2. Если числа римские то проверяю что они корректные и если не правильные то выбрасываю исключение и завершаю программу:
                    if (data[0].equals("I") || data[0].equals("II") || data[0].equals("III") || data[0].equals("IV") || data[0].equals("V") || data[0].equals("VI") || data[0].equals("VII") || data[0].equals("VIII") || data[0].equals("IX") || data[0].equals("X")) {
                    } else {
                        throw new IOException("Не правильное выражение, не правильный римский знак");
                    }
                    if (data[1].equals("I") || data[1].equals("II") || data[1].equals("III") || data[1].equals("IV") || data[1].equals("V") || data[1].equals("VI") || data[1].equals("VII") || data[1].equals("VIII") || data[1].equals("IX") || data[1].equals("X")) {
                    } else {
                        throw new IOException("Не правильное выражение, не правильный римский знак");
                    }
// *****
                    // Если числа римские то конвертирую их в арабские:
                    a = converter.rimToArab(data[0]);
                    b = converter.rimToArab(data[1]);
                    // Проверяю чтобы при вычитание римских чисел не было отрицательного числаи и если оно отрицатльное то выбрасываю исключение и завершаю программу:
                    if (a <= b && indexZnaka == 1) {
                        throw new IOException("Неправильное выражение, при вычитание римские цифры не могут быть меньше 1");
                    }
                } else {
                    // Если числа арабские то конвертирую их из строки в число
                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                }
                // Сравниваю что числа от 1 до 10 и если они не в этом диапазоне то выбрасываю исключение и завершаю программу:
                if ((a > 10 || a < 1) || (b > 10 || b < 1)) {
                    throw new IOException("Неправильное выражение, аргументы должны быть от 1 до 10");
                }
                // Выполняю арифметич. действие:
                int resultat;
                switch (znaki[indexZnaka]) {
                    case "+":
                        resultat = a + b;
                        break;
                    case "-":
                        resultat = a - b;
                        break;
                    case "*":
                        resultat = a * b;
                        break;
                    default:
                        resultat = a / b;
                        break;
                }
                // Если числа были римские то возвращаю результат в римском числе:
                if (formatCheck) {
                    System.out.println(converter.arabToRim(resultat));
                    return converter.arabToRim(resultat);
                }
                // Если числа были арабские то возвращаю результат в арабском числе:
                else {
                    System.out.println(resultat);
                    String result = String.valueOf(resultat);
                    return (result);
                }
            } else {
                throw new IOException("Неправильное выражение, числа должны быть в одном формате");
            }
        }
    }

// CONVERTER:

    class Converter {
        TreeMap<Character, Integer> rimMap = new TreeMap<>();
        TreeMap<Integer, String> arabMap = new TreeMap<>();

        // Карта для конвертации арабских чисел в римские
        Converter() {
            rimMap.put('I', 1);
            rimMap.put('V', 5);
            rimMap.put('X', 10);

            // Карта для конвертации риских чисел в арабские
            arabMap.put(100, "C");
            arabMap.put(90, "XC");
            arabMap.put(50, "L");
            arabMap.put(40, "XL");
            arabMap.put(10, "X");
            arabMap.put(9, "IX");
            arabMap.put(5, "V");
            arabMap.put(4, "IV");
            arabMap.put(1, "I");
        }

        // Метод проверки формата числа:
        boolean formatCheck(String number) {
            return rimMap.containsKey(number.charAt(0));
        }

        // Метод перевода арабских чисел в римские:
        String arabToRim(int number) {
            String rim = "";
            int arabianKey;
            do {
                arabianKey = arabMap.floorKey(number);
                rim += arabMap.get(arabianKey);
                number -= arabianKey;
            } while (number != 0);
            return rim;
        }

        // Метод перевода римских чисел в арабские:
        int rimToArab(String s) {
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int resultat = rimMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = rimMap.get(arr[i]);
                if (arabian < rimMap.get(arr[i + 1])) {
                    resultat -= arabian;
                } else {
                    resultat += arabian;
                }
            }
            return resultat;
        }
    }




