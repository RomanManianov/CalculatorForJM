public class GetResult {

    public static void calcResult() {
        DataGetter getter = new DataGetter();

        while (true) {
            try {
                getter.readData();
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                System.exit(0);
            }
            int result = Calculator.calc(getter.num1, getter.num2, getter.operation);
            if (DataGetter.checkRoman) {  // Если в выражении есть римские числа, конвертирует рез. в римские
                String romanResult = Converter.arabicToRoman(result);
                System.out.println(romanResult);
            }else {
                System.out.println(result);

            }
        }
    }
}
