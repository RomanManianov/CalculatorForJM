import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Converter {

    public enum RomanNum {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private final int value;

        RomanNum(int value) {
            this.value = value;
        }

        public int toInt() {
            return value;
        }

        public static List<RomanNum> getSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNum e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    // Метод конвертирует римские числа в арабские
    public static int romanToArabic(String data) {
        String romanNum = data.toUpperCase();
        int result = 0;

        List<RomanNum> romanNums = RomanNum.getSortedValues();

        int i = 0;

        while ((romanNum.length() > 0) && (i < romanNums.size())) {
            RomanNum symbol = romanNums.get(i);
            if (romanNum.startsWith(symbol.name())) {
                result += symbol.toInt();
                romanNum = romanNum.substring(symbol.name().length());
            }else {
                i++;
            }
        }
        if (romanNum.length() > 0) {
            throw  new IllegalArgumentException(
                    data + " Некорректное римское число!"
            );
        }

        return result;
    }

    // Метод конвертирует арабские числа в римские
    public static String arabicToRoman(int number) {
        if ((number < 0) || (number > 100)) {
            throw new IllegalArgumentException(
                    number + ": результат  меньше 0 а такого в римских числах быть не должно!"
            );
        } else if (number == 0) {
            throw new IllegalArgumentException(
                    "У римлян не было ноля, но у вас получился НОЛЬ!"
            );
        }

        List<RomanNum> romanNums = RomanNum.getSortedValues();

        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while ((number > 0) && (i < romanNums.size())) {
            RomanNum currentSymbol = romanNums.get(i);
            if (currentSymbol.toInt() <= number) {
                stringBuilder.append(currentSymbol.name());
                number -= currentSymbol.toInt();
            } else {
                i++;
            }
        }

        return stringBuilder.toString();
    }
}