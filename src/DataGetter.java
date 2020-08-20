import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGetter {
    int num1;
    int num2;
    char operation;
    public static boolean checkRoman;

    private static final Set<String> romanNums = new HashSet<>(Arrays.asList(
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"));

    public void readData() {
        System.out.println(
                "Введите выражение состоящее из двух целых римских или арабских чисел \n" +
                        "от 1 до 10, используя операторы: '+,-,*,/'\n" +
                        "Например: 2 + 3 или II + III"
        );
        Scanner scanner = new Scanner(System.in);
        String rawData = scanner.nextLine();
        String data = rawData.replace(" ", "");  // Решает проблему с пробелами
        Pattern operatorPattern = Pattern.compile("[+\\-*/]");  // Регулярка для оператора
        Pattern lettersOnly = Pattern.compile("[a-zA-HJ-UWYZа-яА-Я]");  // Регулярка для букв
        Pattern exRomanNumbers = Pattern.compile("[IVX]");  // Допустимые буквенные символы
        Pattern sOnly = Pattern.compile("[!@#№$;:%^&?()_=|\\\\]");  // Ловит ненужные  символы
        Matcher matcher = operatorPattern.matcher(data);

        try {
            if (sOnly.matcher(data).find()){
                throw new IllegalArgumentException(
                        "В выражении должны быть только числа от 1 до 10 и допустимые операторы (+,-,*,/)"
                );
            }
            if (lettersOnly.matcher(data).find()){
                throw new IllegalArgumentException(
                        "В выражении не должно быть букв, кроме обозначений римских чисел от I до X"
                );
            }
            if (operatorPattern.matcher(data).find()){
                assert true;
            }else {
                throw new IllegalArgumentException("Оператор отсутствует или введен некорректно!");
            }
            int extraOperator = 0;
            while (matcher.find()) extraOperator++;
            if (extraOperator > 1) {
                throw new IllegalArgumentException(
                        "Вы можете осуществлять операции только с двумя числами!"
                );
            }

            String[] group = data.split("[+\\-*/]");  // Разбивает строку на группы символов

            checkRoman = romanNums.contains(group[0]);

            // Проверяет целочисленность первого числа
            String[] checkNum1 = group[0].split("[.,]");
            if (checkNum1.length > 1) {
                throw new IllegalArgumentException("Первое число в выражении не является целым!");
            }

            // Проверяет на наличие римских чисел
            boolean checkRomanValue1 = false; // Флаг для проверки наличия римских чисел в выражении
            boolean checkExRomanNum1 = false; // Флаг для проверки наличия римских чисел в данных
            if (romanNums.contains(group[0])) {
                checkRomanValue1 = true;
            }
            if (exRomanNumbers.matcher(data).find()) {
                checkExRomanNum1 = true;
            }
            if (!checkRomanValue1 && checkExRomanNum1) {
                throw new IllegalArgumentException(
                        "Первое число в выражении выше допустимого или в выражении присутсвуют " +
                                "как римские, так и арабские числа!"
                );
            }
            if (checkRomanValue1) {  // Конвертирует римские числа в арабские
                num1 = Converter.romanToArabic(group[0]);
            } else {
                num1 = Integer.parseInt(group[0]);
            }

            // Проверяем целочисленность второго числа
            String[] checkNum2 = group[1].split("[.,]");
            if (checkNum2.length > 1) {
                throw new IllegalArgumentException("Второе число в выражении не является целым!");
            }

            // Проверяет на наличие римских чисел
            boolean checkRomanValue2 = false;
            boolean checkExRomanNum2 = false;
            if (romanNums.contains(group[1])) {
                checkRomanValue2 = true;
            }
            if (exRomanNumbers.matcher(data).find()) {
                checkExRomanNum2 = true;
            }
            if (!checkRomanValue2 && checkExRomanNum2) {
                throw new IllegalArgumentException(
                        "Второе число в выражении выше допустимого или в выражении присутсвуют " +
                                "как римские, так и арабские числа!"
                );
            }
            if (checkRomanValue2) {  // Конвертирует римские числа в арабские
                num2 = Converter.romanToArabic(group[1]);
            } else {
                num2 = Integer.parseInt(group[1]);
            }

            if (num1 > 10 || num2 > 10) {
                throw new IllegalArgumentException("Число должно быть меньше 10!");
            }
            if (num1 < 1 || num2 < 1) {
                throw new IllegalArgumentException("Число должно быть больше 0!");
            }

            operation = data.charAt(group[0].length());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
