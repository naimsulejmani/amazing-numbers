package numbers;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class Number {

    private BigInteger number;
    private boolean isEven;
    private boolean isOdd;
    private boolean isBuzz;
    private boolean isDuck;
    private boolean isGapful;
    private boolean isPalindrome;
    private boolean isSpy;
    private boolean isSunny;

    private boolean isSquare;

    private boolean isJumping;

    private boolean isSad;
    private boolean isHappy;

    public Number(BigInteger number) {
        setNumber(number);
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        if (number.compareTo(BigInteger.ZERO) <= 0)
            throw new IllegalArgumentException();
        this.number = number;

        initializeProperties();
    }

    public boolean checkProperties(Properties property) {
        if (property == null) return true;
        switch (property) {
            case BUZZ:
                return isBuzz;
            case DUCK:
                return isDuck;
            case PALINDROMIC:
                return isPalindrome;
            case GAPFUL:
                return isGapful;
            case SPY:
                return isSpy;
            case EVEN:
                return isEven;
            case ODD:
                return isOdd;
            case SQUARE:
                return isSquare;
            case SUNNY:
                return isSunny;
            case JUMPING:
                return isJumping;
            case SAD:
                return isSad;
            case HAPPY:
                return isHappy;
            default:
                return false;
        }
    }

    public void printTrueProperties() {
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%16s is", number));

        boolean noCommas = true;

        if (isBuzz) {
            sb.append(" buzz");
            noCommas = false;
        }
        if (isDuck) {
            sb.append((noCommas ? " " : ", ") + "duck");
            noCommas = false;
        }
        if (isGapful) {
            sb.append((noCommas ? " " : ", ") + "gapful");
            noCommas = false;
        }
        if (isSpy) {
            sb.append((noCommas ? " " : ", ") + "spy");
            noCommas = false;
        }

        if (isEven) {
            sb.append((noCommas ? " " : ", ") + "even");
            noCommas = false;
        }

        if (isOdd) {
            sb.append((noCommas ? " " : ", ") + "odd");
            noCommas = false;
        }
        if (isPalindrome) {
            sb.append((noCommas ? " " : ", ") + "palindromic");
            noCommas = false;
        }
        if (isSunny) {
            sb.append((noCommas ? " " : ", ") + "sunny");
            noCommas = false;
        }
        if (isSquare) {
            sb.append((noCommas ? " " : ", ") + "square");
            noCommas = false;
        }
        if (isJumping) {
            sb.append((noCommas ? " " : ", ") + "jumping");
            noCommas = false;
        }
        if (isSad) {
            sb.append((noCommas ? " " : ", ") + "sad");
            noCommas = false;
        }
        if (isHappy) {
            sb.append((noCommas ? " " : ", ") + "happy");
            noCommas = false;
        }
        System.out.println(sb);
    }

    private void initializeProperties() {
        isBuzz = checkIsBuzzNumber(number);
        isEven = number.mod(BigInteger.TWO).equals(BigInteger.ZERO);
        isDuck = checkIsDuckNumber(number);
        isOdd = !isEven;
        isPalindrome = checkIsPalindrome(number);
        isGapful = checkIsGapful(number);
        isSpy = checkIsSpy(number);
        isSunny = checkIsSunny(number);
        isSquare = checkIsSquare(number);
        isJumping = checkIsJumping(number);
        if (!number.equals(BigInteger.ZERO)) {
            isHappy = checkIsHappy(number);
            isSad = !isHappy;
        }

    }

    private boolean checkIsHappy(BigInteger number) {
        do {
            number = checkIsHappyNumber(number);
        }
        while (!number.equals(BigInteger.ONE) && !number.equals(BigInteger.valueOf(4)));

        return number.equals(BigInteger.ONE);
    }

    private BigInteger checkIsHappyNumber(BigInteger number) {
        BigInteger sum, rem;
        sum = rem = BigInteger.ZERO;
        while (number.compareTo(BigInteger.ZERO) > 0) {
            rem = number.mod(BigInteger.TEN);
            sum = sum.add(rem.multiply(rem));
            number = number.divide(BigInteger.TEN);
        }
        return sum;
    }

    private boolean checkIsJumping(BigInteger number) {
        while (!number.equals(BigInteger.ZERO)) {
            BigInteger digit1 = number.mod(BigInteger.TEN);
            number = number.divide(BigInteger.TEN);
            if (!number.equals(BigInteger.ZERO)) {
                BigInteger digit2 = number.mod(BigInteger.TEN);

                if (!digit1.add(digit2.negate()).abs().equals(BigInteger.ONE)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIsSquare(BigInteger number) {
        double sqrt = Math.sqrt(number.intValue());
        return ((sqrt - Math.floor(sqrt)) == 0);
    }

    private boolean checkIsSunny(BigInteger number) {
        return Math.sqrt(number.intValue() + 1) % 1 == 0;
    }


    private boolean checkIsSpy(BigInteger number) {
//        if (number.compareTo(BigInteger.TEN) < 0) {
//            return false;
//        }
        BigInteger sum = BigInteger.ZERO;
        BigInteger prod = BigInteger.ONE;


        while (number.compareTo(BigInteger.ZERO) > 0) {
            BigInteger lastDigit = number.mod(BigInteger.TEN);
            if (lastDigit.equals(BigInteger.ZERO)) {
                return false;
            }
            number = number.divide(BigInteger.TEN);
            sum = sum.add(lastDigit);
            prod = prod.multiply(lastDigit);
        }
        return sum.equals(prod);
    }

    private boolean checkIsGapful(BigInteger number) {
        if (number.compareTo(BigInteger.valueOf(100)) < 0) return false;
        String numberText = number.toString();
        int no = Integer.parseInt(numberText.charAt(0) + "" + numberText.charAt(numberText.length() - 1));
        return number.mod(BigInteger.valueOf(no)) == BigInteger.ZERO;
    }

    private boolean checkIsPalindrome(BigInteger number) {
        StringBuilder text = new StringBuilder(number.toString());
        StringBuilder reverseText = new StringBuilder(number.toString()).reverse();
        return text.toString().equals(reverseText.toString());
    }

    private boolean checkIsBuzzNumber(BigInteger number) {
        int lastDigit = number.mod(BigInteger.TEN).intValue();
        boolean endWith7 = lastDigit == 7;
        boolean isDivisible = endWith7 || isDivisibleBy7(number);
        return endWith7 || isDivisible;
    }

    private static boolean checkIsDuckNumber(BigInteger number) {
        // iterate through number till become a zero:)
        while (number.compareTo(BigInteger.ZERO) > 0) {
            if (number.mod(BigInteger.TEN) == BigInteger.ZERO) {
                return true;
            }
            number = number.divide(BigInteger.TEN);
        }
        return false;
    }

    private static boolean isDivisibleBy7(BigInteger number) {
        return number.mod(BigInteger.valueOf(7)) == BigInteger.ZERO;
    }

    public boolean isEven() {
        return isEven;
    }

    public boolean isOdd() {
        return isOdd;
    }

    public boolean isBuzz() {
        return isBuzz;
    }

    public boolean isDuck() {
        return isDuck;
    }

    public boolean isGapful() {
        return isGapful;
    }

    public boolean isPalindrome() {
        return isPalindrome;
    }

    public boolean isSpy() {
        return isSpy;
    }

    public boolean isSunny() {
        return isSunny;
    }

    public boolean isSquare() {
        return isSquare;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean isSad() {
        return isSad;
    }

    public boolean isHappy() {
        return isHappy;
    }

    @Override
    public String toString() {
        StringBuilder formatText = new StringBuilder();

        formatText.append("Properties of "
                + NumberFormat.getNumberInstance(Locale.US).format(number)
                + "\n");
        formatText.append(String.format("%12s: %s%n", "buzz", isBuzz));
        formatText.append(String.format("%12s: %s%n", "duck", isDuck));
        formatText.append(String.format("%12s: %s%n", "palindromic", isPalindrome));
        formatText.append(String.format("%12s: %s%n", "gapful", isGapful));
        formatText.append(String.format("%12s: %s%n", "spy", isSpy));
        formatText.append(String.format("%12s: %s%n", "sunny", isSunny));
        formatText.append(String.format("%12s: %s%n", "square", isSquare));
        formatText.append(String.format("%12s: %s%n", "even", isEven));
        formatText.append(String.format("%12s: %s%n", "odd", isOdd));
        formatText.append(String.format("%12s: %s%n", "jumping", isJumping));
        formatText.append(String.format("%12s: %s%n", "sad", isSad));
        formatText.append(String.format("%12s: %s%n", "happy", isHappy));


        return formatText.toString();
    }
}
