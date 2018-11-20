public class FloatNumber extends ALU {

    public FloatNumber(String n1, String n2) {
        super(n1, n2);
    }

    protected String toDecimal(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        if (isAllZero(binaryExponent) && isAllZero(binarySignificant)) {
            return "0";
        } else if (isAllOne(binaryExponent) && isAllZero(binarySignificant)) {
            if (bin.charAt(0) == '0') return "plus infinity";
            else return "minus infinity";
        } else if (isAllOne(binaryExponent) && !isAllZero(binarySignificant)) {
            return "NaN";
        } else if (isAllZero(binaryExponent) && !isAllZero(binarySignificant)){
            return readBinaryDenormalizd(bin);
        } else {
            return readBinaryNormalized(bin);
        }
    }

    private String readBinaryNormalized(String bin) {
        String binaryExponent = bin.substring(1, 9);
        String binarySignificant = bin.substring(9, 32);
        int decimalExponent = readBinaryExponent(binaryExponent);
        double decimalSignificant = readBinarySignificant(binarySignificant, true);
        double decimalValue = decimalSignificant * Math.pow(2, decimalExponent);
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private String readBinaryDenormalizd(String bin) {
        String binarySignificant = bin.substring(9, 32);
        double decimalSignificant = readBinarySignificant(binarySignificant, false);
        double decimalValue = decimalSignificant * Math.pow(2, -126);
        if (bin.charAt(0) == '1') decimalValue *= -1;
        return String.valueOf(decimalValue);
    }

    private int readBinaryExponent(String binaryExponent) {
        int decimalvalue = 0;
        for (int i = 0; i < binaryExponent.length(); i++) {
            decimalvalue  = decimalvalue << 1;
            if (binaryExponent.charAt(i) == '1') decimalvalue += 1;
        }
        decimalvalue -= 127;
        return decimalvalue;
    }

    private double readBinarySignificant(String binarySignificant, boolean isNormalized) {
        double decimalValue = 0;
        if (isNormalized) {
            decimalValue += 1;
        }
        double fractionValue = 0;
        for (int i = binarySignificant.length() - 1; i >= 0; i--) {
            fractionValue  *= 0.5;
            if (binarySignificant.charAt(i) == '1') fractionValue += 0.5;
        }
        decimalValue += fractionValue;
        return decimalValue;
    }

    private boolean isAllOne(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '1') return false;
        }
        return true;
    }

    private boolean isAllZero(String bin) {
        for (int i = 0; i < bin.length(); i++) {
            if (bin.charAt(i) != '0') return false;
        }
        return true;
    }

    protected String toBinary(String n) {
        return null;
    }

    protected String add(String s1, String s2) {
        return null;
    }

    protected String sub(String s1, String s2) {
        return null;
    }

    protected String multi(String s1, String s2) {
        return null;
    }

    protected void division(String s1, String s2) {

    }
}
