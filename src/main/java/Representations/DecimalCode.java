package Representations;

public class DecimalCode implements Representation {

    private StringBuilder binary;
    private String decimal;

    public DecimalCode(String decimal) {
        setValueByDecimal(decimal);
    }

    public String toBinary() {
        return binary.toString();
    }

    public String toDecimal() {
        return decimal;
    }

    public DecimalCode setValueByDecimal(String num) {
        this.decimal = num;
        binary = new StringBuilder();
        if (num.charAt(0) == '-') {
            binary.append(1);
            num = num.substring(1);
        } else {
            binary.append(0);
        }
        for (int i = 0; i < 32 - 4 * num.length(); i++) {
            binary.append('0');
        }
        for (int i = 0; i < num.length(); i++) {
            StringBuilder part = new StringBuilder(Integer.toBinaryString(num.charAt(i) - '0'));
            while (part.length() < 4) {
                part.insert(0, "0");
            }
            binary.append(part);
        }
        return this;
    }

    public DecimalCode setValueByBinary(String num) {
        binary = new StringBuilder(num);
        StringBuilder decimalBuilder = new StringBuilder();
        for (int i = 1; i < 33; i += 4) {
            String binaryValue = num.substring(i, i + 4);
            int decimalValue = Integer.valueOf(binaryValue, 2);
            if (decimalValue != 0 || decimalBuilder.length() != 0) decimalBuilder.append(decimalValue);
        }
        if (decimalBuilder.length() == 0) decimal = "0";
        if (num.charAt(0) == '1') decimalBuilder.insert(0, '-');
        decimal = decimalBuilder.toString();
        return this;
    }

    public char getSign() {
        return binary.charAt(0);
    }
}
