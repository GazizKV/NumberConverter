import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static HashMap<String, Integer> baseMapToDecimal = new HashMap<>();
    public static HashMap<String, String> baseMapFromDecimal = new HashMap<>();

    static {
        baseMapFromDecimal.put("0", "0");
        baseMapFromDecimal.put("1", "1");
        baseMapFromDecimal.put("2", "2");
        baseMapFromDecimal.put("3", "3");
        baseMapFromDecimal.put("4", "4");
        baseMapFromDecimal.put("5", "5");
        baseMapFromDecimal.put("6", "6");
        baseMapFromDecimal.put("7", "7");
        baseMapFromDecimal.put("8", "8");
        baseMapFromDecimal.put("9", "9");
        baseMapFromDecimal.put("10", "a");
        baseMapFromDecimal.put("11", "b");
        baseMapFromDecimal.put("12", "c");
        baseMapFromDecimal.put("13", "d");
        baseMapFromDecimal.put("14", "e");
        baseMapFromDecimal.put("15", "f");
        baseMapFromDecimal.put("16", "g");
        baseMapFromDecimal.put("17", "h");
        baseMapFromDecimal.put("18", "i");
        baseMapFromDecimal.put("19", "j");
        baseMapFromDecimal.put("20", "k");
        baseMapFromDecimal.put("21", "l");
        baseMapFromDecimal.put("22", "m");
        baseMapFromDecimal.put("23", "n");
        baseMapFromDecimal.put("24", "o");
        baseMapFromDecimal.put("25", "p");
        baseMapFromDecimal.put("26", "q");
        baseMapFromDecimal.put("27", "r");
        baseMapFromDecimal.put("28", "s");
        baseMapFromDecimal.put("29", "t");
        baseMapFromDecimal.put("30", "u");
        baseMapFromDecimal.put("31", "v");
        baseMapFromDecimal.put("32", "w");
        baseMapFromDecimal.put("33", "x");
        baseMapFromDecimal.put("34", "y");
        baseMapFromDecimal.put("35", "z");

        baseMapToDecimal.put("0", 0);
        baseMapToDecimal.put("1", 1);
        baseMapToDecimal.put("2", 2);
        baseMapToDecimal.put("3", 3);
        baseMapToDecimal.put("4", 4);
        baseMapToDecimal.put("5", 5);
        baseMapToDecimal.put("6", 6);
        baseMapToDecimal.put("7", 7);
        baseMapToDecimal.put("8", 8);
        baseMapToDecimal.put("9", 9);
        baseMapToDecimal.put("a", 10);
        baseMapToDecimal.put("b", 11);
        baseMapToDecimal.put("c", 12);
        baseMapToDecimal.put("d", 13);
        baseMapToDecimal.put("e", 14);
        baseMapToDecimal.put("f", 15);
        baseMapToDecimal.put("g", 16);
        baseMapToDecimal.put("h", 17);
        baseMapToDecimal.put("i", 18);
        baseMapToDecimal.put("j", 19);
        baseMapToDecimal.put("k", 20);
        baseMapToDecimal.put("l", 21);
        baseMapToDecimal.put("m", 22);
        baseMapToDecimal.put("n", 23);
        baseMapToDecimal.put("o", 24);
        baseMapToDecimal.put("p", 25);
        baseMapToDecimal.put("q", 26);
        baseMapToDecimal.put("r", 27);
        baseMapToDecimal.put("s", 28);
        baseMapToDecimal.put("t", 29);
        baseMapToDecimal.put("u", 30);
        baseMapToDecimal.put("v", 31);
        baseMapToDecimal.put("w", 32);
        baseMapToDecimal.put("x", 33);
        baseMapToDecimal.put("y", 34);
        baseMapToDecimal.put("z", 35);
    }

    public static void main(String[] args) {
        toStar();
    }

    private static void toStar() {
        System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
        String inputNumberFromToOrExit = scanner.nextLine();
        if (inputNumberFromToOrExit.equals("/exit")) {
            scanner = null;
            System.exit(0);
        } else {
            toConverter(inputNumberFromToOrExit);
        }
    }

    private static void toConverter(String inputNumberFromToOrExit) {
        String[] bases = inputNumberFromToOrExit.split(" ");
        while (true) {
            System.out.printf("Enter number in base %s to convert to base %s (To go back type /back) ", bases[0], bases[1]);
            String numberBeforeConvert = scanner.nextLine();
            if (numberBeforeConvert.equals("/back")) {
                break;
            } else {
                String numberAfterConvert = conversion(numberBeforeConvert, bases[0], bases[1]);
                System.out.println("Conversion result: " + numberAfterConvert);
            }
        }
        toStar();
    }

    private static String conversion(String numberBeforeConvert, String baseFrom, String baseTo) {

        String decimalNumber = baseToDecimal(numberBeforeConvert, baseFrom);
        String result = fromDecimalToBase(decimalNumber, baseTo);
        return result;
    }

    private static String baseToDecimal(String numberBeforeConvert, String baseFrom) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal bigIntBaseFrom = new BigDecimal(String.valueOf(baseFrom));
        String fractionalPart = "";
        if (numberBeforeConvert.contains(".")) {
            fractionalPart = numberBeforeConvert.substring(numberBeforeConvert.indexOf(".") + 1);
            numberBeforeConvert = numberBeforeConvert.substring(0, numberBeforeConvert.indexOf("."));
            fractionalPart = ".".concat(fractionalBasaeToDecimalBase(fractionalPart, baseFrom));
        }
        int length = numberBeforeConvert.length();
        for (int i = length - 1; i >= 0; i--) {
            Integer decimalNumber = baseMapToDecimal.get(Character.toString(numberBeforeConvert.charAt(length - 1 - i)));
            result = result
                    .add(bigIntBaseFrom.pow(i)
                            .multiply(new BigDecimal(String.valueOf(decimalNumber))));
        }
        return result.toString().concat(fractionalPart);
    }

    private static String fractionalBasaeToDecimalBase(String fractionalPart, String baseFrom) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal nextBase = new BigDecimal(baseFrom);
        BigDecimal base = new BigDecimal(baseFrom);
        int length = fractionalPart.length();
        Pattern pattern = Pattern.compile("[a-z1-9]");
        Matcher matcher = pattern.matcher(fractionalPart);
        if (!matcher.find()) {
            return "00000";
        }
        for (int i = 0; i < length; i++) {
            String nextKey = Character.toString(fractionalPart.charAt(i));
            BigDecimal nextBigDecimal = new BigDecimal(baseMapToDecimal.get(nextKey));
            result = result.add(nextBigDecimal.divide(nextBase, 20, RoundingMode.FLOOR));
            nextBase = nextBase.multiply(base);
        }
        return String.valueOf(result).substring(2);
    }


    private static String fromDecimalToBase(String decimalNumber, String baseTo) {
        StringBuilder stringBuilder = new StringBuilder();
        BigDecimal bigIntBaseTo = new BigDecimal(baseTo);
        String fractionalPart = "";
        if (decimalNumber.contains(".")) {
            fractionalPart = decimalNumber.substring(decimalNumber.indexOf(".") + 1);
            decimalNumber = decimalNumber.substring(0, decimalNumber.indexOf("."));
            fractionalPart = ".".concat(fractionalDecimalPartToNewBase(fractionalPart, baseTo));
        }
        BigDecimal bigIntDecimal = new BigDecimal(decimalNumber);
        if (bigIntDecimal.equals(BigDecimal.ZERO)) {
            stringBuilder.append(bigIntDecimal);
        }
        while (!bigIntDecimal.equals(BigDecimal.ZERO)) {
            BigDecimal remainder = bigIntDecimal.remainder(bigIntBaseTo);
            stringBuilder.append(baseMapFromDecimal.get(remainder.toString()));
            bigIntDecimal = bigIntDecimal.divide(bigIntBaseTo, 0, RoundingMode.DOWN);
        }
        return stringBuilder.reverse().toString().concat(fractionalPart);
    }

    private static String fractionalDecimalPartToNewBase(String fractionalPart, String baseTo) {
        StringBuilder result = new StringBuilder();
        BigDecimal base = new BigDecimal(baseTo);
        BigDecimal next;
        String resString = "";
        if (fractionalPart.equals("0")) {
            result.append("00000");
        } else {
            BigDecimal fraction = new BigDecimal("0.".concat(fractionalPart));
            while (!fraction.equals(BigDecimal.ZERO) && result.length() < 5) {
                next = fraction.multiply(base);
                resString = next.toString();
                result.append(baseMapFromDecimal.get(resString.substring(0, resString.indexOf("."))));
                String franctionPart = resString.substring(resString.indexOf(".") + 1);
                fraction = new BigDecimal(franctionPart);
                if (fraction.equals(BigDecimal.ZERO)) {
                    break;
                }
                fraction = new BigDecimal("0.".concat(resString.substring(resString.indexOf(".") + 1)));
            }
        }
        String str = result.toString();
        if (str.length() < 5) {
            while (str.length() < 5) {
                str = str.concat("0");
            }
        }
        return str;
    }
}
